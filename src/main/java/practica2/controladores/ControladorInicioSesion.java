package practica2.controladores;

import io.javalin.Javalin;
import org.attoparser.ParsingXmlDeclarationMarkupUtil;
import practica2.encapsulacion.Controladora;
import practica2.encapsulacion.Producto;
import practica2.encapsulacion.Usuario;
import practica2.util.ControladorBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControladorInicioSesion extends ControladorBase {
    List<Usuario> users = Controladora.getControladora().getMisUsuarios();

    public ControladorInicioSesion(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        app.routes(() -> {

            get("/login", ctx -> {



            });


        });
    }
}