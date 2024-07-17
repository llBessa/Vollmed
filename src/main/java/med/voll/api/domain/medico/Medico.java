package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Medico(DadosCadastroMedico medico) {
        this.ativo = true;
        this.nome = medico.nome();
        this.email = medico.email();
        this.telefone = medico.telefone();
        this.crm = medico.crm();
        this.especialidade = medico.especialidade();
        this.endereco = new Endereco(medico.endereco());
    }

    public void atualizarDados(DadosAtualizacaoMedico dados) {
        if (dados.nome() != null){
            this.setNome(dados.nome());
        }
        if (dados.telefone() != null){
            this.setEmail(dados.telefone());
        }
        if (dados.endereco() != null){
            this.endereco.atualizarDados(dados.endereco());
        }
    }

    public void deletar() {
        this.ativo = false;
    }
}
