package org.sci.serviciolibros.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class Multa {
    private Long id;
    private int diasRetraso;
    private double monto;

    @JsonBackReference
    private Prestamo prestamo;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getDiasRetraso() { return diasRetraso; }
    public void setDiasRetraso(int diasRetraso) { this.diasRetraso = diasRetraso; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public Prestamo getPrestamo() { return prestamo; }
    public void setPrestamo(Prestamo prestamo) { this.prestamo = prestamo; }
}

