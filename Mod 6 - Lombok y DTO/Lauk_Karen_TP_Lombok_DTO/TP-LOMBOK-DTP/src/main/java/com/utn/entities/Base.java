package com.utn.entities;
import lombok.*;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Base {
    protected Long id;
}