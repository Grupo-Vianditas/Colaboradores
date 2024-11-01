package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.model.Contribuciones.DTO.DonacionDeDineroDTO;
import ar.edu.utn.dds.k3003.model.Contribuciones.DTO.FormasDTO;
import ar.edu.utn.dds.k3003.model.Contribuciones.DTO.FormulaDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.FallaHeladeraDTO;
import ar.edu.utn.dds.k3003.model.eventos.DTO.SuscripcionFallaHeladeraDTO;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ColaboradorController {
  Fachada fachada;
  public ColaboradorController(Fachada fachada) {
    this.fachada = fachada;
  }

  public void getColaborador(Context context) {
    try{
      context.json(fachada.buscarXId(Long.parseLong(context.pathParam("id"))));
      context.status(HttpStatus.OK);
    }
    catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al obtener el colaborador: " + e.getMessage());
    }
  }

  public void crearColaborador(Context context) {
    try {
      //En el context llega el DTO del colaborador a agregar, y la fachada me retorna el DTO del colaborador ya agregado
      ColaboradorDTO colaboradorDTOAgregado = fachada.agregar(context.bodyAsClass(ColaboradorDTO.class));
      context.json(colaboradorDTOAgregado);
      context.status(HttpStatus.CREATED);
    }
    catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al agregar el colaborador: " + e.getMessage());
    }
  }

  public void modificarColaborador(Context context) {
    try{
      Long colaboradorId = fachada.buscarXId(Long.parseLong(context.pathParam("id"))).getId();
      List<FormaDeColaborarEnum> formasDeColaborar = context.bodyAsClass(FormasDTO.class).getFormas();
      context.json(fachada.modificar(colaboradorId, formasDeColaborar));
    }
    catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al modificar el colaborador: " + e.getMessage());
    }
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

  public void borrarTodaLaBase(Context context) {
    try {
      fachada.borrarTodaLaBase();
    } catch (Exception e) {
      System.out.println("Error al borrar la base de datos: " + e.getMessage());
    }
  }

  public void getColaboradores(Context context) {
    try {
      Integer cantidad = Integer.parseInt(Objects.requireNonNull(context.queryParam("cantidad"), "Cantidad no puede ser nulo"));
      Integer pagina = Integer.parseInt(Objects.requireNonNull(context.queryParam("pagina"), "Página no puede ser nulo"));
      context.json(fachada.buscarListaColaboradores(cantidad, pagina));
      context.status(HttpStatus.OK);
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al obtener los colaboradores: " + e.getMessage());
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
  public void suscribirseAHeladeraCasiVacia(@NotNull Context context) {
    try {
      fachada.suscribirseAFallaHeladera(Long.parseLong(context.pathParam("id")), context.bodyAsClass(SuscripcionFallaHeladeraDTO.class));
      context.status(HttpStatus.OK);
      context.result("Colaborador suscripto a falla de heladera correctamente");
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al suscribirse a falla de heladera: " + e.getMessage());
    }
  }  public void suscribirseAHeladeraCasiLlena(@NotNull Context context) {
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
}
