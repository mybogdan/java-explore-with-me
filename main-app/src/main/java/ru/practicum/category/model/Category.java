package ru.practicum.category.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "categories", schema = "public")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
}
