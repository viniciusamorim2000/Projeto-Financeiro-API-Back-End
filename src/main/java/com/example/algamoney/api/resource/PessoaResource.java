package com.example.algamoney.api.resource;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.CategoriaRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Pessoa> listarTodos(){
        return pessoaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
      Pessoa pessoaSalva = pessoaRepository.save(pessoa);

      publisher.publishEvent(new RecursoCriadoEvent(this,response,pessoaSalva.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);

        if (!pessoa.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pessoa.get());
    }

}
