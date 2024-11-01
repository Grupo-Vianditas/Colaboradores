package ar.edu.utn.dds.k3003.model.Contribuciones.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DonacionDeDineroDTO {
  Long IdColaborador;
  Double monto;
  LocalDateTime fecha;

}
