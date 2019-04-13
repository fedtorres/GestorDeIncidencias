import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

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
        Incidente incidenteEditar = incidenteMap.get(id);
        incidenteEditar.setEstado(Estado.RESUELTO);
        return incidenteEditar;
    }

    public Collection<Incidente> getIncidentesPorResponsable(int usuarioId) {
        return incidenteMap.values()
                .stream()
                .filter(i -> i.getResponsable().getId() == usuarioId)
                .collect(Collectors.toList());
    }

    public Collection<Incidente> getIncidentesPorReportador(int usuarioId) {
        return incidenteMap.values()
                .stream()
                .filter(i -> i.getReportador().getId() == usuarioId)
                .collect(Collectors.toList());
    }

    public Collection<Incidente> getIncidentesPorProyecto(int proyectoId) {
        return incidenteMap.values()
                .stream()
                .filter(i -> i.getProyecto().getId() == proyectoId)
                .collect(Collectors.toList());
    }

    public Collection<Incidente> getIncidentesAbiertos() {
        return incidenteMap.values()
                .stream()
                .filter(i -> i.getEstado() == Estado.ASIGNADO)
                .collect(Collectors.toList());
    }

    public Collection<Incidente> getIncidentesResueltos() {
        return incidenteMap.values()
                .stream()
                .filter(i -> i.getEstado() == Estado.RESUELTO)
                .collect(Collectors.toList());
    }
}
