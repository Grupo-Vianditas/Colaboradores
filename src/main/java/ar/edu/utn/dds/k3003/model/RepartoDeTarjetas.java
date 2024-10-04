package ar.edu.utn.dds.k3003.model;

import java.time.LocalDateTime;

public class RepartoDeTarjetas extends Contribucion{
  private Double cantidad;
  private static Double pesoPuntaje;

  public static void setPesoPuntaje(Double nuevoPuntaje) {
    pesoPuntaje = nuevoPuntaje;
  }

  public Double getPuntaje() {
    return pesoPuntaje;
  }
  public RepartoDeTarjetas(Double cantidad, LocalDateTime fecha) {
    super(fecha);
    this.cantidad = cantidad;
    this.descripcion = "Reparto de tarjetas";
  }
}
