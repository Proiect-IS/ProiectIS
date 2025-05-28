package com.proiect.ProiectIsAeroport;

import com.proiect.ProiectIsAeroport.controllers.RezervareController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.proiect.ProiectIsAeroport.companie.Rezervare;

import java.util.Collections;

@SpringBootTest
public class TestStanRemus {

    @Autowired
    private RezervareController controller;

    @Mock
    private Model model;

    @BeforeEach
    void setup() {
        controller.getRezervari().clear();
        model = mock(Model.class);
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

    @Test
    void testAdaugaRezervareBusinessClass() {
        String rezultat = controller.adaugaRezervare(
                "CURS456", "Maria Ionescu", "0722333444",
                1, 0, 1, true, true, "business", true,
                "paypal", 550.0, model
        );

        assertEquals(1, controller.getRezervari().size());
        Rezervare r = controller.getRezervari().get(0);
        assertEquals("Maria Ionescu", r.getNumePasager());
        assertEquals("business", r.getClasa());
    }

    @Test
    void testAdaugaRezervareCuCopil() {
        String rezultat = controller.adaugaRezervare(
                "CURS789", "Vasile Dumitrescu", "0733555666",
                1, 1, 0, false, false, "economy", false,
                "cash", 200.0, model
        );

        assertEquals(1, controller.getRezervari().size());
        Rezervare r = controller.getRezervari().get(0);
        assertEquals(1, r.getNrAdulti());
        assertEquals(1, r.getNrCopii());
        assertFalse(r.isMasaInclusa());
    }
    @Test
    void testAdaugaRezervareFaraBagaj() {
        String rezultat = controller.adaugaRezervare(
                "CURS101", "Ana Vlad", "0744666777",
                1, 0, 0, false, false, "economy", false,
                "card", 150.0, model
        );

        assertEquals(1, controller.getRezervari().size());
        Rezervare r = controller.getRezervari().get(0);
        assertFalse(r.isBagajSuplimentar());
    }

    @Test
    void testAdaugaRezervareCuBagajPrioritar() {
        String rezultat = controller.adaugaRezervare(
                "CURS102", "George Enescu", "0755777888",
                1, 0, 0, true, false, "economy", true,
                "cash", 220.0, model
        );

        assertEquals(1, controller.getRezervari().size());
        Rezervare r = controller.getRezervari().get(0);
        assertTrue(r.isBagajSuplimentar());
        assertEquals("card",r.isPlataCuCard());
    }

    @Test
    void testLocuriOcupatePentruORezervareEconomy() {
        Rezervare r = new Rezervare("CURS123", "Ion Popescu", "0712345678",
                2, 1, 0, true, false, "economy", false, true, 300.0,true);
        controller.getRezervari().add(r);

        int locuri = controller.getLocuriOcupate("CURS123", "economy");
        assertEquals(3, locuri);  // 2 adulți + 1 copil
    }

    @Test
    void testLocuriOcupateMaiMulteRezervariAceeasiCursaSiClasa() {
        controller.getRezervari().add(new Rezervare("CURS456", "Maria Ionescu", "0722333444",
                1, 1, 1, false, true, "business", false,
                true, 600.0, true));
        controller.getRezervari().add(new Rezervare("CURS456", "Alex Georgescu", "0733444555",
                2, 0, 1, false, false, "business", false,
                false, 700.0, false));

        int locuri = controller.getLocuriOcupate("CURS456", "business");
        // 3 + 3 = 6
        assertEquals(6, locuri);
    }

    @Test
    void testLocuriOcupateCuRezervariLaCurseDiferite() {
        controller.getRezervari().add(new Rezervare("CURS789", "Ana Pop", "0711222333",
                1, 1, 0, true, false, "economy", true,
                true, 400.0, true));
        controller.getRezervari().add(new Rezervare("CURS101", "Bogdan I", "0722555444",
                1, 0, 1, false, true, "economy", false,
                false, 350.0, false));

        int locuri = controller.getLocuriOcupate("CURS789", "economy");
        assertEquals(2, locuri);  // doar prima rezervare contează
    }
    @Test
    void testLocuriOcupateCuClasaDiferita() {
        controller.getRezervari().add(new Rezervare("CURS123", "Dana L", "0700111222",
                2, 0, 0, true, false, "economy", false,
                true, 320.0, true));
        controller.getRezervari().add(new Rezervare("CURS123", "Cristi M", "0744222333",
                1, 1, 1, false, false, "business", false,
                false, 650.0, false));

        int locuriEconomy = controller.getLocuriOcupate("CURS123", "economy");
        int locuriBusiness = controller.getLocuriOcupate("CURS123", "business");

        assertEquals(2, locuriEconomy);     // 2 adulți
        assertEquals(3, locuriBusiness);    // 1 adult + 1 copil + 1 senior
    }

    @Test
    void testLocuriOcupateCandNuExistaRezervari() {
        // Nu adăugăm nicio rezervare
        int locuri = controller.getLocuriOcupate("CURS999", "economy");
        assertEquals(0, locuri, "Locurile ocupate pentru o cursă/clasă fără rezervări ar trebui să fie 0");
    }


}
