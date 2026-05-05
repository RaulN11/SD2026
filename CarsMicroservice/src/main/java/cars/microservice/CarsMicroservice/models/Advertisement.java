package cars.microservice.CarsMicroservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "advertisements")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private Integer price;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_firstname", nullable = false)
    private String firstName;

    @Column(name = "user_lastname", nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ElementCollection
    private List<String> images;

}

