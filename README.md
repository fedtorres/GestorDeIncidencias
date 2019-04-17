# Gestor de Incidencias

## Métodos de Usuarios

* **POST /usuario**: crear un usuario. El request body debe estar en formato JSON, con los siguientes atributos:

    ```
    {
        "id": <Number>,
        "nombre": <String>,
        "apellido": <String>
    }
    ```

* **GET /usuario**: mostrar todos los usuarios creados.
* **GET /usuario/:id**: mostrar un usuario por id.
* **GET /usuario/:id/proyectos**: mostrar todos los proyectos de los que un usuario es propietario.
* **GET /usuario/:id/incidentes/asignados**: mostrar todos los incidentes asignados a un usuario.
* **GET /usuario/:id/incidentes/creados**: mostrar todos los incidentes creados por un usuario.
* **PUT /usuario**: modificar un usuario existente. El formato y atributos del request body son los mismos que los del 
método *POST /usuario*.
* **DELETE /usuario**: eliminar un usuario que no sea propietario de un proyecto, responsable o reportador.

## Métodos de Proyectos

* **POST /proyecto**: crear un proyecto. El request body debe estar en formato JSON, con los siguientes atributos:
    
    ```
    {
        "proyectoId": <Number>,
        "titulo": <String>,
        "propietarioId": <Number>
    }
    ```

* **GET /proyecto**: mostrar todos los proyectos.
* **GET /proyecto/:id**: mostrar un proyecto por id.
* **GET /proyecto/:id/incidentes**: mostrar todos los incidentes asociados a un proyecto.
* **PUT /proyecto**: modificar un proyecto existente. El formato y atributos del request body son los mismos que los
del método *POST /proyecto*.
* **DELETE /proyecto/:id**: eliminar un proyecto si no tiene incidentes reportados.

## Métodos de Incidentes

* **POST /incidente**: crear un incidente. El request body debe estar en formato JSON, con los siguientes atributos:

    ```
    {
        "incidenteId": <Number>,
        "clasificacion": <String>,
        "descripcion": <String>,
        "reportadorId": <Number>,
        "responsableId": <Number>,
        "estado": <String>,
        "proyectoId": <Number>
    }
    ```

* **GET /incidente/abiertos**: mostrar todos los incidentes abiertos.
* **GET /incidente/resueltos**: mostrar todos los incidentes resueltos.
* **PATCH /incidente/:id/descripcion**: añadir texto a la descripción de un incidente. El request body debe estar en
formato JSON, con el siguiente atributo:

    ```
    {
        "texto": <String>
    }
    ```

* **PATCH /incidente/:id/resuelto**: cambiar el estado de un incidente a RESUELTO.

