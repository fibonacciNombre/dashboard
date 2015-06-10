package pe.grupobbva.muro.service;

import java.util.List;

import pe.grupobbva.muro.entity.Producto;

public interface ProductoManager extends Service<Producto> {

	public List<Producto> buscarProducto(String criterio, String tipoCliente);
	public boolean up(Integer id);
	public boolean down(Integer id);
	public String guardarProducto(Producto producto);
	public String editarProducto(Producto producto);
	public String eliminarProducto(int id);
}
