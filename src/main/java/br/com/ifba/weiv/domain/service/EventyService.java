package br.com.ifba.weiv.domain.service;

import br.com.ifba.weiv.domain.entity.Eventy;
import br.com.ifba.weiv.domain.repository.EventyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventyService {
    @Autowired
    private EventyRepository eventyRepository;

    public List<Eventy> findAll() {
        return eventyRepository.findAll();
    }

    public Optional<Eventy> findById(Long id) {
        return eventyRepository.findById(id);
    }

    public Eventy save(Eventy eventy) {
        return eventyRepository.save(eventy);
    }

    public void deleteById(Long id) {
        eventyRepository.deleteById(id);
    }

    public List<Eventy> findByDate(Date date) {
        return eventyRepository.findByDate(date);
    }

    public List<Eventy> findByLocation(String location) {
        return eventyRepository.findByLocation(location);
    }

    public List<Eventy> findByCategory(String category) {
        return eventyRepository.findByCategory(category);
    }

    public List<Eventy> findByName(String name) {
        return eventyRepository.findByName(name);
    }
}
