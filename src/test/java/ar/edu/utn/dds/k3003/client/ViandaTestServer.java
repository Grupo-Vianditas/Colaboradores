package ar.edu.utn.dds.k3003.client;

import ar.edu.utn.dds.k3003.app.WebApp;
import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.json.JavalinJackson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ar.edu.utn.dds.k3003.app.WebApp.configureObjectMapper;

public class ViandaTestServer {

  public static void main(String[] args) throws Exception {

    var env = System.getenv();

    var port = Integer.parseInt(env.getOrDefault("PORT", "8081"));

    var app = Javalin.create(config -> {
      config.jsonMapper(new JavalinJackson().updateMapper(WebApp::configureObjectMapper));
    }).start(port);

    app.get("/viandas/search/findByColaboradorIdAndAnioAndMes", ViandaTestServer::viandasDonadasTest);
  }

  private static void viandasDonadasTest(Context context) {
    if(context.queryParam("colaboradorId").equals("1")) {
      List<ViandaDTO> viandas = new ArrayList<>();
      var unaDonacion = new ViandaDTO("unQRQueExiste", LocalDateTime.now(), EstadoViandaEnum.PREPARADA, 0L, 1);
      var otraDonacion = new ViandaDTO("otroQRQueExiste", LocalDateTime.now(), EstadoViandaEnum.PREPARADA, 0L, 1);
      unaDonacion.setId(14L);
      otraDonacion.setId(15L);
      viandas.add(unaDonacion);
      viandas.add(otraDonacion);
      context.json(viandas);
    } else {
      context.result("Vianda no encontrada");
      context.status(HttpStatus.NOT_FOUND);
    }
  }
}