package carrito.encapsulacion;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable // Anotamos para indicar que es una clase que se puede incrustar.
public class IteamVenta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Transient
    private String idIteam;
    @Embedded
    private ProductoComprado productoVenta;
    @Column(name = "Cantidad")
    private int cantProducto=0;
    @Transient
    private static int cant = 0;

    private BigDecimal cant_Total;

    public IteamVenta(ProductoComprado producto, int cantProducto) {
        cant+=1;
        this.setIdIteam(""+cant);
        this.cantProducto = cantProducto;
        this.productoVenta = producto;
        calcularTotal();
    }

    public IteamVenta() {
    }

    public BigDecimal getCant_Total() {
        return cant_Total;
    }

    public void setCant_Total(BigDecimal cant_Total) {
        this.cant_Total = cant_Total;
    }

    public String getIdIteam() {
        return idIteam;
    }

    public void setIdIteam(String idIteam) {
        this.idIteam = idIteam;
    }

    public int getCantProducto() {
        return cantProducto;
    }

    public ProductoComprado getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(ProductoComprado producto) {
        this.productoVenta = producto;
    }

    public void setCantProducto(int cantProducto) {
        this.cantProducto = cantProducto;
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = new BigDecimal("0.00");
        if(cantProducto>0){
            total = BigDecimal.valueOf(this.cantProducto * this.productoVenta.getPrecio().doubleValue());
        }
        setCant_Total(total);
        return total.setScale(2, RoundingMode.CEILING);
    }

}
