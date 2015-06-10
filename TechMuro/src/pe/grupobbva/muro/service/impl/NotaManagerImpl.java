package pe.grupobbva.muro.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.grupobbva.muro.dao.NotaDAO;
import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.entity.Nota;
import pe.grupobbva.muro.service.NotaManager;

@Service
public class NotaManagerImpl extends ServiceImpl<Nota> implements NotaManager {
	
	private static final Logger logger = Logger.getLogger(NotaManagerImpl.class);
	@Autowired
	private NotaDAO notaDAO;
	
	@Override
	protected Dao<Nota> getDAO(){
		return notaDAO;
	}
	
}
