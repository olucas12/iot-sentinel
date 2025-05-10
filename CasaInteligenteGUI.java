import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CasaInteligenteGUI extends JFrame {

    private List<Ambiente> listaDeAmbientes;
    private DefaultListModel<Ambiente> ambienteListModel;
    private JList<Ambiente> ambienteJList;

    private DefaultListModel<Dispositivo> dispositivoListModel;
    private JList<Dispositivo> dispositivoJList;

    private Ambiente ambienteSelecionado;
    private Dispositivo dispositivoSelecionado;

    private JPanel controlPanel;
    private JLabel statusLabel;

    public CasaInteligenteGUI() {
        this.listaDeAmbientes = new ArrayList<>();

        setTitle("Controle Casa Inteligente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Nimbus L&F not found, using default.");
        }

        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Painel de ambientes
        JPanel environmentManagementPanel = new JPanel(new BorderLayout(5, 5));
        environmentManagementPanel.setBorder(BorderFactory.createTitledBorder("Ambientes"));
        ambienteListModel = new DefaultListModel<>();
        ambienteJList = new JList<>(ambienteListModel);
        ambienteJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ambienteJList.addListSelectionListener(new AmbienteSelectionListener());
        environmentManagementPanel.add(new JScrollPane(ambienteJList), BorderLayout.CENTER);

        JPanel ambButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton addAmbienteButton = new JButton("Adicionar Ambiente");
        addAmbienteButton.addActionListener(new AddAmbienteListener());
        JButton removeAmbienteButton = new JButton("Remover Ambiente");
        removeAmbienteButton.addActionListener(new RemoveAmbienteListener());
        ambButtonsPanel.add(addAmbienteButton);
        ambButtonsPanel.add(removeAmbienteButton);
        environmentManagementPanel.add(ambButtonsPanel, BorderLayout.SOUTH);

        // Painel de dispositivos
        JPanel deviceManagementPanel = new JPanel(new BorderLayout(5, 5));
        deviceManagementPanel.setBorder(BorderFactory.createTitledBorder("Dispositivos no Ambiente"));
        dispositivoListModel = new DefaultListModel<>();
        dispositivoJList = new JList<>(dispositivoListModel);
        dispositivoJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dispositivoJList.addListSelectionListener(new DispositivoSelectionListener());
        deviceManagementPanel.add(new JScrollPane(dispositivoJList), BorderLayout.CENTER);

        JPanel dispButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton addDispositivoButton = new JButton("Adicionar Dispositivo");
        addDispositivoButton.addActionListener(new AddDispositivoListener());
        JButton removeDispositivoButton = new JButton("Remover Dispositivo");
        removeDispositivoButton.addActionListener(new RemoveDispositivoListener());
        dispButtonsPanel.add(addDispositivoButton);
        dispButtonsPanel.add(removeDispositivoButton);
        deviceManagementPanel.add(dispButtonsPanel, BorderLayout.SOUTH);

        // Painel de controle
        controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controles do Dispositivo"));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        mainPanel.add(environmentManagementPanel);
        mainPanel.add(deviceManagementPanel);
        mainPanel.add(new JScrollPane(controlPanel));

        add(mainPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Bem-vindo!");
        statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(statusLabel, BorderLayout.SOUTH);

        refreshAmbienteList();
    }

    private void setStatus(String message) {
        statusLabel.setText(message);
        System.out.println("GUI Status: " + message);
    }

    private void refreshAmbienteList() {
        ambienteListModel.clear();
        for (Ambiente amb : listaDeAmbientes) {
            ambienteListModel.addElement(amb);
        }
        if (ambienteSelecionado == null && !listaDeAmbientes.isEmpty()) {
            ambienteJList.setSelectedIndex(0);
        } else if (ambienteSelecionado != null && listaDeAmbientes.contains(ambienteSelecionado)) {
            ambienteJList.setSelectedValue(ambienteSelecionado, true);
        } else if (listaDeAmbientes.isEmpty()) {
            ambienteSelecionado = null;
        }
        refreshDispositivoList();
    }

    private void refreshDispositivoList() {
        dispositivoListModel.clear();
        if (ambienteSelecionado != null) {
            for (Dispositivo disp : ambienteSelecionado.getDispositivos()) {
                dispositivoListModel.addElement(disp);
            }
            if (dispositivoSelecionado != null && ambienteSelecionado.getDispositivos().contains(dispositivoSelecionado)) {
                dispositivoJList.setSelectedValue(dispositivoSelecionado, true);
            }
        }
        updateControlPanel();
    }

    private void updateControlPanel() {
        controlPanel.removeAll();
        if (dispositivoSelecionado == null) {
            controlPanel.add(new JLabel("Nenhum dispositivo selecionado."));
            controlPanel.revalidate();
            controlPanel.repaint();
            return;
        }

        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel deviceInfoLabel = new JLabel("<html><b>Dispositivo:</b> " + dispositivoSelecionado.getNome() +
                "<br><b>Tipo:</b> " + dispositivoSelecionado.getTipo().getDescricao() +
                "<br><b>Estado:</b> " + dispositivoSelecionado.getEstado().getDescricao() + "</html>");
        deviceInfoLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        controlPanel.add(deviceInfoLabel, gbc);

        JPanel commonButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton ligarButton = new JButton("Ligar");
        ligarButton.addActionListener(e -> {
            dispositivoSelecionado.ligar();
            refreshDispositivoList();
            updateControlPanel();
            setStatus(dispositivoSelecionado.getTipo().getDescricao() + " - " + dispositivoSelecionado.getNome() + " tentou ligar.");
        });
        JButton desligarButton = new JButton("Desligar");
        desligarButton.addActionListener(e -> {
            dispositivoSelecionado.desligar();
            refreshDispositivoList();
            updateControlPanel();
            setStatus(dispositivoSelecionado.getTipo().getDescricao() + " - " + dispositivoSelecionado.getNome() + " tentou desligar.");
        });
        commonButtons.add(ligarButton);
        commonButtons.add(desligarButton);
        controlPanel.add(commonButtons, gbc);

        if (dispositivoSelecionado instanceof Ar) {
            Ar ar = (Ar) dispositivoSelecionado;
            JLabel arStatusLabel = new JLabel("Modo: " + ar.getModo().getDescricao() + ", Temp: " + ar.getTemperatura() + "°C");
            controlPanel.add(arStatusLabel, gbc);

            JPanel arButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton aumentarTempButton = new JButton("Aumentar Temp");
            aumentarTempButton.addActionListener(e -> {
                ar.aumentarTemp();
                updateControlPanel();
                refreshDispositivoList();
                setStatus(ar.getNome() + ": Temperatura aumentada.");
            });
            JButton diminuirTempButton = new JButton("Diminuir Temp");
            diminuirTempButton.addActionListener(e -> {
                ar.diminuirTemp();
                updateControlPanel();
                refreshDispositivoList();
                setStatus(ar.getNome() + ": Temperatura diminuída.");
            });
            JButton alterarModoArButton = new JButton("Alterar Modo");
            alterarModoArButton.addActionListener(e -> {
                ar.alterarModoAr();
                updateControlPanel();
                refreshDispositivoList();
                setStatus(ar.getNome() + ": Modo alterado.");
            });
            arButtons.add(aumentarTempButton);
            arButtons.add(diminuirTempButton);
            arButtons.add(alterarModoArButton);
            controlPanel.add(arButtons, gbc);
        } else if (dispositivoSelecionado instanceof Computador) {
            Computador pc = (Computador) dispositivoSelecionado;
            JLabel pcStatusLabel = new JLabel("SO: " + pc.getSo().getDescricao() + ", Internet: " + (pc.isAcessoInternet() ? "Conectado" : "Desconectado"));
            controlPanel.add(pcStatusLabel, gbc);

            JPanel pcButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton conectarInternetButton = new JButton("Conectar Internet");
            conectarInternetButton.addActionListener(e -> {
                pc.conectarInternet();
                updateControlPanel();
                refreshDispositivoList();
                setStatus(pc.getNome() + ": Tentou conectar à internet.");
            });
            JButton desconectarInternetButton = new JButton("Desconectar Internet");
            desconectarInternetButton.addActionListener(e -> {
                pc.desconectarInternet();
                updateControlPanel();
                refreshDispositivoList();
                setStatus(pc.getNome() + ": Tentou desconectar da internet.");
            });
            pcButtons.add(conectarInternetButton);
            pcButtons.add(desconectarInternetButton);
            controlPanel.add(pcButtons, gbc);
        } else if (dispositivoSelecionado instanceof Ventilador) {
            Ventilador vent = (Ventilador) dispositivoSelecionado;
            JLabel ventStatusLabel = new JLabel("Velocidade: " + vent.getVelocidade());
            controlPanel.add(ventStatusLabel, gbc);

            JPanel ventButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton aumentarVelButton = new JButton("Aumentar Velocidade");
            aumentarVelButton.addActionListener(e -> {
                vent.aumentarVelocidade();
                updateControlPanel();
                refreshDispositivoList();
                setStatus(vent.getNome() + ": Velocidade aumentada.");
            });
            JButton diminuirVelButton = new JButton("Diminuir Velocidade");
            diminuirVelButton.addActionListener(e -> {
                vent.diminuirVelocidade();
                updateControlPanel();
                refreshDispositivoList();
                setStatus(vent.getNome() + ": Velocidade diminuída.");
            });
            ventButtons.add(aumentarVelButton);
            ventButtons.add(diminuirVelButton);
            controlPanel.add(ventButtons, gbc);
        } else if (dispositivoSelecionado instanceof Temperatura) {
            Temperatura sensorTemp = (Temperatura) dispositivoSelecionado;
            JLabel tempLabel = new JLabel(sensorTemp.getEstado() == Estado.LIGADO
                    ? "Temperatura Atual: " + String.format("%.2f", sensorTemp.getTemperaturaAtual()) + "°C"
                    : "Sensor desligado");
            controlPanel.add(tempLabel, gbc);

            JButton lerTempButton = new JButton("Ler Temperatura");
            lerTempButton.addActionListener(e -> {
                sensorTemp.lerTemperatura();
                updateControlPanel();
                refreshDispositivoList();
                setStatus(sensorTemp.getNome() + ": Leitura de temperatura realizada.");
            });
            controlPanel.add(lerTempButton, gbc);
        } else if (dispositivoSelecionado instanceof Movimento) {
            Movimento sensorMov = (Movimento) dispositivoSelecionado;
            JLabel movLabel = new JLabel(sensorMov.getEstado() == Estado.LIGADO
                    ? (sensorMov.isMexendo() ? "Movimento Detectado" : "Sem Movimento")
                    : "Sensor desligado");
            controlPanel.add(movLabel, gbc);

            JButton detectarMovButton = new JButton("Detectar Movimento");
            detectarMovButton.addActionListener(e -> {
                sensorMov.detectarMovimento();
                updateControlPanel();
                refreshDispositivoList();
                setStatus(sensorMov.getNome() + ": Detecção de movimento realizada.");
            });
            controlPanel.add(detectarMovButton, gbc);
        }

        gbc.weighty = 1.0;
        controlPanel.add(new JPanel(), gbc);

        controlPanel.revalidate();
        controlPanel.repaint();
    }

    private class AddAmbienteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nomeAmbiente = JOptionPane.showInputDialog(CasaInteligenteGUI.this,
                    "Digite o nome do novo ambiente:", "Adicionar Ambiente", JOptionPane.PLAIN_MESSAGE);
            if (nomeAmbiente != null && !nomeAmbiente.trim().isEmpty()) {
                Ambiente novoAmbiente = new Ambiente(nomeAmbiente.trim());
                listaDeAmbientes.add(novoAmbiente);
                refreshAmbienteList();
                ambienteJList.setSelectedValue(novoAmbiente, true);
                setStatus("Ambiente '" + novoAmbiente.getNome() + "' adicionado.");
            } else if (nomeAmbiente != null) {
                JOptionPane.showMessageDialog(CasaInteligenteGUI.this,
                        "O nome do ambiente não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class RemoveAmbienteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ambienteSelecionado != null) {
                int response = JOptionPane.showConfirmDialog(CasaInteligenteGUI.this,
                        "Tem certeza que deseja remover o ambiente '" + ambienteSelecionado.getNome() + "' e todos os seus dispositivos?",
                        "Remover Ambiente", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    String nomeRemovido = ambienteSelecionado.getNome();
                    listaDeAmbientes.remove(ambienteSelecionado);
                    ambienteSelecionado = null;
                    refreshAmbienteList();
                    setStatus("Ambiente '" + nomeRemovido + "' removido.");
                }
            } else {
                JOptionPane.showMessageDialog(CasaInteligenteGUI.this,
                        "Nenhum ambiente selecionado para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class AmbienteSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                ambienteSelecionado = ambienteJList.getSelectedValue();
                refreshDispositivoList();
                if (ambienteSelecionado != null) {
                    setStatus("Ambiente '" + ambienteSelecionado.getNome() + "' selecionado.");
                }
            }
        }
    }

    private class AddDispositivoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ambienteSelecionado == null) {
                JOptionPane.showMessageDialog(CasaInteligenteGUI.this,
                        "Selecione um ambiente primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JTextField nomeField = new JTextField(20);
            JComboBox<Tipo> tipoComboBox = new JComboBox<>(Tipo.values());
            JComboBox<SistemaOperacional> soComboBox = new JComboBox<>(SistemaOperacional.values());
            soComboBox.setVisible(false);

            tipoComboBox.addActionListener(_ -> soComboBox.setVisible(tipoComboBox.getSelectedItem() == Tipo.COMPUTADOR));

            JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
            panel.add(new JLabel("Nome do Dispositivo:"));
            panel.add(nomeField);
            panel.add(new JLabel("Tipo de Dispositivo:"));
            panel.add(tipoComboBox);
            panel.add(new JLabel("Sistema Operacional:"));
            panel.add(soComboBox);

            int result = JOptionPane.showConfirmDialog(CasaInteligenteGUI.this, panel,
                    "Adicionar Dispositivo ao " + ambienteSelecionado.getNome(),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String nomeDispositivo = nomeField.getText().trim();
                if (nomeDispositivo.isEmpty()) {
                    JOptionPane.showMessageDialog(CasaInteligenteGUI.this,
                            "O nome do dispositivo não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Tipo tipoSelecionado = (Tipo) tipoComboBox.getSelectedItem();
                Dispositivo novoDispositivo = null;

                switch (tipoSelecionado) {
                    case AR:
                        novoDispositivo = new Ar(nomeDispositivo);
                        break;
                    case COMPUTADOR:
                        SistemaOperacional soSelecionado = (SistemaOperacional) soComboBox.getSelectedItem();
                        novoDispositivo = new Computador(nomeDispositivo, soSelecionado);
                        break;
                    case LAMPADA:
                        novoDispositivo = new Lampada(nomeDispositivo);
                        break;
                    case MOVIMENTO:
                        novoDispositivo = new Movimento(nomeDispositivo);
                        break;
                    case TEMPERATURA:
                        novoDispositivo = new Temperatura(nomeDispositivo);
                        break;
                    case VENTILADOR:
                        novoDispositivo = new Ventilador(nomeDispositivo);
                        break;
                    default:
                        JOptionPane.showMessageDialog(CasaInteligenteGUI.this,
                                "Tipo de dispositivo desconhecido.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (novoDispositivo != null) {
                    ambienteSelecionado.adicionarDispositivo(novoDispositivo);
                    refreshDispositivoList();
                    dispositivoJList.setSelectedValue(novoDispositivo, true);
                    setStatus(novoDispositivo.getTipo().getDescricao() + " '" + novoDispositivo.getNome() +
                            "' adicionado ao " + ambienteSelecionado.getNome() + ".");
                }
            }
        }
    }

    private class RemoveDispositivoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ambienteSelecionado == null || dispositivoSelecionado == null) {
                JOptionPane.showMessageDialog(CasaInteligenteGUI.this,
                        "Selecione um ambiente e um dispositivo para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int response = JOptionPane.showConfirmDialog(CasaInteligenteGUI.this,
                    "Tem certeza que deseja remover o dispositivo '" + dispositivoSelecionado.getNome() +
                            "' do ambiente '" + ambienteSelecionado.getNome() + "'?",
                    "Remover Dispositivo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                String nomeDispRemovido = dispositivoSelecionado.getNome();
                ambienteSelecionado.removerDispositivo(dispositivoSelecionado);
                refreshDispositivoList();
                setStatus("Dispositivo '" + nomeDispRemovido + "' removido.");
            }
        }
    }

    private class DispositivoSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                dispositivoSelecionado = dispositivoJList.getSelectedValue();
                updateControlPanel();
                if (dispositivoSelecionado != null) {
                    setStatus("Dispositivo '" + dispositivoSelecionado.getNome() + "' selecionado.");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CasaInteligenteGUI gui = new CasaInteligenteGUI();
            gui.setVisible(true);
        });
    }
}
