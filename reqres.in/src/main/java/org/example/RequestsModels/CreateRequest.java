package org.example.RequestsModels;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "name",
        "job"
})
public class CreateRequest {

    @JsonProperty("name")
    public String name;
    @JsonProperty("job")
    public String job;

}