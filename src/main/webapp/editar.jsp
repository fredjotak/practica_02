<%@page import="java.util.ArrayList"%>
<%@page import="com.emergentes.modelo.Prioridad"%>
<%@page import="com.emergentes.modelo.Tarea"%>
<%
    Tarea tarea = (Tarea)request.getAttribute("miTarea");
    ArrayList<Prioridad> listaPrioridades = (ArrayList<Prioridad>)session.getAttribute("listaPrioridades");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/styleGlobal.css">
        <link rel="stylesheet" href="css/style.css">
        <title><%=tarea.getId()==0? "Nueva": "Editar"%> Tarea</title>
    </head>
    <body>
        <section>
            <div class="contenedor-formulario">
                <h1 class="formulario-titulo"><%=tarea.getId()==0? "Nueva": "Editar"%> Tarea</h1>
                <div class="formulario-logo"><img src="./css/imagen/tarea.png" alt=""></div>
                <form class="formulario fondo-azul" action="MainController" method="POST">
                    <div class="formulario-campos">
                        <input type="hidden" name="hdnId" value="<%=tarea.getId()%>">
                        <label for="txtNombreTarea">Tarea:</label>
                        <input type="text" id="txtNombreTarea" name="txtNombreTarea" value="<%=tarea.getTitulo()%>" placeholder="nombre de la tarea" required>
                        <br>
                        <label for="cbPrioridad">Prioridad:</label>
                        <select name="cbPrioridad" id="cbPrioridad">
                            <%
                                for(Prioridad prio: listaPrioridades){
                            %>
                            <option value="<%=prio.getId()%>" <%=tarea.getPrioridad().getTipo().equals(prio.getTipo())? "selected": ""%> ><%=prio.getTipo()%></option>
                            <%
                                }
                            %>
                        </select>
                        <input type="hidden" name="hdnCompletado" value="<%=tarea.isCompletado()%>">
                    </div>
                    <input class="boton fondo-naranja" type="submit" value="Registrar">
                </form>
            </div>
        </section>
        <footer>
            ?? 2021 - Todos los derechos reservados
        </footer>
    </body>
</html>
