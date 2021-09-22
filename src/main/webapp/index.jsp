<%@page import="java.util.ArrayList"%>
<%@page import="com.emergentes.modelo.Tarea"%>
<%
    ArrayList<Tarea> listaTareas = (ArrayList<Tarea>)session.getAttribute("listaTareas");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/styleGlobal.css">
        <link rel="stylesheet" href="css/style.css">
        <title>Inicio - Lista de Tareas</title>
    </head>
    <body>
        <section>
            <h1 class="centro">Inicio</h1>
            <a href="MainController?op=nuevo" class="boton fondo-naranja ancho-200p">Nueva tarea</a>
            <%
                if(listaTareas!=null && listaTareas.size()>0){
            %>
             <div class="contenedor-tabla">
                <table class="tabla-uno">
                    <caption><h3>Lista de Tareas</h3></caption>
                    <tr>
                        <th>Id</th>
                        <th>Tarea</th>
                        <th>Prioridad</th>
                        <th>Completado</th>
                        <th colspan="2">Acción</th>
                    </tr>
                    <%
                        for(Tarea item: listaTareas){
                    %>
                    <tr>
                        <td><%=item.getId()%></td>
                        <td><%=item.getTitulo()%></td>
                        <td><%=item.getPrioridad()%></td>
                        <td><a class="casilla <%=(item.isCompletado())?"casilla-marcada": ""%>" href="MainController?id=<%=item.getId()%>&op=completar"></a></td>
                        <td><a class="color-azul" href="MainController?id=<%=item.getId()%>&op=editar">Editar</a></td>
                        <td><a class="color-rojo" href="MainController?id=<%=item.getId()%>&op=eliminar">Eliminar</a></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>
            <%
                } else {
            %>
            <p class="alerta fondo-rojo">Aun no tiene tareas asignadas, cree una nueva tarea <a href="MainController?op=nuevo">aquí</a>.</p>
            <% 
                }
            %>
        </section>
        <footer>
            © 2021 - Todos los derechos reservados
        </footer>
    </body>
</html>
