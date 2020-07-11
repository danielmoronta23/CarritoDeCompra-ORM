package carrito.controladores;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import carrito.util.ControladorBase;

public class ControladorPlantilla extends ControladorBase {
    public ControladorPlantilla(Javalin app) {
        super(app);
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }

    @Override
    public void aplicarRutas() {

    }
}
