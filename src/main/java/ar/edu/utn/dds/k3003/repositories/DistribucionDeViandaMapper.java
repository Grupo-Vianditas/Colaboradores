package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.model.DistribucionDeVianda;

public class DistribucionDeViandaMapper {
  public DistribucionDeVianda desdeDTO(TrasladoDTO traslado){
    DistribucionDeVianda distribucionDeViandas = new DistribucionDeVianda(traslado.getId() ,traslado.getFechaTraslado());
    return distribucionDeViandas;
  }
}
