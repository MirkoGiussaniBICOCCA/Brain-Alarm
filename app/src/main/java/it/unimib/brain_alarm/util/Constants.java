package it.unimib.brain_alarm.util;

public class Constants {


    public static final String FRANCE = "fr";
    public static final String ITALY = "it";
    public static final String GERMANY = "de";
    public static final String UNITED_KINGDOM = "gb";
    public static final String UNITED_STATES = "us";
    // Constants for SharedPreferences
    public static final String SHARED_PREFERENCES_FILE_NAME = "it.unimib.worldnews.preferences";
    public static final String SHARED_PREFERENCES_SVEGLIA = "nome sveglia";

    public static final String SHARED_PREFERENCES_COUNTRY_OF_INTEREST = "country_of_interest";

    // Constants for EncryptedSharedPreferences
    public static final String ENCRYPTED_SHARED_PREFERENCES_FILE_NAME = "it.unimib.worldnews.encrypted_preferences";
    public static final String EMAIL_ADDRESS = "email_address";
    public static final String PASSWORD = "password";
    public static final String ID_TOKEN = "google_token";

    // Constants for encrypted files
    public static final String ENCRYPTED_DATA_FILE_NAME = "it.unimib.worldnews.encrypted_file.txt";

    public static final int MINIMUM_PASSWORD_LENGTH = 6;
    public static final String WEAK_PASSWORD_ERROR = "passwordIsWeak";

    // Constants for files contained in assets folder
    public static final String NEWS_API_TEST_JSON_FILE = "newsapi-test.json";


    // Constants for NewsApi (https://newsapi.org)
    public static final String NEWS_API_BASE_URL = "https://newsapi.org/v2/";
    public static final String TOP_HEADLINES_ENDPOINT = "top-headlines";
    public static final String TOP_HEADLINES_COUNTRY_PARAMETER = "country";
    public static final String TOP_HEADLINES_PAGE_SIZE_PARAMETER = "pageSize";
    public static final String TOP_HEADLINES_PAGE_PARAMETER = "page";
    public static final int TOP_HEADLINES_PAGE_SIZE_VALUE = 10;

    // Constants for refresh rate of news
    public static final String LAST_UPDATE = "last_update";
    public static final int FRESH_TIMEOUT = 0; //1000 * 60 * 60; // 1 hour in milliseconds

    // Constants for Room database
    public static final String NEWS_DATABASE_NAME = "news_db";
    public static final int DATABASE_VERSION = 1;

    public static final String RETROFIT_ERROR = "retrofit_error";
    public static final String API_KEY_ERROR = "api_key_error";

    public static final String UNEXPECTED_ERROR = "unexpected_error";
    public static final String INVALID_CREDENTIALS_ERROR = "invalidCredentials";
    public static final String USER_COLLISION_ERROR = "userCollisionError";
    public static final String INVALID_USER_ERROR = "invalidUserError";
}



