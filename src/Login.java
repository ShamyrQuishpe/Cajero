import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    private JPanel Panelito;
    private JTextField usuarioTxt;
    private JPasswordField passwordTxt;
    private JButton ingresarButton;
    private JButton registrarseButton;

    public Login(){
        super("LOGIN");
        setContentPane(Panelito);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validaciones();
            }
        });
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registro vent_reg = new Registro();
                vent_reg.abrir();
                dispose();
            }
        });
    }

    public void validaciones(){
        String usuario = usuarioTxt.getText();
        String contrasena = new String(passwordTxt.getPassword());

        if(autenticarUsuario(usuario, contrasena)){
            JOptionPane.showMessageDialog(null, "Inicio de sesion exitoso");
            Saldo.usuarioActual(usuario);
            Retiro.usuarioRetiro(usuario);
            Deposito.usuarioDeposito(usuario);
            Cambio.usuarioCambio(usuario);
            Opciones vent_opc = new Opciones();
            vent_opc.abrir();
            dispose();


        }else {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos. Inténtalo de nuevo.");
            usuarioTxt.setText("");
            passwordTxt.setText("");
        }

    }

    public boolean autenticarUsuario(String nombre, String contraseña){
        BaseDatos manejadorBD = new BaseDatos();
        Connection conexion = manejadorBD.conexionBase();

        if (conexion !=  null){
            try{
                String sql = "SELECT * FROM usuarioscajero WHERE nombre = ? AND contraseña = ?";
                try(PreparedStatement stmt = conexion.prepareStatement(sql)){
                    stmt.setString(1, nombre);
                    stmt.setString(2, contraseña);

                        System.out.println("Consulta sql " + stmt.toString());
                    ResultSet resultSet = stmt.executeQuery();
                    return resultSet.next();
                }
            }catch (SQLException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta");
            }finally {
                try{
                    conexion.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
