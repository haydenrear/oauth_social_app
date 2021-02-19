package com.app.thread.service;

import com.app.thread.model.Region;
import com.app.thread.model.ThreadPost;
import com.app.thread.repo.RegionRepo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Service
public class RegionService {

    @Value("${google.maps.geocode.api.key}")
    String GEOCODEAPIKEY;

    @Value("${google.maps.geocode.api.url}")
    String GEOCODEAPIURL;

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
                .map(this::parseData)
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

    public Tuple2<GeoJsonPolygon, GeoJsonPoint> parseData(String dataToParse){
            try {
                JSONParser jsonParser = new JSONParser();
                JSONObject parse = (JSONObject) jsonParser.parse(dataToParse);
                JSONArray array = (JSONArray) parse.get("results");
                JSONObject innerObj = (JSONObject) jsonParser.parse(array.get(0).toString());
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
                .map(this::parseData)
                .map(Tuple2::getT1)
                .flatMapMany(regionRepo::findByLocationIsWithin);
    }
}
