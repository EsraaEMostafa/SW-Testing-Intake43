package org.example.Requests;

import helper.PropertiesLoader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import helper.Helper;
import org.example.RequestsModels.CreateRequest;
import org.example.RequestsModels.LoginRequest;
import org.example.ResponseModels.LoginResponse;

import java.util.Map;
import helper.EndPoint ;

public class Requests {

//    public static Response loginRequest(String userName , String password)
//    {
//        Response login = RestAssured.given().log().all().contentType("application/json").
//                body("{\n" +
//                        "    \"email\": \""+userName+"\",\n" +
//                        "    \"password\": \""+password+"\"\n" +
//                        "}").post(BaseLine+loginEndPoint);
//        System.out.println(login.statusCode());
//        login.prettyPrint();
//        return login ;
//    }

    //function using POJO
    public static LoginResponse loginRequest(String userName , String password)
    {
        LoginResponse login = RestAssured.given().log().all().contentType("application/json").
                body("{\n" +
                        "    \"email\": \""+userName+"\",\n" +
                        "    \"password\": \""+password+"\"\n" +
                        "}").
                post(PropertiesLoader.readProperty("BaseLine")+EndPoint.loginEndPoint).as(LoginResponse.class);
        return login ;
    }
    /*public static Response loginRequest2(String userName , String password)
    {
        Response login = RestAssured.given().log().all().contentType("application/json").
                body("{\n" +
                        "    \"email\": \""+userName+"\",\n" +
                        "    \"password\": \""+password+"\"\n" +
                        "}").post(BaseLine+userEndPoint);
        System.out.println(login.statusCode());
        login.prettyPrint();
        return login ;
    }*/
    public static Response loginRequest2(String userName , String password)
    {
        LoginRequest loginRequest =new LoginRequest();
        loginRequest.email =userName ;
        loginRequest.password=password ;
        Response login = RestAssured.given().log().all().contentType("application/json").
                body(Helper.getObjectAsString(loginRequest)).
                post(PropertiesLoader.readProperty("BaseLine") +EndPoint.loginEndPoint);
        return login ;
    }
    public static Response ListUsers(Map<String,String> headers  ,Map<String,String> queryParams  )
    {
        Response listUsersResponse=RestAssured.given().log().all().headers(headers).queryParams(queryParams)
                .get(PropertiesLoader.readProperty("BaseLine")+EndPoint.userEndPoint);
        return  listUsersResponse ;
    }
    public static Response SingleUsers(Map<String,String> header )
    {
        Response singleUsersResponse=RestAssured.given().log().all().headers(header).pathParam("userId",2)
                .get(PropertiesLoader.readProperty("BaseLine")+EndPoint.userEndPoint+"/"+"{userId}");
        return  singleUsersResponse ;
    }
    public static Response CreateUser(String name , String job,Map<String,String>header)
    {
        CreateRequest createRequest =new CreateRequest();
        createRequest.name =name ;
        createRequest.job=job ;
        Response create = RestAssured.given().log().all().contentType("application/json").headers(header).
                body(Helper.getObjectAsString(createRequest)).
                post(PropertiesLoader.readProperty("BaseLine")+EndPoint.userEndPoint);
        return create ;
    }
}
