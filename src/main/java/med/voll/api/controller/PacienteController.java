package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public String cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosPaciente){
        repository.save(new Paciente(dadosPaciente));
        return "Paciente criado com sucesso!";
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
    }

    @GetMapping(params = "id")
    public DadosListagemPaciente listarPacientePorId(@RequestParam(name = "id") Long id){
        Optional<Paciente> paciente = repository.findById(id);
        if (paciente.isPresent()){
            return paciente.map(DadosListagemPaciente::new).get();
        }
        return null;
    }

    @PutMapping
    @Transactional
    public String atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados){
        Optional<Paciente> paciente = repository.findById(dados.id());
        if (paciente.isPresent()){
            paciente.get().atualizarDados(dados);
            return "Dados do paciente atualizados com sucesso!";
        }
        return "Paciente não encontrado";
    }

    @DeleteMapping("/{id}")
    @Transactional
    public String deletar(@PathVariable Long id){
        Optional<Paciente> paciente = repository.findById(id);
        if (paciente.isPresent()){
            paciente.get().deletar();
            return "Paciente deletado do banco de dados";
        }
        return "Paciente não encontrado";
    }
}
