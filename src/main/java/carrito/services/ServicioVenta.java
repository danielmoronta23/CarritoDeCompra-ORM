package carrito.services;

import carrito.encapsulacion.ProductoCarrito;
import carrito.encapsulacion.VentasProductos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que permite manejar las tablas relaciona a la VENTA
 */
public class ServicioVenta extends ManejadorBD<VentasProductos>{

    public ServicioVenta() {
        super(VentasProductos.class);
    }

}
