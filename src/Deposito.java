import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Deposito extends JFrame{
    private JTextField depoField;
    private JButton depositarButton;
    private JButton menúButton;
    private JPanel depositarPanel;

    static String usuarioDep = "";
    static int id = 0;


    public Deposito(){
        super("DEPOSITO");
        setContentPane(depositarPanel);

        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double valdeposito = Double.parseDouble(depoField.getText());
                Saldo.depositarSaldo(valdeposito);
                System.out.println(valdeposito);
                String deposito = "Deposito";
                insertarDatos(deposito,valdeposito);
            }
        });
        menúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Opciones vent_opciones2 = new Opciones();
                vent_opciones2.abrir();
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
    public static void usuarioDeposito(String usuario){
        usuarioDep=usuario;
    }
    public static void obtenerId(){
        BaseDatos manejadorBD = new BaseDatos();

        Connection conexion = manejadorBD.conexionBase();

        if(conexion != null){
            try{
                String sql = "SELECT id FROM usuarioscajero WHERE nombre = ?";

                try(PreparedStatement stmt = conexion.prepareStatement(sql)){
                    stmt.setString(1, usuarioDep);

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
