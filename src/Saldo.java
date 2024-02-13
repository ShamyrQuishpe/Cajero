import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Saldo extends JFrame {
    private JButton menuButton;
    private JLabel valSaldo;
    private JPanel saldoPanel;
    static double saldo = 0;

    static double nuevoSaldo = 0;

    static String usuarioSaldo = "";
    public Saldo(){
        super("SALDO");
        setContentPane(saldoPanel);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Opciones vent_opc1 = new Opciones();
                vent_opc1.abrir();
                dispose();
            }
        });
    }
    public void abrir_Saldo(){
        setVisible(true);
        setSize(400,400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        saldoActual();
        valSaldo.setText("$"+String.valueOf(saldo));
    }
    public static void usuarioActual(String usuario){
        usuarioSaldo=usuario;
    }
    public static void saldoActual(){
        BaseDatos manejadorBD = new BaseDatos();

        // Obtener la conexión a la base de datos
        try (Connection conexion = manejadorBD.conexionBase()) {
            // Verificar si la conexión es nula
            if (conexion == null) {
                JOptionPane.showMessageDialog(null, "No se pudo establecer la conexión a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método si no se pudo establecer la conexión
            }

            // Preparar la consulta SQL para obtener todos los usuarios
            String sql = "SELECT saldo FROM usuarioscajero WHERE nombre=?";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                // Ejecutar la consulta para obtener el resultado
                stmt.setString(1, usuarioSaldo);

                ResultSet resultSet = stmt.executeQuery();

                if(resultSet.next()){
                    saldo = resultSet.getDouble("saldo");
                }
            }
        } catch (SQLException e) {
            // Capturar cualquier excepción SQL
            JOptionPane.showMessageDialog(null, "Error al recuperar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprimir la traza de la pila para diagnóstico
        }
    }

    public static void depositarSaldo(double saldo_deposito){
        saldoActual();
        BaseDatos manejadorBD = new BaseDatos();

        // Obtener la conexión a la base de datos
        try (Connection conexion = manejadorBD.conexionBase()) {
            // Verificar si la conexión es nula
            if (conexion == null) {
                JOptionPane.showMessageDialog(null, "No se pudo establecer la conexión a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método si no se pudo establecer la conexión
            }

            // Preparar la consulta SQL para obtener todos los usuarios
            String sql = "UPDATE usuarioscajero SET saldo = ? WHERE nombre = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                double nuevoSaldo = saldo+saldo_deposito;
                // Ejecutar la consulta para obtener el resultado
                stmt.setDouble(1, nuevoSaldo);
                stmt.setString(2, usuarioSaldo);

                stmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Deposito Exitoso");
            }
        } catch (SQLException e) {
            // Capturar cualquier excepción SQL
            JOptionPane.showMessageDialog(null, "Error al recuperar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprimir la traza de la pila para diagnóstico
        }
    }

    public static void retirarSaldo(double saldo_retiro){
        saldoActual();
        BaseDatos manejadorBD = new BaseDatos();

        // Obtener la conexión a la base de datos
        try (Connection conexion = manejadorBD.conexionBase()) {
            // Verificar si la conexión es nula
            if (conexion == null) {
                JOptionPane.showMessageDialog(null, "No se pudo establecer la conexión a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método si no se pudo establecer la conexión
            }

            // Preparar la consulta SQL para obtener todos los usuarios
            String sql = "UPDATE usuarioscajero SET saldo = ? WHERE nombre = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                if(saldo<saldo_retiro){
                    JOptionPane.showMessageDialog(null, "No cuenta con el saldo suficiente");
                }else {
                    double nuevoSaldo = saldo - saldo_retiro;
                    // Ejecutar la consulta para obtener el resultado
                    stmt.setDouble(1, nuevoSaldo);
                    stmt.setString(2, usuarioSaldo);

                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Retiro Exitoso");
                }
            }
        } catch (SQLException e) {
            // Capturar cualquier excepción SQL
            JOptionPane.showMessageDialog(null, "Error al recuperar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprimir la traza de la pila para diagnóstico
        }
    }

}
