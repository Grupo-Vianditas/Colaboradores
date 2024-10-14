package ar.edu.utn.dds.k3003.model.Contribuciones;

import ar.edu.utn.dds.k3003.model.Colaborador;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ReparacionDeHeladera")
public class ReparacionDeHeladera implements Contribucion {

  @ManyToOne
  @JoinColumn(name = "colaborador_id")
  private final Colaborador colaborador;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private final LocalDateTime fecha;

  @Column
  private final Integer heladeraId;

  public ReparacionDeHeladera(LocalDateTime fecha, Integer heladeraId, Colaborador colaborador) {
    this.fecha = fecha;
    this.colaborador = colaborador;
    this.heladeraId = heladeraId;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public LocalDateTime getFecha() {
    return fecha;
  }

  @Override
  public String getDescripcion() {
    return "Reparacion de heladera";
  }
}
