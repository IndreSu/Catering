package com.exam.Catering.client;

import com.exam.Catering.ordering.Ordering;
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
@Table(name="CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    @JsonIgnore // Add this annotation to ignore the clients field during serialization
    private List<Ordering> orderings;

}
