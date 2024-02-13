import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registro extends JFrame{
    private JTextField nombreNew;
    private JTextField nusuarioNEW;
    private JTextField montoNEW;
    private JButton registrarseButton;
    private JButton mostrarUsuariosButton;
    private JPanel registroPanel;
    private JLabel nom;
    private JPasswordField passwordRegistro;

    public Registro() {
        super("REGISTRO");
        setContentPane(registroPanel);

        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresoFormulario();
            }
        });
    }

    public void ingresoFormulario(){
        String nombre = nombreNew.getText();
        String nusuario = nusuarioNEW.getText();
        String contrsaña_reg = new String(passwordRegistro.getPassword());
        double valor = Double.parseDouble(montoNEW.getText());

        insertarDatos(nombre, nusuario, contrsaña_reg, valor);

    }

    public void insertarDatos(String nombres, String usuarios, String contra, double mont){
        BaseDatos manejadorBD = new BaseDatos();

        Connection conexion = manejadorBD.conexionBase();

        if(conexion != null){
            try{
                String sql = "INSERT INTO usuarioscajero (nombre, nombre_usuario, contraseña, saldo) VALUES (?,?,?,?)";

                try(PreparedStatement stmt = conexion.prepareStatement(sql)){
                    stmt.setString(1, nombres);
                    stmt.setString(2, usuarios);
                    stmt.setString(3, contra);
                    stmt.setDouble(4,mont);

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Registro Exitoso");
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Error al agregar los datos");
            }finally {
                try{
                    conexion.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void abrir(){
        setSize(400,400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
