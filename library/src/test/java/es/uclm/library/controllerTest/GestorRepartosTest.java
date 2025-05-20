package es.uclm.library.controllerTest;

import es.uclm.library.business.controller.GestorRepartos;
import es.uclm.library.business.entity.*;
import es.uclm.library.business.service.*;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestorRepartosTest {

    @Mock
    private LoginService loginService;

    @Mock
    private RepartoService repartoService;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private GestorRepartos gestorRepartos;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //Test MostrarRepartos
    @Test
    @DisplayName("CU1 - Email válido, usuario y repartidor encontrados, lista de servicios con elementos")
    void testMostrarRepartos_CU1_FlujoNominal() {
        String email = "repartidor@correo.com";
        Usuario usuario = new Usuario();
        Repartidor repartidor = new Repartidor();
        List<ServicioEntrega> servicios = Arrays.asList(new ServicioEntrega(), new ServicioEntrega());

        when(session.getAttribute("email")).thenReturn(email);
        when(loginService.findUsuarioById(email)).thenReturn(usuario);
        when(loginService.findRepartidorByUsuario(usuario)).thenReturn(repartidor);
        when(repartoService.obtenerServiciosPorRepartidor(repartidor)).thenReturn(servicios);

        String vista = gestorRepartos.mostrarRepartos(session, model);

        verify(model).addAttribute("serviciosEntrega", servicios);
        assertEquals("Repartos", vista);
    }

    @Test
    @DisplayName("CU2 - Email nulo, usuario y repartidor nulos, lista vacía")
    void testMostrarRepartos_CU2_EmailNulo() {
        when(session.getAttribute("email")).thenReturn(null);

        String vista = gestorRepartos.mostrarRepartos(session, model);

        verify(model).addAttribute("serviciosEntrega", Collections.emptyList());
        assertEquals("Repartos", vista);
    }

    @Test
    @DisplayName("CU3 - Email inválido, usuario y repartidor no encontrados")
    void testMostrarRepartos_CU3_UsuarioInexistente() {
        String email = "noexiste@correo.com";

        when(session.getAttribute("email")).thenReturn(email);
        when(loginService.findUsuarioById(email)).thenReturn(null);

        String vista = gestorRepartos.mostrarRepartos(session, model);

        verify(model).addAttribute("serviciosEntrega", Collections.emptyList());
        assertEquals("Repartos", vista);
    }

    //Test RecogerPedido
    @Test
    @DisplayName("CU1 - servicioId válido, servicioEntrega válido, pedido válido con estado PAGADO")
    void testRecogerPedido_CU1_FlujoCorrecto() {
        Long servicioId = 1L;
        Pedido pedido = new Pedido();
        pedido.setEstado(EstadoPedido.PAGADO);

        ServicioEntrega servicioEntrega = new ServicioEntrega();
        servicioEntrega.setPedido(pedido);

        when(repartoService.obtenerServicioPorId(servicioId)).thenReturn(servicioEntrega);

        String vista = gestorRepartos.recogerPedido(servicioId, model);

        assertEquals(EstadoPedido.RECOGIDO, pedido.getEstado());
        verify(pedidoService).actualizarPedido(pedido);
        verify(model).addAttribute("message", "Pedido recogido con éxito");
        assertEquals("redirect:/Repartos", vista);
    }

    @Test
    @DisplayName("CU2 - servicioId inválido, servicioEntrega nulo")
    void testRecogerPedido_CU2_ServicioNoEncontrado() {
        Long servicioId = 999L;

        when(repartoService.obtenerServicioPorId(servicioId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            gestorRepartos.recogerPedido(servicioId, model);
        });
    }

    @Test
    @DisplayName("CU3 - servicioId null, servicioEntrega válido, pedido nulo")
    void testRecogerPedido_CU3_PedidoNulo() {
        Long servicioId = null;

        ServicioEntrega servicioEntrega = new ServicioEntrega();
        servicioEntrega.setPedido(null);

        when(repartoService.obtenerServicioPorId(servicioId)).thenReturn(servicioEntrega);

        assertThrows(NullPointerException.class, () -> {
            gestorRepartos.recogerPedido(servicioId, model);
        });
    }

    @Test
    @DisplayName("CU4 - servicioId válido, servicioEntrega y pedido válidos, estado ENTREGADO")
    void testRecogerPedido_CU4_EstadoEntregado() {
        Long servicioId = 1L;
        Pedido pedido = new Pedido();
        pedido.setEstado(EstadoPedido.ENTREGADO);

        ServicioEntrega servicioEntrega = new ServicioEntrega();
        servicioEntrega.setPedido(pedido);

        when(repartoService.obtenerServicioPorId(servicioId)).thenReturn(servicioEntrega);

        String vista = gestorRepartos.recogerPedido(servicioId, model);

        verify(pedidoService, never()).actualizarPedido(any());
        verify(model, never()).addAttribute(eq("message"), any());
        assertEquals("redirect:/Repartos", vista);
    }

    //Test EntregarPedido
    @Test
    @DisplayName("CU1 - servicioId válido, servicioEntrega y pedido válidos, estado RECOGIDO, repartidor válido")
    void testEntregarPedido_CU1_FlujoCorrecto() {
        Long servicioId = 1L;

        Pedido pedido = new Pedido();
        pedido.setEstado(EstadoPedido.RECOGIDO);

        Repartidor repartidor = new Repartidor();
        repartidor.setDisponible(false); // Simulamos que está ocupado

        ServicioEntrega servicioEntrega = new ServicioEntrega();
        servicioEntrega.setPedido(pedido);
        servicioEntrega.setRepartidor(repartidor);

        when(repartoService.obtenerServicioPorId(servicioId)).thenReturn(servicioEntrega);

        String vista = gestorRepartos.entregarPedido(servicioId, model);

        assertEquals(EstadoPedido.ENTREGADO, pedido.getEstado());
        assertTrue(repartidor.isDisponible());
        verify(pedidoService).actualizarPedido(pedido);
        verify(repartoService).actualizarServicioEntrega(servicioEntrega);
        verify(repartoService).actualizarRepartidor(repartidor);
        verify(model).addAttribute("message", "Pedido entregado con éxito");
        assertEquals("redirect:/Repartos", vista);
    }

    @Test
    @DisplayName("CU2 - servicioId inválido, servicioEntrega nulo")
    void testEntregarPedido_CU2_ServicioNoEncontrado() {
        Long servicioId = 999L;

        when(repartoService.obtenerServicioPorId(servicioId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            gestorRepartos.entregarPedido(servicioId, model);
        });
    }

    @Test
    @DisplayName("CU3 - servicioId null, servicioEntrega válido, pedido nulo")
    void testEntregarPedido_CU3_PedidoNulo() {
        Long servicioId = null;

        ServicioEntrega servicioEntrega = new ServicioEntrega();
        servicioEntrega.setPedido(null);
        servicioEntrega.setRepartidor(new Repartidor());

        when(repartoService.obtenerServicioPorId(servicioId)).thenReturn(servicioEntrega);

        assertThrows(NullPointerException.class, () -> {
            gestorRepartos.entregarPedido(servicioId, model);
        });
    }

    @Test
    @DisplayName("CU4 - servicioId válido, servicioEntrega y pedido válidos, estado PEDIDO")
    void testEntregarPedido_CU4_EstadoNoRecogido() {
        Long servicioId = 1L;

        Pedido pedido = new Pedido();
        pedido.setEstado(EstadoPedido.PEDIDO);

        ServicioEntrega servicioEntrega = new ServicioEntrega();
        servicioEntrega.setPedido(pedido);
        servicioEntrega.setRepartidor(null);

        when(repartoService.obtenerServicioPorId(servicioId)).thenReturn(servicioEntrega);

        String vista = gestorRepartos.entregarPedido(servicioId, model);

        verify(pedidoService, never()).actualizarPedido(any());
        verify(model, never()).addAttribute(eq("message"), any());
        verify(repartoService, never()).actualizarServicioEntrega(any());
        verify(repartoService, never()).actualizarRepartidor(any());
        assertEquals("redirect:/Repartos", vista);
    }


}
