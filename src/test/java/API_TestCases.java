
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.core.StringContains.containsString;

public class API_TestCases {

    private static final String BASE_URI = "https://automationexercise.com/api";


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void test1_Get_All_Products_List() {
        given()
                .when().get("productsList")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void test2_POST_To_All_Products_List() {

        JSONObject productData = new JSONObject();
        productData.put("name", "Sample Product");
        productData.put("price", 100);
        productData.put("category", "electronics");

        given()
                .body(productData.toString())
                .when()
                .post("productsList")
                .then()
                .body(containsString("This request method is not supported"));
    }

    @Test
    public void test3_Get_All_Brand_List() {
        given()
                .when().get("brandsList")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void test4_PUT_To_All_Brands_List() {
        JSONObject productData = new JSONObject();
        productData.put("name", "Sample Product");
        productData.put("price", 100);
        productData.put("category", "electronics");

        given()
                .body(productData.toString())
                .when()
                .put("brandsList")
                .then()
                .body(containsString("This request method is not supported"));
    }

    @Test
    public void test5_POST_To_Search_Product() {
        String searchTerm = "top";
        JSONObject searchData = new JSONObject();
        searchData.put("search_product", searchTerm);

        given()
                .contentType(ContentType.JSON)
                .body(searchData.toString())
                .when()
                .post("searchProduct")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void test6_POST_To_Search_Product_without_search_product_parameter() {
        Response response =
                RestAssured.given()
                        .contentType(ContentType.JSON)
                        .post("/searchProduct");

        JsonPath jsonPath = response.jsonPath();
        int responseCode = jsonPath.getInt("responseCode");
        Assert.assertEquals(responseCode, 400, "Expected response code in the body is 400");
    }

    @Test
    public void test7_POST_To_Verify_Login_with_valid_details() {

        RestAssured.baseURI = "https://automationexercise.com";
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("email", "test@test.com");
        requestParams.put("password", "test12345");

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestParams)
                .when()
                .post("/api/verifyLogin")
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void test8_POST_To_Verify_Login_without_email_parameter() {
        RestAssured.baseURI = "https://automationexercise.com";

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("password", "test12345");

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestParams)
                .when()
                .post("/api/verifyLogin")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        String responseMessage = response.getBody().asString();
        Assert.assertTrue(responseMessage.contains("Bad request, email or password parameter is missing in POST request."));
    }

    @Test
    public void test9_DELETE_To_Verify_Login() {
        RestAssured.baseURI = "https://automationexercise.com";
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/verifyLogin")
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        String responseMessage = response.getBody().asString();
        Assert.assertTrue(responseMessage.contains("This request method is not supported."));
    }

    @Test
    public void test10_POST_To_Verify_Login_with_invalid_details() {

        RestAssured.baseURI = "https://automationexercise.com";
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("email", "t@test.com");
        requestParams.put("password", "tt2025");

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestParams)
                .when()
                .post("/api/verifyLogin")
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        String responseMessage = response.getBody().asString();
        Assert.assertFalse(responseMessage.contains("User not found!"));
    }

    @Test
    public void test11_POST_To_Create_Register_User_Account() {
        RestAssured.baseURI = "https://automationexercise.com";

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("name", "John Doe");
        requestParams.put("email", "johndoe@example.com");
        requestParams.put("password", "password123");
        requestParams.put("title", "Mr");
        requestParams.put("birth_date", "10");
        requestParams.put("birth_month", "5");
        requestParams.put("birth_year", "1990");
        requestParams.put("firstname", "John");
        requestParams.put("lastname", "Doe");
        requestParams.put("company", "ACME Corp");
        requestParams.put("address1", "123 Main St");
        requestParams.put("address2", "Apt. 5");
        requestParams.put("country", "US");
        requestParams.put("zipcode", "12345");
        requestParams.put("state", "California");
        requestParams.put("city", "Los Angeles");
        requestParams.put("mobile_number", "1234567890");

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestParams)
                .when()
                .post("/api/createAccount")
                .then()
                .extract().response();
        int statusCode = response.getStatusCode();
        assert statusCode == 200;
    }

    @Test
    public void test12_DELETE_METHOD_To_Delete_User_Account() {

        RestAssured.baseURI = "https://automationexercise.com";
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("email", "test@test.com");
        requestParams.put("password", "test12345");

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestParams)
                .when()
                .delete("/api/deleteAccount")
                .then()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
//        String responseMessage = response.getBody().asString();
//        Assert.assertEquals(responseMessage, "Account deleted!");

    }

    @Test
    public void test13_PUT_METHOD_To_Update_User_Account() {
        RestAssured.baseURI = "https://automationexercise.com";

        String existingEmail = "johndoe@example.com";
        String password = "password123";

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("name", "John Doe (Updated)");
        requestParams.put("email", existingEmail);
        requestParams.put("password", password);
        requestParams.put("title", "Mr");
        requestParams.put("birth_date", "10");
        requestParams.put("birth_month", "5");
        requestParams.put("birth_year", "1990");
        requestParams.put("firstname", "John");
        requestParams.put("lastname", "Doe");
        requestParams.put("company", "ACME Corp (Updated)");
        requestParams.put("address1", "123 Main St");
        requestParams.put("address2", "Apt. 5");
        requestParams.put("country", "US");
        requestParams.put("zipcode", "12345");
        requestParams.put("state", "California");
        requestParams.put("city", "Los Angeles");
        requestParams.put("mobile_number", "1234567890");

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestParams)
                .when()
                .put("/api/updateAccount/" + existingEmail)
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        assert statusCode == 404;

    }

    @Test
    public void test14_GET_user_account_detail_by_email() {
        String email = "registered_user@example.com";

        given()
                .baseUri("https://automationexercise.com/api")
                .when()
                .get("/getUserDetailByEmail?email=" + email)
                .then()
                .statusCode(200);
    }
}
