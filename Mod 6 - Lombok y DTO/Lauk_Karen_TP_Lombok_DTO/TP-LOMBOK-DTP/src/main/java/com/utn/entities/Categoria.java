package com.utn.entities;
import lombok.*;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "nombre", callSuper = false)
@SuperBuilder
public class Categoria extends Base {
    private String nombre;
    private String descripcion;
}