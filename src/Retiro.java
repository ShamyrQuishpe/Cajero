import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Retiro extends JFrame{
    private JTextField retiroField;
    private JButton retirarButton;
    private JButton menuButton;
    private JPanel retiroPanel;
    static String usuarioRetiro = "";
    static int id = 0;

    public Retiro(){
        super("RETIRO");
        setContentPane(retiroPanel);

        retirarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double valretiro = Double.parseDouble(retiroField.getText());
                String retiro = "Retiro";
                Saldo.retirarSaldo(valretiro);
                insertarDatos(retiro,valretiro);
                System.out.println(id+retiro+valretiro);
            }

        });
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Opciones vent_opc3 = new Opciones();
                vent_opc3.abrir();
                dispose();
            }
        });
    }

    public void abrir(){
        setVisible(true);
        setSize(400,400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        obtenerId();
    }

    public static void usuarioRetiro(String usuario){
        usuarioRetiro=usuario;
    }
    public static void obtenerId(){
        BaseDatos manejadorBD = new BaseDatos();

        Connection conexion = manejadorBD.conexionBase();

        if(conexion != null){
            try{
                String sql = "SELECT id FROM usuarioscajero WHERE nombre = ?";

                try(PreparedStatement stmt = conexion.prepareStatement(sql)){
                    stmt.setString(1, usuarioRetiro);

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
    public static void insertarDatos(String tipo,double val_retiro){
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
