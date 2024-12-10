package ar.edu.utn.dds.k3003.model.notificaciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ChatDTO {
  private Long chatId;
  private Long colaboradorId;

  public ChatDTO(Long chatId, Long colaboradorId) {
    this.chatId = chatId;
    this.colaboradorId = colaboradorId;
  }
}
