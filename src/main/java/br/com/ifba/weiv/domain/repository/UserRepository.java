package br.com.ifba.weiv.domain.repository;




import br.com.ifba.weiv.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    public List<Users> findByName();

    public List<Users> findByEmail();

}
