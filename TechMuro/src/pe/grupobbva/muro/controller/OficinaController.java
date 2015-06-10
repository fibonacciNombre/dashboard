package pe.grupobbva.muro.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.grupobbva.muro.entity.Oficina;
import pe.grupobbva.muro.model.UploadedFile;
import pe.grupobbva.muro.service.NotariaManager;
import pe.grupobbva.muro.service.OficinaManager;
import pe.grupobbva.muro.service.PlazaManager;

import com.google.gson.Gson;

@Controller
public class OficinaController {
	
	private static final Logger logger = Logger.getLogger(OficinaController.class);
	
	@Autowired
	private OficinaManager oficinaManager;
	
	@Autowired
	private NotariaManager notariaManager;
	
	@Autowired
	private PlazaManager plazaManager;
	
	@RequestMapping(value = "/oficinas.htm", method = RequestMethod.GET)
	public String cargaOficinas(ModelMap map){
		
		try {
			List<Oficina> oficinas = oficinaManager.getOficinasActivas();
			for (Oficina oficina: oficinas) {
				oficina.setAficheOficinas(null);
				oficina.setComunicadoOficinas(null);
				oficina.setOcurrenciaMuros(null);
			}
			logger.debug("oficinitas "+ oficinas);
			map.addAttribute("oficinas", oficinas);
			map.addAttribute("uploadedFile", new UploadedFile());

			return "oficinas/oficinas";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "oficinas/oficinas";
	}
	
	@RequestMapping(value = "/buscarOficina.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaOficina(ModelMap map, HttpServletRequest request) throws Exception {
		
		String criterio = request.getParameter("criterio");if(criterio==null)criterio="";
		
		List<Oficina>  afiOfi2 = oficinaManager.buscarOficina("%"+criterio+"%");
		ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
			for(Oficina a: afiOfi2){

				HashMap<String,String> mapa = new HashMap<String,String>();
				  mapa.put("idplaza",a.getPlaza().getIdplaza().toString());
				  mapa.put("idoficina",a.getIdoficina().toString());
				  mapa.put("codigo",a.getCodigo());
				  mapa.put("nombre",a.getNombre());
				  mapa.put("plaza",a.getPlaza().getNombre());
				  y.add(mapa);
			}
		
			Gson g = new Gson();
			return g.toJson(y);

	}
	
	@RequestMapping(value = "/deleteOficina/{oficinaId}.htm", method = RequestMethod.GET)
	public @ResponseBody String deleteComunicado(@PathVariable("oficinaId") Integer oficinaId) throws Exception
	{		
		oficinaManager.deleteOficina(oficinaId);
			
		List<Oficina>  afiOfi2 = oficinaManager.getOficinasActivas();
		ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
			for(Oficina a: afiOfi2){

				HashMap<String,String> mapa = new HashMap<String,String>();
				  mapa.put("idplaza",a.getPlaza().getIdplaza().toString());
				  mapa.put("idoficina",a.getIdoficina().toString());
				  mapa.put("codigo",a.getCodigo());
				  mapa.put("nombre",a.getNombre());
				  mapa.put("plaza",a.getPlaza().getNombre());
				  y.add(mapa);
			}
		
			Gson g = new Gson();
			return g.toJson(y);

	}
	
	@RequestMapping(value = "/upAgregarOficina.htm", method = RequestMethod.GET)
	public String upAgregarComunicado(ModelMap map) throws Exception {
		
		Oficina oficina = new Oficina();
		
		oficina.setFechacreacion(new Date()); 
		map.addAttribute("oficina", oficina);
		
		
		map.addAttribute("plazaList", plazaManager.getAll());
		
		return "oficinas/oficinas-agregar";
	}
	
	@RequestMapping(value = "/upEditarOficina/{idOfi}.htm", method = RequestMethod.GET)
	public String upEditarComunicado(@PathVariable("idOfi") Integer idOfi,ModelMap map) throws Exception {
		
		map.addAttribute("plazaList", plazaManager.getAll());
		
		map.addAttribute("oficina", oficinaManager.finById(idOfi));
		
		return "oficinas/oficinas-agregar";
	}
	
	@RequestMapping(value = "/addOficina.htm", method = RequestMethod.POST)
	public @ResponseBody String addAfiche(@ModelAttribute(value="oficina") Oficina oficina , HttpServletRequest request) throws Exception 
	{

					if(oficina.getIdoficina() == null ){
						oficina.setEstado('A');
						oficina.setFechacreacion(new Date());
						oficina.setFechaactualizacion(new Date());
						oficinaManager.add(oficina);
						
					}
					else{
							Oficina u =oficinaManager.finById(oficina.getIdoficina());
							u.setCodigo(oficina.getCodigo());
							u.setNombre(oficina.getNombre());
							u.setPlaza(oficina.getPlaza());
							u.setFechaactualizacion(new Date());
							oficinaManager.update(u);
					}
				
				
					List<Oficina>  afiOfi2 = oficinaManager.getOficinasActivas();
					ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
						for(Oficina a: afiOfi2){

							HashMap<String,String> mapa = new HashMap<String,String>();
							  mapa.put("idplaza",a.getPlaza().getIdplaza().toString());
							  mapa.put("idoficina",a.getIdoficina().toString());
							  mapa.put("codigo",a.getCodigo());
							  mapa.put("nombre",a.getNombre());
							  mapa.put("plaza",a.getPlaza().getNombre());
							  y.add(mapa);
						}
					
						Gson g = new Gson();
						return g.toJson(y);
	
	}
	
	@RequestMapping(value = "/oficina/subirArchivo.htm", method = RequestMethod.POST)
	public String subirArchivo(UploadedFile uploadedFile, ModelMap map) throws Exception{
		
		map.addAttribute("result", notariaManager.subirArchivoNotarias(uploadedFile));
		
		return "result";

	}
}
