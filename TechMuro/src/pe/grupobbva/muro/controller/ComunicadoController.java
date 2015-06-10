package pe.grupobbva.muro.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pe.grupobbva.muro.entity.Comunicado;
import pe.grupobbva.muro.entity.ComunicadoOficina;
import pe.grupobbva.muro.entity.ComunicadoOficinaId;
import pe.grupobbva.muro.entity.Oficina;
import pe.grupobbva.muro.model.UploadedFile;
import pe.grupobbva.muro.service.ComunicadoManager;
import pe.grupobbva.muro.service.ComunicadoOficinaManager;
import pe.grupobbva.muro.service.OficinaManager;

import com.google.gson.Gson;

@Controller
public class ComunicadoController {
	
	private static final Logger logger = Logger.getLogger(ComunicadoController.class);
	
	@Autowired
	private ComunicadoOficinaManager comunicadoOficinaManager;
	
	@Autowired
	private OficinaManager oficinaManager;
	
	@Autowired
	private ComunicadoManager comunicadoManager;
	
	
	@RequestMapping(value = "/comunicado.htm", method = RequestMethod.GET)
	public String listComunicados(ModelMap map) throws Exception {
		
		List<ComunicadoOficina>  afiOfi = comunicadoOficinaManager.getCO();
		map.addAttribute("lisAll", afiOfi);
		return "comunicados/comunicados";
	}
	
	@RequestMapping(value = "/upAgregarComunicado.htm", method = RequestMethod.GET)
	public String upAgregarComunicado(ModelMap map) throws Exception {
		
		Comunicado comunicadoVacio = new Comunicado();
		
		comunicadoVacio.setFechacreacion(new Date()); 
		map.addAttribute("comunicado", comunicadoVacio);
		
		List<Oficina> oficinas = oficinaManager.getOficinasFilter();
		
		for (Oficina oficina: oficinas) {
			oficina.setAficheOficinas(null);
			oficina.setComunicadoOficinas(null);
			oficina.setOcurrenciaMuros(null);
			oficina.setPlaza(null);
		}
		map.addAttribute("oficinas", oficinas);
		
		return "comunicados/comunicados-agregar";
	}
	
	@RequestMapping(value = "/buscarComunicado.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaProducto(ModelMap map, HttpServletRequest request) throws Exception {
		
		String criterio = request.getParameter("criterio");if(criterio==null)criterio="";
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		List<ComunicadoOficina>  afiOfi2 = comunicadoOficinaManager.buscarComunicado("%"+criterio+"%");
		ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
			for(ComunicadoOficina a: afiOfi2){
				logger.debug("idCO -- >"+a.getId().getIdcomunicado());
				logger.debug("idCO -- >"+a.getId().getIdoficina());
				HashMap<String,String> mapa = new HashMap<String,String>();
				  mapa.put("idoficina",a.getOficina().getIdoficina().toString());
				  mapa.put("idcomunicado",a.getComunicado().getIdcomunicado().toString());
				  Date today = a.getComunicado().getFechacreacion();        
				  String reportDate = df.format(today);
				  mapa.put("fecha",reportDate);
				  mapa.put("titulo",a.getComunicado().getTitulo());
				  mapa.put("descripcion",a.getComunicado().getTipocomunicado());
				  mapa.put("oficina",a.getOficina().getCodigo());
				  y.add(mapa);
			}
		
			Gson g = new Gson();
			logger.debug("funka - "+g.toJson(y));
			return g.toJson(y);

	}
	
	@RequestMapping(value = "/deleteComunicado/{aficheId}/{oficinaId}.htm", method = RequestMethod.GET)
	public @ResponseBody String deleteComunicado(@PathVariable("aficheId") Integer aficheId,@PathVariable("oficinaId") Integer oficinaId) throws Exception
	{		
		comunicadoOficinaManager.deleteComunicado(aficheId, oficinaId);
			
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		List<ComunicadoOficina>  afiOfi2 = comunicadoOficinaManager.getCO();
		
		ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
		
		for(ComunicadoOficina a: afiOfi2){
			logger.debug("idCO -- >"+a.getId().getIdcomunicado());
			logger.debug("idCO -- >"+a.getId().getIdoficina());
			HashMap<String,String> mapa = new HashMap<String,String>();
			  mapa.put("idoficina",a.getOficina().getIdoficina().toString());
			  mapa.put("idcomunicado",a.getComunicado().getIdcomunicado().toString());
			  Date today = a.getComunicado().getFechacreacion();        
			  String reportDate = df.format(today);
			  mapa.put("fecha",reportDate);
			  mapa.put("titulo",a.getComunicado().getTitulo());
			  mapa.put("descripcion",a.getComunicado().getTipocomunicado());
			  mapa.put("oficina",a.getOficina().getCodigo());
			  y.add(mapa);
		}
	
		Gson g = new Gson();
		logger.debug("funka - "+g.toJson(y));
		return g.toJson(y);

	}
	
	@RequestMapping(value = "/addComunicado/{oficinas}.htm", method = RequestMethod.POST)
	public @ResponseBody String addAfiche(@PathVariable("oficinas") String ofic, @ModelAttribute(value="comunicado") Comunicado comunicado , BindingResult result,UploadedFile uploadedFile, ModelMap map, HttpServletRequest request) throws Exception 
	{
		logger.debug("ofcinasssss -- "+ofic);

				if(ofic.equals("null")){
					
				}else{
					
					if(comunicado.getIdcomunicado() == null ){
						
						if(uploadedFile.getFile() == null){
							
							comunicado.setTipocomunicado("Texto");
							
						}else{
								
							InputStream inputStream = null;
							OutputStream outputStream = null;
							MultipartFile file = uploadedFile.getFile();
							String fileName = file.getOriginalFilename();
					
							try {
								inputStream = file.getInputStream();
								File newFile = new File("D:/comunicados/" + fileName);
								if (!newFile.exists()) {
									newFile.createNewFile();
								}
								outputStream = new FileOutputStream(newFile);
								int read = 0;
								byte[] bytes = new byte[1024];
					
								while ((read = inputStream.read(bytes)) != -1) {
									outputStream.write(bytes, 0, read);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							comunicado.setUrl("../comunicado/"+fileName);
							comunicado.setTipocomunicado("Imagen");
							comunicado.setDescripcion("Imagen");

						}
						comunicado.setEstado('A');
						comunicado.setFechacreacion(new Date());
						comunicado.setFechaactualizacion(new Date());
						
						String[] a = ofic.split(",");
						
						for(String j:a){
							if(j.equals("multiselect-all")){
								comunicadoManager.add(comunicado);
								ComunicadoOficina t = new ComunicadoOficina();
								t.setId(new ComunicadoOficinaId(comunicado.getIdcomunicado(), 0));
								comunicadoOficinaManager.add(t);
								break;
							}
							else{
								comunicadoManager.add(comunicado);
								ComunicadoOficina t = new ComunicadoOficina();
								t.setId(new ComunicadoOficinaId(comunicado.getIdcomunicado(), Integer.parseInt(j)));
								comunicadoOficinaManager.add(t);
							}
						}
					}
					else{
							logger.debug("desc --> "+ comunicado.getDescripcion());
							Comunicado u =comunicadoManager.finById(comunicado.getIdcomunicado());
							u.setTitulo(comunicado.getTitulo());
							u.setDescripcion(comunicado.getDescripcion());
							u.setFechaactualizacion(new Date());
							comunicadoManager.update(u);

					}
				}
				
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
				List<ComunicadoOficina>  afiOfi2 = comunicadoOficinaManager.getCO();
				
				ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
				
				for(ComunicadoOficina a: afiOfi2){
					logger.debug("idCO -- >"+a.getId().getIdcomunicado());
					logger.debug("idCO -- >"+a.getId().getIdoficina());
					HashMap<String,String> mapa = new HashMap<String,String>();
					  mapa.put("idoficina",a.getOficina().getIdoficina().toString());
					  mapa.put("idcomunicado",a.getComunicado().getIdcomunicado().toString());
					  Date today = a.getComunicado().getFechacreacion();        
					  String reportDate = df.format(today);
					  mapa.put("fecha",reportDate);
					  mapa.put("titulo",a.getComunicado().getTitulo());
					  mapa.put("descripcion",a.getComunicado().getTipocomunicado());
					  mapa.put("oficina",a.getOficina().getCodigo());
					  y.add(mapa);
				}
			
				Gson g = new Gson();
				logger.debug("funka - "+g.toJson(y));
				return g.toJson(y);
					

		
	}
	
	@RequestMapping(value = "/upEditarComunicado/{idComunicado}/{idOfi}.htm", method = RequestMethod.GET)
	public String upEditarComunicado(@PathVariable("idComunicado") Integer idComunicado,@PathVariable("idOfi") Integer idOfi,ModelMap map) throws Exception {
		
		List<Oficina> oficinas = oficinaManager.getAll();
		
		for (Oficina oficina: oficinas) {
			oficina.setAficheOficinas(null);
			oficina.setComunicadoOficinas(null);
			oficina.setOcurrenciaMuros(null);
			oficina.setPlaza(null);
		}
		
		map.addAttribute("oficinas", oficinas);
		map.addAttribute("comunicado", comunicadoManager.finById(idComunicado));
		map.addAttribute("comunicadoOficina", comunicadoOficinaManager.byIdCO(idComunicado, idOfi));
		
		return "comunicados/comunicados-agregar";
	}
	
	@RequestMapping(value = "/pickComunicado/{comunicadoId}.htm")
	@ResponseBody
	public byte[] imagen(@PathVariable("comunicadoId") Integer comunicadoId) throws Exception  {
		return comunicadoManager.byId(comunicadoId);
	}

}
