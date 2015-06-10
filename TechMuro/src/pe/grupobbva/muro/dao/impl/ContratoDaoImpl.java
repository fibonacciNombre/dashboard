package pe.grupobbva.muro.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.dao.ContratoDAO;
import pe.grupobbva.muro.entity.Contrato;
import pe.grupobbva.muro.entity.NotariaContrato;
import pe.grupobbva.muro.entity.Plaza;

public class ContratoDaoImpl extends AbstractDAO<Contrato> implements ContratoDAO   {

	private static final Logger logger = Logger.getLogger(ContratoDaoImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Contrato> contratoList() {
		try {
			
			List<Contrato> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Contrato "
							+ "where estado = 'A'")
					.list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error en contratoList"+ex);
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public Contrato findByNombreGastos(String descripcion, String gastos) {
		try {
			
			Contrato result = (Contrato)this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from Contrato "
							+ "where trim(upper(descripcion)) = trim(upper(:descripcion)) "
							+ "and trim(upper(gastos)) = trim(upper(:gastos)) and estado = 'A'")
					.setParameter("descripcion", descripcion)
					.setParameter("gastos", gastos)
					.uniqueResult();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error en contratoList"+ex);
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<NotariaContrato> findByPlaza(Plaza plaza) {
		
		try {
			
			List<NotariaContrato> result = this
					.getSessionFactory()
					.getCurrentSession()
					.createQuery("from NotariaContrato nc "
							+ "where nc.id.idnotaria in ( "
							+ "select distinct n.idnotaria from Notaria n "
							+ "where n.plaza.idplaza = :idplaza and n.estado = 'A' "
							+ ") order by nc.contrato.descripcion asc, nc.notaria.nombre asc")
					.setParameter("idplaza", plaza.getIdplaza())
					.list();
			
			return result;
		} catch (Exception ex) {
			logger.error("Error en contratoList"+ex);
			return null;
		}
	}
}
