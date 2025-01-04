package es.uclm.library.business.controller;

import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.CodigoPostal;
import es.uclm.library.business.service.ClienteService;
import es.uclm.library.business.service.RestauranteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class GestorClientes {

	private static final Logger logger = LoggerFactory.getLogger(GestorClientes.class);

	@Autowired
	private ClienteService clienteService;
	@Autowired
	private RestauranteService restauranteService;
}
