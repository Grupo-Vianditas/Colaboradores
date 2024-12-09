package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.eventos.DTO.FallaHeladeraDTO;
import ar.edu.utn.dds.k3003.model.notificaciones.DTO.NotificacionDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionFallaHeladeraDTO;

import java.util.ArrayList;
import java.util.List;

public class FallaHeladera implements Evento {

  static private FallaHeladera fallaHeladera;
  private List<SuscripcionFallaHeladera> suscripciones = new ArrayList<>();

  public static FallaHeladera getFallaHeladera(){
    if (fallaHeladera == null) {
      fallaHeladera = new FallaHeladera();
    }
    return fallaHeladera;
  }

  public void suscribir(Colaborador colaborador, SuscripcionFallaHeladeraDTO suscripcionDTO) {
    SuscripcionFallaHeladera suscripcion = new SuscripcionFallaHeladera(colaborador, suscripcionDTO.getHeladeraId());
    this.suscripciones.add(suscripcion);
  }


  public NotificacionDTO getNotificacion(FallaHeladeraDTO fallaHeladeraDTO) {
    List<Long> chatsANotificar = new ArrayList<>();
    for (SuscripcionFallaHeladera suscripcion : this.suscripciones) {
      if (suscripcion.getHeladeraId().equals(fallaHeladeraDTO.getHeladeraId())) {
        chatsANotificar.add(suscripcion.getColaborador().getChatId());
      }
    }
    return new NotificacionDTO(chatsANotificar, "Falla en la heladera: " + fallaHeladeraDTO.getHeladeraId());
  }

  @Override
  public void desuscribir(Colaborador colaborador, Long heladeraId) {
    this.suscripciones.removeIf(suscripcion -> suscripcion.getColaborador().equals(colaborador) && suscripcion.getHeladeraId().equals(heladeraId));
  }
}
