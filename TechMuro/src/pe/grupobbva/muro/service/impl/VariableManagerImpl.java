package pe.grupobbva.muro.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.dao.VariableDAO;
import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.entity.VariablesGenerales;
import pe.grupobbva.muro.service.VariableManager;
import pe.grupobbva.muro.util.Constants;

@Service
public class VariableManagerImpl extends ServiceImpl<VariablesGenerales> implements VariableManager {
	
	private static final Logger logger = Logger.getLogger(VariableManagerImpl.class);
	@Autowired
	private VariableDAO variableDAO;
	
	@Override
	protected Dao<VariablesGenerales> getDAO(){
		return variableDAO;
	}
	
	@Transactional
	public List<VariablesGenerales> tipoClienteList(){
		List<VariablesGenerales> lista = new ArrayList<VariablesGenerales>();
		try{
			VariablesGenerales var1 = variableDAO.findById(Constants.TIPOCLIENTE1);
			VariablesGenerales var2 = variableDAO.findById(Constants.TIPOCLIENTE2);
			
			if(var1!=null||var2!=null){
				lista.add(var1);
				lista.add(var2);
			}
		}catch(Exception e){
			logger.error("Error tipoClienteList ->"+e);
			
			return null;
		}
		
		return lista;
	}

}
