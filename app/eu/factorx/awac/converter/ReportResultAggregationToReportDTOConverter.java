package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.ReportDTO;
import eu.factorx.awac.dto.awac.get.ReportLineDTO;
import eu.factorx.awac.models.knowledge.ReportIndicator;
import eu.factorx.awac.service.ReportIndicatorService;
import eu.factorx.awac.service.impl.reporting.ReportResultAggregation;
import eu.factorx.awac.service.impl.reporting.ReportResultIndicatorAggregation;
import eu.factorx.awac.util.Colors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;

@Component
public class ReportResultAggregationToReportDTOConverter implements Converter<ReportResultAggregation, ReportDTO> {

    @Autowired
    private ReportIndicatorService reportIndicatorService;

    @Override
    public ReportDTO convert(ReportResultAggregation reportResultAggregation) {
        ReportDTO reportDTO = new ReportDTO();



        reportDTO.setLeftScope1Color("#" + Colors.interpolate(Colors.makeGoodColorForSerieElement(0, 2), "ffffff", 0, 4));
        reportDTO.setLeftScope2Color("#DD1C1C");
        reportDTO.setLeftScope3Color("#EF5E5E");

        reportDTO.setRightScope1Color("#" + Colors.interpolate(Colors.makeGoodColorForSerieElement(1, 2), "ffffff", 0, 4));
        reportDTO.setRightScope2Color("#3CE6E6");
        reportDTO.setRightScope3Color("#73F2CD");


        reportDTO.setLeftPeriod(reportResultAggregation.getPeriod().getLabel());
        reportDTO.setLeftColor("#" + Colors.makeGoodColorForSerieElement(1, 1));

        int notNullValues = 0;
        double total = 0;
        for (ReportResultIndicatorAggregation reportResultIndicatorAggregation : reportResultAggregation.getReportResultIndicatorAggregationList()) {
            Double totalValue = reportResultIndicatorAggregation.getTotalValue();
            if (totalValue > 0) {
                notNullValues++;
            }
            total += totalValue;
        }

        int currentNotNullValue = 0;

        for (ReportResultIndicatorAggregation reportResultIndicatorAggregation : reportResultAggregation.getReportResultIndicatorAggregationList()) {
            ReportLineDTO reportLineDTO = new ReportLineDTO(reportResultIndicatorAggregation.getIndicator());

            ReportIndicator reportIndicator = reportIndicatorService.findByReportCodeAndIndicatorCode(reportResultAggregation.getReportCode(), reportResultIndicatorAggregation.getIndicator());

            reportLineDTO.setOrder(reportIndicator.getOrderIndex());

            reportLineDTO.setLeftScope1Value(reportResultIndicatorAggregation.getScope1Value());
            reportLineDTO.setLeftScope2Value(reportResultIndicatorAggregation.getScope2Value());
            reportLineDTO.setLeftScope3Value(reportResultIndicatorAggregation.getScope3Value());
            reportLineDTO.setLeftOutOfScopeValue(reportResultIndicatorAggregation.getOutOfScopeValue());

            Double totalValue = reportResultIndicatorAggregation.getTotalValue();
            if (totalValue > 0) {
                reportLineDTO.setColor("#" + Colors.makeGoodColorForSerieElement(currentNotNullValue + 1, notNullValues));
                currentNotNullValue++;
            }
            reportLineDTO.setLeftPercentage(100 * totalValue / total);
            reportDTO.getReportLines().add(reportLineDTO);
        }

        Collections.sort(reportDTO.getReportLines(), new Comparator<ReportLineDTO>() {
            @Override
            public int compare(ReportLineDTO o1, ReportLineDTO o2) {
                return o1.getOrder().compareTo(o2.getOrder());
            }
        });


        return reportDTO;
    }

}
