package pe.grupobbva.muro.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.dao.ProductoDAO;
import pe.grupobbva.muro.entity.Producto;

@Repository
public class ProductoDaoImpl extends AbstractDAO<Producto> implements
		ProductoDAO {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Producto> buscarProducto(String criterio, String tipoCliente) {

		try {

			List<Producto> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Producto "
							+ "where tipoCliente like :tipoCliente "
							+ "and upper(nombre) like upper(:nombre) and estado = 'A' "
							+ "order by orden asc")
					.setParameter("nombre", criterio)
					.setParameter("tipoCliente", tipoCliente).list();

			if (result != null) {
				Iterator<Producto> it = result.iterator();
				while (it.hasNext()) {
					it.next().setCapitulos(null);
				}
			}
			return result;
		} catch (Exception ex) {
			return null;
		}
	}
	
	@Transactional
	public Producto findByOrden(Producto producto, int valor){
		
		return (Producto)this.getSessionFactory().getCurrentSession().
				createQuery("from Producto where orden =:orden and tipocliente=:tipocliente")  
		          .setParameter("orden", producto.getOrden()+valor)
		          .setParameter("tipocliente", producto.getTipocliente())
		          .uniqueResult();
	}
	
	@Transactional
	public Integer getMaxOrder(String tipoCliente){
		
		return (Integer)this.getSessionFactory().getCurrentSession().
				createQuery("select nvl(max(orden),0)+1 from Producto where tipocliente=:tipocliente")  
		          .setParameter("tipocliente", tipoCliente)
		          .uniqueResult();
	}
	
	@Transactional
	public Producto validaNombre(Producto producto){
		
		return (Producto)this.getSessionFactory().getCurrentSession().
				createQuery("from Producto where trim(upper(nombre)) =trim(upper(:nombre)) "
							+ "and tipocliente=:tipocliente and estado = 'A'")  
		          .setParameter("nombre", producto.getNombre())
		          .setParameter("tipocliente", producto.getTipocliente())
		          .uniqueResult();
	}
	
}
