import com.google.gson.Gson;

import static spark.Spark.*;

public class GestorDeIncidencias {

    public static void main(String[] args) {

        final UsuarioService usuarioService = new UsuarioServiceMapImpl();
        final ProyectoService proyectoService = new ProyectoServiceMapImpl();
        final IncidenteService incidenteService = new IncidenteServiceMapImpl();

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
    }
}
