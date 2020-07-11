package carrito.encapsulacion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Venta_producto")
public class VentasProductos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private static int cont = 0;
    @Column(name = "fecha_Compra")
    private Date fechaCompra;
    @Column(name = "nombre_Cliente")
    private String nombreCliente;

    //@ElementCollection(fetch = FetchType.EAGER)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductoCarrito> listaProducto;

    public VentasProductos(Date fechaCompra, String nombreCliente, List<ProductoCarrito> listaProducto) {
        cont++;
        this.id ="V:"+cont;
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.listaProducto = listaProducto;
    }
    public VentasProductos(){


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<ProductoCarrito> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<ProductoCarrito> listaProducto) {
        this.listaProducto = listaProducto;

    }
}
