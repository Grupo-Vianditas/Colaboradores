package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn(name = "SuscripcionFallaHeladera")
public class SuscripcionFallaHeladera extends SuscripcionAEventoDeHeladera {
  public SuscripcionFallaHeladera(Colaborador colaborador, Long heladeraId) {
    super(colaborador, heladeraId);
  }
}
