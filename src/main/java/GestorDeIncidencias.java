import com.google.gson.*;

import java.lang.reflect.Type;

import static spark.Spark.*;

public class GestorDeIncidencias {

    public static void main(String[] args) {

        final UsuarioService usuarioService = UsuarioServiceMapImpl.getInstancia();
        final ProyectoService proyectoService = ProyectoServiceMapImpl.getInstancia();
        final IncidenteService incidenteService = new IncidenteServiceMapImpl();

        precargarDatos(usuarioService, proyectoService, incidenteService);

        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<Proyecto> proyectoDeserializer = new JsonDeserializer<Proyecto>() {
            @Override
            public Proyecto deserialize(
                    JsonElement jsonElement,
                    Type type,
                    JsonDeserializationContext jsonDeserializationContext)
                    throws JsonParseException {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                return new Proyecto(
                        jsonObject.get("proyectoId").getAsInt(),
                        jsonObject.get("titulo").getAsString(),
                        jsonObject.get("propietarioId").getAsInt()
                );
            }
        };
        JsonDeserializer<Incidente> incidenteDeserializer = new JsonDeserializer<Incidente>() {
            @Override
            public Incidente deserialize(
                    JsonElement jsonElement,
                    Type type,
                    JsonDeserializationContext jsonDeserializationContext)
                    throws JsonParseException {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                return new Incidente(
                        jsonObject.get("incidenteId").getAsInt(),
                        Clasificacion.valueOf(jsonObject.get("clasificacion").getAsString()),
                        jsonObject.get("descripcion").getAsString(),
                        jsonObject.get("reportadorId").getAsInt(),
                        jsonObject.get("responsableId").getAsInt(),
                        Estado.valueOf(jsonObject.get("estado").getAsString()),
                        jsonObject.get("proyectoId").getAsInt()
                );
            }
        };
        gsonBuilder.registerTypeAdapter(Proyecto.class, proyectoDeserializer);
        gsonBuilder.registerTypeAdapter(Incidente.class, incidenteDeserializer);
        Gson customGson = gsonBuilder.create();

        post("/usuario", (request, response) -> {
            response.type("application/json");
            Usuario usuario = customGson.fromJson(request.body(), Usuario.class);
            usuarioService.addUsuario(usuario);
            return customGson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS));
        });

        get("/usuario", (request, response) -> {
            response.type("application/json");
            return customGson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    customGson.toJsonTree(
                            usuarioService.getUsuarios()
                    )));
        });

        get("/usuario/:id", (request, response) -> {
            response.type("application/json");
            return customGson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    customGson.toJsonTree(
                            usuarioService.getUsuario(
                                    Integer.parseInt(request.params(":id")))
                    )));
        });

        get("/usuario/:id/proyectos", (request, response) -> {
            response.type("application/json");
            return customGson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    customGson.toJsonTree(
                            proyectoService.getProyectosPorUsuario(
                                    Integer.parseInt(request.params(":id")))
                    )));
        });

        get("/usuario/:id/incidentes/asignados", (request, response) -> {
            response.type("application/json");
            return customGson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    customGson.toJsonTree(
                            incidenteService.getIncidentesPorResponsable(
                                    Integer.parseInt(request.params(":id")))
                    )));
        });

        get("/usuario/:id/incidentes/creados", (request, response) -> {
            response.type("application/json");
            return customGson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    customGson.toJsonTree(
                            incidenteService.getIncidentesPorReportador(
                                    Integer.parseInt(request.params(":id")))
                    )));
        });

        put("/usuario", (request, response) -> {
            response.type("application/json");
            Usuario usuario = customGson.fromJson(request.body(), Usuario.class);
            Usuario usuarioEditado = null;
            try {
                usuarioEditado = usuarioService.editUsuario(usuario);
            } catch (Exception exception) {
                usuarioEditado = null;
            }
            if(usuario != null) {
                return customGson.toJson(new StandardResponse(
                        StatusResponse.SUCCESS,
                        customGson.toJsonTree(
                                usuarioEditado
                        )));
            } else {
                return customGson.toJson(new StandardResponse(
                        StatusResponse.ERROR,
                        "Error al editar el usuario."
                        ));
            }
        });

        delete("usuario/:id", (request, response) -> {
            response.type("application/json");
            int usuarioId = Integer.parseInt(request.params(":id"));
            if(proyectoService.getProyectosPorUsuario(usuarioId).isEmpty()
                    && incidenteService.getIncidentesPorReportador(usuarioId).isEmpty()
                    && incidenteService.getIncidentesPorResponsable(usuarioId).isEmpty()) {
                usuarioService.deleteUsuario(usuarioId);
                return customGson.toJson(new StandardResponse(
                        StatusResponse.SUCCESS,
                        "Usuario borrado."));
            } else {
                return customGson.toJson(new StandardResponse(
                        StatusResponse.ERROR,
                        "Error al borrar el usuario."));
            }
        });

        post("/proyecto", (request, response) -> {
            response.type("application/json");
            Proyecto proyecto = customGson.fromJson(request.body(), Proyecto.class);
            proyectoService.addProyecto(proyecto);
            return customGson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS));
        });

        get("/proyecto", (request, response) -> {
            response.type("application/json");
            return customGson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    customGson.toJsonTree(
                            proyectoService.getProyectos()
                    )));
        });

        get("/proyecto/:id", (request, response) -> {
            response.type("application/json");
            return customGson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    customGson.toJsonTree(
                            proyectoService.getProyecto(
                                    Integer.parseInt(request.params(":id")))
                    )));
        });

        get("/proyecto/:id/incidentes", (request, response) -> {
           response.type("application/json");
           return customGson.toJson(new StandardResponse(
                   StatusResponse.SUCCESS,
                   customGson.toJsonTree(
                           incidenteService.getIncidentesPorProyecto(
                                   Integer.parseInt(request.params(":id")))
                   )));
        });

        put("/proyecto", (request, response) -> {
            response.type("application/json");
            Proyecto proyecto = customGson.fromJson(request.body(), Proyecto.class);
            Proyecto proyectoEditado = null;
            try {
                proyectoEditado = proyectoService.editProyecto(proyecto);
            } catch (ProyectoException exception) {
                proyectoEditado = null;
            }
            if(proyectoEditado != null) {
                return customGson.toJson(new StandardResponse(
                        StatusResponse.SUCCESS,
                        customGson.toJsonTree(
                                proyectoEditado
                        )));
            } else {
                return customGson.toJson(new StandardResponse(
                        StatusResponse.ERROR,
                        "Error al editar el proyecto."
                        ));
            }
        });

        delete("/proyecto/:id", (request, response) -> {
            response.type("application/json");
            int proyectoId = Integer.parseInt(request.params(":id"));
            if(incidenteService.getIncidentesPorProyecto(proyectoId).isEmpty()) {
                proyectoService.deleteProyecto(proyectoId);
                return customGson.toJson(new StandardResponse(
                        StatusResponse.SUCCESS,
                        "Proyecto borrado."
                ));
            } else {
                return customGson.toJson(new StandardResponse(
                        StatusResponse.ERROR,
                        "Error al borrar el proyecto."
                ));
            }
        });

        post("/incidente", (request, response) -> {
            response.type("application/json");
            Incidente incidente = customGson.fromJson(request.body(), Incidente.class);
            incidenteService.addIncidente(incidente);
            return customGson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS));
        });

        get("/incidente/abiertos", (request, response) -> {
            response.type("application/json");
            return customGson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    customGson.toJsonTree(
                            incidenteService.getIncidentesAbiertos()
                    )));
        });

        get("/incidente/resueltos", (request, response) -> {
            response.type("application/json");
            return customGson.toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    customGson.toJsonTree(
                            incidenteService.getIncidentesResueltos()
                    )));
        });

        patch("/incidente/:id/descripcion", (request, response) -> {
           response.type("application/json");
           JsonParser jsonParser = new JsonParser();
           JsonObject jsonObject = jsonParser.parse(request.body()).getAsJsonObject();
           String texto = jsonObject.get("texto").getAsString();
           Incidente incidentePatch = null;
           try {
               incidentePatch = incidenteService.patchTextoDescripcion(
                       Integer.parseInt(request.params(":id")),
                       texto);
           } catch (IncidenteException exception) {
               incidentePatch = null;
           }
           if(incidentePatch != null) {
               return customGson.toJson(new StandardResponse(
                       StatusResponse.SUCCESS,
                       customGson.toJsonTree(
                               incidentePatch
                       )));
           } else {
               return customGson.toJson(new StandardResponse(
                       StatusResponse.ERROR,
                       "Error al añadir texto a la descripción del incidente."
               ));
           }
        });

        patch("/incidente/:id/resuelto", (request, response) -> {
            response.type("application/json");
            Incidente incidentePatch = null;
            try {
                incidentePatch = incidenteService.patchEstado(Integer.parseInt(request.params(":id")));
            } catch (IncidenteException exception) {
                incidentePatch = null;
            }
            if(incidentePatch != null) {
                return customGson.toJson(new StandardResponse(
                        StatusResponse.SUCCESS,
                        customGson.toJsonTree(
                                incidentePatch
                        )));
            } else {
                return customGson.toJson(new StandardResponse(
                        StatusResponse.ERROR,
                        "Error al cambiar estado del incidente."
                ));
            }
        });
    }

    private static void precargarDatos(
            UsuarioService usuarioService,
            ProyectoService proyectoService,
            IncidenteService incidenteService) {
        usuarioService.addUsuario(new Usuario(1, "Tony", "Stark"));
        usuarioService.addUsuario(new Usuario(2, "Steve", "Rogers"));
        usuarioService.addUsuario(new Usuario(3, "Bruce", "Banner"));
        proyectoService.addProyecto(new Proyecto(1, "Proyecto 1", 1));
        proyectoService.addProyecto(new Proyecto(2, "Proyecto 2", 2));
        incidenteService.addIncidente(new Incidente(
                1,
                Clasificacion.CRITICO,
                "Incidente crítico",
                3,
                1,
                Estado.ASIGNADO,
                1
                ));
        incidenteService.addIncidente(new Incidente(
                2,
                Clasificacion.NORMAL,
                "Incidente normal",
                3,
                2,
                Estado.RESUELTO,
                2
        ));
    }
}