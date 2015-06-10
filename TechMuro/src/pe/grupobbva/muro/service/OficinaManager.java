package pe.grupobbva.muro.service;

import java.util.List;

import pe.grupobbva.muro.entity.Oficina;

public interface OficinaManager extends Service<Oficina>{
	
	public List<Oficina> getOficinasFilter() throws Exception;
	
	public Oficina getOficinasVisor(Integer id) throws Exception;
	
	public Oficina getOficinasByCodigoVisor(String criterio) throws Exception;
	
	public List<Oficina> getOficinasActivas() throws Exception;
	
	public List<Oficina> buscarOficina(String criterio) throws Exception;
	
	public void deleteOficina(Integer id) throws Exception;

}
