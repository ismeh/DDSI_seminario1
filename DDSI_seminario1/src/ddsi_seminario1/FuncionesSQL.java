package ddsi_seminario1;

import com.sun.tools.javac.Main;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    protected static void consultaTabla(Connection conexion, String tabla){
        ResultSet r = buscar("select * from " + tabla, conexion);

        try{
            System.out.println("\nTODOS LOS REGISTROS DE LA TABLA " + tabla);

            while(r.next()){
                switch (tabla){
                    case "STOCK":
                        System.out.println("\n" + r.getInt("Cproducto") + " | " + r.getInt("Cantidad"));
                        break;

                    case "PEDIDOS":
                        System.out.println("\n" + r.getInt("Cpedido") + " | " + r.getInt("Ccliente") + "\n" +
                                r.getDate("Fecha-pedido"));
                        break;

                    case "DETALLE-PEDIDO":
                        System.out.println("\n" + r.getInt("Cpedido") + " | " + r.getInt("Cproducto") + "\n" +
                                r.getInt("Cantidad"));
                        break;

                    default:
                        System.out.println("Error en el nombre de la tabla.");
                        break;
                }
            }
        }catch(SQLException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static ResultSet buscar(String sql, Connection conexion){
        try{
            Statement stm = conexion.createStatement();
            return stm.executeQuery(sql);
        }catch(SQLException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
