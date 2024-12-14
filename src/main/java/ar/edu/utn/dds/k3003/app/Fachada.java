package ar.edu.utn.dds.k3003.app;

import ar.edu.utn.dds.k3003.client.BotTelegramProxy;
import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.model.ColaboradorDTO;
import ar.edu.utn.dds.k3003.model.Contribuciones.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.model.*;
import ar.edu.utn.dds.k3003.model.Contribuciones.CalculadorDePuntos;
import ar.edu.utn.dds.k3003.model.Contribuciones.DTO.DonacionDeDineroDTO;
import ar.edu.utn.dds.k3003.model.Contribuciones.DonacionDeDinero;
import ar.edu.utn.dds.k3003.model.Contribuciones.ReparacionDeHeladera;
import ar.edu.utn.dds.k3003.model.eventos.DTO.FallaHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.MovimientoDeViandaEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.Contribuciones.DTO.ReparacionHeladeraHeladeraDTO;
import ar.edu.utn.dds.k3003.model.notificaciones.DTO.ChatDTO;
import ar.edu.utn.dds.k3003.model.notificaciones.DTO.NotificacionDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionEscasezEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionExcesoEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionFallaHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.ExcesoEnHeladera;
import ar.edu.utn.dds.k3003.model.eventos.EscasesEnHeladera;
import ar.edu.utn.dds.k3003.model.eventos.FallaHeladera;
import ar.edu.utn.dds.k3003.repositories.ColaboradorMapper;
import ar.edu.utn.dds.k3003.repositories.ColaboradorRepository;
import ar.edu.utn.dds.k3003.repositories.SuscripcionRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.NoSuchElementException;

public class Fachada implements FachadaColaboradores {
  ColaboradorRepository colaboradorRepository;

  SuscripcionRepository suscripcionRepository;
  ColaboradorMapper colaboradorMapper;
  FachadaLogistica facadeLogistica;
  FachadaViandas facadeViandas;
  CalculadorDePuntos calculadorDePuntos;
  private EntityManagerFactory entityManagerFactory;
  private EntityManager entityManager;
  private BotTelegramProxy botTelegramProxy;

  public Fachada(){
    this.entityManagerFactory = Persistence.createEntityManagerFactory("colaboradoresdb");
    this.entityManager = entityManagerFactory.createEntityManager();
    this.colaboradorRepository = new ColaboradorRepository(entityManager);
    this.suscripcionRepository = new SuscripcionRepository(entityManager);
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

  public void setBotTelegramProxy(BotTelegramProxy botTelegramProxy) { this.botTelegramProxy = botTelegramProxy; }

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
    FallaHeladera.getFallaHeladera(suscripcionRepository).suscribir(colaborador, suscripcionHeladeraDTO);
  }

  public void notificarFallaHeladera(FallaHeladeraDTO fallaHeladeraDTO) {
    NotificacionDTO notificacionDTO = FallaHeladera.getFallaHeladera(suscripcionRepository).getNotificacion(fallaHeladeraDTO);
    botTelegramProxy.notificarEvento(notificacionDTO);
  }

  public List<DonacionDeDinero> donacionesDeDineroDelColaborador(Long colaboradorId) {
    return entityManager.createQuery("SELECT d FROM DonacionDeDinero d WHERE d.colaborador.id = :id", DonacionDeDinero.class)
        .setParameter("id", colaboradorId)
        .getResultList();
  }

  public void suscribirseAExcesoEnHeladera(long id, SuscripcionExcesoEnHeladeraDTO suscripcionExcesoEnHeladeraDTO) {
    Colaborador colaborador = colaboradorRepository.findById(id);
    ExcesoEnHeladera.getExcesoEnHeladera(suscripcionRepository).suscribir(colaborador, suscripcionExcesoEnHeladeraDTO);
  }

  public void suscribirseAEscacezEnHeladera(long id, SuscripcionEscasezEnHeladeraDTO suscripcionEscasezEnHeladeraDTO) {
    Colaborador colaborador = colaboradorRepository.findById(id);
    EscasesEnHeladera.getEscasezEnHeladera(suscripcionRepository).suscribir(colaborador, suscripcionEscasezEnHeladeraDTO);
  }

  public void notificarMovimientoDeViandaEnHeladera(MovimientoDeViandaEnHeladeraDTO movimientoDeViandaEnHeladeraDTO) {
    NotificacionDTO notificacionEscasesDTO = EscasesEnHeladera.getEscasezEnHeladera(suscripcionRepository).getNotificacion(movimientoDeViandaEnHeladeraDTO);
    NotificacionDTO notificacionExcesoDTO = ExcesoEnHeladera.getExcesoEnHeladera(suscripcionRepository).getNotificacion(movimientoDeViandaEnHeladeraDTO);
    botTelegramProxy.notificarEvento(notificacionEscasesDTO);
    botTelegramProxy.notificarEvento(notificacionExcesoDTO);
  }

  public void agregarReparacionHeladera(ReparacionHeladeraHeladeraDTO reparacionHeladeraHeladeraDTO) {
    Colaborador colaborador = colaboradorRepository.findById(reparacionHeladeraHeladeraDTO.getIdColaborador());


    if (!colaborador.getFormasDeColaborar().contains(FormaDeColaborarEnum.TECNICO)){
      throw new IllegalArgumentException("El colaborador " + colaborador.getId() + " no es técnico");
    }

    ReparacionDeHeladera reparacionHeladera = new ReparacionDeHeladera(reparacionHeladeraHeladeraDTO.getFecha(), reparacionHeladeraHeladeraDTO.getIdHeladera(), colaborador);
    entityManager.getTransaction().begin();
    entityManager.persist(reparacionHeladera);
    entityManager.getTransaction().commit();
  }

  public List<ReparacionDeHeladera> reparacionesDeHeladeraDelColaborador(Long colaboradorId) {
    return entityManager.createQuery("SELECT r FROM ReparacionDeHeladera r WHERE r.colaborador.id = :id", ReparacionDeHeladera.class)
        .setParameter("id", colaboradorId)
        .getResultList();
  }

  public void desuscribirseAFallaHeladera(long colaboradorId, Long heladeraId) {
    Colaborador colaborador = colaboradorRepository.findById(colaboradorId);
    FallaHeladera.getFallaHeladera(suscripcionRepository).desuscribir(colaborador, heladeraId);
  }

  public void desuscribirseAEscasezEnHeladera(long colaboradorId, Long heladeraId) {
    Colaborador colaborador = colaboradorRepository.findById(colaboradorId);
    EscasesEnHeladera.getEscasezEnHeladera(suscripcionRepository).desuscribir(colaborador, heladeraId);
  }

  public void desuscribirseAExcesoEnHeladera(long colaboradorId, Long heladeraId) {
    Colaborador colaborador = colaboradorRepository.findById(colaboradorId);
    ExcesoEnHeladera.getExcesoEnHeladera(suscripcionRepository).desuscribir(colaborador, heladeraId);
  }

  public void registrarChat(ChatDTO chatDTO) {
    Colaborador colaborador = colaboradorRepository.findById(chatDTO.getColaboradorId());
    colaborador.setChatId(chatDTO.getChatId());
    entityManager.getTransaction().begin();
    entityManager.persist(colaborador);
    entityManager.getTransaction().commit();
  }

  public Long chatDelColaborador(Long colaboradorId) {
    Colaborador colaborador = colaboradorRepository.findById(colaboradorId);
    if (colaborador.getChatId() != null) {
      return colaborador.getChatId();
    } else {
      throw new NoSuchElementException("El colaborador " + colaboradorId + " no tiene chat asociado");
    }
  }

  public ColaboradorDTO colaboradorDelChat(Long chatId) {
    Colaborador colaborador = colaboradorRepository.findByChatId(chatId);
    ColaboradorDTO colaboradorDTO = new ColaboradorDTO(colaborador.getChatId(), colaborador.getNombre(), colaborador.getFormasDeColaborar(), colaborador.getId());
    colaboradorDTO.setId(colaborador.getId());
    return colaboradorDTO;
  }
}
