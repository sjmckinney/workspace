package com.metaweather.api;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import com.metaweather.utils.DateCalculator;
import com.metaweather.utils.TimeFrame;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

@RunWith(DataProviderRunner.class)
public class WeatherByLocationAndDateTest {
    
    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;
    
    @DataProvider
    public static Object[][] locationsAndResponses() {
        return new Object[][] {
            {"api", "location", 30720, "Nottingham", TimeFrame.PlusOne }
        };
    }

    @BeforeClass
    public static void createSpecs() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://metaweather.com").
            build();

        responseSpec = new ResponseSpecBuilder().
            expectStatusCode(200).
            expectContentType(ContentType.JSON).
            expectHeader("Allow", "GET, HEAD, OPTIONS").
            build();
    }

    @Test
    @UseDataProvider("locationsAndResponses")
    public void GetWeatherForLocationTest(String api, String location, int woeid, String place, TimeFrame offset){
        
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
            spec(responseSpec).
            body("title", equalTo(place));
    }

    @Test
    @UseDataProvider("locationsAndResponses")
    public void GetWeatherForLocationAndDateTest(String api, String location, int woeid, String place, TimeFrame offset){
        
        String date = DateCalculator.CalculateDateWithOffset(offset);
        
        System.out.println("GET weather for location " + place + " and date " + date);
        
        given().
            spec(requestSpec).
            pathParam("api", api).
            pathParam("location", location).
            pathParam("woeid", woeid).
        when().
            get("{api}/{location}/{woeid}/" + date).
        then().
            assertThat().
            spec(responseSpec).
            body("applicable_date", everyItem(equalTo(date.replace('/', '-'))));
    }

    @Test
    @UseDataProvider("locationsAndResponses")
    public void ValidateGetWeatherForLocationTest(String api, String location, int woeid, String place, TimeFrame offset){
        
        System.out.println("GET weather for location " + place + " - validation of response against json schema");
        
        given().
            spec(requestSpec).
            pathParam("api", api).
            pathParam("location", location).
            pathParam("woeid", woeid).
        when().
            get("{api}/{location}/{woeid}/").
        then().
            assertThat().
            spec(responseSpec).
            body(matchesJsonSchemaInClasspath("consolidated-weather.json"));
    }
}

