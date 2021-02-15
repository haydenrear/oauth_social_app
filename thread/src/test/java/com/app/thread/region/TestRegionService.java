package com.app.thread.region;

import com.app.thread.model.Region;
import com.app.thread.service.RegionService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class TestRegionService {

    @Autowired
    RegionService regionService;
    Region region;


    @BeforeEach
    public void BeforeEach(){
       region =  new Region(null, null, "97219", null, null, null, null);
    }

    @Test
    public void testJsonMapper(){
        Mono<String> returnStringMono = regionService
                .getDataFromGoogleByZip(region);
        String returnString = returnStringMono.block();
        log.info(returnString);
        assertThat(returnString).isNotNull();
    }

    @Test
    public void testParser(){
        Mono<String> returnStringMono = regionService
                .getDataFromGoogleByZip(region);
        String returnString = returnStringMono.block();
        GeoJsonPolygon point = regionService.parseData(returnString);
        assertThat(point.getCoordinates()).isNotNull();
    }

    @Test
    public void testSaving(){
        regionService.saveRegionWithPoint(region)
                .map(regionReturn -> {
                    assertThat(regionReturn.getPolygon()).isNotNull();
                    return regionReturn;
                }).subscribe();
    }

}
