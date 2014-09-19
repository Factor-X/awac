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
import eu.factorx.awac.util.math.Vector2D;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SvgGeneratorImpl implements SvgGenerator {


	@Override
	public String getDonut(Table data) {

		StringBuilder sb = new StringBuilder();

		int size = 1000;

		sb.append(String.format("<svg xmlns='http://www.w3.org/2000/svg' version='1.1' width='%d' height='%d' viewBox='0 0 %d %d'>\n",
			size,
			size,
			size,
			size
		));

		double oldPercentage = 0.0;
		double total = 0.0;

		for (int i = 0; i < data.getRowCount(); i++) {
			total += (Double) data.getCell(1, i);
		}


//		System.out.println("== Donut");
		for (int i = 0; i < data.getRowCount(); i++) {

			Double cell = (Double) data.getCell(1, i);
//			System.out.println("-- cell:" + i + " == " + cell);

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
				sb.append(String.format(
					"<circle " +
						"cx='%d' " +
						"cy='%d' " +
						"r='%d' " +
						"fill='#%s' " +
						"data-indicator-name='%s' " +
						"data-indicator-value='%s' " +
						"class='path' " +
						"/>",
					size / 2,
					size / 2,
					size / 2,
					Colors.makeGoodColorForSerieElement(i + 1, data.getRowCount()),
					"" + data.getCell(0, i),
					"" + data.getCell(0, i),
					"" + data.getCell(1, i)
				));
			} else {
				sb.append(String.format(
					"<path " +
						"d='%s' " +
						"fill='#%s' " +
						"stroke='white' " +
						"stroke-width='5' " +
						"data-indicator-name='%s' " +
						"data-indicator-value='%s' " +
						"class='path' " +
						"></path>",
					d,
					Colors.makeGoodColorForSerieElement(i + 1, data.getRowCount()),
					"" + data.getCell(0, i),
					"" + data.getCell(0, i),
					"" + data.getCell(1, i)

				));
			}
		}


		sb.append(String.format("<circle cx='%d' cy='%d' r='%d' fill='#ffffff'/>",
			size / 2,
			size / 2,
			size / 4
		));

		sb.append("</svg>\n");

		return sb.toString().replaceAll("'", "\"");

	}

	@Override
	public String getWeb(Table data) {

		double refAngle = Math.PI / 2;
		StringBuilder sb = new StringBuilder();

		int size = 500;
		int radiusSteps = 4;

		sb.append(String.format("<svg xmlns='http://www.w3.org/2000/svg' version='1.1' width='%d' height='%d' viewBox='0 0 %d %d'>\n",
			size,
			size,
			size,
			size
		));

		int count = data.getRowCount();
		double maximum = data.max(1, 0, data.getColumnCount() - 1, data.getRowCount() - 1);

		// rays
		for (int i = 0; i < count; i++) {
			double angle = i * Math.PI * 2 / count;
			double radius = size / 3;
			double x1 = size / 2;
			double y1 = size / 2;
			double x2 = size / 2 + Math.cos(refAngle + angle) * radius;
			double y2 = size / 2 - Math.sin(refAngle + angle) * radius;
			sb.append(String.format("<line x1='%s' y1='%s' x2='%s' y2='%s' stroke-linecap='round' stroke='#888' stroke-width='1' />", x1, y1, x2, y2));
		}

		// circles
		for (int i = 0; i < count; i++) {
			double startAngle = i * Math.PI * 2 / count;
			double endAngle = (i + 1) * Math.PI * 2 / count;
			double radius = size / 3;
			for (double factor = 1.0; factor > 0; factor -= (1.0 / radiusSteps)) {
				double x1 = size / 2 + Math.cos(refAngle + startAngle) * radius * factor;
				double y1 = size / 2 - Math.sin(refAngle + startAngle) * radius * factor;
				double x2 = size / 2 + Math.cos(refAngle + endAngle) * radius * factor;
				double y2 = size / 2 - Math.sin(refAngle + endAngle) * radius * factor;
				sb.append(String.format("<line x1='%s' y1='%s' x2='%s' y2='%s' stroke-linecap='round' stroke='#ccc' stroke-width='1' />", x1, y1, x2, y2));
			}
		}

		// numbers
		for (int i = 0; i < count; i++) {
			double radius = size / 3;
			double angle = i * Math.PI * 2 / count;
			double x = size / 2 + Math.cos(refAngle + angle) * (radius + 25);
			double y = size / 2 - Math.sin(refAngle + angle) * (radius + 25);
			sb.append(String.format("<circle cx='%s' cy='%s' r='15' fill='none' stroke='#000' stroke-width='1' />", x, y));
			sb.append(String.format("<text x='%s' y='%s' text-anchor='middle' dominant-baseline='central' style='fill: #000; stroke: none; font-size: 12px'>%s</text>", x, y, i + 1));
		}

		for (int s = 0; s < data.getColumnCount() - 1; s++) {


			List<Vector2D> seriePoints = new ArrayList<>();

			for (int i = 0; i < count; i++) {
				double angle = i * Math.PI * 2 / count;
				double radius = size / 3;
				double cellLeft = (double) data.getCell(1 + s, i);
				double factorLeft = cellLeft / maximum;
				double x = size / 2 + Math.cos(refAngle + angle) * radius * factorLeft;
				double y = size / 2 - Math.sin(refAngle + angle) * radius * factorLeft;
				seriePoints.add(new Vector2D(x, y));
			}

			// bg-series
			String path = computePathData(seriePoints, true);

			String color = Colors.makeGoodColorForSerieElement(s, data.getColumnCount());

			sb.append(String.format("<path d='%s' stroke='#333' fill='none'                     stroke-width='2' transform='translate(1,1)' />", path));
			sb.append(String.format("<path d='%s' stroke='#%s'  fill='#%s'  fill-opacity='0.25' stroke-width='2'                            />", path, color, color));

		}

		sb.append("</svg>\n");

		return sb.toString().replaceAll("'", "\"");

	}

	@Override
	public String getHistogram(Table data) {

		StringBuilder sb = new StringBuilder();
		int count = data.getRowCount();
		int series = data.getColumnCount() - 1;
		double maximum = data.max(1, 0, data.getColumnCount() - 1, count - 1);
		int size = 1000;
		sb.append(String.format("<svg xmlns='http://www.w3.org/2000/svg' version='1.1' width='%d' height='%d' viewBox='0 0 %d %d'>\n",
			size,
			size,
			size,
			size
		));

		double histoWidth = size * 0.75 / count;
		double left = size * 0.125;
		for (int i = 0; i < count; i++) {

			for (int j = 0; j < series; j++) {

				String color = Colors.makeGoodColorForSerieElement(j, series);
				Double cell = (Double) data.getCell(1, i);
				sb.append(String.format(
					"<rect " +
						"x='%s' " +
						"y='%s' " +
						"width='%s' " +
						"height='%s' " +
						"fill='#%s' " +
						"stroke='none' " +
						"stroke-width='0' " +
						"class='path' " +
						"/>\n",
					left + histoWidth * i + (1.0 * histoWidth * j / series),
					size * 0.875 - cell * size * 0.75 / maximum,
					histoWidth * (2.0 / 3) / series,
					cell * size * 0.75 / maximum,
					color
				));

			}

		}

		// numbers
		for (int i = 0; i < count; i++) {
			double x = left + histoWidth * i + histoWidth / 3.0;
			double y = size * 0.875 + 20;
			sb.append(String.format("<circle cx='%s' cy='%s' r='15' fill='none' stroke='#000' stroke-width='1' />", x, y));
			sb.append(String.format("<text x='%s' y='%s' text-anchor='middle' dominant-baseline='central' style='fill: #000; stroke: none; font-size: 12px'>%s</text>", x, y, i + 1));
		}


		sb.append("</svg>\n");

		return sb.toString().replaceAll("'", "\"");


	}

	private String computePathData(List<Vector2D> points, boolean closeLoop) {
		// bg-series
		String path = "M";
		for (int i = 0; i < points.size(); i++) {
			if (i > 0) path += " L";
			path += " " + points.get(i).getX() + "," + points.get(i).getY();
		}
		if (closeLoop) {
			path += "Z";
		}
		return path;
	}
}
