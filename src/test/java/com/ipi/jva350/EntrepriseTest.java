package com.ipi.jva350;

import com.ipi.jva350.model.Entreprise;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntrepriseTest {
    @Test
    void testEstDansPlage() {
        LocalDate date = LocalDate.of(2023, 6, 15);
        LocalDate debut = LocalDate.of(2023, 6, 1);
        LocalDate fin = LocalDate.of(2023, 6, 30);

        assertTrue(Entreprise.estDansPlage(date, debut, fin));

        date = LocalDate.of(2023, 5, 31);
        assertFalse(Entreprise.estDansPlage(date, debut, fin));

        date = LocalDate.of(2023, 7, 1);
        assertFalse(Entreprise.estDansPlage(date, debut, fin));

        date = LocalDate.of(2023, 6, 1);
        assertTrue(Entreprise.estDansPlage(date, debut, fin));

        date = LocalDate.of(2023, 6, 30);
        assertTrue(Entreprise.estDansPlage(date, debut, fin));
    }

    @Test
    void testEstDansPlageWithNullValues() {
        LocalDate date = LocalDate.of(2023, 6, 15);
        LocalDate debut = LocalDate.of(2023, 6, 1);
        LocalDate fin = LocalDate.of(2023, 6, 30);

        assertThrows(IllegalArgumentException.class, () -> Entreprise.estDansPlage(null, debut, fin));
        assertThrows(IllegalArgumentException.class, () -> Entreprise.estDansPlage(date, null, fin));
        assertThrows(IllegalArgumentException.class, () -> Entreprise.estDansPlage(date, debut, null));
    }

    @Test
    void testEstDansPlageWithInvalidRange() {
        LocalDate date = LocalDate.of(2023, 6, 15);
        LocalDate debut = LocalDate.of(2023, 6, 30);
        LocalDate fin = LocalDate.of(2023, 6, 1);

        assertFalse(Entreprise.estDansPlage(date, debut, fin));
    }
}
