package pe.grupobbva.muro.dao.impl;

import org.springframework.stereotype.Repository;

import pe.grupobbva.muro.dao.PlazaDAO;
import pe.grupobbva.muro.entity.Plaza;
import pe.grupobbva.muro.entity.Producto;

@Repository
public class PlazaDaoImpl extends AbstractDAO<Plaza> implements PlazaDAO{

	public Plaza findByNombre(String nombre){
		return (Plaza)this.getSessionFactory().getCurrentSession().
				createQuery("from Plaza where trim(upper(nombre)) =trim(upper(:nombre)) and estado = 'A'")  
		          .setParameter("nombre", nombre)
		          .uniqueResult();
	}
}
