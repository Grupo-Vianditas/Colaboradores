package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

public class FachadaTest {
  ColaboradorDTO colaboradorDTO1;

  Fachada fachada;

  @BeforeEach
  void setUp() {
    colaboradorDTO1 = new ColaboradorDTO("Lucas", List.of(FormaDeColaborarEnum.TRANSPORTADOR));

    fachada = new Fachada();
    fachada.actualizarPesosPuntos(0.5, 1.0, 1.5, 2.0, 5.0);
  }

  @Test
  @DisplayName("Test de puntuacion de colaborador sin contribuciones")
  void testPuntuacionColaboradorSinContribuciones() {
    ColaboradorDTO colaboradorDTO = fachada.agregar(colaboradorDTO1);
    Double puntos = fachada.puntos(colaboradorDTO.getId());
    Assertions.assertEquals(0.0, puntos, "La puntuacion de un colaborador sin contribuciones deberia ser 0");
  }

  @Test
  @DisplayName("Guardado de colaborador")
  void testGuardarColaborador() {
    ColaboradorDTO colaboradorDTO = new ColaboradorDTO("Emi", List.of(FormaDeColaborarEnum.TRANSPORTADOR));
    colaboradorDTO = fachada.agregar(colaboradorDTO);
    ColaboradorDTO colaboradorAuxiliar = fachada.buscarXId(colaboradorDTO.getId());
    Assertions.assertNotNull(colaboradorAuxiliar.getId(), "El colaborador deberia tener un ID asignado");
  }

  @Test
  @DisplayName("Modificacion de colaborador")
  void testModificarColaborador() {
    ColaboradorDTO colaboradorAuxiliar = fachada.agregar(colaboradorDTO1);
    fachada.modificar(colaboradorAuxiliar.getId(), List.of(FormaDeColaborarEnum.DONADOR));
    colaboradorAuxiliar = fachada.buscarXId(colaboradorAuxiliar.getId());
    Assertions.assertEquals(List.of(FormaDeColaborarEnum.DONADOR), colaboradorAuxiliar.getFormas(), "El colaborador deberia ser modificado");
  }

  @Test
  @DisplayName("Test de modificacion de puntos")
  void testModificarPuntos() {
    colaboradorDTO1 = fachada.agregar(colaboradorDTO1);
    FachadaLogistica logistica = Mockito.mock(FachadaLogistica.class);
    FachadaViandas viandas = Mockito.mock(FachadaViandas.class);
    fachada.setLogisticaProxy(logistica);
    fachada.setViandasProxy(viandas);
    Mockito.when(logistica.trasladosDeColaborador(colaboradorDTO1.getId(), 1, 2024)).thenReturn(List.of(new TrasladoDTO("42", 5, 6)));
    Mockito.when(viandas.viandasDeColaborador(colaboradorDTO1.getId(), 1, 2024)).thenReturn(List.of());
    Assertions.assertEquals(1.0, fachada.puntos(colaboradorDTO1.getId()), "La puntuacion de un colaborador con 1 distribucion de vianda deberia ser 1.0");
    fachada.actualizarPesosPuntos(5.0, 7.0, 5.0, 5.0, 5.0);
    Mockito.when(viandas.viandasDeColaborador(colaboradorDTO1.getId(), 1, 2024)).thenReturn(List.of(new ViandaDTO("25", LocalDateTime.now(), EstadoViandaEnum.DEPOSITADA, 2L,3)));
    Assertions.assertEquals(12.0, fachada.puntos(colaboradorDTO1.getId()), "La puntuacion de un colaborador con 1 distribucion de vianda y 1 donacion de vianda deberia ser 12.0");
  }

}
