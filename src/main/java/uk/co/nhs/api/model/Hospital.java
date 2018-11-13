package uk.co.nhs.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hospital")
@Data
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(name = "hospital_id", nullable = false)
    private long hospitalId;

    @Column(name = "hospital_name", nullable = false)
    private String hospitalName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName ="id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<Appointment> appointments = new HashSet<>();

}