package ddsi_seminario1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class conexionBD {
    static private Connection conexion;
    static private String usuario, password;
    static private String url = "jdbc:oracle:thin:@//oracle0.ugr.es:1521/practbd.oracle0.ugr.es";
    static private String ficheroDatos = "passwd.properties";


    static public void iniciarConexion(){
        leerDatos();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion = DriverManager.getConnection(url, usuario, password);
        }  catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }

    static public Connection getConexion() {
        return conexion;
    }

     static public void cerrarConexion(){
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    static public void leerDatos(){
        Properties properties = new Properties();
        try {
            Path path = Paths.get(ficheroDatos);
            String util = path.toAbsolutePath().toString();
            properties.load(new FileInputStream(util));
            usuario = properties.get("db.user").toString();
            password = properties.get("db.passwd").toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
