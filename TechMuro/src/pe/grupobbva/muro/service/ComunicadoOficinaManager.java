package pe.grupobbva.muro.service;

import java.util.List;

import pe.grupobbva.muro.entity.ComunicadoOficina;

public interface ComunicadoOficinaManager extends Service<ComunicadoOficina> {
	
	public List<ComunicadoOficina> getCO() throws Exception;
	
	public List<ComunicadoOficina> buscarComunicado(String criterio) throws Exception;
		
	public void deleteComunicado(Integer idA, Integer idO) throws Exception;
	
	public ComunicadoOficina byIdCO(Integer criterio1, Integer criterio2) throws Exception;

}
