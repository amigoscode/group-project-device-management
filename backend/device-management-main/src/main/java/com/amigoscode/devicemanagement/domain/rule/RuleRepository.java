package com.amigoscode.devicemanagement.domain.rule;


import com.amigoscode.devicemanagement.domain.rule.model.PageRule;
import com.amigoscode.devicemanagement.domain.rule.model.Rule;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RuleRepository {

    Rule save(Rule rule);

    void update(Rule rule);

    void remove(String id);

    Optional<Rule> findById(String id);

    Optional<Rule> findByName(String name);

    PageRule findAll(Pageable pageable);

}
