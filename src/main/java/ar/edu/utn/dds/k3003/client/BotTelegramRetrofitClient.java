package ar.edu.utn.dds.k3003.client;

import ar.edu.utn.dds.k3003.model.eventos.DTO.NotificacionDTO;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;


public interface BotTelegramRetrofitClient {
  @POST("/eventos")
  Call<Void> notificacionDeEvento(
      @Body NotificacionDTO notificacionDTO
      );
}
