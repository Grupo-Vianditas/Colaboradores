package ar.edu.utn.dds.k3003.model;

import java.time.LocalDateTime;

public class ActivacionDeHeladera extends Contribucion{
  private static Double pesoPuntaje;

  public static void setPesoPuntaje(Double nuevoPuntaje) {
    pesoPuntaje = nuevoPuntaje;
  }

  public ActivacionDeHeladera(LocalDateTime fecha, LocalDateTime fechaActivacion, LocalDateTime fechaDesactivacion) {
    super(fecha);
    this.descripcion = "Activacion de heladera";
    this.fechaActivacion = fechaActivacion;
    this.fechaDesactivacion = fechaDesactivacion;
  }
  private LocalDateTime fechaActivacion;
  private LocalDateTime fechaDesactivacion;

  @Override
  public Double getPuntaje() {
    //Puntaje = pesoPuntaje * (cantidad de tiempo que estuvo activa expresado en meses)
    if(fechaDesactivacion != null){
      return pesoPuntaje * (fechaDesactivacion.getMonthValue() - fechaActivacion.getMonthValue()) + 12*(fechaDesactivacion.getYear() - fechaActivacion.getYear());
    }else{
      return pesoPuntaje * (LocalDateTime.now().getMonthValue() - fechaActivacion.getMonthValue()) + 12*(LocalDateTime.now().getYear() - fechaActivacion.getYear());
    }
  }
}
