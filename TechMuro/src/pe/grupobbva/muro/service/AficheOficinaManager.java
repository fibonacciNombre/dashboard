package pe.grupobbva.muro.service;

import java.util.List;

import pe.grupobbva.muro.entity.AficheOficina;

public interface AficheOficinaManager extends Service<AficheOficina> {
	
	 public List<AficheOficina> getAO() throws Exception;
	 public List<AficheOficina> buscarAfi(String criterio) throws Exception ;
	 public void deleteAfi(Integer idA, Integer idO) throws Exception;
	 public AficheOficina byIdAO(Integer criterio1, Integer criterio2) throws Exception;

}
