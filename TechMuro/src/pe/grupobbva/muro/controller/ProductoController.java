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

import pe.grupobbva.muro.entity.Notaria;
import pe.grupobbva.muro.entity.Producto;
import pe.grupobbva.muro.service.ProductoManager;
import pe.grupobbva.muro.service.VariableManager;

import com.google.gson.Gson;

@Controller
public class ProductoController {
	
	private static final Logger logger = Logger.getLogger(ProductoController.class);

	@Autowired
	private ProductoManager productoManager;
	
	@Autowired
	private VariableManager variableManager;

	@RequestMapping(value = "/tarifario/producto/list.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String cargaLista(ModelMap map) {
		
		map.addAttribute("producto", new Producto());
		map.addAttribute("tipoCliente", variableManager.tipoClienteList());
		
		return "tarifario/producto";
	}
	
	@RequestMapping(value = "/tarifario/producto/buscar.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaProducto(@ModelAttribute(value="producto") Producto producto, ModelMap map, HttpServletRequest request) {
		
		String tipoCliente = producto.getTipocliente();if(tipoCliente==null)tipoCliente="";
		String criterio = producto.getNombre();if(criterio==null)criterio="";
		
		return new Gson().toJson(productoManager.buscarProducto(criterio+"%", tipoCliente+"%"));
	}
	
	@RequestMapping(value = "/tarifario/producto/combo.htm", method = RequestMethod.POST)
	public @ResponseBody String cargaCombo(HttpServletRequest request) {
		
		String tipoCliente = request.getParameter("tipoCliente");if(tipoCliente==null)tipoCliente="";
		
		return new Gson().toJson(productoManager.buscarProducto("%", tipoCliente));
	}
	
	@RequestMapping(value = "/tarifario/producto/cargaForm.htm", method = RequestMethod.POST)
	public String cargaForm(ModelMap map, HttpServletRequest request) {
		
		try {
			
			String id = request.getParameter("id");if(id==null)id="";
			Producto producto;
			if(id=="0"||id==""){
				producto = new Producto();
				producto.setFechacreacion(new Date());
			}else{
				producto = productoManager.finById(Integer.parseInt(id));
			}
			
			map.addAttribute("tipoCliente", variableManager.tipoClienteList());
			map.addAttribute("producto", producto);
		} catch (Exception e) {
			logger.error("Error cargaForm -> "+e);
		}	
		return "tarifario/productoForm";
	}
	
	@RequestMapping(value = "/tarifario/producto/up.htm", method = RequestMethod.POST)
	public @ResponseBody String productoUp(ModelMap map, @RequestParam("id") Integer idProducto) {
		
		return productoManager.up(idProducto)+"";
	}
	
	@RequestMapping(value = "/tarifario/producto/down.htm", method = RequestMethod.POST)
	public @ResponseBody String productoDown(ModelMap map, @RequestParam("id") Integer idProducto) {
		
		return productoManager.down(idProducto)+"";
	}
	
	@RequestMapping(value = "/tarifario/producto/agregar.htm", method = RequestMethod.POST)
	public String agregarProducto(@ModelAttribute(value="producto") Producto producto, HttpServletRequest request) {
		try{
		    String result="";
			if(producto.getIdproducto()==null){
				result =  productoManager.guardarProducto(producto);
			}else{
				result = productoManager.editarProducto(producto);
			}
			
			request.setAttribute("result", result);
			return "result";
		}catch(Exception e){
			logger.error("Error agregar Producto ->"+e);
			request.setAttribute("result", "false");
			return "result";
		}
	}
	
	@RequestMapping(value = "/tarifario/producto/eliminar.htm", method = RequestMethod.POST)
	public @ResponseBody String eliminarNotaria(HttpServletRequest request) {
		String id = request.getParameter("id");if(id==null)id="0";
		
		return ""+productoManager.eliminarProducto(Integer.parseInt(id));
	}
	
}
