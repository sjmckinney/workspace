# README

## Task Details
The task was to send a number of requests the **MetaWeather** API to retrieve weather data by location and date.

The task asked for ways of expanding the tests to incorporate multiple destinations and dates.

## Project Structure

This is classic run of the the mill `Maven` project.

The test framework is **JUnit** which is included in the maven archetype by default.

The API client is **Rest Assured** which provides the means to create the Request and examine and manipulate the response. The **Rest Assured** library provides a DSL with a Given-Then-When like syntax and a fair number of ways of separating and parameterizing test data in order to reduce repetition and provide flexibility.

Assertions could be performed with either **Rest Assured** inbuilt matchers or, for more complex conditions, the **Hamcrest** library.

The seperation of test data is acheived by utilising an `@DataProvider` strucure to encapsulate the input parameters into a nested array of type `Object`. These were then passed to the test methods as both parameters to the request and expected values.

Since the date parameter was to be described in relative terms e.g. tomorrow, it had to be generated dynmically as of offset to the current date. The date was generated and correctly formatted by a utilty method with the offset represented as an `enum`. The latter provides some degree of type safety and greater context than passing in a literal number value. A simple offset is fine for a small range of dates but would be unweildly if the range of possible dates encompassed more than a week into the past or future.

The structure of the JSON response returned was validated using a JSON schema. This will flexibly validate that the response contains the correct number of named elements structured correctly and containing the correct type and range of values.

Time has prevented me from using more than the most basic of JSON schema files. At the vary least I would have customised it to make use of regular expressions and enums to validate the fixed range of responses and values returned i.e. date formats, weather states.

## Triggering the Tests

### Navigate to project root

In a command window or terminal, navigate to the `testsuite` directory, which contains the `pom.xml` file and issue the following commands;

### To run the tests
`mvn test`

### To run tests and generate a test report
`mvn site`

The report can be found in;

`<project root>/target/site/surefire-report.html`

In a CI/CD environment a post test job could be configured to copy this and its supporting files to an archive location to provide an historical record of the test results and as the source of real time test reporting.

## Test Design

Test code should be first class code in that it should be written and structured using accepted software principles.

It should be maintainable, flexible and extensible and make best use of the features of the language its written in as well as the features of any frameworks it utilises.

Had I had more time I would have used the OO features of Java to promote re-use of code by using a Base Test class to peform some of the configuration before each test rather than create a `@BeforeClass` method in each test class.

In previous API test frameworks I have used classes to model both requests and responses. I created new instances of these request models to generate test data, which was serialized into requests and the deserialized the responses to provide objects that I could assert against.

That is a feature of **Rest Assured** but given the relatively simple structures and nature of the data returned I feel that it would be overkill in the case of the MetaWeather API. The validation of the responses using JSON schema files provides a similar level of confidence in the consistency of the responses over a range of valid locations and dates.

Seperation of data from tests at the very least provides some degree of clarity as well as allow data and tests to be amended independently. The re-use of any classes containing test data really relies on the nature of the API under test and the complexity of request parameters.

## Stretch Goal

The stretch goal was an invitation to create a suite of tests that would combine a series of tests that would inject a series of method types, path, query string parameters, request body data and request header values in order to test the robustness of the API.

In a more complex system one might also look at authentication methods as well as security setting like CORS and CRSF header values as well as general behaviour like caching.

Depending on the parameters chosen one could soon stray into testing non-functional behaviour and the characteristics and configuration of the server.

I believe the most useful areas to investigate would be response to invalid query and path parameters combined with request make with HTTP methods other than GET.

Query parameters and path segments represent values like place names, a combination of latitude and longitude values and dates represented by strings and numbers various delimited variously by a comma and forward slashes.

It would also be valid to inject a series of random values before, after and into the advertised endpoint paths as well as truncating the path values.

With some knowledge of the routing mechanisms / application controller code it would be possible to tailor the range of inputs to eliminate those values the application expects and will handle correctly.