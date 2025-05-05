package br.com.fiap.checkpoint3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.checkpoint3.dto.PacienteRequestCreate;
import br.com.fiap.checkpoint3.dto.PacienteRequestUpdate;
import br.com.fiap.checkpoint3.model.Paciente;
import br.com.fiap.checkpoint3.repository.PacienteRepository;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    // Criar paciente
    public Paciente createPaciente(PacienteRequestCreate dto) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.getNome());
        paciente.setEndereco(dto.getEndereco());
        paciente.setBairro(dto.getBairro());
        paciente.setEmail(dto.getEmail());
        paciente.setTelefone(dto.getTelefone());
        return pacienteRepository.save(paciente);
    }

    // Atualizar paciente
    public Optional<Paciente> updatePaciente(Long id, PacienteRequestUpdate dto) {
        return pacienteRepository.findById(id)
            .map(paciente -> {
                paciente.setNome(dto.getNome());
                paciente.setEndereco(dto.getEndereco());
                paciente.setBairro(dto.getBairro());
                paciente.setEmail(dto.getEmail());
                paciente.setTelefone(dto.getTelefone());
                return pacienteRepository.save(paciente);
            });
    }

    // Deletar paciente
    public boolean deletePaciente(Long id) {
        if (pacienteRepository.existsById(id)) {
            pacienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar por ID
    public Optional<Paciente> getPacienteById(Long id) {
        return pacienteRepository.findById(id);
    }

    // Listar todos
    public List<Paciente> getAll() {
        return pacienteRepository.findAll();
    }
}
