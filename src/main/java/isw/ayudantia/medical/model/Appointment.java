package isw.ayudantia.medical.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne

    private Pacient pacient;

    @ManyToOne
    private Doctor doctor;

    @Column(name = "fecha", nullable = false, length = 100, columnDefinition = "DATE", updatable = false)
    private Date fecha;
    // getters y setters
}