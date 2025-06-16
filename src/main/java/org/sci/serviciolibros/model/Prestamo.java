package org.sci.serviciolibros.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDate;

public class Prestamo {
    private Long id;

    private Usuario usuario;

    private Libro libro;

    private LocalDate fechaPrestamo;
    private LocalDate fechaEntrega;
    private LocalDate fechaDevolucion;

    private Multa multa;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public Multa getMulta() { return multa; }
    public void setMulta(Multa multa) { this.multa = multa; }
}

