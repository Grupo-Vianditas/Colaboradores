package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import java.util.List;
import java.util.Map;

public abstract class Evento {
  private Long id;
  private final String descripcion;
  private final String fecha;

  private List<Colaborador> suscriptores;

  public Evento(String descripcion, String fecha) {
    this.descripcion = descripcion;
    this.fecha = fecha;
  }

  public Long getId() {
    return id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getFecha() {
    return fecha;
  }

  public void suscribir(Colaborador colaborador, Map<String, String> preferencias) {
    suscriptores.add(colaborador);
  }

  public void desuscribir(Colaborador colaborador) {
    suscriptores.remove(colaborador);
  }

  public abstract void notificar();
}
