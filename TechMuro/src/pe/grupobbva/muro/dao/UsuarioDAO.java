package pe.grupobbva.muro.dao;

import pe.grupobbva.muro.entity.Usuario;

public interface UsuarioDAO extends Dao<Usuario>{

	public Usuario verifyPass(Usuario usuario);
}