package practica2.encapsulacion;



import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductoCarrito {
    private int cantProducto=0;
    private BigDecimal cant_Total;
    private Producto producto;
    private String IdProductoCarrito;
    private static int cant = 0;

    public ProductoCarrito(Producto producto, int cantProducto) {
        cant+=1;
        this.setIdProductoCarrito("PC:"+cant);
        this.cantProducto = cantProducto;
        this.producto = producto;
        this.cantProducto = cantProducto;
        this.cant_Total = this.calcularTotal();
    }
    public String getIdProductoCarrito() {
        return IdProductoCarrito;
    }

    public void setIdProductoCarrito(String idProductoCarrito) {
        IdProductoCarrito = idProductoCarrito;
    }

    public int getCantProducto() {
        return cantProducto;
    }

    public BigDecimal getCant_Total() {
        return cant_Total;
    }

    public void setCant_Total(BigDecimal cant_Total) {
        this.cant_Total = cant_Total;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCantProducto(int cantProducto) {
        this.cantProducto = cantProducto;
    }
    private BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.valueOf(this.cantProducto * this.producto.getPrecio().doubleValue());

        return total.setScale(2, RoundingMode.CEILING);
    }

}
