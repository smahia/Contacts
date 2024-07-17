package com.example.contactlistback.service.impl;

import com.example.contactlistback.dto.createDto.CreateTelephoneDto;
import com.example.contactlistback.dtoConverter.TelephoneDtoConverter;
import com.example.contactlistback.entity.Contact;
import com.example.contactlistback.entity.Telephone;
import com.example.contactlistback.exception.NotFoundException;
import com.example.contactlistback.repository.ContactRepository;
import com.example.contactlistback.repository.TelephoneRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// https://medium.com/thefreshwrites/junit-and-mockito-in-spring-boot-38dcbf4b132f

// https://medium.com/javarevisited/restful-api-testing-in-java-with-mockito-service-layer-4d0e5dc58023

// https://www.vogella.com/tutorials/JUnit/article.html

@ExtendWith(MockitoExtension.class)
class TelephoneServiceImplTest {

    // Creating a Mock to mimic TelephoneRepository, but returns the mock data that can be found here
    @Mock
    private TelephoneRepository telephoneRepository;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private TelephoneDtoConverter telephoneDtoConverter;

    private AutoCloseable autoCloseable;

    private Contact contact;

    private Telephone telephone;

    private Telephone existentTelephone;

    private CreateTelephoneDto createTelephoneDto;

    // An instance of TelephoneServiceImpl, injected with the Mock created with @Mock.
    //@InjectMocks
    private TelephoneServiceImpl telephoneService;

    @BeforeEach
    void setUp() {

        autoCloseable = MockitoAnnotations.openMocks(this);

        this.telephoneService = new TelephoneServiceImpl(telephoneRepository, contactRepository, telephoneDtoConverter);

        // Creating an instance of Contact to be used for the test.
        contact = new Contact();
        contact.setId(1);
        contact.setName("Contact1");
        contact.setSurname("Contact1 Surname");
        contact.setContactEmergency(false);

        // Creating an instance of TelephoneDto to be used for the test.
        createTelephoneDto = new CreateTelephoneDto("643222333", "Home");

        // Creating an instance of Telephone to be used for the addTelephone test.
        telephone = new Telephone();
        telephone.setTelephoneNumber("643222333");
        telephone.setType("Home");
        telephone.setContact(contact);

        // Creating an instance of Telephone to be used for the editTelephone and deleteTelephone test.
        existentTelephone = new Telephone();
        existentTelephone.setId(1);
        existentTelephone.setTelephoneNumber("643222333");
        existentTelephone.setType("Home");
        existentTelephone.setContact(contact);

    }

    @AfterEach
    void tearDown() throws Exception {

        autoCloseable.close();
    }

    // A test method to test the create method of the TelephoneService class.
    @Test
    void addTelephone() {

        // Expected behaviour of the mocks
        when(contactRepository.findById(1)).thenReturn(Optional.of(contact));
        when(telephoneDtoConverter.dtoToNewEntity(createTelephoneDto, contact)).thenReturn(telephone);
        when(telephoneRepository.save(telephone)).thenReturn(telephone);

        // Call to the service to be tested
        Telephone result = telephoneService.addTelephone(createTelephoneDto, 1);

        // Asserts
        assertNotNull(result);
        assertEquals("643222333", result.getTelephoneNumber());

        // Verify the calls to the methods inside the method to be tested
        verify(contactRepository, times(1)).findById(1);

        verify(telephoneDtoConverter, times(1)).dtoToNewEntity(createTelephoneDto, contact);

        verify(telephoneRepository, times(1)).save(telephone);
    }

    /**
     * When trying to add a Telephone to a Contact that does not exists
     */
    @Test
    void addTelephoneInvalidContactError() {

        when(contactRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () ->
                        telephoneService.addTelephone(createTelephoneDto, 1)
        );

        verify(contactRepository, times(1)).findById(1);
    }

    /*
    When a Telephone does not exists when deleting or updating it
     */
    @Test
    void TelephoneNotExistsErrorWhenUpdating() {

        when(telephoneRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () ->
                        telephoneService.editTelephone(createTelephoneDto, 1)
        );

        assertThrows(NotFoundException.class,
                () ->
                        telephoneService.deleteTelephone( 1)
        );

        verify(telephoneRepository, times(1)).findById(1);
    }

    @Test
    void TelephoneNotExistsErrorWhenDeleting() {

        when(telephoneRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () ->
                        telephoneService.deleteTelephone( 1)
        );

        verify(telephoneRepository, times(1)).findById(1);
    }

    @Test
    void editTelephone() {

        // What the user enters
        CreateTelephoneDto editedTelephoneDto = new CreateTelephoneDto("11", "Work");

        // The expected result
        Telephone editedTelephone = new Telephone();
        editedTelephone.setId(1);
        editedTelephone.setTelephoneNumber("11");
        editedTelephone.setType("Work");

        // given - precondition or setup
        when(telephoneRepository.findById(1)).thenReturn(Optional.of(existentTelephone));
        when(telephoneRepository.save(any(Telephone.class))).thenReturn(editedTelephone);

        // when -  action or the behaviour that we are going test
        Telephone result = telephoneService.editTelephone(editedTelephoneDto, 1);

        // then - verify the output and check if matches what the user enters with the expected result
        assertEquals(editedTelephone.getTelephoneNumber(), result.getTelephoneNumber());
        assertEquals(editedTelephone.getType(), result.getType());

        verify(telephoneRepository, times(1)).findById(1);
        verify(telephoneRepository, times(1)).save(any(Telephone.class));
    }

    @Test
    void deleteTelephone() {

        // given - precondition or setup
        when(telephoneRepository.findById(1)).thenReturn(Optional.of(telephone));

        // when -  action or the behaviour that we are going test
        telephoneService.deleteTelephone(1);

        // then - verify the output
        verify(telephoneRepository, times(1)).deleteById(1);

    }
}