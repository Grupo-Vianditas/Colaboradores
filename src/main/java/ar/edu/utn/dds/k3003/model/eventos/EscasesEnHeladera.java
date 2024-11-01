package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionEscasezEnHeladeraDTO;
import java.util.List;

public class EscasesEnHeladera {
  static EscasesEnHeladera escasesEnHeladera;

  private List<SuscripcionEscasezEnHeladera> suscripciones;

  public static EscasesEnHeladera getEscasezEnHeladera() {
    if (escasesEnHeladera == null) {
      escasesEnHeladera = new EscasesEnHeladera();
    }
    return escasesEnHeladera;
  }

  public void suscribir(Colaborador colaborador, SuscripcionEscasezEnHeladeraDTO suscripcionEscasezEnHeladeraDTO) {
    suscripciones.add(new SuscripcionEscasezEnHeladera(colaborador, suscripcionEscasezEnHeladeraDTO.getHeladeraId(), suscripcionEscasezEnHeladeraDTO.getEspaciosRestante()));
  }
}
