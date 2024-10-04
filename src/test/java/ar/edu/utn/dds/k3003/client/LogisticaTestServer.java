package ar.edu.utn.dds.k3003.client;

import static ar.edu.utn.dds.k3003.app.WebApp.configureObjectMapper;

import ar.edu.utn.dds.k3003.facades.dtos.EstadoTrasladoEnum;
import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.json.JavalinJackson;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogisticaTestServer {
  public static void main(String[] args) throws Exception {

    var env = System.getenv();

    var port = Integer.parseInt(env.getOrDefault("PORT", "8082"));

    var app = Javalin.create(config -> {
      config.jsonMapper(new JavalinJackson().updateMapper(mapper -> {
        configureObjectMapper(mapper);
      }));
    }).start(port);

    app.get("/traslados/search/findByColaboradorId", LogisticaTestServer::trasladosTest);
  }

  private static void trasladosTest(Context context) {
    if(context.queryParam("colaboradorId").equals("1")) {
      List<TrasladoDTO> traslados = new ArrayList<>();
      var unTraslado = new TrasladoDTO("unQr", EstadoTrasladoEnum.ENTREGADO, LocalDateTime.now(), 1, 2);
      unTraslado.setId(1L);
      traslados.add(unTraslado);
      context.json(traslados);
    } else {
      context.result("Traslado no encontrado");
      context.status(HttpStatus.NOT_FOUND);
    }
  }
}
