package com.ipi.jva350;

import com.ipi.jva350.model.SalarieAideADomicile;
import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalarieAideADomicileTest {

    @Mock
    private SalarieAideADomicileRepository salarieAideADomicileRepository;

    private SalarieAideADomicile salarie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        salarie = new SalarieAideADomicile();
        salarie.setId(1L);
        salarie.setNom("Dupont");
        salarie.setMoisDebutContrat(LocalDate.of(2023, 1, 1));
        salarie.setMoisEnCours(LocalDate.of(2023, 1, 1));
        salarie.setJoursTravaillesAnneeN(20);
        salarie.setJoursTravaillesAnneeNMoins1(15);
        salarie.setCongesPayesAcquisAnneeN(10);
        salarie.setCongesPayesAcquisAnneeNMoins1(5);
        salarie.setCongesPayesPrisAnneeNMoins1(0);
        salarie.setCongesPayesPris(new LinkedHashSet<>());
        when(salarieAideADomicileRepository.findById(1L)).thenReturn(Optional.of(salarie));
    }


    @Test
    void testSalarieInitialization() {
        assertEquals(1L, salarie.getId());
        assertEquals("Dupont", salarie.getNom());
        assertEquals(LocalDate.of(2023, 1, 1), salarie.getMoisDebutContrat());
        assertEquals(LocalDate.of(2023, 1, 1), salarie.getMoisEnCours());
        assertEquals(20, salarie.getJoursTravaillesAnneeN());
        assertEquals(15, salarie.getJoursTravaillesAnneeNMoins1());
        assertEquals(10, salarie.getCongesPayesAcquisAnneeN());
        assertEquals(5, salarie.getCongesPayesAcquisAnneeNMoins1());
        assertEquals(0, salarie.getCongesPayesPrisAnneeNMoins1());
        assertTrue(salarie.getCongesPayesPris().isEmpty());
    }

    @Test
    void aLegalementDroitADesCongesPayesTest() {
        SalarieAideADomicile salarie1 = new SalarieAideADomicile("Dupont",LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), 15, 0, 11, 0, 0);
        boolean result = salarie1.aLegalementDroitADesCongesPayes();
        assertTrue(result);
    }

    @Test
    void aPasDroitADesCongesPayesTest() {
        SalarieAideADomicile salarie2 = new SalarieAideADomicile("Dupont",LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), 9, 0, 0, 0, 0);
        boolean result = salarie2.aLegalementDroitADesCongesPayes();
        assertFalse(result);
    }

    @ParameterizedTest
    @CsvSource({
            "2023-01-01, 2023-01-10, 8",
            "2023-01-01, 2023-01-01, 0",
            "2023-01-10, 2023-01-01, 0",
            "2023-01-01, 2023-01-15, 12"
    })
    @DisplayName("Test calculeJoursDeCongeDecomptesPourPlage with various date ranges")
    void testCalculeJoursDeCongeDecomptesPourPlage(LocalDate dateDebut, LocalDate dateFin, int expectedSize) {
        SalarieAideADomicile salarieTest = new SalarieAideADomicile();
        LinkedHashSet<LocalDate> result = salarieTest.calculeJoursDeCongeDecomptesPourPlage(dateDebut, dateFin);
        assertEquals(expectedSize, result.size());
    }
}