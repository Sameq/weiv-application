package br.com.ifba.weiv.web.controllers;

import br.com.ifba.weiv.domain.dto.EventyCreateDTO;
import br.com.ifba.weiv.domain.dto.EventyUpdateDTO;
import br.com.ifba.weiv.domain.entity.Eventy;
import br.com.ifba.weiv.domain.entity.Users;
import br.com.ifba.weiv.domain.service.EventyService;
import br.com.ifba.weiv.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventyController {
    @Autowired
    private EventyService eventyService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Eventy> getAllEvents() {
        return eventyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eventy> getEventById(@PathVariable Long id) {
        return eventyService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/date/{date}")
    public List<Eventy> getEventsByDate(@PathVariable Date date) {
        return eventyService.findByDate(date);
    }

    @GetMapping("/location/{location}")
    public List<Eventy> getEventsByLocation(@PathVariable String location) {
        return eventyService.findByLocation(location);
    }

    @GetMapping("/category/{category}")
    public List<Eventy> getEventsByCategory(@PathVariable String category) {
        return eventyService.findByCategory(category);
    }

    @GetMapping("/name/{name}")
    public List<Eventy> getEventsByName(@PathVariable String name) {
        return eventyService.findByName(name);
    }

    @PostMapping
    public ResponseEntity<Eventy> createEvent(@RequestBody EventyCreateDTO eventyCreateDTO) {
        Users owner = userService.findById(eventyCreateDTO.getOwnerId());
        if (owner.isPresent()) {
            Eventy eventy = new Eventy();
            eventy.setName(eventyCreateDTO.getName());
            eventy.setLocation(eventyCreateDTO.getLocation());
            eventy.setDate(eventyCreateDTO.getDate());
            eventy.setHour(eventyCreateDTO.getHour());
            eventy.setOwner(owner.get());
            eventy.setDescription(eventyCreateDTO.getDescription());
            eventy.setCategory(eventyCreateDTO.getCategory());
            eventy.setImage(eventyCreateDTO.getImage());
            Eventy savedEventy = eventyService.save(eventy);
            return ResponseEntity.ok(savedEventy);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Eventy> updateEvent(@PathVariable Long id, @RequestBody EventyUpdateDTO eventyUpdateDTO) {
        return eventyService.findById(id)
                .map(event -> {
                    event.setName(eventyUpdateDTO.getName());
                    event.setLocation(eventyUpdateDTO.getLocation());
                    event.setDate(eventyUpdateDTO.getDate());
                    event.setHour(eventyUpdateDTO.getHour());
                    if (eventyUpdateDTO.getOwnerId() != null) {
                        Users owner = userService.findById(eventyUpdateDTO.getOwnerId());
                        owner.isPresent();
                    }
                    event.setDescription(eventyUpdateDTO.getDescription());
                    event.setCategory(eventyUpdateDTO.getCategory());
                    event.setImage(eventyUpdateDTO.getImage());
                    return ResponseEntity.ok(eventyService.save(event));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventyService.findById(id).isPresent()) {
            eventyService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
