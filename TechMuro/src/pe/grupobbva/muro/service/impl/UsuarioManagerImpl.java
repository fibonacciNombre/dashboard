package pe.grupobbva.muro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.grupobbva.muro.dao.Dao;
import pe.grupobbva.muro.dao.UsuarioDAO;
import pe.grupobbva.muro.entity.Usuario;
import pe.grupobbva.muro.service.UsuarioManager;

@Service
public class UsuarioManagerImpl extends ServiceImpl<Usuario> implements UsuarioManager {

	@Autowired
    private UsuarioDAO usuarioDAO;
	
	@Override
	protected Dao<Usuario> getDAO() {
		return usuarioDAO;
	}
	
	public Usuario verifyPass(Usuario usuario)  throws Exception{
		
		usuario = usuarioDAO.verifyPass(usuario);
		
		return usuario;
	}

}
