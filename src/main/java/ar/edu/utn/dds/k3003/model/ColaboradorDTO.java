package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.model.Contribuciones.ConversorFormasDeColaborar;
import lombok.Data;

import java.util.List;

@Data
public class ColaboradorDTO {
  private Long id;

  private List<FormaDeColaborarEnum> formasDeColaborar;

  private String nombre;

  private Long chatId;
}
