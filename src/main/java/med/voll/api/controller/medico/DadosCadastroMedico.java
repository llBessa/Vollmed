package med.voll.api.controller.medico;

import med.voll.api.controller.endereco.DadosEndereco;

public record DadosCadastroMedico(String nome, String email, String telefone, String CRM, Especialidade especialidade, DadosEndereco endereco) {
}
