package stepDefinitions;

import java.io.IOException;
import io.cucumber.java.Before;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {

        // write a code that will return a place id
        stepDefinition m = new stepDefinition();

        // execute this code only when place id is null, if not is because the @AddPlace scenario had run
        if (stepDefinition.place_id==null)    // Im calling the static global variable
        {
        m.add_place_payload_with("Lucas", "Italian-IT", "Marvelous st 456");
        m.user_calls_with_http_request("AddPlaceAPI", "POST");
        m.verify_parameter_created_maps_to_something_using_something("Lucas", "GetPlaceAPI");
        }
    }
}
