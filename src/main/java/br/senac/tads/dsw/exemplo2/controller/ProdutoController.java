package br.senac.tads.dsw.exemplo2.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.senac.tads.dsw.exemplo2.model.Produto;
import br.senac.tads.dsw.exemplo2.repository.ProdutoRepository;

@RestController 
@RequestMapping("/api/produtos")
public class ProdutoController {

private final ProdutoRepository repository;
public ProdutoController(ProdutoRepository repository) {
this.repository = repository;
}

@PostMapping 
public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
Produto produtoSalvo = repository.save(produto);
URI location = ServletUriComponentsBuilder
.fromCurrentRequest()
.path("/{id}")
.buildAndExpand(produtoSalvo.getId())
.toUri();
return ResponseEntity.created(location).body(produtoSalvo);
}
@GetMapping 
public List<Produto> listarTodos() {
return repository.findAll();
}

@GetMapping("/{id}")
public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
Optional<Produto> produtoBuscado = repository.findById(id);
if (produtoBuscado.isPresent()) {
return ResponseEntity.ok(produtoBuscado.get());
} else {
return ResponseEntity.notFound().build();
}
}

}

