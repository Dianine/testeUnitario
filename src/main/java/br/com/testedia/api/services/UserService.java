package br.com.testedia.api.services;

import br.com.testedia.api.domain.User;
import br.com.testedia.api.domain.dto.UserDTO;
import br.com.testedia.api.repository.UserRepository;
import br.com.testedia.api.services.exception.DataIntegratyViolationException;
import br.com.testedia.api.services.exception.ObjNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    public User create (UserDTO obj){
        finfByEmail(obj);                                                                   //Aplicação da regra abaixo
        return repository.save(mapper.map(obj, User.class));
    }
    //Verifica se Email já existe no Banco
    private void finfByEmail(UserDTO obj){
        Optional<User> user = repository.findByEmail(obj.getEmail());
        if(user.isPresent() && !user.get().getId().equals(obj.getId())){
            throw new DataIntegratyViolationException("E-mail já Cadastrado no Sistema");
        }

    }
    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjNotFoundException("Objeto não encontrado"));
      }

     public User update(UserDTO obj){
        finfByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
     }

    public void delete (Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}
