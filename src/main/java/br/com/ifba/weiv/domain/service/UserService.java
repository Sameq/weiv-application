package br.com.ifba.weiv.domain.service;



import br.com.ifba.weiv.domain.dto.AuthDto;
import br.com.ifba.weiv.domain.entity.Users;
import br.com.ifba.weiv.domain.exception.BusinessException;
import br.com.ifba.weiv.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public List<Users> findByName(String name) {
        return userRepository.findByName(name);
    }

    public Users findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
    }

    public Users findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new BusinessException("Recurso não encontrado!"));
    }
    @Transactional
    public Users save(Users users) {

        Optional<Users> usersOptional = this.userRepository.findByEmail(users.getEmail());
        if(usersOptional.isPresent()){
            throw new RuntimeException("User already exists");
        }
        if(users.getName().isBlank() || users.getEmail().isBlank() || users.getPassword().isBlank()){
            throw new RuntimeException("Invalid registration credentials (Name, User or Password)");
        }

        users.setPassword(this.passwordEncoder.encode(users.getPassword()));

        return userRepository.save(users);
    }
    @Transactional
    public void updateUser(Users users) {
        userRepository.save(users);
    }

    public Map<String, String> deleteUser(Long id) {
        userRepository.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuario deletado com sucesso");
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> usersOptional = this.userRepository.findByEmail(username);

        return usersOptional.map(users -> new User(users.getEmail(), users.getPassword(), new ArrayList<GrantedAuthority>()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }

    public AuthDto auth(AuthDto authDto) {
        Users users = this.findByEmail(authDto.getEmail());

        if (!this.passwordEncoder.matches(authDto.getPassword(), users.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        StringBuilder password = new StringBuilder().append(users.getEmail()).append(":").append(users.getPassword());

        return AuthDto.builder().email(users.getEmail()).token(
                Base64.getEncoder().withoutPadding().encodeToString(password.toString().getBytes())
        ).id(users.getId()).build();
    }
}
