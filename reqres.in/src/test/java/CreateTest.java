import io.restassured.response.Response;
import org.example.Requests.Requests;
import org.example.RequestsModels.CreateRequest;
import org.example.ResponseModels.CreateResponse;
import org.example.ResponseModels.LoginResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.example.Requests.Requests.CreateUser;

public class CreateTest {
    static String name="morpheus";
    static String job="leader";
    static String UserName="eve.holt@reqres.in";
    static String Password="cityslicka";
    String token="";
    @BeforeClass
    public void loginSuccess(){
        LoginResponse login = Requests.loginRequest(UserName,Password);
        token = login.token;
    }
    @Test
    void createSuccess() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        Response create = CreateUser(name, job,headers);
        CreateResponse createUser = create.as(CreateResponse.class);
        Assert.assertEquals(create.statusCode(), 201, "Fail");
        Assert.assertEquals(createUser.name, "morpheus");
    }
}
