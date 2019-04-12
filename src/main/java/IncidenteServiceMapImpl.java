import java.util.Collection;
import java.util.HashMap;

public class IncidenteServiceMapImpl implements IncidenteService {

    private HashMap<Integer, Incidente> incidenteMap;

    public IncidenteServiceMapImpl() {
        incidenteMap = new HashMap<Integer, Incidente>();
    }

    public void addIncidente(Incidente incidente) {
        incidenteMap.put(incidente.getId(), incidente);
    }

    public Collection<Incidente> getIncidentes() {
        return incidenteMap.values();
    }

    public Incidente getIncidente(int id) {
        return incidenteMap.get(id);
    }

    public Incidente editTextoDescripcion(Incidente incidente) {
        Incidente incidenteEditar = incidenteMap.get(incidente.getId());
        if(incidente.getDescripcion() != null) {
            incidenteEditar.setDescripcion(incidente.getDescripcion());
        }
        return incidenteEditar;
    }

    public Incidente editEstado(int id) {
        Incidente
    }

    public Collection<Incidente> getIncidentePorResponsable(int userId) {
        return null;
    }

    public Collection<Incidente> getIncidentePorReportador(int userId) {
        return null;
    }

    public Collection<Incidente> getIncidentePorProyecto(int proyectoId) {
        return null;
    }

    public Collection<Incidente> getIncidentesAbiertos() {
        return null;
    }

    public Collection<Incidente> getIncidentesResueltos() {
        return null;
    }
}
