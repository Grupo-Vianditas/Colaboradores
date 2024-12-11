package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.model.Contribuciones.DTO.DonacionDeDineroDTO;
import ar.edu.utn.dds.k3003.model.Contribuciones.DTO.FormulaDTO;
import ar.edu.utn.dds.k3003.model.Contribuciones.DTO.ReparacionHeladeraHeladeraDTO;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class ContribucionController {
  Fachada fachada;

  public ContribucionController(Fachada fachada) {
    this.fachada = fachada;
  }


  public void getPuntuacionColaborador(Context context) {
    try {
      Long colaboradorId = fachada.buscarXId(Long.parseLong(context.pathParam("id"))).getId();
      context.json(fachada.puntos(colaboradorId));
    }
    catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al obtener la puntuación del colaborador: " + e.getMessage());
    }
  }


  public void modificarPuntuacionMultiplicador(Context context) {
    try {
      FormulaDTO formula = context.bodyAsClass(FormulaDTO.class);
      fachada.actualizarPesosPuntos(
          formula.getPesosDonados(),
          formula.getViandasDistribuidas(),
          formula.getViandasDonadas(),
          formula.getTarjetasRepartidas(),
          formula.getHeladerasActivas(),
          formula.getHeladerasReparadas()
      );
      context.status(HttpStatus.OK);
      context.result("Fórmula de puntuación modificada correctamente");

    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al modificar la fórmula de puntuación: " + e.getMessage());
    }
  }

  public void donacionDeDinero(Context context) {
    try{
      fachada.donacionDeDinero(context.bodyAsClass(DonacionDeDineroDTO.class));
      context.status(HttpStatus.OK);
      context.result("Donación cargada correctamente");
    }
    catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al cargar la donación: " + e.getMessage());
    }
  }

  public void getDineroDonado(Context context) {
    try {
      Long colaboradorId = Long.parseLong(context.pathParam("id"));
      context.json(fachada.donacionesDeDineroDelColaborador(colaboradorId));
      context.status(HttpStatus.OK);
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al obtener el dinero donado por el colaborador: " + e.getMessage());
    }
  }

  public void agregarReparacionHeladera(Context context) {
    try {
      fachada.agregarReparacionHeladera(context.bodyAsClass(ReparacionHeladeraHeladeraDTO.class));
      context.status(HttpStatus.OK);
      context.result("Reparación de heladera notificada correctamente");
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al notificar reparación de heladera: " + e.getMessage());
    }
  }

  public void getReparacionesHeladeraByColaboradorId(Context context) {
    try {
      Long colaboradorId = Long.parseLong(context.pathParam("id"));
      context.json(fachada.reparacionesDeHeladeraDelColaborador(colaboradorId));
      context.status(HttpStatus.OK);
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al obtener las reparaciones de heladera del colaborador: " + e.getMessage());
    }
  }
}
