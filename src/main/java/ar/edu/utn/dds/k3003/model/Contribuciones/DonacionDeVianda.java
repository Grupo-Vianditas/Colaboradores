package ar.edu.utn.dds.k3003.model.Contribuciones;

import java.time.LocalDateTime;

public class DonacionDeVianda implements Contribucion{
  private Long id;
  private LocalDateTime fecha;

  public DonacionDeVianda(Long id, LocalDateTime fecha) {
    this.fecha = fecha;
    this.id = id;
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
    return "Donacion de vianda";
  }
}
