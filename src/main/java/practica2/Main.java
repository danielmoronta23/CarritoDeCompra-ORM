package practica2;

import io.javalin.Javalin;
import practica2.controladores.ControladorCarrito;
import practica2.controladores.ControladorPlantilla;
import practica2.controladores.ZonaAdmin;

public class Main {
    public static void main(String[] args) {
        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //desde la carpeta de resources
        }).start(7000);
        new ControladorPlantilla(app);
        new ControladorCarrito(app).aplicarRutas();
        new ZonaAdmin(app).aplicarRutas();

    }
    /**
     * Metodo para indicar el puerto en Heroku
     * @return
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }
}
