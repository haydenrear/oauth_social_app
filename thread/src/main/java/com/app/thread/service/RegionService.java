package com.app.thread.service;

import com.app.thread.model.Region;
import com.app.thread.model.ThreadPost;
import com.app.thread.repo.RegionRepo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Optional;

@Service
public class RegionService {

    @Value("${google.maps.geocode.api.key}")
    String GEOCODEAPIKEY;

    @Value("${google.maps.geocode.api.url}")
    String GEOCODEAPIURL;

    @Value("${google.maps.geocode.api.reverse.url}")
    String GEOCODEREVERSEURL;

    public RegionService(RegionRepo regionRepo, WebClient client, ReactiveMongoTemplate template) {
        this.regionRepo = regionRepo;
        this.webClient = client;
    }

    RegionRepo regionRepo;
    WebClient webClient;

    public Mono<Region> saveRegionWithPoint(Region regionIn){
        return setPointZip(regionIn)
            .flatMap(point -> regionRepo.save(regionIn));
    }

    public Mono<GeoJsonPolygon> setPointZip(Region regionIn){
        return getDataFromGoogle(regionIn.getZip())
                .map(response -> parseData(response, 0))
                .doOnNext(regionIn::setLocation)
                .map(Tuple2::getT1);
    }

    public Mono<String> getDataFromGoogle(String byBlank){
        return webClient
                .get()
                .uri(GEOCODEAPIURL+byBlank+"&key="+GEOCODEAPIKEY)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getDataByReverse(String byBlank){
        System.out.println(GEOCODEREVERSEURL+byBlank+"&key="+GEOCODEAPIKEY);
        return webClient
                .get()
                .uri(GEOCODEREVERSEURL+byBlank+"&key="+GEOCODEAPIKEY)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Tuple2<GeoJsonPolygon, GeoJsonPoint> parseData(String dataToParse, int latLongIsTwoAddressIsZero){
            try {
                JSONParser jsonParser = new JSONParser();
                JSONObject parse = (JSONObject) jsonParser.parse(dataToParse);
                JSONArray array = (JSONArray) parse.get("results");
                JSONObject innerObj = (JSONObject) jsonParser.parse(array.get(latLongIsTwoAddressIsZero).toString());
                JSONObject geometry = (JSONObject) jsonParser.parse(innerObj.get("geometry").toString());
                JSONObject bounds = (JSONObject) jsonParser.parse(geometry.get("bounds").toString());
                JSONObject northeast = (JSONObject) jsonParser.parse(bounds.get("northeast").toString());
                JSONObject southwest= (JSONObject) jsonParser.parse(bounds.get("southwest").toString());
                JSONObject location = (JSONObject) jsonParser.parse(geometry.get("location").toString());
                GeoJsonPoint northeastPoint = new GeoJsonPoint((Double) northeast.get("lng"),(Double) northeast.get("lat"));
                GeoJsonPoint southwestPoint = new GeoJsonPoint((Double) southwest.get("lng"), (Double) southwest.get("lat"));
                GeoJsonPoint locationPoint = new GeoJsonPoint((Double) location.get("lng"),(Double) location.get("lat"));
                double xNorthwest = southwestPoint.getX();
                double yNorthwest = northeastPoint.getY();
                GeoJsonPoint northwestPoint = new GeoJsonPoint(xNorthwest, yNorthwest);
                double xSoutheast = northeastPoint.getX();
                double ySoutheast = southwestPoint.getY();
                GeoJsonPoint finishLoop = new GeoJsonPoint(southwestPoint.getX(), northeastPoint.getY());
                GeoJsonPoint southeastPoint = new GeoJsonPoint(xSoutheast, ySoutheast);
                return Tuples.of(new GeoJsonPolygon(northwestPoint, southwestPoint, southeastPoint, northeastPoint, finishLoop), locationPoint);
            } catch (ParseException ie) {
                ie.printStackTrace();
            }
            return null;
    }

    public Flux<Region> findRegionsNearAny(String any) {
        return getDataFromGoogle(any)
                .map(response -> parseData(response, 0))
                .map(Tuple2::getT1)
                .flatMapMany(regionRepo::findByLocationIsWithin);
    }

    public Flux<Region> findRegionsByLongLat(String longLatitude) {
        return getDataByReverse(longLatitude)
                .map(response -> parseData(response, 2))
                .map(Tuple2::getT1)
                .map(this::getCenter)
                .flatMapMany(regionRepo::findByLocationIsNearOrderByLocationDesc);
    }

    public GeoJsonPoint getCenter(GeoJsonPolygon geoJsonPolygon){

        double highestX = 10000d;
        double lowestX = -10000d;
        double highestY = 10000d;
        double lowestY = -10000d;

        for(Point point : geoJsonPolygon.getPoints()){
            if(point.getX() < lowestX) {
                lowestX = point.getX();
            }
            if(point.getY() > highestY){
                highestY = point.getY();
            }
            if(point.getX() > highestX) {
                highestX = point.getX();
            }
            if(point.getY() < lowestY){
                lowestY = point.getY();
            }
        }

        double centerX = lowestX + ((highestX - lowestX) / 2);
        double centerY = lowestY + ((highestY - lowestY) / 2);

        return new GeoJsonPoint(centerX, centerY);
    }
}
