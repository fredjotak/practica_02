package com.emergentes.modelo;

public class Tarea {
    private int id;
    private String titulo;
    private Prioridad prioridad;
    private boolean completado;
    
    public Tarea(){
        this.id = 0;
        this.titulo = "";
        this.prioridad = new Prioridad();
        this.completado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}