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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestorRestaurantesTest {

    @InjectMocks
    private GestorRestaurantes gestorRestaurantes;

    @Mock
    private RestauranteService restauranteService;

    @Mock
    private ItemMenuDAO itemMenuDAO;

    @Mock
    private HttpSession session;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    private ItemMenu itemMenu;

    @BeforeEach
    void setUp() {
        itemMenu = new ItemMenu();
    }

    @Test
    void test_CP1_HasErrorsTrue() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = gestorRestaurantes.guardarMenu(session, itemMenu, bindingResult, null, null, model);

        assertEquals("DarAltaMenu", view);
        verify(model).addAttribute(eq("error"), anyString());
        verifyNoInteractions(restauranteService, itemMenuDAO);
    }

    @Test
    void test_CP2_NoErrors_NuevaCarta_McMenu_CartaId_0() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(session.getAttribute("email")).thenReturn("usuario@email.com");
        when(restauranteService.obtenerIdRestaurantePorUsuario("usuario@email.com")).thenReturn(1L);

        CartaMenu nuevaCarta = new CartaMenu();
        nuevaCarta.setNombre("McMenu");
        nuevaCarta.setRestaurante(new Restaurante(1L));

        when(restauranteService.guardarNuevaCarta(any(CartaMenu.class))).thenReturn(nuevaCarta);
        when(restauranteService.cartaExiste("McMenu", 1L)).thenReturn(false);
        when(restauranteService.obtenerCartasPorRestaurante(1L)).thenReturn(List.of());

        String view = gestorRestaurantes.guardarMenu(session, itemMenu, bindingResult, "McMenu", 0L, model);

        assertEquals("DarAltaMenu", view);
        verify(model).addAttribute("success", "Ítem agregado con éxito.");
    }

    @Test
    void test_CP3_HasErrorsTrue_CartaMenuId50() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = gestorRestaurantes.guardarMenu(session, itemMenu, bindingResult, null, 50L, model);

        assertEquals("DarAltaMenu", view);
        verify(model).addAttribute(eq("error"), anyString());
        verifyNoInteractions(restauranteService, itemMenuDAO);
    }

    @Test
    void test_CP4_NoErrors_NuevaCarta_McMenu_CartaIdNegativo() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(session.getAttribute("email")).thenReturn("usuario@email.com");
        when(restauranteService.obtenerIdRestaurantePorUsuario("usuario@email.com")).thenReturn(1L);

        CartaMenu nuevaCarta = new CartaMenu();
        nuevaCarta.setNombre("McMenu");
        nuevaCarta.setRestaurante(new Restaurante(1L));

        when(restauranteService.guardarNuevaCarta(any(CartaMenu.class))).thenReturn(nuevaCarta);
        when(restauranteService.cartaExiste("McMenu", 1L)).thenReturn(false);
        when(restauranteService.obtenerCartasPorRestaurante(1L)).thenReturn(List.of());

        String view = gestorRestaurantes.guardarMenu(session, itemMenu, bindingResult, "McMenu", -50L, model);

        assertEquals("DarAltaMenu", view);
        verify(model).addAttribute("success", "Ítem agregado con éxito.");
    }

    @Test
    void test_CP5_ErroresValidacionYParametrosNulos() {
        when(bindingResult.hasErrors()).thenReturn(true);
        when(session.getAttribute("email")).thenReturn(null);

        String view = gestorRestaurantes.guardarEdicionItem(session, itemMenu, bindingResult, null, model);

        assertEquals("editarItem", view);
        verify(model).addAttribute("error", "Error en la edición del ítem.");
        verify(model).addAttribute(eq("item"), eq(itemMenu));
        verify(restauranteService).obtenerCartasPorRestaurante(any()); // el método debe ser llamado con un ID nulo
    }

    @Test
    void test_CP6_SinErrores_CartaNoEncontrada_porIdNegativo() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(session.getAttribute("email")).thenReturn("admin");
        when(restauranteService.obtenerIdRestaurantePorUsuario("admin")).thenReturn(1L);
        when(restauranteService.obtenerCartaPorId(-50L)).thenReturn(null);

        String view = gestorRestaurantes.guardarEdicionItem(session, itemMenu, bindingResult, -50L, model);

        assertEquals("editarItem", view);
        verify(model).addAttribute("error", "Carta no encontrada.");
        verify(model).addAttribute(eq("item"), eq(itemMenu));
        verify(restauranteService).obtenerCartasPorRestaurante(1L);
    }

    @Test
    void test_CP7_SinErrores_CartaRecuperadaCorrectamente() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(session.getAttribute("email")).thenReturn("admin");
        when(restauranteService.obtenerIdRestaurantePorUsuario("admin")).thenReturn(1L);

        Restaurante restaurante = new Restaurante(1L);
        CartaMenu carta = new CartaMenu();
        carta.setId(50L);
        carta.setRestaurante(restaurante);
        carta.setItems(new java.util.ArrayList<>());

        when(restauranteService.obtenerCartaPorId(50L)).thenReturn(carta);

        String view = gestorRestaurantes.guardarEdicionItem(session, itemMenu, bindingResult, 50L, model);

        assertEquals("RestaurantesPag", view);
        verify(itemMenuDAO).save(itemMenu);
        verify(restauranteService).actualizarCarta(carta);
        verify(model).addAttribute("success", "Ítem editado correctamente.");
    }
}
