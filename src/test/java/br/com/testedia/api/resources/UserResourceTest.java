package br.com.testedia.api.resources;

import br.com.testedia.api.domain.User;
import br.com.testedia.api.domain.dto.UserDTO;
import br.com.testedia.api.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserResourceTest {

    public static final Integer ID      = 1;
    public static final String NAME     = "Dianine";
    public static final String EMAIL    = "dianine@gmail.com";
    public static final String PASSWORD = "123";



    @InjectMocks
    private UserResource resource;

    @Mock
    private ModelMapper mapper;

    @Mock
    private UserService service;
    private User user;
    private UserDTO userDTO;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void create() {
    }

    @Test
    void findAll() {
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        Mockito.when(service.findById(Mockito.anyInt())).thenReturn(user);
        Mockito.when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(UserDTO.class, response.getBody().getClass());

        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(NAME, response.getBody().getName());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);

    }
}