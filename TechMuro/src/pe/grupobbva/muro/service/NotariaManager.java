package pe.grupobbva.muro.service;

import java.util.List;

import pe.grupobbva.muro.entity.Contrato;
import pe.grupobbva.muro.entity.Notaria;
import pe.grupobbva.muro.entity.NotariaContrato;
import pe.grupobbva.muro.model.UploadedFile;

public interface NotariaManager extends Service<Notaria> {

	public List<Notaria> buscarNotaria(String nombre);
	
	public boolean eliminarNotaria(Notaria notaria);
	
	public Notaria getNotaria(Notaria notaria);
	
	public boolean agregarNotariaContrato(Notaria notaria, List<Contrato> contratoList);
	
	public boolean deleteNotariaContrato(List<NotariaContrato> contratoList);
	
	public boolean subirArchivoNotarias(UploadedFile uploadedFile);
}
