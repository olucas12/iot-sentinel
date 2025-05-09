public enum ModoAr {
    REFRIGERACAO("Refrigeração"),
    VENTILACAO("Ventilação");

    private String descricao;

    ModoAr(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
