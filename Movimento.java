public class Movimento extends Dispositivo {
    private boolean mexendo;

    public Movimento(String nome) {
        super(nome, Tipo.MOVIMENTO);
        this.mexendo = false;
    }

    public boolean isMexendo() {
        return mexendo;
    }

    public void detectarMovimento() {
        if (getEstado() == Estado.LIGADO) {
            mexendo = Math.random() < 0.5;
        } else {
            System.out.printf(getTipo().getDescricao() + " - " + getNome() + " está desligado.%n");
        }
    }

    @Override
    public String toString() {
        return getTipo().getDescricao() + " - " + getNome() + ": " + (getEstado() == Estado.LIGADO ? (isMexendo() ? "detectando movimento" : "sem movimento detectado") : "desligado");
    }

}
