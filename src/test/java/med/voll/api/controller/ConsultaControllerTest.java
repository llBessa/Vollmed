package med.voll.api.controller;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.service.ConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> agendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> detalhamentoJson;

    @MockBean
    private ConsultaService consultaService;

    @Test
    @DisplayName("Deveria devolver o codigo 400 quando as informacoes enviadas são inválidas")
    @WithMockUser
    void agendamentoCenario1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/consultas")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver o codigo 200 quando as informacoes enviadas são válidas")
    @WithMockUser
    void agendamentoCenario2() throws Exception, ValidacaoException {

        LocalDateTime dataDaqui2Horas = LocalDateTime.now().plusHours(2);

        DadosDetalhamentoConsulta dadosDetalhamento = new DadosDetalhamentoConsulta(null, 1L, 1L, dataDaqui2Horas);

        when(consultaService.agendar(any())).thenReturn(dadosDetalhamento);

        MockHttpServletResponse response = mockMvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agendamentoConsultaJson.write(new DadosAgendamentoConsulta(1L, 1L, dataDaqui2Horas, Especialidade.CARDIOLOGIA))
                        .getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String jsonEsperado = detalhamentoJson.write(dadosDetalhamento).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}