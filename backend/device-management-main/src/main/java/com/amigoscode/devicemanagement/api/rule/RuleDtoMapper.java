package com.amigoscode.devicemanagement.api.rule;

import com.amigoscode.devicemanagement.domain.rule.model.Rule;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
interface RuleDtoMapper {

    RuleDto toDto(Rule domain);

    Rule toDomain(RuleDto dto);
}
