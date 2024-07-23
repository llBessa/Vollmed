package med.voll.api.domain.consulta.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidadorMedicoOcupadoNoHorario implements ValidadorAgendamentoConsulta{

    private final ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) throws ValidacaoException {
        boolean isMedicoOcupadoNoHorario = consultaRepository.existsByMedicoIdAndData(dadosAgendamentoConsulta.idMedico(), dadosAgendamentoConsulta.data());
        if (isMedicoOcupadoNoHorario) {
            throw new ValidacaoException("Médico já possui outra consulta marcada nesse horário");
        }
    }
}
