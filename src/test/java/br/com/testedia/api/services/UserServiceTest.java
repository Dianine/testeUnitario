package br.com.testedia.api.services;

import br.com.testedia.api.domain.User;
import br.com.testedia.api.domain.dto.UserDTO;
import br.com.testedia.api.repository.UserRepository;
import br.com.testedia.api.services.exception.DataIntegratyViolationException;
import br.com.testedia.api.services.exception.ObjNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    public static final Integer ID      = 1;
    public static final String NAME     = "Dianine";
    public static final String EMAIL    = "dianine@gmail.com";
    public static final String PASSWORD = "123";
    public static final String OBJETO_NÃO_ENCONTRADO = "Objeto não encontrado";
    public static final int INDEX = 0;
    public static final String E_MAIL_JA_CADASTRADO_NO_SISTEMA = "E-mail já Cadastrado no Sistema";
    public static final String OBJETO_NAO_ENCOSNTRADO = "Objeto não encosntrado";


    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.create(userDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PASSWORD, response.getPassword());


    }
    @Test
    void whenCreateThenReturnAnDataIntergrityVilationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

    try {
        optionalUser.get().setId(2); //Id existente é o 1, setado lá em baixo. Assim ele cai na exception
        service.create(userDTO);
    } catch (Exception ex) {
        Assertions.assertEquals(DataIntegratyViolationException.class, ex.getClass());
        Assertions.assertEquals(E_MAIL_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
    }


    }

    @Test
    void whwnFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(User.class, response.get(INDEX).getClass());

        Assertions.assertEquals(ID, response.get(INDEX).getId());
        Assertions.assertEquals(NAME, response.get(INDEX).getName());
        Assertions.assertEquals(EMAIL, response.get(INDEX).getEmail());
        Assertions.assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }

    @Test
    //Teste do ambiente positivo
    void whenfindByIdThenReturnAnUserInstance() {
        when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
    }
    @Test
    //Teste do ambiente negativo
    void whenfindByIdThenReturnAnObjectNotFoundException() {
        when(repository.findById(Mockito.anyInt())).thenThrow(new ObjNotFoundException(OBJETO_NÃO_ENCONTRADO));

        try {
            service.findById(ID);
        } catch (Exception ex) {
            Assertions.assertEquals(ObjNotFoundException.class, ex.getClass());
            Assertions.assertEquals(OBJETO_NÃO_ENCONTRADO, ex.getMessage());
        }

    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.update(userDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PASSWORD, response.getPassword());

    }
    @Test
    void whenUpdateThenReturnAnDataIntergrityVilationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2); //Id existente é o 1, setado lá em baixo. Assim ele cai na exception
            service.create(userDTO);
        } catch (Exception ex) {
            Assertions.assertEquals(DataIntegratyViolationException.class, ex.getClass());
            Assertions.assertEquals(E_MAIL_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }
    }
    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());

    }

    @Test
    void deleteWhiObjectNotFoundException(){
        when(repository.findById(anyInt())).thenThrow(new ObjNotFoundException(OBJETO_NAO_ENCOSNTRADO));
        try {
            service.delete(ID);
        } catch (Exception ex) {
            Assertions.assertEquals(ObjNotFoundException.class, ex.getClass());
            Assertions.assertEquals(OBJETO_NAO_ENCOSNTRADO, ex.getMessage());
        }
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}