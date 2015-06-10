package pe.grupobbva.muro.service;

import java.util.List;

import pe.grupobbva.muro.entity.VariablesGenerales;

public interface VariableManager extends Service<VariablesGenerales> {

	public List<VariablesGenerales> tipoClienteList();
}
