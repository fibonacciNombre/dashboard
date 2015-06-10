package pe.grupobbva.muro.entity;

// Generated Feb 27, 2014 5:00:05 PM by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AficheOficina generated by hbm2java
 */
@Entity
@Table(name = "AFICHE_OFICINA")
public class AficheOficina implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6436270010040336281L;
	
	private AficheOficinaId id;
	private Oficina oficina;
	private Afiche afiche;

	public AficheOficina() {
	}

	public AficheOficina(AficheOficinaId id) {
		this.id = id;
	}

	public AficheOficina(AficheOficinaId id, Oficina oficina, Afiche afiche) {
		this.id = id;
		this.oficina = oficina;
		this.afiche = afiche;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idafiche", column = @Column(name = "IDAFICHE", precision = 22, scale = 0)),
			@AttributeOverride(name = "idoficina", column = @Column(name = "IDOFICINA", precision = 22, scale = 0)) })
	public AficheOficinaId getId() {
		return this.id;
	}

	public void setId(AficheOficinaId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDOFICINA", insertable = false, updatable = false)
	public Oficina getOficina() {
		return this.oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDAFICHE", insertable = false, updatable = false)
	public Afiche getAfiche() {
		return this.afiche;
	}

	public void setAfiche(Afiche afiche) {
		this.afiche = afiche;
	}

}
