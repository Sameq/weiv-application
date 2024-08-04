package br.com.ifba.weiv.domain.repository;

import br.com.ifba.weiv.domain.entity.Eventy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventyRepository extends JpaRepository<Eventy, Long> {
    List<Eventy> findByDate(Date date);
    List<Eventy> findByLocation(String location);
    List<Eventy> findByCategory(String category);
    List<Eventy> findByName(String name);
}
