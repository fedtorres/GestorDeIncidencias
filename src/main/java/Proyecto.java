public class Proyecto {
    private int id;
    private String titulo;
    private Usuario propietario;

    public Proyecto(int proyectoId, String titulo, int propietarioId) {
        id = proyectoId;
        this.titulo = titulo;
        UsuarioService usuarioService = UsuarioServiceMapImpl.getInstancia();
        propietario = usuarioService.getUsuario(propietarioId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }
}
