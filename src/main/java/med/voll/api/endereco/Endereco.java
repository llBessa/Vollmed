package med.voll.api.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;

    public Endereco(DadosEndereco endereco) {
        this.logradouro = endereco.logradouro();
        this.bairro = endereco.bairro();
        this.cep = endereco.cep();
        this.numero = endereco.numero();
        this.complemento = endereco.complemento();
        this.cidade = endereco.cidade();
        this.uf = endereco.uf();
    }

    public void atualizarDados(DadosEndereco dados) {
        if (dados.logradouro() != null){
            this.setLogradouro(dados.logradouro());
        }
        if (dados.bairro() != null){
            this.setBairro(dados.bairro());
        }
        if (dados.cep() != null){
            this.setCep(dados.cep());
        }
        if (dados.numero() != null){
            this.setNumero(dados.numero());
        }
        if (dados.complemento() != null){
            this.setComplemento(dados.complemento());
        }
        if (dados.cidade() != null){
            this.setCidade(dados.cidade());
        }
        if (dados.uf() != null){
            this.setUf(dados.uf());
        }
    }
}
