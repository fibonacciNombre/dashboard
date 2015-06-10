package pe.grupobbva.muro.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import pe.grupobbva.muro.entity.Afiche;
import pe.grupobbva.muro.entity.AficheOficina;
import pe.grupobbva.muro.entity.AficheOficinaId;
import pe.grupobbva.muro.entity.LogMuro;
import pe.grupobbva.muro.entity.Oficina;
import pe.grupobbva.muro.model.UploadedFile;
import pe.grupobbva.muro.service.AficheManager;
import pe.grupobbva.muro.service.AficheOficinaManager;
import pe.grupobbva.muro.service.LogMuroManager;
import pe.grupobbva.muro.service.OficinaManager;
import pe.grupobbva.muro.service.VariableManager;
import pe.grupobbva.muro.util.Constants;

import com.google.gson.Gson;

@Controller
public class AficheController {
	
	private static final Logger logger = Logger.getLogger(AficheController.class);
	
	@Autowired
	private AficheOficinaManager aficheOficinaManager;
	
	@Autowired
	private AficheManager aficheManager;
	
	@Autowired
	private OficinaManager oficinaManager;
	
	@Autowired
	private LogMuroManager logMuroManager;
	
	@Autowired
	private VariableManager variableManager;
	
	@RequestMapping(value = "/afiche.htm", method = RequestMethod.GET)
	public String listAfiches(ModelMap map) throws Exception {
		
		map.addAttribute("aficheList", aficheManager.getAll());
		List<AficheOficina>  afiOfi = aficheOficinaManager.getAO();
		map.addAttribute("lisAll", afiOfi);
		return "afiches/afiches";
	}
	
	@RequestMapping(value = "/upAgregarAfiche.htm", method = RequestMethod.GET)
	public String upAgregarAfiche(ModelMap map) throws Exception {
		
		Afiche aficheVacio = new Afiche();
		
		aficheVacio.setFechacreacion(new Date());;
		map.addAttribute("afiche", aficheVacio);
		List<Oficina> oficinas = oficinaManager.getOficinasFilter();
		
		for (Oficina oficina: oficinas) {
			oficina.setAficheOficinas(null);
			oficina.setComunicadoOficinas(null);
			oficina.setOcurrenciaMuros(null);
			oficina.setPlaza(null);
		}
		
		logger.debug("lista de oficinas "+oficinas);
		map.addAttribute("oficinas", oficinas);
		
		return "afiches/afiches-agregar";
	}
	
	@RequestMapping(value = "/upAgregarAfiche/{idAfi}/{idOfi}.htm", method = RequestMethod.GET)
	public String upEditarAfiche(@PathVariable("idAfi") Integer idAfi,@PathVariable("idOfi") Integer idOfi,ModelMap map) throws Exception {
		
		List<Oficina> oficinas = oficinaManager.getAll();
		
		for (Oficina oficina: oficinas) {
			oficina.setAficheOficinas(null);
			oficina.setComunicadoOficinas(null);
			oficina.setOcurrenciaMuros(null);
			oficina.setPlaza(null);
		}
		
		map.addAttribute("oficinas", oficinas);
		map.addAttribute("afiche", aficheManager.finById(idAfi));
		map.addAttribute("aficheOficina", aficheOficinaManager.byIdAO(idAfi, idOfi));
		
		return "afiches/afiches-agregar";
	}
	
	@RequestMapping(value = "/listAfiche.htm", method = RequestMethod.GET)
	public @ResponseBody String buscaProducto(ModelMap map) throws Exception {
		
		List<Afiche> afiches = aficheManager.getAll();

		for (Afiche afiche: afiches) {
			afiche.setAficheOficinas(null);
		}

		Gson g = new Gson();
		logger.debug(g.toJson(afiches));
		return g.toJson(afiches);
	}
	
	@RequestMapping(value = "/listAll.htm", method = RequestMethod.GET)
	public @ResponseBody String listAll(ModelMap map) throws Exception {
		
		List<Object> afiches = aficheManager.listarAll();
		
		map.addAttribute("lisAll", afiches);
		return "";
	}
	
	@RequestMapping(value = "/min/{oficinas}.htm", method = RequestMethod.GET)
	public @ResponseBody String minMiniatura(@PathVariable("oficinas") String ofic,ModelMap map) throws Exception 
	{
		
		if(ofic.equals("null") || ofic.equals("")){
		}
		else{
			String[] a = ofic.split(",");
		
			for(String j:a){
				if(j.equals("multiselect-all")){
					
					logger.debug("todas!");
					Oficina s = oficinaManager.getOficinasVisor(0);
					if(s.getAficheOficinas().size() == 7 ){
						for(AficheOficina p: s.getAficheOficinas()){
							if(p.getAfiche().getMiniatura()=='S'){
								return "encontro";
							}
						}
					}
					break;
				}
				else{
					logger.debug("todas!23");
					Oficina s = oficinaManager.getOficinasVisor(Integer.parseInt(j));
					
					if(s.getAficheOficinas().size() == 7){
						for(AficheOficina p: s.getAficheOficinas()){
							if(p.getAfiche().getMiniatura()=='S'){
								return "encontro";
							}
						}
					
					}
				}
			}
		
			return "no";
		}
		
		return "no";
		
	}

	@RequestMapping(value = "/addAfi/{oficinaMiniatura}/{oficinas}.htm", method = RequestMethod.POST)
	public @ResponseBody String addAfiche(@PathVariable("oficinaMiniatura") String oficinaMiniatura, @PathVariable("oficinas") String ofic, @ModelAttribute(value="afiche") Afiche afiche , BindingResult result,UploadedFile uploadedFile, ModelMap map, HttpServletRequest request) throws Exception 
	{
		
		
		
		logger.debug("miniatura -- > "+oficinaMiniatura);
		
		if(afiche.getMiniatura()== null){
	
			//return aficheManager.validateMinAny(oficinaMiniatura, ofic, afiche, result, uploadedFile);

				if(ofic.equals("null")){
					
				}else{
					
					if(afiche.getIdafiche() == null ){
						
						logger.debug("URL! --> "+uploadedFile);
						logger.debug("URL! 222--> "+uploadedFile.getFile());
						
						if(uploadedFile.getFile() == null){
							
							afiche.setDireccion("../afiche/video.jpg");
							afiche.setTipoafiche("y");
							
						}else{
								
							InputStream inputStream = null;
							OutputStream outputStream = null;
							MultipartFile file = uploadedFile.getFile();
							String fileName = file.getOriginalFilename();
					
							try {
								inputStream = file.getInputStream();
								File newFile = new File(variableManager.finById(Constants.DIRECCIONAFICHES) + fileName);
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
							
							InputStream inputStream2 = null;
							OutputStream outputStream2 = null;
							MultipartFile file2 = uploadedFile.getFile();
							String fileName2 = file2.getOriginalFilename();
					
							try {
								inputStream2 = file.getInputStream();
								File newFile2 = new File(request.getServletContext().getRealPath("/") + "/afiche/" + fileName2);
								logger.debug("context  "+ request.getServletContext().getRealPath("/"));
								if (!newFile2.exists()) {
									newFile2.createNewFile();
								}
								outputStream2 = new FileOutputStream(newFile2);
								int read2 = 0;
								byte[] bytes2 = new byte[1024];
					
								while ((read2 = inputStream2.read(bytes2)) != -1) {
									outputStream2.write(bytes2, 0, read2);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							if(fileName.substring(fileName.length()-3).equals("mp4")){
								afiche.setDireccion("../afiche/video.jpg");
								afiche.setVideo("../afiche/"+fileName);
								afiche.setTipoafiche("v");
							}else{
								afiche.setDireccion("../afiche/"+fileName);
								afiche.setTipoafiche("i");
							}
							
						}
							
						afiche.setEstado('A');
						afiche.setFechacreacion(new Date());
						afiche.setFechaactualizacion(new Date());
						afiche.setMiniatura('S');
						
						String[] a = ofic.split(",");
						
						for(String j:a){
							if(j.equals("multiselect-all")){
								aficheManager.add(afiche);
								AficheOficina t = new AficheOficina();
								t.setId(new AficheOficinaId(afiche.getIdafiche(), 0));
								aficheOficinaManager.add(t);
								LogMuro log = new LogMuro();
								log.setOpcion("Editar");
								logMuroManager.add(log);
								break;
							}
							else{
								aficheManager.add(afiche);
								AficheOficina t = new AficheOficina();
								t.setId(new AficheOficinaId(afiche.getIdafiche(), Integer.parseInt(j)));
								aficheOficinaManager.add(t);
								LogMuro log = new LogMuro();
								log.setOpcion("Editar");
								logMuroManager.add(log);
								
							}
						}
					}
					else{

						if(aficheManager.validateMin(oficinaMiniatura) == "encontro"){
							
							Afiche u =aficheManager.finById(afiche.getIdafiche());
							u.setDescripcion(afiche.getDescripcion());
							if(afiche.getMiniatura()== null){
								u.setMiniatura('N');
							}else{
								u.setMiniatura(afiche.getMiniatura());
							}
							u.setFechaactualizacion(new Date());
							aficheManager.update(u);
							
						}else{
							
							return "error";
						}
					}
				}
				
				List<AficheOficina>  afiOfi2 = aficheOficinaManager.getAO();
				ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
				
				for(AficheOficina a: afiOfi2){
					logger.debug("idAO -- >"+a.getId().getIdafiche());
					logger.debug("idAO -- >"+a.getId().getIdoficina());
					HashMap<String,String> mapa = new HashMap<String,String>();
					  mapa.put("idoficina",a.getOficina().getIdoficina().toString());
					  mapa.put("idafiche",a.getAfiche().getIdafiche().toString());
					  mapa.put("imagen","0");
					  mapa.put("oficina",a.getOficina().getCodigo());
					  mapa.put("descripcion",a.getAfiche().getDescripcion());
					  mapa.put("miniatura",a.getAfiche().getMiniatura().toString());
					  y.add(mapa);
				}
				
				Gson g = new Gson();
				logger.debug("funka - "+g.toJson(y));
				return g.toJson(y);
					
		}else{
			
			//return aficheManager.validateMinAnyElse(oficinaMiniatura, ofic, afiche, result, uploadedFile);
			if(ofic.equals("null")){
				}else{
					if(afiche.getIdafiche() == null){
								
							InputStream inputStream = null;
							OutputStream outputStream = null;
							MultipartFile file = uploadedFile.getFile();
							String fileName = file.getOriginalFilename();

							try {
								inputStream = file.getInputStream();
								File newFile = new File(variableManager.finById(Constants.DIRECCIONAFICHES) + fileName);
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
							
							InputStream inputStream2 = null;
							OutputStream outputStream2 = null;
							MultipartFile file2 = uploadedFile.getFile();
							String fileName2 = file2.getOriginalFilename();
					
							try {
								inputStream2 = file.getInputStream();
								File newFile2 = new File(request.getServletContext().getRealPath("/") + "/afiche/" + fileName2);
								if (!newFile2.exists()) {
									newFile2.createNewFile();
								}
								outputStream2 = new FileOutputStream(newFile2);
								int read2 = 0;
								byte[] bytes2 = new byte[1024];
					
								while ((read2 = inputStream2.read(bytes2)) != -1) {
									outputStream2.write(bytes2, 0, read2);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
								
							if(fileName.substring(fileName.length()-3).equals("mp4")){
								afiche.setDireccion("../afiche/video.jpg");
								afiche.setVideo("../afiche/"+fileName);
								afiche.setTipoafiche("v");
							}else{
								afiche.setDireccion("../afiche/"+fileName);
							}
								
							afiche.setEstado('A');
							afiche.setFechacreacion(new Date());
							afiche.setMiniatura('S');
							afiche.setTipoafiche("i");
							
							String[] a = ofic.split(",");
								
								for(String j:a){
									if(j.equals("multiselect-all")){
										aficheManager.add(afiche);
										AficheOficina t = new AficheOficina();
										t.setId(new AficheOficinaId(afiche.getIdafiche(), 0));
										aficheOficinaManager.add(t);
										
										LogMuro log = new LogMuro();
										
										log.setOpcion("Editar");
										logMuroManager.add(log);
										
										break;
									}
									else{
										aficheManager.add(afiche);
										AficheOficina t = new AficheOficina();
										t.setId(new AficheOficinaId(afiche.getIdafiche(), Integer.parseInt(j)));
										aficheOficinaManager.add(t);
										
										LogMuro log = new LogMuro();
										
										log.setOpcion("Editar");
										logMuroManager.add(log);
									}
						
								}
						
						}else{
							Afiche u =aficheManager.finById(afiche.getIdafiche());
							u.setDescripcion(afiche.getDescripcion());
							if(afiche.getMiniatura()== null){
								u.setMiniatura('N');
							}else{
								u.setMiniatura(afiche.getMiniatura());
							}
							u.setFechaactualizacion(new Date());
							aficheManager.update(u);
						}
					}
					
					List<AficheOficina>  afiOfi2 = aficheOficinaManager.getAO();
					ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
						for(AficheOficina a: afiOfi2){
							logger.debug("idAO -- >"+a.getId().getIdafiche());
							logger.debug("idAO -- >"+a.getId().getIdoficina());
							HashMap<String,String> mapa = new HashMap<String,String>();
							  mapa.put("idoficina",a.getOficina().getIdoficina().toString());
							  mapa.put("idafiche",a.getAfiche().getIdafiche().toString());
							  mapa.put("imagen","0");
							  mapa.put("oficina",a.getOficina().getCodigo());
							  mapa.put("descripcion",a.getAfiche().getDescripcion());
							  mapa.put("miniatura",a.getAfiche().getMiniatura().toString());
							  y.add(mapa);
					}
					
						Gson g = new Gson();
						logger.debug("funka - "+g.toJson(y));
						return g.toJson(y);
				
		}
		
		
		
		
		
	}

	@RequestMapping(value = "/deleteAfi/{aficheId}/{oficinaId}.htm", method = RequestMethod.GET)
	public @ResponseBody String deleteEmplyee(@PathVariable("aficheId") Integer aficheId,@PathVariable("oficinaId") Integer oficinaId) throws Exception
	{
		
		
		if(aficheManager.validateMin(oficinaId.toString()) == "encontro"){
			
			aficheOficinaManager.deleteAfi(aficheId, oficinaId);
			
			List<AficheOficina>  afiOfi2 = aficheOficinaManager.getAO();
			ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
				for(AficheOficina a: afiOfi2){
					logger.debug("idAO -- >"+a.getId().getIdafiche());
					logger.debug("idAO -- >"+a.getId().getIdoficina());
					HashMap<String,String> mapa = new HashMap<String,String>();
					  mapa.put("idoficina",a.getOficina().getIdoficina().toString());
					  mapa.put("idafiche",a.getAfiche().getIdafiche().toString());
					  mapa.put("imagen","0");
					  mapa.put("oficina",a.getOficina().getCodigo());
					  mapa.put("descripcion",a.getAfiche().getDescripcion());
					  mapa.put("miniatura",a.getAfiche().getMiniatura().toString());
					  y.add(mapa);
				}
			
				Gson g = new Gson();
				logger.debug("funka - "+g.toJson(y));
				return g.toJson(y);
			
		}else{
			
			return "error";
		}
		

	}
	
	@RequestMapping(value = "/pick/{aficheId}.htm")
	@ResponseBody
	public byte[] imagen(@PathVariable Integer aficheId) throws Exception  {
		return aficheManager.byId(aficheId);
	}

	
	
	@RequestMapping(value = "/buscarAfiche.htm", method = RequestMethod.POST)
	public @ResponseBody String buscaProducto(ModelMap map, HttpServletRequest request) throws Exception {
		
		String criterio = request.getParameter("criterio");if(criterio==null)criterio="";
		
		List<AficheOficina>  afiOfi2 = aficheOficinaManager.buscarAfi("%"+criterio+"%");
		ArrayList<HashMap<String,String>> y = new ArrayList<HashMap<String,String>>();
			for(AficheOficina a: afiOfi2){
				logger.debug("idAO -- >"+a.getId().getIdafiche());
				logger.debug("idAO -- >"+a.getId().getIdoficina());
				HashMap<String,String> mapa = new HashMap<String,String>();
				  mapa.put("idoficina",a.getOficina().getIdoficina().toString());
				  mapa.put("idafiche",a.getAfiche().getIdafiche().toString());
				  mapa.put("imagen","0");
				  mapa.put("oficina",a.getOficina().getCodigo());
				  mapa.put("descripcion",a.getAfiche().getDescripcion());
				  mapa.put("miniatura",a.getAfiche().getMiniatura().toString());
				  y.add(mapa);
			}
		
			Gson g = new Gson();
			logger.debug("funka - "+g.toJson(y));
			return g.toJson(y);

	}
	
	public void setAficheManager(AficheManager aficheManager) {
		this.aficheManager = aficheManager;
	}

}
