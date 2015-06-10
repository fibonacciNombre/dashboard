package pe.grupobbva.muro.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.dao.ComunicadoOficinaDAO;
import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.entity.ComunicadoOficina;
import pe.grupobbva.muro.service.ComunicadoOficinaManager;

@Service
public class ComunicadoOficinaManagerImpl extends ServiceImpl<ComunicadoOficina> implements ComunicadoOficinaManager{
	
	private static final Logger logger = Logger.getLogger(ComunicadoOficinaManagerImpl.class);
	
	@Autowired
	private ComunicadoOficinaDAO comunicadoOficinaDAO;
	
	@Override
	protected Dao<ComunicadoOficina> getDAO() {

		return comunicadoOficinaDAO;
	}
	
	@Override
	@Transactional
	public List<ComunicadoOficina> getCO() throws Exception {
		return comunicadoOficinaDAO.getCO();
	}
	
	@Override
	@Transactional
	public List<ComunicadoOficina> buscarComunicado(String criterio) throws Exception {
		return comunicadoOficinaDAO.buscarComunicado(criterio);
	}
	
	@Override
	@Transactional
	public void deleteComunicado(Integer idA, Integer idO) throws Exception {
		
		if(comunicadoOficinaDAO.deleteComunicado(idA, idO).size() > 0){
			
			ComunicadoOficina comunicadoOficina = comunicadoOficinaDAO.deleteComunicado(idA, idO).get(0);
			logger.debug(comunicadoOficina.getComunicado());
			comunicadoOficinaDAO.deleteFisico(comunicadoOficina);
			
		}
	}
	
	@Override
	@Transactional
	public ComunicadoOficina byIdCO(Integer criterio1, Integer criterio2) throws Exception {
		return comunicadoOficinaDAO.deleteComunicado(criterio1, criterio2).get(0);
	}

}
