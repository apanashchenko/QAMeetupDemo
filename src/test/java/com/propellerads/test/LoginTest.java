package com.propellerads.test;

import com.propellerads.config.Config;
import com.propellerads.domain.User;
import com.propellerads.jupiter.annotation.Convert;
import com.propellerads.jupiter.annotation.Environment;
import com.propellerads.jupiter.annotation.Id;
import com.propellerads.jupiter.annotation.Inject;
import com.propellerads.jupiter.extension.DIFieldsExtension;
import com.propellerads.jupiter.extension.UserParameterResolver;
import com.propellerads.jupiter.extension.WebDriverExtension;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.propellerads.domain.User.State.DISABLED;
import static com.propellerads.domain.User.State.LOCKED;
import static com.propellerads.jupiter.annotation.Environment.EnvType.PROD;
import static org.junit.jupiter.params.provider.Arguments.of;

@Feature("Login actions")
@DisplayName("Example tests for login page")
@ExtendWith({WebDriverExtension.class, DIFieldsExtension.class})
public class LoginTest {

    @Inject
    private Config config;

    static Stream<Arguments> advSource() {
        return Stream.of(
                of(101, DISABLED, "User account is disabled"),
                of(101, LOCKED, "Your account has been permanently terminated for violating the Advertiser Agreement")
        );
    }

    @DisplayName("Check login for disabled or locked users")
    @Environment(PROD)
    @MethodSource("advSource")
    @ParameterizedTest
    void loginDeniedForLockedUser(@Id int caseId,
                                  @Convert User user,
                                  String expectedMessage) {
        open(config.getBaseUrl());
        $("#username").setValue(user.getLogin());
        $("#password").setValue(user.getPassword());

        $("button[type='submit']").click();
        $("div[class*='alert_error']").shouldBe(visible)
                .shouldHave(text(expectedMessage));
    }


}
