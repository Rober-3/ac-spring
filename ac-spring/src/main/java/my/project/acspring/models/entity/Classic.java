package my.project.acspring.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import my.project.acspring.models.validation.MaxAnnotation;


@Entity
@Table(name = "clasicos")
public class Classic implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "nombre", unique = true)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "marca_id")
	private Brand brand;
	
	@MaxAnnotation
	@Min(value = 1886)
	@NotBlank
	@Column(name = "anio")
	private String year;
	
	@Column(name = "foto")
	private String photo;
	
	@Column(name = "aprobado")
	private Boolean approved;
	
	@Column(name = "eliminado")
	private Boolean deleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private UserR user;
	
	
	public Classic() {
		this.id = (long) 0;
		this.name = "";
		this.brand = new Brand();
		this.year = "";
		this.photo = "";
		this.approved = false;
		this.deleted = false;
		this.user = new UserR();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	public Boolean getApproved() {
		return approved;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
	
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	
	public UserR getUser() {
		return user;
	}
	public void setUser(UserR user) {
		this.user = user;
	}
	
}
