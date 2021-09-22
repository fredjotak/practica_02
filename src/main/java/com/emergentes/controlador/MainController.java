package com.emergentes.controlador;

import com.emergentes.modelo.Tarea;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        
        if(sesion.getAttribute("listaTareas")==null){
            sesion.setAttribute("listaTareas", new ArrayList<Tarea>());
        }
        
        ArrayList<Tarea> listaTareas = (ArrayList<Tarea>)sesion.getAttribute("listaTareas");
        String op = request.getParameter("op");
        String opcion = op!=null? op: "vista";
        Tarea tarea = new Tarea();
        int id, posicion;
        System.out.println(opcion);
        switch(opcion){
            case "nuevo":
                request.setAttribute("miTarea", tarea);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "editar":
                id = Integer.parseInt(request.getParameter("id"));
                posicion = buscarIndice(request, id);
                tarea = listaTareas.get(posicion);
                request.setAttribute("miTarea", tarea);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "completar":
                id = Integer.parseInt(request.getParameter("id"));
                posicion = buscarIndice(request, id);
                if (listaTareas.get(posicion).isCompletado()){
                    listaTareas.set(posicion, listaTareas.get(posicion)).setCompletado(false);
                } else {
                    listaTareas.set(posicion, listaTareas.get(posicion)).setCompletado(true);
                }
                sesion.setAttribute("listaTareas", listaTareas);
                response.sendRedirect("index.jsp");
                break;
            case "eliminar":
                id = Integer.parseInt(request.getParameter("id"));
                posicion = buscarIndice(request, id);
                listaTareas.remove(posicion);
                sesion.setAttribute("listaTareas", listaTareas);
            default:
                response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        ArrayList<Tarea> listaTareas = (ArrayList<Tarea>)sesion.getAttribute("listaTareas");
        
        Tarea tarea = new Tarea();
        
        tarea.setId(Integer.parseInt(request.getParameter("hdnId")));
        tarea.setTitulo(request.getParameter("txtNombreTarea"));
        tarea.setPrioridad(request.getParameter("cbPrioridad"));
        tarea.setCompletado(Boolean.parseBoolean(request.getParameter("hdnCompletado")));
        // Por defecto el completado se mantiene, por defecto no está completado
        
        int idActual = tarea.getId();
        if(idActual==0){
            // Nueva Tarea
            tarea.setId(ultimoId(request));
            listaTareas.add(tarea);
        } else {
            // Actualizar la tarea
            listaTareas.set(buscarIndice(request, idActual), tarea);
        }
        
        sesion.setAttribute("listaTareas", listaTareas);
        response.sendRedirect("index.jsp");
    }
    
    private int buscarIndice(HttpServletRequest request, int id){
        HttpSession sesion = request.getSession();
        ArrayList<Tarea> lista = (ArrayList<Tarea>)sesion.getAttribute("listaTareas");
        int i = 0;
        if(lista.size() > 0){
            while (i<lista.size()){
                if (lista.get(i).getId() == id){
                    break;
                } else {
                    i++;
                }
            }
        }
        return i;
    }
    
    private int ultimoId(HttpServletRequest request){
        HttpSession sesion = request.getSession();
        ArrayList<Tarea> lista = (ArrayList<Tarea>)sesion.getAttribute("listaTareas");
        int idAux = 0;
        /*
        for(Tarea tareas: lista){
            idAux = tareas.getId();
        }*/
        if( lista.size() > 0){
            idAux = lista.get(lista.size()-1).getId();
        }
        return idAux+1;
    }
    
}