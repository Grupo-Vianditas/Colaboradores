package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.eventos.DTO.FallaHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.MovimientoDeViandaEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionExcesoEnHeladeraDTO;

import java.util.ArrayList;
import java.util.List;

public class ExesoEnHeladera {
  static ExesoEnHeladera exesoEnHeladera;

  private List<SuscripcionExcesoEnHeladera> suscripciones = new ArrayList<>();
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

  public void notificar(MovimientoDeViandaEnHeladeraDTO movimientoDeViandaEnHeladeraDTO) {
    for (SuscripcionExcesoEnHeladera suscripcion : suscripciones) {
      if (suscripcion.getHeladeraId() == movimientoDeViandaEnHeladeraDTO.getHeladeraId() && suscripcion.getCantidadMinimaDeEspacio() < movimientoDeViandaEnHeladeraDTO.getCapacidadMaxima()) {
        suscripcion.getColaborador().notificar(suscripcion.getHeladeraId() + " tiene mas de " + suscripcion.getCantidadMinimaDeEspacio() + " espacio");
      }
    }
  }
}
