package practica2;

import io.javalin.Javalin;
import practica2.controladores.ControladorCarrito;
import practica2.controladores.ControladorPlantilla;
import practica2.controladores.ZonaAdmin;
import practica2.encapsulacion.Controladora;
import practica2.services.CrearTablas;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //desde la carpeta de resources
        }).start(7000);
        new ControladorPlantilla(app);
        new ControladorCarrito(app).aplicarRutas();
        new ZonaAdmin(app).aplicarRutas();
       //  Controladora.getInstance();
        /**
         * Iniciando en modo servidor la BD
         */
        try {
            CrearTablas crearTablas = new CrearTablas();
            crearTablas.InciarBD();
            crearTablas.crearTablas();
            Controladora.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


}
