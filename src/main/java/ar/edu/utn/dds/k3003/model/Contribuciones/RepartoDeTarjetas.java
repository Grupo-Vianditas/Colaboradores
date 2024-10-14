package ar.edu.utn.dds.k3003.model.Contribuciones;

import java.time.LocalDateTime;

public class RepartoDeTarjetas implements Contribucion{
  private LocalDateTime fecha;
  private Long id;
  public RepartoDeTarjetas(LocalDateTime fecha) {
    this.fecha = fecha;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public LocalDateTime getFecha() {
    return fecha;
  }

  @Override
  public String getDescripcion() {
    return "Reparto de tarjetas";
  }
}
