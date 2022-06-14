package br.com.testedia.api.services;

import br.com.testedia.api.domain.User;
import br.com.testedia.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Optional<User> findById(Integer id){
        return repository.findById(id);
    }

}
