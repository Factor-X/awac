package eu.factorx.awac.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.Logger;

public class MyrmexExpressionParser {


	/*
	 * ----------------------------------------------------
	 * ----------------- PUBLIC FUNCTIONS -----------------
	 * ----------------------------------------------------
	 */

	private final static String	NUMBER_REGEX	= "-?[0-9.]+(E-?[0-9]+)?";

	public static Double toDouble(	String equation,
									final double xValue) throws NumberFormatException {

		equation = equation.replace("x",
									xValue + "");
		equation = equation.replace("X",
									xValue + "");
		equation = equation.replace(",",
									".");
		equation = equation.replace(" ",
									"");

//		final Pattern pattern = Pattern.compile("(\\(([0-9-*/+.]*?)\\))");
		final Pattern pattern = Pattern.compile("(\\(([^()]*?)\\))");

		boolean find = true;
		while (find) {
			Logger.debug("--> Evaluating equation '" + equation + "'");
			final Matcher matcher = pattern.matcher(equation);
			if (matcher.find()) {
				Logger.debug("  --> Resolving sub-equation: '" + matcher.group(1) + "'");
				Double resolveCalcul = resolveCalcul(matcher.group(2));
				Logger.debug("    --> " + resolveCalcul);
				equation = equation.replace(matcher.group(1),
											resolveCalcul + "");
			}
			else {
				find = false;
			}
		}
		equation = resolveCalcul(equation) + "";

		Logger.debug("  --> " + equation);
		return Double.parseDouble(equation);

	}

	/*
	 * ----------------------------------------------------
	 * ----------------- PRIVATE FUNCTIONS ----------------
	 * ----------------------------------------------------
	 */

	private static Double resolveCalcul(String calcul) throws NumberFormatException {

		boolean find = true;
		Pattern pattern = Pattern.compile("( *(" +
											NUMBER_REGEX +
											") *\\* *(" +
											NUMBER_REGEX +
											"))");
		Matcher matcher = null;
		if (find) {
			matcher = pattern.matcher(calcul);
			if (matcher.find()) {
				final double x = Double.parseDouble(matcher.group(2));
				final double y = Double.parseDouble(matcher.group(4));

				calcul = calcul.replace(matcher.group(1),
										(x * y) +
														"").replace(",",
																	".");
				;
			}
			else {
				find = false;
			}
		}

		find = true;
		pattern = Pattern.compile("( *(" +
									NUMBER_REGEX +
									") */ *(" +
									NUMBER_REGEX +
									"))");
		while (find) {
			matcher = pattern.matcher(calcul);
			if (matcher.find()) {

				final double x = Double.parseDouble(matcher.group(2));
				final double y = Double.parseDouble(matcher.group(4));

				calcul = calcul.replace(matcher.group(1),
										(x / y) +
														"").replace(",",
																	".");
			}
			else {
				find = false;
			}
		}

		find = true;
		pattern = Pattern.compile("( *(" +
									NUMBER_REGEX +
									") *\\+ *(" +
									NUMBER_REGEX +
									"))");
		while (find) {
			matcher = pattern.matcher(calcul);
			if (matcher.find()) {
				final double x = Double.parseDouble(matcher.group(2));
				final double y = Double.parseDouble(matcher.group(4));

				calcul = calcul.replace(matcher.group(1),
										(x + y) +
														"");
			}
			else {
				find = false;
			}
		}
		find = true;
		pattern = Pattern.compile("( *(" +
									NUMBER_REGEX +
									") *- *(" +
									NUMBER_REGEX +
									"))");
		while (find) {
			matcher = pattern.matcher(calcul);
			if (matcher.find()) {
				final double x = Double.parseDouble(matcher.group(2));
				final double y = Double.parseDouble(matcher.group(4));

				calcul = calcul.replace(matcher.group(1),
										(x - y) +
														"");
			}
			else {
				find = false;
			}
		}

		return Double.parseDouble(calcul);
	}

}
