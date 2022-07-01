package my.project.acspring.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "usuarios")
public class UserR implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "nombre_usuario", unique = true)
	private String username;

	@Column(name = "contrasena")
	private String password;

	@Column(name = "correo", unique = true)
	private String email;

	@Column(name = "foto")
	private String photo;

	@Column(name = "habilitado")
	private Boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "usuarios_roles",
			   joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
			   inverseJoinColumns = @JoinColumn (name = "rol_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	@OneToMany(mappedBy = "user", cascade=CascadeType.ALL)
	@Column(name = "clasicos")
	private List<Classic> classics;


	public UserR() {
		super();
		this.id = (long) 0;
		this.username = "";
		this.password = "";
		this.email = "";
		this.photo = "";
		this.enabled = true;
		this.roles = new ArrayList<Role>();
		this.classics =  new ArrayList<Classic>();
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}


	public List<Classic> getClassics() {
		return classics;
	}
	public void setClassics(List<Classic> classics) {
		this.classics = classics;
	}

}
