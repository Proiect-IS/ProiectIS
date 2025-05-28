package com.proiect.ProiectIsAeroport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.proiect.ProiectIsAeroport.controllers.CompanieController;
import com.proiect.ProiectIsAeroport.controllers.LoginController;
import com.proiect.ProiectIsAeroport.companie.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestHaiduCarina {

    @Autowired
    private CompanieController companieController;

    @Autowired
    private LoginController loginController;

    @Mock
    private org.springframework.ui.Model model;

    @BeforeEach
    public void setUp() {
        companieController.zboruri.clear();
        companieController.rezervari.clear();
    }

    @Test
    public void testStergereZbor_ZborSiRezervariExistente() throws IOException {
        companieController.zboruri.add(new Zbor_Regulat("AB123", Tip_Zbor.REGULAT, "Paris", "Bucuresti", 100, 200, 300, com.proiect.ProiectIsAeroport.companie.Model.MODEL1, 10, 10, 10, true, 10, "Luni", "08:00"));
        companieController.rezervari.add(new Rezervare("AB123", "Ion Popescu", "0712345678", 1, 0, 0, true, false, "economy", false, true, 200.0, true));

        String result = companieController.StergereZbor("AB123");

        assertTrue(companieController.zboruri.isEmpty());
        assertTrue(companieController.rezervari.isEmpty());
        assertEquals("redirect:/companie", result);
    }

    @Test
    public void testStergereZbor_DoarZborFaraRezervari() throws IOException {
        companieController.zboruri.add(new Zbor_Sezonier("CD456", Tip_Zbor.SEZONIER, "Madrid", "Cluj", 150, 250, 350, com.proiect.ProiectIsAeroport.companie.Model.MODEL3, 5, 5, 5, false, 5, "Marti", "10:30", "01-06-2025", "01-09-2025"));

        String result = companieController.StergereZbor("CD456");

        assertTrue(companieController.zboruri.isEmpty());
        assertEquals("redirect:/companie", result);
    }

    @Test
    public void testStergereZbor_CodInexistent() throws IOException {
        companieController.zboruri.add(new Zbor_Regulat("EF789", Tip_Zbor.REGULAT, "Berlin", "Timisoara", 100, 200, 300, com.proiect.ProiectIsAeroport.companie.Model.MODEL1, 15, 15, 15, false, 0, "Miercuri", "12:00"));

        String result = companieController.StergereZbor("ZZ999");

        assertEquals(1, companieController.zboruri.size());
        assertEquals("redirect:/companie", result);
    }

    @Test
    public void testStergereZbor_MaiMulteZboruriCuCodSimilar() throws IOException {
        companieController.zboruri.add(new Zbor_Regulat("GH101", Tip_Zbor.REGULAT, "Milano", "Sibiu", 120, 220, 320, com.proiect.ProiectIsAeroport.companie.Model.MODEL2, 12, 12, 12, true, 8, "Joi", "14:45"));
        companieController.zboruri.add(new Zbor_Regulat("GH102", Tip_Zbor.REGULAT, "Milano", "Sibiu", 120, 220, 320, com.proiect.ProiectIsAeroport.companie.Model.MODEL2, 12, 12, 12, true, 8, "Joi", "15:30"));
        companieController.rezervari.add(new Rezervare("GH101", "Ana Vlad", "0744666777", 2, 0, 0, true, true, "business", true, false, 420.0, true));

        String result = companieController.StergereZbor("GH101");

        assertEquals(1, companieController.zboruri.size());
        assertEquals("GH102", companieController.zboruri.get(0).getCod_cursa());
        assertTrue(companieController.rezervari.isEmpty());
        assertEquals("redirect:/companie", result);
    }

    @Test
    public void testStergereZbor_CodNullSauGol() throws IOException {
        companieController.zboruri.add(new Zbor_Sezonier("NULL1", Tip_Zbor.SEZONIER, "Roma", "Iasi", 130, 230, 330, com.proiect.ProiectIsAeroport.companie.Model.MODEL3, 8, 8, 8, false, 12, "Vineri", "09:15", "01-07-2025", "01-10-2025"));
        companieController.rezervari.add(new Rezervare("NULL1", "Bogdan I", "0722555444", 1, 1, 0, false, false, "economy", false, true, 180.0, true));

        String result1 = companieController.StergereZbor(null);
        String result2 = companieController.StergereZbor("");

        assertEquals(1, companieController.zboruri.size());
        assertEquals(1, companieController.rezervari.size());
        assertEquals("redirect:/companie", result1);
        assertEquals("redirect:/companie", result2);
    }

    @Test
    public void testLoginCorectCompanie() {
        Model model = mock(Model.class);
        String result = loginController.processLogin("companie", "parolaCompanie", model);
        assertEquals("redirect:/companie", result);
    }

    @Test
    public void testLoginParolaGresitaCompanie() {
        Model model = mock(Model.class);
        String result = loginController.processLogin("companie", "gresit", model);
        assertEquals("client_web/login", result);
        verify(model).addAttribute(eq("error"), contains("Parolă incorectă"));
    }

    @Test
    public void testLoginCorectStaff() {
        Model model = mock(Model.class);
        String result = loginController.processLogin("staff", "parolaStaff", model);
        assertEquals("redirect:/personal", result);
    }

    @Test
    public void testLoginParolaGresitaStaff() {
        Model model = mock(Model.class);
        String result = loginController.processLogin("staff", "gresit", model);
        assertEquals("client_web/login", result);
        verify(model).addAttribute(eq("error"), contains("Parolă incorectă"));
    }

    @Test
    public void testLoginTipInvalidUtilizator() {
        Model model = mock(Model.class);
        String result = loginController.processLogin("admin", "orice", model);
        assertEquals("client_web/login", result);
        verify(model).addAttribute(eq("error"), contains("Tip de utilizator invalid"));
    }
}

