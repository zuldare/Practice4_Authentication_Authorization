package es.codeurjc.books.config;

public class Constants {

    // Spring Security
    public static final String LOGIN_URL = "/login";
    public static final String SIGNUP_URL = "/api/v1/users/";
    public static final String BOOKS_URL = "/api/v1/books/";

    // SWAGGER AND API DOCUMENTATION
    public static final String API_DOCS_URL = "/api-docs/**";
    public static final String API_DOCS_YAML = "/api-docs.yaml";
    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    public static final String SWAGGER_UI_URL = "/swagger-ui/**";


    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // JWT

    public static final String ISSUER_INFO = "PRACTISE 4";
    public static final String SUPER_SECRET_KEY = "1234";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day
}
