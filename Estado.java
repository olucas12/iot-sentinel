public enum Estado {
    DESLIGADO("Desligado"),
    LIGADO("Ligado");

    private String descricao;

    Estado(String descricao){
        this.descricao = descricao;
    }

     public String getDescricao() {
         return descricao;
     }
}
