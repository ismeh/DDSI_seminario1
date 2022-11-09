package ddsi_seminario1;

import java.sql.*;

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

    public static int addPedido(int codPedido, int codCliente, String fechaPedido) throws SQLException {

        //Insertar datos en la tabla
        PreparedStatement stmt = null;
        stmt =  conexionBD.getConexion().prepareStatement("INSERT INTO PEDIDOS VALUES (?,?,?)");
        stmt.setInt(1,codPedido);
        stmt.setInt(2,codCliente);
        stmt.setString(3,fechaPedido);
        stmt.executeUpdate();

        return codPedido;
    }


}
