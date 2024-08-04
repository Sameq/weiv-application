package br.com.ifba.weiv.domain.repository;




import br.com.ifba.weiv.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    public List<Users> findByName(String name);

    public List<Users> findByEmail(String email);

}
