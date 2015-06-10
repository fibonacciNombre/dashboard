package pe.grupobbva.muro.dao;

import java.util.List;

import pe.grupobbva.muro.entity.Contrato;
import pe.grupobbva.muro.entity.NotariaContrato;
import pe.grupobbva.muro.entity.Plaza;

public interface ContratoDAO extends Dao<Contrato>  {

	public List<Contrato> contratoList();
	public Contrato findByNombreGastos(String descripcion, String gastos);
	
	public List<NotariaContrato> findByPlaza(Plaza plaza);
	
}
