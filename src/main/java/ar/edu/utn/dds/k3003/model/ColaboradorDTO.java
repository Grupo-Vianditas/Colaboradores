package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.model.Contribuciones.FormaDeColaborarEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ColaboradorDTO {
  private Long id;

  private String nombre;

  private List<FormaDeColaborarEnum> formas;

  private Long chatId;

  public ColaboradorDTO() {
  }
}
