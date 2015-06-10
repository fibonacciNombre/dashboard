package pe.grupobbva.muro.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.dao.ContratoDAO;
import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.dao.OficinaDAO;
import pe.grupobbva.muro.entity.Contrato;
import pe.grupobbva.muro.entity.Notaria;
import pe.grupobbva.muro.entity.NotariaContrato;
import pe.grupobbva.muro.entity.Oficina;
import pe.grupobbva.muro.service.ContratoManager;

@Service
public class ContratoManagerImpl extends ServiceImpl<Contrato> implements ContratoManager {
	
	private static final Logger logger = Logger.getLogger(ContratoManagerImpl.class);
	
	@Autowired
	private ContratoDAO contratoDAO;
	@Autowired
	private OficinaDAO oficinaDAO;
	
	@Override
	protected Dao<Contrato> getDAO() {
		return contratoDAO; 
	}

	@Override
	public List<Contrato> contratoList() {
		
		return contratoDAO.contratoList();
	}
	
	@Override
	@Transactional 
	public List<NotariaContrato> findByCodigoOficina(String codigoOficina) {
		try{
			
			Oficina oficina = oficinaDAO.getOficinasByCodigo(codigoOficina);
			
			logger.debug(oficina.getPlaza().getIdplaza());
			
			List<NotariaContrato> notariaContratos = contratoDAO.findByPlaza(oficina.getPlaza());
			
			for(NotariaContrato lista : notariaContratos){
				logger.debug(lista.getContrato().getDescripcion());
				logger.debug(lista.getContrato().getGastos());
				logger.debug(lista.getNotaria().getNombre());
			}
			
			
			return notariaContratos;
		}catch(Exception ex){
			logger.error("agregarNotariaContrato -->"+ex);
			return null;
		}
	}
	

}
