package pe.grupobbva.muro.service;

import pe.grupobbva.muro.entity.Usuario;

public interface UsuarioManager extends Service<Usuario> {

	public Usuario verifyPass(Usuario usuario) throws Exception;
	
}
