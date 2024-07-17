package med.voll.api.paciente;

public record DadosListagemPaciente(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf
) {

    public DadosListagemPaciente(Paciente dados){
        this(dados.getId(), dados.getNome(), dados.getEmail(), dados.getTelefone(), dados.getCpf());
    }
}
