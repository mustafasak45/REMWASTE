package com.opensource.tests.apiTests;

import com.opensource.core.api.base_urls.JsonPlaceHolderBaseUrl;
import com.opensource.core.api.pojo.JsonPlaceHolderPojo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static io.restassured.RestAssured.given;

public class JsonPlaceHolderTests extends JsonPlaceHolderBaseUrl {
    /*
         Given
            https://jsonplaceholder.typicode.com/todos
            {
            "userId": 55,
            "title": "Tidy your room",
            "completed": false
            }
        When
            I send POST Request to the Url
        Then
            Status code is 201
        And
            response body is like {
                                    "userId": 55,
                                    "title": "Tidy your room",
                                    "completed": false,
                                    "id": 201
                                    }
     */

    @Test
    public void givenValidTodoData_whenPostRequest_thenReturn201AndVerifyResponseBody() {
        spec.pathParam("first","todos");

        JsonPlaceHolderPojo expData = new JsonPlaceHolderPojo(55,"Tidy your room",false);

        Response response = given().
                spec(spec).
                contentType(ContentType.JSON).
                body(expData).
                when().
                post("/{first}");

        JsonPlaceHolderPojo actData = response.as(JsonPlaceHolderPojo.class);

        assertEquals(expData.getUserId(),actData.getUserId());
        assertEquals(expData.getTitle(),actData.getTitle());
        assertEquals(expData.getCompleted(),actData.getCompleted());
    }
}
