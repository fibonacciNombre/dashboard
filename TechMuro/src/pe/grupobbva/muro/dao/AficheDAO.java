package pe.grupobbva.muro.dao;

import java.util.List;

import pe.grupobbva.muro.entity.Afiche;

public interface AficheDAO extends Dao<Afiche> {
	
	public List<Afiche> buscarAfiche(String criterio) throws Exception;
	
	public List<Object> listarAll() throws Exception;

}
