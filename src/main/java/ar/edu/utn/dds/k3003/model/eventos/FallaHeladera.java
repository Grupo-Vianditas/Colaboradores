package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.eventos.DTO.FallaHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionFallaHeladeraDTO;

import java.util.ArrayList;
import java.util.List;

public class FallaHeladera implements Evento {

  static private FallaHeladera fallaHeladera;
  private List<SuscripcionFallaHeladera> suscripciones;

  public static FallaHeladera getFallaHeladera(){
    if (fallaHeladera == null) {
      fallaHeladera = new FallaHeladera();
    }
    return fallaHeladera;
  }

  public FallaHeladera(){
    this.suscripciones = new ArrayList<>();
  }

  public void suscribir(Colaborador colaborador, SuscripcionFallaHeladeraDTO suscripcionDTO) {
    SuscripcionFallaHeladera suscripcion = new SuscripcionFallaHeladera(colaborador, suscripcionDTO.getHeladeraId());
    this.suscripciones.add(suscripcion);
  }


  public void notificar(FallaHeladeraDTO fallaHeladeraDTO) {
    for (SuscripcionFallaHeladera suscripcion : this.suscripciones) {
      if (suscripcion.getHeladeraId().equals(fallaHeladeraDTO.getHeladeraId())) {
        suscripcion.getColaborador().notificar("Falla en heladera: " + fallaHeladeraDTO.getHeladeraId());
      }
    }
  }

  @Override
  public void desuscribir(Colaborador colaborador) {

  }
}
