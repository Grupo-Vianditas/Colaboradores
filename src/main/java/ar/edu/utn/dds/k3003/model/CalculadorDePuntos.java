package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.model.Contribuciones.DonacionDeDinero;
import java.util.List;

public class CalculadorDePuntos {
  private final Fachada fachada;
  private FachadaLogistica facadeLogistica;
  private FachadaViandas facadeViandas;
  private Double puntajePorActivacionDeHeladera;
  private Double puntajePorReparacionDeHeladera;

  private Double puntajePorDonacionDeVianda;
  private Double puntajePorDistribucionDeVianda;

  private Double puntajePorDonacionDeDinero;
  private Double puntajePorRepartoDeTarjeta;

  public Double calcularPuntosPorActivacionDeHeladera(Long ColaboradorId) {
    return puntajePorActivacionDeHeladera * 0.0;
  }

  public Double calcularPuntosPorReparacionDeHeladera(Long ColaboradorId) {
    return puntajePorReparacionDeHeladera * 0.0;
  }

  public Double calcularPuntosPorDonacionDeVianda(Long colaboradorId) {
    List<ViandaDTO> donacionesDTO = facadeViandas.viandasDeColaborador(colaboradorId,2024,1);

    //DonacionDeViandaMapper mapperDonacion = new DonacionDeViandaMapper();
    //donacionesDTO.stream().map(mapperDonacion::desdeDTO).map(DonacionDeVianda::getPuntaje).reduce(0.0, Double::sum);

    Integer cantidad = donacionesDTO.size();

    return puntajePorDonacionDeVianda * cantidad;
  }

  public Double calcularPuntosPorDistribucionDeVianda(Long colaboradorId) {
    List<TrasladoDTO> distribucionesDTO = facadeLogistica.trasladosDeColaborador(colaboradorId, 1, 2024);

    //DistribucionDeViandaMapper mapperDistribucion = new DistribucionDeViandaMapper();
    //double puntaje = distribucionesDTO.stream().map(mapperDistribucion::desdeDTO).map(DistribucionDeVianda::getPuntaje).reduce(0.0, Double::sum);

    Integer cantidad = distribucionesDTO.size();

    return puntajePorDistribucionDeVianda * cantidad;
  }

  public Double calcularPuntosPorDonacionDeDinero(Long ColaboradorId) {
    Double cantidad = fachada.donacionesDeDineroDelColaborador(ColaboradorId).stream().mapToDouble(DonacionDeDinero::getMonto).sum();
    return puntajePorDonacionDeDinero * cantidad;
  }

  public Double calcularPuntosPorRepartoDeTarjeta(Long ColaboradorId) {
    return puntajePorRepartoDeTarjeta * 0.0;
  }

  public CalculadorDePuntos(Fachada fachada, FachadaLogistica facadeLogistica, FachadaViandas facadeViandas, Double puntajePorActivacionDeHeladera, Double puntajePorReparacionDeHeladera, Double puntajePorDonacionDeVianda, Double puntajePorDistribucionDeVianda, Double puntajePorDonacionDeDinero, Double puntajePorRepartoDeTarjeta) {
    this.fachada = fachada;
    this.facadeLogistica = facadeLogistica;
    this.facadeViandas = facadeViandas;
    this.puntajePorActivacionDeHeladera = puntajePorActivacionDeHeladera;
    this.puntajePorReparacionDeHeladera = puntajePorReparacionDeHeladera;
    this.puntajePorDonacionDeVianda = puntajePorDonacionDeVianda;
    this.puntajePorDistribucionDeVianda = puntajePorDistribucionDeVianda;
    this.puntajePorDonacionDeDinero = puntajePorDonacionDeDinero;
    this.puntajePorRepartoDeTarjeta = puntajePorRepartoDeTarjeta;
  }

  public Double calcularPuntosColaborador(Long colaboradorId) {
    return calcularPuntosPorActivacionDeHeladera(colaboradorId) +
        calcularPuntosPorReparacionDeHeladera(colaboradorId) +
        calcularPuntosPorDonacionDeVianda(colaboradorId) +
        calcularPuntosPorDistribucionDeVianda(colaboradorId) +
        calcularPuntosPorDonacionDeDinero(colaboradorId) +
        calcularPuntosPorRepartoDeTarjeta(colaboradorId);
  }
}
