package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.User;
import io.restassured.response.Response;

public class AuthEndPoints extends Routes {
	public Response register(User user) {
        return given()
                .spec(reqSpec)
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    public Response adminLogin(String email, String password) {
        return given()
                .spec(reqSpec)
                .body("{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }")
                .when()
                .post("/api/auth/login");
    }

    public Response userLogin(String email, String password) {
        return adminLogin(email, password);
    }

}
