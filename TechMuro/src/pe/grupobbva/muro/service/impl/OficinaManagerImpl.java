package pe.grupobbva.muro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.dao.OficinaDAO;
import pe.grupobbva.muro.entity.Oficina;
import pe.grupobbva.muro.service.OficinaManager;

@Service
public class OficinaManagerImpl extends ServiceImpl<Oficina> implements OficinaManager {
	
	@Autowired
	private OficinaDAO oficinaDAO;
	
	@Override
	protected Dao<Oficina> getDAO() {
		return oficinaDAO;
	}
	
	@Override
	@Transactional
	public List<Oficina> getOficinasFilter() throws Exception {

		return oficinaDAO.getOficinasFilter();
	}

	@Override
	@Transactional
	public Oficina getOficinasVisor(Integer id) throws Exception {
		return oficinaDAO.getOficinasVisor(id);
	}

	@Override
	@Transactional
	public Oficina getOficinasByCodigoVisor(String criterio) throws Exception {
		
		String codigo = criterio.replaceFirst("^0+(?!$)", "");
		return oficinaDAO.getOficinasByCodigoVisor(codigo);
	}

	@Override
	@Transactional
	public List<Oficina> getOficinasActivas() throws Exception {
		return oficinaDAO.getOficinasActivas();
	}

	@Override
	@Transactional
	public List<Oficina> buscarOficina(String criterio) throws Exception {
		return oficinaDAO.buscarOficina(criterio);
	}
	
	@Override
	@Transactional
	public void deleteOficina(Integer id) throws Exception {
		Oficina oficina = oficinaDAO.findById(id);
		
		oficina.setEstado('I');
		
		oficinaDAO.update(oficina);
	}

}
