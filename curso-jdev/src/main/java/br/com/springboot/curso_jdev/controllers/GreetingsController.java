package br.com.springboot.curso_jdev.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.springboot.curso_jdev.model.Usuario;
import br.com.springboot.curso_jdev.repository.UsuarioRepository;

@RestController

public class GreetingsController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/monstrarnome/{name}")
    public ResponseEntity<String> greetingText(@PathVariable String name) {
        return ResponseEntity.ok("Curso Spring Boot API: " + name + "!");
    }
    
    @GetMapping("/olamundo/{nome}")
    public ResponseEntity<String> returnaOlaMundo(@PathVariable String nome) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Ola mundo " + nome);
    }
    
    @GetMapping("/listatodos")
    public ResponseEntity<List<Usuario>> listaUsuario() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }
    
    @PostMapping("/salvar")
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        Usuario user = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {
        if (usuario.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("ID não foi informado para atualização.");
        }
        
        if (!usuarioRepository.existsById(usuario.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Usuário não encontrado.");
        }
        
        Usuario user = usuarioRepository.save(usuario);
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long iduser) {
        if (!usuarioRepository.existsById(iduser)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Usuário não encontrado.");
        }
        
        usuarioRepository.deleteById(iduser);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    } 
    
    @GetMapping("/buscaruserid")
    public ResponseEntity<?> buscaruserid(@RequestParam(name = "iduser") Long iduser) {
        Optional<Usuario> usuario = usuarioRepository.findById(iduser);
        
        if (!usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Usuário não encontrado.");
        }
        
        return ResponseEntity.ok(usuario.get());
    }
    
    @GetMapping("/buscarPorNome")
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) {
        List<Usuario> usuarios = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
        return ResponseEntity.ok(usuarios);
    }
}
