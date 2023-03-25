package com.amigoscode.devicemanagement.api.rule;


import com.amigoscode.devicemanagement.domain.rule.RuleService;
import com.amigoscode.devicemanagement.domain.rule.model.Rule;
import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.amigoscode.devicemanagement.domain.user.model.UserRole.ADMIN;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/rules",
        produces = "application/json",
        consumes = "application/json"
)

class RuleController {

    private final RuleService ruleService;
    private final RuleDtoMapper ruleMapper;
    private final PageRuleDtoMapper pageRuleDtoMapper;
    private final UserService userService;


    @GetMapping(path = "/{ruleId}")
    public ResponseEntity<RuleDto> getRule(@PathVariable String ruleId){
        Rule rule = ruleService.findById(ruleId);
        return ResponseEntity
                .ok(ruleMapper.toDto(rule));
    }


    @GetMapping
    public ResponseEntity<PageRuleDto> getRules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());

        Pageable pageable = PageRequest.of(page, size);
        PageRuleDto pageRules;

        if (user.getRoles().contains(ADMIN)) {
            pageRules = pageRuleDtoMapper.toPageDto(ruleService.findAll(pageable));
        }
        else {
            pageRules = pageRuleDtoMapper.toPageDto(ruleService.findAll(pageable));
        }


        return ResponseEntity.ok(pageRules);
    }


    @PostMapping
    public ResponseEntity<RuleDto> saveRule(@RequestBody RuleDto dto){
    Rule rule = ruleService.save(ruleMapper.toDomain(dto));
    return ResponseEntity
            .ok(ruleMapper.toDto(rule));
    }


    @PutMapping(path = "/{ruleId}")
    public ResponseEntity<Void> updateRule(@PathVariable String ruleId, @RequestBody RuleDto dto){
        ruleService.update(ruleMapper.toDomain(dto));

        return ResponseEntity.ok().build();
    }


    @DeleteMapping(path = "/{ruleId}")
    public ResponseEntity<Void> removeRule(@PathVariable String ruleId){
        ruleService.removeById(ruleId);
        return ResponseEntity.noContent().build();
    }
}
