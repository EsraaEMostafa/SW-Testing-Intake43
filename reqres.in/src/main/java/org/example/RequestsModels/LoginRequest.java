package org.example.RequestsModels;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
        "email",
        "password"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {
    @JsonProperty("email")
    public String email;
    @JsonProperty("password")
    public String password;

}
