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
import eu.factorx.awac.models.knowledge.FactorValue;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.CUD;
import eu.factorx.awac.util.data.importer.CodeLabelImporter;
import eu.factorx.awac.util.data.importer.FactorImporter;
import eu.factorx.awac.util.data.importer.IndicatorImporter;
import eu.factorx.awac.util.data.importer.TranslationImporter;
import eu.factorx.awac.util.data.importer.badImporter.BADImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Play;
import play.db.jpa.JPA;
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


    private FactorValueDTO findById(List<FactorValueDTO> items, Long id) {

        for (FactorValueDTO item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;

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

                CUD<FactorValueDTO> cud = CUD.fromLists(oldValues, newValues);

                List<FactorValueDTO> created = cud.getCreated();
                List<FactorValueDTO> updated = cud.getUpdated();
                List<FactorValueDTO> deleted = cud.getDeleted();

                for (FactorValueDTO factorValueDTO : deleted) {
                    for (FactorValue factorValue : factor.getValues()) {
                        if (factorValue.getId().equals(factorValueDTO.getId())) {
                            factor.getValues().remove(factorValue);
                            System.out.println("DELETED #" + factorValueDTO.getId());
                            break;
                        }
                    }
                }

                for (FactorValueDTO factorValueDTO : updated) {
                    for (FactorValue factorValue : factor.getValues()) {
                        if (factorValue.getId().equals(factorValueDTO.getId())) {

                            if (factorValueDTO.getDateIn() != null) {
                                factorValue.setDateIn(Integer.valueOf(factorValueDTO.getDateIn()));
                            } else {
                                factorValue.setDateIn(null);
                            }

                            if (factorValueDTO.getDateOut() != null) {
                                factorValue.setDateIn(Integer.valueOf(factorValueDTO.getDateOut()));
                            } else {
                                factorValue.setDateOut(null);
                            }

                            factorValue.setValue(factorValueDTO.getValue());

                            JPA.em().persist(factorValue);

                            System.out.println("UPDATED #" + factorValueDTO.getId());
                            break;
                        }
                    }

                }

                for (FactorValueDTO factorValueDTO : created) {

                    Integer i = null;
                    if (factorValueDTO.getDateIn() != null) {
                        i = Integer.valueOf(factorValueDTO.getDateIn());
                    }

                    Integer o = null;
                    if (factorValueDTO.getDateOut() != null) {
                        o = Integer.valueOf(factorValueDTO.getDateOut());
                    }

                    FactorValue factorValue = new FactorValue(factorValueDTO.getValue(), i, o, factor);

                    JPA.em().persist(factorValue);

                    System.out.println("CREATED #" + factorValue.getId());
                }


            } else {
                // CREATE

            }
        }

        return ok();

    }
}
