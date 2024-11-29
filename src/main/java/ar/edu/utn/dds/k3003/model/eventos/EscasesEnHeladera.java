package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.eventos.DTO.MovimientoDeViandaEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.NotificacionDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionEscasezEnHeladeraDTO;

import java.util.ArrayList;
import java.util.List;

public class EscasesEnHeladera {
  static EscasesEnHeladera escasesEnHeladera;

  private List<SuscripcionEscasezEnHeladera> suscripciones = new ArrayList<>();

  public static EscasesEnHeladera getEscasezEnHeladera() {
    if (escasesEnHeladera == null) {
      escasesEnHeladera = new EscasesEnHeladera();
    }
    return escasesEnHeladera;
  }

  public void suscribir(Colaborador colaborador, SuscripcionEscasezEnHeladeraDTO suscripcionEscasezEnHeladeraDTO) {
    suscripciones.add(new SuscripcionEscasezEnHeladera(colaborador, suscripcionEscasezEnHeladeraDTO.getHeladeraId(), suscripcionEscasezEnHeladeraDTO.getCantidadMinimaDeViandas()));
  }

  public NotificacionDTO notificar(MovimientoDeViandaEnHeladeraDTO movimientoDeViandaEnHeladeraDTO) {
    List<Long> colaboradoresANotificar = new ArrayList<>();
    for (SuscripcionEscasezEnHeladera suscripcion : suscripciones) {
      if (suscripcion.getHeladeraId() == movimientoDeViandaEnHeladeraDTO.getHeladeraId() && suscripcion.getCantidadMinimaDeViandas() > movimientoDeViandaEnHeladeraDTO.getCantidadDeViandas()) {
        suscripcion.getColaborador().notificar(suscripcion.getHeladeraId() + " tiene menos de " + suscripcion.getCantidadMinimaDeViandas() + " viandas");
        colaboradoresANotificar.add(suscripcion.getColaborador().getID());
      }
    }
    return new NotificacionDTO(colaboradoresANotificar, "Escasez de viandas en la heladera: " + movimientoDeViandaEnHeladeraDTO.getHeladeraId());
  }

  public void desuscribir(Colaborador colaborador, Long heladeraId) {
    suscripciones.removeIf(suscripcion -> suscripcion.getColaborador().equals(colaborador) && suscripcion.getHeladeraId().equals(heladeraId));
  }
}
