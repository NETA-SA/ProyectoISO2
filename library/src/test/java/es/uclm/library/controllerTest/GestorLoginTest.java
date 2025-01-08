package es.uclm.library.controllerTest;
import es.uclm.library.business.controller.GestorLogin;
import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.Usuario;
import es.uclm.library.business.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorLoginTest {

    @InjectMocks
    private GestorLogin gestorLogin;

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
    void testShowLoginForm() {
        // Act
        String viewName = gestorLogin.showLoginForm(model);

        // Assert
        assertEquals("login", viewName);
        assertNotNull(model.getAttribute("usuario"));
    }

    @Test
    void testProcessLoginSuccessCliente() {
        // Arrange
        String idUsuario = "cliente@example.com";
        String pass = "password";
        Usuario usuario = new Usuario();
        usuario.setRol("cliente");
        when(loginService.authenticate(idUsuario, pass)).thenReturn(true);
        when(loginService.findUsuarioById(idUsuario)).thenReturn(usuario);

        // Act
        String viewName = gestorLogin.processLogin(idUsuario, pass, session, model);

        // Assert
        assertEquals("redirect:/login/BienvenidaUsuario", viewName);
        verify(session, times(1)).setAttribute("email", idUsuario);
    }

    @Test
    void testProcessLoginFailure() {
        // Arrange
        String idUsuario = "cliente@example.com";
        String pass = "wrongPassword";
        when(loginService.authenticate(idUsuario, pass)).thenReturn(false);

        // Act
        String viewName = gestorLogin.processLogin(idUsuario, pass, session, model);

        // Assert
        assertEquals("redirect:/login", viewName);
        assertNotNull(model.getAttribute("error"));
    }

    @Test
    void testBienvenidaUsuario() {
        // Arrange
        String email = "cliente@example.com";
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan Pérez");
        when(session.getAttribute("email")).thenReturn(email);
        when(loginService.findUsuarioById(email)).thenReturn(usuario);
        when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);

        // Act
        String viewName = gestorLogin.bienvenidaUsuario(session, model);

        // Assert
        assertEquals("BienvenidaUsuario", viewName);
        assertEquals("Juan Pérez", model.getAttribute("nombreUsuario"));
    }
}
