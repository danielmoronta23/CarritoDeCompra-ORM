package practica2.services;

import jdk.jshell.spi.SPIResolutionException;
import org.jetbrains.annotations.NotNull;
import practica2.encapsulacion.Producto;
import practica2.encapsulacion.ProductoCarrito;
import practica2.encapsulacion.Usuario;
import practica2.encapsulacion.VentasProductos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que permite manejar las tablas relaciona a la VENTA
 */
public class ServicioVenta {


    public static boolean realizarVenta(VentasProductos venta){
        boolean estado = false;
        Connection con = null;
        try {
            System.out.println("\nEntrendo medoto realizarVentas() desde servicio producto\n");
            String sql = "INSERT INTO VENTA(ID, FECHA_COMPRA, NOMBRE_CLIENTE) VALUES(?,?,?)";
            con = ConexionBD.getInstance().getConexion();
            Date fecha = new Date(venta.getFechaCompra().getTime());

            PreparedStatement prepareStatement = con.prepareStatement(sql);
            prepareStatement.setString(1,venta.getId());
            prepareStatement.setDate(2, fecha);
            prepareStatement.setString(3,venta.getNombreCliente());
            prepareStatement.executeUpdate();
            estado = realizarVentaProducto(venta.getId(),venta.getListaProducto());//agregando lista de producto a una tabla

        } catch (SQLException throwables) {
            Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, throwables);
            }
        }
        return  estado;

    }

    public static boolean realizarVentaProducto(String idVenta, List<ProductoCarrito> productoCarritos){
        boolean estado = false;
        Connection con = null;

        for (ProductoCarrito aux:productoCarritos) {
            System.out.println("\nAGREANDO PRODUCTO A TABLA PRODUCTO_VENTA\n");
            try {
                String sql = "INSERT INTO PRODUCTO_VENTA(ID_ITEM, ID_VENTA, ID_PRODUCTO, CANTIDAD) VALUES(?,?, ?,?)";
                con = ConexionBD.getInstance().getConexion();

                PreparedStatement prepareStatement = con.prepareStatement(sql);
                prepareStatement.setString(1, aux.getIdProductoCarrito());
                prepareStatement.setString(2, idVenta);
                prepareStatement.setString(3, aux.getProducto().getId());
                prepareStatement.setInt(4, aux.getCantProducto());

                if(prepareStatement.executeUpdate() > 0){
                    estado = true;
                }

            } catch (SQLException throwables) {
                Logger.getLogger(ServicioVenta.class.getName()).log(Level.SEVERE, null, throwables);
            } finally {
                try {
                    con.close();
                } catch (SQLException throwables) {
                    Logger.getLogger(ServicioVenta.class.getName()).log(Level.SEVERE, null, throwables);
                }
            }
        }

        return estado;
    }

    public static List<VentasProductos> listaVenta(){
        List<VentasProductos> auxVenta = new ArrayList<>();
        Connection con = null;
        try {
            String sql = "SELECT * FROM VENTA ";
            con = ConexionBD.getInstance().getConexion();
            PreparedStatement prepareStatement = con.prepareStatement(sql);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                VentasProductos aux = new VentasProductos();
                aux.setId( rs.getString("ID"));
                aux.setFechaCompra( rs.getDate("FECHA_COMPRA"));
                aux.setNombreCliente(  rs.getString("NOMBRE_CLIENTE"));
                auxVenta.add(aux);
            }

        } catch (SQLException throwables) {
            Logger.getLogger(ServicioVenta.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                Logger.getLogger(ServicioVenta.class.getName()).log(Level.SEVERE, null, throwables);
            }
        }
        return auxVenta;
    }

    //Por Id de venta de carrito los porducto que se tiene esa venta
    public static List<ProductoCarrito> listaProductoVenta(String ID_VENTA){
        List<ProductoCarrito> auxProductoCarrito = new ArrayList<>();
        Connection con = null;
        try {
            String sql = "SELECT * FROM PRODUCTO_VENTA WHERE ID_VENTA LIKE ? ";
            con = ConexionBD.getInstance().getConexion();
            PreparedStatement prepareStatement = con.prepareStatement(sql);
            prepareStatement.setString(1,ID_VENTA);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                ProductoCarrito aux = new ProductoCarrito();
                aux.setIdProductoCarrito(rs.getString("ID_VENTA"));
                aux.setProducto(ServicioProducto.buscaProudcto(rs.getString("ID_PRODUCTO")));
                aux.setCantProducto(rs.getInt("CANTIDAD"));
                aux.calcularTotal();
                System.out.println("EL TOTAL ES=" +aux.getCant_Total());
                auxProductoCarrito.add(aux);
            }

        } catch (SQLException throwables) {
            Logger.getLogger(ServicioVenta.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                Logger.getLogger(ServicioVenta.class.getName()).log(Level.SEVERE, null, throwables);
            }
        }
        return auxProductoCarrito;
    }

    public static VentasProductos buscarVentaByID(String idVenta){
        VentasProductos ventasProductos = null;
        Connection con = null;

        try {
            String sql = "SELECT * FROM VENTA\n" +
                    "WHERE ID LIKE ? ";
            con = ConexionBD.getInstance().getConexion();
            PreparedStatement prepareStatement = con.prepareStatement(sql);

            prepareStatement.setString(1, idVenta);
            ResultSet rs = prepareStatement.executeQuery();

            //simula un cursor
            while(rs.next()){
                ventasProductos = new VentasProductos();

                ventasProductos.setId(rs.getString("ID"));
                ventasProductos.setNombreCliente( rs.getString("NOMBRE_CLIENTE"));
                ventasProductos.setFechaCompra(rs.getDate("FECHA_COMPRA"));
                ventasProductos.setListaProducto(listaProductoVenta(idVenta));

            }

        } catch (SQLException e) {
            Logger.getLogger(servicioUsuario.class.getName()).log(Level.SEVERE, null, e);
        } finally{
            try {
                con.close(); //cerrando conexion
            } catch (SQLException e) {
                Logger.getLogger(servicioUsuario.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return  ventasProductos;

    }




}
