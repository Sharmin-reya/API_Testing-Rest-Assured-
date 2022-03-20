import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Customer {
    Properties props=new Properties();
    FileInputStream file=new FileInputStream("./src/test/resources/config.properties");

    public Customer() throws FileNotFoundException {
    }
    public void callingCustomerLoginApi() throws ConfigurationException, IOException {


        props.load(file);
        RestAssured.baseURI=props.getProperty("baseUrl");
        Response response=given().contentType("application/json")
                .body("{\n" +
                        "    \"username\":\"salman\",\n" +
                        "    \"password\":\"salman1234\"\n" +
                        "}")
        .when()
                .post("/customer/api/v1/login")
        .then()
                .assertThat().statusCode(200).extract().response();

        JsonPath resObj=response.jsonPath();
        String token=resObj.get("token");
        Utils.setEnvVariable("token",token);
        System.out.println(token);


    }
    public void callingCustomerListApi() throws IOException {

        props.load(file);
        RestAssured.baseURI=props.getProperty("baseUrl");
        Response response=given()
                .contentType("application/json")
                .header("Authorization",props.getProperty("token"))
        .when()
                .get("/customer/api/v1/list")
        .then().assertThat().statusCode(200).extract().response();

        //for showing raw data
        //System.out.println(response.toString());
        JsonPath resObj=response.jsonPath();
        String id=resObj.get("Customers[0].id").toString();
        Assert.assertEquals("101",id);

        //System.out.println(id);

    }
    public void callingCustomerSearchApi() throws IOException {
        props.load(file);
        RestAssured.baseURI=props.getProperty("baseUrl");
        Response response=given()
                .contentType("application/json")
                .header("Authorization",props.getProperty("token"))
        .when()
                .get("/customer/api/v1/get/101")
        .then()
                .assertThat().statusCode(200).extract().response();

        JsonPath resObj=response.jsonPath();
        String name=resObj.get("name");
        Assert.assertEquals("Mr. Kamal",name);
        System.out.println(response.asString());


    }
    public String ID;
    public String name;
    public String email;
    public String address;
    public String phone_number;
    public void addCustomer() throws IOException, ConfigurationException {
        props.load(file);
        RestAssured.baseURI="https://randomuser.me/";
        Response response=given()
                .contentType("application/json")
                .when()
                .get("/api")
                .then()
                .assertThat().statusCode(200).extract().response();

        JsonPath resObj=response.jsonPath();
        ID= String.valueOf((int)Math.floor(Math.random()*(999999-100000)+1));
        name= resObj.get("results[0].name.first");
        email= resObj.get("results[0].email");
        address= resObj.get("results[0].location.state");
        phone_number= resObj.get("results[0].cell");
        Utils.setEnvVariable("id",ID.toString());
        Utils.setEnvVariable("name",name);
        Utils.setEnvVariable("email",email);
        Utils.setEnvVariable("address",address);
        Utils.setEnvVariable("phone_number",phone_number);
        System.out.println(response.asString());


    }
    public void createCustomer() throws IOException, ConfigurationException {
        props.load(file);
        RestAssured.baseURI=props.getProperty("baseUrl");
        Response response=given().contentType("application/json")
                .header("Authorization",props.getProperty("token"))
                .body("" +
                        " {  \"id\":"+props.getProperty("id")+",\n"+
                        "    \"name\":\""+props.getProperty("name")+"\",\n"+
                        "    \"email\":\""+props.getProperty("email")+ "\",\n" +
                        "    \"address\":\""+props.getProperty("address")+"\",\n" +
                        "    \"phone_number\":\""+props.getProperty("phone_number")+"\"}")
                .post("/customer/api/v1/create")
                .then()
                .assertThat().statusCode(201).extract().response();

        System.out.println(response.asString());


    }
    public void customerUpdate() throws IOException {
        props.load(file);
        RestAssured.baseURI=props.getProperty("baseUrl");
        Response response=given().contentType("application/json")
                .header("Authorization",props.getProperty("token"))
                .body("{\n" +
                        "    \"id\": 102,\n" +
                        "    \"name\": \"Mr. Jamal Islam\",\n" +
                        "    \"email\": \"mrjamal@test.com\",\n" +
                        "    \"address\": \"Mirpur,Dhaka\",\n" +
                        "    \"phone_number\": \"01502020110\"\n" +
                        "}")
                .put("/customer/api/v1/update/102")
                .then()
                .assertThat().statusCode(200).extract().response();
        System.out.println(response.asString());

    }


    }


