package med.voll.api.domain.consulta.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsulta{

    private final MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) throws ValidacaoException {
        if (dadosAgendamentoConsulta.idMedico() == null) {
            return;
        }

        boolean isMedicoAtivo = medicoRepository.findAtivoById(dadosAgendamentoConsulta.idMedico());
        if (!isMedicoAtivo) {
            throw new ValidacaoException("A consulta não pode ser agendada com um médico inativo!");
        }
    }
}
