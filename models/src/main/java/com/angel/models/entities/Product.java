package com.angel.models.entities;

import com.angel.models.api.IEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Product implements IEvent {
//POJO class should be immutable (without setter and final also is thread safe)
    private String id;

    private String name;

    private String description;

    private double price;

    private int quantity;
}
