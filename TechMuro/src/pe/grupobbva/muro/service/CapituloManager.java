package pe.grupobbva.muro.service;

import java.util.List;

import pe.grupobbva.muro.entity.Capitulo;

public interface CapituloManager extends Service<Capitulo> {

	public List<Capitulo> findByProducto(int id);
	public List<Capitulo> buscarCapitulo(Capitulo capitulo);
	public String guardarCapitulo(Capitulo capitulo);
	public String editarCapitulo(Capitulo capitulo);
	public String eliminarCapitulo(int id);
	public boolean up(Integer id);
	public boolean down(Integer id);
}
