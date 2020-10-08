package com.metaweather.api;

import com.tngtech.java.junit.dataprovider.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

@RunWith(DataProviderRunner.class)
public class WeatherByLocationAndDateTest {
    
    private static RequestSpecification requestSpec;
    
    @DataProvider
    public static Object[][] locationsAndResponses() {
        return new Object[][] {
            {"api", "location", 30720, "Nottingham" TimeFrame.Now}
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
    public void GetWeatherForLocation(String api, String location, int woeid, String place){
        
        System.out.println("GET weather for location " + place);
        
        given().
            spec(requestSpec).
            pathParam("api", api).
            pathParam("location", location).
            pathParam("woeid", woeid).
        when().
            get("{api}/{location}/{woeid}/").
        then().
            assertThat().
            body("title", equalTo(place));
    }

    @Test
    @UseDataProvider("locationsAndResponses")
    public void GetWeatherForLocationAndDate(String api, String location, int woeid, String place TimeFrame offset){
        
        String date = DateCalculator.CalculateDateWithOffset(offset);
        
        System.out.println("GET weather for location " + place + " and date " date);
        
        given().
            spec(requestSpec).
            pathParam("api", api).
            pathParam("location", location).
            pathParam("woeid", woeid).
        when().
            get("{api}/{location}/{woeid}/{date}").
        then().
            //log().all();
            assertThat().
            body("title", equalTo(place));
    }

    @Test
    @UseDataProvider("locationsAndResponses")
    public void ValidateGetWeatherForLocation(String api, String location, int woeid, String place){
        
        System.out.println("Validation of response for GET weather for location " + place);
        
        given().
            spec(requestSpec).
            pathParam("api", api).
            pathParam("location", location).
            pathParam("woeid", woeid).
        when().
            get("{api}/{location}/{woeid}/").
        then().
            assertThat().
            body(matchesJsonSchemaInClasspath("consolidated-weather.json"));
    }
}

