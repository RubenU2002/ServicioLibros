package org.sci.serviciolibros.service;

public class MultaEstudianteStrategy implements MultaStrategy {
    @Override
    public double calcularMulta(int diasRetraso) {
        return diasRetraso * 1000;
    }
}
