<%-- Es una venta modal que despliega un formulario al momento de hacer click en editar en alguno de los cientes --%>

<div class="modal fade" id="agregarClienteModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <%-- CABECERO DEL FORMULARIO CON MODAL HEADER --%>
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title">Agregar Cliente</h5>
                <%-- Boton que permite el cierre de la ventana modal que contiene el formulario --%>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <%-- FORMULARIO --%>
            <!-- Si se envia la informacion al servlet controlador anteponiendo el nombre de la aplicacion -->
            <!-- Se le pasa como argumento una accion -->
            <form action="${pageContext.request.contextPath}/ServletControlador?accion=insertar" method="post" class="was-validated">
                <div class="modal-body"> 
                    <div class="form-group">
                        <label for="nombre">Nombre:</label>
                        <input type="text" name="nombre" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="apellido">Apellido: </label>
                        <input type="text" name="apellido" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="telefono">Telefono:</label>
                        <input type="tel" name="telefono" class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="saldo">Saldo:</label>
                        <input type="number" name="saldo" class="form-control" required step="any">
                    </div>
                </div>
                <!-- PIE DE FORMULARIO -->
                <div class="modal-footer">
                    <button class="btn btn-primary" type="submit">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>
