package org.example.ResponseModels;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.example.ResponseModels.ListUserResponse.Support;
import org.example.ResponseModels.ListUserResponse.UserData;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "support"
})
public class SingleUserResponse {
    @JsonProperty("data")
    public UserData data;
    @JsonProperty("support")
    public Support support;
}
