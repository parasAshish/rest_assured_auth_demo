package com.example.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestAssuredAuthDemoApplicationTests {

	// Primitive Basic Authentication
	// This will send the basic authentication credential even before the server gives an unauthorized response in certain situations,
	// thus reducing the overhead of making an additional connection.
	@Test
	public void test_primitive_basic_auth() {
		given().auth().preemptive().basic("admin", "password").when().get("/v2/employees").then().statusCode(200);
	}

	// Challenged Basic Authentication
	// When using "challenged basic authentication" REST Assured will not supply the credentials unless the server has explicitly asked for it.
	// This means that REST Assured will make an additional request to the server in order to be challenged and
	// then follow up with the same request once more but this time setting the basic credentials in the header.
	@Test
	public void test_challenged_basic_auth() {
		given().auth().basic("admin", "password").when().get("/v2/employees").then().statusCode(200);
	}

	// Challenged Digest Authentication
	@Test
	public void test_digest_auth() {
		given().auth().digest("admin", "password").when().get("/v2/employees").then().statusCode(200);
	}

}
