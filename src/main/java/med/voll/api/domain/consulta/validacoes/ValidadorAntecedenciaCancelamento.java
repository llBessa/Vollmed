package med.voll.api.domain.consulta.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ValidadorAntecedenciaCancelamento implements ValidacaoCancelamentoConsulta{

    private final ConsultaRepository consultaRepository;

    public void validar(DadosCancelamentoConsulta dados) throws ValidacaoException {
        LocalDateTime dataAtual = LocalDateTime.now();
        LocalDateTime dataConsulta = consultaRepository.getReferenceById(dados.idConsulta()).getData();
        Long diferencaEmDias = Duration.between(dataAtual, dataConsulta).toDays();

        if (diferencaEmDias < 1){
            throw new ValidacaoException("A consulta somente pode ser cancelada com 24 horas ou mais de antecedência");
        }
    }
}
