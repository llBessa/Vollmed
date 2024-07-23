package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

public interface ValidacaoCancelamentoConsulta {

    public void validar(DadosCancelamentoConsulta dados) throws ValidacaoException;
}
