import java.util.Collection;
import java.util.HashMap;

public class ProyectoServiceMapImpl implements ProyectoService {

    private HashMap<Integer, Proyecto> proyectoMap;

    public ProyectoServiceMapImpl() {
        proyectoMap = new HashMap<Integer, Proyecto>();
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

    public Proyecto editProyecto(Proyecto proyecto) {
        Proyecto proyectoEditar = proyectoMap.get(proyecto.getId());
        if(proyecto.getTitulo() != null) {
            proyectoEditar.setTitulo(proyecto.getTitulo());
        }
        if(proyecto.getPropietario() != null) {
            proyectoEditar.setPropietario(proyecto.getPropietario());
        }
        return proyectoEditar;
    }

    public void deleteProyecto(int id) {
        proyectoMap.remove(id);
    }

    public Collection<Proyecto> getProyectoPorUsuario(int usuarioId) {
        // TODO
        return null;
    }
}
