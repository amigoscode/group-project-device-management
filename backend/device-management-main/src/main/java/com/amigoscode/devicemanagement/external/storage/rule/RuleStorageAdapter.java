package com.amigoscode.devicemanagement.external.storage.rule;


import com.amigoscode.devicemanagement.domain.rule.RuleRepository;
import com.amigoscode.devicemanagement.domain.rule.exception.RuleAlreadyExistsException;
import com.amigoscode.devicemanagement.domain.rule.model.PageRule;
import com.amigoscode.devicemanagement.domain.rule.model.Rule;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
@Component
class RuleStorageAdapter implements RuleRepository {

    private final DynamoRuleRepository ruleRepository;

    private final RuleEntityMapper mapper;

    @Override
    public Rule save(Rule rule){
        try{
            RuleEntity saved = ruleRepository.save(mapper.toEntity(rule));
            log.info("Saved rule setting entity" + saved);
            return mapper.toDomain(saved);
        } catch (DuplicateKeyException ex) {
            log.warning("Rule " + rule.getId() + "already exists in db");
            throw new RuleAlreadyExistsException();

        }
    }

    @Override
    public void update(Rule rule) {
        ruleRepository.findById(rule.getId()).ifPresent(ruleEntity -> ruleRepository.save(mapper.toEntity(rule)));
    }

    @Override
    public void remove(String id) {
        ruleRepository.findById(id).ifPresent(ruleEntity -> ruleRepository.deleteById(id));
    }

    @Override
    public Optional<Rule> findById(String id) {
        return ruleRepository.findById(id).map((mapper::toDomain));
    }

    @Override
    public Optional<Rule> findByName(String name) {
        return ruleRepository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public PageRule findAll(Pageable pageable) {
        Page<RuleEntity> pageOfRulesEntity = ruleRepository.findAll(pageable);
        List<Rule> rulesOnCurrentPage = pageOfRulesEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageRule(
                rulesOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfRulesEntity.getTotalPages(),
                pageOfRulesEntity.getTotalElements()
        );
    }
}
