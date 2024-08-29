package web;

import datos.ClienteDaoJDBC;
import dominio.Cliente;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ServletControlador")
public class ServletControlador extends HttpServlet{
    //Sobreescribimos metodo doGet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Recuperamos el parametro de accion 
        String accion = request.getParameter("accion");
        
        //Verficamos que el parametro accion no este vacion 
        if(accion != null){
            //Si es distinto de null y tiene un valor escogemos la opcion entre los case
            switch(accion){
                case "editar": 
                    //Llamamos al metodo que edita al cliente y le pasamos los parametros request y response 
                    this.editarCliente(request, response);
                    break;
                case "eliminar":
                    //Llamamos al metodo que elimina un cliente de la base de datos 
                    this.eliminarCliente(request, response);
                    break;
                default:
                    //Llamamos al metodo por default 
                    this.accionDault(request, response);
            }
        }else{
            //Si no hay nada en el parametro accion, llamamos al metodo por default
            this.accionDault(request, response);
        }
    }
    
    //Metodo que permite tener siempre la lista de clientes, el total de clientes, saldo total y redirigir a la pagina de inicio
    private void accionDault(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        //Recupera la informacion de clientes en un arraList llamado clientes
        List <Cliente> clientes = new ClienteDaoJDBC().select();
        System.out.println("clientes = " + clientes);
        
        //Compartimos la infoormacion en el alcande de session para que no se pierda 
        HttpSession session = request.getSession();
        
        //Compartimos una variable en el alcance session que contiene el arrayList de clientes 
        session.setAttribute("clientes", clientes);
        //Compartimos una variable en el alcacen session que contiene el largo del arrayList, que es el total de clientes
        session.setAttribute("totalClientes", clientes.size());
        //Compartimos una variable en el alcance session que contiene el saldoTotal del arrayList, es el total del saldo de todos los elementos
        session.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));
        
        //Enviamos la informacion recuperada al jsp clientes.jsp, pero con sendRedirect para que el url cambie y no se envie dos veces la informacion al servidor
        //request.getRequestDispatcher("clientes.jsp").forward(request, response);
        response.sendRedirect("clientes.jsp");
    }
    
    //Sobreescribimos el metodo doPost
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        //Recuperamos el parametro de accion 
        String accion = request.getParameter("accion");
        //Revisamos si hay informacion en la variable de accion 
        if(accion != null){
            //Si hay informacion preguntamos que tipo de accion deseamos ejecutar 
            switch(accion){
                case "insertar":
                    //Llamamos al metodo insertarCliente
                    this.insertarCliente(request, response);
                    break;
                case "modificar":
                    //Llamamos al metodo modificarCliente
                    this.modificarCliente(request, response);
                    break;
                default:
                    //Llamamos al metodo accionDefault
                    this.accionDault(request, response);
            }
        }else{
            //Si la opcion es igual a null, llamamos al metodo accionDefault
            this.accionDault(request, response);
        }
    }
    
    //Funcion que recibe el arrayList de clientes y calcula el total de los saldos de los clientes 
    private double calcularSaldoTotal (List<Cliente> clientes){
        //Variable que contiene el saldoTotal 
        double saldoTotal = 0;
        
        //Realizamos interacion del arrayList
        for(Cliente cliente: clientes){
            //Por cada elemento que haya en el arrayList se va a ir acumulado su saldo en la variable saldoTotal
            saldoTotal += cliente.getSaldo();
        }
        //Regresamos la variable saldo total que contiene el total de dinero
        return  saldoTotal;
    }
    
    //Funcion que inserta un cliente en la base de datos 
    private void insertarCliente(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        //Recupearamos los valores del formulario que se contiene en el jsp AgregarCliente
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = 0;
        String saldoString = request.getParameter("saldo");
        
        //Revisamos si la variable saldoString contiene un valor 
        if(saldoString != null && !"".equals(saldoString)){
            //Convertimos el valor de la variable saldoString en double y lo asignamos a la variable saldo
            saldo = Double.parseDouble(saldoString);
        }
        
        //Creamos el objeto de cliente con los nuevos datos que hemos recibido por parte del formulario 
        Cliente cliente = new Cliente(nombre, apellido, email, telefono, saldo);
        
        //Insertamos este nuevo objeto en la base de datos, utilizando la capa de datos con la clase ClienteDaoJDBC y el metodo insert
        int registrosModificados = new ClienteDaoJDBC().insert(cliente);
        //Procesamos la variable registrosModificados para ver cuantos registros se modificaron
        System.out.println("registrosModificados = " + registrosModificados);
        
        //Despues de guardar la informacion en la base de datos redirgimos al servlet clientes por medio del metodo acciondefault
        this.accionDault(request, response);
    }
    
    //Funcion que edita un cliente en la base de datos 
    private void editarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Este metodo recupera el idCliente que se trae de listadoClientes.jsp
        
        //Rcuperamos el idCliente 
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        
        //Con el idCliente, recuperamos los demas valores de ese id de la base de datos
        Cliente cliente = new ClienteDaoJDBC().encontrar(new Cliente(idCliente));
        
        //Colocamos el objeto cliente en el alcance 
        request.setAttribute("cliente", cliente);
        
        //Definimos la pagina JSP que se va a utilizar 
        String jspEditar = "/WEB-INF/Paginas/Cliente/editarCliente.jsp";
        
        //Compartimos los objetos request y response con getRequestDispatcher
        request.getRequestDispatcher(jspEditar).forward(request, response);   
    }
    
    //Funcion que modifica los valores de un cliente en la base de datos
    private void modificarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Recuperamos los valores del formulario del jsp editarCliente
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = 0;
        String saldoString = request.getParameter("saldo");
        
        //Revisamos si la varible saldo tiene un valor 
        if(saldoString != null && !"".equals(saldoString)){
            //Convertimos el valor de la variable saldoString en double y lo asignamos a saldo
            saldo = Double.parseDouble(saldoString);
        }
        
        //Creamos nuevo objeto de la clase Cliente con los atributos que tienen lo que se ingreso en el formulario
        Cliente cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);
        
        //Modificamos el objeto en la base de datos
        int registrosModificados = new ClienteDaoJDBC().update(cliente);
        System.out.println("registrosModificados = " + registrosModificados);
        
        //Redirigimos la accion por default
        this.accionDault(request, response);
    }
    
    //Funcion que elimina un cliente de la base de datos
    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Recuperamos el atributo del idCliente 
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        
        //Creamos el objeto de la clase cliente con el id proporcionado 
        Cliente cliente = new Cliente(idCliente);
        
        //Eliminamos el objeto de la base de datos usando la clase ClienteDAOJdbc
        int registrosModificados = new ClienteDaoJDBC().delete(cliente);
        System.out.println("registrosModificados = " + registrosModificados);
        
        //Redirigimos al metodo por default
        this.accionDault(request, response);
    }
}
