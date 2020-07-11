package carrito.encapsulacion;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity //para indicar que la entidad será maneja por el provedor de persistencia
@Table(name = "Producto")
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "precio")
    private BigDecimal precio;


    //Constructor por defecto
    public Producto() {
    }

    public Producto(String nombre, BigDecimal precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

}