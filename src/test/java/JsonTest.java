/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import java.io.File;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author badri
 */
public class JsonTest {
    
    @Rule
    public WireMockRule wiremockrule = new WireMockRule(8080);
    
    public JsonTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getProducts_Should_Contain_A_BlueMouse(){
        mockproductServer();
        when().
                get("http://localhost:8080/products").then().
                body("[1].name",containsString("A blue mouse"));
                
    }
    
    @Test
    public void getProducts_Should_getResponse_asPerSchema(){
        mockproductServer();
        when().
                get("http://localhost:8080/products").then().
                body(matchesJsonSchema(new File("src/test/resources/__files/ProductJsonSchema.json")));
    }
    
    public void mockproductServer(){
        wiremockrule.stubFor(WireMock.get(urlEqualTo("/products"))      
        .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBodyFile("Product.json")));
        wiremockrule.start();
    }
}
