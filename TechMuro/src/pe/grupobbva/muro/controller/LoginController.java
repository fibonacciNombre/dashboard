package pe.grupobbva.muro.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pe.grupobbva.muro.entity.Usuario;
import pe.grupobbva.muro.service.UsuarioManager;

@Controller
public class LoginController {
	
	private static final Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private UsuarioManager usuarioManager;

	@RequestMapping(value = "/login/page.htm", method = RequestMethod.GET)
	public String cargaLogin(ModelMap map) {
		map.addAttribute("usuario", new Usuario());
		return "usuarios/login";
	}
	
	@RequestMapping(value = "/login/header.htm", method = RequestMethod.GET)
	public String cargaHeader(ModelMap map, HttpServletRequest request) {
		
		return "menu/header";
	}
	
	@RequestMapping(value = "/login/menu.htm", method = RequestMethod.GET)
	public String cargaMenu(ModelMap map) {
		
		return "menu/menu";
	}
	
	@RequestMapping(value = "/login/bienvenida.htm", method = RequestMethod.GET)
	public String cargaBienvenida(ModelMap map) {
		
		return "menu/bienvenida";
	}

	@RequestMapping(value = "/login/login.htm", method = RequestMethod.POST)
	public String verifyPass(@ModelAttribute(value = "usuario") Usuario usuario,
			BindingResult result, HttpServletRequest request) {

		try {
			usuario = usuarioManager.verifyPass(usuario);
			
			request.getSession().setAttribute("usuario", usuario);
			logger.debug("Ingreso exitoso");
			
		} catch (Exception e) {
			
			logger.error("Error en login ->"+e);
		}

		if (usuario == null) {
			return "redirect:/";
		}

		return "menu/plantilla";
	}
	
	@RequestMapping(value = "/login/logout.htm", method = RequestMethod.GET)
	public String salida(ModelMap map, HttpServletRequest request) {
		
		request.getSession().removeAttribute("usuario");
		
		return "redirect:/";
	}
}
