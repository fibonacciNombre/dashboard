package pe.grupobbva.muro.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.controller.ProductoController;
import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.dao.ProductoDAO;
import pe.grupobbva.muro.entity.Producto;
import pe.grupobbva.muro.service.ProductoManager;

@Service
public class ProductoManagerImpl extends ServiceImpl<Producto> implements ProductoManager {

	private static final Logger logger = Logger.getLogger(ProductoManagerImpl.class);
	
	@Autowired
    private ProductoDAO productoDAO;
	
	@Override
	protected Dao<Producto> getDAO() {
		return productoDAO;
	}
	
	public List<Producto> buscarProducto(String criterio, String tipoCliente){
		return productoDAO.buscarProducto(criterio, tipoCliente);
	}
	
	@Transactional
	public boolean up(Integer id) {
		
		try {
			Producto prod1  = productoDAO.findById(id);
			
			Producto prod2 = productoDAO.findByOrden(prod1, -1);

			prod1.setOrden(prod1.getOrden()-1);
			prod2.setOrden(prod2.getOrden()+1);
			
			return true;
		} catch (Exception ex) {
			logger.error("up " +ex.getLocalizedMessage());
			return false;
		}
	}
	
	@Transactional
	public boolean down(Integer id) {
		
		try {
			Producto prod1  = productoDAO.findById(id);
			
			Producto prod2 = productoDAO.findByOrden(prod1, +1);

			prod1.setOrden(prod1.getOrden()+1);
			prod2.setOrden(prod2.getOrden()-1);
			
			return true;
		} catch (Exception ex) {
			logger.error("up " +ex.getLocalizedMessage());
			return false;
		}
	}
	
	
	@Transactional
	public String guardarProducto(Producto producto){
		try{
			producto.setEstado("A".charAt(0));
			producto.setFechacreacion(new Date());
			producto.setOrden(productoDAO.getMaxOrder(producto.getTipocliente()));
			
			if(productoDAO.validaNombre(producto)==null){
				productoDAO.add(producto);
				return "true";
			}else{
				return "Producto ya existe para este Tipo de Cliente";
			}
			
			
		}catch(Exception e){
			logger.error("Error al guardar Producto -->" +e);
			return "Error al Insertar producto";
		}
	}
	
	@Transactional
	public String editarProducto(Producto producto){
		try{
			Producto productoTemp = productoDAO.findById(producto.getIdproducto());
			if(productoDAO.validaNombre(producto)==null){
				productoTemp.setNombre(producto.getNombre());
				//productoTemp.setFechaactualizacion(new Date());
				
				return "true";
			}else{
				return "Producto ya existe para este Tipo de Cliente";
			}
			
		}catch(Exception e){
			logger.error("Error al guardar Producto -->" +e);
			return "Error al Insertar producto";
		}
	}
	
	@Transactional
	public String eliminarProducto(int id){
		try{
			productoDAO.findById(id).setEstado("I".charAt(0));
			return ""+true;
		}catch(Exception e){
			
			return ""+false;
		}
	}
	
}
