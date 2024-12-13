package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.eventos.DTO.MovimientoDeViandaEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.notificaciones.DTO.NotificacionDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionEscasezEnHeladeraDTO;
import ar.edu.utn.dds.k3003.repositories.SuscripcionRepository;

import java.util.ArrayList;
import java.util.List;

public class EscasesEnHeladera {
  static EscasesEnHeladera escasesEnHeladera;

  private SuscripcionRepository suscripcionRepository;

  public static EscasesEnHeladera getEscasezEnHeladera(SuscripcionRepository suscripcionRepository) {
    if (escasesEnHeladera == null) {
      escasesEnHeladera = new EscasesEnHeladera(suscripcionRepository);
    }
    return escasesEnHeladera;
  }

  private EscasesEnHeladera(SuscripcionRepository suscripcionRepository) {
    this.suscripcionRepository = suscripcionRepository;
  }


  public void suscribir(Colaborador colaborador, SuscripcionEscasezEnHeladeraDTO suscripcionEscasezEnHeladeraDTO) {
    suscripcionRepository.save(new SuscripcionEscasezEnHeladera(colaborador, suscripcionEscasezEnHeladeraDTO.getHeladeraId(), suscripcionEscasezEnHeladeraDTO.getCantidadMinimaDeViandas()));
  }

  public NotificacionDTO getNotificacion(MovimientoDeViandaEnHeladeraDTO movimientoDeViandaEnHeladeraDTO) {
    List<Long> chatsANotificar = new ArrayList<>();
    List<SuscripcionEscasezEnHeladera> suscripciones = suscripcionRepository.findSuscripcionEscasezEnHeladeraByHeladeraId(movimientoDeViandaEnHeladeraDTO.getHeladeraId());
    for (SuscripcionEscasezEnHeladera suscripcion : suscripciones) {
      if (suscripcion.getHeladeraId() == movimientoDeViandaEnHeladeraDTO.getHeladeraId() && suscripcion.getCantidadMinimaDeViandas() > movimientoDeViandaEnHeladeraDTO.getCantidadDeViandas()) {
        suscripcion.getColaborador().notificar(suscripcion.getHeladeraId() + " tiene menos de " + suscripcion.getCantidadMinimaDeViandas() + " viandas");
        chatsANotificar.add(suscripcion.getColaborador().getChatId());
      }
    }
    return new NotificacionDTO(chatsANotificar, "Escasez de viandas en la heladera: " + movimientoDeViandaEnHeladeraDTO.getHeladeraId());
  }

  public void desuscribir(Colaborador colaborador, Long heladeraId) {
    List<SuscripcionEscasezEnHeladera> suscripciones = suscripcionRepository.findSuscripcionEscasezEnHeladeraByHeladeraId(heladeraId);
    for (SuscripcionEscasezEnHeladera suscripcion : suscripciones) {
      if (suscripcion.getColaborador().equals(colaborador)) {
        suscripcionRepository.delete(suscripcion);
      }
    }
  }
}
