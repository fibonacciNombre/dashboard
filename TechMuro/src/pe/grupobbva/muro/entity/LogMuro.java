package pe.grupobbva.muro.entity;

// Generated Feb 27, 2014 5:00:05 PM by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * LogMuro generated by hbm2java
 */
@Entity
@Table(name = "LOG_MURO")
public class LogMuro implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4334651075182639041L;
	
	private Integer idlog;
	private Privilegio privilegio;
	private Usuario usuario;
	private String opcion;
	private Date fecha;
	private String valoranterior;
	private String valornuevo;
	private Integer id;
	private String campo;

	public LogMuro() {
	}

	public LogMuro(Integer idlog) {
		this.idlog = idlog;
	}

	public LogMuro(Integer idlog, Privilegio privilegio, Usuario usuario,
			String opcion, Date fecha, String valoranterior, String valornuevo,
			Integer id, String campo) {
		this.idlog = idlog;
		this.privilegio = privilegio;
		this.usuario = usuario;
		this.opcion = opcion;
		this.fecha = fecha;
		this.valoranterior = valoranterior;
		this.valornuevo = valornuevo;
		this.id = id;
		this.campo = campo;
	}

	@Id
	@Column(name = "IDLOG", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "seq_muro_log", sequenceName = "SEQ_MURO_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_muro_log")
	public Integer getIdlog() {
		return this.idlog;
	}

	public void setIdlog(Integer idlog) {
		this.idlog = idlog;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDPRIVILEGIO")
	public Privilegio getPrivilegio() {
		return this.privilegio;
	}

	public void setPrivilegio(Privilegio privilegio) {
		this.privilegio = privilegio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDUSUARIO")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "OPCION", length = 20)
	public String getOpcion() {
		return this.opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA", length = 7)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Column(name = "VALORANTERIOR", length = 2000)
	public String getValoranterior() {
		return this.valoranterior;
	}

	public void setValoranterior(String valoranterior) {
		this.valoranterior = valoranterior;
	}

	@Column(name = "VALORNUEVO", length = 2000)
	public String getValornuevo() {
		return this.valornuevo;
	}

	public void setValornuevo(String valornuevo) {
		this.valornuevo = valornuevo;
	}

	@Column(name = "ID", precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CAMPO", length = 40)
	public String getCampo() {
		return this.campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

}
