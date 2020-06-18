package practica2.controladores;

import io.javalin.Javalin;
import practica2.util.ControladorBase;

import javax.xml.xpath.XPath;

import java.net.StandardSocketOptions;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class ControladorPlantilla extends ControladorBase {

    public ControladorPlantilla(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        System.out.println("Entrado por ruta de controlador Plantilla");
        app.routes(() ->{
            path("/", () -> {
                get("/", ctx -> {

                  ctx.redirect("/publico/GRUD.html");
                });
            });

        });

    }
}
