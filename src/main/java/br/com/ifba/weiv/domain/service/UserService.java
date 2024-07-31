package br.com.ifba.weiv.domain.service;



import br.com.ifba.weiv.domain.entity.Users;
import br.com.ifba.weiv.domain.exception.BusinessException;
import br.com.ifba.weiv.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public List<Users> findByName() {
        return userRepository.findByName();
    }

    public List<Users> findByEmail() {
        return userRepository.findByEmail();
    }

    public Users findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new BusinessException("Recurso não encontrado!"));
    }
    @Transactional
    public Users save(Users users) {
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
}
