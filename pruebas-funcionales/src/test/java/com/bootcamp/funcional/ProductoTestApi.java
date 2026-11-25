package com.bootcamp.funcional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.report.LevelResolver;
import com.atlassian.oai.validator.report.ValidationReport;
import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.config.JsonPathConfig.NumberReturnType;
import io.restassured.specification.RequestSpecification;

@DisplayName("API de Productos en el laboratorio")
public class ProductoTestApi {
	
	private static RequestSpecification api;
    private static OpenApiValidationFilter contrato;
    
    
    @BeforeAll
    static void iniciarSesion() {
        // Los decimales como BigDecimal: comparar dinero con double da falsos negativos
        RestAssured.config = RestAssuredConfig.config()
                .jsonConfig(JsonConfig.jsonConfig().numberReturnType(NumberReturnType.BIG_DECIMAL));

        contrato = new OpenApiValidationFilter(OpenApiInteractionValidator
                .createForSpecificationUrl(Path.of(System.getProperty("contrato")).toUri().toString())
                // La cookie la exige Kong/Authelia; aquí sólo nos interesa validar RESPUESTAS
                .withLevelResolver(LevelResolver.create()
                        .withLevel("validation.request", ValidationReport.Level.IGNORE)
                        .build())
                .build());

        api = Laboratorio.autenticado();   // un solo login para toda la clase
    }
	
    	@Test
	    @DisplayName("GET /api/v1/products/1 -> 200 con el producto 1")
	    void testGetProductById() {
	        given(api).filter(contrato)
	        .when()
	                .get("/api/v1/products/1")
	        .then()
	                .statusCode(200)
	                .contentType("application/json")
	                .time(lessThan(2L), TimeUnit.SECONDS)
	                .body("id", equalTo(1));
	                //.body("name",  equalTo("Laptop Pro 14"));
	    }

}
