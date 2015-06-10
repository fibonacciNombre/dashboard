package pe.grupobbva.muro.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.dao.AficheOficinaDAO;
import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.entity.AficheOficina;
import pe.grupobbva.muro.service.AficheOficinaManager;

@Service
public class AficheOficinaManagerImpl extends ServiceImpl<AficheOficina> implements AficheOficinaManager{
	
	private static final Logger logger = Logger.getLogger(AficheOficinaManagerImpl.class);

	@Autowired
	private AficheOficinaDAO aficheOficinaDAO;
	
	@Override
	@Transactional
	protected Dao<AficheOficina> getDAO() {
		return aficheOficinaDAO;
	}

	@Override
	@Transactional
	public List<AficheOficina> getAO() throws Exception {
		return aficheOficinaDAO.getAO();
	}
	
	@Override
	@Transactional
	public List<AficheOficina> buscarAfi(String criterio) throws Exception {
		return aficheOficinaDAO.buscarAfi(criterio);
	}
	
	@Override
	@Transactional
	public void deleteAfi(Integer idA, Integer idO) throws Exception {
		
		if(aficheOficinaDAO.deleteAfi(idA, idO).size() > 0){
			
			AficheOficina aficheOficina = aficheOficinaDAO.deleteAfi(idA, idO).get(0);
			logger.debug(aficheOficina.getAfiche());
			aficheOficinaDAO.deleteFisico(aficheOficina);
			
		}
	}
	
	@Override
	@Transactional
	public AficheOficina byIdAO(Integer criterio1, Integer criterio2) throws Exception {
		return aficheOficinaDAO.deleteAfi(criterio1, criterio2).get(0);
	}
	

}
