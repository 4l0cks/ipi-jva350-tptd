package com.ipi.jva350.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SalarieAideADomicileTest {

    @Test
    void aLegalementDroitADesCongesPayesTest() {
        //Given
        SalarieAideADomicile salarie = new SalarieAideADomicile("Dupont",LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), 15, 0, 11, 0, 0);
        //When
        boolean result = salarie.aLegalementDroitADesCongesPayes();
        //Then
        assertTrue(result);
    }

    @Test
    void aPasDroitADesCongesPayesTest() {
        //Given
        SalarieAideADomicile salarie = new SalarieAideADomicile("Dupont",LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), 9, 0, 0, 0, 0);
        //Then
        boolean result = salarie.aLegalementDroitADesCongesPayes();
        //When
        assertFalse(result);
    }


}