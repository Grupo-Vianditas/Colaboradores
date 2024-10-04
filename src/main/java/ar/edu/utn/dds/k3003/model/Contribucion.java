package ar.edu.utn.dds.k3003.model;

import java.time.LocalDateTime;

public abstract class Contribucion {

  protected Long id;
  protected String descripcion;

  private LocalDateTime fecha;

  public Contribucion(LocalDateTime fecha) {
    this.fecha = fecha;
  }

  public abstract Double getPuntaje();

  public LocalDateTime getFecha() {
    return fecha;
  }

  public String getDescripcion() {
    return descripcion;
  }
}
