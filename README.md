![Java CI](https://github.com/leomiro1/APIFramework/workflows/Java%20CI/badge.svg)

# Cucumber BDD API Framework
    
Maven project with REST Assured, Cucumber and Cucumber report (based on https://www.udemy.com/course/rest-api-automation-testing-rest-assured/)

## Requisites

openjdk version "11.0.14"
Apache Maven 3.6.3

## To execute the tests

* To run all the scenarios: mvn test verify
* Valid tags of scenarios: @AddPlace, @DeletePlace, @Regresion -> mvn test verify -Dcucumber.filter.tags="@AddPlace"

## To create a BUILD in Jenkins

On build configuration pick option 'Invoke top level maven target'.
Specify the local path and run from command shell. The command could be for example: test verify -Dcucumber.filter.tags="@Regression"

### Test runner
```Java
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/features",
    glue = "stepDefinitions",
    plugin = "json:target/jsonReports/cucumber-report.json"/*,
    tags= "@DeletePlace"*/
)

public class TestRunner {
    // compile - test - verify    
}
```
### A sample Feature file
```gherkin
    Scenario Outline: Verify if Place is being successfully added using AddPlaceAPI
        Given Add Place Payload with "<name>","<language>" and "<address>"
        When user calls "AddPlaceAPI" with "POST" http request
        Then the API call got success with status code 200
        And "status" in response body is "OK"
        And "scope" in response body is "APP"
        And verify place_id created maps to "<name>" using "GetPlaceAPI"

        Examples:
            | name | language   | address           |
            | Facu | Spanish-SP | Evergreen ave 123 |
```
### A sample stepdefinition
```Java
    @Then("the API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(int expectedStatusCode) {
        
        assertEquals(response.getStatusCode(), expectedStatusCode);
    }
```
### Example of how a JAVA code snippet looks
```Java
    // method to obtain a value from json
    public String getJsonPath(Response response, String key){
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }
```
## Postman collection
Check file Maps API.postman_collection.json

