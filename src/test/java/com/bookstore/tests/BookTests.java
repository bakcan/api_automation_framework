package com.bookstore.tests;

import com.bookstore.base.BaseTest;
import com.bookstore.models.Book;
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

    @Test(priority = 1, description = "01 - GET all books - verify status 200 and validates the JSON schema")
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

    @Test(priority = 2, description = "02 - GET book by ID - verify the book is retrieved successfully and validates the JSON schema")
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

    @Test(priority = 3, description = "03 - POST new book - verify the book is created successfully")
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

    @Test(priority = 4, description = "04 - GET the created book by ID - verify the created book is persisted in the database")
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

    @Test(priority = 5, description = "05 - PUT update book - verify the book is updated successfully")
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

    @Test(priority = 6, description = "06 - GET the updated book by ID - verify the updated book is persisted in the database")
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

    @Test(priority = 7, description = "07 - DELETE book - verify status 200")
    public void test_07_DeleteBook_ShouldReturnStatus200() {
        given()
            .spec(requestSpec)
        .when()
            .delete("/Books/200")
        .then()
            .statusCode(200);
    }

    @Test(priority = 8, description = "08 - GET the deleted book by ID - verify delete operation is persisted in the database")
    public void test_08_VerifyBookDeleted() {
        given()
            .spec(requestSpec)
        .when()
            .get("/Books/200")
        .then()
            .statusCode(404);
    }

    @Test(priority = 9, description = "09 - PUT update non-existent book - verify 404 is returned")
    public void test_09_UpdateNonExistentBook_ShouldReturn404() {
        Book updatedBook = Book.builder()
            .id(300)
            .title("Non-Existent Book")
            .description("This book does not exist")
            .pageCount(150)
            .excerpt("Test Excerpt")
            .publishDate("2025-10-18T14:30:07.735Z")
            .build();

        given()
            .spec(requestSpec)
            .body(updatedBook)
        .when()
            .put("/Books/300")
        .then()
            .statusCode(404);
    }

    @Test(priority = 10, description = "DELETE non-existent book - verify 404 is returned")
    public void test_10_DeleteNonExistentBook_ShouldReturn404() {
        given()
            .spec(requestSpec)
        .when()
            .delete("/Books/300")
        .then()
            .statusCode(404);
    }

    @Test(priority = 11, description = "POST book with existing ID - verify 409 conflict or 400 bad request is returned")
    public void test_11_CreateBookWithExistingId_ShouldReturnError() {
        Book duplicateBook = Book.builder()
            .id(1)
            .title("Duplicate ID Book")
            .description("Attempting to create with existing ID")
            .pageCount(250)
            .excerpt("This should fail")
            .publishDate("2025-10-18T14:35:07.735Z")
            .build();

        given()
            .spec(requestSpec)
            .body(duplicateBook)
        .when()
            .post("/Books")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(409), equalTo(422))); // Could be Bad Request or Conflict or Unprocessable Entity
    }

}

