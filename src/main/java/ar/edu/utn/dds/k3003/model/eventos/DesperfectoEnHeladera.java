package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;

public class DesperfectoEnHeladera extends Evento {
  private Integer heladeraId;

  public DesperfectoEnHeladera(Integer heladeraId, String fecha) {
    super("Desperfecto en heladera", fecha);
    this.heladeraId = heladeraId;
  }

  public Integer getHeladeraId() {
    return heladeraId;
  }

  @Override
  public void notificar() {}
}
