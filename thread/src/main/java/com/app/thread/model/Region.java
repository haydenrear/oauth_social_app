package com.app.thread.model;

import com.app.thread.service.RegionService;
import com.mongodb.client.model.Indexes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import reactor.util.function.Tuple2;


@Data
@AllArgsConstructor
@Document
@NoArgsConstructor
@Scope("prototype")
@Component
public class Region {

    @Id
    String id;

    RegionService regionService;

    String threadPostId;

    String zip;
    String address;
    String state;
    String region;
    String city;

    @GeoSpatialIndexed(name="zipPoly")
    private GeoJsonPolygon zipPoly;
    private GeoJsonPoint location;

    public Region(String zip, String address, String state, String region, String city) {
        this.zip = zip;
        this.address = address;
        this.state = state;
        this.region = region;
        this.city = city;
    }

    public void saveRegion(){
        regionService
                .saveRegionWithPoint(this)
                .subscribe();
    }

    public void setRegionService(RegionService regionService){
        this.regionService = regionService;
    }

    public void setLocation(Tuple2<GeoJsonPolygon, GeoJsonPoint> locationData){
        this.location = locationData.getT2();
        this.zipPoly = locationData.getT1();
    }

}
