package com.emergentes.controlador;

import com.emergentes.modelo.Prioridad;
import com.emergentes.modelo.Tarea;
import java.io.IOException;
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
        if(sesion.getAttribute("listaPrioridades")==null){
            ArrayList<Prioridad> listaPriorAux = new ArrayList<Prioridad>();
            listaPriorAux.add(new Prioridad(1, "Baja"));
            listaPriorAux.add(new Prioridad(2, "Media"));
            listaPriorAux.add(new Prioridad(3, "Alta"));
            // Cargamos las prioridades
            sesion.setAttribute("listaPrioridades", listaPriorAux);
        }
        
        ArrayList<Tarea> listaTareas = (ArrayList<Tarea>)sesion.getAttribute("listaTareas");
        
        String op = request.getParameter("op");
        String opcion = op!=null? op: "vista";
        Tarea tarea = new Tarea();
        int id, posicion;
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
        ArrayList<Prioridad> listaPrioridades = (ArrayList<Prioridad>)sesion.getAttribute("listaPrioridades");
        
        Tarea tarea = new Tarea();
        
        tarea.setId(Integer.parseInt(request.getParameter("hdnId")));
        tarea.setTitulo(request.getParameter("txtNombreTarea"));
         // Por defecto el completado se mantiene, por defecto no está completado
        tarea.setCompletado(Boolean.parseBoolean(request.getParameter("hdnCompletado")));
        try {
            int idActual = buscarIndicePrioridad(request, Integer.parseInt(request.getParameter("cbPrioridad")));
            tarea.setPrioridad(listaPrioridades.get(idActual));
        } catch(Exception e){
            tarea.setPrioridad(listaPrioridades.get(0)); // Si ocurre un error poner el primero
        }
        
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
    private int buscarIndicePrioridad(HttpServletRequest request, int id){
        HttpSession sesion = request.getSession();
        ArrayList<Prioridad> lista = (ArrayList<Prioridad>)sesion.getAttribute("listaPrioridades");
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
        if( lista.size() > 0){
            idAux = lista.get(lista.size()-1).getId();
        }
        return idAux+1;
    }
    
}