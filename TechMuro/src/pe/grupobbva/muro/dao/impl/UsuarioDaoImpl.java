package pe.grupobbva.muro.dao.impl;

import java.util.Iterator;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.grupobbva.muro.dao.UsuarioDAO;
import pe.grupobbva.muro.entity.PerfilPrivilegio;
import pe.grupobbva.muro.entity.Usuario;

@Repository
public class UsuarioDaoImpl extends AbstractDAO<Usuario> implements UsuarioDAO {
	
	@Transactional
	public Usuario verifyPass(Usuario usuario) {
		try{

		usuario = (Usuario)this.getSessionFactory().getCurrentSession().
				createQuery("from Usuario where codigo =:codigo")  
		          .setParameter("codigo", usuario.getCodigo())
		          .uniqueResult();
		
		if(usuario!=null){
			usuario.getPerfil().getPerfilPrivilegios().size();
			
			Iterator<PerfilPrivilegio> it = usuario.getPerfil().getPerfilPrivilegios().iterator();
			
			while(it.hasNext()){
				it.next().getPrivilegio().getDescripcion();
			}
		}
		
		return usuario;
		} catch(Exception ex){
			return null;
		}
	}
}
