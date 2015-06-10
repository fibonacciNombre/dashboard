package pe.grupobbva.muro.dao;

import java.util.List;

import pe.grupobbva.muro.entity.Oficina;

public interface OficinaDAO extends Dao<Oficina>{
	
	public List<Oficina> getOficinasFilter() throws Exception;
	
	public Oficina getOficinasVisor(Integer id) throws Exception;
	
	public Oficina getOficinasByCodigoVisor(String criterio) throws Exception;

	public Oficina getOficinasByCodigo(String codigoOficina) throws Exception;
	
	public List<Oficina> getOficinasActivas() throws Exception;
	
	public List<Oficina> buscarOficina(String criterio) throws Exception;
}
