package ar.edu.utn.dds.k3003.model.Contribuciones;

import java.time.LocalDateTime;

public class ActivacionDeHeladera implements Contribucion{
  private Long id;

  public ActivacionDeHeladera(LocalDateTime fecha, LocalDateTime fechaActivacion, LocalDateTime fechaDesactivacion) {
    this.id = id;
    this.fechaActivacion = fechaActivacion;
    this.fechaDesactivacion = fechaDesactivacion;
  }
  private LocalDateTime fechaActivacion;
  private LocalDateTime fechaDesactivacion;


  public Integer CantidadDeMeses() {
    if(fechaDesactivacion != null){
      return (fechaDesactivacion.getMonthValue() - fechaActivacion.getMonthValue()) + 12*(fechaDesactivacion.getYear() - fechaActivacion.getYear());
    }else{
      return (LocalDateTime.now().getMonthValue() - fechaActivacion.getMonthValue()) + 12*(LocalDateTime.now().getYear() - fechaActivacion.getYear());
    }
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public LocalDateTime getFecha() {
    return fechaActivacion;
  }

  @Override
  public String getDescripcion() {
    return "Activacion de heladera";
  }
}
