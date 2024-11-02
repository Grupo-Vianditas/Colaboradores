package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "colaborador")
public class Colaborador {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ID;

  @Column
  @Convert(converter = ConversorFormasDeColaborar.class)
  private List<FormaDeColaborarEnum> formasDeColaborar;

  @Column(name = "nombre")
  private String nombre;

  public Colaborador(String nombre, List<FormaDeColaborarEnum> formasDeColaborar) {
    this.nombre = nombre;
    this.formasDeColaborar = formasDeColaborar;
  }

  public Long getID() {
    return ID;
  }

  public String getNombre() {
    return nombre;
  }

  public void setID(Long ID) { this.ID = ID; }

  public List<FormaDeColaborarEnum> getFormasDeColaborar() {
    return formasDeColaborar;
  }

  public void setFormasDeColaborar(List<FormaDeColaborarEnum> formasDeColaborar) {
    this.formasDeColaborar = formasDeColaborar;
  }

  public void notificar(String s) {
    System.out.println(s);
  }
}
