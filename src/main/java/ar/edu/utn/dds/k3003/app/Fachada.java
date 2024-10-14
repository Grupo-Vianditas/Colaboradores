package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.model.*;
import ar.edu.utn.dds.k3003.model.Contribuciones.ActivacionDeHeladera;
import ar.edu.utn.dds.k3003.model.Contribuciones.DistribucionDeVianda;
import ar.edu.utn.dds.k3003.model.Contribuciones.DonacionDeDinero;
import ar.edu.utn.dds.k3003.model.Contribuciones.DonacionDeVianda;
import ar.edu.utn.dds.k3003.model.Contribuciones.RepartoDeTarjetas;
import ar.edu.utn.dds.k3003.repositories.ColaboradorMapper;
import ar.edu.utn.dds.k3003.repositories.ColaboradorRepository;
import ar.edu.utn.dds.k3003.repositories.DistribucionDeViandaMapper;
import ar.edu.utn.dds.k3003.repositories.DonacionDeViandaMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public class Fachada implements FachadaColaboradores {
  ColaboradorRepository colaboradorRepository;
  ColaboradorMapper colaboradorMapper;
  FachadaLogistica facadeLogistica;
  FachadaViandas facadeViandas;
  CalculadorDePuntos calculadorDePuntos;
  private EntityManagerFactory entityManagerFactory;
  private EntityManager entityManager;

  public Fachada(){
    this.entityManagerFactory = Persistence.createEntityManagerFactory("colaboradoresdb");
    this.entityManager = entityManagerFactory.createEntityManager();
    this.colaboradorRepository = new ColaboradorRepository(entityManager);
    this.colaboradorMapper = new ColaboradorMapper();

    this.calculadorDePuntos = new CalculadorDePuntos(facadeLogistica, facadeViandas, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
  }

  @Override
  public ColaboradorDTO agregar(ColaboradorDTO colaboradorDTO) {
    Colaborador colaborador = new Colaborador(colaboradorDTO.getNombre(), colaboradorDTO.getFormas());
    colaboradorRepository.save(colaborador);
    return colaboradorMapper.map(colaborador);
  }

  @Override
  public ColaboradorDTO buscarXId(Long colaboradorId) throws NoSuchElementException {
    try {
      Colaborador colaborador = colaboradorRepository.findById(colaboradorId);
      return colaboradorMapper.map(colaborador);
    }
    catch (NoSuchElementException e) {
      throw new NoSuchElementException("Colaborador " + colaboradorId + " no encontrado");
    }
  }

  @Override
  public Double puntos(Long colaboradorId) throws NoSuchElementException {
    Colaborador colaborador = colaboradorRepository.findById(colaboradorId);

    Double puntos = calculadorDePuntos.calcularPuntosColaborador(colaboradorId);
    return puntos;
  }

  @Override
  public ColaboradorDTO modificar(Long colaboradorId, List<FormaDeColaborarEnum> formasDeColaborar) throws NoSuchElementException {
    Colaborador colaborador = colaboradorRepository.findById(colaboradorId);
    colaborador.setFormasDeColaborar(formasDeColaborar);
    return colaboradorMapper.map(colaborador);
  }

  @Override
  public void actualizarPesosPuntos(Double pesosDonados, Double viandasDistribuidas,
                                    Double viandasDonadas, Double tarjetasRepartidas,
                                    Double heladerasActivas, Double reparacionHeladera) {
    calculadorDePuntos = new CalculadorDePuntos(facadeLogistica, facadeViandas,
        heladerasActivas, reparacionHeladera,
        viandasDonadas, viandasDistribuidas,
        pesosDonados, tarjetasRepartidas);
  }

  @Override
  public void setLogisticaProxy(FachadaLogistica fachadaLogistica) {
    this.facadeLogistica = fachadaLogistica;
  }

  @Override
  public void setViandasProxy(FachadaViandas fachadaViandas) {
    this.facadeViandas = fachadaViandas;
  }

  @Override
  public void borrarTodaLaBase() {
    entityManager.getTransaction().begin();
    entityManager.createNativeQuery("TRUNCATE TABLE Colaboradores").executeUpdate();
    entityManager.getTransaction().commit();
  }

  @Override
  public List<Colaborador> buscarListaColaboradores(Integer cantidad, Integer pagina) {
      return colaboradorRepository.findAll(cantidad, pagina);
  }


  @Override
  public double cantidadDonadores() {
    return colaboradorRepository.cantidadColaboradoresSegunTipo(FormaDeColaborarEnum.DONADOR);
  }

  @Override
  public double cantidadTransportadores() {
    return colaboradorRepository.cantidadColaboradoresSegunTipo(FormaDeColaborarEnum.TRANSPORTADOR);
  }

  @Override
  public double cantidadColaboradores() {
    return colaboradorRepository.cantidadColaboradores();
  }

  @Override
  public void donacionDeDinero(Object idColaborador, Double monto, LocalDateTime fecha) {
    Colaborador colaborador = colaboradorRepository.findById((Long) idColaborador);
    DonacionDeDinero donacionDeDinero = new DonacionDeDinero(fecha, monto, colaborador);
    entityManager.getTransaction().begin();
    entityManager.persist(donacionDeDinero);
    entityManager.getTransaction().commit();
  }
}
