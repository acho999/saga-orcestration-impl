package com.angel.models.entities;

import com.angel.models.api.IEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product implements IEvent {

    private String id;

    private String name;

    private String description;

    private double price;

    private int quantity;
}
