package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class ChatController {
  Fachada fachada;

  public ChatController(Fachada fachada) {
    this.fachada = fachada;
  }

  public void registrarChat(Context context) {
    try {
      fachada.registrarChat(context.bodyAsClass(ar.edu.utn.dds.k3003.model.notificaciones.DTO.ChatDTO.class));
      context.status(HttpStatus.OK);
      context.result("Chat registrado correctamente");
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al registrar chat: " + e.getMessage());
    }
  }

  public void getChatByColaboradorId(Context context) {
    try {
      Long colaboradorId = Long.parseLong(context.pathParam("id"));
      context.json(fachada.chatDelColaborador(colaboradorId));
      context.status(HttpStatus.OK);
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al obtener los chats del colaborador: " + e.getMessage());
    }
  }

  public void getColaboradorByChatId(Context context) {
    try {
      Long chatId = Long.parseLong(context.pathParam("id"));
      context.json(fachada.colaboradorDelChat(chatId));
      context.status(HttpStatus.OK);
    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al obtener el chat: " + e.getMessage());
    }
  }
}
