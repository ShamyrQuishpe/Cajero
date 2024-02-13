import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class BaseDatos {
    public Connection conexionBase() {
        String url = "jdbc:mysql://localhost:3306/capacitacion";
        String usuarioDB = "root";
        String contrasenaDB = "123456";

        Connection conexion = null;

        try{
            conexion = DriverManager.getConnection(url, usuarioDB, contrasenaDB);
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error al conectar con la base");
        }
        return conexion;
    }
}
