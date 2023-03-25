package com.amigoscode.devicemanagement.domain.rule;

import com.amigoscode.devicemanagement.domain.rule.exception.RuleAlreadyExistsException;
import com.amigoscode.devicemanagement.domain.rule.exception.RuleNotFoundException;
import com.amigoscode.devicemanagement.domain.rule.model.Rule;
import com.amigoscode.devicemanagement.domain.rule.model.RuleCallBackMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class RuleServiceTest {

    @Mock
    private RuleRepository ruleRepository;

    @InjectMocks
    private RuleService ruleService;

    private final Rule fakeRule = new Rule(
            "ID28",
            "testRule",
            true,
            "temperature",
            "test",
            Set.of(RuleCallBackMethod.GET),
            "http://iotdevmgr.com",
            ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
            ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
            ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
            "updatedBy"
    );

    @Test
    void update_method_should_not_throw_exception(){
        // Expect
        Assertions.assertDoesNotThrow(() -> ruleService.update(fakeRule));
    }

    @Test
     void delete_method_should_not_throw_exception() {
        Assertions.assertDoesNotThrow(() -> ruleService.removeById(fakeRule.getId()));
    }

    @Test
      void save_method_should_return_saved_rule_when_rule_does_not_exist() {
        Mockito.when(ruleRepository.save(
                fakeRule
        )).thenReturn(fakeRule);

        Rule savedRule = ruleService.save(fakeRule);

        Assertions.assertNotNull(savedRule);
        Assertions.assertEquals(fakeRule.getId(), savedRule.getId());
        Assertions.assertEquals(fakeRule.getName(), savedRule.getName());
        Assertions.assertEquals(fakeRule.getIsActive(), savedRule.getIsActive());
        Assertions.assertEquals(fakeRule.getTopicPattern(), savedRule.getTopicPattern());
        Assertions.assertEquals(fakeRule.getPayloadPattern(), savedRule.getPayloadPattern());
        Assertions.assertEquals(fakeRule.getMethod(), savedRule.getMethod());
        Assertions.assertEquals(fakeRule.getWebhookUrl(), savedRule.getWebhookUrl());
        Assertions.assertEquals(fakeRule.getCreatedAt(), savedRule.getCreatedAt());
        Assertions.assertEquals(fakeRule.getUpdatedAt(), savedRule.getUpdatedAt());
        Assertions.assertEquals(fakeRule.getDeletedAt(), savedRule.getDeletedAt());
        Assertions.assertEquals(fakeRule.getUpdatedBy(), savedRule.getUpdatedBy());


    }

    @Test
     void save_method_should_throw_rule_already_exist_exception_when_rule_exists() {
        Mockito.when(ruleRepository.save(
                fakeRule
        )).thenThrow(new RuleAlreadyExistsException());
        //when
        //then
        Assertions.assertThrows(RuleAlreadyExistsException.class,
                ()-> ruleService.save(fakeRule));
    }

    @Test
     void find_by_name_method_should_return_found_rule_when_rule_exists() {
        Mockito.when(ruleRepository.findByName(
                fakeRule.getName()
        )).thenReturn(Optional.of(fakeRule));

        //when
        Rule foundRule = ruleService.findByName(fakeRule.getName());

        //then
        Assertions.assertNotNull(foundRule);
        Assertions.assertEquals(fakeRule.getId(), foundRule.getId());
        Assertions.assertEquals(fakeRule.getName(), foundRule.getName());
        Assertions.assertEquals(fakeRule.getIsActive(), foundRule.getIsActive());
        Assertions.assertEquals(fakeRule.getTopicPattern(), foundRule.getTopicPattern());
        Assertions.assertEquals(fakeRule.getPayloadPattern(), foundRule.getPayloadPattern());
        Assertions.assertEquals(fakeRule.getMethod(), foundRule.getMethod());
        Assertions.assertEquals(fakeRule.getWebhookUrl(), foundRule.getWebhookUrl());
        Assertions.assertEquals(fakeRule.getCreatedAt(), foundRule.getCreatedAt());
        Assertions.assertEquals(fakeRule.getUpdatedAt(), foundRule.getUpdatedAt());
        Assertions.assertEquals(fakeRule.getDeletedAt(), foundRule.getDeletedAt());
        Assertions.assertEquals(fakeRule.getUpdatedBy(), foundRule.getUpdatedBy());
    }

    @Test
     void find_by_name_method_should_throw_rule_not_found_exception_when_rule_does_not_exist(){
        Mockito.when(ruleRepository.findByName(
                fakeRule.getName()
        )).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(RuleNotFoundException.class,
                ()-> ruleService.findByName(fakeRule.getName()));
    }

    @Test
    void find_by_id_method_should_return_found_rule_when_rule_exists(){
        Mockito.when(ruleRepository.findById(
                fakeRule.getId()
        )).thenReturn(Optional.of(fakeRule));

        //when
        Rule foundRule = ruleService.findById(fakeRule.getId());

        //then
        Assertions.assertNotNull(foundRule);
        Assertions.assertEquals(fakeRule.getId(), foundRule.getId());
        Assertions.assertEquals(fakeRule.getName(), foundRule.getName());
        Assertions.assertEquals(fakeRule.getIsActive(), foundRule.getIsActive());
        Assertions.assertEquals(fakeRule.getTopicPattern(), foundRule.getTopicPattern());
        Assertions.assertEquals(fakeRule.getPayloadPattern(), foundRule.getPayloadPattern());
        Assertions.assertEquals(fakeRule.getMethod(), foundRule.getMethod());
        Assertions.assertEquals(fakeRule.getWebhookUrl(), foundRule.getWebhookUrl());
        Assertions.assertEquals(fakeRule.getCreatedAt(), foundRule.getCreatedAt());
    }

    @Test
    void find_by_id_method_should_throw_rule_not_found_exception_when_rule_does_not_exist(){
        Mockito.when(ruleRepository.findById(
                fakeRule.getId()
        )).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(RuleNotFoundException.class,
                ()-> ruleService.findById(fakeRule.getId()));
    }

}
