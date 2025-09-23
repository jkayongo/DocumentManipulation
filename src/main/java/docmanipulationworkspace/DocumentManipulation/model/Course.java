package docmanipulationworkspace.DocumentManipulation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course_details")
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    @Column(name = "course_id", updatable = false)
    private Long courseId;

    @Column(name = "course_name", nullable = false, unique = true)
    private String courseName;

    @Column(name = "price", nullable = false, unique = false)
    private Double price;

    public Course() {

    }

    public Course(String courseName, Double price) {
        this.courseName = courseName;
        this.price = price;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", price=" + price +
                '}';
    }
}
