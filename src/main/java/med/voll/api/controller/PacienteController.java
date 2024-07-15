package med.voll.api.controller;

import med.voll.api.controller.paciente.DadosCadastroPaciente;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @PostMapping
    public String cadastrar(@RequestBody DadosCadastroPaciente paciente){
        System.out.println(paciente);
        return "Paciente criado!";
    }
}