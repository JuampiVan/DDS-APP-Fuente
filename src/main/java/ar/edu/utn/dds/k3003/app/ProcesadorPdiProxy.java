package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.DTOs.PdIDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ProcesadorPdiProxy {

    private final String endpoint;
    private final ProcesadorPdiRetrofitClient service;

    public ProcesadorPdiProxy(ObjectMapper objectMapper) {

        var env = System.getenv();
        this.endpoint = env.getOrDefault("URL_PROCESADOR", "https://dds-app-procesador-ykg6.onrender.com/");

        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        var retrofit =
                new Retrofit.Builder()
                        .baseUrl(this.endpoint)
                        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                        .build();

        this.service = retrofit.create(ProcesadorPdiRetrofitClient.class);
    }

    public ProcesadorPdiProxy(String endpoint, ProcesadorPdiRetrofitClient service) {
        this.endpoint = endpoint;
        this.service = service;
    }

    public PdIDTO procesar(PdIDTO pdIDTO) throws java.io.IOException {
        var res = service.procesar(pdIDTO).execute();
        if (!res.isSuccessful()) {
            throw new RuntimeException("Error conectandose con procesadorPdi (" + res.code() + ")");
        }

        return res.body();
    }
}
