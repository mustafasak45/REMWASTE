package com.opensource.tests.apiTests;

import com.opensource.core.api.base_urls.GorestBaseUrl;
import com.opensource.core.api.pojo.GorestDataPojo;
import com.opensource.core.api.pojo.GorestMetaPojo;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class GorestDataTests extends GorestBaseUrl {

    @Test
    public void getUserById_shouldReturnCorrectUserDetailsAndMetaData() {
        spec.pathParams("first","users","second",8036415);

        GorestDataPojo expData = new GorestDataPojo("Adikavi Johar","adikavi_johar@hegmann-lind.test","female","active");
        GorestMetaPojo expMeta = new GorestMetaPojo(null,expData);

        Response response = given().spec(spec).when().get("/{first}/{second}");

        GorestMetaPojo actMeta = response.as(GorestMetaPojo.class);

        assertEquals(expMeta.getMeta(),actMeta.getMeta());

        assertEquals(expMeta.getData().getName(),actMeta.getData().getName());
        assertEquals(expMeta.getData().getEmail(),actMeta.getData().getEmail());
        assertEquals(expMeta.getData().getGender(),actMeta.getData().getGender());
        assertEquals(expMeta.getData().getStatus(),actMeta.getData().getStatus());
    }
}
