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
import play.Logger;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class SvgGeneratorImpl implements SvgGenerator {


    @Override
    public String getDonut(Table data, String period) {

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
        int rows = data.getRowCount();

        if (rows > 0) {
            for (int i = 0; i < data.getRowCount(); i++) {
                total += (Double) data.getCell(1, i);
            }
        }

        if (rows > 0 && total > 0) {

            for (int i = 0; i < rows; i++) {

                Double cell = (Double) data.getCell(1, i);

                if (cell == 0) {
                    continue;
                }

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
                        "" + data.getCell(1, i)

                    ));
                }
            }


            sb.append(String.format("<circle cx='%d' cy='%d' r='%d' fill='#ffffff'/>",
                size / 2,
                size / 2,
                size / 4
            ));


        } else {

            sb.append(String.format("<circle cx='%d' cy='%d' r='%d' stroke-dasharray='%d,%d' stroke-width='5' stroke='black' fill='none'/>",
                size / 2,
                size / 2,
                size / 2 - 10,
                size / 20,
                size / 20
            ));

            sb.append(String.format("<circle cx='%d' cy='%d' r='%d' stroke-dasharray='%d,%d' stroke-width='5' stroke='black' fill='none'/>",
                size / 2,
                size / 2,
                size / 4,
                size / 20,
                size / 20
            ));

        }

        sb.append(String.format(
            "<text " +
                "x='%s' " +
                "y='%s' " +
                "text-anchor='middle' " +
                "dominant-baseline='central' " +
                "style='fill: #000000; stroke: none; font-size: 72px; font-weight: bold'" +
                ">" +
                "%s" +
                "</text>",
            size / 2,
            size / 2,
            period
        ));

        sb.append("</svg>\n");

        return sb.toString().replaceAll("'", "\"");

    }

	@Override
	public String getSimpleDonut(Table data, String period) {

		Logger.info("Pie chart data = \n" + data.dump());

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
		int rows = data.getRowCount();

		int pathCount = 0;
		if (rows > 0) {
			for (int i = 0; i < data.getRowCount(); i++) {
				Double cellValue = (Double) data.getCell(1, i);
				total += cellValue;
				if (cellValue > 0) {
					pathCount++;
				}
			}
		}

		if (rows > 0 && total > 0) {

			for (int i = 0; i < rows; i++) {

				Double cell = (Double) data.getCell(1, i);

				if (cell == 0) {
					continue;
				}

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
						Colors.makeGoodColorForSerieElement(i + 1, pathCount),
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
						Colors.makeGoodColorForSerieElement(i + 1, pathCount),
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


		} else {

			sb.append(String.format("<circle cx='%d' cy='%d' r='%d' stroke-dasharray='%d,%d' stroke-width='5' stroke='black' fill='none'/>",
				size / 2,
				size / 2,
				size / 2 - 10,
				size / 20,
				size / 20
			));

			sb.append(String.format("<circle cx='%d' cy='%d' r='%d' stroke-dasharray='%d,%d' stroke-width='5' stroke='black' fill='none'/>",
				size / 2,
				size / 2,
				size / 4,
				size / 20,
				size / 20
			));

		}

		sb.append(String.format(
			"<text " +
				"x='%s' " +
				"y='%s' " +
				"text-anchor='middle' " +
				"dominant-baseline='central' " +
				"style='fill: #000000; stroke: none; font-size: 72px; font-weight: bold'" +
				">" +
				"%s" +
				"</text>",
			size / 2,
			size / 2,
			period
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

        int count = data.getRowCount();

        if (count > 0) {
            sb.append(String.format("<svg xmlns='http://www.w3.org/2000/svg' version='1.1' width='%d' height='%d' viewBox='0 0 %d %d'>\n",
                size,
                size,
                size,
                size
            ));

            double maximum = data.max(1, 0, data.getColumnCount() - 1, data.getRowCount() - 1);

            double l = Math.log10(maximum);
            double lDown = Math.floor(l);
            double c = Math.ceil(maximum / Math.pow(10, lDown));
            double cap = c * Math.pow(10, lDown);

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

            double radius = size / 3;

            // circles
            for (int i = 0; i < count; i++) {
                double startAngle = i * Math.PI * 2 / count;
                double endAngle = (i + 1) * Math.PI * 2 / count;

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
                    double cellLeft = (double) data.getCell(1 + s, i);
                    double factorLeft = cellLeft / maximum;
                    double x = size / 2 + Math.cos(refAngle + angle) * radius * factorLeft * maximum / cap;
                    double y = size / 2 - Math.sin(refAngle + angle) * radius * factorLeft * maximum / cap;
                    seriePoints.add(new Vector2D(x, y));
                }

                // bg-series
                String path = computePathData(seriePoints, true);

                String color = Colors.makeGoodColorForSerieElement(s, data.getColumnCount() - 1);

                sb.append(String.format("<path d='%s' stroke='#333' fill='none'                     stroke-width='2' transform='translate(1,1)' />", path));
                sb.append(String.format("<path d='%s' stroke='#%s'  fill='#%s'  fill-opacity='0.25' stroke-width='2'                            />", path, color, color));

            }

            double startAngle = 0;

            for (double factor = 1.0; factor > 0; factor -= (1.0 / radiusSteps)) {
                double x1 = size / 2 + Math.cos(refAngle + startAngle) * radius * factor;
                double y1 = size / 2 - Math.sin(refAngle + startAngle) * radius * factor;

                NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("fr"));
                nf.setMaximumFractionDigits(12);

                String stdNot = nf.format(cap * factor);

                String scientNot = String.format(Locale.forLanguageTag("fr"), "%.3e", cap * factor);

                String level = stdNot;

                if (scientNot.length() < stdNot.length()) {
                    level = scientNot;
                }

                sb.append(String.format(
                    "<text " +
                        "x='%s' " +
                        "y='%s' " +
                        "text-anchor='end' " +
                        "dominant-baseline='central' " +
                        "style='fill: #000; stroke: none; font-size: 12px' " +
                        "title='%s' " +
                        ">" +
                        "%s" +
                        "</text>",
                    x1 - 10,
                    y1,
                    stdNot + " tCO2e",
                    level + " tCO2e"
                ));
            }

            sb.append("</svg>\n");
        }

        return sb.toString().replaceAll("'", "\"");

    }

	@Override
	public String getSimpleHistogram(Table data) {
		// +-----------+------------+------------+------------+-----------------+------------+------------+------------+-----------------+
		// + Indicator + P1:Scope 1 + P1:Scope 2 + P1:Scope 3 + P1:Out of scope + P2:Scope 1 + P2:Scope 2 + P2:Scope 3 + P2:Out of scope +
		// +-----------+------------+------------+------------+-----------------+------------+------------+------------+-----------------+
		Logger.info("data = \n" + data.dump());
		StringBuilder sb = new StringBuilder();
		int count = data.getRowCount();
		int series = (data.getColumnCount() - 1) / 4;
		double maximum = 0.0;

		for (int i = 0; i < count; i++) {
			for (int j = 0; j < series; j++) {
				double sum = 0;
				for (int iScope = 0; iScope < 4; iScope++) {
					sum += (Double) data.getCell(1 + j * 4 + iScope, i);
				}
				if (sum > maximum) {
					maximum = sum;
				}
			}
		}


		int sizex = 300 + count * 200;
		int sizey = 1000;


		double l = Math.log10(maximum);
		double lDown = Math.floor(l);
		double c = Math.ceil(maximum / Math.pow(10, lDown));
		double cap = c * Math.pow(10, lDown);

		if (series > 0 && count > 0) {

			sb.append(String.format("<svg xmlns='http://www.w3.org/2000/svg' version='1.1' width='%d' height='%d' viewBox='0 0 %d %d'>\n",
				sizex,
				sizey,
				sizex,
				sizey
			));

			double histoWidth = 200;
			double left = 300;
			for (int i = 0; i < count; i++) {
				for (int j = 0; j < series; j++) {
					double sum = 0;
					for (int iScope = 0; iScope < 4; iScope++) {
						Double cell = (Double) data.getCell(1 + j * 4 + iScope, i);
						sum += cell;
					}
					String color = Colors.makeGoodColorForSerieElement(j, series);

					sb.append(String.format(
						"<rect " +
							"x='%s' " +
							"y='%s' " +
							"width='%s' " +
							"height='%s' " +
							"fill='#%s' " +
							"stroke='white' " +
							"stroke-width='0' " +
							"class='path' " +
							"title='' " +
							"/>\n",
						left + histoWidth * i + (1.0 * histoWidth * 0.8 * j / series) + histoWidth * 0.1,
						sizey * 0.875 - (sum) * sizey * 0.75 / cap,
						histoWidth * 0.8 / series,
						sum * sizey * 0.75 / cap,
						color
					));
				}
			}

			// numbers
			for (int i = 0; i < count; i++) {
				double x = left + histoWidth * i + histoWidth / 2.0;
				double y = sizey * 0.875 + 50;
				sb.append(String.format(
					"<circle " +
						"cx='%s' " +
						"cy='%s' " +
						"r='24' " +
						"fill='none' " +
						"stroke='#000' " +
						"stroke-width='1' " +
						"/>",
					x,
					y
				));
				sb.append(String.format(
					"<text " +
						"x='%s' " +
						"y='%s' " +
						"text-anchor='middle' " +
						"dominant-baseline='central' " +
						"style='fill: #000; stroke: none; font-size: 32px'" +
						">" +
						"%s" +
						"</text>",
					x,
					y,
					i + 1
				));
			}

			sb.append(String.format(
				"<line " +
					"x1='%s' " +
					"y1='%s' " +
					"x2='%s' " +
					"y2='%s' " +
					"stroke-linecap='round' " +
					"stroke='#ccc' " +
					"stroke-width='3' " +
					"/>",
				0,
				sizey * 0.875,
				sizex,
				sizey * 0.875
			));

			sb.append(String.format(
				"<line " +
					"x1='%s' " +
					"y1='%s' " +
					"x2='%s' " +
					"y2='%s' " +
					"stroke-linecap='round' " +
					"stroke='#ccc' " +
					"stroke-width='3' " +
					"/>",
				left,
				0,
				left,
				sizey
			));

			for (double factor = 1.0; factor > 0; factor -= 0.25) {
				double t = sizey * 0.875 - sizey * factor * 0.75;
				sb.append(String.format(
					"<line " +
						"x1='%s' " +
						"y1='%s' " +
						"x2='%s' " +
						"y2='%s' " +
						"stroke-linecap='round' " +
						"stroke='#ccc' " +
						"stroke-width='3' " +
						"/>",
					left - 20,
					t,
					left,
					t
				));

				NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("fr"));
				nf.setMaximumFractionDigits(12);

				String stdNot = nf.format(cap * factor);

				String scientNot = String.format(Locale.forLanguageTag("fr"), "%.3e", cap * factor);

				String level = stdNot;

				if (scientNot.length() < stdNot.length()) {
					level = scientNot;
				}

				sb.append(String.format(
					"<text " +
						"x='%s' " +
						"y='%s' " +
						"text-anchor='end' " +
						"dominant-baseline='central' " +
						"style='fill: #000; stroke: none; font-size: 32px' " +
						"title='%s' " +
						">" +
						"%s" +
						"</text>",
					left - 30,
					t,
					stdNot + " tCO2e",
					level + " tCO2e"
				));
			}

			sb.append("</svg>\n");
		}

		return sb.toString().replaceAll("'", "\"");
	}

	@Override
    public String getHistogram(Table data) {

        // +-----------+------------+------------+------------+-----------------+------------+------------+------------+-----------------+
        // + Indicator + P1:Scope 1 + P1:Scope 2 + P1:Scope 3 + P1:Out of scope + P2:Scope 1 + P2:Scope 2 + P2:Scope 3 + P2:Out of scope +
        // +-----------+------------+------------+------------+-----------------+------------+------------+------------+-----------------+
		Logger.info("Histogram data = \n" + data.dump());

        StringBuilder sb = new StringBuilder();
        int count = data.getRowCount();
        int series = (data.getColumnCount() - 1) / 4;
        double maximum = 0.0;

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < series; j++) {
                double sum = 0;
                for (int iScope = 0; iScope < 4; iScope++) {
                    sum += (Double) data.getCell(1 + j * 4 + iScope, i);
                }
                if (sum > maximum) {
                    maximum = sum;
                }
            }
        }


        int sizex = 300 + count * 200;
        int sizey = 1000;


        double l = Math.log10(maximum);
        double lDown = Math.floor(l);
        double c = Math.ceil(maximum / Math.pow(10, lDown));
        double cap = c * Math.pow(10, lDown);

        if (series > 0 && count > 0) {

            sb.append(String.format("<svg xmlns='http://www.w3.org/2000/svg' version='1.1' width='%d' height='%d' viewBox='0 0 %d %d'>\n",
                sizex,
                sizey,
                sizex,
                sizey
            ));

            String[] scopeNames = new String[]{
                "Scope 1", "Scope 2", "Scope 3", "Hors scope"
            };

            double histoWidth = 200;
            double left = 300;
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < series; j++) {
                    double sum = 0;
                    for (int iScope = 0; iScope < 4; iScope++) {

                        String color = Colors.makeGoodColorForSerieElement(j, series);
                        color = Colors.interpolate(color, "ffffff", iScope, 4);

                        if (iScope == 1) {
                            if (j == 0) {
                                color = "DD1C1C";
                            } else {
                                color = "3CE6E6";
                            }
                        }

                        if (iScope == 2) {
                            if (j == 0) {
                                color = "EF5E5E";
                            } else {
                                color = "73F2CD";
                            }
                        }

                        Double cell = (Double) data.getCell(1 + j * 4 + iScope, i);
                        sb.append(String.format(
                            "<rect " +
                                "x='%s' " +
                                "y='%s' " +
                                "width='%s' " +
                                "height='%s' " +
                                "fill='#%s' " +
                                "stroke='white' " +
                                "stroke-width='0' " +
                                "class='path' " +
                                "title='%s' " +
                                "/>\n",
                            left + histoWidth * i + (1.0 * histoWidth * 0.8 * j / series) + histoWidth * 0.1,
                            sizey * 0.875 - (sum + cell) * sizey * 0.75 / cap,
                            histoWidth * 0.8 / series,
                            cell * sizey * 0.75 / cap,
                            color,
                            scopeNames[iScope]
                        ));
                        sum += cell;
                    }
                }
            }

            // numbers
            for (int i = 0; i < count; i++) {
                double x = left + histoWidth * i + histoWidth / 2.0;
                double y = sizey * 0.875 + 50;
                sb.append(String.format(
                    "<circle " +
                        "cx='%s' " +
                        "cy='%s' " +
                        "r='24' " +
                        "fill='none' " +
                        "stroke='#000' " +
                        "stroke-width='1' " +
                        "/>",
                    x,
                    y
                ));
                sb.append(String.format(
                    "<text " +
                        "x='%s' " +
                        "y='%s' " +
                        "text-anchor='middle' " +
                        "dominant-baseline='central' " +
                        "style='fill: #000; stroke: none; font-size: 32px'" +
                        ">" +
                        "%s" +
                        "</text>",
                    x,
                    y,
                    i + 1
                ));
            }

            sb.append(String.format(
                "<line " +
                    "x1='%s' " +
                    "y1='%s' " +
                    "x2='%s' " +
                    "y2='%s' " +
                    "stroke-linecap='round' " +
                    "stroke='#ccc' " +
                    "stroke-width='3' " +
                    "/>",
                0,
                sizey * 0.875,
                sizex,
                sizey * 0.875
            ));

            sb.append(String.format(
                "<line " +
                    "x1='%s' " +
                    "y1='%s' " +
                    "x2='%s' " +
                    "y2='%s' " +
                    "stroke-linecap='round' " +
                    "stroke='#ccc' " +
                    "stroke-width='3' " +
                    "/>",
                left,
                0,
                left,
                sizey
            ));

            for (double factor = 1.0; factor > 0; factor -= 0.25) {
                double t = sizey * 0.875 - sizey * factor * 0.75;
                sb.append(String.format(
                    "<line " +
                        "x1='%s' " +
                        "y1='%s' " +
                        "x2='%s' " +
                        "y2='%s' " +
                        "stroke-linecap='round' " +
                        "stroke='#ccc' " +
                        "stroke-width='3' " +
                        "/>",
                    left - 20,
                    t,
                    left,
                    t
                ));

                NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("fr"));
                nf.setMaximumFractionDigits(12);

                String stdNot = nf.format(cap * factor);

                String scientNot = String.format(Locale.forLanguageTag("fr"), "%.3e", cap * factor);

                String level = stdNot;

                if (scientNot.length() < stdNot.length()) {
                    level = scientNot;
                }

                sb.append(String.format(
                    "<text " +
                        "x='%s' " +
                        "y='%s' " +
                        "text-anchor='end' " +
                        "dominant-baseline='central' " +
                        "style='fill: #000; stroke: none; font-size: 32px' " +
                        "title='%s' " +
                        ">" +
                        "%s" +
                        "</text>",
                    left - 30,
                    t,
                    stdNot + " tCO2e",
                    level + " tCO2e"
                ));
            }

            sb.append("</svg>\n");
        }

        return sb.toString().replaceAll("'", "\"");


    }

    private String computePathData(List<Vector2D> points, boolean closeLoop) {
        // bg-series
        String path = "M";
        for (int i = 0; i < points.size(); i++) {
            if (i > 0) {
                path += " L";
            }
            path += " " + points.get(i).getX() + "," + points.get(i).getY();
        }
        if (closeLoop) {
            path += "Z";
        }
        return path;
    }
}
