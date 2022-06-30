package expertostech.tutorial.rest.api.controller;

import expertostech.tutorial.rest.api.model.UsuarioModel;
import expertostech.tutorial.rest.api.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping(path = "/api/usuario/{codigo}")
    public ResponseEntity consultar(@PathVariable("codigo") Integer codigo) {
        return repository.findById(codigo)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/usuario/salvar")
    public UsuarioModel salvar(@RequestBody UsuarioModel usuario) {
        return repository.save(usuario);
    }

    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    public List<UsuarioModel> lsitarTodosUsuarios() {
        return (List<UsuarioModel>) repository.findAll();
    }

    @RequestMapping(value = "/usuario/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<UsuarioModel> atualizarUsuario(@PathVariable(value = "id") long id, @RequestBody UsuarioModel newusuario)
    {
        
        Optional<UsuarioModel> oldusuario = repository.findById((int) id);
        if(oldusuario.isPresent()){
            UsuarioModel usuario = oldusuario.get();
            usuario.setNome(newusuario.getNome());
            repository.save(usuario);
            return new ResponseEntity<UsuarioModel>(usuario, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deletarUsuario(@PathVariable(value = "id") long id)
    {
        Optional<UsuarioModel> usuario = repository.findById((int) id);
        if(usuario.isPresent()){
            repository.delete(usuario.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
