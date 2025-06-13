package org.sci.serviciolibros.service;

public class MultaEmpleadoStrategy implements MultaStrategy {
    @Override
    public double calcularMulta(int diasRetraso) {
        return diasRetraso * 2500;
    }
}
