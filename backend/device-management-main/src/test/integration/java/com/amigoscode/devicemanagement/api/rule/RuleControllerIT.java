package com.amigoscode.devicemanagement.api.rule;

import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.api.response.ErrorResponse;
import com.amigoscode.devicemanagement.domain.rule.RuleService;
import com.amigoscode.devicemanagement.domain.rule.model.Rule;
import com.amigoscode.devicemanagement.domain.user.UserService;
import integration.java.com.amigoscode.devicemanagement.TestRuleFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleControllerIT extends BaseIT {

    @Autowired
    UserService userService;

    @Autowired
    RuleService ruleService;

    @Autowired
    RuleDtoMapper ruleDtoMapper;

    @Autowired
    PageRuleDtoMapper pageRuleDtoMapper;


    @Test
    void admin_should_be_able_to_get_information_about_a_rule(){
        //given
        String adminAccessToken = getTokenForAdmin();
        Rule  rule = TestRuleFactory.createRule();
        ruleService.save(rule);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/rules/" + rule.getId(),
                adminAccessToken,
                null,
                RuleDto.class);

        //then
        RuleDto body = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareRules(rule, ruleDtoMapper.toDomain(body));

    }

    @Test
    void admin_should_be_able_to_get_information_about_all_rules(){
        //given
        String adminAccessToken = getTokenForAdmin();
        Rule  rule1 = TestRuleFactory.createRule();
        Rule  rule2 = TestRuleFactory.createRule();
        Rule  rule3 = TestRuleFactory.createRule();
        ruleService.save(rule1);
        ruleService.save(rule2);
        ruleService.save(rule3);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/rules",
                adminAccessToken,
                null,
                PageRuleDto.class);

        //then
        PageRuleDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        assertEquals(3, body.getTotalElements());
        List<Rule> rules = List.of(rule1, rule2, rule3);

        assertTrue(rules
                .stream()
                .filter(d -> d.equals(ruleDtoMapper.toDomain(body.getRules().get(0))))
                .findAny().isPresent()
        );
        assertTrue(rules
                .stream()
                .filter(d -> d.equals(ruleDtoMapper.toDomain(body.getRules().get(1))))
                .findAny().isPresent()
        );
        assertTrue(rules
                .stream()
                .filter(d -> d.equals(ruleDtoMapper.toDomain(body.getRules().get(2))))
                .findAny().isPresent()
        );

    }

    @Test
    void admin_should_be_able_to_save_a_new_rule(){
        //given
        String adminAccessToken = getTokenForAdmin();
        Rule rule = TestRuleFactory.createRule();

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/rules",
                adminAccessToken,
                ruleDtoMapper.toDto(rule),
                RuleDto.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        RuleDto body = response.getBody();
        //and
        compareRules(rule, ruleDtoMapper.toDomain(body));
    }

    @Test
    void admin_should_be_able_to_update_a_rule(){
        //given
        String adminAccessToken = getTokenForAdmin();
        Rule rule = TestRuleFactory.createRule();
        ruleService.save(rule);
        Rule updatedRule = new Rule(
                rule.getId(),
                "Updated Rule Name",
                false,
                "location",
                "location",
                "POST",
                "http://loaction.com",
                rule.getCreatedAt().plusDays(7),
                rule.getDeletedAt().plusDays(10),
                rule.getUpdatedAt().plusDays(8),
                "New Updated By"
        );

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/rules/" + rule.getId(),
                adminAccessToken,
                ruleDtoMapper.toDto(updatedRule),
                RuleDto.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        RuleDto body = response.getBody();
        Assertions.assertNull(body);
        //and
        Rule ruleFromDb = ruleService.findById(rule.getId());
        compareRules(updatedRule, ruleFromDb);

    }

    @Test
    void admin_should_be_able_to_delete_a_rule(){
        //given
        String adminAccessToken = getTokenForAdmin();
        Rule rule = TestRuleFactory.createRule();
        ruleService.save(rule);

        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/rules/" + rule.getId(),
                adminAccessToken,
                ruleDtoMapper.toDto(rule),
                Void.class);

        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    void admin_should_not_be_able_to_add_a_duplicate_rule(){
        //given
        String adminAccessToken = getTokenForAdmin();
        Rule rule = TestRuleFactory.createRule();
        ruleService.save(rule);

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/rules",
                adminAccessToken,
                ruleDtoMapper.toDto(rule),
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void should_return_conflict_about_duplicate_rule(){
        //given
        String adminAccessToken = getTokenForAdmin();
        Rule rule = TestRuleFactory.createRule();
        ruleService.save(rule);

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/rules",
                adminAccessToken,
                ruleDtoMapper.toDto(rule),
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    private void compareRules(Rule model, Rule tested){
        assertNotNull(tested);
        assertEquals(model.getId(), tested.getId());
        assertEquals(model.getName(), tested.getName());
        assertEquals(model.getIsActive(), tested.getIsActive());
        assertEquals(model.getTopicPattern(), tested.getTopicPattern());
        assertEquals(model.getPayloadPattern(), tested.getPayloadPattern());
        assertEquals(model.getMethod(), tested.getMethod());
        assertEquals(model.getWebhookUrl(), tested.getWebhookUrl());
        assertEquals(model.getCreatedAt().toLocalDateTime(), tested.getCreatedAt().toLocalDateTime());
        assertEquals(model.getDeletedAt().toLocalDateTime(), tested.getDeletedAt().toLocalDateTime());
        assertEquals(model.getUpdatedAt().toLocalDateTime(), tested.getUpdatedAt().toLocalDateTime());
        assertEquals(model.getUpdatedBy(), tested.getUpdatedBy());

    }

}
