package com.ipi.jva350;

import com.ipi.jva350.model.SalarieAideADomicile;
import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class SalarieAideADomicileRepositoryTest {

    @Autowired
    private SalarieAideADomicileRepository repository;

    @Test
    void testPartCongesPrisTotauxAnneeNMoins1() {
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

        Double partCongesPris = repository.partCongesPrisTotauxAnneeNMoins1();
        assertEquals(0.5, partCongesPris);
    }
}
