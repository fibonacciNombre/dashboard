package pe.grupobbva.muro.dao;

import pe.grupobbva.muro.entity.Plaza;

public interface PlazaDAO extends Dao<Plaza>{

	
	public Plaza findByNombre(String nombre);
	
}
