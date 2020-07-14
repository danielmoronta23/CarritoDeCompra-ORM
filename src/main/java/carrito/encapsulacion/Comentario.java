package carrito.encapsulacion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Comentario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Comentario")
    private String id;
    @ManyToOne
    private Producto producto;
    @Column(name = "Comentario")
    private String comentario;
    @Column(name = "Fecha")
    private Date fecha;

    public Comentario() {
    }

    public Comentario(Producto producto, String comentario, Date fecha) {
        this.producto = producto;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
