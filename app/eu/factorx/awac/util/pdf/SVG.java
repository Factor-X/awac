package eu.factorx.awac.util.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;

/**
 * Created by gaston on 9/1/14.
 */
public class SVG {
	public static void toPDF () {


		try {
			Transcoder transcoder = new PDFTranscoder();
			TranscoderInput transcoderInput = new TranscoderInput(new FileInputStream(new File("public/svg/svg.xml")));
			TranscoderOutput transcoderOutput = new TranscoderOutput(new FileOutputStream(new File("test.pdf")));
			transcoder.transcode(transcoderInput, transcoderOutput);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		Document document = new Document();
//		try {
//			PdfWriter writer = PdfWriter.getInstance(document,
//					new FileOutputStream("svg.pdf"));
//			document.open();
//			document.add(new Paragraph("SVG Example"));
//
//			int width = 250;
//			int height = 250;
//			PdfContentByte cb = writer.getDirectContent();
//			PdfTemplate template = cb.createTemplate(width, height);
//			Graphics2D g2 = template.createGraphics(width, height);
//
//			PrintTranscoder prm = new PrintTranscoder();
//			TranscoderInput ti = new TranscoderInput("svg/svg.xml");
//			prm.transcode(ti, null);
//
//			PageFormat pg = new PageFormat();
//			Paper pp = new Paper();
//			pp.setSize(width, height);
//			pp.setImageableArea(0, 0, width, height);
//			pg.setPaper(pp);
//			prm.print(g2, pg, 0);
//			g2.dispose();
//
//			ImgTemplate img = new ImgTemplate(template);
//			document.add(img);
//
//		} catch (DocumentException e) {
//			System.err.println(e);
//		} catch (IOException e) {
//			System.err.println(e);
//		}
//		document.close();
	} // end of method


} // end of class
