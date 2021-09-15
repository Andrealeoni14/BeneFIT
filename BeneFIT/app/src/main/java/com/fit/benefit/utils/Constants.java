// file containing the constants used in the .java classes
package com.fit.benefit.utils;

public class Constants {

    public static final String EXERCISES_API_BASE_URL = "https://wger.de/api/v2/exercise/?language=2&limit=300&offset=0";
    public static final String EXERCISES_API_CATEGORY_URL = "https://wger.de/api/v2/exercise/?language=2&limit=300&offset=0&category=";
    public static final String  EXERCISES_IMAGE_API_URL = "https://wger.de/api/v2/exerciseimage/?is_main=true&exercise_base=";
    
    public static final String  EXERCISES_API_KEY = "AIzaSyCXhEsEkCXOEgxwSL-ZpajbWLA_PGEAeMo";

    public static final int FRESH_TIMEOUT = 3600*1000*5; // 5 hour in milliseconds
    public static final String LAST_UPDATE = "lastUpdate";

    public static final String DATABASE_NAME = "exercise_database.db";
    public static final int DATABASE_VERSION = 1;

    public static final String FIREBASE_DB = "";
    public static final String USER = "users";
    public static final String FAVORITE_EXERCISE = "favorite_Exercise";
}
