package pe.grupobbva.muro.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import pe.grupobbva.muro.entity.Contrato;
import pe.grupobbva.muro.entity.Notaria;
import pe.grupobbva.muro.model.UploadedFile;
import pe.grupobbva.muro.service.ContratoManager;
import pe.grupobbva.muro.service.NotariaManager;
import pe.grupobbva.muro.service.PlazaManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class NotariaController {
	
	private static final Logger logger = Logger.getLogger(NotariaController.class);

	@Autowired
	private NotariaManager notariaManager;
	@Autowired
	private PlazaManager plazaManager;
	@Autowired
	private ContratoManager contratoManager;

	@RequestMapping(value = "/notaria/list.htm", method = RequestMethod.GET)
	public String cargaLista(ModelMap map) {
		List<Notaria> lista= new ArrayList<Notaria>();
		try{
			lista = notariaManager.buscarNotaria("%");
			for(Notaria notaria:lista){
				notaria.setNotariaContratos(null);
				notaria.getPlaza().setNotarias(null);
				notaria.getPlaza().setOficinas(null);
			}
			
			map.addAttribute("notariaList", new Gson().toJson(lista));
			
			map.addAttribute("uploadedFile", new UploadedFile());
			
		}catch(Exception e){
			logger.error("Error lista notarias"+e) ;
		}	
		
		return "notaria/notariaList";
	}
	
	@RequestMapping(value = "/notaria/buscar.htm", method = RequestMethod.POST)
	public @ResponseBody String buscarNotaria(HttpServletRequest request) {
		
		String criterio = request.getParameter("criterio");if(criterio==null)criterio="";
		
		List<Notaria> lista= new ArrayList<Notaria>();
		try{
			lista = notariaManager.buscarNotaria("%"+criterio+"%");
			for(Notaria notaria:lista){
				notaria.setNotariaContratos(null);
				notaria.getPlaza().setNotarias(null);
				notaria.getPlaza().setOficinas(null);
			}
		}catch(Exception e){
			logger.error("Error lista notarias"+e) ;
		}
		
		return new Gson().toJson(lista);
	}
	
	@RequestMapping(value = "/notaria/eliminar.htm", method = RequestMethod.POST)
	public @ResponseBody String eliminarNotaria(HttpServletRequest request) {
		String id = request.getParameter("id");if(id==null)id="0";
		
		return ""+notariaManager.eliminarNotaria(new Notaria(Integer.parseInt(id),""));
	}
	
	@RequestMapping(value = "/notaria/agregar.htm", method = RequestMethod.POST)
	public String agregarNotaria(@ModelAttribute(value="notaria") Notaria notaria, HttpServletRequest request) {
		try{
			String contratoList = request.getParameter("contratoList");if(contratoList==null)contratoList="";
			final Gson gson = new Gson();
		    final Type tipoListaContratos = new TypeToken<List<Contrato>>(){}.getType();
		    final List<Contrato> contratos = gson.fromJson(contratoList, tipoListaContratos);
		    
			if(notaria.getIdnotaria()==null){
				notaria.setEstado("A".charAt(0));
				notaria.setFechacreacion(new Date());
				
				notariaManager.add(notaria);
			}else{
				Notaria notariaTemp = notariaManager.getNotaria(new Notaria(notaria.getIdnotaria(),""));
				notaria.setEstado(notariaTemp.getEstado());
				notaria.setFechacreacion(notariaTemp.getFechacreacion());
				notaria.setFechaactualizacion(new Date());
				
				notariaManager.update(notaria);
				notariaManager.deleteNotariaContrato(notariaTemp.getNotariaContratos());
			}
			notariaManager.agregarNotariaContrato(notaria, contratos);
			
			request.setAttribute("result", "true");
			return "result";
		}catch(Exception e){
			logger.error("Error agregar notaria ->"+e);
			request.setAttribute("result", "false");
			return "result";
		}
	}
	
	@RequestMapping(value = "/notaria/cargaForm.htm", method = RequestMethod.POST)
	public String cargaForm(ModelMap map) {
		
		try{
			Notaria n = new Notaria();
			n.setFechacreacion(new Date());
			map.addAttribute("notaria", n);
			map.addAttribute("plazaList", plazaManager.getAll());
			
		}catch(Exception e){
			logger.error("Error lista notarias"+e) ;
		}
		
		return "notaria/notariaForm";
	}
	
	@RequestMapping(value = "/notaria/cargaEditarForm.htm", method = RequestMethod.POST)
	public String cargaEditarForm(ModelMap map, HttpServletRequest request) {
		
		try{
			String id = request.getParameter("id");if(id==null)id="0";
			
			map.addAttribute("notaria", notariaManager.getNotaria(new Notaria(Integer.parseInt(id), "")));
			map.addAttribute("plazaList", plazaManager.getAll());
			
		}catch(Exception e){
			logger.error("Error cargaEditarForm"+e) ;
		}
		
		return "notaria/notariaForm";
	}
	
	@RequestMapping(value = "/notaria/cargaDetalle.htm", method = RequestMethod.POST)
	public String cargaDetalle(ModelMap map, HttpServletRequest request) {
		
		String idNotaria=request.getParameter("idNotaria");if(idNotaria==null)idNotaria="";
		
		try{
			
			map.addAttribute("notaria", notariaManager.getNotaria(new Notaria(Integer.parseInt(idNotaria), "")));
			
			
		}catch(Exception e){
			logger.error("Error cargaDetalle"+e) ;
		}
		
		return "notaria/notariaDetalle";
	}
	
	@RequestMapping(value = "/notaria/cargaContratoForm.htm", method = RequestMethod.POST)
	public String cargaContratoForm(ModelMap map, HttpServletRequest request) {
		
		try{
			String idContrato = request.getParameter("id");if(idContrato==null)idContrato="0";
			
			map.addAttribute("contratoList", contratoManager.contratoList());
			
			if(idContrato.equals("0") ){
				map.addAttribute("notariaContrato", new Contrato(0));
			}else{
				map.addAttribute("notariaContrato", contratoManager.finById(Integer.parseInt(idContrato)));
			}
			
			
			
		}catch(Exception e){
			logger.error("Error cargaDetalle"+e) ;
		}
		
		return "notaria/notariaContratoForm";
	}
	
	@RequestMapping(value = "/notaria/subirArchivo.htm", method = RequestMethod.POST)
	public String subirArchivo(UploadedFile uploadedFile, ModelMap map) throws Exception{
		
		map.addAttribute("result", notariaManager.subirArchivoNotarias(uploadedFile));
		
		return "result";

	}
	
}
