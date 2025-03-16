package com.ipi.jva350.service;

import com.ipi.jva350.exception.SalarieException;
import com.ipi.jva350.model.SalarieAideADomicile;
import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalarieAideADomicileServiceTest {
    private SalarieAideADomicileService service;
    private SalarieAideADomicileRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(SalarieAideADomicileRepository.class);
        service = new SalarieAideADomicileService(repository);
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        salarie.setId(1L);
        salarie.setNom("Dupont");
        salarie.setMoisDebutContrat(LocalDate.of(2023, 1, 1));
        salarie.setMoisEnCours(LocalDate.of(2023, 6, 1));
        salarie.setJoursTravaillesAnneeN(20);
        salarie.setJoursTravaillesAnneeNMoins1(15);
        salarie.setCongesPayesAcquisAnneeN(10);
        salarie.setCongesPayesAcquisAnneeNMoins1(5);
        salarie.setCongesPayesPrisAnneeNMoins1(0);
        salarie.setCongesPayesPris(new LinkedHashSet<>());
    }

    @Test
    void creerSalarieAideADomicileTest() throws SalarieException {
        SalarieAideADomicile salarie1 = new SalarieAideADomicile();
        salarie1.setNom("John Doe");
        when(repository.findByNom("John Doe")).thenReturn(null);
        service.creerSalarieAideADomicile(salarie1);
        verify(repository).save(salarie1);
    }

    @Test
    void calculeLimiteEntrepriseCongesPermisTest() {
        LocalDate moisEnCours = LocalDate.of(2023, 7, 1);
        double congesPayesAcquisAnneeNMoins1 = 20.0;
        LocalDate moisDebutContrat = LocalDate.of(2020, 1, 1);
        LocalDate premierJourDeConge = LocalDate.of(2023, 7, 1);
        LocalDate dernierJourDeConge = LocalDate.of(2023, 7, 10);
        when(repository.partCongesPrisTotauxAnneeNMoins1()).thenReturn(0.5);
        long limite = service.calculeLimiteEntrepriseCongesPermis(moisEnCours, congesPayesAcquisAnneeNMoins1,
                moisDebutContrat, premierJourDeConge, dernierJourDeConge);
        assertEquals(6, limite);
    }

    @Test
    void clotureMoisTest() throws SalarieException {
        SalarieAideADomicile salarie2 = new SalarieAideADomicile();
        salarie2.setMoisEnCours(LocalDate.of(2023, 5, 1));
        service.clotureMois(salarie2, 20);
        assertEquals(LocalDate.of(2023, 6, 1), salarie2.getMoisEnCours());
    }

    @Test
    void clotureAnnee() {
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        salarie.setJoursTravaillesAnneeN(20);
        salarie.setCongesPayesAcquisAnneeN(15);
        salarie.setCongesPayesPrisAnneeNMoins1(5);
        salarie.setMoisEnCours(LocalDate.of(2023, 6, 1));
        LinkedHashSet<LocalDate> congesPayesPris = new LinkedHashSet<>();
        congesPayesPris.add(LocalDate.of(2023, 5, 30));
        congesPayesPris.add(LocalDate.of(2023, 6, 1));
        salarie.setCongesPayesPris(congesPayesPris);
        service.clotureAnnee(salarie);
        assertEquals(20, salarie.getJoursTravaillesAnneeNMoins1());
        assertEquals(15, salarie.getCongesPayesAcquisAnneeNMoins1());
        assertEquals(0, salarie.getCongesPayesPrisAnneeNMoins1());
        assertEquals(0, salarie.getJoursTravaillesAnneeN());
        assertEquals(0, salarie.getCongesPayesAcquisAnneeN());
        assertTrue(salarie.getCongesPayesPris().contains(LocalDate.of(2023, 6, 1)));
        assertTrue(salarie.getCongesPayesPris().contains(LocalDate.of(2023, 5, 30)));
        verify(repository, times(1)).save(salarie);
    }
}