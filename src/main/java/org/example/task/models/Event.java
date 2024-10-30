package org.example.task.models;

import jakarta.persistence.*;
import org.example.task.enums.EventStatus;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private Admin createdBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    public Event(){}

    public Event(Long id, String title, Admin createdBy, EventStatus status){
        this.id = id;
        this.title = title;
        this.createdBy = createdBy;
        this.status = status;
    }

    public Long getId() {return this.id;}

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Admin getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Admin createdBy) {
        this.createdBy = createdBy;
    }

    public EventStatus getStatus() {return this.status;}

    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
