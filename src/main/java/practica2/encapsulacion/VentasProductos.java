package practica2.encapsulacion;

import java.util.Date;
import java.util.List;

public class VentasProductos {

    private static int cont = 0;
    private String id;
    private Date fechaCompra;
    private String nombreCliente;
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
