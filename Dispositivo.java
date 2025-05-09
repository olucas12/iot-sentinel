public class Dispositivo {
    private String nome;
    private Estado estado;
    private Tipo tipo;

    public Dispositivo(String nome, Tipo tipo){
        this.nome = nome;
        this.estado = Estado.DESLIGADO;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public Estado getEstado() {
        return estado;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void ligar(){
        if(getEstado() == Estado.LIGADO){
            System.out.printf(tipo.getDescricao() + " - " + getNome() + " já está ligado.%n");
        } else {
            estado = Estado.LIGADO;
            System.out.printf(tipo.getDescricao() + " - " + getNome() + " foi ligado.%n");
        }
    }

    public void desligar(){
        if(getEstado() == Estado.DESLIGADO){
            System.out.printf(tipo.getDescricao() + " - " + getNome() + " já está desligado.%n");
        } else {
            estado = Estado.DESLIGADO;
            System.out.printf(tipo.getDescricao() + " - " + getNome() + " foi desligado.%n");
        }
    }

    @Override
    public String toString() {
        return getTipo().getDescricao() + " - " + getNome() + ": "
            + getEstado().getDescricao().toLowerCase();
    }
    

    public static void main(String[] args) {
        Ar d1 = new Ar("Elgin QFrio");

        d1.ligar();
        d1.alterarModoAr();
        d1.alterarModoAr();
        d1.desligar();
        
        System.out.println(d1);

    }
}