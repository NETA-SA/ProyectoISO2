package es.uclm.library.business.controller;

import es.uclm.library.business.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class GestorLogin {

	private static final Logger logger = LoggerFactory.getLogger(GestorLogin.class);

	@Autowired
	private LoginService loginService;


}
