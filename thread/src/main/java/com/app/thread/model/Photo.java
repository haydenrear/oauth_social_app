package com.app.thread.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
@Document
@NoArgsConstructor
public class Photo {

    @Id String id;

    byte [] binary;
    String fileType;

    public Photo(byte[] binary, String fileType) {
        this.binary = binary;
        this.fileType = fileType;
    }
}
