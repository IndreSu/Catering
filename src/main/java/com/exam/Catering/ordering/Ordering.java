package com.exam.Catering.ordering;

import com.exam.Catering.client.Client;
import com.exam.Catering.meal.Meal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ORDERING")
public class Ordering {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @JsonIgnore // Add this annotation to ignore the meals field during serialization
    private Client client;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore // Add this annotation to ignore the meals field during serialization
    @JoinTable(
            name = "ordering_meal",
            joinColumns = @JoinColumn(name = "ordering_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals;

    @Enumerated(EnumType.STRING)
    private OrderingStatus status = OrderingStatus.PENDING;

}
