package br.com.github.ljeronimodarocha.vendas.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.github.ljeronimodarocha.vendas.MocksEstoque;
import br.com.github.ljeronimodarocha.vendas.services.EstoqueServices;

@WebMvcTest(EstoqueController.class)
class EstoqueControllerTest extends MocksEstoque {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private EstoqueController controller;

    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private EstoqueServices services;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        when(services.listar(any())).thenReturn(Collections.singletonList(mockEstoque()));
        when(services.salvar(any())).thenReturn(mockEstoque());
        when(services.buscaPeloId(anyLong())).thenReturn(mockEstoque());
    }

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    @WithMockUser(roles = "user")
    void deveRetornarListaEstoque() throws Exception {
        this.mockMvc
                .perform(get("/api/estoque").contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(services, times(1)).listar(any());
    }

    @Test
    void deveSalvarNovoEstoque() throws Exception {
        this.mockMvc.perform(post("/api/estoque").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockEstoqueRequest()))
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists());
        verify(services, times(1)).salvar(any());
    }

    @Test
    void deveAtualizarEstoque() throws Exception {
        this.mockMvc.perform(
                put("/api/estoque/{id}", 1L)
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockEstoqueRequest())))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
        verify(services, times(1)).atualizar(anyLong(), any());
    }

    @Test
    void deveExcluirEstoque() throws Exception {
        this.mockMvc.perform(
                delete("/api/estoque/{id}", 1L)
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
        verify(services, times(1)).excluir(anyLong());
    }

}
