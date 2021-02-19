package com.app.thread.service;

import com.app.thread.model.Region;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class TestRegionService {

    @Autowired
    RegionService regionService;
    Region region;


    @BeforeEach
    public void BeforeEach(){
       region =  new Region("97219", "2085 Oakmont Way", "OR", "midwest", "Eugene");
    }

    @Test
    public void testJsonMapper(){
        Mono<String> returnStringMono = regionService
                .getDataFromGoogle(region.getZip());
        String returnString = returnStringMono.block();
        log.info(returnString);
        assertThat(returnString).isNotNull();
    }

    @Test
    public void testParser(){
        Mono<String> returnStringMono = regionService
                .getDataFromGoogle(region.getAddress());
        String returnString = returnStringMono.block();
        Tuple2<GeoJsonPolygon, GeoJsonPoint> point = regionService.parseData(returnString);
        assertThat(point.getT1()).isNotNull();
    }

    @Test
    public void testSaving(){
        regionService.saveRegionWithPoint(region)
                .map(regionReturn -> {
                    assertThat(regionReturn.getZipPoly()).isNotNull();
                    return regionReturn;
                }).subscribe();
    }

    @Test
    public void findRegion(){
        regionService.findRegionsNearAny("Oregon")
                .subscribe(System.out::println);
    }

}
