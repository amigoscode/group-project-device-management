package com.amigoscode.devicemanagement.external.storage.rule;

import com.amigoscode.devicemanagement.domain.rule.model.Rule;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
interface RuleEntityMapper {

    RuleEntity toEntity(Rule domain);


    Rule toDomain(RuleEntity entity);

}
