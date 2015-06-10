package pe.grupobbva.muro.service;

import pe.grupobbva.muro.entity.Comunicado;

public interface ComunicadoManager extends Service<Comunicado>{
	
	public byte[] byId(Integer id) throws Exception;

}
