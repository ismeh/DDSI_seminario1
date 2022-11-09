package ddsi_seminario1;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionesSQL {
    private static int contadorPedidos = 0;

    static void  SacaInfo(Connection conexion) throws SQLException {
        DatabaseMetaData metaDatos = conexion.getMetaData();
        ResultSet rs= metaDatos.getColumns(null,null,"STOCK",null);

        while (rs.next()){
            String nombreColumna  = rs.getString(4);
            String tipoColumna = rs.getString(6);

            System.out.println("Columna: " + nombreColumna + " tipo: " + tipoColumna);
        }
    }

    public static int addPedido(int codCliente, String fechaPedido){
        int codPedido = 0;
        //Gestión de codPedido


        //Insertar datos en la tabla
        
        return codPedido;
    }


}
