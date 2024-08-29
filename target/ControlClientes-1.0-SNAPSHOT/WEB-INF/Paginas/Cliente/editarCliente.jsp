<!DOCTYPE html>
<html>
    <head>
        <!-- Hacemos importacion de librerias para el uso de estilos de bootStrap -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- Font Awesome -->
        <script src="https://kit.fontawesome.com/582c9b9f51.js" crossorigin="anonymous"></script>
        <title>EDITAR CLIENTE</title>
    </head>
    <body>
        <!-- Incluimos el JSP de cabecero  -->
        <jsp:include page="/WEB-INF/Paginas/Comunes/Cabecero.jsp" />


        <!-- Agregamos formulario de editarCliente -->
        <form action="${pageContext.request.contextPath}/ServletControlador?accion=modificar&idCliente=${cliente.idCliente}" method="post" class="was-validated  ">
            
            <!-- Incluimos el jsp con botones de barra de navegacion -->
            <jsp:include page="/WEB-INF/Paginas/Comunes/botonesNavegacionEdicion.jsp"/>

            <section id="details">
                <div class="container">
                    <div class="row">
                        <div class="col">
                            <div class="card">
                                <div class="card-header">
                                    <h4>Editar Cliente</h4>
                                </div>
                                <div class="card-body">
                                    <div class="form-group">
                                        <label for="nombre">Nombre:</label>
                                        <input type="text" name="nombre" class="form-control" required value="${cliente.nombre}">
                                    </div>

                                    <div class="form-group">
                                        <label for="apellido">Apellido: </label>
                                        <input type="text" name="apellido" class="form-control" required value="${cliente.apellido}">
                                    </div>

                                    <div class="form-group">
                                        <label for="email">Email:</label>
                                        <input type="email" name="email" class="form-control" required value="${cliente.email}">
                                    </div>

                                    <div class="form-group">
                                        <label for="telefono">Telefono:</label>
                                        <input type="tel" name="telefono" class="form-control" required value="${cliente.telefono}"> 
                                    </div>

                                    <div class="form-group">
                                        <label for="saldo">Saldo:</label>
                                        <input type="number" name="saldo" class="form-control" required value="${cliente.saldo}" step="any">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </form>

        <!-- Incluimos pie de pagina -->
        <jsp:include page="/WEB-INF/Paginas/Comunes/PiePagina.jsp" />

        <!-- Link de JavaScript -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>

