package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.model.Colaborador;
import ar.edu.utn.dds.k3003.model.Contribuciones.DTO.DonacionDeDineroDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public interface FachadaColaboradores {
  ColaboradorDTO agregar(ColaboradorDTO colaboradorDTO);

  ColaboradorDTO buscarXId(Long colaboradorId) throws NoSuchElementException;

  Double puntos(Long colaboradorId) throws NoSuchElementException;

  ColaboradorDTO modificar(Long colaboradorId, List<FormaDeColaborarEnum> formasDeColaborar) throws NoSuchElementException;

  void actualizarPesosPuntos(Double pesosDonados, Double viandasDistribuidas,
                             Double viandasDonadas, Double tarjetasRepartidas, Double heladerasActivas,
                             Double reparacionHeladera);

  void setLogisticaProxy(FachadaLogistica fachadaLogistica);

  void setViandasProxy(FachadaViandas fachadaViandas);

  void borrarTodaLaBase();

  List<Colaborador> buscarListaColaboradores(Integer cantidad, Integer pagina);

  double cantidadDonadores();

  double cantidadTransportadores();

  double cantidadColaboradores();

  void donacionDeDinero(DonacionDeDineroDTO donacionDeDineroDTO);
}
