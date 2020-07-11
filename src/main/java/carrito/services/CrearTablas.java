package carrito.services;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearTablas {
    private static Server tcp;

    public static void InciarBD() throws SQLException {
        //subiendo en modo servidor H2
        tcp = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();//sube eL modo servidor H2
    }
    public static void detenerBD() throws SQLException {
       // Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
        tcp.stop();
    }

    /**
     * Creando tablas necesarias
     */
    public static void crearTablas() throws SQLException{

        //Creando tabla producto
        String sqlProducto = "CREATE TABLE IF NOT EXISTS PRODUCTO\n" +
                "(\n" +
                "  ID VARCHAR(100) PRIMARY KEY NOT NULL,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  PRECIO DECIMAL NOT NULL,\n" +
                ");";

        //Creando Tabla Usuario
        String sqlUsuaro= "CREATE TABLE IF NOT EXISTS USUARIO\n" +
                "(\n" +
                "  ID VARCHAR(100) PRIMARY KEY NOT NULL,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  PASSWORD VARCHAR(100) NOT NULL,\n" +
                ");";

        //Creando tabla Venta
        String sqlVentas= "CREATE TABLE IF NOT EXISTS VENTA\n" +
                "(\n" +
                "  ID VARCHAR(100)PRIMARY KEY NOT NULL,\n" +
                "  FECHA_COMPRA DATE NOT NULL,\n" +
                "  NOMBRE_CLIENTE VARCHAR(100) NOT NULL\n" +
                ");";


        String sqlVentasProductos= "CREATE TABLE IF NOT EXISTS PRODUCTO_VENTA\n" +
                "(\n" +
                "  ID_ITEM VARCHAR(100),\n" +
                "  ID_VENTA VARCHAR(100),\n" +
                "  ID_PRODUCTO VARCHAR(100),\n" +
                "  CANTIDAD INT NOT NULL,\n" +
                "  PRIMARY KEY (ID_ITEM,ID_VENTA, ID_PRODUCTO),\n" +
                "  FOREIGN KEY (ID_VENTA) REFERENCES VENTA(ID),\n" +
                "  FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTO(ID),\n" +

                ");";

        /**
         * Creando el objecto de connection. 2do paso para conectarse la BD
         */
        Connection con = ConexionBD.getInstance().getConexion();

        /** paso 3 (para conectarse a una BD(
         * Crea el enlace con la base de datos, con el fin de ejecutrar comandos, llamado a createStatement()
         */
        Statement statement = con.createStatement(); //creacion de objeto de declaracion

        /**
         * Paso 4. Realizando consultas
         */
        statement.execute(sqlProducto);
        statement.execute(sqlUsuaro);
        statement.execute(sqlVentas);
        statement.execute(sqlVentasProductos);


        /**
         * Paso 5. Cerrando conexion.
         */
        statement.close(); //cerrando la conexion
        con.close(); //cerrando la conexion
    }
}
