package ar.edu.utn.dds.k3003.client;

import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.RutaDTO;
import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.facades.exceptions.TrasladoNoAsignableException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.HttpStatus;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LogisticaProxy implements FachadaLogistica {

  private final String endpoint;
  private final LogisticaRetrofitClient service;

  public LogisticaProxy(ObjectMapper objectMapper) {

    var env = System.getenv();
    this.endpoint = env.getOrDefault("URL_LOGISTICA", "http://localhost:8082/");

    var retrofit =
        new Retrofit.Builder()
            .baseUrl(this.endpoint)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build();

    this.service = retrofit.create(LogisticaRetrofitClient.class);
  }

  @Override
  public RutaDTO agregar(RutaDTO rutaDTO) {
    return null;
  }

  @Override
  public TrasladoDTO buscarXId(Long aLong) throws NoSuchElementException {
    return null;
  }

  @Override
  public TrasladoDTO asignarTraslado(TrasladoDTO trasladoDTO) throws TrasladoNoAsignableException {
    return null;
  }

  @Override
  public List<TrasladoDTO> trasladosDeColaborador(Long id, Integer anio, Integer mes) throws NoSuchElementException{
    Response<List<TrasladoDTO>> execute = null;
    try {
      execute = service.trasladosDeColaborador(id).execute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    if (execute.isSuccessful()) {
      return execute.body();
    }
    if (execute.code() == HttpStatus.NOT_FOUND.getCode()) {
      //throw new NoSuchElementException("no se encontro traslados para el id: " + id);
      return new ArrayList<>();
    }
    throw new RuntimeException("Error conectandose con el componente logistica");
  }

  @Override
  public void setHeladerasProxy(FachadaHeladeras fachadaHeladeras) {

  }

  @Override
  public void setViandasProxy(FachadaViandas fachadaViandas) {

  }

  @Override
  public void trasladoRetirado(Long aLong) {

  }

  @Override
  public void trasladoDepositado(Long aLong) {

  }
}
