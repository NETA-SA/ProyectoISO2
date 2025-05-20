package es.uclm.library.controllerTest;

import es.uclm.library.business.controller.GestorRegistro;
import es.uclm.library.business.entity.*;
import es.uclm.library.business.service.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorRegistroTest {

    @InjectMocks
    private GestorRegistro gestorRegistro;

    @Mock
    private LoginService loginService;

    @Mock
    private EntityManager entityManager;

    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        model = new BindingAwareModelMap();
    }

    @Test
    @DisplayName("CU1 - Rol cliente, email nuevo, password definido → registro cliente exitoso")
    void testRegister_CU1_ClienteNuevo_Exito() {
        String role = "cliente";
        String email = "nuevo@correo.com";
        String password = "1234";
        String nombre = "Juan";
        String apellidos = "Pérez";
        String dni = "12345678A";

        when(loginService.findUsuarioById(email)).thenReturn(null);

        String vista = gestorRegistro.register(
                role, email, password,
                nombre, apellidos, dni,
                null, null, null, null, null,
                null, null,
                model
        );

        assertEquals("Registro", vista);
        assertNotNull(model.getAttribute("successMessage"));
        verify(loginService).registerCliente(any(Cliente.class));
    }

    @Test
    @DisplayName("CU2 - Rol restaurante, usuario ya existente, CP y municipio válidos → registro exitoso")
    void testRegister_CU2_RestauranteExistente_Exito() {
        String role = "restaurante";
        String email = "existente@correo.com";
        String password = "1234";
        String nombre = "La Esquina";
        String cif = "B12345678";
        String calle = "Mayor";
        String numero = "7";
        String complemento = "";
        String municipio = "Talavera";
        String codigoPostal = "45600";

        Usuario usuario = new Usuario();
        when(loginService.findUsuarioById(email)).thenReturn(usuario);

        String vista = gestorRegistro.register(
                role, email, password,
                null, null, null,
                nombre, cif, calle, numero, complemento,
                municipio, codigoPostal,
                model
        );

        assertEquals("Registro", vista);
        assertNotNull(model.getAttribute("successMessage"));
        verify(loginService).registerRestaurante(any(Restaurante.class));
    }

    @Test
    @DisplayName("CU3 - Rol repartidor, email nuevo, password nulo → registro exitoso")
    void testRegister_CU3_Repartidor_PasswordNulo() {
        String role = "repartidor";
        String email = "nuevo@correo.com";
        String password = null;

        when(loginService.findUsuarioById(email)).thenReturn(null);

        String vista = gestorRegistro.register(
                role, email, password,
                null, null, null,
                null, null, null, null, null,
                null, null,
                model
        );

        assertEquals("Registro", vista);
        assertNotNull(model.getAttribute("successMessage"));
        verify(loginService).registerRepartidor(any(Repartidor.class));
    }

    @Test
    @DisplayName("CU4 - Rol inválido (anonimo) → muestra mensaje de error sin lanzar excepción")
    void testRegister_CU4_RolInvalido_ErrorModel() {
        String role = "anonimo";
        String email = "nuevo@correo.com";
        String password = "1234";
        String municipio = "Madrid";
        String codigoPostal = "00000";

        when(loginService.findUsuarioById(email)).thenReturn(null);

        String vista = gestorRegistro.register(
                role, email, password,
                null, null, null,
                null, null, null, null, null,
                municipio, codigoPostal,
                model
        );

        assertEquals("Registro", vista);
        assertNotNull(model.getAttribute("errorMessage"));
    }

}
