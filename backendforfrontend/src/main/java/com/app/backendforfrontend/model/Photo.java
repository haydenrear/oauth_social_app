package com.app.backendforfrontend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
@NoArgsConstructor
public class Photo {

    byte [] binary;
    String fileType;
    String id;

}
