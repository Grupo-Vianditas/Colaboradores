package ar.edu.utn.dds.k3003.model.Contribuciones.DTO;

import ar.edu.utn.dds.k3003.model.Contribuciones.FormaDeColaborarEnum;
import java.util.List;

public class FormasDTO {
  private List<FormaDeColaborarEnum> formas;

  public List<FormaDeColaborarEnum> getFormas() {
    return formas;
  }

  public void setFormas(List<FormaDeColaborarEnum> formasDeColaborar) {
    this.formas = formasDeColaborar;
  }
}
