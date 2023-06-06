package com.exam.Catering.ordering;

//import com.exam.Catering.client.Client;
import com.exam.Catering.users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @JsonIgnore
    private Users client;


    @OneToMany(mappedBy = "ordering", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<OrderedMeal> orderedMeals;

    @Enumerated(EnumType.STRING)
    private OrderingStatus status = OrderingStatus.PENDING;
}
