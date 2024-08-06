package br.com.ifba.weiv.web.controllers;



import br.com.ifba.weiv.domain.dto.UserGetResponseDto;
import br.com.ifba.weiv.domain.dto.UserPostRequestDto;
import br.com.ifba.weiv.domain.entity.Users;
import br.com.ifba.weiv.domain.service.UserService;
import br.com.ifba.weiv.web.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ObjectMapperUtil objectMapperUtil;

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>findAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(
                        this.userService.findAll(),
                        UserGetResponseDto.class));
    }

    @GetMapping(path = "/findByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>findByName(@RequestBody @Valid UserGetResponseDto data){
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(
                        this.userService.findByName(data.getName()),
                        UserGetResponseDto.class));
    }

    @GetMapping(path = "/findByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>findByEmail(@RequestBody @Valid UserGetResponseDto data){
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.map(
                        this.userService.findByEmail(data.getEmail()),
                        UserGetResponseDto.class));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserGetResponseDto> findById(@PathVariable Long id) {
        Users users = userService.findById(id);
            UserGetResponseDto responseDto = objectMapperUtil.map(users, UserGetResponseDto.class);
            return ResponseEntity.ok(responseDto);
        }


    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid UserPostRequestDto userPostRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(userService.save(
                        (objectMapperUtil.map(userPostRequestDto, Users.class))),
                        UserGetResponseDto.class));
    }

    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserPostRequestDto userPostRequestDto) {
        Users users = objectMapperUtil.map(userPostRequestDto, Users.class);
        userService.updateUser(users);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.deleteUser(id));
    }
}
