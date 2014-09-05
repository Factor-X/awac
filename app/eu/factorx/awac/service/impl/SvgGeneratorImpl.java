/*
 *
 * Instant Play Framework
 * AWAC
 *                       
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */

package eu.factorx.awac.service.impl;

import eu.factorx.awac.service.SvgGenerator;
import eu.factorx.awac.util.Colors;
import eu.factorx.awac.util.Table;
import org.springframework.stereotype.Component;

@Component
public class SvgGeneratorImpl implements SvgGenerator {


	@Override
	public String getDonut(Table data) {

		StringBuilder sb = new StringBuilder();

		int size = 1000;

		sb.append(String.format("<svg xmlns='http://www.w3.org/2000/svg' version='1.1' width='%d' height='%d'>\n",
			size,
			size
		));

		double oldPercentage = 0.0;
		double total = 0.0;

		for (int i = 0; i < data.getRowCount(); i++) {
			total += (Double) data.getCell(1, i);
		}


		for (int i = 0; i < data.getRowCount(); i++) {

			Double cell = (Double) data.getCell(1, i);
			double percentage = 100.0 * cell / total;

			double unit = (Math.PI * 2) / 100;
			double startangle = oldPercentage * unit;
			double endangle = (oldPercentage + percentage) * unit - 0.001;

			double x1 = (size / 2) + (size / 2) * Math.sin(startangle);
			double y1 = (size / 2) - (size / 2) * Math.cos(startangle);
			double x2 = (size / 2) + (size / 2) * Math.sin(endangle);
			double y2 = (size / 2) - (size / 2) * Math.cos(endangle);
			int big = 0;
			if (endangle - startangle > Math.PI) {
				big = 1;
			}
			String d = "M " + (size / 2) + "," + (size / 2) + " L " + x1 + "," + y1 + " A " + (size / 2) + "," + (size / 2) + " 0 " +
				big + " 1 " + x2 + "," + y2 + " Z";

			oldPercentage += percentage;


			if (percentage == 100.0) {
				sb.append(String.format("<circle cx='%d' cy='%d' r='%d' fill='#%s'/>",
					size / 2,
					size / 2,
					size / 2,
					Colors.makeGoodColorForSerieElement(i + 1, data.getRowCount())
				));
			} else {
				sb.append(String.format("<path d='%s' fill='#%s' stroke='white' stroke-width='5'></path>",
					d,
					Colors.makeGoodColorForSerieElement(i + 1, data.getRowCount())
				));
			}
		}

		sb.append("</svg>\n");

		return sb.toString().replaceAll("'", "\"");

	}
}
