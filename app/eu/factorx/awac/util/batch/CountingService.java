package eu.factorx.awac.util.batch;


import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import play.Logger;
import play.api.Play;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import javax.inject.Named;
import java.util.List;

/**
 * A simple service that can increment a number.
 */

@Named("CountingService")
public class CountingService {

    @Autowired
    private PeriodService periodService;

    @Autowired
    private UnitService unitService;

    /**
     * Increment the given number by one.
     */
    public int increment(int count) {
        Logger.info("service called");

        try {
            JPA.withTransaction("default", false, new play.libs.F.Function0<Void>() {
                public Void apply() throws Throwable {

                    // try to access an autowired service.
                    final Period period = periodService.findByCode(new PeriodCode("2013"));
                    Logger.info("Period:" + period.getLabel());
                    final Period lastYear = periodService.findLastYear();
                    Logger.info("Last year:" + lastYear.getLabel());

                    List<Unit> lu = unitService.findAll();
                    Logger.info("UnitService size = : " + lu.size());

                    return null;
                }
            });
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }



        return count + 1;
    }
}