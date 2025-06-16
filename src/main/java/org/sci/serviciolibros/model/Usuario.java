package org.sci.serviciolibros.model;


public class Usuario {
    private Long id;
    private String nombre;
    private Rol rol;

    public enum Rol {
        DOCENTE, ESTUDIANTE, EMPLEADO
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}

