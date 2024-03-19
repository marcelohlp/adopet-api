package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.model.abrigo.Abrigo;
import br.com.alura.adopet.api.model.pet.Pet;
import br.com.alura.adopet.api.model.pet.PetDTOCadastrar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculadoraProbabilidadeAdocaoTest {

    @Test
    @DisplayName("Probabilidade ALTA para pets com peso baixo e idade baixa")
    void probabilidadeAlta() {

        //ARRANGE
        Abrigo abrigo = new Abrigo("Pet feliz", "19980650089", "petfeliz@email.com");
        PetDTOCadastrar petDto = new PetDTOCadastrar(TipoPet.GATO, "Miau", "Siames", 4, "Cinza", 4.0f);
        Pet pet = new Pet(petDto, abrigo);

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();

        //ACT
        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        //ASSERT
        Assertions.assertEquals(ProbabilidadeAdocao.ALTA, probabilidade);

    }

    @Test
    @DisplayName("Probabilidade MEDIA para pets com peso baixo e idade alta")
    void probabilidadeMedia() {

        Abrigo abrigo = new Abrigo("Pet feliz", "19980650089", "petfeliz@email.com");
        PetDTOCadastrar petDto = new PetDTOCadastrar(TipoPet.GATO, "Miau", "Siames", 15, "Cinza", 4.0f);
        Pet pet = new Pet(petDto, abrigo);

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();

        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        Assertions.assertEquals(ProbabilidadeAdocao.MEDIA, probabilidade);

    }

    @Test
    @DisplayName("Probabilidade BAIXA para pets com peso alto e idade alta")
    void probabilidadeBaixa() {

        Abrigo abrigo = new Abrigo("Pet feliz", "19980650089", "petfeliz@email.com");
        PetDTOCadastrar petDto = new PetDTOCadastrar(TipoPet.GATO, "Miau", "Siames", 15, "Cinza", 11.0f);
        Pet pet = new Pet(petDto, abrigo);

        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();

        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        Assertions.assertEquals(ProbabilidadeAdocao.BAIXA, probabilidade);

    }



}