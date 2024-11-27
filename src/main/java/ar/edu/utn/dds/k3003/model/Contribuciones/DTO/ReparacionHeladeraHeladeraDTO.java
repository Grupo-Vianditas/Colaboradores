package ar.edu.utn.dds.k3003.model.Contribuciones.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReparacionHeladeraHeladeraDTO {
  private Long IdHeladera;
  private Long IdColaborador;
  private LocalDateTime fecha;
}
