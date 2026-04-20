package com.tests.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties props = new Properties();

    static {
        try (InputStream in = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        // System properties (e.g. -Dbrowser=firefox) override config file
        return System.getProperty(key, props.getProperty(key));
    }

    public static String getBrowser()       { return get("browser"); }
    public static boolean isHeadless()      { return Boolean.parseBoolean(get("headless")); }
    public static String getBaseUrl()       { return get("baseUrl"); }

    public static String getDefaultCity()       { return get("defaultCity"); }

    public static int getDefaultTimeout()   { return Integer.parseInt(get("defaultTimeout")); }
    public static double getSlowMo()        { return Double.parseDouble(get("slowMo")); }
}
