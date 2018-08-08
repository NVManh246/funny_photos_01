package com.example.ginz.funnyphoto.configuration;

public final class Constants {

    private Constants() {}

    public class Server {
        public static final String BASE_URL_API = "http://167.99.74.194:3000/";
        public static final String RESOURCE_USER = BASE_URL_API + "users/";
        public static final String RESOURCE_PHOTO = BASE_URL_API + "photos?page=";
        public static final String ACTION_UPDATE_PROFILE = BASE_URL_API + "users/update";
        public static final String ACTION_LOGIN = RESOURCE_USER + "login/";
        public static final String ACTION_REGISTER = RESOURCE_USER + "register/";
    }

    public class Authentication {
        public static final String MESSAGE_OK = "ok";
        public static final String MESSAGE_ERROR = "error";
        public static final String KEY_MESSAGE = "message";
        public static final String KEY_DESCRIPTION = "description";
        public static final String DATA = "data";
        public static final String DOCS = "docs";
        public static final int ERROR_SERVER = 1;
        public static final int ERROR_USER = 1;
    }

    public class Method {
        public static final String METHOD_POST = "POST";
        public static final String METHOD_GET = "GET";
        public static final String METHOD_PUT = "PUT";
    }
}
