package ar.edu.utn.dds.k3003.model.eventos;

import ar.edu.utn.dds.k3003.model.Colaborador;
import java.util.List;
import java.util.Map;

public interface Evento {
  public void desuscribir(Colaborador colaborador);
}
