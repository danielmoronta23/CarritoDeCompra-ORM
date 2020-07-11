package carrito.services;

import carrito.encapsulacion.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServicioProducto extends ManejadorBD<Producto> {

    public ServicioProducto() {
        super(Producto.class);
    }
}
