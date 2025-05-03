package es.uclm.library.controllerTest;

import es.uclm.library.business.controller.GestorLogin;
import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.Usuario;
import es.uclm.library.business.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Test unitarios para GestorLogin")
@WebMvcTest(GestorLogin.class)
class GestorLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    private MockHttpSession session;

    @BeforeEach
    void setup() {
        session = new MockHttpSession();
    }

    @Nested
    @DisplayName("Tests para showLoginForm")
    class ShowLoginFormTests {
        @Test
        @DisplayName("Debería mostrar el formulario de login correctamente")
        void testShowLoginForm() throws Exception {
            mockMvc.perform(get("/login"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("login"))
                    .andExpect(model().attributeExists("usuario"))
                    .andExpect(model().attribute("usuario", instanceOf(Usuario.class)));
        }
    }

    @Nested
    @DisplayName("Tests para processLogin")
    class ProcessLoginTests {

        @Test
        @DisplayName("Login exitoso como cliente")
        void testProcessLoginCliente() throws Exception {
            when(loginService.authenticate("cliente1", "1234")).thenReturn(true);
            Usuario usuario = new Usuario();
            usuario.setRol("cliente");
            when(loginService.findUsuarioById("cliente1")).thenReturn(usuario);

            mockMvc.perform(post("/login")
                            .param("idUsuario", "cliente1")
                            .param("pass", "1234")
                            .session(session))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/login/BienvenidaUsuario"));
        }

        @Test
        @DisplayName("Login exitoso como restaurante")
        void testProcessLoginRestaurante() throws Exception {
            when(loginService.authenticate("rest1", "abcd")).thenReturn(true);
            Usuario usuario = new Usuario();
            usuario.setRol("restaurante");
            when(loginService.findUsuarioById("rest1")).thenReturn(usuario);

            mockMvc.perform(post("/login")
                            .param("idUsuario", "rest1")
                            .param("pass", "abcd")
                            .session(session))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/restaurantes/RestaurantesPag"));
        }

        @Test
        @DisplayName("Login exitoso como repartidor")
        void testProcessLoginRepartidor() throws Exception {
            when(loginService.authenticate("reparto1", "pass")).thenReturn(true);
            Usuario usuario = new Usuario();
            usuario.setRol("repartidor");
            when(loginService.findUsuarioById("reparto1")).thenReturn(usuario);

            mockMvc.perform(post("/login")
                            .param("idUsuario", "reparto1")
                            .param("pass", "pass")
                            .session(session))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/Repartos"));
        }

        @Test
        @DisplayName("Login exitoso pero rol desconocido")
        void testProcessLoginRolDesconocido() throws Exception {
            when(loginService.authenticate("admin1", "admin")).thenReturn(true);
            Usuario usuario = new Usuario();
            usuario.setRol("admin");
            when(loginService.findUsuarioById("admin1")).thenReturn(usuario);

            mockMvc.perform(post("/login")
                            .param("idUsuario", "admin1")
                            .param("pass", "admin")
                            .session(session))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));
        }

        @Test
        @DisplayName("Login fallido")
        void testProcessLoginFallido() throws Exception {
            when(loginService.authenticate("cliente1", "fail")).thenReturn(false);

            mockMvc.perform(post("/login")
                            .param("idUsuario", "cliente1")
                            .param("pass", "1235")
                            .session(session))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/login"));
        }
    }

    @Nested
    @DisplayName("Tests para bienvenidaUsuario")
    class BienvenidaUsuarioTests {

        @Test
        @DisplayName("Debería mostrar la bienvenida del usuario correctamente")
        void testBienvenidaUsuario() throws Exception {
            session.setAttribute("email", "cliente1@mail.com");

            Usuario usuario = new Usuario();
            Cliente cliente = new Cliente();
            cliente.setNombre("Juan");

            when(loginService.findUsuarioById("cliente1@mail.com")).thenReturn(usuario);
            when(loginService.findClienteByUsuario(usuario)).thenReturn(cliente);

            mockMvc.perform(get("/login/BienvenidaUsuario").session(session))
                    .andExpect(status().isOk())
                    .andExpect(view().name("BienvenidaUsuario"))
                    .andExpect(model().attributeExists("nombreUsuario"))
                    .andExpect(model().attribute("nombreUsuario", "Juan"));
        }
    }
}
