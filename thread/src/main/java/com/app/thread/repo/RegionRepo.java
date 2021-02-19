package com.app.thread.repo;

import com.app.thread.model.Region;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface RegionRepo extends ReactiveMongoRepository<Region, String> {
    Flux<Region> findByLocationIsWithin(GeoJsonPolygon geoJsonPolygon);
}