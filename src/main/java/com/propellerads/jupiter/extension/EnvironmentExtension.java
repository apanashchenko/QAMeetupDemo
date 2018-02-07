package com.propellerads.jupiter.extension;

import com.propellerads.config.Config;
import com.propellerads.jupiter.annotation.Environment;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.propellerads.jupiter.Utils.getMethod;

public class EnvironmentExtension implements ExecutionCondition {

    private static final String PROD_URL = "https://partners.propellerads.com";

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Environment env = getMethod(context).getAnnotation(Environment.class);

        switch (env.value()) {
            case PROD:
                return Config.getInstance().getBaseUrl().equals(PROD_URL)
                        ? ConditionEvaluationResult.enabled("Let`s execute")
                        : ConditionEvaluationResult.disabled("Test only for production environment");
            default:
                return ConditionEvaluationResult.disabled("Value " + env.value() + " not supported here, test will be skipped");
        }
    }
}
