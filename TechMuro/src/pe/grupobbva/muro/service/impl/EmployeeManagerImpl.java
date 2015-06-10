package pe.grupobbva.muro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.dao.EmployeeDAO;
import pe.grupobbva.muro.entity.EmployeeEntity;
import pe.grupobbva.muro.service.EmployeeManager;

@Service
public class EmployeeManagerImpl extends ServiceImpl<EmployeeEntity> implements EmployeeManager {

	@Autowired
    private EmployeeDAO employeeDAO;
	
	@Override
	protected Dao<EmployeeEntity> getDAO() {
		return employeeDAO;
	}
	

}
