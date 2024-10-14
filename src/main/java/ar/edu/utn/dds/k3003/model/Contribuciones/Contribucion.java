package ar.edu.utn.dds.k3003.model.Contribuciones;

import java.time.LocalDateTime;

public interface Contribucion {
  public Long getId();

  public LocalDateTime getFecha();

  public abstract String getDescripcion();
}
