package es.uclm.library.controllerTest;

import es.uclm.library.business.controller.GestorLogin;
import es.uclm.library.business.entity.Usuario;
import es.uclm.library.business.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorLoginTest {

    @Mock
    private LoginService loginService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private GestorLogin gestorLogin;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("CU1 - idUsuario válido, pass válida, rol cliente")
    void testRolCliente() {
        Usuario usuario = new Usuario();
        usuario.setRol("cliente");

        when(loginService.authenticate("cliente1", "1234")).thenReturn(true);
        when(loginService.findUsuarioById("cliente1")).thenReturn(usuario);

        String resultado = gestorLogin.processLogin("cliente1", "1234", session, model);

        assertEquals("redirect:/login/BienvenidaUsuario", resultado);
        verify(session).setAttribute("email", "cliente1");
    }

    @Test
    @DisplayName("CU2 - idUsuario válido, pass incorrecta, rol restaurante")
    void testLoginFailConRolRestaurante() {
        Usuario usuario = new Usuario();
        usuario.setRol("restaurante");

        when(loginService.authenticate("cliente1", "fail")).thenReturn(false);

        String resultado = gestorLogin.processLogin("cliente1", "fail", session, model);

        assertEquals("redirect:/login", resultado);
        verify(model).addAttribute("error", "Credenciales incorrectas, intentalo de nuevo");
    }

    @Test
    @DisplayName("CU3 - idUsuario inválido, pass válida, rol repartidor")
    void testUsuarioInvalidoRepartidor() {
        when(loginService.authenticate("none", "1234")).thenReturn(false);

        String resultado = gestorLogin.processLogin("none", "1234", session, model);

        assertEquals("redirect:/login", resultado);
        verify(model).addAttribute("error", "Credenciales incorrectas, intentalo de nuevo");
    }

    @Test
    @DisplayName("CU4 - campos vacíos, rol admin")
    void testCamposVaciosRolAdmin() {
        when(loginService.authenticate("", "")).thenReturn(false);

        String resultado = gestorLogin.processLogin("", "", session, model);

        assertEquals("redirect:/login", resultado);
        verify(model).addAttribute("error", "Credenciales incorrectas, intentalo de nuevo");
    }

    @Test
    @DisplayName("CU5 - idUsuario válido, pass válida, rol null")
    void testRolNull() {
        Usuario usuario = new Usuario();
        usuario.setRol(null);

        when(loginService.authenticate("cliente1", "1234")).thenReturn(true);
        when(loginService.findUsuarioById("cliente1")).thenReturn(usuario);

        String resultado = gestorLogin.processLogin("cliente1", "1234", session, model);

        assertEquals("redirect:/", resultado);
    }
}