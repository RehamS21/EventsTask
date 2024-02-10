package com.example.eventstask.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2 , max = 20)
    @Column(columnDefinition = "varchar(20)")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Events_Users_Table",
    joinColumns = {
            @JoinColumn(name = "user_id",referencedColumnName = "id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "event_id", referencedColumnName = "id")
    })
    private List<Events> events;
}
