package br.com.ifba.weiv.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class EventyUpdateDTO {
    private String name;
    private String location;
    private Date date;
    private String hour;
    private String description;
    private String category;
    private String image;
}
