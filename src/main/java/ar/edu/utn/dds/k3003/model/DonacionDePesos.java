package ar.edu.utn.dds.k3003.model;

import java.time.LocalDateTime;

public class DonacionDePesos extends Contribucion{
  private Double cantidad;

  public DonacionDePesos(LocalDateTime fecha, Double cantidad) {
    super(fecha);
    this.descripcion = "Donacion de pesos";
    this.cantidad = cantidad;
  }
  private static Double pesoPuntaje;

  public static void setPesoPuntaje(Double nuevoPuntaje) {
    pesoPuntaje = nuevoPuntaje;
  }

  @Override
  public Double getPuntaje() {
    return pesoPuntaje * cantidad;
  }
}
