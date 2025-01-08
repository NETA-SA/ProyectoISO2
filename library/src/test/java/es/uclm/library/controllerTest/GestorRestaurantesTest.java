package es.uclm.library.controllerTest;
import es.uclm.library.business.controller.GestorRestaurantes;
import es.uclm.library.business.entity.*;
import es.uclm.library.business.service.RestauranteService;
import es.uclm.library.persistence.ItemMenuDAO;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorRestaurantesTest {

    @InjectMocks
    private GestorRestaurantes gestorRestaurantes;

    @Mock
    private RestauranteService restauranteService;

    @Mock
    private ItemMenuDAO itemMenuDAO;

    @Mock
    private HttpSession session;

    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        model = new BindingAwareModelMap();
    }

    @Test
    void testMostrarPaginaRestaurantes() {
        // Arrange
        String email = "restaurante@example.com";
        Long idRestaurante = 1L;
        when(session.getAttribute("email")).thenReturn(email);
        when(restauranteService.obtenerIdRestaurantePorUsuario(email)).thenReturn(idRestaurante);

        // Act
        String viewName = gestorRestaurantes.mostrarPaginaRestaurantes(session, model);

        // Assert
        assertEquals("RestaurantesPag", viewName);
        assertEquals(idRestaurante, model.getAttribute("idRestaurante"));
    }

    @Test
    void testMostrarRestaurantePedido() {
        // Arrange
        Long restauranteId = 1L;
        Long cartaId = 2L;
        List<CartaMenu> cartasMenu = List.of(new CartaMenu());
        CartaMenu cartaMenu = new CartaMenu();
        when(restauranteService.obtenerCartasPorRestaurante(restauranteId)).thenReturn(cartasMenu);
        when(restauranteService.obtenerCartaPorId(cartaId)).thenReturn(cartaMenu);

        // Act
        String viewName = gestorRestaurantes.mostrarRestaurantePedido(restauranteId, cartaId, model);

        // Assert
        assertEquals("RestaurantePedido", viewName);
        assertEquals(cartasMenu, model.getAttribute("cartasMenu"));
        assertEquals(restauranteId, model.getAttribute("restauranteId"));
        assertEquals(cartaMenu.getItems(), model.getAttribute("items"));
    }

    @Test
    void testGuardarMenuExitoso() {
        // Arrange
        String email = "restaurante@example.com";
        Long idRestaurante = 1L;
        ItemMenu itemMenu = new ItemMenu();
        BindingResult result = mock(BindingResult.class);
        when(session.getAttribute("email")).thenReturn(email);
        when(restauranteService.obtenerIdRestaurantePorUsuario(email)).thenReturn(idRestaurante);
        when(result.hasErrors()).thenReturn(false);

        // Act
        String viewName = gestorRestaurantes.guardarMenu(session, itemMenu, result, null, null, model);

        // Assert
        assertEquals("DarAltaMenu", viewName);
        verify(itemMenuDAO, times(1)).save(itemMenu);
    }

    @Test
    void testListarRestaurantes() {
        // Arrange
        List<Restaurante> listaRestaurantes = List.of(new Restaurante());
        when(restauranteService.obtenerTodosRestaurantes()).thenReturn(listaRestaurantes);

        // Act
        String viewName = gestorRestaurantes.listarRestaurantes(model);

        // Assert
        assertEquals("ListaRestaurantes", viewName);
        assertEquals(listaRestaurantes, model.getAttribute("restaurantes"));
    }

    @Test
    void testMostrarCartas() {
        // Arrange
        String email = "restaurante@example.com";
        Long idRestaurante = 1L;
        List<CartaMenu> cartasMenu = List.of(new CartaMenu());
        when(session.getAttribute("email")).thenReturn(email);
        when(restauranteService.obtenerIdRestaurantePorUsuario(email)).thenReturn(idRestaurante);
        when(restauranteService.obtenerCartasPorRestaurante(idRestaurante)).thenReturn(cartasMenu);

        // Act
        String viewName = gestorRestaurantes.mostrarCartas(session, model);

        // Assert
        assertEquals("verCartas", viewName);
        assertEquals(cartasMenu, model.getAttribute("cartasMenu"));
    }
}
