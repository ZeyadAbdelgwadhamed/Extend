package Test_Class;

import POJO_Classes.Support;
import POJO_Classes.User;
import POJO_Classes.UserData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UsersApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void testGetAllUsers() {
        Response response =
                given()
                        .log().all()
                        .when()
                        .get("/users?page=1")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();

        UserData userData = response.as(UserData.class);

        // Assertions to verify the deserialized data
        Assert.assertEquals(userData.getPage(), 1);
        Assert.assertEquals(userData.getPer_page(), 6);
        Assert.assertEquals(userData.getTotal(), 12);
        Assert.assertEquals(userData.getTotal_pages(), 2);
        Assert.assertNotNull(userData.getData());
       // Assert.assertEquals(userData.getData().size(), 6);

        // Verify the first user
        User firstUser = userData.getData().get(0);
        Assert.assertEquals(firstUser.getId(), 1);
        Assert.assertEquals(firstUser.getEmail(), "george.bluth@reqres.in");
        Assert.assertEquals(firstUser.getFirst_name(), "George");
        Assert.assertEquals(firstUser.getLast_name(), "Bluth");
        Assert.assertEquals(firstUser.getAvatar(), "https://reqres.in/img/faces/1-image.jpg");


        // Verify the support object
        Support support = userData.getSupport();
        Assert.assertEquals(support.getUrl(), "https://reqres.in/#support-heading");
        Assert.assertEquals(support.getText(), "To keep ReqRes free, contributions towards server costs are appreciated!");
    }
}

