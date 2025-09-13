package ar.edu.utn.dds.k3003;

import datadog.trace.api.GlobalTracer;
import datadog.trace.api.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
