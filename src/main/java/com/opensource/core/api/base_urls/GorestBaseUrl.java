package com.opensource.core.api.base_urls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;

public class GorestBaseUrl {

    public static RequestSpecification spec;

    @Before
    public void setUp() throws Exception {
        spec = new RequestSpecBuilder().setBaseUri("https://gorest.co.in/public/v1").build();
    }
}
