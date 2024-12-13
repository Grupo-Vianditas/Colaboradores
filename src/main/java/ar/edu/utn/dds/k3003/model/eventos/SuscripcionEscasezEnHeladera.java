package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn(name = "SuscripcionEscasezEnHeladera")
public class SuscripcionEscasezEnHeladera extends SuscripcionAEventoDeHeladera{
  @Column
  Integer cantidadMinimaDeViandas;

  public SuscripcionEscasezEnHeladera(Colaborador colaborador, Long heladeraId, Integer cantidadMinimaDeViandas) {
    super(colaborador, heladeraId);
    this.cantidadMinimaDeViandas = cantidadMinimaDeViandas;
  }
  public Integer getCantidadMinimaDeViandas() {
    return cantidadMinimaDeViandas;
  }
}
