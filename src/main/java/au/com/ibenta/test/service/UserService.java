package au.com.ibenta.test.service;

import au.com.ibenta.test.exception.UserNotFoundException;
import au.com.ibenta.test.persistence.UserEntity;
import au.com.ibenta.test.persistence.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RestController
@RequestMapping("/users")
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<UserEntity> get(@PathVariable Long id) throws UserNotFoundException {
        if (repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.FOUND).body(repository.getOne(id));
        } else {
            throw new UserNotFoundException(String.valueOf(id));
        }
    }

    @PostMapping(path = "/create")
    public ResponseEntity<UserEntity> create(@RequestBody UserEntity user) {

        UserEntity entity = mapper.convertValue(user, UserEntity.class);
        entity.setPassword(encoder.encode(entity.getPassword()));

        repository.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws UserNotFoundException {

        int status = HttpStatus.NOT_FOUND.value();

        if (repository.existsById(id)) {
            repository.deleteById(id);
            status = HttpStatus.OK.value();
        } else  {
            throw new UserNotFoundException(String.valueOf(id));
        }

        return ResponseEntity.status(status).body(null);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<UserEntity> update(@PathVariable Long id, @RequestBody UserEntity user)
            throws UserNotFoundException {

        UserEntity entityForUpdate = repository.existsById(id) ? repository.getOne(id) : null;
        if (entityForUpdate == null) {
            throw new UserNotFoundException(String.valueOf(id));
        }

        user.setId(entityForUpdate.getId());
        user.setPassword(encoder.encode(user.getPassword()));

        repository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping(path = "/list")
    public List<UserEntity> list() {

        return repository.findAll();
    }


}
