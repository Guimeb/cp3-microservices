package br.com.fiap.checkpoint3.controller;

import br.com.fiap.checkpoint3.dto.PacienteRequestCreate;
import br.com.fiap.checkpoint3.dto.PacienteRequestUpdate;
import br.com.fiap.checkpoint3.dto.PacienteResponse;
import br.com.fiap.checkpoint3.model.Paciente;
import br.com.fiap.checkpoint3.service.PacienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    // Create
    @PostMapping
    public ResponseEntity<PacienteResponse> createPaciente(@RequestBody PacienteRequestCreate dto) {
        Paciente paciente = pacienteService.createPaciente(dto);
        PacienteResponse response = toResponse(paciente);
        return ResponseEntity.created(URI.create("/pacientes/" + response.getId())).body(response);
    }

    // Put
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> updatePaciente(@PathVariable Long id, @RequestBody PacienteRequestUpdate dto) {
        Optional<Paciente> optional = pacienteService.updatePaciente(id, dto);

        if (optional.isPresent()) {
            return ResponseEntity.ok(toResponse(optional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        boolean deleted = pacienteService.deletePaciente(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build();   // 404
        }
    }

    // Get Id
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> getPacienteById(@PathVariable Long id) {
        Optional<Paciente> optional = pacienteService.getPacienteById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(toResponse(optional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get All
    @GetMapping
    public ResponseEntity<List<PacienteResponse>> getAll() {
        List<Paciente> pacientes = pacienteService.getAll();
        List<PacienteResponse> responseList = pacientes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    // DTO Response
    private PacienteResponse toResponse(Paciente paciente) {
        return new PacienteResponse(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEndereco(),
                paciente.getBairro(),
                paciente.getEmail(),
                paciente.getTelefoneCompleto()
        );
    }
}
