package carrito.encapsulacion;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "password")
    private String password;

    public Usuario(String nombre, String password) {
       // this.id = id;
        this.usuario = nombre;
        this.password = password;
    }

    public Usuario() {
    }
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String nombre) {
        this.usuario = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
