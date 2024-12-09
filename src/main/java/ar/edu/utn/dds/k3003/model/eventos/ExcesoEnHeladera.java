package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.eventos.DTO.MovimientoDeViandaEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.notificaciones.DTO.NotificacionDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionExcesoEnHeladeraDTO;

import java.util.ArrayList;
import java.util.List;

public class ExcesoEnHeladera {
  static ExcesoEnHeladera excesoEnHeladera;

  private List<SuscripcionExcesoEnHeladera> suscripciones = new ArrayList<>();
  public static ExcesoEnHeladera getExcesoEnHeladera() {
    if (excesoEnHeladera == null) {
      excesoEnHeladera = new ExcesoEnHeladera();
    }
    return excesoEnHeladera;
  }

  public void suscribir(Colaborador colaborador, SuscripcionExcesoEnHeladeraDTO suscripcionExcesoEnHeladeraDTO) {
    suscripciones.add(new SuscripcionExcesoEnHeladera(colaborador, suscripcionExcesoEnHeladeraDTO.getHeladeraId(), suscripcionExcesoEnHeladeraDTO.getCantidadMinimaDeEspacio()));
  }

  public void desuscribir(Colaborador colaborador, Long heladeraId) {
    suscripciones.removeIf(suscripcion -> suscripcion.getColaborador().equals(colaborador) && suscripcion.getHeladeraId().equals(heladeraId));
  }

  public NotificacionDTO getNotificacion(MovimientoDeViandaEnHeladeraDTO movimientoDeViandaEnHeladeraDTO) {
    List<Long> chatsANotificar = new ArrayList<>();
    for (SuscripcionExcesoEnHeladera suscripcion : suscripciones) {
      if (suscripcion.getHeladeraId() == movimientoDeViandaEnHeladeraDTO.getHeladeraId() && suscripcion.getCantidadMinimaDeEspacio() < movimientoDeViandaEnHeladeraDTO.getCapacidadMaxima()) {
        chatsANotificar.add(suscripcion.getColaborador().getChatId());
      }
    }
    return new NotificacionDTO(chatsANotificar, "Exceso de viandas en la heladera: " + movimientoDeViandaEnHeladeraDTO.getHeladeraId());
  }

}
