Feature: Validating Place API's

    @AddPlace @Regression
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
            | Nani | French-FR  | Rainbow St 432    |

    @DeletePlace @Regression
    Scenario: Verify if Place is being successfully deleted using DeletePlaceAPI
        Given DeletePlace Payload
        When user calls "DeletePlaceAPI" with "DELETE" http request
        Then the API call got success with status code 200
        And "status" in response body is "OK"
