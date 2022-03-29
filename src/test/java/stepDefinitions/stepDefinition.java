package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.*;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.restassured.response.Response;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;


public class stepDefinition extends Utils{

    // global variables so parts of the calls and response could be used in all the step definitions
    RequestSpecification req;
    ResponseSpecification res;
    Response response;
    TestDataBuild data = new TestDataBuild();
    static String place_id;  // static to endure to all the scenarios, otherwise I will receive a null pointer exception error 


    @Given("^Add Place Payload with \"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\"$")
    public void add_place_payload_with (String name, String language, String address) throws IOException {

        req = given().spec(requestSpecification()).body(data.addPlacePayload(name,language,address));
    }

    @When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request$")
    public void user_calls_with_http_request(String resource, String httpmethod) {

        // constructor will be called  with value of resource which is passed
        APIResources resourceAPI = APIResources.valueOf(resource);

        res = new ResponseSpecBuilder().expectStatusCode(200)
                                       .expectContentType(ContentType.JSON)  
                                       .build();  

        if(httpmethod.equalsIgnoreCase("POST"))
            response = req.when().post(resourceAPI.getResource());
        else if(httpmethod.equalsIgnoreCase("GET"))
            response = req.when().get(resourceAPI.getResource());
        else if(httpmethod.equalsIgnoreCase("DELETE"))
            response = req.when().delete(resourceAPI.getResource());
    }

    @Then("the API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(int expectedStatusCode) {
        
        assertEquals(response.getStatusCode(), expectedStatusCode);
    }

    @And("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
    public void something_in_response_body_is_something(String keyValue, String Expectedvalue) {

        assertEquals(getJsonPath(response,keyValue), Expectedvalue);
    }

    @And("^verify place_id created maps to \"([^\"]*)\" using \"([^\"]*)\"$")
    public void verify_parameter_created_maps_to_something_using_something(String expectedParam, String resource) throws IOException {
        
        //requestSpec
        place_id = getJsonPath(response, "place_id");
        req = given().spec(requestSpecification()).queryParam("place_id", place_id);

        user_calls_with_http_request(resource,"GET");
        String actualName = getJsonPath(response, "name");
        assertEquals(actualName, expectedParam);
    }

    @Given("^DeletePlace Payload$")
    public void deleteplace_payload () throws IOException {

        // body is one single key value, that's why I do not use a proper pojo class
        req = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
    }

}
