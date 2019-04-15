import java.util.Collection;
import java.util.HashMap;

public class UsuarioServiceMapImpl implements UsuarioService {

    private static UsuarioServiceMapImpl primeraInstancia = null;
    private HashMap<Integer, Usuario> usuarioMap;

    private UsuarioServiceMapImpl() {
        usuarioMap = new HashMap<Integer, Usuario>();
    }

    public static UsuarioServiceMapImpl getInstancia() {
        if(primeraInstancia == null) {
            primeraInstancia = new UsuarioServiceMapImpl();
        }
        return primeraInstancia;
    }

    public void addUsuario(Usuario usuario) {
        usuarioMap.put(usuario.getId(), usuario);
    }

    public Collection<Usuario> getUsuarios() {
        return usuarioMap.values();
    }

    public Usuario getUsuario(int id) {
        return usuarioMap.get(id);
    }

    public Usuario editUsuario(Usuario usuario) throws UsuarioException {
        try {
            if(usuario.getId() == 0) {
                throw new UsuarioException("El id no puede ser nulo.");
            }
            Usuario usuarioEditar = usuarioMap.get(usuario.getId());
            if (usuario.getNombre() != null) {
                usuarioEditar.setNombre(usuario.getNombre());
            }
            if (usuario.getApellido() != null) {
                usuarioEditar.setApellido(usuario.getApellido());
            }
            return usuarioEditar;
        } catch (Exception exception) {
            throw new UsuarioException(exception.getMessage());
        }
    }

    public void deleteUsuario(int id) {
        usuarioMap.remove(id);
    }
}
