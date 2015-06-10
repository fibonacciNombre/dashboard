package pe.grupobbva.muro.dao;

import java.util.List;

import pe.grupobbva.muro.entity.Producto;

public interface ProductoDAO extends Dao<Producto>{

	public List<Producto> buscarProducto(String criterio, String tipoCliente);

	public Producto findByOrden(Producto producto, int valor);
	
	public Integer getMaxOrder(String tipoCliente);
	
	public Producto validaNombre(Producto producto);
}