package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.ColaboradorDTO;
import ar.edu.utn.dds.k3003.model.Colaborador;

public class ColaboradorMapper {
  public ColaboradorDTO map(Colaborador colaborador) {

    ColaboradorDTO colaboradorDTO = new ColaboradorDTO(colaborador.getId(), colaborador.getNombre(), colaborador.getFormasDeColaborar(), colaborador.getChatId());

    colaboradorDTO.setId(colaborador.getId());

    return colaboradorDTO;
  }
}
