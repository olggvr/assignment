package org.example.task.models;

import jakarta.persistence.*;


@Entity
@Table(name = "participants")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;

    public Participant() {}

    public Participant(Event event, Visitor visitor) {
        this.event = event;
        this.visitor = visitor;
    }

    public Long getId() {
        return this.id;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Visitor getVisitor() {
        return this.visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

}
