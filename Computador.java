public class Computador extends Dispositivo {
    private SistemaOperacional so;
    private boolean acessoInternet;

    public Computador(String nome, SistemaOperacional so){
        super(nome, Tipo.COMPUTADOR);
        this.so = so;
        this.acessoInternet = false;
    }

    public SistemaOperacional getSo() {
        return so;
    }

    public boolean isAcessoInternet() {
        return acessoInternet;
    }

    public void setSo(SistemaOperacional so) {
        this.so = so;
    }

    public void conectarInternet(){
        if(getEstado() == Estado.LIGADO){
            if(isAcessoInternet() == false){
                acessoInternet = true;
                System.out.printf(getTipo().getDescricao() + " - " + getNome() + " foi conectado a internet.%n");
            } else {
                System.out.printf(getTipo().getDescricao() + " - " + getNome() + " já está conectado a internet.%n");
            }
        } else {
            System.out.printf(getTipo().getDescricao() + " - " + getNome() + " não pode se conectar a internet pois está desligado.%n");
        }
    }

    public void desconectarInternet(){
        if(getEstado() == Estado.LIGADO){
            if(isAcessoInternet() == true){
                acessoInternet = false;
                System.out.printf(getTipo().getDescricao() + " - " + getNome() + " foi desconectado da internet.%n");
            } else {
                System.out.printf(getTipo().getDescricao() + " - " + getNome() + " já está desconectado da internet.%n");
            }
        } else {
            System.out.printf(getTipo().getDescricao() + " - " + getNome() + " já foi desconectado da internet pois está desligado.%n");
        }
    }

    @Override
    public void desligar(){
        if(getEstado() == Estado.DESLIGADO){
            System.out.printf(getTipo().getDescricao() + " - " + getNome() + " já está desligado.%n");
        } else {
            this.setEstado(Estado.DESLIGADO);
            acessoInternet = false;
            System.out.printf(getTipo().getDescricao() + " - " + getNome() + " foi desligado.%n");
        }
    }

    @Override
    public String toString() {
        return getTipo().getDescricao() + " - " + getNome() + ": " + (getEstado() == Estado.LIGADO ? "com SO " + getSo().getDescricao().toLowerCase() + (isAcessoInternet() ? " conectado a internet" : " desconectado da internet") : "desligado");
    }
}