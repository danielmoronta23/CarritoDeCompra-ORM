package practica2.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que permite encapsular la informacion de connectividad con la BD
 * La cual implementa un patron Singleton.
 */


public class ConexionBD {
    /**
     * Iniciando en modo Servidor. Quien indica el modo es el URL.
     * http://www.h2database.com/html/cheatSheet.html
     */
    private String URL = "jdbc:h2:tcp://localhost/~/practica3";
    private static ConexionBD conexionBD;

    /**
     * Implementado patron Singleton
     */
    private ConexionBD() {
        registroDriver();
    }

    public static ConexionBD getInstance() {
        if(conexionBD==null){
            conexionBD = new ConexionBD();
        }
        return conexionBD;
    }

    /**
     * Registrar Driver. 1er paso para hacer una conectividad JDBS
     */
    private void registroDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /**
         * No es necesario hacerlo En los Driver modernos porque se registran automatico.
         * pero se hace con fines educativos
         */
    }


    /**
     * Abrir Objeto de conexion. 2do paso.
     * URL = protocolo:subprotocolo://servidor:puerto/subnombre
     */
    public Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, "sa", "");
            //Por defecto se le pasa password vacio, pero claramente se puede cambiar

        } catch (SQLException ex) {
          Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }


    /**
     * Abrir objeto Statement para poder trabjar. 3er paso
     */
    /**
     * Hacer la consulta de los elementos deseado. 4to paso
     */
    /**
     * Cerrar conexion. 5to paso.
     */


}
