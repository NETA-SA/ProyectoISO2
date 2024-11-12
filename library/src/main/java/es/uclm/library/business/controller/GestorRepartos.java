package es.uclm.library.business.controller;

import es.uclm.library.business.entity.CodigoPostal;
import es.uclm.library.business.entity.ServicioEntrega;
import es.uclm.library.business.service.RepartoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/repartos")
public class GestorRepartos {

	private static final Logger logger = LoggerFactory.getLogger(GestorRepartos.class);

	@Autowired
	private RepartoService repartoService;

}
