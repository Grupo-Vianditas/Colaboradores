package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.Contribuciones.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.model.Colaborador;

import javax.persistence.EntityManager;
import java.util.*;

public class ColaboradorRepository {
  private final EntityManager entityManager;

  public ColaboradorRepository(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void save(Colaborador colaborador) {
    if(Objects.isNull(colaborador.getId())) {
      entityManager.getTransaction().begin();
      entityManager.persist(colaborador);
      entityManager.getTransaction().commit();
    }
  }

  public Colaborador findById(Long id) {
    Optional<Colaborador> first = Optional.ofNullable(entityManager.find(Colaborador.class, id));
    return first.orElseThrow(() -> new NoSuchElementException("No se encontro el colaborador con el id: " + id));
  }

  public List<Colaborador> findAll(Integer cantidad, Integer pagina) {
    return entityManager.createQuery("SELECT c FROM Colaborador c", Colaborador.class)
        .setFirstResult((pagina) * cantidad)
        .setMaxResults(cantidad)
        .getResultList();
  }

  public Integer cantidadColaboradoresSegunTipo(FormaDeColaborarEnum forma) {
    //TODO Esta solucion es una mierda, me gustaria que el count(*) y el filtrado se hagan en la base de datos
//    return entityManager.createQuery("SELECT c FROM Colaborador c", Colaborador.class)
//       .getResultList()
//        .stream()
//        .filter(colaborador -> colaborador.getFormasDeColaborar().contains(forma)).toList().size();
    return entityManager.createQuery("SELECT COUNT(c) FROM Colaborador c WHERE :forma MEMBER OF c.formasDeColaborar", Long.class)
        .setParameter("forma", forma)
        .getSingleResult().intValue();
  }

  public double cantidadColaboradores() {
    return entityManager.createQuery("SELECT COUNT(c) FROM Colaborador c", Long.class).getSingleResult();
  }

  public Colaborador findByChatId(Long chatId) {
    return entityManager.createQuery("SELECT c FROM Colaborador c WHERE c.chatId = :chatId", Colaborador.class)
        .setParameter("chatId", chatId)
        .getSingleResult();
  }
}
