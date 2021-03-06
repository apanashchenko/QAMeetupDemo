package com.propellerads.jupiter.extension;

import com.codeborne.selenide.WebDriverRunner;
import com.propellerads.config.Config;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class WebDriverExtension implements BeforeEachCallback, AfterEachCallback, TestExecutionExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverExtension.class);
    private static final String RESOLUTION_FULL_HD = "1920x1080x24";
    private static final Dimension DIMENSION_FULL_HD = new Dimension(1920, 1080);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Optional<Method> testMethod = extensionContext.getTestMethod();
        initDriver(Config.getInstance().getBrowser(),
                testMethod.isPresent() ? testMethod.get().getName() : null);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        getWebDriver().quit();
    }

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        saveScreenShot("Screenshot on fail");
        throw throwable;
    }

    public void initDriver(String browser, String testName) {
        if (Config.getInstance().isRemote()) {
            RemoteWebDriver webDriver = initRemoteDriver(browser, testName, true);
            WebDriverRunner.setWebDriver(webDriver);
        }
    }

    @Step("Init driver for test {testName}")
    private RemoteWebDriver initRemoteDriver(String browser, String testName, boolean retryIfError) {
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setBrowserName(browser);
        capability.setCapability("name", testName);
        capability.setCapability("screenResolution", RESOLUTION_FULL_HD);
        try {
            RemoteWebDriver driver = new RemoteWebDriver(new URL(Config.getInstance().getRemoteUrl()), capability);
            driver.setFileDetector(new LocalFileDetector());
            driver.manage().window().setSize(DIMENSION_FULL_HD);
            if (isAlive(driver)) {
                return driver;
            } else {
                if (retryIfError) {
                    LOGGER.warn("Session not found, try to re-init");
                    return initRemoteDriver(browser, testName, false);
                } else {
                    throw new IllegalStateException("Remote session is die");
                }
            }
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Error while configuring url for remote web driver");
        }
    }

    private boolean isAlive(WebDriver driver) {
        try {
            driver.getCurrentUrl();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Attachment(value = "{description}", type = "image/png")
    private byte[] saveScreenShot(String description) {
        Object screenShot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
        if (screenShot != null)
            return (byte[]) screenShot;
        else
            return new byte[0];
    }
}