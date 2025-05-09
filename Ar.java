public class Ar extends Dispositivo {
    private int temperatura;
    private ModoAr modo;

    public Ar(String nome){
        super(nome, Tipo.AR);
        this.temperatura = 25;
        this.modo = ModoAr.REFRIGERACAO;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public ModoAr getModo() {
        return modo;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public void setModo(ModoAr modo) {
        this.modo = modo;
    }

    public void alterarModoAr(){
        if(getEstado() == Estado.LIGADO){
            if(getModo() == ModoAr.REFRIGERACAO){
                modo = ModoAr.VENTILACAO;
            } else {
                modo = ModoAr.REFRIGERACAO;
            }
            System.out.printf(getTipo().getDescricao() + " - " + getNome() + ": modo alterado para " + modo.getDescricao().toLowerCase() + ".%n");
        } else {
            System.out.printf(getTipo().getDescricao() + " - " + getNome() + " está desligado, o modo não pode ser alterado.%n");
        }
    }

    public void aumentarTemp(){
        if(getEstado() == Estado.LIGADO){
            if(temperatura < 28){
                temperatura++;
                System.out.printf(getTipo().getDescricao() + " - " + getNome() + ": temperatura aumentada para " + getTemperatura() + "ºC.%n");
            } else {
                System.out.printf(getTipo().getDescricao() + " - " + getNome() + ": temperatura máxima atingida. [" + getTemperatura() + "ºC]%n");
            }
        } else {
            System.out.printf(getTipo().getDescricao() + " - " + getNome() + " está desligado, a temperatura não pode ser alterada.%n");
        }
    }

    public void diminuirTemp(){
        if(getEstado() == Estado.LIGADO){
            if(temperatura > 16){
                temperatura--;
                System.out.printf(getTipo().getDescricao() + " - " + getNome() + ": temperatura diminuida para " + getTemperatura() + "ºC.%n");
            } else {
                System.out.printf(getTipo().getDescricao() + " - " + getNome() + ": temperatura mínima atingida. [" + getTemperatura() + "ºC]%n");
            }
        } else {
            System.out.printf(getTipo().getDescricao() + " - " + getNome() + " está desligado, a temperatura não pode ser alterada.%n");
        }
    }

    @Override
    public String toString() {
        return getTipo().getDescricao() + " - " + getNome() + ": " + (getEstado() == Estado.LIGADO ? "modo " + getModo().getDescricao().toLowerCase() + " a " + getTemperatura() + "ºC" : "desligado");
    }

}