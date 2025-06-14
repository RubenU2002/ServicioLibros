package org.sci.serviciolibros.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.sci.serviciolibros.model.Multa;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MultaRepository implements IMultaRepository {

    private final ObjectMapper mapper;
    private final Path file;

    public MultaRepository() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.file = Paths.get("data", "multas.txt");
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

    private List<Multa> readAll() {
        try {
            if (!Files.exists(file) || Files.size(file) == 0) {
                return new ArrayList<>();
            }
            return mapper.readValue(Files.newBufferedReader(file),
                    new TypeReference<List<Multa>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeAll(List<Multa> multas) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), multas);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Multa save(Multa multa) {
        List<Multa> multas = readAll();
        if (multa.getId() == null) {
            long nextId = multas.stream()
                    .mapToLong(m -> m.getId() == null ? 0 : m.getId())
                    .max().orElse(0L) + 1;
            multa.setId(nextId);
            multas.add(multa);
        } else {
            boolean replaced = false;
            for (int i = 0; i < multas.size(); i++) {
                if (multas.get(i).getId().equals(multa.getId())) {
                    multas.set(i, multa);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                multas.add(multa);
            }
        }
        writeAll(multas);
        return multa;
    }

    @Override
    public List<Multa> findAll() {
        return readAll();
    }

    @Override
    public Optional<Multa> findById(Long id) {
        return readAll().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        List<Multa> multas = readAll();
        multas.removeIf(m -> m.getId().equals(id));
        writeAll(multas);
    }
}
