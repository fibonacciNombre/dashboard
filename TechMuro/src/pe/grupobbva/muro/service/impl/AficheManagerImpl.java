package pe.grupobbva.muro.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import pe.grupobbva.muro.dao.AficheDAO;
import pe.grupobbva.muro.dao.AficheOficinaDAO;
import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.dao.OficinaDAO;
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
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Service
public class AficheManagerImpl extends ServiceImpl<Afiche> implements AficheManager{
	
	private static final Logger logger = Logger.getLogger(AficheManagerImpl.class);

	@Autowired
	private AficheDAO aficheDAO;
	
	@Autowired
	private OficinaDAO oficinaDAO;
	
	@Autowired
	private AficheOficinaManager aficheOficinaManager;
	
	@Autowired
	private AficheOficinaDAO aficheOficinaDAO;
	
	
	@Autowired
	private OficinaManager oficinaManager;
	
	@Autowired
	private LogMuroManager logMuroManager;
	
	@Autowired
	private VariableManager variableManager;
	
	
	@Override
	protected Dao<Afiche> getDAO() {
		return aficheDAO;
	}

	@Override
	@Transactional
	public void deleteAfi(Integer id) throws Exception {
		Afiche afiche = aficheDAO.findById(id);
		
		afiche.setEstado('I');
		
		aficheDAO.update(afiche);
	}

	@Override
	@Transactional
	public List<Afiche> buscarAfiche(String criterio) throws Exception {
		return aficheDAO.buscarAfiche(criterio);
	}
	
	@Override
	@Transactional
	public byte[] byId(Integer id) throws Exception{
		
		String filename = aficheDAO.findById(id).getDireccion().substring(9);
		
		System.out.println("test -- "+filename);
		BufferedImage image = ImageIO.read(new File("D:/afiches"+filename));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		//byte[] res=baos.toByteArray();
		String encodedImage = Base64.encode(baos.toByteArray());
		byte[] t = Base64.decode(encodedImage);
		return t;
	}

	@Override
	public List<Object> listarAll() throws Exception {
		
		return aficheDAO.listarAll();
	}
	
	@Override
	@Transactional
	public String validateMin(String ofic) throws Exception{
		
		Integer contador = 0;
		
		if(ofic.equals("null") || ofic.equals("")){
			
			return "encontro";
		}
		else{
			String[] a = ofic.split(",");
		
			for(String j:a){
				if(j.equals("multiselect-all")){
					
					logger.debug("todas!");
					Oficina s = oficinaDAO.getOficinasVisor(0);
					if(s.getAficheOficinas().size() < 7){
						return "encontro";
					}
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
					Oficina s = oficinaDAO.getOficinasVisor(Integer.parseInt(j));
//					if(s.getAficheOficinas().size() < 7){
//						return "encontro";
//					}
				//	if(s.getAficheOficinas().size() == 8){
					
						for(AficheOficina p: s.getAficheOficinas()){
							logger.debug("todas! 88888888888888888");
							if(p.getAfiche().getMiniatura()=='S'){
								contador++;
							}
						}
						if(contador == 1){
							return "no";
						}
						return "encontro";
					//}
				}
				
			}
		
			return "no";
		}

	}
	
	@Override
	@Transactional
	public String validateMinAny(String oficinaMiniatura, String ofic, Afiche afiche , BindingResult result,UploadedFile uploadedFile) throws Exception{
		

		if(ofic.equals("null")){
			
		}else{
			
			if(afiche.getIdafiche() == null ){
				
				logger.debug("URL! --> "+uploadedFile);
				logger.debug("URL! 222--> "+uploadedFile.getFile());
				
				if(uploadedFile.getFile() == null){
					
					afiche.setDireccion("../afiche/video.jpg");
					afiche.setTipoafiche("v");
					
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
						this.add(afiche);
						AficheOficina t = new AficheOficina();
						t.setId(new AficheOficinaId(afiche.getIdafiche(), 0));
						aficheOficinaManager.add(t);
						
						LogMuro log = new LogMuro();
						
						log.setOpcion("Editar");
						logMuroManager.add(log);
						
						break;
					}
					else{
						this.add(afiche);
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

				if(this.validateMin(oficinaMiniatura) == "encontro"){
					
					Afiche u =this.finById(afiche.getIdafiche());
					u.setDescripcion(afiche.getDescripcion());
					if(afiche.getMiniatura()== null){
						u.setMiniatura('N');
					}else{
						u.setMiniatura(afiche.getMiniatura());
					}
					u.setFechaactualizacion(new Date());
					this.update(u);
					
				}else{
					
					return "error";
				}
				
				
			}
		}
		
			List<AficheOficina>  afiOfi2 = aficheOficinaDAO.getAO();
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
	
	@Override
	@Transactional
	public String validateMinAnyElse(String oficinaMiniatura, String ofic, Afiche afiche , BindingResult result,UploadedFile uploadedFile) throws Exception{
		
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
					}catch (IOException e) {
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
							this.add(afiche);
							AficheOficina t = new AficheOficina();
							t.setId(new AficheOficinaId(afiche.getIdafiche(), 0));
							aficheOficinaManager.add(t);
							
							LogMuro log = new LogMuro();
							
							log.setOpcion("Editar");
							logMuroManager.add(log);
							
							break;
						}
						else{
							this.add(afiche);
							AficheOficina t = new AficheOficina();
							t.setId(new AficheOficinaId(afiche.getIdafiche(), Integer.parseInt(j)));
							aficheOficinaManager.add(t);
							
							LogMuro log = new LogMuro();
							
							log.setOpcion("Editar");
							logMuroManager.add(log);
						}
			
					}
				
				}else{
					Afiche u =this.finById(afiche.getIdafiche());
					u.setDescripcion(afiche.getDescripcion());
					if(afiche.getMiniatura()== null){
						u.setMiniatura('N');
					}else{
						u.setMiniatura(afiche.getMiniatura());
					}
					u.setFechaactualizacion(new Date());
					this.update(u);
				}
			}
			
			List<AficheOficina>  afiOfi2 = aficheOficinaDAO.getAO();
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
