package pe.grupobbva.muro.service;

import java.util.List;

import pe.grupobbva.muro.entity.Contrato;
import pe.grupobbva.muro.entity.NotariaContrato;

public interface ContratoManager extends Service<Contrato> {

	public List<Contrato> contratoList();
	
	public List<NotariaContrato> findByCodigoOficina(String codigoOficina);
}
