package eu.factorx.awac.util;

public class Colors {

	public static String makeGoodColorForSerieElement(int element, int count) {
		return makeGoodColorForSerieElement(element, count, 0.66f, 0.5f);
	}

	public static String makeGoodColorForSerieElement(int element, int count, float saturation, float value) {
		return hsvToRgb(1.0f * (element % count) / count, saturation, value);
	}


	public static String hsvToRgb(float hue, float saturation, float value) {

		int h = (int) (hue * 6);
		float f = hue * 6 - h;
		float p = value * (1 - saturation);
		float q = value * (1 - f * saturation);
		float t = value * (1 - (1 - f) * saturation);

		switch (h) {
			case 0:
				return rgbToString(value, t, p);
			case 1:
				return rgbToString(q, value, p);
			case 2:
				return rgbToString(p, value, t);
			case 3:
				return rgbToString(p, q, value);
			case 4:
				return rgbToString(t, p, value);
			case 5:
				return rgbToString(value, p, q);
			default:
				throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
		}
	}

	public static String rgbToString(float r, float g, float b) {
		String rs = Integer.toHexString((int) (r * 256));
		String gs = Integer.toHexString((int) (g * 256));
		String bs = Integer.toHexString((int) (b * 256));
		return rs + gs + bs;
	}

	public static String interpolate(String color, String target, int current, int total) {

		String r1, g1, b1, r2, g2, b2;
		int ir1, ig1, ib1, ir2, ig2, ib2;

		r1 = color.substring(0, 2);
		g1 = color.substring(2, 4);
		b1 = color.substring(4, 6);

		r2 = target.substring(0, 2);
		g2 = target.substring(2, 4);
		b2 = target.substring(4, 6);

		ir1 = Integer.parseInt(r1, 16);
		ig1 = Integer.parseInt(g1, 16);
		ib1 = Integer.parseInt(b1, 16);

		ir2 = Integer.parseInt(r2, 16);
		ig2 = Integer.parseInt(g2, 16);
		ib2 = Integer.parseInt(b2, 16);

		double ratio = 1.0 * current / total;

		int ifr, ifg, ifb;
		String fr, fg, fb;

		ifr = (int) (ir1 + ratio * (ir2 - ir1));
		ifg = (int) (ig1 + ratio * (ig2 - ig1));
		ifb = (int) (ib1 + ratio * (ib2 - ib1));

		return Integer.toHexString(ifr) + Integer.toHexString(ifg) + Integer.toHexString(ifb);
	}
}
