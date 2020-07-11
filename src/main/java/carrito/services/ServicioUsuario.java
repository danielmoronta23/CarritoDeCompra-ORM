package carrito.services;

import carrito.encapsulacion.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioUsuario extends ManejadorBD<Usuario>{

    public ServicioUsuario() {
        super(Usuario.class);
    }

    /**
     * Antigua implementacion
     * @param usuario
     * @return
    public static boolean crearUsuario(Usuario usuario){
        boolean estado = false;
        Connection con = null;
        try {
            String sql = "INSERT INTO USUARIO(ID, NOMBRE, PASSWORD) VALUES (?,?,?)";
            con = ConexionBD.getInstance().getConexion();

            PreparedStatement prepareStatement = con.prepareStatement(sql);
            prepareStatement.setString(1,usuario.getId());
            prepareStatement.setString(2, usuario.getNombre());
            prepareStatement.setString(3, usuario.getPassword());

            if(prepareStatement.executeUpdate()>0){
                estado = true;
            }
        } catch (SQLException throwables) {
            Logger.getLogger(servicioUsuario.class.getName()).log(Level.SEVERE, null, throwables);
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                Logger.getLogger(servicioUsuario.class.getName()).log(Level.SEVERE, null, throwables);
            }
        }
        return estado;
    }
    public static Usuario autenticarUsuario(String usuario, String password) {
        Usuario auxUsuario = null;
        Connection con = null;

        try {
            String sql = "SELECT * FROM USUARIO\n" +
                    "WHERE NOMBRE LIKE ? AND PASSWORD LIKE ?";
            con = ConexionBD.getInstance().getConexion();
            PreparedStatement prepareStatement = con.prepareStatement(sql);

            prepareStatement.setString(1, usuario);
            prepareStatement.setString(2, password);

            ResultSet rs = prepareStatement.executeQuery();

            //simula un cursor
            while(rs.next()){
                 auxUsuario = new Usuario(
                        rs.getString("NOMBRE"),
                        rs.getString("PASSWORD"));
                 auxUsuario.setId(  rs.getString("ID"));
                 System.out.println("ID->"+rs.getString("ID"));
            }

            if (auxUsuario != null) {
                System.out.println("USUARIO ENCONTRADO\n");

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
        return  auxUsuario;
    }
    public static Usuario buscarUsuario(String id) {
        Usuario auxUsuario = null;
        Connection con = null;

        try {
            String sql = "SELECT * FROM USUARIO\n" +
                    "WHERE NOMBRE LIKE ? ";
            con = ConexionBD.getInstance().getConexion();
            PreparedStatement prepareStatement = con.prepareStatement(sql);

            prepareStatement.setString(1, id);


            ResultSet rs = prepareStatement.executeQuery();

            //simula un cursor
            while(rs.next()){
                auxUsuario = new Usuario(
                        rs.getString("NOMBRE"),
                        rs.getString("PASSWORD"));
                auxUsuario.setId(  rs.getString("ID"));
                System.out.println("ID->"+rs.getString("ID"));
            }

            if (auxUsuario != null) {
                System.out.println("USUARIO ENCONTRADO\n");

            }else{
                System.out.println("USUARIO NO EXISTE\n");
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
        return  auxUsuario;
    }
     */

}
