package com.amigoscode.devicemanagement;

import com.amigoscode.devicemanagement.domain.rule.RuleService;
import com.amigoscode.devicemanagement.domain.rule.model.Rule;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@Log
public class DefaultRules implements CommandLineRunner {

    private final RuleService ruleService;

    public DefaultRules(RuleService ruleService){
        this.ruleService = ruleService;
    }

    private final Rule rule1 = new Rule(
            "1",
            "Location Rule",
            true,
            "/^location",
            "/^regex1/i",
           "GET",
            "https://callbackurl.com:1880/pub/modifiedLocation/location-rule-works",
            ZonedDateTime.now(),
            ZonedDateTime.now(),
            ZonedDateTime.now(),
            "Osagie"
    );

    private final Rule rule2 = new Rule(
            "2",
            "Wind Rule",
            false   ,
            "/wind$",
            "/^regex2/i",
            "POST",
            "https://callbackurl.com:1880/pub/modifiedWind/locationrule/wind-rule-works",
            ZonedDateTime.now(),
            ZonedDateTime.now(),
            ZonedDateTime.now(),
            "Rafal"
    );


    @Override
    public void run(String... args){
        try {
            addRule(rule1);
            addRule(rule2);
        } catch (Exception ex) {
            log.warning("Rules already exist");
        }
    }

    private void addRule(Rule rule){
        ruleService.save(rule);
    }
}
