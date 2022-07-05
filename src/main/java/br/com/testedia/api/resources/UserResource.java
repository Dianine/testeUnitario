package br.com.testedia.api.resources;

import br.com.testedia.api.domain.User;
import br.com.testedia.api.domain.dto.UserDTO;
import br.com.testedia.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")

public class UserResource {

    public static final String ID = "/{id}";
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService service;


    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj){
        User newObj = service.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path(ID)
                .buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        List<User> list = service.findAll();
        List<UserDTO> listDTO = list.stream().map(x -> mapper.map(x, UserDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
    @GetMapping(ID)
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDTO.class));
    }
    @PutMapping(ID)
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO obj){
        obj.setId(id); //Garantir que é o id certo
        return  ResponseEntity.ok().body(mapper.map(service.update(obj), UserDTO.class));
    }
    @DeleteMapping(ID)
    public ResponseEntity<UserDTO> delete (@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();

    }


}


/* Tornar ("/{id}") em uma constante: seleciona, refactor, constante,
marca primeira opção para todos ficarem iguais */