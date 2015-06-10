package pe.grupobbva.muro.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.controller.NotariaController;
import pe.grupobbva.muro.dao.CapituloDAO;
import pe.grupobbva.muro.entity.Capitulo;
import pe.grupobbva.muro.entity.Producto;

@Repository
public class CapituloDaoImpl extends AbstractDAO<Capitulo> implements
	CapituloDAO {

	private static final Logger logger = Logger.getLogger(CapituloDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Capitulo> buscarCapitulo(Capitulo capitulo) {

		try {

			List<Capitulo> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Capitulo "
							+ "where producto.idproducto = :idproducto "
							+ "and upper(nombre) like upper(:nombre||'%') and estado = 'A' "
							+ "order by orden asc")
					.setParameter("nombre", capitulo.getNombre())
					.setParameter("idproducto", capitulo.getProducto().getIdproducto()).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error buscarCapitulo ->" +ex);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Capitulo> findByProducto(int id) {

		try {

			List<Capitulo> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Capitulo "
							+ "where producto.idproducto = :idproducto "
							+ "and estado = 'A' "
							+ "order by orden asc")
					.setParameter("idproducto", id).list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error findByProducto ->" +ex);
			return null;
		}
	}
	
	@Transactional
	public Capitulo validaCapitulo(Capitulo capitulo){
		
		return (Capitulo)this.getSessionFactory().getCurrentSession().
				createQuery("from Capitulo where producto.idproducto = :idproducto "
							+ "and trim(upper(nombre)) =trim(upper(:nombre)) and estado = 'A'")  
		          .setParameter("nombre", capitulo.getNombre())
		          .setParameter("idproducto", capitulo.getProducto().getIdproducto())
		          .uniqueResult();
	}
	
	@Transactional
	public Integer getMaxOrder(Capitulo capitulo){
		
		return (Integer)this.getSessionFactory().getCurrentSession().
				createQuery("select nvl(max(orden),0)+1 from Capitulo "
							+ "where producto.idproducto=:idproducto and estado = 'A'")  
		          .setParameter("idproducto", capitulo.getProducto().getIdproducto())
		          .uniqueResult();
	}
	
	@Transactional
	public Capitulo findByOrden(Capitulo capitulo, int valor){
		
		return (Capitulo)this.getSessionFactory().getCurrentSession().
				createQuery("from Capitulo where orden =:orden and producto.idproducto=:idproducto and estado = 'A'")
		          .setParameter("orden", capitulo.getOrden()+valor)
		          .setParameter("idproducto", capitulo.getProducto().getIdproducto())
		          .uniqueResult();
	}
	
}
