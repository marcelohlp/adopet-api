package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.tutor.Tutor;
import br.com.alura.adopet.api.model.tutor.TutorDTOCadastrar;
import br.com.alura.adopet.api.model.tutor.TutorDTOCompleto;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid TutorDTOCadastrar dto) {

        try {
            tutorService.cadastrar(dto);
            return ResponseEntity.ok().build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid TutorDTOCompleto dto) {

        tutorService.atualizar(dto);
        return ResponseEntity.ok().build();

    }

}
