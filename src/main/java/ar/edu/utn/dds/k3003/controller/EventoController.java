package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.model.eventos.DTO.FallaHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.MovimientoDeViandaEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionEscasezEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionExcesoEnHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionFallaHeladeraDTO;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

public class EventoController {
  Fachada fachada;

  public EventoController(Fachada fachada) {
    this.fachada = fachada;
  }

  public void suscribirseAEscasezEnHeladera(Context context) {
    try {
      fachada.suscribirseAEscacezEnHeladera(Long.parseLong(context.pathParam("id")), context.bodyAsClass(SuscripcionEscasezEnHeladeraDTO.class));
      context.status(HttpStatus.OK);
      context.result("Colaborador suscripto a escacez en heladera correctamente");
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al suscribirse a escacez en heladera: " + e.getMessage());
    }
  }

  public void suscribirseAExcesoEnHeladera(Context context) {
    try {
      fachada.suscribirseAExcesoEnHeladera(Long.parseLong(context.pathParam("id")), context.bodyAsClass(SuscripcionExcesoEnHeladeraDTO.class));
      context.status(HttpStatus.OK);
      context.result("Colaborador suscripto a exceso en heladera correctamente");
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al suscribirse a exceso en heladera: " + e.getMessage());
    }
  }

  public void suscribirseAFallaHeladera(@NotNull Context context) {
    try {
      fachada.suscribirseAFallaHeladera(Long.parseLong(context.pathParam("id")), context.bodyAsClass(SuscripcionFallaHeladeraDTO.class));
      context.status(HttpStatus.OK);
      context.result("Colaborador suscripto a falla de heladera correctamente");
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al suscribirse a falla de heladera: " + e.getMessage());
    }
  }

  public void notificarFallaHeladera(@NotNull Context context) {
    try {
      fachada.notificarFallaHeladera(context.bodyAsClass(FallaHeladeraDTO.class));
      context.status(HttpStatus.OK);
      context.result("Falla de heladera notificada correctamente");
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al notificar falla de heladera: " + e.getMessage());
    }
  }

  public void notificarMovimientoDeViandaEnHeladera(@NotNull Context context) {
    try {
      fachada.notificarMovimientoDeViandaEnHeladera(context.bodyAsClass(MovimientoDeViandaEnHeladeraDTO.class));
      context.status(HttpStatus.OK);
      context.result("Movimiento de vianda en heladera notificado correctamente");
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al notificar movimiento de vianda en heladera: " + e.getMessage());
    }
  }

  public void desuscribirseAFallaHeladera(Context context) {
    try {
      fachada.desuscribirseAFallaHeladera(Long.parseLong(context.pathParam("id")), Long.parseLong(context.pathParam("heladeraId")));
      context.status(HttpStatus.OK);
      context.result("Colaborador desuscripto a falla de heladera correctamente");
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al desuscribirse a falla de heladera: " + e.getMessage());
    }
  }

  public void desuscribirseAEscasezEnHeladera(Context context) {
    try {
      fachada.desuscribirseAEscasezEnHeladera(Long.parseLong(context.pathParam("id")), Long.parseLong(context.pathParam("heladeraId")));
      context.status(HttpStatus.OK);
      context.result("Colaborador desuscripto a escacez en heladera correctamente");
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al desuscribirse a escacez en heladera: " + e.getMessage());

    }
  }

  public void desuscribirseAExcesoEnHeladera(Context context) {
    try {
      fachada.desuscribirseAExcesoEnHeladera(Long.parseLong(context.pathParam("id")), Long.parseLong(context.pathParam("heladeraId")));
      context.status(HttpStatus.OK);
      context.result("Colaborador desuscripto a exceso en heladera correctamente");
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al desuscribirse a exceso en heladera: " + e.getMessage());
    }
  }
}
