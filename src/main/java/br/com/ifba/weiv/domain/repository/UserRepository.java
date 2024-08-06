package br.com.ifba.weiv.domain.repository;




import br.com.ifba.weiv.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    public List<Users> findByName(String name);

    Optional<Users> findByEmail(String email);

    public Optional<Users> findById(Long id);

}
