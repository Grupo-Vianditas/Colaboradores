package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_suscripcion", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class SuscripcionAEventoDeHeladera {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private Long heladeraId;

  @OneToOne
  @JoinColumn(name = "colaborador_id")
  private Colaborador colaborador;

  public SuscripcionAEventoDeHeladera(Colaborador colaborador, Long heladeraId) {
    this.colaborador = colaborador;
    this.heladeraId = heladeraId;
  }
}
