package com.propellerads.test;

import com.propellerads.config.Config;
import com.propellerads.jupiter.annotation.Environment;
import com.propellerads.jupiter.annotation.Inject;
import com.propellerads.jupiter.annotation.TestCaseId;
import com.propellerads.jupiter.extension.DIFieldsExtension;
import com.propellerads.jupiter.extension.WebDriverExtension;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.propellerads.jupiter.annotation.Environment.EnvType.PROD;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Login actions")
@DisplayName("Example tests with AssertAll & @Nested")
@ExtendWith({WebDriverExtension.class, DIFieldsExtension.class})
public class AssertAllTest {

    @Inject
    private Config config;

    @DisplayName("Assert all demo test")
    @Environment(PROD)
    @TestCaseId(104)
    @Test
    void checkAllFieldsVisivle() {
        open(config.getBaseUrl() + "/#/app/auth/signUp");
        assertAll(
                () -> assertTrue($("#firstName").is(visible)),
                () -> assertTrue($("#lastName").is(visible)),
                () -> assertTrue($("#_value").is(visible)),
                () -> assertTrue($("#city").is(visible)),
                () -> assertTrue($("#address").is(visible)),
                () -> assertTrue($("#email").is(visible))
        );
    }

}
