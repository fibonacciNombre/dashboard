package pe.grupobbva.muro.entity;

// Generated Feb 27, 2014 5:00:05 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Rubro generated by hbm2java
 */
@Entity
@Table(name = "RUBRO")
public class Rubro implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8127131132990570189L;
	
	private Integer idrubro;
	private Nota nota;
	private Subcapitulo subcapitulo;
	private String nombre;
	private String descripcion;
	private Date fechacreacion;
	private Character estado;
	private Integer orden;
	private Set<Columna> columnas = new HashSet<Columna>(0);
	private Set<Categoria> categorias = new HashSet<Categoria>(0);

	public Rubro() {
	}

	public Rubro(Integer idrubro) {
		this.idrubro = idrubro;
	}

	public Rubro(Integer idrubro, Nota nota, Subcapitulo subcapitulo,
			String nombre, String descripcion, Date fechacreacion,
			Character estado, Integer orden, Set<Columna> columnas,
			Set<Categoria> categorias) {
		this.idrubro = idrubro;
		this.nota = nota;
		this.subcapitulo = subcapitulo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechacreacion = fechacreacion;
		this.estado = estado;
		this.orden = orden;
		this.columnas = columnas;
		this.categorias = categorias;
	}

	@Id
	@Column(name = "IDRUBRO", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getIdrubro() {
		return this.idrubro;
	}

	public void setIdrubro(Integer idrubro) {
		this.idrubro = idrubro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDNOTA")
	public Nota getNota() {
		return this.nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDSUBCAPITULO")
	public Subcapitulo getSubcapitulo() {
		return this.subcapitulo;
	}

	public void setSubcapitulo(Subcapitulo subcapitulo) {
		this.subcapitulo = subcapitulo;
	}

	@Column(name = "NOMBRE", length = 200)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "DESCRIPCION", length = 500)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHACREACION", length = 7)
	public Date getFechacreacion() {
		return this.fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	@Column(name = "ESTADO", length = 1)
	public Character getEstado() {
		return this.estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	@Column(name = "ORDEN", precision = 22, scale = 0)
	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rubro")
	public Set<Columna> getColumnas() {
		return this.columnas;
	}

	public void setColumnas(Set<Columna> columnas) {
		this.columnas = columnas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rubro")
	public Set<Categoria> getCategorias() {
		return this.categorias;
	}

	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}

}
