package pe.grupobbva.muro.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.service.Service;

public abstract class ServiceImpl<E>  implements Service<E> {
	
	protected abstract Dao<E> getDAO();

	@Override
	@Transactional
	public void add(E entity) throws Exception {
		getDAO().add(entity);
	}

	@Override
	@Transactional
	public List<E> getAll() throws Exception{
//		return getDAO().findByPage(1, 2);
		return getDAO().getAll();
	}
	
	@Override
	@Transactional
	public List<E> getAnything() throws Exception{
		return getDAO().getAnything();
	}
	
	

	@Override
	@Transactional
	public void delete(Integer id) throws Exception{
		getDAO().delete(id);
	}
	
	@Override
	@Transactional
	public E finById(Integer id) throws Exception{
		return getDAO().findById(id);
	}
	
	@Override
	@Transactional
	public void update(E entity) throws Exception{
		getDAO().update(entity);
	}

}
