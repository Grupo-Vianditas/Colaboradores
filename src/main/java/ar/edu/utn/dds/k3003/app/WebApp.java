package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.client.LogisticaProxy;
import ar.edu.utn.dds.k3003.client.ViandasProxy;
import ar.edu.utn.dds.k3003.controller.ColaboradorController;
import ar.edu.utn.dds.k3003.facades.dtos.Constants;
import ar.edu.utn.dds.k3003.model.CalculadorDePuntos;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.micrometer.MicrometerPlugin;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmHeapPressureMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class WebApp {
  //TODO: Configurar token como variable de entorno
  private static final String TOKEN = "tokenDeEjemplo";

  public static void main(String[] args) {

    Fachada fachada = new Fachada();

    var env = System.getenv();
    var objectMapper = createObjectMapper();

    fachada.setViandasProxy(new ViandasProxy(objectMapper));
    fachada.setLogisticaProxy(new LogisticaProxy(objectMapper));

    ColaboradorController colaboradorController = new ColaboradorController(fachada);


    // Configuración de Prometheus########################

    var registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    registry.config().commonTags("application", "k3003");

    try (var jvmGcMetrics = new JvmGcMetrics();
         var jvmHeapPressureMetrics = new JvmHeapPressureMetrics()) {
      jvmGcMetrics.bindTo(registry);
      jvmHeapPressureMetrics.bindTo(registry);
    }
    new JvmMemoryMetrics().bindTo(registry);
    new ProcessorMetrics().bindTo(registry);
    new FileDescriptorMetrics().bindTo(registry);

    Gauge.builder("Cantidad_donadores_viandas", fachada, Fachada::cantidadDonadores)
        .description("Cantidad de donadores de viandas").register(registry);
    Gauge.builder("cantidad_transportadores_viandas", fachada, Fachada::cantidadTransportadores)
        .description("Cantidad de transportadores de viandas").register(registry);
    Gauge.builder("cantidad_colaboradores", fachada, Fachada::cantidadColaboradores)
        .description("Cantidad total de colaboradores").register(registry);



    final var micrometerPlugin =
        new MicrometerPlugin(config -> config.registry = registry);

    //Fin de configuración de Prometheus##################


      var port = Integer.parseInt(env.getOrDefault("PORT", "8080"));
      var app = Javalin.create(config -> { config.registerPlugin(micrometerPlugin); }).start(port);

      app.get("/", ctx -> ctx.result("Hello World"));
      app.get("/colaboradores/{id}", colaboradorController::getColaborador);
      app.get("/colaboradores", colaboradorController::getColaboradores);
      app.post("/colaboradores", colaboradorController::crearColaborador);
      app.patch("/colaboradores/{id}", colaboradorController::modificarColaborador);
      app.get("/colaboradores/{id}/puntos", colaboradorController::getPuntuacionColaborador);
      app.put("/formula", colaboradorController::modificarPuntuacionMultiplicador);
      app.post("/donacionDeDinero", colaboradorController::donacionDeDinero);

      app.post("/borrarTodaLaBase", colaboradorController::borrarTodaLaBase);


      app.get("/metrics",
        ctx -> {
          var auth = ctx.header("Authorization");

          if (auth != null && auth.intern() == "Bearer " + TOKEN) {
            ctx.contentType("text/plain; version=0.0.4")
                .result(registry.scrape());
          } else {
            ctx.status(401).json("unauthorized access");
          }
        });
    }

    public static ObjectMapper createObjectMapper() {
        var objectMapper = new ObjectMapper();
        configureObjectMapper(objectMapper);
        return objectMapper;
    }

    public static void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var sdf = new SimpleDateFormat(Constants.DEFAULT_SERIALIZATION_FORMAT, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        objectMapper.setDateFormat(sdf);
    }
}
