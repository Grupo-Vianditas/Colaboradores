package ar.edu.utn.dds.k3003.model.Contribuciones.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DonacionDeDineroDTO {
  private Long IdColaborador;
  private Double monto;
  private LocalDateTime fecha;
}
