package ddsi_seminario1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionBD {
    static private Connection conexion;

    public conexionBD(String url, String usuario, String password) {
        iniciarConexion(url, usuario, password);
    }

    static private void iniciarConexion(String url, String usuario, String password){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion = DriverManager.getConnection(url, usuario, password);
        }  catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }

    static public Connection getConexion(String url, String usuario, String password) {
        if (conexion == null)
            iniciarConexion(url,usuario,password);

        return conexion;
    }

     public void cerrarConexion(){
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
