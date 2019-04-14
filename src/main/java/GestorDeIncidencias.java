import com.google.gson.Gson;
import com.sun.xml.internal.bind.api.impl.NameConverter;

import static spark.Spark.*;

public class GestorDeIncidencias {

    public static void main(String[] args) {

        final UsuarioService usuarioService = new UsuarioServiceMapImpl();
        final ProyectoService proyectoService = new ProyectoServiceMapImpl();
        final IncidenteService incidenteService = new IncidenteServiceMapImpl();

        precargarDatos(usuarioService, proyectoService, incidenteService);

        post("/usuario", (request, response) -> {
            response.type("application/json");
            Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
            usuarioService.addUsuario(usuario);
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS));
        });

        get("/usuario", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(
                            usuarioService.getUsuarios()
                    )));
        });

        get("/usuario/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(
                            usuarioService.getUsuario(
                                    Integer.parseInt(request.params(":id")))
                    )));
        });

        get("/usuario/:id/proyectos", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(
                            proyectoService.getProyectosPorUsuario(
                                    Integer.parseInt(request.params(":id")))
                    )));
        });

        put("/usuario", (request, response) -> {
            response.type("application/json");
            Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
            Usuario usuarioEditado = usuarioService.editUsuario(usuario);
            if(usuario != null) {
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.SUCCESS,
                        new Gson().toJsonTree(
                                usuarioEditado
                        )));
            } else {
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.ERROR,
                        "Error al editar el usuario."
                        ));
            }
        });

        delete("usuario/:id", (request, response) -> {
            response.type("application/json");
            usuarioService.deleteUsuario(Integer.parseInt(request.params(":id")));
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    "Integrante borrado."));
        });

        post("/proyecto", (request, response) -> {
            response.type("application/json");
            Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
            proyectoService.addProyecto(proyecto);
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS));
        });

        get("/proyecto", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(
                            proyectoService.getProyectos()
                    )));
        });

        get("/proyecto/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(
                            proyectoService.getProyecto(
                                    Integer.parseInt(request.params(":id")))
                    )));
        });

        put("/proyecto", (request, response) -> {
            response.type("application/json");
            Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
            Proyecto proyectoEditado = proyectoService.editProyecto(proyecto);
            if(proyectoEditado != null) {
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.SUCCESS,
                        new Gson().toJsonTree(
                                proyectoEditado
                        )));
            } else {
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.ERROR,
                        "Error al editar el proyecto."
                        ));
            }
        });

        delete("/proyecto/:id", (request, response) -> {
            response.type("application/json");
            proyectoService.deleteProyecto(Integer.parseInt(request.params(":id")));
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    "Integrante borrado"
            ));
        });
    }

    private static void precargarDatos(
            UsuarioService usuarioService,
            ProyectoService proyectoService,
            IncidenteService incidenteService) {
        usuarioService.addUsuario(new Usuario(1, "Tony", "Stark"));
        usuarioService.addUsuario(new Usuario(2, "Steve", "Rogers"));
        usuarioService.addUsuario(new Usuario(3, "Bruce", "Banner"));
        proyectoService.addProyecto(new Proyecto(1, "Team Iron Man", usuarioService.getUsuario(1)));
        proyectoService.addProyecto(new Proyecto(2, "Team Cap", usuarioService.getUsuario(2)));
    }
}
