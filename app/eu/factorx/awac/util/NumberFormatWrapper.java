package eu.factorx.awac.util;

import java.math.RoundingMode;
import java.text.*;
import java.util.Currency;
import java.util.Locale;

public class NumberFormatWrapper {

	private NumberFormat numberFormat;

	public NumberFormatWrapper(NumberFormat numberFormat) {
		this.numberFormat = numberFormat;
	}

	public StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos) {
		if (number == null) {
			return new StringBuffer();
		}
		return numberFormat.format(number, toAppendTo, pos);
	}

	public int getMinimumFractionDigits() {
		return numberFormat.getMinimumFractionDigits();
	}

	public int getMaximumIntegerDigits() {
		return numberFormat.getMaximumIntegerDigits();
	}

	public static NumberFormat getPercentInstance(Locale inLocale) {
		return NumberFormat.getPercentInstance(inLocale);
	}

	public Currency getCurrency() {
		return numberFormat.getCurrency();
	}

	public String format(long number) {
		return numberFormat.format(number);
	}

	public static NumberFormat getIntegerInstance() {
		return NumberFormat.getIntegerInstance();
	}

	public void setGroupingUsed(boolean newValue) {
		numberFormat.setGroupingUsed(newValue);
	}

	public String format(Object obj) {
		if (obj == null) {
			return "";
		}
		return numberFormat.format(obj);
	}

	public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
		return numberFormat.format(number, toAppendTo, pos);
	}

	public void setParseIntegerOnly(boolean value) {
		numberFormat.setParseIntegerOnly(value);
	}

	public Number parse(String source, ParsePosition parsePosition) {
		return numberFormat.parse(source, parsePosition);
	}

	public Object parseObject(String source, ParsePosition pos) {
		return numberFormat.parseObject(source, pos);
	}

	public static Locale[] getAvailableLocales() {
		return NumberFormat.getAvailableLocales();
	}

	public static NumberFormat getInstance(Locale inLocale) {
		return NumberFormat.getInstance(inLocale);
	}

	public void setRoundingMode(RoundingMode roundingMode) {
		numberFormat.setRoundingMode(roundingMode);
	}

	public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
		return numberFormat.formatToCharacterIterator(obj);
	}

	public boolean isParseIntegerOnly() {
		return numberFormat.isParseIntegerOnly();
	}

	public RoundingMode getRoundingMode() {
		return numberFormat.getRoundingMode();
	}

	public void setMinimumIntegerDigits(int newValue) {
		numberFormat.setMinimumIntegerDigits(newValue);
	}

	public static NumberFormat getIntegerInstance(Locale inLocale) {
		return NumberFormat.getIntegerInstance(inLocale);
	}

	public String format(double number) {
		return numberFormat.format(number);
	}

	public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
		return numberFormat.format(number, toAppendTo, pos);
	}

	public static NumberFormat getPercentInstance() {
		return NumberFormat.getPercentInstance();
	}

	public void setMinimumFractionDigits(int newValue) {
		numberFormat.setMinimumFractionDigits(newValue);
	}

	public static NumberFormat getInstance() {
		return NumberFormat.getInstance();
	}

	public Object parseObject(String source) throws ParseException {
		return numberFormat.parseObject(source);
	}

	public void setMaximumFractionDigits(int newValue) {
		numberFormat.setMaximumFractionDigits(newValue);
	}

	public void setCurrency(Currency currency) {
		numberFormat.setCurrency(currency);
	}

	public Number parse(String source) throws ParseException {
		return numberFormat.parse(source);
	}

	public void setMaximumIntegerDigits(int newValue) {
		numberFormat.setMaximumIntegerDigits(newValue);
	}

	public static NumberFormat getNumberInstance() {
		return NumberFormat.getNumberInstance();
	}

	public static NumberFormat getCurrencyInstance() {
		return NumberFormat.getCurrencyInstance();
	}

	public static NumberFormat getNumberInstance(Locale inLocale) {
		return NumberFormat.getNumberInstance(inLocale);
	}

	public int getMinimumIntegerDigits() {
		return numberFormat.getMinimumIntegerDigits();
	}

	public boolean isGroupingUsed() {
		return numberFormat.isGroupingUsed();
	}

	public static NumberFormat getCurrencyInstance(Locale inLocale) {
		return NumberFormat.getCurrencyInstance(inLocale);
	}

	public int getMaximumFractionDigits() {
		return numberFormat.getMaximumFractionDigits();
	}
}
