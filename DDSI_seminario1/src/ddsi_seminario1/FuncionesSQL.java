package ddsi_seminario1;

import com.sun.tools.javac.Main;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuncionesSQL {


    private static int contadorPedidos = 0;
    private static String STOCK = "STOCK";
    private static String PEDIDOS = "PEDIDOS";
    private static String DETALLEPEDIDOS= "DETALLEPEDIDOS";

    static void borradoYCreacion() throws SQLException {
        Connection conexion = conexionBD.getConexion();
        borrado(conexion);
        creacion(conexion);
    }
    static void borrado(Connection conexion) throws SQLException {
        DatabaseMetaData metaDatos = conexion.getMetaData();

        ResultSet rsDetallePedido = metaDatos.getTables(null, null, DETALLEPEDIDOS, null);
        if (rsDetallePedido.next())
            eliminaTabla(conexion, DETALLEPEDIDOS);
        ResultSet rsStock = metaDatos.getTables(null, null, STOCK, null);
        if (rsStock.next())
            eliminaTabla(conexion, STOCK);

        ResultSet rsPedido = metaDatos.getTables(null, null, PEDIDOS, null);
        if (rsPedido.next())
            eliminaTabla(conexion, PEDIDOS);
    }

    static void eliminaTabla(Connection conexion, String nombreTabla) throws SQLException {
        PreparedStatement stmt = null;
        stmt = conexion.prepareStatement("DROP TABLE " + nombreTabla);
        stmt.execute();
        stmt.close();
        stmt =null;
        System.out.println( nombreTabla +" table --> ELIMINADA");
    }


    static void creacion(Connection conexion) throws SQLException {
        String creacionTablaStock= "CREATE TABLE " + STOCK + " (Cproducto int PRIMARY KEY NOT NULL,"+
                                    " Cantidad int)";
        String creacionTablaPedidos = "CREATE TABLE " + PEDIDOS +" (CPedido int PRIMARY KEY NOT NULL," +
                                    "Ccliente int, FechaPedido DATE)";
        String creacionTablaDetallePedidos = "CREATE TABLE "+  DETALLEPEDIDOS+" (CPedido int," +
                "Cproducto int, Cantidad int, PRIMARY KEY (Cpedido,CProducto), FOREIGN KEY (CProducto) "+
                "REFERENCES STOCK(Cproducto), FOREIGN KEY (CPedido) REFERENCES PEDIDOS(Cpedido)) ";
        crearTabla(conexion, creacionTablaStock);
        crearTabla(conexion, creacionTablaPedidos);
        crearTabla(conexion, creacionTablaDetallePedidos);

        addAllStock();

    }
    static void crearTabla(Connection conexion, String creacionTabla) throws SQLException {
        PreparedStatement stmt = null;
        stmt = conexion.prepareStatement(creacionTabla);
        stmt.execute();
        stmt.close();
    }

    public static int addPedido(int codPedido, int codCliente, String fechaPedido) throws SQLException {
        //Gestión de codPedido

        //Insertar datos en la tabla
        
        PreparedStatement stmt = null;
        stmt =  conexionBD.getConexion().prepareStatement("INSERT INTO "+ PEDIDOS +" VALUES (?,?,?)");
        stmt.setInt(1,codPedido);
        stmt.setInt(2,codCliente);
        stmt.setString(3,fechaPedido);
        stmt.executeUpdate();
        stmt.close();
        return codPedido;
    }

    public static void addStock(int Cproducto, int cantidad) throws SQLException {

        //Insertar datos en la tabla
        PreparedStatement stmt = null;
        stmt =  conexionBD.getConexion().prepareStatement("INSERT INTO " + STOCK +" VALUES (?,?)");
        stmt.setInt(1,Cproducto);
        stmt.setInt(2,cantidad);
        stmt.executeUpdate();
        stmt.close();

    }

    static void addAllStock() throws SQLException {
        addStock(1,10);
        addStock(2,10);
        addStock(3,10);
        addStock(4,10);
        addStock(5,15);

        addStock(6,20);
        addStock(7,20);
        addStock(8,20);
        addStock(9,20);
        addStock(10,15);

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
                                r.getDate("FechaPedido"));
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
