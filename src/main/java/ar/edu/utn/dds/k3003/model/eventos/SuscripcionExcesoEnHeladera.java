package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;

public class SuscripcionExcesoEnHeladera {
  Colaborador colaborador;

  Long HeladeraId;

  Integer cantidadMinimaDeEspacio;

  public SuscripcionExcesoEnHeladera(Colaborador colaborador, Long heladeraId, Integer cantidadMinimaDeEspacio) {
    this.colaborador = colaborador;
    HeladeraId = heladeraId;
    this.cantidadMinimaDeEspacio = cantidadMinimaDeEspacio;
  }

  public Object getColaborador() {
    return colaborador;
  }
}
