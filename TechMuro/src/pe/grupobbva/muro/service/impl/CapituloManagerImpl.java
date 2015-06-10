package pe.grupobbva.muro.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.dao.CapituloDAO;
import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.entity.Capitulo;
import pe.grupobbva.muro.entity.Producto;
import pe.grupobbva.muro.service.CapituloManager;

@Service
public class CapituloManagerImpl extends ServiceImpl<Capitulo> implements CapituloManager {

	private static final Logger logger = Logger.getLogger(CapituloManagerImpl.class);
	
	@Autowired
    private CapituloDAO capituloDAO;
	
	@Override
	protected Dao<Capitulo> getDAO() {
		return capituloDAO;
	}
	@Transactional
	public List<Capitulo> findByProducto(int id){
		
		return capituloDAO.findByProducto(id);
	}
	
	@Transactional
	public List<Capitulo> buscarCapitulo(Capitulo capitulo){
		return capituloDAO.buscarCapitulo(capitulo);
	}
	
	@Transactional
	public String guardarCapitulo(Capitulo capitulo){
		try{
			capitulo.setEstado("A".charAt(0));
			capitulo.setFechacreacion(new Date());
			capitulo.setOrden(capituloDAO.getMaxOrder(capitulo));
			
			if(capituloDAO.validaCapitulo(capitulo)==null){
				capituloDAO.add(capitulo);
				return "true";
			}else{
				return "Capítulo ya está relacionado con este Producto”";
			}
			
		}catch(Exception e){
			logger.error("Error al guardar Capitulo -->" +e);
			return "Error al Insertar Capitulo";
		}
	}
	
	@Override
	@Transactional
	public Capitulo finById(Integer id){
		try{
			Capitulo cap =  capituloDAO.findById(id);
			logger.debug("Aquí el producto ->"+cap.getProducto().getTipocliente());
			
			if(cap.getNotaByIdnotainicial()!=null){
				logger.debug("Aquí las Notas ->"+cap.getNotaByIdnotainicial().getTitulo());
				logger.debug("Aquí las Notas ->"+cap.getNotaByIdnotainicial().getDescripcion());
			}
			
			if(cap.getNotaByIdnotafinal()!=null){
				logger.debug("Aquí las Notas ->"+cap.getNotaByIdnotafinal().getTitulo());
				logger.debug("Aquí las Notas ->"+cap.getNotaByIdnotafinal().getDescripcion());
			}
			
			return cap;
		}catch(Exception e){
			logger.error("Error al buscar x id capitulo -->" +e);
			return null;
		}
	}
	
	@Transactional
	public String editarCapitulo(Capitulo capitulo){
		try{
			if(capituloDAO.validaCapitulo(capitulo)==null){
				Capitulo capTemp = capituloDAO.findById(capitulo.getIdcapitulo());
				capTemp.setNombre(capitulo.getNombre());
				capTemp.setProducto(capitulo.getProducto());
				
				return "true";
			}else{
				return "Capítulo ya está relacionado con este Producto";
			}
			
		}catch(Exception e){
			logger.error("Error al Editar Capitulo -->" +e);
			return "Error al Insertar Capitulo";
		}
	}
	
	@Transactional
	public String eliminarCapitulo(int id){
		try{
			capituloDAO.findById(id).setEstado("I".charAt(0));
			return "true";
		}catch(Exception e){
			logger.error("Error al Eliminar Capítulo ->" + e);
			return "Error al Eliminar Capítulo";
		}
	}
	
	@Transactional
	public boolean up(Integer id) {
		
		try {
			Capitulo cap1  = capituloDAO.findById(id);
			
			Capitulo cap2 = capituloDAO.findByOrden(cap1, -1);

			cap1.setOrden(cap1.getOrden()-1);
			cap2.setOrden(cap2.getOrden()+1);
			
			return true;
		} catch (Exception ex) {
			logger.error("up " +ex.getLocalizedMessage());
			return false;
		}
	}
	
	@Transactional
	public boolean down(Integer id) {
		
		try {
			Capitulo prod1  = capituloDAO.findById(id);
			
			Capitulo prod2 = capituloDAO.findByOrden(prod1, +1);

			prod1.setOrden(prod1.getOrden()+1);
			prod2.setOrden(prod2.getOrden()-1);
			
			return true;
		} catch (Exception ex) {
			logger.error("up " +ex.getLocalizedMessage());
			return false;
		}
	}
}
