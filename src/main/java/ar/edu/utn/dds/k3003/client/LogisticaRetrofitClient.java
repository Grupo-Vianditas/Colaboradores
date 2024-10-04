package ar.edu.utn.dds.k3003.client;

import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface LogisticaRetrofitClient {
  @GET("/traslados/search/findByColaboradorId")
  Call<List<TrasladoDTO>> trasladosDeColaborador(
      @Query("colaboradorId") Long colaboradorId
  );
}
