package ar.edu.utn.dds.k3003.client;

import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.NotificacionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.HttpStatus;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BotTelegramProxy {
  private final String endpoint;
  private final BotTelegramRetrofitClient service;
  public BotTelegramProxy(ObjectMapper objectMapper) {

    var env = System.getenv();
    this.endpoint = env.getOrDefault("URL_BOT_TELEGRAM", "http://localhost:8083/");

    var retrofit =
        new Retrofit.Builder()
            .baseUrl(this.endpoint)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build();

    this.service = retrofit.create( BotTelegramRetrofitClient.class);
  }

  public void notificarEvento(NotificacionDTO notificacionDTO) throws NoSuchElementException {
    Response<Void> execute = null;
    try {
      execute = service.notificacionDeEvento(notificacionDTO).execute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    throw new RuntimeException("Error conectandose con el componente BotTelegram");
  }
}
