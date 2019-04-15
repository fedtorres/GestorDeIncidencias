import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ProyectoServiceMapImpl implements ProyectoService {

    private static ProyectoServiceMapImpl primeraInstancia = null;
    private HashMap<Integer, Proyecto> proyectoMap;

    private ProyectoServiceMapImpl() {
        proyectoMap = new HashMap<Integer, Proyecto>();
    }

    public static ProyectoServiceMapImpl getInstancia() {
        if(primeraInstancia == null) {
            primeraInstancia = new ProyectoServiceMapImpl();
        }
        return primeraInstancia;
    }

    public void addProyecto(Proyecto proyecto) {
        proyectoMap.put(proyecto.getId(), proyecto);
    }

    public Collection<Proyecto> getProyectos() {
        return proyectoMap.values();
    }

    public Proyecto getProyecto(int id) {
        return proyectoMap.get(id);
    }

    public Proyecto editProyecto(Proyecto proyecto) throws ProyectoException {
        try {
            if(proyecto.getId() == 0) {
                throw new ProyectoException("El id no puede ser nulo.");
            }
            Proyecto proyectoEditar = proyectoMap.get(proyecto.getId());
            if (proyecto.getTitulo() != null) {
                proyectoEditar.setTitulo(proyecto.getTitulo());
            }
            if (proyecto.getPropietario() != null) {
                proyectoEditar.setPropietario(proyecto.getPropietario());
            }
            return proyectoEditar;
        } catch (Exception exception) {
            throw new ProyectoException(exception.getMessage());
        }
    }

    public void deleteProyecto(int id) {
        proyectoMap.remove(id);
    }

    public Collection<Proyecto> getProyectosPorUsuario(int usuarioId) {
        return proyectoMap.values()
                .stream()
                .filter(p -> p.getPropietario().getId() == usuarioId)
                .collect(Collectors.toList());
    }
}
