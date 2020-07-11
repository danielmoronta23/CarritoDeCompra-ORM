package carrito.encapsulacion;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity // Anotamos para indicar que es una clase que se puede incrustar.
public class ProductoCarrito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String IdProductoCarrito;
    @OneToOne(cascade = CascadeType.ALL) //creando realacion con la tabla producto
    private Producto producto;
    @Column(name = "Cantidad")
    private int cantProducto=0;

    /**
     @Id
     private String IdProductoCarrito;
     **/
    private BigDecimal cant_Total;
    private static int cant = 0;


    public BigDecimal getCant_Total() {
        return cant_Total;
    }

    public void setCant_Total(BigDecimal cant_Total) {
        this.cant_Total = cant_Total;
    }

    public ProductoCarrito(Producto producto, int cantProducto) {
        cant+=1;
        this.setIdProductoCarrito("PC:"+cant);
        this.cantProducto = cantProducto;
        this.producto = producto;
        calcularTotal();
    }

    public ProductoCarrito() {
        calcularTotal();
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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCantProducto(int cantProducto) {
        this.cantProducto = cantProducto;
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = new BigDecimal("0.00");
        if(cantProducto>0){
            total = BigDecimal.valueOf(this.cantProducto * this.producto.getPrecio().doubleValue());

        }

        setCant_Total(total);
        return total.setScale(2, RoundingMode.CEILING);
    }

}
