package es.uclm.library.controllerTest;

import es.uclm.library.business.controller.GestorPedidos;
import es.uclm.library.business.entity.*;
import es.uclm.library.business.service.*;
import es.uclm.library.persistence.ItemPedidoDAO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GestorPedidos.class)
public class GestorPedidosTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;
    @MockBean
    private RepartoService repartoService;
    @MockBean
    private RestauranteService restauranteService;
    @MockBean
    private LoginService loginService;
    @MockBean
    private ItemPedidoDAO itemPedidoDAO;
    @MockBean
    private EntityManager entityManager;

    @Test
    @DisplayName("GET /RealizarPedido - createPedidoItems inicializa lista vacía")
    void createPedidoItems_devuelveListaVacia() throws Exception {
        mockMvc.perform(get("/RealizarPedido"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pedidoItems"))
                .andExpect(model().attribute("pedidoItems", List.of()));
    }

    @Test
    @DisplayName("GET /RealizarPedido - calculateItemFrequencies calcula correctamente las frecuencias")
    void calculateItemFrequencies_devuelveFrecuenciasCorrectas() throws Exception {
        ItemPedido item1 = new ItemPedido();
        item1.setTipo(TipoItemMenu.valueOf("Plato"));
        item1.setNombre("Pizza");
        item1.setPrecio(10.0);
        item1.setCantidad(1);
        item1.setId(1L);

        ItemPedido item2 = new ItemPedido();
        item2.setTipo(TipoItemMenu.valueOf("Plato"));
        item2.setNombre("Pizza");
        item2.setPrecio(10.0);
        item2.setCantidad(1);
        item2.setId(1L);

        ItemPedido persistido = new ItemPedido();
        persistido.setTipo(TipoItemMenu.valueOf("Plato"));
        persistido.setNombre("Pizza");
        persistido.setPrecio(10.0);
        persistido.setCantidad(1);
        persistido.setId(1L);

        Mockito.when(itemPedidoDAO.findById(1L)).thenReturn(java.util.Optional.of(persistido));

        mockMvc.perform(get("/RealizarPedido")
                        .flashAttr("pedidoItems", List.of(item1, item2)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("itemFrequencies"))
                .andExpect(model().attribute("itemFrequencies", org.hamcrest.Matchers.hasSize(1)));
    }
    @Test
    @DisplayName("GET /RealizarPedido/cartas - retorna fragmento con cartas del restaurante")
    void cargarCartas_devuelveCartasFragment() throws Exception {
        CartaMenu carta1 = new CartaMenu();
        carta1.setId(101L);

        Mockito.when(restauranteService.obtenerCartasPorRestaurante(1L)).thenReturn(List.of(carta1));

        mockMvc.perform(get("/RealizarPedido/cartas")
                        .param("restauranteId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("cartas :: cartasFragment"))
                .andExpect(model().attributeExists("cartasMenu"))
                .andExpect(model().attribute("cartasMenu", org.hamcrest.Matchers.hasSize(1)));
    }

    @Test
    @DisplayName("GET /RealizarPedido/ListaRestaurantes - con cliente válido y favoritos")
    void listarRestaurantes_conSesionYFavoritos_devuelveVistaConModeloPersonalizado() throws Exception {
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();

        Restaurante rest1 = new Restaurante(); rest1.setNombre("Favorito");
        Restaurante rest2 = new Restaurante(); rest2.setNombre("NoFavorito");

        cliente.setFavoritos(new HashSet<>(Set.of(rest1)));

        Mockito.when(loginService.findUsuarioById("cliente@mail.com")).thenReturn(usuario);
        Mockito.when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);
        Mockito.when(restauranteService.obtenerTodosRestaurantes()).thenReturn(List.of(rest1, rest2));

        mockMvc.perform(get("/RealizarPedido/ListaRestaurantes")
                        .sessionAttr("email", "cliente@mail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("RealizarPedido"))
                .andExpect(model().attributeExists("restaurantes"))
                .andExpect(model().attribute("restaurantes", org.hamcrest.Matchers.hasSize(2)));
    }

    @Test
    @DisplayName("GET /RealizarPedido/ListaRestaurantes - sin sesión carga restaurantes sin personalizar")
    void listarRestaurantes_sinSesion_devuelveVistaConTodos() throws Exception {
        Restaurante rest1 = new Restaurante(); rest1.setNombre("Restaurante1");
        Restaurante rest2 = new Restaurante(); rest2.setNombre("Restaurante2");

        Mockito.when(restauranteService.obtenerTodosRestaurantes()).thenReturn(List.of(rest1, rest2));

        mockMvc.perform(get("/RealizarPedido/ListaRestaurantes"))
                .andExpect(status().isOk())
                .andExpect(view().name("RealizarPedido"))
                .andExpect(model().attributeExists("restaurantes"))
                .andExpect(model().attribute("restaurantes", org.hamcrest.Matchers.hasSize(2)));
    }

    @Test
    @DisplayName("GET /RealizarPedido/ListaRestaurantes - email sin cliente devuelve todos")
    void listarRestaurantes_emailSinCliente_devuelveTodosRestaurantes() throws Exception {
        Usuario usuario = new Usuario();

        Restaurante rest = new Restaurante(); rest.setNombre("Restaurante Libre");

        Mockito.when(loginService.findUsuarioById("otro@mail.com")).thenReturn(usuario);
        Mockito.when(loginService.findClienteByUsuario(usuario)).thenReturn(null);
        Mockito.when(restauranteService.obtenerTodosRestaurantes()).thenReturn(List.of(rest));

        mockMvc.perform(get("/RealizarPedido/ListaRestaurantes")
                        .sessionAttr("email", "otro@mail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("RealizarPedido"))
                .andExpect(model().attributeExists("restaurantes"))
                .andExpect(model().attribute("restaurantes", org.hamcrest.Matchers.hasSize(1)));
    }

    @Test
    @DisplayName("GET /RealizarPedido/RestaurantePedido - con cartaId muestra items de carta")
    void mostrarRestaurantePedido_conCartaYRestaurante_devuelveVistaYItems() throws Exception {
        CartaMenu carta = new CartaMenu();
        carta.setId(5L);

        ItemMenu item = new ItemMenu();
        item.setNombre("Pizza");
        carta.setItems(List.of(item));

        Mockito.when(restauranteService.obtenerCartasPorRestaurante(1L)).thenReturn(List.of(carta));
        Mockito.when(restauranteService.obtenerCartaPorId(5L)).thenReturn(carta);

        mockMvc.perform(get("/RealizarPedido/RestaurantePedido")
                        .param("restauranteId", "1")
                        .param("cartaId", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("RestaurantePedido"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attribute("items", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(model().attribute("selectedCartaId", 5L));
    }

    @Test
    @DisplayName("GET /RealizarPedido/RestaurantePedido - sin cartaId solo carga cartas")
    void mostrarRestaurantePedido_soloCartas_devuelveVistaSinItems() throws Exception {
        CartaMenu carta = new CartaMenu();
        carta.setId(7L);

        Mockito.when(restauranteService.obtenerCartasPorRestaurante(2L)).thenReturn(List.of(carta));

        mockMvc.perform(get("/RealizarPedido/RestaurantePedido")
                        .param("restauranteId", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("RestaurantePedido"))
                .andExpect(model().attributeExists("cartasMenu"))
                .andExpect(model().attributeDoesNotExist("items"));
    }

    @Test
    @DisplayName("POST /RealizarPedido/RestaurantePedido/agregarItem - item nuevo se agrega a la lista")
    void agregarItem_itemNuevo_seAgrega() throws Exception {
        ItemMenu itemMenu = new ItemMenu();
        itemMenu.setNombre("CocaCola");
        itemMenu.setPrecio(2.5);
        itemMenu.setTipo(TipoItemMenu.BEBIDA);

        Mockito.when(restauranteService.obtenerItemPorId(1L)).thenReturn(itemMenu);

        mockMvc.perform(post("/RealizarPedido/RestaurantePedido/agregarItem")
                        .param("itemId", "1")
                        .flashAttr("pedidoItems", new ArrayList<>()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pedidoItems", "total", "itemFrequencies"));
    }

    @Test
    @DisplayName("POST /RealizarPedido/RestaurantePedido/agregarItem - item repetido incrementa cantidad")
    void agregarItem_itemRepetido_incrementaCantidad() throws Exception {
        ItemMenu itemMenu = new ItemMenu();
        itemMenu.setNombre("CocaCola");
        itemMenu.setPrecio(2.5);
        itemMenu.setTipo(TipoItemMenu.BEBIDA);

        ItemPedido existente = new ItemPedido();
        existente.setNombre("CocaCola");
        existente.setTipo(TipoItemMenu.BEBIDA);
        existente.setPrecio(2.5);
        existente.setCantidad(1);

        Mockito.when(restauranteService.obtenerItemPorId(1L)).thenReturn(itemMenu);

        mockMvc.perform(post("/RealizarPedido/RestaurantePedido/agregarItem")
                        .param("itemId", "1")
                        .flashAttr("pedidoItems", new ArrayList<>(List.of(existente))))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pedidoItems", "total", "itemFrequencies"))
                .andExpect(model().attribute("pedidoItems", org.hamcrest.Matchers.hasItem(org.hamcrest.Matchers.hasProperty("cantidad", org.hamcrest.Matchers.equalTo(2)))));
    }

    @Test
    @DisplayName("POST /RealizarPedido/RestaurantePedido/quitarItem - elimina item por nombre")
    void quitarItem_itemExistente_seEliminaDeLista() throws Exception {
        ItemMenu itemMenu = new ItemMenu();
        itemMenu.setNombre("CocaCola");

        ItemPedido existente = new ItemPedido();
        existente.setNombre("CocaCola");
        existente.setTipo(TipoItemMenu.BEBIDA);
        existente.setPrecio(2.5);
        existente.setCantidad(1);

        Mockito.when(restauranteService.obtenerItemPorId(1L)).thenReturn(itemMenu);

        mockMvc.perform(post("/RealizarPedido/RestaurantePedido/quitarItem")
                        .param("itemId", "1")
                        .flashAttr("pedidoItems", new ArrayList<>(List.of(existente))))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pedidoItems", "total", "itemFrequencies"))
                .andExpect(model().attribute("pedidoItems", org.hamcrest.Matchers.not(org.hamcrest.Matchers.hasItem(org.hamcrest.Matchers.hasProperty("nombre", org.hamcrest.Matchers.equalTo("CocaCola"))))));
    }

    @Test
    @DisplayName("POST /RealizarPedido/RestaurantePedido/quitarItem - no elimina si nombre no coincide")
    void quitarItem_nombreNoCoincide_listaSeMantiene() throws Exception {
        ItemMenu itemMenu = new ItemMenu();
        itemMenu.setNombre("Fanta");

        ItemPedido existente = new ItemPedido();
        existente.setNombre("CocaCola");
        existente.setTipo(TipoItemMenu.BEBIDA);
        existente.setPrecio(2.5);
        existente.setCantidad(1);

        Mockito.when(restauranteService.obtenerItemPorId(1L)).thenReturn(itemMenu);

        mockMvc.perform(post("/RealizarPedido/RestaurantePedido/quitarItem")
                        .param("itemId", "1")
                        .flashAttr("pedidoItems", new ArrayList<>(List.of(existente))))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pedidoItems", "total", "itemFrequencies"))
                .andExpect(model().attribute("pedidoItems", org.hamcrest.Matchers.hasSize(1)));
    }

    @Test
    @DisplayName("POST /RealizarPedido/marcarFavorito - cliente logueado marca restaurante")
    void marcarFavorito_usuarioLogueado_agregaRestauranteAFavoritos() throws Exception {
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();
        cliente.setFavoritos(new HashSet<>());
        Restaurante restaurante = new Restaurante();

        Mockito.when(loginService.findUsuarioById("cliente@mail.com")).thenReturn(usuario);
        Mockito.when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);
        Mockito.when(entityManager.find(Restaurante.class, 1L)).thenReturn(restaurante);

        mockMvc.perform(post("/RealizarPedido/marcarFavorito")
                        .sessionAttr("email", "cliente@mail.com")
                        .param("restauranteId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/RealizarPedido/ListaRestaurantes"));

        assertThat(cliente.getFavoritos()).contains(restaurante);
        Mockito.verify(loginService).updateCliente(cliente);
    }

    @Test
    @DisplayName("POST /RealizarPedido/marcarFavorito - sin sesión redirige con error")
    void marcarFavorito_sinSesion_redirigeLista() throws Exception {
        mockMvc.perform(post("/RealizarPedido/marcarFavorito")
                        .param("restauranteId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/RealizarPedido/ListaRestaurantes"));
    }

    @Test
    @DisplayName("POST /RealizarPedido/toggleFavorito - marca restaurante como favorito")
    void toggleFavorito_checkedTrue_agregaFavorito() throws Exception {
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente(); cliente.setFavoritos(new HashSet<>());
        Restaurante restaurante = new Restaurante();

        Mockito.when(loginService.findUsuarioById("cliente@mail.com")).thenReturn(usuario);
        Mockito.when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);
        Mockito.when(entityManager.find(Restaurante.class, 1L)).thenReturn(restaurante);

        mockMvc.perform(post("/RealizarPedido/toggleFavorito")
                        .param("restauranteId", "1")
                        .param("isChecked", "true")
                        .sessionAttr("email", "cliente@mail.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        assertThat(cliente.getFavoritos()).contains(restaurante);
        Mockito.verify(loginService).updateCliente(cliente);
    }

    @Test
    @DisplayName("POST /RealizarPedido/toggleFavorito - desmarca restaurante como favorito")
    void toggleFavorito_checkedFalse_eliminaFavorito() throws Exception {
        Usuario usuario = new Usuario();
        Restaurante restaurante = new Restaurante();
        Cliente cliente = new Cliente(); cliente.setFavoritos(new HashSet<>(Set.of(restaurante)));

        Mockito.when(loginService.findUsuarioById("cliente@mail.com")).thenReturn(usuario);
        Mockito.when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);
        Mockito.when(entityManager.find(Restaurante.class, 1L)).thenReturn(restaurante);

        mockMvc.perform(post("/RealizarPedido/toggleFavorito")
                        .param("restauranteId", "1")
                        .param("isChecked", "false")
                        .sessionAttr("email", "cliente@mail.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        assertThat(cliente.getFavoritos()).doesNotContain(restaurante);
        Mockito.verify(loginService).updateCliente(cliente);
    }

    @Test
    @DisplayName("POST /RealizarPedido/toggleFavorito - sin sesión devuelve Error")
    void toggleFavorito_sinSesion_devuelveError() throws Exception {
        mockMvc.perform(post("/RealizarPedido/toggleFavorito")
                        .param("restauranteId", "1")
                        .param("isChecked", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string("Error"));
    }

    @Test
    @DisplayName("POST /RealizarPedido/RestaurantePedido/realizarPedido - cliente válido y items válidos")
    void realizarPedido_clienteYItemsValidos_creaPedidoYRedirige() throws Exception {
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente(); cliente.setDirecciones(new HashSet<>());
        Restaurante restaurante = new Restaurante(1L);

        ItemPedido item = new ItemPedido();
        item.setNombre("Pizza");
        item.setCantidad(1);
        item.setPrecio(10.0);
        item.setTipo(TipoItemMenu.valueOf("Plato"));

        Pedido pedido = new Pedido();
        ReflectionTestUtils.setField(pedido, "id", 99L);

        Mockito.when(loginService.findUsuarioById("cliente@mail.com")).thenReturn(usuario);
        Mockito.when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);
        Mockito.when(restauranteService.obtenerRestaurantePorId(1L)).thenReturn(restaurante);
        Mockito.doAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            ReflectionTestUtils.setField(p, "id", 99L);
            return null;
        }).when(pedidoService).crearPedido(Mockito.any(Pedido.class));

        mockMvc.perform(post("/RealizarPedido/RestaurantePedido/realizarPedido")
                        .sessionAttr("email", "cliente@mail.com")
                        .param("restauranteId", "1")
                        .flashAttr("pedidoItems", List.of(item)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/RealizarPedido/PagoPedido?pedidoId=99"));

        Mockito.verify(itemPedidoDAO).save(Mockito.any(ItemPedido.class));
        Mockito.verify(pedidoService).crearPedido(Mockito.any(Pedido.class));
    }

    @Test
    @DisplayName("POST /RealizarPedido/RestaurantePedido/realizarPedido - sin cliente redirige a error")
    void realizarPedido_sinCliente_lanzaError() throws Exception {
        Mockito.when(loginService.findUsuarioById("cliente@mail.com")).thenReturn(new Usuario());
        Mockito.when(loginService.findClienteByUsuario(Mockito.any())).thenReturn(null);

        mockMvc.perform(post("/RealizarPedido/RestaurantePedido/realizarPedido")
                        .sessionAttr("email", "cliente@mail.com")
                        .param("restauranteId", "1")
                        .flashAttr("pedidoItems", List.of()))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    @DisplayName("POST /RealizarPedido/RestaurantePedido/realizarPedido - sin items redirige con error")
    void realizarPedido_sinItems_redirigeError() throws Exception {
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente(); cliente.setDirecciones(new HashSet<>());

        Mockito.when(loginService.findUsuarioById("cliente@mail.com")).thenReturn(usuario);
        Mockito.when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);

        mockMvc.perform(post("/RealizarPedido/RestaurantePedido/realizarPedido")
                        .sessionAttr("email", "cliente@mail.com")
                        .param("restauranteId", "1")
                        .flashAttr("pedidoItems", List.of()))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));

        Mockito.verify(pedidoService, Mockito.never()).crearPedido(Mockito.any());
        Mockito.verify(itemPedidoDAO, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("GET /RealizarPedido/PagoPedido - cliente y pedido válidos")
    void mostrarPagoPedido_clienteYPedidoValidos_devuelveVista() throws Exception {
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();
        Restaurante restaurante = new Restaurante();

        ItemPedido item = new ItemPedido();
        item.setCantidad(2);
        item.setPrecio(5.0);

        Pedido pedido = new Pedido();
        pedido.setItems(List.of(item));
        pedido.setRestaurante(restaurante);

        Mockito.when(loginService.findUsuarioById("cliente@mail.com")).thenReturn(usuario);
        Mockito.when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);
        Mockito.when(pedidoService.obtenerPedidoPorId(1L)).thenReturn(pedido);

        mockMvc.perform(get("/RealizarPedido/PagoPedido")
                        .param("pedidoId", "1")
                        .sessionAttr("email", "cliente@mail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("PagoPedido"))
                .andExpect(model().attributeExists("cliente", "pedido", "restaurante", "total"))
                .andExpect(model().attribute("total", 10.0));
    }

    @Test
    @DisplayName("POST /RealizarPedido/PagoPedido/realizarPago - datos correctos realiza pago y redirige")
    void realizarPago_datosValidos_creaPagoYRedirige() throws Exception {
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente(); cliente.setDirecciones(new HashSet<>());
        Restaurante restaurante = new Restaurante(); restaurante.setDireccion(new Direccion());

        Pedido pedido = new Pedido(); pedido.setRestaurante(restaurante);
        ReflectionTestUtils.setField(pedido, "id", 42L);

        Repartidor repartidor = new Repartidor(); repartidor.setDisponible(true);
        Direccion direccion = new Direccion();

        Mockito.when(loginService.findUsuarioById("cliente@mail.com")).thenReturn(usuario);
        Mockito.when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);
        Mockito.when(pedidoService.obtenerPedidoPorId(42L)).thenReturn(pedido);
        Mockito.when(repartoService.obtenerRepartidorDisponible()).thenReturn(repartidor);

        Mockito.when(pedidoService.guardarDireccion(Mockito.any())).thenReturn(direccion);
        Mockito.when(pedidoService.guardarPago(Mockito.any())).thenReturn(new Pago());
        Mockito.when(pedidoService.guardarServicioEntrega(Mockito.any())).thenReturn(new ServicioEntrega());

        mockMvc.perform(post("/RealizarPedido/PagoPedido/realizarPago")
                        .sessionAttr("email", "cliente@mail.com")
                        .param("pedidoId", "42")
                        .param("calle", "Calle Falsa")
                        .param("numero", "123")
                        .param("complemento", "Piso 4")
                        .param("municipio", "Madrid")
                        .param("codigoPostal", "CP_28000")
                        .param("metodoPago", "PAYPAL"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Mockito.verify(pedidoService).guardarDireccion(Mockito.any());
        Mockito.verify(pedidoService).guardarPago(Mockito.any());
        Mockito.verify(pedidoService).guardarServicioEntrega(Mockito.any());
        Mockito.verify(repartoService).actualizarRepartidor(repartidor);
    }

    @Test
    @DisplayName("POST /RealizarPedido/PagoPedido/realizarPago - cliente no encontrado redirige a error")
    void realizarPago_sinCliente_redirigeError() throws Exception {
        Usuario usuario = new Usuario();

        Mockito.when(loginService.findUsuarioById("cliente@mail.com")).thenReturn(usuario);
        Mockito.when(loginService.findClienteByUsuario(usuario)).thenReturn(null);

        mockMvc.perform(post("/RealizarPedido/PagoPedido/realizarPago")
                        .sessionAttr("email", "cliente@mail.com")
                        .param("pedidoId", "42")
                        .param("calle", "Calle Falsa")
                        .param("numero", "123")
                        .param("complemento", "Piso 4")
                        .param("municipio", "Madrid")
                        .param("codigoPostal", "CP_28000")
                        .param("metodoPago", "PAYPAL"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    @DisplayName("POST /RealizarPedido/PagoPedido/realizarPago - pedido no encontrado redirige a error")
    void realizarPago_sinPedido_redirigeError() throws Exception {
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();

        Mockito.when(loginService.findUsuarioById("cliente@mail.com")).thenReturn(usuario);
        Mockito.when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);
        Mockito.when(pedidoService.obtenerPedidoPorId(42L)).thenReturn(null);

        mockMvc.perform(post("/RealizarPedido/PagoPedido/realizarPago")
                        .sessionAttr("email", "cliente@mail.com")
                        .param("pedidoId", "42")
                        .param("calle", "Calle Falsa")
                        .param("numero", "123")
                        .param("complemento", "Piso 4")
                        .param("municipio", "Madrid")
                        .param("codigoPostal", "CP_28000")
                        .param("metodoPago", "PAYPAL"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    @DisplayName("GET /RealizarPedido/EstadoPedido - con sesión válida devuelve pedidos del cliente")
    void estadoPedido_conSesion_devuelvePedidos() throws Exception {
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        cliente.setPedidos(List.of(pedido1, pedido2));

        Mockito.when(loginService.findUsuarioById("cliente@mail.com")).thenReturn(usuario);
        Mockito.when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);

        mockMvc.perform(get("/RealizarPedido/EstadoPedido")
                        .sessionAttr("email", "cliente@mail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("EstadoPedido"))
                .andExpect(model().attributeExists("pedidos"))
                .andExpect(model().attribute("pedidos", org.hamcrest.Matchers.hasSize(2)));
    }

    @Test
    @DisplayName("GET /RealizarPedido/EstadoPedido - sin sesión redirige con error")
    void estadoPedido_sinSesion_redirigeError() throws Exception {
        mockMvc.perform(get("/RealizarPedido/EstadoPedido"))
                .andExpect(status().isOk())
                .andExpect(view().name("EstadoPedido"))
                .andExpect(model().attributeExists("pedidos"))
                .andExpect(model().attribute("pedidos", org.hamcrest.Matchers.empty()));
    }



}