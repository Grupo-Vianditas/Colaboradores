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
@Table(name = "donacion_de_dinero")
public class DonacionDeDinero implements Contribucion{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private Double monto;

  @Column
  private LocalDateTime fecha;

  @ManyToOne
  @JoinColumn(name = "colaborador_id")
  private final Colaborador colaborador;

  public DonacionDeDinero(LocalDateTime fecha, Double monto, Colaborador colaborador) {
    this.fecha = fecha;
    this.monto = monto;
    this.colaborador = colaborador;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public LocalDateTime getFecha() {
    return fecha;
  }

  public String getDescripcion() {
    return "Donacion de pesos";
  }

  public Double getMonto() {
    return monto;
  }
}
