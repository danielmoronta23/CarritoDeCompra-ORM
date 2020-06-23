package practica2.encapsulacion;

import java.math.BigDecimal;


import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
public class CarroCompra {

    private String id;
    private int cont=0;
    private BigDecimal total;
    private List<ProductoCarrito> listaProducto; //Se deberia buscar otra forma para implentar esta parte

    public CarroCompra() {
        this.listaProducto = new ArrayList<ProductoCarrito>();
        total = calcularTotal();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void agregarProducto(Producto producto, int cantidad) {
        ProductoCarrito comprar = this.buscarProducto(producto.getId());
        cont++;
        if (comprar != null) {
            System.out.print("El producto ya habia agregado");
            this.listaProducto.add(new ProductoCarrito(producto, cantidad));



        } else {
            this.listaProducto.add(new ProductoCarrito(producto, cantidad));
            System.out.print("El producto aun no se ha puesto ");


        }
        setId("CP:"+cont);
        System.out.print(cantidad);
    }

    public void eliminarProducto(String codigo) {
        this.listaProducto.remove(this.buscarProducto(codigo));
        cont = cont -1;
    }

    private ProductoCarrito buscarProducto(String id) {
        for (ProductoCarrito compra : listaProducto) {
            if (compra.getIdProductoCarrito().equals(id)) {
                return compra;
            }
        }
        return null;
    }
    public BigDecimal calcularTotal() {
        BigDecimal total = new BigDecimal(0);
        for (ProductoCarrito comprar : this.listaProducto) {
            total = total.add(new BigDecimal(comprar.getCantProducto() * comprar.getProducto().getPrecio().doubleValue()));
        }

        return total.setScale(2, RoundingMode.CEILING);
    }
    public void setListaProducto(List<ProductoCarrito> listaProducto) {
        this.listaProducto = listaProducto;
    }
    public List<ProductoCarrito> getListaProducto() {
        return listaProducto;
    }
    public void limpiarCarrito(){
        this.listaProducto = new ArrayList<>();

        cont =0;
    }

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
