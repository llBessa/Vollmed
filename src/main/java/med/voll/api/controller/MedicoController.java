package med.voll.api.controller;

import med.voll.api.controller.medico.DadosCadastroMedico;
import med.voll.api.controller.medico.Medico;
import med.voll.api.controller.medico.MedicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    private static final Logger logger = LoggerFactory.getLogger(MedicoController.class);

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    public String cadastrar(@RequestBody DadosCadastroMedico medico){
        repository.save(new Medico(medico));
        return "Médico criado com sucesso!";
    }
}
