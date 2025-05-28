package com.proiect.ProiectIsAeroport;

import com.proiect.ProiectIsAeroport.companie.*;
import com.proiect.ProiectIsAeroport.controllers.HomeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestSimocDavid {

    @InjectMocks
    private HomeController homeController;

    private List<Zbor> zboruriDeTest;

    @Mock
    private org.springframework.ui.Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        zboruriDeTest = Arrays.asList(
                new Zbor_Regulat(
                        "Z1",
                        Tip_Zbor.REGULAT,
                        "Dubai",
                        "Timisoara",
                        500.0,
                        800.0,
                        300.0,
                        Model.MODEL1,
                        30,
                        10,
                        60,
                        false,
                        0,
                        "Luni",
                        "15:00"
                ),
                new Zbor_Regulat(
                        "R102",
                        Tip_Zbor.REGULAT,
                        "New York",
                        "Londra",
                        400.0,
                        600.0,
                        200.0,
                        Model.MODEL2,
                        80,
                        30,
                        150,
                        false,
                        0,
                        "Marti",
                        "14:00"
                ),
                new Zbor_Regulat(
                        "R101",
                        Tip_Zbor.REGULAT,
                        "Londra",
                        "Bucuresti",
                        160.0,
                        320.0,
                        100.0,
                        Model.MODEL3,
                        50,
                        20,
                        100,
                        true,
                        0,
                        "Marti",
                        "12:00"
                ),
                new Zbor_Regulat(
                        "R103",
                        Tip_Zbor.REGULAT,
                        "Roma",
                        "Paris",
                        250.0,
                        400.0,
                        120.0,
                        Model.MODEL1,
                        40,
                        15,
                        80,
                        false,
                        0,
                        "Vineri",
                        "18:00"
                )
        );
        try {
            Field zboruriField = HomeController.class.getDeclaredField("zboruri");
            zboruriField.setAccessible(true);
            zboruriField.set(homeController, new ArrayList<>(zboruriDeTest));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            fail("Nu s-a putut accesa câmpul privat zboruri prin reflection.");
        }
    }

    @Test
    void findZbor_cazPrincipal() {
        List<Zbor> rezultat = homeController.FindZbor("Bucuresti", "Londra", true);
        assertEquals(1, rezultat.size());
        assertTrue(rezultat.stream().allMatch(z -> z.getOras_plecare().equals("Bucuresti") &&
                z.getOras_destinatie().equals("Londra") &&
                z.isEsteTurRetur()));
    }

    @Test
    void findZbor_faraPotrivire() {
        List<Zbor> rezultat = homeController.FindZbor("Berlin", "Madrid", true);
        assertTrue(rezultat.isEmpty());
    }

    @Test
    void findZbor_potrivirePartialaPlecare() {
        List<Zbor> rezultat = homeController.FindZbor("Bucuresti", "Roma", true);
        assertTrue(rezultat.isEmpty());
    }

    @Test
    void findZbor_insensibilLaCaz() {
        List<Zbor> rezultat = homeController.FindZbor("bucuresti", "londra", true);
        assertEquals(1, rezultat.size());
    }

    @Test
    void findZbor_potrivireDupaTipZbor() {
        List<Zbor> rezultat = homeController.FindZbor("Londra", "New York", false);
        assertEquals(1, rezultat.size());
        assertEquals("R102", rezultat.get(0).getCod_cursa());
    }

    @Test
    void findZbor_cazPrincipal_doarDus() {
        List<Zbor> rezultat = homeController.FindZbor("Timisoara", "Dubai", false);
        assertEquals(1, rezultat.size());
        assertTrue(rezultat.stream().allMatch(z -> z.getOras_plecare().equals("Timisoara") &&
                z.getOras_destinatie().equals("Dubai") &&
                !z.isEsteTurRetur()));
    }

    @Test
    void findZbor_nuSePotrivesteTipZbor() {
        List<Zbor> rezultat = homeController.FindZbor("New York", "Londra", true);
        assertTrue(rezultat.isEmpty());
    }

    @Test
    void findZbor_partialMatch_shouldReturnEmptyList() {
        List<Zbor> rezultat = homeController.FindZbor("Dub", "Timisoara", false);
        assertTrue(rezultat.isEmpty());
    }

    @Test
    void detaliiZbor_oraInvalida_aruncaExceptie() {
        String codCursa = "R103";
        String ziua = "Vineri";
        String oraPlecare = "25:00";

        LocalDateTime now = LocalDateTime.of(2025, 5, 28, 14, 40);
        try (var mockedStatic = mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(now);
            assertThrows(DateTimeParseException.class, () -> {
                homeController.detaliiZbor(codCursa, ziua, oraPlecare, model);
            });
            verifyNoInteractions(model);
        }
    }

    @Test
    void detaliiZbor_ziInvalida_aruncaExceptie() {
        String codCursa = "R103";
        String ziua = "InvalidDay";
        String oraPlecare = "18:00";

        LocalDateTime now = LocalDateTime.of(2025, 5, 28, 14, 40);
        try (var mockedStatic = mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(now);
            assertThrows(IllegalArgumentException.class, () -> {
                homeController.detaliiZbor(codCursa, ziua, oraPlecare, model);
            });
            verifyNoInteractions(model);
        }
    }


}
