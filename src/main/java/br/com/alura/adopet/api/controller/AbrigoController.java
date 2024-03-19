package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.abrigo.Abrigo;
import br.com.alura.adopet.api.model.abrigo.AbrigoDTOCadastrar;
import br.com.alura.adopet.api.model.abrigo.AbrigoDTOListar;
import br.com.alura.adopet.api.model.pet.PetDTOCadastrar;
import br.com.alura.adopet.api.model.pet.PetDTOListar;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AbrigoService abrigoService;
    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<AbrigoDTOListar>> listar() {

        return ResponseEntity.ok(abrigoService.listar());

    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid AbrigoDTOCadastrar dto) {

        try {
            abrigoService.cadastrar(dto);
            return ResponseEntity.ok().build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<PetDTOListar>> listarPets(@PathVariable String idOuNome) {

        try {
            return ResponseEntity.ok(abrigoService.listarPetsDoAbrigo(idOuNome));
        } catch (ValidacaoException e) {
            return  ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid PetDTOCadastrar petDto) {

        try {
            Abrigo abrigo = abrigoService.carregarAbrigo(idOuNome);
            petService.cadastrarPet(petDto, abrigo);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
