package med.voll.api.domain.consulta.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ValidadorPacienteSemConsultaNoDia implements ValidadorAgendamentoConsulta{

    private final ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) throws ValidacaoException {
        LocalDateTime horaAbertua = dados.data().withHour(7);
        LocalDateTime horaEncerramento = dados.data().withHour(18);
        boolean pacientePossuiConsultaNoDia = consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), horaAbertua, horaEncerramento);

        if(pacientePossuiConsultaNoDia){
            throw new ValidacaoException("Paciente já possui consulta agendada nesse dia");
        }
    }
}
