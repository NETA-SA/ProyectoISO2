package es.uclm.library.controllerTest;
import es.uclm.library.business.controller.GestorRepartos;
import es.uclm.library.business.entity.*;
import es.uclm.library.business.service.*;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorRepartosTest {

    @InjectMocks
    private GestorRepartos gestorRepartos;

    @Mock
    private RepartoService repartoService;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private LoginService loginService;

    @Mock
    private HttpSession session;

    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        model = new BindingAwareModelMap();
    }

    @Test
    void testMostrarRepartos() {
        // Arrange
        String email = "repartidor@example.com";
        Usuario usuario = new Usuario();
        Repartidor repartidor = new Repartidor();
        List<ServicioEntrega> serviciosEntrega = new ArrayList<>();
        when(session.getAttribute("email")).thenReturn(email);
        when(loginService.findUsuarioById(email)).thenReturn(usuario);
        when(loginService.findRepartidorByUsuario(usuario)).thenReturn(repartidor);
        when(repartoService.obtenerServiciosPorRepartidor(repartidor)).thenReturn(serviciosEntrega);

        // Act
        String viewName = gestorRepartos.mostrarRepartos(session, model);

        // Assert
        assertEquals("Repartos", viewName);
        assertEquals(serviciosEntrega, model.getAttribute("serviciosEntrega"));
    }

    @Test
    void testRecogerPedido() {
        // Arrange
        Long servicioId = 1L;
        ServicioEntrega servicioEntrega = new ServicioEntrega();
        Pedido pedido = new Pedido();
        pedido.setEstado(EstadoPedido.PAGADO);
        servicioEntrega.setPedido(pedido);
        when(repartoService.obtenerServicioPorId(servicioId)).thenReturn(servicioEntrega);

        // Act
        String viewName = gestorRepartos.recogerPedido(servicioId, model);

        // Assert
        assertEquals("redirect:/Repartos", viewName);
        assertEquals(EstadoPedido.RECOGIDO, pedido.getEstado());
        verify(pedidoService, times(1)).actualizarPedido(pedido);
    }

    @Test
    void testEntregarPedido() {
        // Arrange
        Long servicioId = 1L;
        ServicioEntrega servicioEntrega = new ServicioEntrega();
        Pedido pedido = new Pedido();
        pedido.setEstado(EstadoPedido.RECOGIDO);
        Repartidor repartidor = new Repartidor();
        servicioEntrega.setPedido(pedido);
        servicioEntrega.setRepartidor(repartidor);
        when(repartoService.obtenerServicioPorId(servicioId)).thenReturn(servicioEntrega);

        // Act
        String viewName = gestorRepartos.entregarPedido(servicioId, model);

        // Assert
        assertEquals("redirect:/Repartos", viewName);
        assertEquals(EstadoPedido.ENTREGADO, pedido.getEstado());
        assertNotNull(servicioEntrega.getFechaEntrega());
        assertTrue(repartidor.isDisponible());
        verify(pedidoService, times(1)).actualizarPedido(pedido);
        verify(repartoService, times(1)).actualizarServicioEntrega(servicioEntrega);
        verify(repartoService, times(1)).actualizarRepartidor(repartidor);
    }
}
