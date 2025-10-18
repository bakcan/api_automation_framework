package com.bookstore.tests;

import com.bookstore.base.BaseTest;
import com.bookstore.models.Book;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

/**
 * Positive test cases for Books API endpoints
 * Tests all happy path scenarios for CRUD operations
 */
@Feature("Books API - Positive Tests")
public class BooksApiPositiveTests extends BaseTest {

    @Test(priority = 1, 
          description = "01 - GET all books - verify status 200 and validates the JSON schema",
          groups = {"smoke", "positive", "get"})
    public void test_01_GetAllBooks_ShouldReturnAllBooks() {
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

    @Test(priority = 2, 
          description = "02 - GET book by ID - verify the book is retrieved successfully and validates the JSON schema",
          groups = {"smoke", "positive", "get_id"})
    public void test_02_GetBookById_ShouldReturnBook() {
        given()
            .spec(requestSpec)
        .when()
            .get("/Books/1")
        .then()
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("id", equalTo(1))
            .body("title", notNullValue())
            .body("description", notNullValue())
            .body("pageCount", notNullValue())
            .body("excerpt", notNullValue())
            .body("publishDate", notNullValue())
            .body(matchesJsonSchemaInClasspath("schemas/book-schema.json"));
    }

    @Test(priority = 3, 
          description = "03 - POST new book - verify the book is created successfully",
          groups = {"positive", "post"})
    public void test_03_CreateBook_ShouldCreateBook() {
        Book newBook = Book.builder()
            .id(201)
            .title("Test Book")
            .description("Test Description")
            .pageCount(100)
            .excerpt("Test Excerpt")
            .publishDate("2025-10-18T14:22:07.735Z")
            .build();
        
        given()
            .spec(requestSpec)
            .body(newBook)
        .when()
            .post("/Books")
        .then()
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("id", equalTo(201))
            .body("title", equalTo("Test Book"))
            .body("description", equalTo("Test Description"))
            .body("pageCount", equalTo(100))
            .body("excerpt", equalTo("Test Excerpt"))
            .body("publishDate", equalTo("2025-10-18T14:22:07.735Z"))
            .body(matchesJsonSchemaInClasspath("schemas/book-schema.json"));
    }

    @Test(priority = 4, 
          description = "04 - GET the created book by ID - verify the created book is persisted in the database",
          groups = {"positive", "get_id"})
    public void test_04_GetCreatedBookById_ShouldReturnCreatedBook() {
        given()
            .spec(requestSpec)
        .when()
            .get("/Books/201")
        .then()
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("id", equalTo(201))
            .body("title", equalTo("Test Book"))
            .body("description", equalTo("Test Description"))
            .body("pageCount", equalTo(100))
            .body("excerpt", equalTo("Test Excerpt"))
            .body("publishDate", equalTo("2025-10-18T14:22:07.735Z"))
            .body(matchesJsonSchemaInClasspath("schemas/book-schema.json"));
    }

    @Test(priority = 5, 
          description = "05 - PUT update book - verify the book is updated successfully",
          groups = {"positive", "put"})
    public void test_05_UpdateBook_ShouldUpdateBook() {
        Book updatedBook = Book.builder()
            .id(200)
            .title("Updated Book")
            .description("Updated Description")
            .pageCount(200)
            .excerpt("Updated Excerpt")
            .publishDate("2025-10-18T14:25:07.735Z")
            .build();
        
        given()
            .spec(requestSpec)
            .body(updatedBook)
        .when()
            .put("/Books/200")
        .then()
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("id", equalTo(200))
            .body("title", equalTo("Updated Book"))
            .body("description", equalTo("Updated Description"))
            .body("pageCount", equalTo(200))
            .body("excerpt", equalTo("Updated Excerpt"))
            .body("publishDate", equalTo("2025-10-18T14:25:07.735Z"))
            .body(matchesJsonSchemaInClasspath("schemas/book-schema.json"));
    }

    @Test(priority = 6, 
          description = "06 - GET the updated book by ID - verify the updated book is persisted in the database",
          groups = {"positive", "get_id"})
    public void test_06_GetUpdatedBookById_ShouldReturnUpdatedBook() {
        given()
            .spec(requestSpec)
        .when()
            .get("/Books/200")
        .then()
            .statusCode(200)
            .contentType(containsString("application/json"))
            .body("id", equalTo(200))
            .body("title", equalTo("Updated Book"))
            .body("description", equalTo("Updated Description"))
            .body("pageCount", equalTo(200))
            .body("excerpt", equalTo("Updated Excerpt"))
            .body("publishDate", equalTo("2025-10-18T14:25:07.735Z"))
            .body(matchesJsonSchemaInClasspath("schemas/book-schema.json"));
    }

    @Test(priority = 7, 
          description = "07 - DELETE book - verify status 200",
          groups = {"positive", "delete"})
    public void test_07_DeleteBook_ShouldReturnStatus200() {
        given()
            .spec(requestSpec)
        .when()
            .delete("/Books/200")
        .then()
            .statusCode(200);
    }

    @Test(priority = 8, 
          description = "08 - GET the deleted book by ID - verify delete operation is persisted in the database",
          groups = {"positive", "get_id"})
    public void test_08_VerifyBookDeleted() {
        given()
            .spec(requestSpec)
        .when()
            .get("/Books/200")
        .then()
            .statusCode(404);
    }

}
