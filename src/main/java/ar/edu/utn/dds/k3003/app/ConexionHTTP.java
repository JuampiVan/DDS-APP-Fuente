package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.DTOs.PdIDTOEnviado;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class ConexionHTTP {
    private String url;
    private RestTemplate restTemplate;

    public ConexionHTTP() {
        this.url = "https://dds-app-procesador-ykg6.onrender.com/pdis";
        this.restTemplate = new RestTemplate();
    }

    public Optional<PdIDTOEnviado> postearPdi(PdIDTOEnviado pdi) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PdIDTOEnviado> request = new HttpEntity<>(pdi, headers);
            ResponseEntity<PdIDTOEnviado> response = restTemplate.postForEntity(url, request, PdIDTOEnviado.class);

            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            if (response.getStatusCode().is2xxSuccessful()) {
                return Optional.ofNullable(response.getBody());
            } else {
                System.err.println("Respuesta no exitosa: " + response.getStatusCode());
                return Optional.empty();
            }

        } catch (HttpServerErrorException e) {
            System.err.println("Error 500 del servidor: " + e.getResponseBodyAsString());
            return Optional.empty();
        } catch (RestClientException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return Optional.empty();
        }
    }
}
