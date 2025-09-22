package docmanipulationworkspace.DocumentManipulation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users_height_weight_data")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_generator",
            sequenceName = "user_generator",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @Column(name = "user_id", updatable = false)
    private Long id;

    @Column(name = "height", nullable = false, unique = true)
    private String height;

    @Column(name = "weight", nullable = false, unique = true)
    private String weight;

    public User() {

    }

    public User(String height, String weight) {
        this.height = height;
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "User{" +
                "height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
