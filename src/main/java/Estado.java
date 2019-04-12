public enum Estado {

    ASIGNADO ("Asignado"),
    RESUELTO ("Resuelto");

    private String est;

    Estado(String est) {
        this.est = est;
    }

    public String getEst() {
        return est;
    }

    public void setEst(String est) {
        this.est = est;
    }
}
