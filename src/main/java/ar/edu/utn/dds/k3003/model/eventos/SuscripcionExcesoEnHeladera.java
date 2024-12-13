package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn(name = "SuscripcionExcesoEnHeladera")
public class SuscripcionExcesoEnHeladera extends SuscripcionAEventoDeHeladera {
  @Column
  Integer cantidadMinimaDeEspacio;

  public SuscripcionExcesoEnHeladera(Colaborador colaborador, Long heladeraId, Integer cantidadMinimaDeEspacio) {
    super(colaborador, heladeraId);
    this.cantidadMinimaDeEspacio = cantidadMinimaDeEspacio;
  }

  public Integer getCantidadMinimaDeEspacio() {
    return cantidadMinimaDeEspacio;
  }
}
