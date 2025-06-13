package org.sci.serviciolibros.controller;

import org.sci.serviciolibros.model.Prestamo;
import org.sci.serviciolibros.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;

    @Autowired
    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<Prestamo> solicitarPrestamo(@RequestBody Map<String, String> body) {
        Long usuarioId = Long.parseLong(body.get("usuarioId"));
        Long libroId = Long.parseLong(body.get("libroId"));
        LocalDate fechaEntrega = LocalDate.parse(body.get("fechaEntrega"));
        return ResponseEntity.ok(prestamoService.solicitarPrestamo(usuarioId, libroId, fechaEntrega));
    }

    @PostMapping("/devolver/{prestamoId}")
    public ResponseEntity<Prestamo> devolverLibro(@PathVariable Long prestamoId) {
        return ResponseEntity.ok(prestamoService.devolverLibro(prestamoId));
    }

    @GetMapping
    public List<Prestamo> listarPrestamos() {
        return prestamoService.listarPrestamos();
    }
}

