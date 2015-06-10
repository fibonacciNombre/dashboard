package pe.grupobbva.muro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.dao.LogMuroDAO;
import pe.grupobbva.muro.entity.LogMuro;
import pe.grupobbva.muro.service.LogMuroManager;

@Service
public class LogMuroManagerImpl extends ServiceImpl<LogMuro> implements LogMuroManager {
	
	@Autowired
	private LogMuroDAO logMuroDAO;
	
	@Override
	protected Dao<LogMuro> getDAO() {
		return logMuroDAO;
	}

}
