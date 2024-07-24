package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> jsonCadastroMedico;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> jsonDetalhamentoMedico;

    @MockBean
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Deveria retornar o codigo http 400 quando informacoes invalidas forem enviadas")
    @WithMockUser
    void cadastrarCenario1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/medicos")).andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar o codigo http 201 quando informacoes validas forem enviadas")
    @WithMockUser
    void cadastrarCenario2() throws Exception {

        DadosCadastroMedico dadosCadastroMedico = new DadosCadastroMedico(
                "Medico",
                "medico@voll.med",
                "95999999999",
                "456321",
                Especialidade.CARDIOLOGIA,
                criaEndereco()
        );

        when(medicoRepository.save(any())).thenReturn(new Medico(dadosCadastroMedico));

        MockHttpServletResponse response = mockMvc.perform(post("/medicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCadastroMedico.write(dadosCadastroMedico)
                .getJson())).andReturn().getResponse();

        DadosDetalhamentoMedico dadosDetalhamentoMedico = new DadosDetalhamentoMedico(
                null,
                dadosCadastroMedico.nome(),
                dadosCadastroMedico.email(),
                dadosCadastroMedico.telefone(),
                dadosCadastroMedico.crm(),
                dadosCadastroMedico.especialidade(),
                new Endereco(dadosCadastroMedico.endereco())
        );

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(response.getContentAsString(), jsonDetalhamentoMedico.write(dadosDetalhamentoMedico).getJson());
    }

    private DadosEndereco criaEndereco() {
        return new DadosEndereco(
                "Rua Teste",
                "23",
                "Casa",
                "Teste do Bairro",
                "Cidade do Teste",
                "RR",
                "51102560"
        );
    }
}