package carrito;

import carrito.services.ConexionBD;
import io.javalin.Javalin;
import carrito.controladores.ControladorCarrito;
import carrito.controladores.ControladorPlantilla;
import carrito.controladores.ZonaAdmin;
import carrito.encapsulacion.Controladora;
import carrito.services.CrearTablas;

import java.sql.SQLException;

public class Main {
    private static String modoConexion = "";
    public static void main(String[] args) throws SQLException {
        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //desde la carpeta de resources
            config.enableCorsForAllOrigins();
        }).start(getHerokuAssignedPort());
        new ControladorPlantilla(app);
        new ControladorCarrito(app).aplicarRutas();
        new ZonaAdmin(app).aplicarRutas();
       //  Controladora.getInstance();
        /**
         * Iniciando en modo servidor la BD
         */
        if(modoConexion.isEmpty()) {
            ConexionBD.getInstance().InciarBD();
            Controladora.getInstance().crearDatosPorDefecto();
        }

    }
    public static String getModoConexion() {
        return modoConexion;
    }

    public static void setModoConexion(String modoConexion) {
        Main.modoConexion = modoConexion;
    }
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }

}
