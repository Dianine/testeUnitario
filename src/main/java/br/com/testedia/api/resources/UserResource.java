package br.com.testedia.api.resources;

import br.com.testedia.api.domain.User;
import br.com.testedia.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserResource {
    @Autowired
    private UserService service;

    @GetMapping(value = "/{id}")
    public Optional<User> findById(@PathVariable ("id") Integer id){
        return service.findById(id);

    }
}
