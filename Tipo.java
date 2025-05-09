public enum Tipo {
    AR("Ar Condicionado"),
    COMPUTADOR("Computador"),
    LAMPADA("Lâmpada"),
    MOVIMENTO("Sensor de Movimento"),
    TEMPERATURA("Sensor de Temperatura"),
    VENTILADOR("Ventilador");

    private String descricao;

    Tipo(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
