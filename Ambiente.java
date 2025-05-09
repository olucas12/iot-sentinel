import java.util.ArrayList;
import java.util.List;

public class Ambiente {
    private String nome;
    private List<Dispositivo> dispositivos;

    public Ambiente(String nome){
        this.nome = nome;
        this.dispositivos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDispositivos(List<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
    }

    public void adicionarDispositivo(Dispositivo dispositivo){
        dispositivos.add(dispositivo);
        System.out.printf(dispositivo.getTipo().getDescricao() + " - " + dispositivo.getNome() + " adicionado ao ambiente " + nome + ".%n");
    }

    public void removerDispositivo(Dispositivo dispositivo){
        if(dispositivos.remove(dispositivo)){
            System.out.printf(dispositivo.getTipo().getDescricao() + " - " + dispositivo.getNome() + " removido do ambiente " + nome + ".%n");
        } else {
            System.out.printf("Dispositivo não encontrado no ambiente " + nome + ".%n");
        }
    }

    public void listarDispositivos() {
        System.out.println("Ambiente " + nome + " | Quantidade [" + dispositivos.size() + "]");
        if (dispositivos.isEmpty()) {
            System.out.printf("Nenhum dispositivo cadastrado no ambiente " + nome + ".%n");
        } else {
            for (Dispositivo d : dispositivos) {
                System.out.println(d);
            }
        }
    }

    @Override
    public String toString() {
        return "Ambiente: " + nome + " | Dispositivos: " + dispositivos.size();
    }
}
