package org.sci.serviciolibros.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.sci.serviciolibros.model.Usuario;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository implements IUsuarioRepository {

    private final ObjectMapper mapper;
    private final Path file;

    public UsuarioRepository() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.file = Paths.get("data", "usuarios.txt");
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

    private List<Usuario> readAll() {
        try {
            if (!Files.exists(file) || Files.size(file) == 0) {
                return new ArrayList<>();
            }
            return mapper.readValue(Files.newBufferedReader(file),
                    new TypeReference<List<Usuario>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeAll(List<Usuario> usuarios) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), usuarios);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario save(Usuario usuario) {
        List<Usuario> usuarios = readAll();
        if (usuario.getId() == null) {
            long nextId = usuarios.stream()
                    .mapToLong(u -> u.getId() == null ? 0 : u.getId())
                    .max().orElse(0L) + 1;
            usuario.setId(nextId);
            usuarios.add(usuario);
        } else {
            boolean replaced = false;
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getId().equals(usuario.getId())) {
                    usuarios.set(i, usuario);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                usuarios.add(usuario);
            }
        }
        writeAll(usuarios);
        return usuario;
    }

    @Override
    public List<Usuario> findAll() {
        return readAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return readAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        List<Usuario> usuarios = readAll();
        usuarios.removeIf(u -> u.getId().equals(id));
        writeAll(usuarios);
    }
}
