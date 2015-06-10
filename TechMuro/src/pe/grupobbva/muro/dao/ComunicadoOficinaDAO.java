package pe.grupobbva.muro.dao;

import java.util.List;

import pe.grupobbva.muro.entity.ComunicadoOficina;

public interface ComunicadoOficinaDAO extends Dao<ComunicadoOficina> {
	
	
	public List<ComunicadoOficina> getCO() throws Exception;
	
	public List<ComunicadoOficina> buscarComunicado(String criterio) throws Exception;
	
	public List<ComunicadoOficina> deleteComunicado(Integer criterio1,Integer criterio2) throws Exception;

}
