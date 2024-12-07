package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;

public class SuscripcionEscasezEnHeladera {
  Colaborador colaborador;

  Long HeladeraId;

  Integer cantidadMinimaDeViandas;

  public SuscripcionEscasezEnHeladera(Colaborador colaborador, Long heladeraId, Integer cantidadMinimaDeViandas) {
    this.colaborador = colaborador;
    HeladeraId = heladeraId;
    this.cantidadMinimaDeViandas = cantidadMinimaDeViandas;
  }

  public Colaborador getColaborador() {
    return colaborador;
  }

  public Long getHeladeraId() {
    return HeladeraId;
  }

  public Integer getCantidadMinimaDeViandas() {
    return cantidadMinimaDeViandas;
  }
}
