package med.voll.api.domain.consulta.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsulta{

    private final PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) throws ValidacaoException {
        boolean isPacienteAtivo = pacienteRepository.findAtivoById(dadosAgendamentoConsulta.idPaciente());
        if (!isPacienteAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente inativo");
        }
    }
}
