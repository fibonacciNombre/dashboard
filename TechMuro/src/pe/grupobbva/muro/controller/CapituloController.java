package pe.grupobbva.muro.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.grupobbva.muro.entity.Capitulo;
import pe.grupobbva.muro.entity.Nota;
import pe.grupobbva.muro.service.CapituloManager;
import pe.grupobbva.muro.service.NotaManager;
import pe.grupobbva.muro.service.ProductoManager;
import pe.grupobbva.muro.service.VariableManager;

import com.google.gson.Gson;

@Controller
public class CapituloController {
	
	private static final Logger logger = Logger.getLogger(CapituloController.class);

	@Autowired
	private ProductoManager productoManager;
	
	@Autowired
	private CapituloManager capituloManager;
	
	@Autowired
	private VariableManager variableManager;
	
	@Autowired
	private NotaManager notaManager;

	@RequestMapping(value = "/tarifario/capitulo/list.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String cargaLista(ModelMap map, HttpServletRequest request) {
		try{
			String id = request.getParameter("id");if(id==null)id="0";
			
			
			if(id!="0"){
				Capitulo capitulo = new Capitulo();
				capitulo.setProducto(productoManager.finById(Integer.parseInt(id)));
				map.addAttribute("capitulo", capitulo);
				map.addAttribute("capituloList",capituloManager.findByProducto(Integer.parseInt(id)));
				map.addAttribute("productoList", productoManager.buscarProducto("%", capitulo.getProducto().getTipocliente()));
			}else{
				map.addAttribute("capitulo", new Capitulo());
			}
			
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
		}catch(Exception e){
			logger.error("Error cargaLista -> "+e);
		}
		
		return "tarifario/capitulo";
	}
	
	@RequestMapping(value = "/tarifario/capitulo/buscar.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaCapitulo(@ModelAttribute(value="capitulo") Capitulo capitulo, ModelMap map) {
		
		List<Capitulo> capitulos = capituloManager.buscarCapitulo(capitulo);
		
		for(Capitulo cap: capitulos){
			cap.setSubcapitulos(null);
			cap.setNotaByIdnotainicial(null);
			cap.setNotaByIdnotafinal(null);
			cap.setProducto(null);
		}
		
		Gson g = new Gson();
		logger.debug(g.toJson(capitulos));
		return g.toJson(capitulos);
	}
	
	@RequestMapping(value = "/tarifario/capitulo/cargaForm.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaForm(@ModelAttribute(value="capitulo") Capitulo capitulo, ModelMap map, 
				HttpServletRequest request) {
		
		try {
			
			String id = request.getParameter("id");if(id==null)id="";
			if(id=="0"||id==""){
				capitulo.setNombre("");
				capitulo.setFechacreacion(new Date());
			}else{
				capitulo = capituloManager.finById(Integer.parseInt(id));
			}
			
			map.addAttribute("productoList", productoManager.buscarProducto("%", capitulo.getProducto().getTipocliente()));
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
			map.addAttribute("capitulo", capitulo);
			
		} catch (Exception e) {
			logger.error("Error cargaForm -> "+e);
		}	
		return "tarifario/capituloForm";
	}
	
	@RequestMapping(value = "/tarifario/capitulo/up.htm", method = RequestMethod.POST)
	public @ResponseBody String capituloUp(ModelMap map, @RequestParam("id") Integer idCapitulo) {
		
		return capituloManager.up(idCapitulo)+"";
	}
	
	@RequestMapping(value = "/tarifario/capitulo/down.htm", method = RequestMethod.POST)
	public @ResponseBody String capituloDown(ModelMap map, @RequestParam("id") Integer idCapitulo) {
		
		return capituloManager.down(idCapitulo)+"";
	}
	
	@RequestMapping(value = "/tarifario/capitulo/agregar.htm", method = RequestMethod.POST)
	public String agregarCapitulo(@ModelAttribute(value="capitulo") Capitulo capitulo, HttpServletRequest request) {
		try{
		    String result="";
			if(capitulo.getIdcapitulo()==null){
				result = capituloManager.guardarCapitulo(capitulo);
			}else{
				result = capituloManager.editarCapitulo(capitulo);
			}
			
			request.setAttribute("result", result);
			return "result";
		}catch(Exception e){
			logger.error("Error agregar Capitulo ->"+e);
			request.setAttribute("result", "Error agregar Capitulo");
			return "result";
		}
	}
	
	@RequestMapping(value = "/tarifario/capitulo/eliminar.htm", method = RequestMethod.POST)
	public @ResponseBody String eliminarCapitulo(HttpServletRequest request) {
		String id = request.getParameter("id");if(id==null)id="0";
		
		return ""+capituloManager.eliminarCapitulo(Integer.parseInt(id));
	}
	
	@RequestMapping(value = "/tarifario/capitulo/cargaNotas.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public String cargaNotas(ModelMap map, HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			
			Capitulo cap = capituloManager.finById(Integer.parseInt(id));
			
			if(cap.getNotaByIdnotainicial()!=null){
				map.addAttribute("notaSuperior", cap.getNotaByIdnotainicial());
			}else{
				map.addAttribute("notaSuperior", new Nota());
			}
			if(cap.getNotaByIdnotafinal()!=null){
				map.addAttribute("notaInferior", cap.getNotaByIdnotafinal());
			}else{
				map.addAttribute("notaInferior", new Nota());
			}
			
			request.setAttribute("idCapitulo", id);
		} catch (Exception e) {
			logger.error("Error cargaNotas -> "+e);
		}	
		return "tarifario/notasForm";
	}
	
	@RequestMapping(value = "/tarifario/capitulo/agregarNotaSuperior.htm", method = RequestMethod.POST)
	public String agregarNotaSuperior(@ModelAttribute(value="notaSuperior") Nota nota, ModelMap map, HttpServletRequest request) {
		
		try {
			if(nota.getIdnota()==null){
				nota.setEstado("A".charAt(0));
				nota.setFechacreacion(new Date());
				notaManager.add(nota);
				int idCapitulo = Integer.parseInt(request.getParameter("idCapitulo"));
				Capitulo cap = capituloManager.finById(idCapitulo);
				cap.setNotaByIdnotainicial(nota);
				capituloManager.update(cap);
			}else{
				Nota notaTemp = notaManager.finById(nota.getIdnota());
				notaTemp.setTitulo(nota.getTitulo());
				notaTemp.setDescripcion(nota.getDescripcion());
				notaManager.update(notaTemp);
			}
			map.addAttribute("result", "true");
		} catch (Exception e) {
			logger.error("Error al insertar nota");
			map.addAttribute("result", "false");
		}
		
		return "result";
	}
	
	@RequestMapping(value = "/tarifario/capitulo/agregarNotaInferior.htm", method = RequestMethod.POST)
	public String agregarNotaInferior(@ModelAttribute(value="notaInferior") Nota nota, HttpServletRequest request) {
		
		try {
			if(nota.getIdnota()==null){
				nota.setEstado("A".charAt(0));
				nota.setFechacreacion(new Date());
				notaManager.add(nota);
				int idCapitulo = Integer.parseInt(request.getParameter("idCapitulo"));
				Capitulo cap = capituloManager.finById(idCapitulo);
				cap.setNotaByIdnotafinal(nota);
				capituloManager.update(cap);
			}else{
				Nota notaTemp = notaManager.finById(nota.getIdnota());
				notaTemp.setTitulo(nota.getTitulo());
				notaTemp.setDescripcion(nota.getDescripcion());
				notaManager.update(notaTemp);
			}
			request.setAttribute("result", "true");
		} catch (Exception e) {
			logger.error("Error al insertar nota");
			request.setAttribute("result", "false");
		}
		
		return "result";
	}
	
}
