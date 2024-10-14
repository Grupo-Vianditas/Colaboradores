package ar.edu.utn.dds.k3003.model.Contribuciones.DTO;

import java.time.LocalDateTime;

public class DonacionDeDineroDTO {
  Long colaboradorId;
  Double monto;
  LocalDateTime fecha;

  public Object getIdColaborador() {
    return colaboradorId;
  }

  public void setIdColaborador(Long colaboradorId) {
    this.colaboradorId = colaboradorId;
  }

  public Double getMonto() {
    return monto;
  }

  public void setMonto(Double monto) {
    this.monto = monto;
  }

  public LocalDateTime getFecha() {
    return fecha;
  }

  public void setFecha(LocalDateTime fecha) {
    this.fecha = fecha;
  }
}
