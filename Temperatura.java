public class Temperatura extends Dispositivo {
    private double temperaturaAtual;

    public Temperatura(String nome) {
        super(nome, Tipo.TEMPERATURA);
        this.temperaturaAtual = 0.0;
    }

    public double getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public void lerTemperatura() {
        if (getEstado() == Estado.LIGADO) {
            temperaturaAtual = 15 + Math.random() * 15; 
        } else {
            System.out.printf(getTipo().getDescricao() + " - " + getNome() + " está desligado.%n");
        }
    }

    @Override
    public String toString() {
        return getTipo().getDescricao() + " - " + getNome() + ": " + (getEstado() == Estado.LIGADO ? temperaturaAtual + "ºC" : "desligado");
    }

    public static void main(String[] args) {
        Temperatura t = new Temperatura("Termômetro Principal");

        t.ligar();

        for (int i = 0; i < 5; i++) {
            t.lerTemperatura();
            System.out.println(t);
        }

        t.desligar();
        t.lerTemperatura();

        System.out.println(t);
    }
}
