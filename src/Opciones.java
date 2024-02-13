import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class Opciones extends JFrame {
    private JRadioButton verSaldoRadioButton;
    private JRadioButton retiroRadioButton;
    private JRadioButton depositoRadioButton;
    private JRadioButton cambiarContraseñaRadioButton;
    private JPanel opcionesPanel;

    public Opciones(){
        super("OPCIONES");
        setContentPane(opcionesPanel);

        verSaldoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Saldo vent_saldo = new Saldo();
                vent_saldo.abrir_Saldo();
                dispose();
            }

        });
        depositoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Deposito vent_dep = new Deposito();
                vent_dep.abrir();
                dispose();
            }
        });
        retiroRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Retiro vent_ret = new Retiro();
                vent_ret.abrir();
                dispose();
            }
        });
        cambiarContraseñaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cambio ven_cam = new Cambio();
                ven_cam.abrir();
                dispose();
            }
        });
    }

    public void abrir(){
        setSize(400,400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
