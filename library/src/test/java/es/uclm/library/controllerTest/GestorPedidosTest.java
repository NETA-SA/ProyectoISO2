package es.uclm.library.controllerTest;

import es.uclm.library.business.controller.GestorPedidos;
import es.uclm.library.business.entity.*;
import es.uclm.library.business.service.*;
import es.uclm.library.persistence.*;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestorPedidosTest {

    @Mock
    private LoginService loginService;

    @Mock
    private RepartoService RepartoService;

    @Mock
    private RestauranteService restauranteService;

    @Mock
    private ItemPedidoDAO itemPedidoDAO;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private GestorPedidos gestorPedidos;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //Test ListarRestaurantes
    @Test
    @DisplayName("CU1 - Email válido, cliente con favoritos, restaurantes disponibles")
    void testListarRestaurantes_ClienteConFavoritos() {
        // Datos
        String email = "cliente1@mail.com";
        Restaurante rest1 = new Restaurante(); rest1.setNombre("Rest1");
        Restaurante rest2 = new Restaurante(); rest2.setNombre("Rest2");
        Restaurante rest3 = new Restaurante(); rest3.setNombre("Rest3");
        List<Restaurante> favoritos = Arrays.asList(rest1, rest2);
        List<Restaurante> todos = Arrays.asList(rest1, rest2, rest3);
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();
        cliente.setFavoritos(favoritos);

        when(session.getAttribute("email")).thenReturn(email);
        when(loginService.findUsuarioById(email)).thenReturn(usuario);
        when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);
        when(restauranteService.obtenerTodosRestaurantes()).thenReturn(todos);

        String vista = gestorPedidos.listarRestaurantes(model, session);

        // Verificaciones
        verify(model).addAttribute("cliente", cliente);
        verify(model).addAttribute(eq("restaurantes"), argThat(lista ->
                lista instanceof List && ((List<?>) lista).size() == 3 &&
                        ((List<?>) lista).get(0).equals(rest1)
        ));
        assertEquals("RealizarPedido", vista);
    }

    @Test
    @DisplayName("CU2 - Sin email, cliente null, lista vacía de restaurantes")
    void testListarRestaurantes_SinCliente() {
        when(session.getAttribute("email")).thenReturn(null);
        when(restauranteService.obtenerTodosRestaurantes()).thenReturn(Collections.emptyList());

        String vista = gestorPedidos.listarRestaurantes(model, session);

        verify(model).addAttribute(eq("restaurantes"), eq(Collections.emptyList()));
        assertEquals("RealizarPedido", vista);
    }

    //Test MostrarRestaurantePedido
    @Test
    @DisplayName("CU1 - restauranteId válido, cartaId válido, cartas con contenido, carta con ítems")
    void testMostrarRestaurantePedido_CasoCompleto() {
        Long restauranteId = 1L;
        Long cartaId = 8L;

        CartaMenu carta1 = new CartaMenu();
        CartaMenu carta2 = new CartaMenu();

        ItemMenu item1 = new ItemMenu(); item1.setNombre("Item1");
        ItemMenu item2 = new ItemMenu(); item2.setNombre("Item2");
        ItemMenu item3 = new ItemMenu(); item3.setNombre("Item3");

        carta1.setItems(List.of(item1, item2, item3));

        when(restauranteService.obtenerCartasPorRestaurante(restauranteId)).thenReturn(List.of(carta1, carta2));
        when(restauranteService.obtenerCartaPorId(cartaId)).thenReturn(carta1);

        String vista = gestorPedidos.mostrarRestaurantePedido(restauranteId, cartaId, model);

        verify(model).addAttribute("cartasMenu", List.of(carta1, carta2));
        verify(model).addAttribute("restauranteId", restauranteId);
        verify(model).addAttribute("items", carta1.getItems());
        verify(model).addAttribute("selectedCartaId", cartaId);
        assertEquals("RestaurantePedido", vista);
    }

    @Test
    @DisplayName("CU2 - restauranteId inválido, cartaId nulo, cartas vacías")
    void testMostrarRestaurantePedido_SinCarta() {
        Long restauranteId = 999L;
        Long cartaId = null;

        when(restauranteService.obtenerCartasPorRestaurante(restauranteId)).thenReturn(Collections.emptyList());

        String vista = gestorPedidos.mostrarRestaurantePedido(restauranteId, cartaId, model);

        verify(model).addAttribute("cartasMenu", Collections.emptyList());
        verify(model).addAttribute("restauranteId", restauranteId);
        verify(model, never()).addAttribute(eq("items"), any());
        verify(model, never()).addAttribute(eq("selectedCartaId"), any());
        assertEquals("RestaurantePedido", vista);
    }

    @Test
    @DisplayName("CU3 - restauranteId nulo, cartaId inválido, carta sin ítems")
    void testMostrarRestaurantePedido_RestauranteNuloCartaInvalida() {
        Long restauranteId = null;
        Long cartaId = 999L;

        CartaMenu carta1 = new CartaMenu();
        CartaMenu carta2 = new CartaMenu();
        carta1.setItems(Collections.emptyList());

        when(restauranteService.obtenerCartasPorRestaurante(restauranteId)).thenReturn(List.of(carta1, carta2));
        when(restauranteService.obtenerCartaPorId(cartaId)).thenReturn(carta1);

        String vista = gestorPedidos.mostrarRestaurantePedido(restauranteId, cartaId, model);

        verify(model).addAttribute("cartasMenu", List.of(carta1, carta2));
        verify(model).addAttribute("restauranteId", restauranteId);
        verify(model).addAttribute("items", Collections.emptyList());
        verify(model).addAttribute("selectedCartaId", cartaId);
        assertEquals("RestaurantePedido", vista);
    }

    //Test agregarItem
    @Test
    @DisplayName("CU1 - itemId válido, lista vacía de pedidoItems → se añade nuevo item")
    void testAgregarItem_NuevoItemEnListaVacia() {
        Long itemId = 5L;
        List<ItemPedido> pedidoItems = Collections.synchronizedList(new java.util.ArrayList<>());
        ItemMenu itemMenu = new ItemMenu();
        itemMenu.setNombre("Pizza");
        itemMenu.setPrecio(10.0);
        itemMenu.setTipo(TipoItemMenu.PRIMER_PLATO);

        when(restauranteService.obtenerItemPorId(itemId)).thenReturn(itemMenu);

        String vista = gestorPedidos.agregarItem(itemId, pedidoItems, model);

        assertEquals(1, pedidoItems.size());
        assertEquals("Pizza", pedidoItems.get(0).getNombre());
        assertEquals(1, pedidoItems.get(0).getCantidad());
        assertEquals("RestaurantePedido :: pedidoResumen", vista);
        verify(model).addAttribute(eq("pedidoItems"), eq(pedidoItems));
        verify(model).addAttribute(eq("total"), eq(10.0));
        verify(model).addAttribute(eq("itemFrequencies"), any());
    }

    @Test
    @DisplayName("CU2 - itemId inexistente, lista con varios ítems → no se añade nada")
    void testAgregarItem_ItemNoExistente() {
        Long itemId = 999L;
        List<ItemPedido> pedidoItems = Arrays.asList(
                new ItemPedido(TipoItemMenu.PRIMER_PLATO, "Pizza", 10.0, 1),
                new ItemPedido(TipoItemMenu.PRIMER_PLATO, "Hamburguesa", 12.0, 1)
        );

        when(restauranteService.obtenerItemPorId(itemId)).thenReturn(null); // simulamos item no existente

        assertThrows(NullPointerException.class, () -> {
            gestorPedidos.agregarItem(itemId, pedidoItems, model);
        });
    }

    @Test
    @DisplayName("CU3 - itemId es null → se lanza excepción")
    void testAgregarItem_ItemIdNull_LanzaExcepcion() {
        Long itemId = null;
        List<ItemPedido> pedidoItems = new java.util.ArrayList<>();

        when(restauranteService.obtenerItemPorId(itemId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            gestorPedidos.agregarItem(itemId, pedidoItems, model);
        }
        );
    }

    //Test RealizarPedido

    @Test
    @DisplayName("CU1 - Pedido correcto con cliente y restaurante válidos")
    void testRealizarPedido_OK() {
        Long restauranteId = 5L;
        String email = "cliente@mail.com";

        Cliente cliente = new Cliente();
        Restaurante restaurante = new Restaurante();
        List<ItemPedido> pedidoItems = new java.util.ArrayList<>(List.of(
                new ItemPedido(TipoItemMenu.PRIMER_PLATO, "Pizza", 10.0, 1),
                new ItemPedido(TipoItemMenu.PRIMER_PLATO, "Hamburguesa", 8.0, 1)
        ));

        when(session.getAttribute("email")).thenReturn(email);
        when(loginService.findUsuarioById(email)).thenReturn(new Usuario());
        when(loginService.findClienteByUsuario(any())).thenReturn(cliente);
        when(restauranteService.obtenerRestaurantePorId(restauranteId)).thenReturn(restaurante);

        doAnswer(inv -> {
            Pedido p = inv.getArgument(0);
            p.setId(123L);
            return null;
        }).when(pedidoService).crearPedido(any(Pedido.class));

        String vista = gestorPedidos.realizarPedido(restauranteId, pedidoItems, session, model);

        assertEquals("redirect:/RealizarPedido/PagoPedido?pedidoId=123", vista);
        verify(itemPedidoDAO, times(2)).save(any(ItemPedido.class));
        verify(pedidoService).crearPedido(any(Pedido.class));
    }

    @Test
    @DisplayName("CU2 - email nulo, restauranteId nulo, cliente nulo → lanza NullPointerException")
    void testRealizarPedido_CamposNulos_LanzaExcepcion() {
        Long restauranteId = null;
        String email = null;
        List<ItemPedido> pedidoItems = new java.util.ArrayList<>();

        when(session.getAttribute("email")).thenReturn(email);
        when(loginService.findUsuarioById(any())).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> {
            gestorPedidos.realizarPedido(restauranteId, pedidoItems, session, model);
        });
    }

    @Test
    @DisplayName("CU3 - restauranteId inexistente, email inválido → cliente y restaurante nulos → excepción")
    void testRealizarPedido_ClienteYRestauranteInexistentes_LanzaExcepcion() {
        Long restauranteId = 999L;
        String email = "noexiste@mail.com";
        List<ItemPedido> pedidoItems = new java.util.ArrayList<>(List.of(
                new ItemPedido(TipoItemMenu.PRIMER_PLATO, "Pizza", 10.0, 1),
                new ItemPedido(TipoItemMenu.PRIMER_PLATO, "Hamburguesa", 8.0, 1)
        ));

        when(session.getAttribute("email")).thenReturn(email);
        when(loginService.findUsuarioById(email)).thenReturn(null);
        when(loginService.findClienteByUsuario(null)).thenReturn(null);
        when(restauranteService.obtenerRestaurantePorId(restauranteId)).thenReturn(null);

        doAnswer(invocation -> {
            Pedido pedido = invocation.getArgument(0);
            pedido.setId(999L);
            return null;
        }).when(pedidoService).crearPedido(any());

        String vista = gestorPedidos.realizarPedido(restauranteId, pedidoItems, session, model);

        assertEquals("redirect:/RealizarPedido/PagoPedido?pedidoId=999", vista);
    }

    //Test realizarPago
    @Test
    @DisplayName("CU1 - PedidoId nulo, email nulo, cliente y pedido nulos → error")
    void testRealizarPago_CU1_CamposNulos_Error() {
        Long pedidoId = null;
        String email = null;
        String codigoPostal = "99999"; // inválido (fuera de enum)
        String municipio = "Toledo";


        when(session.getAttribute("email")).thenReturn(email);
        when(loginService.findUsuarioById(any())).thenReturn(null);
        when(loginService.findClienteByUsuario(any())).thenReturn(null);
        when(pedidoService.obtenerPedidoPorId(pedidoId)).thenReturn(null);

        String vista = gestorPedidos.realizarPago(pedidoId, "Calle Falsa", "10", "", municipio, codigoPostal, MetodoPago.PAYPAL, session, model);

        verify(model).addAttribute(eq("message"), contains("Error: Cliente o pedido no encontrado"));
        assertEquals("error", vista);
    }

    @Test
    @DisplayName("CU2 - Flujo válido completo, todo correcto → redirección exitosa")
    void testRealizarPago_CU2_FlujoValido() {
        Long pedidoId = 1L;
        String email = "cliente@mail.com";
        Cliente cliente = new Cliente();
        cliente.setDirecciones(new ArrayList<>());

        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        Restaurante restaurante = new Restaurante();
        Direccion direccionRestaurante = new Direccion();
        restaurante.setDireccion(direccionRestaurante);
        pedido.setRestaurante(restaurante);

        Repartidor repartidor = new Repartidor();
        repartidor.setDisponible(true);

        when(session.getAttribute("email")).thenReturn(email);
        when(loginService.findUsuarioById(email)).thenReturn(new Usuario());
        when(loginService.findClienteByUsuario(any())).thenReturn(cliente);
        when(pedidoService.obtenerPedidoPorId(pedidoId)).thenReturn(pedido);
        when(RepartoService.obtenerRepartidorDisponible()).thenReturn(repartidor);
        when(pedidoService.guardarPago(any())).thenAnswer(inv -> inv.getArgument(0));
        when(pedidoService.guardarServicioEntrega(any())).thenAnswer(inv -> inv.getArgument(0));

        String vista = gestorPedidos.realizarPago(pedidoId, "Calle Real", "5", "", "Talavera", "45600", MetodoPago.MasterCard, session, model);

        assertEquals("redirect:/", vista);
        verify(model).addAttribute(eq("message"), contains("Pago realizado con éxito"));
        verify(pedidoService).guardarDireccion(any());
        verify(pedidoService).guardarPago(any());
        verify(pedidoService).guardarServicioEntrega(any());
        verify(pedidoService, atLeastOnce()).actualizarPedido(any());
        verify(RepartoService).actualizarRepartidor(repartidor);
    }


    @Test
    @DisplayName("CU3 - PedidoId inexistente, email inválido, cliente null → error")
    void testRealizarPago_CU3_EmailYPedidoInvalidos_Error() {
        Long pedidoId = 999L;
        String email = "noexiste@mail.com";
        String codigoPostal = "45600";
        String municipio = "Toledo";

        when(session.getAttribute("email")).thenReturn(email);
        when(loginService.findUsuarioById(email)).thenReturn(null); // usuario no encontrado
        when(loginService.findClienteByUsuario(null)).thenReturn(null); // cliente no encontrado
        when(pedidoService.obtenerPedidoPorId(pedidoId)).thenReturn(null); // pedido no encontrado

        String vista = gestorPedidos.realizarPago(pedidoId, "Calle Luna", "7", "", municipio, codigoPostal, MetodoPago.Visa, session, model);

        verify(model).addAttribute(eq("message"), contains("Error: Cliente o pedido no encontrado"));
        assertEquals("error", vista);
    }


}


