package br.com.ifba.weiv.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EventyUpdateDTO {
    private String name;
    private String location;
    private Date date;
    private String hour;
    private Long ownerId;
    private String description;
    private String category;
    private String image;
}
