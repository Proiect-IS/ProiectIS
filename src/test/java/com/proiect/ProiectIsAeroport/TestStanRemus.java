package com.proiect.ProiectIsAeroport;

import com.proiect.ProiectIsAeroport.controllers.RezervareController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.proiect.ProiectIsAeroport.companie.Rezervare;

public class TestStanRemus {

    @InjectMocks
    private RezervareController controller;

    @Mock
    private Model model;

    @BeforeEach
    void setup() {
        controller.getRezervari().clear();
    }

    @Test
    void testAdaugaRezervareStandard() {
        String rezultat = controller.adaugaRezervare("CURS123", "Ion Popescu", "0712345678",
                2, 1, 0, true, false, "economy", false,
                "card", 300.0, model);

        assertEquals(1, controller.getRezervari().size());
        Rezervare r = controller.getRezervari().get(0);
        assertEquals("Ion Popescu", r.getNumePasager());
        assertTrue(r.isMasaInclusa());
        assertEquals("client_web/confirmareRezervare", rezultat);
        verify(model).addAttribute(eq("mesajRezervare"), anyString());
    }
}
