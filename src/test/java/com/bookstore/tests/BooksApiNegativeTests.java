package com.bookstore.tests;

import com.bookstore.base.BaseTest;
import com.bookstore.models.Book;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Negative and edge case test scenarios for Books API endpoints
 * Tests error handling, validation, and boundary conditions
 */
@Feature("Books API - Negative Tests")
public class BooksApiNegativeTests extends BaseTest {

    @Test(priority = 1, 
          description = "01 - GET book with invalid ID - verify 404 is returned",
          groups = {"negative", "get_id"})
    public void test_01_GetBookWithInvalidId_ShouldReturn404() {
        given()
            .spec(requestSpec)
        .when()
            .get("/Books/300")
        .then()
            .statusCode(404);
    }

    @Test(priority = 2,
          description = "02 - PUT update non-existent book - verify 404 is returned",
          groups = {"negative", "put"})
    public void test_02_UpdateNonExistentBook_ShouldReturn404() {
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

    @Test(priority = 3,
          description = "03 - DELETE non-existent book - verify 404 is returned",
          groups = {"negative", "delete"})
    public void test_03_DeleteNonExistentBook_ShouldReturn404() {
        given()
            .spec(requestSpec)
        .when()
            .delete("/Books/300")
        .then()
            .statusCode(404);
    }

    @Test(priority = 4,
          description = "04 - POST book with existing ID - verify error is returned",
          groups = {"negative", "post"})
    public void test_04_CreateBookWithExistingId_ShouldReturnError() {
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
            .statusCode(anyOf(equalTo(400), equalTo(409), equalTo(422)));
    }

    @Test(priority = 5,
          description = "05 - POST book with empty JSON - verify 400 is returned",
          groups = {"negative", "post"})
    public void test_05_CreateBookWithEmptyJson_ShouldReturn400() {
        given()
            .spec(requestSpec)
            .body("{}")
        .when()
            .post("/Books")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 6,
          description = "06 - PUT book with empty JSON - verify 400 is returned",
          groups = {"negative", "put"})
    public void test_06_UpdateBookWithEmptyJson_ShouldReturn400() {
        given()
            .spec(requestSpec)
            .body("{}")
        .when()
            .put("/Books/100")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 7,
          description = "07 - POST book without ID - verify 400 is returned",
          groups = {"negative", "post"})
    public void test_07_CreateBookWithoutId_ShouldReturn400() {
        String bookWithoutId = "{"
            + "\"title\": \"Book Without ID\","
            + "\"description\": \"Testing missing ID field\","
            + "\"pageCount\": 150,"
            + "\"excerpt\": \"Test excerpt\","
            + "\"publishDate\": \"2025-10-18T14:40:07.735Z\""
            + "}";

        given()
            .spec(requestSpec)
            .body(bookWithoutId)
        .when()
            .post("/Books")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 8,
          description = "08 - GET book with invalid ID (non-numeric) - verify 400 or 404 is returned",
          groups = {"negative", "get_id"})
    public void test_08_GetBookWithNonNumericId_ShouldReturnError() {
        given()
            .spec(requestSpec)
        .when()
            .get("/Books/invalid-id")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(404)));
    }

    @Test(priority = 9,
          description = "09 - PUT update book without ID - verify 400 is returned",
          groups = {"negative", "put"})
    public void test_09_UpdateBookWithoutId_ShouldReturn400() {
        String bookWithoutId = "{"
            + "\"title\": \"Book Without ID\","
            + "\"description\": \"Testing missing ID field\","
            + "\"pageCount\": 150,"
            + "\"excerpt\": \"Test excerpt\","
            + "\"publishDate\": \"2025-10-18T14:40:07.735Z\""
            + "}";

        given()
            .spec(requestSpec)
            .body(bookWithoutId)
        .when()
            .put("/Books/100")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 10,
          description = "10 - DELETE book with invalid ID (non-numeric) - verify 400 or 404 is returned",
          groups = {"negative", "delete"})
    public void test_10_DeleteBookWithNonNumericId_ShouldReturnError() {
        given()
            .spec(requestSpec)
        .when()
            .delete("/Books/invalid-id")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(404)));
    }

    @Test(priority = 11,
          description = "11 - POST book without title - verify 400 is returned",
          groups = {"negative", "post"})
    public void test_11_CreateBookWithoutTitle_ShouldReturn400() {
        String bookWithoutTitle = "{"
            + "\"id\": 500,"
            + "\"description\": \"Missing title field\","
            + "\"pageCount\": 100,"
            + "\"excerpt\": \"Test excerpt\","
            + "\"publishDate\": \"2025-10-18T14:40:07.735Z\""
            + "}";

        given()
            .spec(requestSpec)
            .body(bookWithoutTitle)
        .when()
            .post("/Books")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 12,
          description = "12 - POST book with invalid ID (non-numeric) - verify 400 is returned",
          groups = {"negative", "post"})
    public void test_12_CreateBookWithNonNumericId_ShouldReturn400() {
        String bookWithInvalidId = "{"
            + "\"id\": \"invalid-id\","
            + "\"title\": \"Test Book\","
            + "\"description\": \"Testing non-numeric ID\","
            + "\"pageCount\": 100,"
            + "\"excerpt\": \"Test excerpt\","
            + "\"publishDate\": \"2025-10-18T14:40:07.735Z\""
            + "}";

        given()
            .spec(requestSpec)
            .body(bookWithInvalidId)
        .when()
            .post("/Books")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 13,
          description = "13 - PUT book with invalid ID (non-numeric) - verify 400 is returned",
          groups = {"negative", "put"})
    public void test_13_UpdateBookWithNonNumericId_ShouldReturn400() {
        String bookWithInvalidId = "{"
            + "\"id\": \"invalid-id\","
            + "\"title\": \"Updated Book\","
            + "\"description\": \"Testing non-numeric ID\","
            + "\"pageCount\": 200,"
            + "\"excerpt\": \"Test excerpt\","
            + "\"publishDate\": \"2025-10-18T14:40:07.735Z\""
            + "}";

        given()
            .spec(requestSpec)
            .body(bookWithInvalidId)
        .when()
            .put("/Books/100")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 14,
          description = "14 - POST book with invalid pageCount (non-numeric) - verify 400 is returned",
          groups = {"negative", "post"})
    public void test_14_CreateBookWithInvalidPageCount_ShouldReturn400() {
        String bookWithInvalidPageCount = "{"
            + "\"id\": 501,"
            + "\"title\": \"Test Book\","
            + "\"description\": \"Testing invalid pageCount\","
            + "\"pageCount\": \"not-a-number\","
            + "\"excerpt\": \"Test excerpt\","
            + "\"publishDate\": \"2025-10-18T14:40:07.735Z\""
            + "}";

        given()
            .spec(requestSpec)
            .body(bookWithInvalidPageCount)
        .when()
            .post("/Books")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 15,
          description = "15 - PUT book with invalid pageCount (non-numeric) - verify 400 is returned",
          groups = {"negative", "put"})
    public void test_15_UpdateBookWithInvalidPageCount_ShouldReturn400() {
        String bookWithInvalidPageCount = "{"
            + "\"id\": 100,"
            + "\"title\": \"Updated Book\","
            + "\"description\": \"Testing invalid pageCount\","
            + "\"pageCount\": \"not-a-number\","
            + "\"excerpt\": \"Test excerpt\","
            + "\"publishDate\": \"2025-10-18T14:40:07.735Z\""
            + "}";

        given()
            .spec(requestSpec)
            .body(bookWithInvalidPageCount)
        .when()
            .put("/Books/100")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 16,
          description = "16 - POST book with negative pageCount - verify 400 is returned",
          groups = {"negative", "post"})
    public void test_16_CreateBookWithNegativePageCount_ShouldReturn400() {
        Book bookWithNegativePages = Book.builder()
            .id(100)
            .title("Book with Negative Pages")
            .description("Testing negative pageCount value")
            .pageCount(-1)
            .excerpt("Test excerpt")
            .publishDate("2025-10-18T14:40:07.735Z")
            .build();

        given()
            .spec(requestSpec)
            .body(bookWithNegativePages)
        .when()
            .post("/Books")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 17,
          description = "17 - PUT book with negative pageCount - verify 400 is returned",
          groups = {"negative", "put"})
    public void test_17_UpdateBookWithNegativePageCount_ShouldReturn400() {
        Book bookWithNegativePages = Book.builder()
            .id(100)
            .title("Book with Negative Pages")
            .description("Testing negative pageCount value")
            .pageCount(-1)
            .excerpt("Test excerpt")
            .publishDate("2025-10-18T14:40:07.735Z")
            .build();

        given()
            .spec(requestSpec)
            .body(bookWithNegativePages)
        .when()
            .put("/Books/100")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 18,
          description = "18 - GET book with negative ID - verify 400 or 404 is returned",
          groups = {"negative", "get_id"})
    public void test_18_GetBookWithNegativeId_ShouldReturnError() {
        given()
            .spec(requestSpec)
        .when()
            .get("/Books/-1")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(404)));
    }

    @Test(priority = 19,
          description = "19 - DELETE book with negative ID - verify 400 or 404 is returned",
          groups = {"negative", "delete"})
    public void test_19_DeleteBookWithNegativeId_ShouldReturnError() {
        given()
            .spec(requestSpec)
        .when()
            .delete("/Books/-1")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(404)));
    }

    @Test(priority = 20,
        description = "20 - POST book with invalid date format - verify 400 is returned",
        groups = {"negative", "post"})
    public void test_20_CreateBookWithInvalidDateFormat_ShouldReturn400() {
        String bookWithInvalidDate = "{"
            + "\"id\": 502,"
            + "\"title\": \"Test Book\","
            + "\"description\": \"Testing invalid date\","
            + "\"pageCount\": 100,"
            + "\"excerpt\": \"Test excerpt\","
            + "\"publishDate\": \"invalid-date-format\""
            + "}";

        given()
            .spec(requestSpec)
            .body(bookWithInvalidDate)
        .when()
            .post("/Books")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 21,
        description = "21 - POST book with null title - verify 400 is returned",
        groups = {"negative", "post"})
    public void test_21_CreateBookWithNullTitle_ShouldReturn400() {
        Book bookWithNullTitle = Book.builder()
            .id(503)
            .title(null)
            .description("Testing null title")
            .pageCount(100)
            .excerpt("Test excerpt")
            .publishDate("2025-10-18T14:40:07.735Z")
            .build();

        given()
            .spec(requestSpec)
            .body(bookWithNullTitle)
        .when()
            .post("/Books")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 22,
        description = "22 - POST book with malformed JSON - verify 400 is returned",
        groups = {"negative", "post"})
    public void test_22_CreateBookWithMalformedJson_ShouldReturn400() {
        String malformedJson = "{"
            + "\"id\": 504,"
            + "\"title\": \"Test Book\","
            + "\"description\": \"Malformed JSON\","
            + "\"pageCount\": 100,"
            + "\"excerpt\": \"Test\""  // Missing closing brace and comma
            + "\"publishDate\": \"2025-10-18T14:40:07.735Z\"";

        given()
            .spec(requestSpec)
            .body(malformedJson)
        .when()
            .post("/Books")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422), equalTo(500)));
    }

    @Test(priority = 23,
        description = "23 - PUT book with invalid date format - verify 400 is returned",
        groups = {"negative", "put"})
    public void test_23_UpdateBookWithInvalidDateFormat_ShouldReturn400() {
        String bookWithInvalidDate = "{"
            + "\"id\": 100,"
            + "\"title\": \"Test Book\","
            + "\"description\": \"Testing invalid date\","
            + "\"pageCount\": 100,"
            + "\"excerpt\": \"Test excerpt\","
            + "\"publishDate\": \"invalid-date-format\""
            + "}";

        given()
            .spec(requestSpec)
            .body(bookWithInvalidDate)
        .when()
            .put("/Books/100")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422)));
    }

    @Test(priority = 24,
        description = "24 - PUT book with malformed JSON - verify 400 is returned",
        groups = {"negative", "put"})
    public void test_24_UpdateBookWithMalformedJson_ShouldReturn400() {
        String malformedJson = "{"
            + "\"id\": 100,"
            + "\"title\": \"Updated Book\""  // Missing comma
            + "\"description\": \"Malformed JSON\","
            + "\"pageCount\": 100,"
            + "\"excerpt\": \"Test\","
            + "\"publishDate\": \"2025-10-18T14:40:07.735Z\""
            + "}";

        given()
            .spec(requestSpec)
            .body(malformedJson)
        .when()
            .put("/Books/100")
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(422), equalTo(500)));
    }

    @Test(priority = 25,
        description = "25 - PUT book with ID mismatch between URL and body - verify 400 is returned",
        groups = {"negative", "put"})
    public void test_25_UpdateBookWithIdMismatch_ShouldReturn400() {
        Book bookWithDifferentId = Book.builder()
            .id(999)  // Body has ID 999
            .title("Mismatched ID Book")
            .description("ID in body doesn't match URL")
            .pageCount(150)
            .excerpt("Test excerpt")
            .publishDate("2025-10-18T14:40:07.735Z")
            .build();

        given()
            .spec(requestSpec)
            .body(bookWithDifferentId)
        .when()
            .put("/Books/100")  // URL has ID 100
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(409), equalTo(422)));
    }


}
