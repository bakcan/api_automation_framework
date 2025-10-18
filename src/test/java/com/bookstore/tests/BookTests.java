package com.bookstore.tests;

import com.bookstore.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

/**
 * Test class for Books API endpoints
 */
@Feature("Books API")
public class BookTests extends BaseTest {

    @Test(description = "GET all books - verify status 200 and validates the JSON schema")
    public void testGetAllBooks_Success() {
        given()
            .spec(requestSpec)
        .when()
            .get("/Books")
        .then()
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("$", not(empty()))
            .body("size()", greaterThan(0))
            .body(matchesJsonSchemaInClasspath("schemas/books-array-schema.json"));
    }
}

