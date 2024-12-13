package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.eventos.DTO.MovimientoDeViandaEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.notificaciones.DTO.NotificacionDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionExcesoEnHeladeraDTO;
import ar.edu.utn.dds.k3003.repositories.SuscripcionRepository;

import java.util.ArrayList;
import java.util.List;

public class ExcesoEnHeladera {
  static ExcesoEnHeladera excesoEnHeladera;

  private SuscripcionRepository suscripcionRepository;
  public static ExcesoEnHeladera getExcesoEnHeladera(SuscripcionRepository suscripcionRepository) {
    if (excesoEnHeladera == null) {
      excesoEnHeladera = new ExcesoEnHeladera(suscripcionRepository);
    }
    return excesoEnHeladera;
  }

  private ExcesoEnHeladera(SuscripcionRepository suscripcionRepository) {
    this.suscripcionRepository = suscripcionRepository;
  }

  public void suscribir(Colaborador colaborador, SuscripcionExcesoEnHeladeraDTO suscripcionExcesoEnHeladeraDTO) {
    suscripcionRepository.save(new SuscripcionExcesoEnHeladera(colaborador, suscripcionExcesoEnHeladeraDTO.getHeladeraId(), suscripcionExcesoEnHeladeraDTO.getCantidadMinimaDeEspacio()));
  }

  public void desuscribir(Colaborador colaborador, Long heladeraId) {
    List<SuscripcionExcesoEnHeladera> suscripciones = suscripcionRepository.findSuscripcionExcesoEnHeladeraByHeladeraId(heladeraId);
    for (SuscripcionExcesoEnHeladera suscripcion : suscripciones) {
      if (suscripcion.getColaborador().getChatId() == colaborador.getChatId()) {
        suscripcionRepository.delete(suscripcion);
      }
    }

  }

  public NotificacionDTO getNotificacion(MovimientoDeViandaEnHeladeraDTO movimientoDeViandaEnHeladeraDTO) {
    List<Long> chatsANotificar = new ArrayList<>();
    List<SuscripcionExcesoEnHeladera> suscripciones = suscripcionRepository.findSuscripcionExcesoEnHeladeraByHeladeraId(movimientoDeViandaEnHeladeraDTO.getHeladeraId());
    for (SuscripcionExcesoEnHeladera suscripcion : suscripciones) {
      if (suscripcion.getHeladeraId() == movimientoDeViandaEnHeladeraDTO.getHeladeraId() && suscripcion.getCantidadMinimaDeEspacio() < movimientoDeViandaEnHeladeraDTO.getCapacidadMaxima()) {
        chatsANotificar.add(suscripcion.getColaborador().getChatId());
      }
    }
    return new NotificacionDTO(chatsANotificar, "Exceso de viandas en la heladera: " + movimientoDeViandaEnHeladeraDTO.getHeladeraId());
  }

}
