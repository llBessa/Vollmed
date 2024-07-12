package med.voll.api.controller.paciente;

import med.voll.api.controller.endereco.DadosEndereco;

public record DadosCadastroPaciente(String nome, String email, String telefone, String cpf, DadosEndereco endereco) {
}
