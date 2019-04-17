import java.util.Collection;

public interface IncidenteService {

    public void addIncidente(Incidente incidente);
    public Incidente patchTextoDescripcion(int id, String texto) throws IncidenteException;
    public Incidente patchEstado(int id) throws IncidenteException;
    public Collection<Incidente> getIncidentesPorResponsable(int usuarioId);
    public Collection<Incidente> getIncidentesPorReportador(int usuarioId);
    public Collection<Incidente> getIncidentesPorProyecto(int proyectoId);
    public Collection<Incidente> getIncidentesAbiertos();
    public Collection<Incidente> getIncidentesResueltos();
}
