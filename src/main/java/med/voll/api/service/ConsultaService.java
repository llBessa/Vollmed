package med.voll.api.service;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.consulta.*;
import med.voll.api.domain.consulta.validacoes.ValidacaoCancelamentoConsulta;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    private final List<ValidadorAgendamentoConsulta> validadoresAgendamento;
    private final List<ValidacaoCancelamentoConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dadosAgendamento) throws ValidacaoException{
        if(!pacienteRepository.existsById(dadosAgendamento.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        if(dadosAgendamento.idMedico() != null && !medicoRepository.existsById(dadosAgendamento.idMedico())){
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        for (ValidadorAgendamentoConsulta validador : validadoresAgendamento) {
            validador.validar(dadosAgendamento);
        }

        Medico medico = escolheMedicoAleatorio(dadosAgendamento);

        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }

        Consulta consulta = new Consulta();
        consulta.setMedico(medico);
        consulta.setPaciente(pacienteRepository.getReferenceById(dadosAgendamento.idPaciente()));
        consulta.setData(dadosAgendamento.data());
        repository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolheMedicoAleatorio(DadosAgendamentoConsulta dadosAgendamento) throws ValidacaoException {
        if (dadosAgendamento.idMedico() != null){
            return medicoRepository.getReferenceById(dadosAgendamento.idMedico());
        }

        if (dadosAgendamento.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dadosAgendamento.especialidade(), dadosAgendamento.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) throws ValidacaoException {
        if (!repository.existsById(dados.idConsulta())){
            throw new ValidacaoException("Id da consulta não existe");
        }

        for (ValidacaoCancelamentoConsulta validador : validadoresCancelamento) {
            validador.validar(dados);
        }

        Consulta consulta = repository.getReferenceById(dados.idConsulta());
        consulta.setMotivoCancelamento(dados.motivo());
    }
}
