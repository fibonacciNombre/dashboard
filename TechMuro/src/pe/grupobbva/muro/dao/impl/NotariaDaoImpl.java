package pe.grupobbva.muro.dao.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.dao.NotariaDAO;
import pe.grupobbva.muro.entity.Notaria;

public class NotariaDaoImpl extends AbstractDAO<Notaria> implements NotariaDAO   {
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Notaria> buscarNotaria(String nombre) {

		try {

			List<Notaria> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Notaria "
							+ "where upper(nombre) like upper(:nombre) and estado = 'A'")
					.setParameter("nombre", nombre)
					.list();
			
			return result;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
}
