public enum SistemaOperacional {
    LINUX("Linux"),
    WINDOWS("Windows"),
    MAC("Mac");

    private String descricao;

    SistemaOperacional(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
