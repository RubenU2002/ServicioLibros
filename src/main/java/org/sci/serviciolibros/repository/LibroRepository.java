package org.sci.serviciolibros.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.sci.serviciolibros.model.Libro;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LibroRepository implements ILibroRepository {

    private final ObjectMapper mapper;
    private final Path file;

    public LibroRepository() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.file = Paths.get("data", "libros.txt");
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

    private List<Libro> readAll() {
        try {
            if (!Files.exists(file) || Files.size(file) == 0) {
                return new ArrayList<>();
            }
            return mapper.readValue(Files.newBufferedReader(file),
                    new TypeReference<List<Libro>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeAll(List<Libro> libros) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), libros);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Libro save(Libro libro) {
        List<Libro> libros = readAll();
        if (libro.getId() == null) {
            long nextId = libros.stream()
                    .mapToLong(l -> l.getId() == null ? 0 : l.getId())
                    .max().orElse(0L) + 1;
            libro.setId(nextId);
            libros.add(libro);
        } else {
            boolean replaced = false;
            for (int i = 0; i < libros.size(); i++) {
                if (libros.get(i).getId().equals(libro.getId())) {
                    libros.set(i, libro);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                libros.add(libro);
            }
        }
        writeAll(libros);
        return libro;
    }

    @Override
    public List<Libro> findAll() {
        return readAll();
    }

    @Override
    public Optional<Libro> findById(Long id) {
        return readAll().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        List<Libro> libros = readAll();
        libros.removeIf(l -> l.getId().equals(id));
        writeAll(libros);
    }
}
