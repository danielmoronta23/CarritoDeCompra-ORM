package practica2.services;

import practica2.encapsulacion.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que permite encapsular los metodos de GRUP en Productos
 */

public class ServicioProducto {

    public static boolean crarProducto(Producto producto){
        boolean estado = false;
        Connection con = null;
        try {
            String sql = "INSERT INTO PRODUCTO(ID, NOMBRE, PRECIO) VALUES(?,?,?)";
            con = ConexionBD.getInstance().getConexion();
            PreparedStatement prepareStatement = con.prepareStatement(sql);
            prepareStatement.setString(1,producto.getId());
            prepareStatement.setString(2,producto.getNombre());
            prepareStatement.setBigDecimal(3,producto.getPrecio());

             if(prepareStatement.executeUpdate() > 0){
                estado = true;
            }

        } catch (SQLException throwables) {
            Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, throwables);
            }
        }
        return estado;

    }
    public static boolean actualizarProducto(Producto producto){
        boolean estado = false;
        Connection con = null;
        try {
            String sql = "UPDATE PRODUCTO SET NOMBRE=?, PRECIO = ?"+
                    "WHERE ID =?";
            con = ConexionBD.getInstance().getConexion();
            PreparedStatement prepareStatement = con.prepareStatement(sql);
            prepareStatement.setString(1,producto.getNombre());
            prepareStatement.setBigDecimal(2,producto.getPrecio());
            prepareStatement.setString(3,producto.getId());

            estado = prepareStatement.executeUpdate() > 0;


        } catch (SQLException throwables) {
            Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, throwables);
            }
        }
        return estado;
    }
    public static boolean borrarProducto(String id){
        boolean estado = false;
        Connection con = null;
        try {
            String sql = "DELETE FROM PRODUCTO "+
                    "WHERE ID =?";
            con = ConexionBD.getInstance().getConexion();
            PreparedStatement prepareStatement = con.prepareStatement(sql);
            prepareStatement.setString(1,id);

            estado = prepareStatement.executeUpdate() > 0;


        } catch (SQLException throwables) {
            Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, throwables);
            }
        }
        return estado;
    }

    public static Producto buscaProudcto(String id){
        Producto auxProducto = null;
        Connection con = null;
        try {
            String sql = "SELECT * FROM PRODUCTO "+
                    "WHERE ID =?";
            con = ConexionBD.getInstance().getConexion();
            PreparedStatement prepareStatement = con.prepareStatement(sql);
            prepareStatement.setString(1,id);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                auxProducto = new Producto(
                       rs.getString("ID"),
                       rs.getString("NOMBRE"),
                       rs.getBigDecimal("PRECIO")
               );
            }

        } catch (SQLException throwables) {
            Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, throwables);
            }
        }
        return auxProducto;
    }

    public static List<Producto> listaProducto(){
        List<Producto> auxProducto = new ArrayList<>();
        Connection con = null;
        try {
            String sql = "SELECT * FROM PRODUCTO ";
            con = ConexionBD.getInstance().getConexion();
            PreparedStatement prepareStatement = con.prepareStatement(sql);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getString("ID"),
                        rs.getString("NOMBRE"),
                        rs.getBigDecimal("PRECIO")
                );
                auxProducto.add(producto);
            }

        } catch (SQLException throwables) {
            Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, throwables);
            }
        }
        return auxProducto;
    }

}
