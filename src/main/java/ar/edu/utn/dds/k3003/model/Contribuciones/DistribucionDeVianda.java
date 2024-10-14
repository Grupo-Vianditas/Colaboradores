package ar.edu.utn.dds.k3003.model.Contribuciones;

import java.time.LocalDateTime;

public class DistribucionDeVianda implements Contribucion{

  private final Long id;
  private final LocalDateTime fecha;

  public DistribucionDeVianda(Long id, LocalDateTime fecha) {
    this.id = id;
    this.fecha = fecha;
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getFecha() {
    return fecha;
  }

  @Override
  public String getDescripcion() {
    return "Distribucion de vianda";
  }
}
