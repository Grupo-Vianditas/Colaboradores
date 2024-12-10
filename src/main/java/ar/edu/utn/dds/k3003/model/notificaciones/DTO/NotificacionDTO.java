package ar.edu.utn.dds.k3003.model.notificaciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NotificacionDTO {
  private List<Long> chatIds;

  private String mensaje;
}
