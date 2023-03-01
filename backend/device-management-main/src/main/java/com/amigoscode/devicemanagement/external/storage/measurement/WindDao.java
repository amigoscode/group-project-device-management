package com.amigoscode.devicemanagement.external.storage.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
class WindDao {
    Float speed;
    Float direction;
}
