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
 * Transaccion generated by hbm2java
 */
@Entity
@Table(name = "TRANSACCION")
public class Transaccion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4543601866303759014L;
	
	private Integer idtransaccion;
	private Categoria categoria;
	private String nombre;
	private String descripcion;
	private Date fechacreacion;
	private Character estado;
	private Set<TransaccionDetalle> transaccionDetalles = new HashSet<TransaccionDetalle>(
			0);

	public Transaccion() {
	}

	public Transaccion(Integer idtransaccion) {
		this.idtransaccion = idtransaccion;
	}

	public Transaccion(Integer idtransaccion, Categoria categoria,
			String nombre, String descripcion, Date fechacreacion,
			Character estado, Set<TransaccionDetalle> transaccionDetalles) {
		this.idtransaccion = idtransaccion;
		this.categoria = categoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechacreacion = fechacreacion;
		this.estado = estado;
		this.transaccionDetalles = transaccionDetalles;
	}

	@Id
	@Column(name = "IDTRANSACCION", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getIdtransaccion() {
		return this.idtransaccion;
	}

	public void setIdtransaccion(Integer idtransaccion) {
		this.idtransaccion = idtransaccion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDCATEGORIA")
	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "transaccion")
	public Set<TransaccionDetalle> getTransaccionDetalles() {
		return this.transaccionDetalles;
	}

	public void setTransaccionDetalles(
			Set<TransaccionDetalle> transaccionDetalles) {
		this.transaccionDetalles = transaccionDetalles;
	}

}