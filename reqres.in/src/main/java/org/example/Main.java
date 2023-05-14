package org.example;
import io.restassured.RestAssured ;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.codehaus.groovy.tools.shell.util.Logger.io;
public class Main {
    static String UserName="eve.holt@reqres.in";
    static String Password="cityslicka";
    static String BaseLine="https://reqres.in/api";
    static String SiingleUserEndPoint="/users";
    static String loginEndPoint="/login";
    public static Response loginRequest(String userName ,String password)
    {
        Response login = RestAssured.given().log().all().contentType("application/json").
                body("{\n" +
                        "    \"email\": \""+userName+"\",\n" +
                        "    \"password\": \""+password+"\"\n" +
                        "}").post(BaseLine+loginEndPoint);
        System.out.println(login.statusCode());
        login.prettyPrint();
       return login ;
    }
    public static void main(String[] args) {
        Response singleuser =  RestAssured.given().get(BaseLine+SiingleUserEndPoint+"/2");
        System.out.println(singleuser.statusCode());
        singleuser.prettyPrint();


        JsonPath loginpath= loginRequest( UserName ,Password).jsonPath();
        String token =loginpath.getString("token");
        System.out.println(token);
    }
}