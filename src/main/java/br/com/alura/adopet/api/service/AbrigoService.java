package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.abrigo.Abrigo;
import br.com.alura.adopet.api.model.abrigo.AbrigoDTOCadastrar;
import br.com.alura.adopet.api.model.abrigo.AbrigoDTOListar;
import br.com.alura.adopet.api.model.pet.PetDTOListar;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.validation.abrigo.ValidarCadastroAbrigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private List<ValidarCadastroAbrigo> validarCadastroAbrigos;

    public List<AbrigoDTOListar> listar() {

        List<Abrigo> listaAbrigos = abrigoRepository.findAll();

        return listaAbrigos
                .stream()
                .map(AbrigoDTOListar::new)
                .toList();

    }

    public void cadastrar(AbrigoDTOCadastrar dto) {

        validarCadastroAbrigos.forEach(validador -> validador.validar(dto));

        Abrigo abrigo = new Abrigo(dto.nome(), dto.telefone(), dto.email());
        abrigoRepository.save(abrigo);

    }

    public List<PetDTOListar> listarPetsDoAbrigo(String idOuNome) {

        Abrigo abrigo = carregarAbrigo(idOuNome);

        return petRepository
                .findByAbrigo(abrigo)
                .stream()
                .map(PetDTOListar::new)
                .toList();
    }

    public Abrigo carregarAbrigo(String idOuNome) {

        Optional<Abrigo> optional;

        try {
            Long id = Long.parseLong(idOuNome);
            optional = abrigoRepository.findById(id);
        } catch (NumberFormatException exception) {
            optional = abrigoRepository.findByNome(idOuNome);
        }

        return optional.orElseThrow(() -> new ValidacaoException("Abrigo não encontrado"));

    }

}
