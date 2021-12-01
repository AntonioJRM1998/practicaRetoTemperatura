package com.example.demo.endpoinst;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class EndPointTempActual {
	private Counter counterGrados;
	private Counter counterFar;
	
	private final static Logger logger= LoggerFactory.getLogger(EndPointTempActual.class);
	
	public EndPointTempActual(MeterRegistry meterRegistry,MeterRegistry meterRegistry2) {
		this.counterGrados=Counter.builder("Invocaciones mas usadas").description("Total de invocaciones para Grados").register(meterRegistry);
		this.counterFar=Counter.builder("Invocaciones mas usadas").description("Total de invocaciones para Fahrenheit").register(meterRegistry);
	}
	
	@Value("${some.value}")
	private String myValue;
	
	@GetMapping(path="/myValue")
	public String myValue() {
		return this.myValue;
	}
	@GetMapping("/temperatura")
	public String devolverTemp() {
		logger.info("Temperaturas");
		return"Grados Celcuis"+ (Integer.parseInt(myValue())-32/1.8f) +" Grados Fahrenheit "+((Integer.parseInt(myValue())*1.8f)+32);
	}
	@GetMapping("/grados/{temp}")
	public String celcius(@PathVariable int temp) {
		counterGrados.increment();
		logger.info("Temperaturas para grados");
		return"Grados Celcuis " + ((int)(temp-32/1.8f));
	}
	@GetMapping("/faren/{temp}")
	public String Faren(@PathVariable int temp) {
		counterFar.increment();
		logger.info("Temperaturas para Fahrenheit");
		return"Grados Fahrenheit " + (temp*1.8f/+32);
	}
}
