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

    public Usuario editUsuario(Usuario usuario) {
        Usuario usuarioEditar = usuarioMap.get(usuario.getId());
        if(usuario.getNombre() != null) {
            usuarioEditar.setNombre(usuario.getNombre());
        }
        if(usuario.getApellido() != null) {
            usuarioEditar.setApellido(usuario.getApellido());
        }
        return usuarioEditar;
    }

    public void deleteUsuario(int id) {
        usuarioMap.remove(id);
    }
}
