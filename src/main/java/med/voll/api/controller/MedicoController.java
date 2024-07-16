package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public String cadastrar(@RequestBody @Valid DadosCadastroMedico dadosMedico){
        repository.save(new Medico(dadosMedico));
        return "Médico criado com sucesso!";
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }

    @GetMapping(params = "id")
    public DadosListagemMedico listarMedicoPorId(@RequestParam(name = "id") Long id){
        Optional<Medico> medico = repository.findById(id);
        if(medico.isPresent()){
            return medico.map(DadosListagemMedico::new).get();
        }
        return null;
    }

    @PutMapping
    @Transactional
    public String atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        Optional<Medico> medico = repository.findById(dados.id());
        if (medico.isPresent()){
            medico.get().atualizarDados(dados);
            return "Dados do médico foram atualizados com sucesso!";
        }
        return "Médico não encontrado!";
    }
}
