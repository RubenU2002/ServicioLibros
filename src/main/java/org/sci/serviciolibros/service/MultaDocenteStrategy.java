package org.sci.serviciolibros.service;

class MultaDocenteStrategy implements MultaStrategy {
    @Override
    public double calcularMulta(int diasRetraso) {
        return diasRetraso * 5000;
    }
}
