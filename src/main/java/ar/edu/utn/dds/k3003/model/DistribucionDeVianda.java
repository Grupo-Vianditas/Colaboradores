package ar.edu.utn.dds.k3003.model;

import java.time.LocalDateTime;

public class DistribucionDeVianda extends Contribucion{

  public DistribucionDeVianda(LocalDateTime fecha) {
    super(fecha);
    this.descripcion = "Distribucion de viandas";
  }

  public DistribucionDeVianda(Long id, LocalDateTime fecha) {
    super(fecha);
    this.id = id;
    this.descripcion = "Distribucion de viandas";
  }

  private static Double pesoPuntaje;

  public static void setPesoPuntaje(Double nuevoPuntaje) {
    pesoPuntaje = nuevoPuntaje;
  }

  public Double getPuntaje() {
    return pesoPuntaje;
  }
}
