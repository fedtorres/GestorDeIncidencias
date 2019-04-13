import java.util.Collection;

public interface IncidenteService {

    public void addIncidente(Incidente incidente);
    public Collection<Incidente> getIncidentes();
    public Incidente getIncidente(int id);
    public Incidente editTextoDescripcion(Incidente incidente);
    public Incidente editEstado(int id);
    public Collection<Incidente> getIncidentesPorResponsable(int usuarioId);
    public Collection<Incidente> getIncidentesPorReportador(int usuarioId);
    public Collection<Incidente> getIncidentesPorProyecto(int proyectoId);
    public Collection<Incidente> getIncidentesAbiertos();
    public Collection<Incidente> getIncidentesResueltos();
}
