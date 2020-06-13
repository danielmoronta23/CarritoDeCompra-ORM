package practica2.encapsulacion;

import java.math.BigDecimal;


import java.util.List;
public class CarroCompra {
    private String id;
    private List<Producto> listaProducto;

    public CarroCompra(String id, List<Producto> listaProducto) {
        this.id = id;
        this.listaProducto = listaProducto;
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
