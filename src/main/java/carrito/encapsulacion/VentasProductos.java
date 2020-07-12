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

    @Column(name = "fecha_Compra")
    private Date fechaCompra;
    @Column(name = "nombre_Cliente")
    private String nombreCliente;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<IteamVenta> listaProducto;

    public VentasProductos(Date fechaCompra, String nombreCliente, List<IteamVenta> listaProducto) {

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

    public List<IteamVenta> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<IteamVenta> listaProducto) {
        this.listaProducto = listaProducto;
    }
}
