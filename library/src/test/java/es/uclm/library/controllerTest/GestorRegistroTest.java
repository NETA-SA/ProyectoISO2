package es.uclm.library.controllerTest;
import es.uclm.library.business.controller.GestorRegistro;
import es.uclm.library.business.service.LoginService;
import es.uclm.library.business.entity.*;
import jakarta.persistence.EntityManager;
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
    void testShowRegistrationForm() {
        // Act
        String viewName = gestorRegistro.showRegistrationForm();

        // Assert
        assertEquals("Registro", viewName);
    }

    @Test
    void testRegisterClienteSuccess() {
        // Arrange
        String role = "cliente";
        String email = "cliente@example.com";
        String password = "password";
        String clienteNombre = "Juan";
        String apellidos = "Pérez";
        String dni = "12345678A";
        when(loginService.findUsuarioById(email)).thenReturn(null);

        // Act
        String viewName = gestorRegistro.register(role, email, password, clienteNombre, apellidos, dni, null, null, null, null, null, null, null, model);

        // Assert
        assertEquals("Registro", viewName);
        assertNotNull(model.getAttribute("successMessage"));
        verify(loginService, times(1)).registerCliente(any(Cliente.class));
    }

    @Test
    void testRegisterRestauranteInvalidCodigoPostal() {
        // Arrange
        String role = "restaurante";
        String email = "restaurante@example.com";
        String password = "password";
        String restauranteNombre = "Restaurante Gourmet";
        String cif = "B12345678";
        String calle = "Avenida Principal";
        String numero = "10";
        String municipio = "Ciudad";
        String codigoPostal = "12345";
        when(loginService.findUsuarioById(email)).thenReturn(null);
        doThrow(IllegalArgumentException.class).when(loginService).registerRestaurante(any(Restaurante.class));

        // Act
        String viewName = gestorRegistro.register(role, email, password, null, null, null, restauranteNombre, cif, calle, numero, null, municipio, codigoPostal, model);

        // Assert
        assertEquals("Registro", viewName);
        assertNotNull(model.getAttribute("errorMessage"));
    }

    @Test
    void testRegisterRepartidorSuccess() {
        // Arrange
        String role = "repartidor";
        String email = "repartidor@example.com";
        String password = "password";
        when(loginService.findUsuarioById(email)).thenReturn(null);

        // Act
        String viewName = gestorRegistro.register(role, email, password, null, null, null, null, null, null, null, null, null, null, model);

        // Assert
        assertEquals("Registro", viewName);
        assertNotNull(model.getAttribute("successMessage"));
        verify(loginService, times(1)).registerRepartidor(any(Repartidor.class));
    }

    @Test
    void testRegisterUserAlreadyExists() {
        // Arrange
        String role = "cliente";
        String email = "cliente@example.com";
        String password = "password";
        when(loginService.findUsuarioById(email)).thenReturn(new Usuario());

        // Act
        String viewName = gestorRegistro.register(role, email, password, "Juan", "Pérez", "12345678A", null, null, null, null, null, null, null, model);

        // Assert
        assertEquals("Registro", viewName);
        assertNotNull(model.getAttribute("successMessage"));
    }
}
