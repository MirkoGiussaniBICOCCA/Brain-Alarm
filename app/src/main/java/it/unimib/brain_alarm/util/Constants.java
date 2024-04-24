package it.unimib.brain_alarm.util;

public class Constants {



    // Constants for SharedPreferences
    public static final String SHARED_PREFERENCES_FILE_NAME = "it.unimib.worldnews.preferences";
    public static final String SHARED_PREFERENCES_SVEGLIA = "nome sveglia";

    public static final String SHARED_PREFERENCES_COUNTRY_OF_INTEREST = "country_of_interest";
    // Constants for files contained in assets folder
    public static final String NEWS_API_TEST_JSON_FILE = "newsapi-test.json";


    // Constants for NewsApi (https://newsapi.org)
    public static final String NEWS_API_BASE_URL = "https://newsapi.org/v2/";
    public static final String TOP_HEADLINES_ENDPOINT = "top-headlines";
    public static final String TOP_HEADLINES_COUNTRY_PARAMETER = "country";
    public static final String TOP_HEADLINES_PAGE_SIZE_PARAMETER = "pageSize";
    public static final int TOP_HEADLINES_PAGE_SIZE_VALUE = 100;

    // Constants for refresh rate of news
    public static final String LAST_UPDATE = "last_update";
    public static final int FRESH_TIMEOUT = 1000 * 60 * 60; // 1 hour in milliseconds

    // Constants for Room database
    public static final String NEWS_DATABASE_NAME = "news_db";
    public static final int DATABASE_VERSION = 1;

}
