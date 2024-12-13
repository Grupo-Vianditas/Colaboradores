package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.eventos.SuscripcionAEventoDeHeladera;
import ar.edu.utn.dds.k3003.model.eventos.SuscripcionEscasezEnHeladera;
import ar.edu.utn.dds.k3003.model.eventos.SuscripcionExcesoEnHeladera;
import ar.edu.utn.dds.k3003.model.eventos.SuscripcionFallaHeladera;
import javax.persistence.EntityManager;
import java.util.List;

public class SuscripcionRepository {
  private final EntityManager entityManager;

  public SuscripcionRepository(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void save(SuscripcionAEventoDeHeladera suscripcion) {
    if(suscripcion.getId() == null){
      entityManager.getTransaction().begin();
      entityManager.persist(suscripcion);
      entityManager.getTransaction().commit();
    }else {
      update(suscripcion);
    }
  }

  public SuscripcionAEventoDeHeladera findById(Long id) {
    return entityManager.find(SuscripcionAEventoDeHeladera.class, id);
  }

  public void delete(SuscripcionAEventoDeHeladera suscripcionABorrar) {
    entityManager.getTransaction().begin();
    SuscripcionAEventoDeHeladera suscripcion = entityManager.find(SuscripcionAEventoDeHeladera.class, suscripcionABorrar.getId());
    if (suscripcion != null) {
      entityManager.remove(suscripcion);
    }
    entityManager.getTransaction().commit();
  }

  public void update(SuscripcionAEventoDeHeladera suscripcion) {
    entityManager.getTransaction().begin();
    entityManager.merge(suscripcion);
    entityManager.getTransaction().commit();
  }

  public List<SuscripcionFallaHeladera> findSuscripcionFallaHeladeraByHeladeraId(Long heladeraId) {
    return entityManager.createQuery("SELECT s FROM SuscripcionFallaHeladera s WHERE s.heladeraId = :heladeraId", SuscripcionFallaHeladera.class)
        .setParameter("heladeraId", heladeraId)
        .getResultList();
  }

  public List<SuscripcionEscasezEnHeladera> findSuscripcionEscasezEnHeladeraByHeladeraId(Long heladeraId) {
    return entityManager.createQuery( "SELECT s FROM SuscripcionEscasezEnHeladera s WHERE s.heladeraId = :heladeraId",
            SuscripcionEscasezEnHeladera.class)
        .setParameter("heladeraId", heladeraId)
        .getResultList();
  }

  public List<SuscripcionExcesoEnHeladera> findSuscripcionExcesoEnHeladeraByHeladeraId(Long heladeraId) {
    return entityManager.createQuery( "SELECT s FROM SuscripcionExcesoEnHeladera s WHERE s.heladeraId = :heladeraId",
            SuscripcionExcesoEnHeladera.class)
        .setParameter("heladeraId", heladeraId)
        .getResultList();
  }
}
