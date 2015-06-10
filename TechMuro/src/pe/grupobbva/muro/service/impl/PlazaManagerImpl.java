package pe.grupobbva.muro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.dao.PlazaDAO;
import pe.grupobbva.muro.entity.Plaza;
import pe.grupobbva.muro.service.PlazaManager;

@Service
public class PlazaManagerImpl extends ServiceImpl<Plaza> implements PlazaManager {
	
	@Autowired
	private PlazaDAO plazaDAO;
	@Override
	protected Dao<Plaza> getDAO() {
		return plazaDAO; 
	}

}
