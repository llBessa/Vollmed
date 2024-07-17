package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosPaciente, UriComponentsBuilder uriBuilder){
        Paciente paciente = repository.save(new Paciente(dadosPaciente));
        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        return ResponseEntity.ok(repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable(name = "id") Long id){
        if (repository.existsById(id)){
            Paciente paciente = repository.getReferenceById(id);
            return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados){
        if (repository.existsById(dados.id())){
            Paciente paciente = repository.getReferenceById(dados.id());
            paciente.atualizarDados(dados);
            return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> deletar(@PathVariable Long id){
        if (repository.existsById(id)){
            Paciente paciente = repository.getReferenceById(id);
            paciente.deletar();
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
