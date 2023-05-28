package com.example.cardapio.controller;

import com.example.cardapio.modelo.Food;
import com.example.cardapio.repository.FileObRepository;
import com.example.cardapio.repository.FoodRepository;
import com.example.cardapio.service.FileDbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import resources.FoodRequest;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoodController.class)
class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FoodRepository repository;
    @MockBean
    private FileObRepository fileObRepository;
    @MockBean
    private FileDbService fileDbService;

    @Test
    public void deveTrazerAComidaQuandoElaExistir() throws Exception {
        final String idFood = "123456";
        final Food food = new Food();
        food.setId(idFood);

        when(repository.findById(idFood)).thenReturn(Optional.of(food));

        this.mockMvc.perform(get("/food/" + idFood))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"123456\"}"));
    }

    @Test
    public void deveRetornar404QuandoAComidaNaoExistir() throws Exception {
        final String idFood = "123456";
        when(repository.findById(idFood)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/food/" + idFood))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveAtualizarAComidaQuandoElaExistir() throws Exception {
        final String idFood = "123456";
        final Food food = new Food();
        food.setId(idFood);
        final FoodRequest newFood = new FoodRequest();
        newFood.setName("Novidade");
        when(repository.findById(idFood)).thenReturn(Optional.of(food));
        when(repository.save(food)).thenReturn(food);

        String content = objectMapper.writeValueAsString(newFood);
        this.mockMvc.perform(put("/food/" + idFood).content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"123456\",\"name\":\"Novidade\"}"));
    }

    @Test
    public void deveDevolverErroSeONomeDaComidaNaoForInformado() throws Exception {
        final String idFood = "123456";
        final Food food = new Food();
        food.setId(idFood);
        final FoodRequest newFood = new FoodRequest();

        when(repository.findById(idFood)).thenReturn(Optional.of(food));
        when(repository.save(food)).thenReturn(food);

        String content = objectMapper.writeValueAsString(newFood);
        this.mockMvc.perform(put("/food/" + idFood).content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
        newFood.setName("");
        content = objectMapper.writeValueAsString(newFood);
        this.mockMvc.perform(put("/food/" + idFood).content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveManterADescricaoAntigaSeANovaEstiverVazia() throws Exception {
        final String idFood = "123456";
        final String oldDescriptor = "Desc old";
        final Food food = new Food();
        food.setId(idFood);
        food.setDescriptor(oldDescriptor);
        final FoodRequest newFood = new FoodRequest();
        newFood.setName("Novidade");
        when(repository.findById(idFood)).thenReturn(Optional.of(food));
        when(repository.save(food)).thenReturn(food);

        String content = objectMapper.writeValueAsString(newFood);
        this.mockMvc.perform(put("/food/" + idFood).content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{\"id\":\"123456\",\"name\":\"Novidade\",\"descriptor\":\"Desc old\"}"));
    }

    @Test
    public void naoPodeCadastrarFoodComNomeEDescriptorNulo() throws Exception{
        final String nome = null;
        final String descriptor = null;
        final FoodRequest food = new FoodRequest();
        food.setName(nome);
        food.setDescriptor(descriptor);
        String content = objectMapper.writeValueAsString(food);
        this.mockMvc.perform(post("/food").content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
