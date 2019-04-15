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

    public Incidente patchTextoDescripcion(int id, String texto) throws IncidenteException {
        try {
            if (id == 0) {
                throw new IncidenteException("El id no puede ser 0.");
            }
            Incidente incidentePatch = incidenteMap.get(id);
            if (texto != null) {
                incidentePatch.setDescripcion(incidentePatch.getDescripcion() + " " + texto);
            }
            return incidentePatch;
        } catch (Exception exception) {
            throw new IncidenteException(exception.getMessage());
        }
    }

    public Incidente patchEstado(int id) throws IncidenteException {
        try {
            if(id == 0) {
                throw new IncidenteException("El id no puede ser 0.");
            }
            Incidente incidentePatch = incidenteMap.get(id);
            incidentePatch.setEstado(Estado.RESUELTO);
            return incidentePatch;
        } catch (Exception exception) {
            throw new IncidenteException(exception.getMessage());
        }
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

    public Collection<Incidente> getIncidentesAbiertosPorProyecto(int proyectoId) {
        return incidenteMap.values()
                .stream()
                .filter(i -> i.getProyecto().getId() == proyectoId)
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
