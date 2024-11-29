package ar.edu.utn.dds.k3003.model.eventos.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NotificacionDTO {
  private List<Long> colaboradoresId;

  private String mensaje;
}
