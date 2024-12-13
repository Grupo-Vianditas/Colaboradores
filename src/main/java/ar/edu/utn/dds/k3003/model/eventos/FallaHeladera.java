package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.eventos.DTO.FallaHeladeraDTO;
import ar.edu.utn.dds.k3003.model.notificaciones.DTO.NotificacionDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionFallaHeladeraDTO;
import ar.edu.utn.dds.k3003.repositories.SuscripcionRepository;

import java.util.ArrayList;
import java.util.List;

public class FallaHeladera implements Evento {

  static private FallaHeladera fallaHeladera;
  private SuscripcionRepository suscripcionRepository;

  public static FallaHeladera getFallaHeladera(SuscripcionRepository suscripcionRepository){
    if (fallaHeladera == null) {
      fallaHeladera = new FallaHeladera(suscripcionRepository);
    }
    return fallaHeladera;
  }

  private FallaHeladera(SuscripcionRepository suscripcionRepository) {
    this.suscripcionRepository = suscripcionRepository;
  }

  public void suscribir(Colaborador colaborador, SuscripcionFallaHeladeraDTO suscripcionDTO) {
    SuscripcionFallaHeladera suscripcion = new SuscripcionFallaHeladera(colaborador, suscripcionDTO.getHeladeraId());
    this.suscripcionRepository.save(suscripcion);
  }


  public NotificacionDTO getNotificacion(FallaHeladeraDTO fallaHeladeraDTO) {
    List<Long> chatsANotificar = new ArrayList<>();
    List<SuscripcionFallaHeladera> suscripciones = this.suscripcionRepository.findSuscripcionFallaHeladeraByHeladeraId(fallaHeladeraDTO.getHeladeraId());
    for (SuscripcionFallaHeladera suscripcion : suscripciones) {
      if (suscripcion.getHeladeraId().equals(fallaHeladeraDTO.getHeladeraId())) {
        chatsANotificar.add(suscripcion.getColaborador().getChatId());
      }
    }
    return new NotificacionDTO(chatsANotificar, "Falla en la heladera: " + fallaHeladeraDTO.getHeladeraId());
  }

  @Override
  public void desuscribir(Colaborador colaborador, Long heladeraId) {
    List<SuscripcionFallaHeladera> suscripciones = this.suscripcionRepository.findSuscripcionFallaHeladeraByHeladeraId(heladeraId);
    for (SuscripcionFallaHeladera suscripcion : suscripciones) {
      if (suscripcion.getColaborador().equals(colaborador)) {
        this.suscripcionRepository.delete(suscripcion);
      }
    }
  }
}
