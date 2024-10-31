package org.example.task;

import org.example.task.enums.EventStatus;
import org.example.task.models.Admin;
import org.example.task.models.Contract;
import org.example.task.models.Event;
import org.example.task.models.Participant;
import org.example.task.models.Visitor;
import org.example.task.repositories.ContractRepository;
import org.example.task.repositories.EventRepository;
import org.example.task.repositories.ParticipantRepository;
import org.example.task.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private EventService eventService;

    private Admin testAdmin;
    private Contract testContract;
    private Event testEvent;
    private Visitor testVisitor;

    @BeforeEach
    void setUp() {
        testAdmin = new Admin();
        testAdmin.setUsername("adminUser");

        testContract = new Contract();
        testContract.sign();

        testEvent = new Event();
        testEvent.setTitle("Test Event");
        testEvent.setCreatedBy(testAdmin);
        testEvent.setStatus(EventStatus.APPROVED);

        testVisitor = new Visitor();
        testVisitor.setUsername("visitorUser");
    }

    @Test
    public void testCreateEvent_Success() {
        when(contractRepository.findActiveContractByAdmin(testAdmin)).thenReturn(testContract);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        Event createdEvent = eventService.createEvent("Test Event", testAdmin);

        assertNotNull(createdEvent);
        assertEquals("Test Event", createdEvent.getTitle());
        assertEquals(EventStatus.APPROVED, createdEvent.getStatus());
        assertEquals(testAdmin, createdEvent.getCreatedBy());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    public void testCreateEvent_ContractNotSigned() {
        testContract.setSigned(false);
        when(contractRepository.findActiveContractByAdmin(testAdmin)).thenReturn(testContract);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> eventService.createEvent("Test Event", testAdmin)
        );

        assertEquals("Contract is not signed, event can not be created", exception.getMessage());
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    public void testGetAllEvents() {
        when(eventRepository.findAll()).thenReturn(List.of(testEvent));

        List<Event> events = eventService.getAllEvents();

        assertNotNull(events);
        assertEquals(1, events.size());
        assertEquals("Test Event", events.get(0).getTitle());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    public void testGetEventById_Found() {
        when(eventRepository.findEventById(1L)).thenReturn(Optional.of(testEvent));

        Optional<Event> event = eventService.getEventById(1L);

        assertTrue(event.isPresent());
        assertEquals("Test Event", event.get().getTitle());
        verify(eventRepository, times(1)).findEventById(1L);
    }

    @Test
    public void testGetEventById_NotFound() {
        when(eventRepository.findEventById(1L)).thenReturn(Optional.empty());

        Optional<Event> event = eventService.getEventById(1L);

        assertFalse(event.isPresent());
        verify(eventRepository, times(1)).findEventById(1L);
    }
}
