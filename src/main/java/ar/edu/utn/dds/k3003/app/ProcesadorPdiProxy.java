package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.DTOs.PdIDTOEnviado;
import ar.edu.utn.dds.k3003.DTOs.PdiDTORecibido;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

public class ProcesadorPdiProxy {

    private final String endpoint;
    private final ProcesadorPdiRetrofitClient service;

    public ProcesadorPdiProxy(ObjectMapper objectMapper) {

        var env = System.getenv();
        this.endpoint = env.getOrDefault("URL_PROCESADOR", "https://dds-app-procesador.onrender.com/");

        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)   // conexión al servidor
                .readTimeout(60, TimeUnit.SECONDS)      // tiempo de respuesta
                .writeTimeout(60, TimeUnit.SECONDS)     // tiempo para enviar request
                .build();

        var retrofit =
                new Retrofit.Builder()
                        .baseUrl(this.endpoint)
                        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                        .client(okHttpClient)
                        .build();

        this.service = retrofit.create(ProcesadorPdiRetrofitClient.class);
    }

    public ProcesadorPdiProxy(String endpoint, ProcesadorPdiRetrofitClient service) {
        this.endpoint = endpoint;
        this.service = service;
    }

    public PdiDTORecibido procesar(PdIDTOEnviado pdIDTOEnviado) throws java.io.IOException {

        var res = service.procesar(pdIDTOEnviado).execute();
        if (!res.isSuccessful()) {
            String errorBody = res.errorBody() != null ? res.errorBody().string() : "sin detalles";
            throw new RuntimeException("Error conectandose con procesadorPdi (" + res.code() + ") " + errorBody);
        }

        return res.body();
    }
}
