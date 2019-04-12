public enum Clasificacion {

    CRITICO ("Crítico"),
    NORMAL ("Normal"),
    MENOR ("Menor");

    private String nivel;

    Clasificacion(String nivel) {
        this.nivel = nivel;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
