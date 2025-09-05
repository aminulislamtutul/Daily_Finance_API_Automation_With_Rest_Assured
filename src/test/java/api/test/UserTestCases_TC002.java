package api.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.Routes;
import api.endpoints.UserEndPoints;
import io.restassured.response.Response;

public class UserTestCases_TC002 extends Routes {
	UserEndPoints userController = new UserEndPoints();
	    Properties props = new Properties();

	    @Test(priority = 1, description = "Get User List")
	    public void getUserList() throws IOException {
	        loadProps();
	        String token = props.getProperty("adminToken");

	        Response res = userController.getUserList(token);
	        Assert.assertEquals(res.statusCode(), 200);
	    }

	    @Test(priority = 2, description = "Search by Valid User Id")
	    public void searchUserById() throws IOException {
	        loadProps();
	        String token = props.getProperty("adminToken");
	        String userId = props.getProperty("userId");

	        Response res = userController.getUserById(userId, token);
	        Assert.assertEquals(res.statusCode(), 200);
	    }
	    @Test(priority = 3, description = "Search by Invalid User Id")
	    public void searchUserByInvalidId() throws IOException {
	        loadProps();
	        String token = props.getProperty("adminToken");
	        String userId = props.getProperty("invalid_id");

	        Response res = userController.getUserById(userId, token);
	        Assert.assertEquals(res.statusCode(), 404);
	        String message = res.jsonPath().getString("message");
	        Assert.assertTrue(message.contains("User not found"), "Unexpected error message");
	    }
	    @Test(priority = 4, description = "Edit User Info")
	    public void editUserInfo() throws IOException {
	        loadProps();
	        String token = props.getProperty("adminToken");
	        String userId = props.getProperty("userId");
	        Faker faker = new Faker();

	        String updateBody = "{\n" +
	                "  \"firstName\": \"" + faker.name().firstName() + "\",\n" +
	                "  \"lastName\": \"" + faker.name().lastName() + "\",\n" +
	                "  \"phoneNumber\": \"" + faker.phoneNumber().subscriberNumber(11) + "\",\n" +
	                "  \"address\": \"" + faker.address().fullAddress() + "\"\n" +
	                "}";
	        Response res = userController.updateUser(userId, token, updateBody);
	        Assert.assertEquals(res.statusCode(), 200);
	    }
	    @Test(priority = 5, description = "Search User Info After Edit")
	    public void searchUserAfterUpdateById() throws IOException {
	        loadProps();
	        String token = props.getProperty("adminToken");
	        String userId = props.getProperty("userId");

	        Response res = userController.getUserById(userId, token);
	        Assert.assertEquals(res.statusCode(), 200);
	    }

	    private void loadProps() throws IOException {
	        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
	        props.load(fis);
	        fis.close();
	    }

}
