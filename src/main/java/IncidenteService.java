import java.util.Collection;

public interface IncidenteService {

    public void addIncidente(Incidente incidente);
    public Collection<Incidente> getIncidentes();
    public Incidente getIncidente(int id);
    public Incidente editTextoDescripcion(Incidente incidente);
    public Incidente editEstado(int id);
    public Collection<Incidente> getIncidentePorResponsable(int userId);
    public Collection<Incidente> getIncidentePorReportador(int userId);
    public Collection<Incidente> getIncidentePorProyecto(int proyectoId);
    public Collection<Incidente> getIncidentesAbiertos();
    public Collection<Incidente> getIncidentesResueltos();
}
