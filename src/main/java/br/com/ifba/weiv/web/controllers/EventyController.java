package br.com.ifba.weiv.web.controllers;

import br.com.ifba.weiv.domain.dto.EventyCreateDTO;
import br.com.ifba.weiv.domain.dto.EventyUpdateDTO;
import br.com.ifba.weiv.domain.dto.EventyViewDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventyController {
    @Autowired
    private EventyService eventyService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<EventyViewDTO> getAllEvents() {
        return eventyService.findAll().stream()
                .map(this::convertToEventyViewDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventyViewDTO> getEventById(@PathVariable Long id) {
        return eventyService.findById(id)
                .map(event -> ResponseEntity.ok(convertToEventyViewDTO(event)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/date/{date}")
    public List<EventyViewDTO> getEventsByDate(@PathVariable Date date) {
        return eventyService.findByDate(date).stream()
                .map(this::convertToEventyViewDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/location/{location}")
    public List<EventyViewDTO> getEventsByLocation(@PathVariable String location) {
        return eventyService.findByLocation(location).stream()
                .map(this::convertToEventyViewDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{category}")
    public List<EventyViewDTO> getEventsByCategory(@PathVariable String category) {
        return eventyService.findByCategory(category).stream()
                .map(this::convertToEventyViewDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/name/{name}")
    public List<EventyViewDTO> getEventsByName(@PathVariable String name) {
        return eventyService.findByName(name).stream()
                .map(this::convertToEventyViewDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<EventyViewDTO> createEvent(@RequestBody EventyCreateDTO eventyCreateDTO) {
        Optional<Users> owner = Optional.ofNullable(userService.findById(eventyCreateDTO.getOwnerId()));
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
            return ResponseEntity.ok(convertToEventyViewDTO(savedEventy));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<EventyViewDTO> updateEvent(@PathVariable Long id, @RequestBody EventyUpdateDTO eventyUpdateDTO) {
        return eventyService.findById(id)
                .map(event -> {
                    event.setName(eventyUpdateDTO.getName());
                    event.setLocation(eventyUpdateDTO.getLocation());
                    event.setDate(eventyUpdateDTO.getDate());
                    event.setHour(eventyUpdateDTO.getHour());
                    event.setDescription(eventyUpdateDTO.getDescription());
                    event.setCategory(eventyUpdateDTO.getCategory());
                    event.setImage(eventyUpdateDTO.getImage());
                    Eventy updatedEvent = eventyService.save(event);
                    return ResponseEntity.ok(convertToEventyViewDTO(updatedEvent));
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

    private EventyViewDTO convertToEventyViewDTO(Eventy eventy) {
        EventyViewDTO dto = new EventyViewDTO();
        dto.setId(eventy.getId());
        dto.setName(eventy.getName());
        dto.setLocation(eventy.getLocation());
        dto.setDate(eventy.getDate());
        dto.setHour(eventy.getHour());
        dto.setOwnerId(eventy.getOwner().getId());
        dto.setDescription(eventy.getDescription());
        dto.setCategory(eventy.getCategory());
        dto.setImage(eventy.getImage());
        return dto;
    }
}
