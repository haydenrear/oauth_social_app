package com.app.thread.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Region {

    @DBRef
    List<Thread> regionThreads;
}
