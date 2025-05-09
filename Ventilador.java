public class Ventilador extends Dispositivo {
    private int velocidade;

    public Ventilador(String nome){
        super(nome, Tipo.VENTILADOR);
        this.velocidade = 1;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public void aumentarVelocidade(){
        if(getEstado() == Estado.LIGADO){
            if(velocidade < 3){
                velocidade++;
                System.out.printf(getTipo().getDescricao() + " - " + getNome() + ": velocidade aumentada para " + getVelocidade() + ".%n");
            } else {
                System.out.printf(getTipo().getDescricao() + " - " + getNome() + ": velocidade máxima atingida. [" + getVelocidade() + "]%n");
            }
        } else {
            System.out.printf(getTipo().getDescricao() + " - " + getNome() + " está desligado, a velocidade não pode ser alterada.%n");
        }
    }

    public void diminuirVelocidade(){
        if(getEstado() == Estado.LIGADO){
            if(velocidade > 1){
                velocidade--;
                System.out.printf(getTipo().getDescricao() + " - " + getNome() + ": velocidade diminuida para " + getVelocidade() + ".%n");
            } else {
                System.out.printf(getTipo().getDescricao() + " - " + getNome() + ": velocidade mínima atingida. [" + getVelocidade() + "]%n");
            }
        } else {
            System.out.printf(getTipo().getDescricao() + " - " + getNome() + " está desligado, a velocidade não pode ser alterada.%n");
        }
    }

    @Override
    public String toString() {
        return getTipo().getDescricao() + " - " + getNome() + ": " + (getEstado() == Estado.LIGADO  ? "na velocidade " + getVelocidade() : "desligado");
    }

}