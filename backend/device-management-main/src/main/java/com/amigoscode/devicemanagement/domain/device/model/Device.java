package com.amigoscode.devicemanagement.domain.device.model;

import lombok.Value;

import java.io.Serializable;
import java.util.Date;

@Value
public class Device implements Serializable {

    String id;
    String name;
    String ownerId;
    Date createdAt;
    Date deletedAt;
    Date updatedAt;
    String updatedByclear;



}
