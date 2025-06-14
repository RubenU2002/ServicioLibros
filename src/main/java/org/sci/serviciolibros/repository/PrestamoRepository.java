package org.sci.serviciolibros.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.sci.serviciolibros.model.Prestamo;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PrestamoRepository implements IPrestamoRepository {

    private final ObjectMapper mapper;
    private final Path file;

    public PrestamoRepository() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.file = Paths.get("data", "prestamos.txt");
        try {
            Files.createDirectories(file.getParent());
            if (!Files.exists(file)) {
                Files.createFile(file);
                writeAll(new ArrayList<>());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Prestamo> readAll() {
        try {
            if (!Files.exists(file) || Files.size(file) == 0) {
                return new ArrayList<>();
            }
            return mapper.readValue(Files.newBufferedReader(file),
                    new TypeReference<List<Prestamo>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeAll(List<Prestamo> prestamos) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), prestamos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Prestamo save(Prestamo prestamo) {
        List<Prestamo> prestamos = readAll();
        if (prestamo.getId() == null) {
            long nextId = prestamos.stream()
                    .mapToLong(p -> p.getId() == null ? 0 : p.getId())
                    .max().orElse(0L) + 1;
            prestamo.setId(nextId);
            prestamos.add(prestamo);
        } else {
            boolean replaced = false;
            for (int i = 0; i < prestamos.size(); i++) {
                if (prestamos.get(i).getId().equals(prestamo.getId())) {
                    prestamos.set(i, prestamo);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                prestamos.add(prestamo);
            }
        }
        writeAll(prestamos);
        return prestamo;
    }

    @Override
    public List<Prestamo> findAll() {
        return readAll();
    }

    @Override
    public Optional<Prestamo> findById(Long id) {
        return readAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        List<Prestamo> prestamos = readAll();
        prestamos.removeIf(p -> p.getId().equals(id));
        writeAll(prestamos);
    }
}
