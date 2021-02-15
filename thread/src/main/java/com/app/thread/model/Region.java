package com.app.thread.model;

import com.mongodb.client.model.Indexes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document
@NoArgsConstructor
public class Region {

    @Id
    ObjectId id;

    ObjectId threadPostId;

    String zip;
    String address;
    String state;
    String region;

    @GeoSpatialIndexed(name="polygon")
    GeoJsonPolygon polygon;

}
