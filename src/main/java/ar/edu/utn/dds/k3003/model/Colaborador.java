package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.model.Contribuciones.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.model.Contribuciones.ConversorFormasDeColaborar;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "colaborador")
public class Colaborador {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @Convert(converter = ConversorFormasDeColaborar.class)
  private List<FormaDeColaborarEnum> formasDeColaborar;

  @Column(name = "nombre")
  private String nombre;

  @Column
  private Long chatId;

  public Colaborador(String nombre, List<FormaDeColaborarEnum> formasDeColaborar) {
    this.nombre = nombre;
    this.formasDeColaborar = formasDeColaborar;
  }

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public List<FormaDeColaborarEnum> getFormasDeColaborar() {
    return formasDeColaborar;
  }

  public void setFormasDeColaborar(List<FormaDeColaborarEnum> formasDeColaborar) {
    this.formasDeColaborar = formasDeColaborar;
  }

  public void notificar(String s) {
    System.out.println("NOTIFICACION COLABORADOR " + this.id +": " + s);
  }

  public void setChatId(Long chatId) {
    this.chatId = chatId;
  }

  public Long getChatId() {
    return chatId;
  }
}
