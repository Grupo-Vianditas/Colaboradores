package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.model.DonacionDeVianda;

public class DonacionDeViandaMapper {
  public DonacionDeVianda desdeDTO(ViandaDTO vianda){
    DonacionDeVianda donacionDeVianda = new DonacionDeVianda(vianda.getId() ,vianda.getFechaElaboracion());
    return donacionDeVianda;
  }
}
