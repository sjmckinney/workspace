package com.metaweather.api;

import com.tngtech.java.junit.dataprovider.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(DataProviderRunner.class)
public class WeatherByLocationAndDateTest {
    
    private static RequestSpecification requestSpec;
    
    @DataProvider
    public static Object[][] locationsAndResponses() {
        return new Object[][] {
            {"api", "location", 30720, "nottingham"}
        };
    }

    @BeforeClass
    public static void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://metaweather.com").
            build();
    }

    @Test
    @UseDataProvider("locationsAndResponses")
    public void GetWeatherForLocationAndTomorrow(String api, String location, int woeid, String place){
        
        System.out.println("Getting weather for location " + place);
        
        given().
            spec(requestSpec).
            pathParam("api", api).
            pathParam("location", location).
            pathParam("woeid", woeid).
        when().
            get("{api}/{location}/{woeid}/").
        then().
            log().all();
            // assertThat().
            // body("[0].'title'", equalTo("Nottingham"));
    }
}

