package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.eventos.DTO.MovimientoDeViandaEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.notificaciones.DTO.NotificacionDTO;
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

  public NotificacionDTO getNotificacion(MovimientoDeViandaEnHeladeraDTO movimientoDeViandaEnHeladeraDTO) {
    List<Long> chatsANotificar = new ArrayList<>();
    for (SuscripcionEscasezEnHeladera suscripcion : suscripciones) {
      if (suscripcion.getHeladeraId() == movimientoDeViandaEnHeladeraDTO.getHeladeraId() && suscripcion.getCantidadMinimaDeViandas() > movimientoDeViandaEnHeladeraDTO.getCantidadDeViandas()) {
        suscripcion.getColaborador().notificar(suscripcion.getHeladeraId() + " tiene menos de " + suscripcion.getCantidadMinimaDeViandas() + " viandas");
        chatsANotificar.add(suscripcion.getColaborador().getChatId());
      }
    }
    return new NotificacionDTO(chatsANotificar, "Escasez de viandas en la heladera: " + movimientoDeViandaEnHeladeraDTO.getHeladeraId());
  }

  public void desuscribir(Colaborador colaborador, Long heladeraId) {
    suscripciones.removeIf(suscripcion -> suscripcion.getColaborador().equals(colaborador) && suscripcion.getHeladeraId().equals(heladeraId));
  }
}
