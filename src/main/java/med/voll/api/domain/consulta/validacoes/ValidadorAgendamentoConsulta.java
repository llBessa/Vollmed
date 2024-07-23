package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

public interface ValidadorAgendamentoConsulta {

    public void validar(DadosAgendamentoConsulta dados) throws ValidacaoException;
}
