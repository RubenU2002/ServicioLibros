package org.sci.serviciolibros.service;

import org.sci.serviciolibros.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MultaStrategyFactory {
    private final Map<Usuario.Rol, MultaStrategy> strategies = new HashMap<>();

    public MultaStrategyFactory() {
        strategies.put(Usuario.Rol.DOCENTE, new MultaDocenteStrategy());
        strategies.put(Usuario.Rol.ESTUDIANTE, new MultaEstudianteStrategy());
        strategies.put(Usuario.Rol.EMPLEADO, new MultaEmpleadoStrategy());
    }

    public MultaStrategy getStrategy(Usuario.Rol rol) {
        return strategies.get(rol);
    }
}

