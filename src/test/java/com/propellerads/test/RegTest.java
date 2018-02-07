package com.propellerads.test;

import com.propellerads.config.Config;
import com.propellerads.domain.User;
import com.propellerads.jupiter.annotation.Environment;
import com.propellerads.jupiter.annotation.Inject;
import com.propellerads.jupiter.annotation.TestCaseId;
import com.propellerads.jupiter.extension.DIFieldsExtension;
import com.propellerads.jupiter.extension.UserParameterResolver;
import com.propellerads.jupiter.extension.WebDriverExtension;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.propellerads.jupiter.annotation.Environment.EnvType.PROD;

@Feature("Login actions")
@DisplayName("Example tests for registration page")
@ExtendWith({WebDriverExtension.class, UserParameterResolver.class, DIFieldsExtension.class})
public class RegTest {

    @Inject
    private Config config;

    @DisplayName("Show tooltip in case that email is incorrect")
    @Environment(PROD)
    @TestCaseId(102)
    @Test
    void showToolTipForIncorrectEmail(@Inject User user) {
        open(config.getBaseUrl() + "/#/app/auth/forgotPassword");
        $("#email").setValue(user.getLogin());
        $$(".button").findBy(text("Send")).click();

        $(".form-group__input-errors").click();
        $(".form-group__input-errors-bubble").shouldBe(visible)
                .shouldHave(text("Invalid email"));
    }


    @DisplayName("field 'VAT ID' should be visible for registration from EC")
    @Environment(PROD)
    @ValueSource(strings = {"Germany", "Spain"})
    @TestCaseId(103)
    @ParameterizedTest
    void showVatField(String country) {
        open(config.getBaseUrl() + "/#/app/auth/signUp");
        $("input[name='radioCompany'] ~ span").click();
        $("#vatId").shouldNotBe(visible);
        $("#_value").setValue(country).pressEnter();

        $("#vatId").shouldBe(visible);
    }

}
