package com.ipi.jva350;

import com.ipi.jva350.exception.SalarieException;
import com.ipi.jva350.model.SalarieAideADomicile;
import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import com.ipi.jva350.service.SalarieAideADomicileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
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

    @InjectMocks
    private SalarieAideADomicileService salarieAideADomicileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SalarieAideADomicile salarie = new SalarieAideADomicile();
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
    void aLegalementDroitADesCongesPayesTest() {
        SalarieAideADomicile salarie = new SalarieAideADomicile("Dupont",LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), 15, 0, 11, 0, 0);
        boolean result = salarie.aLegalementDroitADesCongesPayes();
        assertTrue(result);
    }

    @Test
    void aPasDroitADesCongesPayesTest() {
        SalarieAideADomicile salarie = new SalarieAideADomicile("Dupont",LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), 9, 0, 0, 0, 0);
        boolean result = salarie.aLegalementDroitADesCongesPayes();
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
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        LinkedHashSet<LocalDate> result = salarie.calculeJoursDeCongeDecomptesPourPlage(dateDebut, dateFin);
        assertEquals(expectedSize, result.size());
    }


}