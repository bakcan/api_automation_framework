package com.bookstore.utils;

import com.bookstore.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Utility class for creating Rest Assured request specifications
 */
public class RestClient {
    
    private static RequestSpecification requestSpec;
    
    /**
     * Get the base request specification with common configurations
     */
    public static RequestSpecification getRequestSpec() {
        if (requestSpec == null) {
            TestConfig config = TestConfig.getInstance();
            
            RequestSpecBuilder builder = new RequestSpecBuilder()
                    .setBaseUri(config.getBaseUrl())
                    .setBasePath(config.getBasePath())
                    .setContentType(ContentType.JSON)
                    .setAccept(ContentType.JSON);
            
            // Add logging if enabled
            if (config.isRequestLoggingEnabled()) {
                builder.log(LogDetail.ALL);
            }
            
            requestSpec = builder.build();
        }
        return requestSpec;
    }
    
    /**
     * Reset the request specification (useful for changing configurations)
     */
    public static void resetRequestSpec() {
        requestSpec = null;
    }
}

