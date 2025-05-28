package com.proiect.ProiectIsAeroport;

import com.proiect.ProiectIsAeroport.companie.Rezervare;
import com.proiect.ProiectIsAeroport.controllers.RezervareController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestTicaAndrei {

    private RezervareController controller;
    private Model model;

    @BeforeEach
    public void setup() {
        controller = new RezervareController();
        model = spy(new ExtendedModelMap());
    }


    @Test
    void testBusinessFaraExtraPlataCard() {
        controller.preconfirmareRezervare(
                "CURSA1", "Ion Pop", "0712345678",
                1, 0, 0,
                false, false,
                "business", false,
                "card",
                100.0, 50.0, 70.0,
                "2025-06-12", "09:00",
                model
        );

        Rezervare r = (Rezervare) ((ExtendedModelMap) model).get("detaliiZbor");
        assertEquals("business", r.getClasa());
        assertEquals(1, r.getNrAdulti());
        assertEquals(0, r.getNrCopii());
        assertEquals(0, r.getNrSeniori());
        assertEquals(100.0, r.getPretTotal());
        assertTrue(r.isPlataCuCard());
        assertEquals("2025-06-12", model.getAttribute("zi"));
        assertEquals("09:00", model.getAttribute("ora"));
    }


    @Test
    void testEcoCuExtraMasaBagajTurReturPlataCash() {
        controller.preconfirmareRezervare(
                "CURSA2", "Maria Ionescu", "0722000000",
                2, 1, 1,
                true, true,
                "eco", true,
                "cash",
                50.0, 25.0, 35.0,
                "2025-07-01", "13:30",
                model
        );

        Rezervare r = (Rezervare) ((ExtendedModelMap) model).get("detaliiZbor");
        int totalPersoane = r.getNrAdulti() + r.getNrCopii() + r.getNrSeniori();
        assertEquals(4, totalPersoane);
        assertEquals("eco", r.getClasa());
        assertTrue(r.isMasaInclusa());
        assertTrue(r.isBagajSuplimentar());
        assertTrue(r.isTurRetur());
        assertFalse(r.isPlataCuCard());
        assertEquals("2025-07-01", model.getAttribute("zi"));
        assertEquals("13:30", model.getAttribute("ora"));
        assertTrue(r.getPretTotal() > 0);  // doar să verificăm că a fost calculat
    }


    @Test
    void testFirstMasaInclusaFaraBagajPlataCard() {
        controller.preconfirmareRezervare(
                "CURSA3", "Alex Popa", "0744555666",
                1, 1, 0,
                true, false,
                "first", false,
                "card",
                150.0, 75.0, 100.0,
                "2025-08-05", "17:00",
                model
        );

        Rezervare r = (Rezervare) ((ExtendedModelMap) model).get("detaliiZbor");
        assertEquals("first", r.getClasa());
        assertTrue(r.isMasaInclusa());
        assertFalse(r.isBagajSuplimentar());
        assertTrue(r.isPlataCuCard());
        assertEquals("2025-08-05", model.getAttribute("zi"));
        assertEquals("17:00", model.getAttribute("ora"));
        assertEquals(2, r.getNrAdulti() + r.getNrCopii() + r.getNrSeniori());
    }


    @Test
    void testZeroPasageriBusiness() {
        controller.preconfirmareRezervare(
                "CURSA4", "Test", "0700000000",
                0, 0, 0,
                false, false,
                "business", false,
                "card",
                100.0, 50.0, 70.0,
                "2025-09-01", "10:00",
                model
        );

        Rezervare r = (Rezervare) ((ExtendedModelMap) model).get("detaliiZbor");
        int totalPersoane = r.getNrAdulti() + r.getNrCopii() + r.getNrSeniori();
        assertEquals(0, totalPersoane);
        assertEquals("business", r.getClasa());
        assertEquals(0.0, r.getPretTotal());
    }

    @Test
    void testClasaNeexistenta() {
        controller.preconfirmareRezervare(
                "CURSA5", "Ion Test", "0766777888",
                1, 1, 1,
                false, false,
                "luxury", false,
                "cash",
                100.0, 50.0, 70.0,
                "2025-09-15", "11:00",
                model
        );

        Rezervare r = (Rezervare) ((ExtendedModelMap) model).get("detaliiZbor");
        assertEquals("luxury", r.getClasa());
        assertEquals(3, r.getNrAdulti() + r.getNrCopii() + r.getNrSeniori());
        assertTrue(r.getPretTotal() >= 0);
        assertEquals("2025-09-15", model.getAttribute("zi"));
        assertEquals("11:00", model.getAttribute("ora"));
    }


    @Test
    void testPlataCashFaraExtras() {
        controller.preconfirmareRezervare(
                "CURSA6", "Elena Vasile", "0755666777",
                2, 0, 1,
                false, false,
                "eco", false,
                "cash",
                80.0, 40.0, 60.0,
                "2025-10-01", "14:45",
                model
        );

        Rezervare r = (Rezervare) ((ExtendedModelMap) model).get("detaliiZbor");
        assertFalse(r.isPlataCuCard());
        assertFalse(r.isMasaInclusa());
        assertFalse(r.isBagajSuplimentar());
        assertEquals(3, r.getNrAdulti() + r.getNrCopii() + r.getNrSeniori());
        assertEquals("eco", r.getClasa());
    }


    @Test
    void testDoarSenioriCuExtras() {
        controller.preconfirmareRezervare(
                "CURSA7", "Senior Test", "0788999000",
                0, 0, 3,
                true, true,
                "business", false,
                "card",
                120.0, 60.0, 80.0,
                "2025-11-10", "19:00",
                model
        );

        Rezervare r = (Rezervare) ((ExtendedModelMap) model).get("detaliiZbor");
        assertEquals(3, r.getNrSeniori());
        assertTrue(r.isMasaInclusa());
        assertTrue(r.isBagajSuplimentar());
        assertEquals("business", r.getClasa());
    }


    @Test
    void testTurReturMaiMultiAdultiCopii() {
        controller.preconfirmareRezervare(
                "CURSA8", "Familie Test", "0733444555",
                3, 2, 0,
                false, false,
                "eco", true,
                "card",
                90.0, 45.0, 0.0,
                "2025-12-25", "06:30",
                model
        );

        Rezervare r = (Rezervare) ((ExtendedModelMap) model).get("detaliiZbor");
        assertEquals(5, r.getNrAdulti() + r.getNrCopii() + r.getNrSeniori());
        assertTrue(r.isTurRetur());
        assertEquals("eco", r.getClasa());
        assertEquals("2025-12-25", model.getAttribute("zi"));
        assertEquals("06:30", model.getAttribute("ora"));
    }


    @Test
    void testNumePasagerSiTelefon() {
        controller.preconfirmareRezervare(
                "CURSA9", "George Enescu", "0799888777",
                1, 1, 1,
                false, false,
                "first", false,
                "cash",
                110.0, 55.0, 75.0,
                "2025-10-10", "18:20",
                model
        );

        Rezervare r = (Rezervare) ((ExtendedModelMap) model).get("detaliiZbor");
        assertEquals("George Enescu", r.getNumePasager());
        assertEquals("0799888777", r.getTelefon());
    }


    @Test
    void testValidareRezervareFlag() {
        controller.preconfirmareRezervare(
                "CURSA10", "Validare Test", "0722333444",
                1, 0, 0,
                false, false,
                "eco", false,
                "card",
                100.0, 50.0, 70.0,
                "2025-10-20", "20:00",
                model
        );

        Rezervare r = (Rezervare) ((ExtendedModelMap) model).get("detaliiZbor");
        assertNotNull(r);

    }
}
