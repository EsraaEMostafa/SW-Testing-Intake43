import io.restassured.response.Response;
import org.example.Requests.Requests;
import org.example.ResponseModels.LoginResponse;
import org.example.ResponseModels.SingleUserResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.example.Requests.Requests.SingleUsers;


public class SingleUserTests {
    static String UserName="eve.holt@reqres.in";
    static String Password="cityslicka";
    String token="";

    @BeforeClass
    public void loginSuccess(){
        LoginResponse login = Requests.loginRequest(UserName,Password);
        token = login.token;
    }
    @Test
    public void SingleUsersSuccess() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
//        Map<String, String> pathParams = new HashMap<>();
//        pathParams.put("id", "4");
        Response singleUsersResponse = SingleUsers(headers);
        singleUsersResponse.prettyPrint();
        SingleUserResponse singleUser = singleUsersResponse.as(SingleUserResponse.class);

        Assert.assertNotNull(singleUser.data.firstName);
        System.out.println(singleUser.data.firstName);
    }

    }
