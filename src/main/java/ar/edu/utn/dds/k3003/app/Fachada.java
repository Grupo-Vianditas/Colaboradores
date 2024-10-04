package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.model.*;
import ar.edu.utn.dds.k3003.repositories.ColaboradorMapper;
import ar.edu.utn.dds.k3003.repositories.ColaboradorRepository;
import ar.edu.utn.dds.k3003.repositories.DistribucionDeViandaMapper;
import ar.edu.utn.dds.k3003.repositories.DonacionDeViandaMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.NoSuchElementException;

public class Fachada implements ar.edu.utn.dds.k3003.facades.FachadaColaboradores{
  ColaboradorRepository colaboradorRepository;
  ColaboradorMapper colaboradorMapper;
  FachadaLogistica facadeLogistica;
  FachadaViandas facadeViandas;
  private EntityManagerFactory entityManagerFactory;
  private EntityManager entityManager;

  public Fachada(){
    this.entityManagerFactory = Persistence.createEntityManagerFactory("colaboradoresdb");
    this.entityManager = entityManagerFactory.createEntityManager();
    this.colaboradorRepository = new ColaboradorRepository(entityManager);
    this.colaboradorMapper = new ColaboradorMapper();
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

    List<TrasladoDTO> distribucionesDTO = facadeLogistica.trasladosDeColaborador(colaboradorId, 1, 2024);
    List<ViandaDTO> donacionesDTO = facadeViandas.viandasDeColaborador(colaboradorId,2024,1);
    DistribucionDeViandaMapper mapperDistribucion = new DistribucionDeViandaMapper();
    DonacionDeViandaMapper mapperDonacion = new DonacionDeViandaMapper();
    Double puntos = 0D;
    //Mapeo cada viandaDTO a una DonacionDeVianda y después sumo los puntos

    puntos += donacionesDTO.stream().map(mapperDonacion::desdeDTO).map(DonacionDeVianda::getPuntaje).reduce(0.0, Double::sum);
    //Mapeo cada trasladoDTO a una DistribucionDeVianda y después sumo los puntos
    puntos += distribucionesDTO.stream().map(mapperDistribucion::desdeDTO).map(DistribucionDeVianda::getPuntaje).reduce(0.0, Double::sum);

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
                                    Double viandasDonadas, Double tarjetasRepartidas, Double heladerasActivas){
    DonacionDePesos.setPesoPuntaje(pesosDonados);
    DistribucionDeVianda.setPesoPuntaje(viandasDistribuidas);
    DonacionDeVianda.setPesoPuntaje(viandasDonadas);
    RepartoDeTarjetas.setPesoPuntaje(tarjetasRepartidas);
    ActivacionDeHeladera.setPesoPuntaje (heladerasActivas);
  }

  @Override
  public void setLogisticaProxy(FachadaLogistica fachadaLogistica) {
    this.facadeLogistica = fachadaLogistica;
  }

  @Override
  public void setViandasProxy(FachadaViandas fachadaViandas) {
    this.facadeViandas = fachadaViandas;
  }

  public void borrarTodaLaBase() {
    entityManager.getTransaction().begin();
    entityManager.createNativeQuery("TRUNCATE TABLE Colaboradores").executeUpdate();
    entityManager.getTransaction().commit();
  }

  public List<Colaborador> buscarListaColaboradores(Integer cantidad, Integer pagina) {
      return colaboradorRepository.findAll(cantidad, pagina);
  }


  public double cantidadDonadores() {
    return colaboradorRepository.cantidadColaboradoresSegunTipo(FormaDeColaborarEnum.DONADOR);
  }

  public double cantidadTransportadores() {
    return colaboradorRepository.cantidadColaboradoresSegunTipo(FormaDeColaborarEnum.TRANSPORTADOR);
  }

  public double cantidadColaboradores() {
    return colaboradorRepository.cantidadColaboradores();
  }
}
