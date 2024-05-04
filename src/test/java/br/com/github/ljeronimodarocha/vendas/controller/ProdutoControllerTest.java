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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.github.ljeronimodarocha.vendas.MocksProduto;
import br.com.github.ljeronimodarocha.vendas.services.ProdutoServices;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest extends MocksProduto {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ProdutoController controller;

    @MockBean
    private ProdutoServices services;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        when(services.listar(any())).thenReturn(Collections.singletonList(mockProduto()));
        when(services.salvar(any())).thenReturn(mockProduto());
        when(services.buscaPeloId(anyLong())).thenReturn(mockProduto());
    }

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void deveRetornarListaProdutos() throws Exception {
        this.mockMvc.perform(get("/api/produtos").contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(services, times(1)).listar(any());
    }

    @Test
    void deveSalvarNovoProduto() throws Exception {
        this.mockMvc.perform(post("/api/produtos").contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .content(mapper.writeValueAsString(mockProdutoRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists());
        verify(services, times(1)).salvar(any());
    }

    @Test
    void deveAtualizarProduto() throws Exception {
        this.mockMvc.perform(
                put("/api/produtos/{id}", 1L)
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockProdutoRequest())))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
        verify(services, times(1)).atualizar(anyLong(), any());
    }

    @Test
    void deveExcluirProduto() throws Exception {
        this.mockMvc.perform(
                delete("/api/produtos/{id}", 1L)
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
        verify(services, times(1)).excluir(anyLong());
    }
}
