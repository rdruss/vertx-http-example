package io.openshift.booster;

import io.restassured.RestAssured;
import org.arquillian.cube.openshift.impl.enricher.AwaitRoute;
import org.arquillian.cube.openshift.impl.enricher.RouteURL;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import static io.openshift.booster.HttpApplication.template;
import static io.restassured.RestAssured.get;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Arquillian.class)
public class OpenShiftIT {

    @RouteURL("${app.name}")
    @AwaitRoute(path = "/api/greeting", repetitions = 10)
    private URL route;

    @Before
    public void setup() {
        RestAssured.baseURI = route.toString();
    }

    @Test
    public void testThatWeAreReady() {
        // Check that the route is served.
        get().then().statusCode(equalTo(200));
        get("/api/greeting").then().statusCode(equalTo(200));
    }

    @Test
    public void testThatWeServeAsExpected() {
        get("/api/greeting").then().body("content", equalTo(String.format(template, "World")));
        get("/api/greeting?name=vert.x").then().body("content", equalTo(String.format(template, "vert.x")));
    }
}
