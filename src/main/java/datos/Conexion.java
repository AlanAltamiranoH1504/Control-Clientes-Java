package datos;

import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {
    //Atributos que nos permite la conexion a la base de datos 
    protected static final String JDBC_URL = "jdbc:mysql://localhost:3306/control_clientes?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    protected static final String JDBC_USER = "root";
    protected static final String JDBC_PASSWORD = "admin";
    
    //Metodo que nos permite realizar la conexion a la base de datos 
    public static DataSource getDataSource(){
        //Creamos nuevo objeto de la clase BasicDataSource
        BasicDataSource ds = new  BasicDataSource();
        //Al objeto ds seteamos los valores de url, usuario y password
        ds.setUrl(JDBC_URL);
        ds.setUsername(JDBC_USER);
        ds.setPassword(JDBC_PASSWORD);
        //Definimos tamaño inicial de pool de conexiones
        ds.setInitialSize(50);
        //Regresamos el objeto 
        return ds;
    }
    
    //Definimos metodo para obtener una conexion con la clase getConnection
    public static Connection getConnection() throws SQLException{
        //Obtenemos una conexion con getDataSource y getConecction
        return getDataSource().getConnection();
    }
    
    //Definimos metodo close para los de clase Connection, preparedStatement y Resultset
    public static void close(ResultSet resultado){
        try {
            resultado.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    public static void close(PreparedStatement instruccion){
        try {
            instruccion.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    public static void close(Connection conexion){
        try {
            conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
