package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionExcesoEnHeladeraDTO;
import java.util.List;

public class ExesoEnHeladera {
  static ExesoEnHeladera exesoEnHeladera;

  private List<SuscripcionExcesoEnHeladera> suscripciones;
  public static ExesoEnHeladera getExesoEnHeladera() {
    if (exesoEnHeladera == null) {
      exesoEnHeladera = new ExesoEnHeladera();
    }
    return exesoEnHeladera;
  }

  public void suscribir(Colaborador colaborador, SuscripcionExcesoEnHeladeraDTO suscripcionExcesoEnHeladeraDTO) {
    suscripciones.add(new SuscripcionExcesoEnHeladera(colaborador, suscripcionExcesoEnHeladeraDTO.getHeladeraId(), suscripcionExcesoEnHeladeraDTO.getCantidadMinimaDeEspacio()));
  }

  public void desuscribir(Colaborador colaborador) {
    suscripciones.removeIf(suscripcion -> suscripcion.getColaborador().equals(colaborador));
  }
}
