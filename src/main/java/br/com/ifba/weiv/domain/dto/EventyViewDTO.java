package br.com.ifba.weiv.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class EventyViewDTO {
    private Long id;
    private String name;
    private String location;
    private Date date;
    private String hour;
    private Long ownerId;
    private String description;
    private String category;
    private String image;
}
