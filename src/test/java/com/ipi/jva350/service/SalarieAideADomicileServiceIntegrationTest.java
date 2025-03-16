package com.ipi.jva350.service;

import com.ipi.jva350.model.SalarieAideADomicile;
import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class SalarieAideADomicileServiceIntegrationTest {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @Autowired
    private SalarieAideADomicileRepository repository;

    @Test
    void testCalculeLimiteEntrepriseCongesPermis() {
        SalarieAideADomicile salarie1 = new SalarieAideADomicile();
        salarie1.setNom("Dupont");
        salarie1.setMoisDebutContrat(LocalDate.of(2022, 1, 1));
        salarie1.setMoisEnCours(LocalDate.of(2023, 1, 1));
        salarie1.setCongesPayesAcquisAnneeNMoins1(10);
        salarie1.setCongesPayesPrisAnneeNMoins1(5);
        repository.save(salarie1);

        SalarieAideADomicile salarie2 = new SalarieAideADomicile();
        salarie2.setNom("Durand");
        salarie2.setMoisDebutContrat(LocalDate.of(2022, 1, 1));
        salarie2.setMoisEnCours(LocalDate.of(2023, 1, 1));
        salarie2.setCongesPayesAcquisAnneeNMoins1(20);
        salarie2.setCongesPayesPrisAnneeNMoins1(10);
        repository.save(salarie2);
        LocalDate moisEnCours = LocalDate.of(2023, 1, 1);
        double congesPayesAcquisAnneeNMoins1 = 30.0;
        LocalDate moisDebutContrat = LocalDate.of(2022, 1, 1);
        LocalDate premierJourDeConge = LocalDate.of(2023, 7, 1);
        LocalDate dernierJourDeConge = LocalDate.of(2023, 7, 10);

        long limiteCongesPermis = salarieAideADomicileService.calculeLimiteEntrepriseCongesPermis(
                moisEnCours, congesPayesAcquisAnneeNMoins1, moisDebutContrat, premierJourDeConge, dernierJourDeConge);
        assertEquals(6.0, limiteCongesPermis);
    }
}
