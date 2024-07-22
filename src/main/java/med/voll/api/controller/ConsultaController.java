package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.service.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) throws ValidacaoException {
        Consulta consulta = service.agendar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(
                consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData())
        );
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) throws ValidacaoException {
        service.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}