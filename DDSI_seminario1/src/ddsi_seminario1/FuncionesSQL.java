package ddsi_seminario1;

import com.sun.tools.javac.Main;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuncionesSQL {
    private static String STOCK = "STOCK";
    private static String PEDIDOS = "PEDIDOS";
    private static String DETALLEPEDIDOS= "DETALLEPEDIDOS";

    static void borradoYCreacion() throws SQLException {
        Connection conexion = conexionBD.getConexion();
        borrado(conexion);
        creacion(conexion);
        conexion.commit();
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
                "Cproducto int, Cantidad int CHECK (Cantidad > 0), PRIMARY KEY (Cpedido,CProducto), FOREIGN KEY (CProducto) "+
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

    public static void addDetallePedido(int codPedido, int Cproducto, int Cantidad) throws SQLException {

        //Insertar datos en la tabla

        PreparedStatement stmt = null;

        stmt = conexionBD.getConexion().prepareStatement("INSERT INTO " + DETALLEPEDIDOS +" VALUES (?,?,?)");

        stmt.setInt(1,codPedido);
        stmt.setInt(2,Cproducto);
        stmt.setInt(3,Cantidad);

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
        String str;
        try{
            System.out.println("\nTODOS LOS REGISTROS DE LA TABLA " + tabla + ".");
            switch (tabla){
                case "STOCK":
                    str= String.format("\n%10s %10s", "Cproducto","Cantidad");
                    System.out.println(str);

                    break;

                case "PEDIDOS":
//                    System.o
                    str = String.format("\n%10s %10s %10s", "Cpedido","Ccliente", "FechaPedido");
                    System.out.println(str);
                    break;

                case "DETALLEPEDIDOS":
                    str=String.format("\n%10s %10s %10s", "Cpedido", "Cproducto", "Cantidad");
                    System.out.println(str);
                    break;

                default:
                    System.out.println("Error en el nombre de la tabla.");
                    break;
            }


            while(r.next()){
                switch (tabla){
                    case "STOCK":

                        str = String.format("%10d %10d", r.getInt("Cproducto"), r.getInt("Cantidad"));
                        System.out.println(str);

                        break;

                    case "PEDIDOS":
                        str = String.format("%10d %10d", r.getInt("Cpedido"), r.getInt("Ccliente"));
                        System.out.println(str + "  "+ r.getDate("FechaPedido"));
                        break;

                    case "DETALLEPEDIDOS":
                        str = String.format("%10d %10d %10d",r.getInt("Cpedido"), r.getInt("Cproducto"),
                                r.getInt("Cantidad"));
                        System.out.println(str + "  "+ r.getDate("FechaPedido"));

                        break;

                    default:
//                        System.out.println("Error en el nombre de la tabla.");
                        break;
                }
            }

        }catch(SQLException ex){
            System.out.println("\n¡No hemos podido acceder a los datos de la tabla! Comprueba que haya datos en ésta y que" +
                    " estén introducidos correctamente.");
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

    static void guardarCambios(Connection conexion) throws SQLException{
        PreparedStatement stmt = null;
        stmt = conexion.prepareStatement("COMMIT");
        stmt.execute();
        stmt.close();
        stmt = null;
        System.out.println( "Cambios realizados guardados con éxito");
    }
}
