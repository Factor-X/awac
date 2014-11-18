package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.admin.BADLogDTO;
import eu.factorx.awac.dto.admin.get.FactorDTO;
import eu.factorx.awac.dto.admin.get.FactorValueDTO;
import eu.factorx.awac.dto.admin.get.FactorsDTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.awac.post.UpdateFactorsDTO;
import eu.factorx.awac.dto.myrmex.get.NotificationDTO;
import eu.factorx.awac.dto.myrmex.get.NotificationsDTO;
import eu.factorx.awac.generated.AwacEnterpriseInitialData;
import eu.factorx.awac.generated.AwacMunicipalityInitialData;
import eu.factorx.awac.models.Notification;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.data.importer.CodeLabelImporter;
import eu.factorx.awac.util.data.importer.FactorImporter;
import eu.factorx.awac.util.data.importer.IndicatorImporter;
import eu.factorx.awac.util.data.importer.TranslationImporter;
import eu.factorx.awac.util.data.importer.badImporter.BADImporter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Play;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class AdminController extends AbstractController {


    @Autowired
    private ConversionService           conversionService;
    @Autowired
    private NotificationService         notificationService;
    @Autowired
    private CodeLabelService            codeLabelService;
    @Autowired
    private FormService                 formService;
    @Autowired
    private TranslationImporter         translationImporter;
    @Autowired
    private CodeLabelImporter           codeLabelImporter;
    @Autowired
    private FactorImporter              factorImporter;
    @Autowired
    private IndicatorImporter           indicatorImporter;
    @Autowired
    private BADImporter                 badImporter;
    @Autowired
    private AwacEnterpriseInitialData   awacEnterpriseInitialData;
    @Autowired
    private AwacMunicipalityInitialData awacMunicipalityInitialData;
    @Autowired
    private QuestionSetService          questionSetService;
    @Autowired
    private FactorService               factorService;
    @Autowired
    private PeriodService               periodService;


    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    // @AuthenticatedAsSystemAdmin
    public Result getAllNotifications() {
        Account currentUser = securedController.getCurrentUser();
        List<Notification> all = notificationService.findAll();
        List<NotificationDTO> dtos = new ArrayList<>();
        for (Notification notification : all) {
            dtos.add(conversionService.convert(notification, NotificationDTO.class));
        }
        return ok(new NotificationsDTO(dtos));
    }

    @Transactional(readOnly = false)
    public Result resetCodeLabels() {
        if (!Play.application().isDev()) {
            return unauthorized();
        }
        // import code labels
        codeLabelImporter.run();
        // import translations
        translationImporter.run();

        return (ok());
    }

    @Transactional(readOnly = false)
    public Result resetIndicatorsAndFactors() {
        if (!Play.application().isDev()) {
            return unauthorized();
        }
        // import indicators and factors
        indicatorImporter.run();
        factorImporter.run();

        return (ok());
    }

    @Transactional(readOnly = false)
    public Result createMunicipalitySurveyData() {
        if (!Play.application().isDev()) {
            return unauthorized();
        }

        awacMunicipalityInitialData.createOrUpdateSurvey();

        return (ok());
    }

    @Transactional(readOnly = false)
    public Result createEnterpriseSurveyData() {
        if (!Play.application().isDev()) {
            return unauthorized();
        }

        awacEnterpriseInitialData.createOrUpdateSurvey();

        return (ok());
    }

    @Transactional(readOnly = true)
    public Result runBADImporter(String interfaceString) {
        if (!Play.application().isDev()) {
            return unauthorized();
        }

        InterfaceTypeCode interfaceTypeCode = InterfaceTypeCode.getByKey(interfaceString);

        return ok(conversionService.convert(badImporter.importBAD(interfaceTypeCode), BADLogDTO.class));
    }

    @Transactional(readOnly = true)
    public Result allFactors() {

        List<Factor> all = factorService.findAll();
        List<FactorDTO> dtos = new ArrayList<>();
        for (Factor factor : all) {
            dtos.add(conversionService.convert(factor, FactorDTO.class));
        }

        List<Period> periods = periodService.findAll();
        List<PeriodDTO> periodsDTOs = new ArrayList<>();
        for (Period period : periods) {
            periodsDTOs.add(conversionService.convert(period, PeriodDTO.class));
        }

        return ok(new FactorsDTO(dtos, periodsDTOs));

    }

    @Transactional(readOnly = false)
    public Result updateFactors() {

        UpdateFactorsDTO dto = extractDTOFromRequest(UpdateFactorsDTO.class);

        for (FactorDTO factorDTO : dto.getFactors()) {
            String key = factorDTO.getKey();

            Factor factor = factorService.findByCode(key);
            if (factor != null) {
                // UPDATE
                FactorDTO converted = conversionService.convert(factor, FactorDTO.class);

                List<FactorValueDTO> oldValues = converted.getFactorValues();
                List<FactorValueDTO> newValues = factorDTO.getFactorValues();

                List<FactorValueDTO> deleted = (List<FactorValueDTO>) CollectionUtils.subtract(oldValues, newValues);
                List<FactorValueDTO> updated = (List<FactorValueDTO>) CollectionUtils.intersection(oldValues, newValues);
                List<FactorValueDTO> created = (List<FactorValueDTO>) CollectionUtils.subtract(newValues, oldValues);

                for (FactorValueDTO factorValueDTO : updated) {
                    System.out.println("UPDATED " + factorValueDTO.getId());
                }

                for (FactorValueDTO factorValueDTO : created) {
                    System.out.println("CREATED " + factorValueDTO.getId());
                }

                for (FactorValueDTO factorValueDTO : deleted) {
                    System.out.println("DELETED " + factorValueDTO.getId());
                }

            } else {
                // CREATE

            }
        }


        return ok();

    }
}
