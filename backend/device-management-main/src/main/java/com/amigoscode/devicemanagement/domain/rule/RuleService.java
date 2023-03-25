package com.amigoscode.devicemanagement.domain.rule;

import com.amigoscode.devicemanagement.domain.rule.exception.RuleNotFoundException;
import com.amigoscode.devicemanagement.domain.rule.model.PageRule;
import com.amigoscode.devicemanagement.domain.rule.model.Rule;
import com.amigoscode.devicemanagement.domain.rule.exception.RuleAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    public Rule save(Rule rule){
        if(ruleRepository.findByName(rule.getName()).isPresent()){
            throw new RuleAlreadyExistsException();
        }
        return ruleRepository.save(rule);
    }

    public void update(Rule rule){
         ruleRepository.update(rule);
    }

    public void removeById(String id){
        ruleRepository.remove(id);
    }

    public Rule findById(String id){
        return ruleRepository.findById(id)
                .orElseThrow(RuleNotFoundException::new);
    }

    public Rule findByName(String name){
        return ruleRepository.findByName(name)
                .orElseThrow(RuleNotFoundException::new);
    }

    public PageRule findAll(Pageable pageable){
        return ruleRepository.findAll(pageable);
    }
}
