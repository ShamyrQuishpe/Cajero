import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cambio extends JFrame{
    private JTextField passNueva;
    private JButton cambiarButton;
    private JPanel passwdPanel;
    private JButton menuButton;
    static int id = 0;
    static String usuario_camb = "";

    public Cambio(){
        super("CAMBIO CONTRASEÑA");
        setContentPane(passwdPanel);

        cambiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contrasena_nueva = passNueva.getText();
                String tipo = "Cambio de contraseña";
                double mont = 0;
                insertarDatos(contrasena_nueva);
                insertarTransaccion(tipo, mont);

            }
        });
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Opciones vent_opc4 = new Opciones();
                vent_opc4.abrir();
                dispose();
            }
        });
    }
    public static void usuarioCambio(String usuario){
        usuario_camb=usuario;
    }
    public void abrir(){
        setVisible(true);
        setSize(400,400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        obtenerId();
    }
    public static void obtenerId(){
        BaseDatos manejadorBD = new BaseDatos();

        Connection conexion = manejadorBD.conexionBase();

        if(conexion != null){
            try{
                String sql = "SELECT id FROM usuarioscajero WHERE nombre = ?";

                try(PreparedStatement stmt = conexion.prepareStatement(sql)){
                    stmt.setString(1, usuario_camb);

                    ResultSet resultSet = stmt.executeQuery();

                    if(resultSet.next()){
                        id = resultSet.getInt("id");
                    }
                    JOptionPane.showMessageDialog(null, "ID Reconocido");
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Error al obtener el id");
            }finally {
                try{
                    conexion.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void insertarDatos(String passwd){
        BaseDatos manejadorBD = new BaseDatos();

        Connection conexion = manejadorBD.conexionBase();

        if(conexion != null){
            try{
                String sql = "UPDATE usuarioscajero SET contraseña = ? WHERE id = ?";
                try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                    // Ejecutar la consulta para obtener el resultado
                    stmt.setString(1, passwd);
                    stmt.setInt(2, id);

                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Cambio de contraseña exitoso");
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Error al cambiar contraseña");
            }finally {
                try{
                    conexion.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void insertarTransaccion(String tipo,double val_retiro){
        BaseDatos manejadorBD = new BaseDatos();

        Connection conexion = manejadorBD.conexionBase();

        if(conexion != null){
            try{
                String sql = "INSERT INTO transacciones (id_usuario, tipo_transaccion, monto) VALUES (?,?,?)";

                try(PreparedStatement stmt = conexion.prepareStatement(sql)){
                    stmt.setInt(1, id);
                    stmt.setString(2, tipo);
                    stmt.setDouble(3, val_retiro);

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Registro Exitoso");
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Error al agregar la transaccion");
            }finally {
                try{
                    conexion.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
