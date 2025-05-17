// Test para la clase GestorPedidos
package es.uclm.library.controllerTest;

import es.uclm.library.business.controller.GestorPedidos;
import es.uclm.library.business.entity.*;
import es.uclm.library.business.service.*;
import es.uclm.library.persistence.ItemPedidoDAO;
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

class GestorPedidosTest {

    @InjectMocks
    private GestorPedidos gestorPedidos;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private RepartoService repartoService;

    @Mock
    private RestauranteService restauranteService;

    @Mock
    private LoginService loginService;

    @Mock
    private ItemPedidoDAO itemPedidoDAO;

    @Mock
    private HttpSession session;

    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        model = new BindingAwareModelMap();
    }

    @Test
    void testCargarCartas() {
        // Arrange
        Long restauranteId = 1L;
        List<CartaMenu> cartasMenu = List.of(new CartaMenu());
        when(restauranteService.obtenerCartasPorRestaurante(restauranteId)).thenReturn(cartasMenu);

        // Act
        String viewName = gestorPedidos.cargarCartas(restauranteId, model);

        // Assert
        assertEquals("cartas :: cartasFragment", viewName);
        assertEquals(cartasMenu, model.getAttribute("cartasMenu"));
    }

    @Test
    void testListarRestaurantes() {
        // Arrange
        when(session.getAttribute("email")).thenReturn("user@example.com");
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();
        cliente.setFavoritos(new ArrayList<>());
        when(loginService.findUsuarioById(anyString())).thenReturn(usuario);
        when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);
        when(restauranteService.obtenerTodosRestaurantes()).thenReturn(List.of(new Restaurante()));

        // Act
        String viewName = gestorPedidos.listarRestaurantes(model, session);

        // Assert
        assertEquals("RealizarPedido", viewName);
        assertNotNull(model.getAttribute("restaurantes"));
    }

    @Test
    void testAgregarItem() {
        // Arrange
        Long itemId = 1L;
        List<ItemPedido> pedidoItems = new ArrayList<>();
        ItemMenu itemMenu = new ItemMenu();
        itemMenu.setNombre("Pizza");
        itemMenu.setPrecio(10.0);
        when(restauranteService.obtenerItemPorId(itemId)).thenReturn(itemMenu);

        // Act
        String viewName = gestorPedidos.agregarItem(itemId, pedidoItems, model);

        // Assert
        assertEquals("RestaurantePedido :: pedidoResumen", viewName);
        assertFalse(pedidoItems.isEmpty());
        assertEquals(10.0, model.getAttribute("total"));
    }

    @Test
    void testQuitarItem() {
        // Arrange
        Long itemId = 1L;
        List<ItemPedido> pedidoItems = new ArrayList<>();
        ItemMenu itemMenu = new ItemMenu();
        itemMenu.setNombre("Pizza");
        itemMenu.setPrecio(10.0);
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setNombre("Pizza");
        itemPedido.setCantidad(1);
        pedidoItems.add(itemPedido);
        when(restauranteService.obtenerItemPorId(itemId)).thenReturn(itemMenu);

        // Act
        String viewName = gestorPedidos.quitarItem(itemId, pedidoItems, model);

        // Assert
        assertEquals("RestaurantePedido :: pedidoResumen", viewName);
        assertTrue(pedidoItems.isEmpty());
        assertEquals(0.0, model.getAttribute("total"));
    }

    @Test
    void testRealizarPedido() {
        // Arrange
        Long restauranteId = 1L;
        List<ItemPedido> pedidoItems = List.of(new ItemPedido());
        Cliente cliente = new Cliente();
        Restaurante restaurante = new Restaurante();
        when(session.getAttribute("email")).thenReturn("user@example.com");
        when(loginService.findClienteByUsuario(any())).thenReturn(cliente);
        when(restauranteService.obtenerRestaurantePorId(restauranteId)).thenReturn(restaurante);

        // Act
        String viewName = gestorPedidos.realizarPedido(restauranteId, pedidoItems, session, model);

        // Assert
        assertEquals("redirect:/RealizarPedido/PagoPedido?pedidoId=null", viewName);
    }
}
