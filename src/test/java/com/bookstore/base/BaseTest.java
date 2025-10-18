package com.bookstore.base;

import com.bookstore.config.TestConfig;
import com.bookstore.utils.RestClient;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * Base test class that all test classes should extend
 * Provides common setup and utilities
 */
public class BaseTest {
    
    protected TestConfig config;
    protected RequestSpecification requestSpec;
    
    @BeforeClass
    public void setupClass() {
        config = TestConfig.getInstance();
        
        // Set base URI and path globally
        RestAssured.baseURI = config.getBaseUrl();
        RestAssured.basePath = config.getBasePath();
        
        // Add Allure filter for better reporting
        RestAssured.filters(
                new AllureRestAssured(),
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
        
        System.out.println("=== Test Configuration ===");
        System.out.println("Base URL: " + config.getBaseUrl());
        System.out.println("Base Path: " + config.getBasePath());
        System.out.println("==========================");
    }
    
    @BeforeMethod
    public void setupMethod() {
        // Get fresh request specification for each test
        requestSpec = RestClient.getRequestSpec();
    }
}

