package datos;

import dominio.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoJDBC {

    //Variables que tienen la sentencias sql 
    private static final String SQL_SELECT = "SELECT id_cliente, nombre, apellido, email, telefono ,saldo FROM cliente";
    private static final String SQL_SELECT_BY_ID = "SELECT id_cliente, nombre, apellido, email, telefono, saldo "
            + " FROM cliente WHERE id_cliente = ?";
    private static final String SQL_INSERT = "INSERT INTO cliente(nombre, apellido, email, telefono, saldo) "
            + " VALUES(?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE cliente SET nombre = ?, apellido = ?, email = ?, telefono = ?, saldo = ? WHERE id_cliente = ?";
    private static final String SQL_DELETE = "DELETE FROM cliente WHERE id_cliente = ?";

    //Definimos el metodo SELECT que regresa un objeto de la clase Cliente
    public List<Cliente> select() {
        //Preparamos las variables para la conexion a la base de datos
        Connection conexion = null;
        PreparedStatement instrucion = null;
        ResultSet resultado = null;
        Cliente cliente = null;
        //ArrayList de la clase Cliente 
        List<Cliente> clientes = new ArrayList<>();

        try {
            //2.Realizamos la conexion a la base de datos
            conexion = Conexion.getConnection();
            //3. Inicializamos el objeto instruccion 
            instrucion = conexion.prepareStatement(SQL_SELECT);
            //4. Ejecutamos el query 
            resultado = instrucion.executeQuery();
            while (resultado.next()) {
                //Variables que van a traer lo que se contiene en la base de datos 
                int idCliente = resultado.getInt("id_cliente");
                String nombre = resultado.getString("nombre");
                String apellido = resultado.getString("apellido");
                String email = resultado.getString("email");
                String telefono = resultado.getString("telefono");
                double saldo = resultado.getDouble("saldo");

                //Con la variable cliente creamos un nuevo objeto de la clase Cliente
                cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);
                //Agregamos al arrayList el nuevo objeto que creo antes
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            //Cerramos los objetos abiertos
            Conexion.close(resultado);
            Conexion.close(instrucion);
            Conexion.close(conexion);
        }
        //Regresamos el arrayList de clientes 
        return clientes;
    }

    //DEFINIMOS METODO SELECT_BY_ID
    public Cliente encontrar(Cliente cliente) {
        //Preparamos las variables para la conexion a la base de datos
        Connection conexion = null;
        PreparedStatement instrucion = null;
        ResultSet resultado = null;

        try {
            //2.Realizamos la conexion a la base de datos
            conexion = Conexion.getConnection();
            //3. Inicializamos el objeto instruccion 
            instrucion = conexion.prepareStatement(SQL_SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //3.1 Indicamos el parametro que vamos a seleccionar para encontrar el id
            instrucion.setInt(1, cliente.getIdCliente());
            //4. Ejecutamos el query 
            resultado = instrucion.executeQuery();
            //4.1 Indicamos el registro en el que nos queremos posicionar
            //resultado.absolute(1);

            if (resultado.next()) {
                //Variables que van a traer lo que se contiene en la base de datos 
                String nombre = resultado.getString("nombre");
                String apellido = resultado.getString("apellido");
                String email = resultado.getString("email");
                String telefono = resultado.getString("telefono");
                double saldo = resultado.getDouble("saldo");

                //Asignamos los valores que traiamos al que nos fue pasado como parametro
                cliente.setNombre(nombre);
                cliente.setApellido(apellido);
                cliente.setEmail(email);
                cliente.setTelefono(telefono);
                cliente.setSaldo(saldo);
            }else{
                System.out.println("No se encontro");
                return null;
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            //Cerramos los objetos abiertos
            Conexion.close(resultado);
            Conexion.close(instrucion);
            Conexion.close(conexion);
        }
        //Regresamos el ojeto cliente 
        return cliente;
    }

    //Definimos metodo para insertar un objeto de tipo cliente 
    public int insert(Cliente cliente) {
        //Prepramos las variables para la conexion 
        Connection conexion = null;
        PreparedStatement instruccion = null;
        //Variable que indica los renglones insertados
        int row = 0;
        try {
            //2. Realizamos la conexion
            conexion = Conexion.getConnection();
            //3. Inicializamos el objeto instruccion 
            instruccion = conexion.prepareStatement(SQL_INSERT);
            //4. Definimos atributos que nos permiten hacer el insert
            instruccion.setString(1, cliente.getNombre());
            instruccion.setString(2, cliente.getApellido());
            instruccion.setString(3, cliente.getEmail());
            instruccion.setString(4, cliente.getTelefono());
            instruccion.setDouble(5, cliente.getSaldo());
            //Ejecutamos el query 
            row = instruccion.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            //Cerramos los objetos que abrimos 
            Conexion.close(instruccion);
            Conexion.close(conexion);
        }
        //Regresamos el numero de renglones afectados
        return row;
    }

    //Metodo UPDATE que recibe un objeto de la clase Cliente 
    public int update(Cliente cliente) {
        //Preparamos variables para la conexion 
        Connection conexion = null;
        PreparedStatement instruccion = null;
        int rows = 0;

        try {
            //Realizamos la conexion
            conexion = Conexion.getConnection();
            //Inicializamos objeto instruccion 
            instruccion = conexion.prepareStatement(SQL_UPDATE);
            //Preaparamos las variables
            instruccion.setString(1, cliente.getNombre());
            instruccion.setString(2, cliente.getApellido());
            instruccion.setString(3, cliente.getEmail());
            instruccion.setString(4, cliente.getTelefono());
            instruccion.setDouble(5, cliente.getSaldo());
            instruccion.setInt(6, cliente.getIdCliente());
            //Ejecutamos query
            rows = instruccion.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(instruccion);
            Conexion.close(conexion);
        }
        //Regresamos los renglones actualizados 
        return rows;
    }

    //Metodo DELETE que recibe un objeto de la clase Cliente
    public int delete(Cliente cliente) {
        //Preparamos variables para la conexion 
        Connection conexion = null;
        PreparedStatement instruccion = null;
        int rows = 0;

        try {
            //Realizamos la conexion
            conexion = Conexion.getConnection();
            //Inicializamos objeto instruccion 
            instruccion = conexion.prepareStatement(SQL_DELETE);
            //Preaparamos las variables
            instruccion.setInt(1, cliente.getIdCliente());
            //Ejecutamos query
            rows = instruccion.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(instruccion);
            Conexion.close(conexion);
        }
        //Regresamos los renglones actualizados 
        return rows;
    }
}
