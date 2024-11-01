package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.model.*;
import ar.edu.utn.dds.k3003.model.Contribuciones.DTO.DonacionDeDineroDTO;
import ar.edu.utn.dds.k3003.model.Contribuciones.DonacionDeDinero;
import ar.edu.utn.dds.k3003.model.eventos.DTO.FallaHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionEscasezEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionExcesoEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionFallaHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.ExesoEnHeladera;
import ar.edu.utn.dds.k3003.model.eventos.EscasesEnHeladera;
import ar.edu.utn.dds.k3003.model.eventos.FallaHeladera;
import ar.edu.utn.dds.k3003.repositories.ColaboradorMapper;
import ar.edu.utn.dds.k3003.repositories.ColaboradorRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

    this.calculadorDePuntos = new CalculadorDePuntos(this, facadeLogistica, facadeViandas, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
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

    return calculadorDePuntos.calcularPuntosColaborador(colaboradorId);
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
    calculadorDePuntos = new CalculadorDePuntos(this, facadeLogistica, facadeViandas,
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
  public void donacionDeDinero(DonacionDeDineroDTO donacionDeDineroDTO) {
    Colaborador colaborador = colaboradorRepository.findById(donacionDeDineroDTO.getIdColaborador());
    DonacionDeDinero donacionDeDinero = new DonacionDeDinero(donacionDeDineroDTO.getFecha(), donacionDeDineroDTO.getMonto(), colaborador);
    entityManager.getTransaction().begin();
    entityManager.persist(donacionDeDinero);
    entityManager.getTransaction().commit();
  }

  public void suscribirseAFallaHeladera(Long colaboradorId, SuscripcionFallaHeladeraDTO suscripcionHeladeraDTO) {
    Colaborador colaborador = colaboradorRepository.findById(colaboradorId);
    FallaHeladera.getFallaHeladera().suscribir(colaborador, suscripcionHeladeraDTO);

  }

  public void notificarFallaHeladera(FallaHeladeraDTO fallaHeladeraDTO) {
    FallaHeladera.getFallaHeladera().notificar(fallaHeladeraDTO);
  }

  public List<DonacionDeDinero> donacionesDeDineroDelColaborador(Long colaboradorId) {
    return entityManager.createQuery("SELECT d FROM DonacionDeDinero d WHERE d.colaborador.id = :id", DonacionDeDinero.class)
        .setParameter("id", colaboradorId)
        .getResultList();
  }

  public void suscribirseAExesoEnHeladera(long id, SuscripcionExcesoEnHeladeraDTO suscripcionExcesoEnHeladeraDTO) {
    Colaborador colaborador = colaboradorRepository.findById(id);
    ExesoEnHeladera.getExesoEnHeladera().suscribir(colaborador, suscripcionExcesoEnHeladeraDTO);
  }

  public void suscribirseAEscacezEnHeladera(long id, SuscripcionEscasezEnHeladeraDTO suscripcionEscasezEnHeladeraDTO) {
    Colaborador colaborador = colaboradorRepository.findById(id);
    EscasesEnHeladera.getEscasezEnHeladera().suscribir(colaborador, suscripcionEscasezEnHeladeraDTO);
  }
}
