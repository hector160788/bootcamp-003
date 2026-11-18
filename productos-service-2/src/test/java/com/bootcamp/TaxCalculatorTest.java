package com.bootcamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bootcamp.helper.TaxCalculator;

@SpringBootTest
public class TaxCalculatorTest {
	
	 private static final double DELTA = 0.0001;

	    @Test
	    void shouldCalculateIvaWhenPriceIsGreaterThanZero() {
	        double valorProducto = 100.0;
	        TaxCalculator calculator = new TaxCalculator(16.0);

	        double ivaCalculado = calculator.calculateIva(valorProducto);

	        assertEquals(16.0, ivaCalculado, DELTA);
	    }
	    
	    @Test
	    void valorMayorACero() {
	        double valorProducto = -1.0;
	        TaxCalculator calculator = new TaxCalculator(16.0);

	        double ivaCalculado = calculator.calculateIva(valorProducto);

	        assertEquals(0.0, ivaCalculado, DELTA);
	    }
	    
	    @Test
	    void valorIgualACero() {
	        double valorProducto = 0.0;
	        TaxCalculator calculator = new TaxCalculator(16.0);

	        double ivaCalculado = calculator.calculateIva(valorProducto);

	        assertEquals(0.0, ivaCalculado, DELTA);
	    }

}
