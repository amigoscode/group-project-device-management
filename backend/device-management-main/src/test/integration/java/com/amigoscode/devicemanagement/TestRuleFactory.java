package integration.java.com.amigoscode.devicemanagement;

import com.amigoscode.devicemanagement.domain.rule.model.Rule;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestRuleFactory {

    private static int ruleSequence = 0;
    private static Boolean ruleStatus = true;

    public static Rule createRule(){
        ++ruleSequence;

        return new Rule(
             "Rule " + ruleSequence,
             "Rule" + ruleSequence,
                true,
                "location",
                "location",
               "GET",
                "www.ruleback.com",
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                "Updated By " + ruleSequence
        );
    }
}
