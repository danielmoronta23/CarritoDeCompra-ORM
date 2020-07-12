package carrito.services;

import carrito.encapsulacion.VentasProductos;

/**
 * Clase que permite manejar las tablas relaciona a la VENTA
 */
public class ServicioVenta extends ManejadorBD<VentasProductos>{

    public ServicioVenta() {
        super(VentasProductos.class);
    }

}
