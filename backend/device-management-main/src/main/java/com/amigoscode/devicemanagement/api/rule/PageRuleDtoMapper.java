package com.amigoscode.devicemanagement.api.rule;

import com.amigoscode.devicemanagement.domain.rule.model.PageRule;
import com.amigoscode.devicemanagement.domain.rule.model.Rule;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;


@Mapper(componentModel = "spring")
interface PageRuleDtoMapper {

    @Mapping(target = "rules", qualifiedByName = "toRuleDtoList")
    PageRuleDto toPageDto(PageRule domain);

    @Named("toRuleDtoList")
    @IterableMapping(qualifiedByName = "ruleToRuleDto")
    List<RuleDto> toListDto(List<Rule> rules);

    @Named("ruleToRuleDto")
    RuleDto  toDto(Rule domain);
}
