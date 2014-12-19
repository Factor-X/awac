package eu.factorx.awac.util.document.business;

import eu.factorx.awac.GlobalVariables;
import eu.factorx.awac.dto.awac.get.DownloadFileDTO;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.AwacCalculatorService;
import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.service.ResultPdfGeneratorService;
import eu.factorx.awac.util.document.messages.DocumentMessage;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import play.Application;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import scala.Option;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Map;

@Component
public class DocumentSender implements ApplicationContextAware {

	// Application context aware
	private static ApplicationContext ctx = null;

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		// Assign the ApplicationContext into a static method
		DocumentSender.ctx = ctx;
	}

	public void generateDocument(final DocumentMessage document) throws BiffException, IOException, WriteException {

		EntityManager em;
		Application app = Play.application();
		Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
		em = jpaPlugin.get().em("default");
		JPA.bindForCurrentThread(em);

		PeriodService periodService = ctx.getBean(PeriodService.class);
		AwacCalculatorService awacCalculatorService = ctx.getBean(AwacCalculatorService.class);
		ResultPdfGeneratorService resultPdfGeneratorService = ctx.getBean(ResultPdfGeneratorService.class);


		Period period = periodService.findByCode(new PeriodCode(document.getPeriodKey()));
		Period comparedPeriod = null;
		if (document.getComparedPeriodKey() != null) {
			comparedPeriod = periodService.findByCode(new PeriodCode(document.getComparedPeriodKey()));
		}

		AwacCalculator awacCalculator = awacCalculatorService.findByCode(document.getInterfaceCode());
		Map<String, Double> type = document.getTypicalResultValues();
		Map<String, Double> ideal = document.getIdealResultValues();
		byte[] bytes = resultPdfGeneratorService.generate(awacCalculator, document.getLang(), document.getScopes(), period, comparedPeriod, document.getInterfaceCode(), type, ideal);

		DownloadFileDTO downloadFileDTO = new DownloadFileDTO();
		downloadFileDTO.setFilename("export_bilanGES_" + DateTime.now().toString("YMd-HH:mm").replace(':', 'h') + ".pdf");
		downloadFileDTO.setMimeType("application/pdf");
		downloadFileDTO.setBase64(new Base64().encodeAsString(bytes));

		GlobalVariables.promises.put(document.getUUID(), downloadFileDTO);

		Logger.info("DownloadFileDTO Successfully generated");

	}


}
