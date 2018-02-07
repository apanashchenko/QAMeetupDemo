package com.propellerads.config;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;

public class Config {

    private static Config instance;

    private Config() {
        PropertyLoader.populate(this);
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }

    @Property("base.url")
    private String baseUrl = "https://partners.propellerads.com";

    @Property("is.remote")
    private boolean isRemote = false;

    @Property("selenide.browser")
    private String browser = "chrome";

    @Property("remote.driver.url")
    private String remoteUrl = "";

    public String getBaseUrl() {
        return baseUrl;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public String getBrowser() {
        return browser;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }
}
