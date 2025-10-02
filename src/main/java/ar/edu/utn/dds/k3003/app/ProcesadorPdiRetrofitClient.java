package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.DTOs.PdIDTOEnviado;
import ar.edu.utn.dds.k3003.DTOs.PdiDTORecibido;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProcesadorPdiRetrofitClient {

    @POST("pdis")
    Call<PdiDTORecibido> procesar(@Body PdIDTOEnviado pdi);
}
