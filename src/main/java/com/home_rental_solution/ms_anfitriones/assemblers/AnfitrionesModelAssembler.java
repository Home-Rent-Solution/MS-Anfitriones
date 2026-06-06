package com.home_rental_solution.ms_anfitriones.assemblers;

import com.home_rental_solution.ms_anfitriones.controller.AnfitrionesController;
import com.home_rental_solution.ms_anfitriones.dto.AnfitrionesResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AnfitrionesModelAssembler implements RepresentationModelAssembler<AnfitrionesResponseDTO, EntityModel<AnfitrionesResponseDTO>> {

    @Override
    public EntityModel<AnfitrionesResponseDTO> toModel (AnfitrionesResponseDTO dto){
        Long id = dto.getIdAnfitrion();
        return EntityModel.of(dto,
                linkTo(methodOn(AnfitrionesController.class).getPorId(id)).withSelfRel(),
                linkTo(methodOn(AnfitrionesController.class).getAnfitriones()).withRel("anfitriones"),
                linkTo(methodOn(AnfitrionesController.class).validar(id)).withRel("validar-estado"),
                linkTo(methodOn(AnfitrionesController.class).getPropiedades(id)).withRel("propiedades")
                );
    }
}
