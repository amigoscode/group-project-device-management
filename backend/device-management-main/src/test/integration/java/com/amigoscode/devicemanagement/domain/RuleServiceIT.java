package com.amigoscode.devicemanagement.domain;

import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.domain.rule.RuleService;
import com.amigoscode.devicemanagement.domain.rule.model.Rule;
import integration.java.com.amigoscode.devicemanagement.TestRuleFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RuleServiceIT  extends BaseIT {

    @Autowired
    RuleService service;

    @Test
    void add_rule_test() {
        //given
        Rule rule  = TestRuleFactory.createRule();
        service.save(rule);

        //when
        Rule readRule = service.findById(rule.getId());

        //then
        Assertions.assertEquals(rule.getId(), readRule.getId());
        Assertions.assertEquals(rule.getName(), readRule.getName());
        Assertions.assertEquals(rule.getIsActive(), readRule.getIsActive());
        Assertions.assertEquals(rule.getTopicPattern(), readRule.getTopicPattern());
        Assertions.assertEquals(rule.getMethod(), readRule.getMethod());
        Assertions.assertEquals(rule.getWebhookUrl(), readRule.getWebhookUrl());
    }

    @Test
    void get_id_should_return_correct_rule() {
        //given
        Rule readRule1  = TestRuleFactory.createRule();
        Rule readRule2  = TestRuleFactory.createRule();
        Rule readRule3  = TestRuleFactory.createRule();
        service.save(readRule1);
        service.save(readRule2);
        service.save(readRule3);

        //when
        Rule readRule= service.findById(readRule3.getId());

        //then
        Assertions.assertEquals(readRule3.getId(), readRule.getId());
        Assertions.assertEquals(readRule3.getName(), readRule.getName());
        Assertions.assertEquals(readRule3.getIsActive(), readRule.getIsActive());
        Assertions.assertEquals(readRule3.getTopicPattern(), readRule.getTopicPattern());
        Assertions.assertEquals(readRule3.getTopicPattern(), readRule.getTopicPattern());
        Assertions.assertEquals(readRule3.getTopicPattern(), readRule.getTopicPattern());
        Assertions.assertEquals(readRule3.getMethod(), readRule.getMethod());
        Assertions.assertEquals(readRule3.getWebhookUrl(), readRule.getWebhookUrl());
    }

}
