package ar.edu.utn.dds.k3003.model.Contribuciones.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReparacionHeladeraHeladeraDTO {
  private Integer IdHeladera;
  private Integer IdColaborador;
  private LocalDateTime fecha;
}
