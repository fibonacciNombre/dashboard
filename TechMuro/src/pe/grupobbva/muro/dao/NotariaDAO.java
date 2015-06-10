package pe.grupobbva.muro.dao;

import java.util.List;

import pe.grupobbva.muro.entity.Notaria;

public interface NotariaDAO extends Dao<Notaria>  {

	public List<Notaria> buscarNotaria(String nombre);
}
