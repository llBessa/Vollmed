package med.voll.api.domain.consulta.validacoes;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoConsulta{

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) throws ValidacaoException {
        LocalDateTime dataConsulta = dadosAgendamentoConsulta.data();

        boolean isDomingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isAntesDaAbertura = dataConsulta.getHour() < 7;
        boolean isDepoisDoEncerramento = dataConsulta.getHour() > 18;

        if (isDomingo || isAntesDaAbertura || isDepoisDoEncerramento) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
