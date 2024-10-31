package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;

public class SuscripcionFallaHeladera {
  private Long heladeraId;

  private Colaborador colaborador;

  public SuscripcionFallaHeladera(Colaborador colaborador, Long heladeraId) {
    this.colaborador = colaborador;
    this.heladeraId = heladeraId;
  }

  public Long getHeladeraId() {
    return heladeraId;
  }

  public Colaborador getColaborador() {
    return colaborador;
  }
}
