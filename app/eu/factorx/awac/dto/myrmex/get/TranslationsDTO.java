package eu.factorx.awac.dto.myrmex.get;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import eu.factorx.awac.dto.DTO;

import java.util.HashMap;
import java.util.Map;

public class TranslationsDTO extends DTO {

	public String language;

	public Map<String, TranslationTextPart> lines;

	public TranslationsDTO(String language) {
		this.language = language;
		this.lines = new HashMap<>();
	}

	public TranslationTextPart put(String key, String fallback) {
		return lines.put(key, new TranslationTextPart(fallback));
	}

	public TranslationTextPart put(String key, String zero, String one, String more) {
		return lines.put(key, new TranslationTextPart(zero, one, more));
	}

	/**
	 * Returns the number of key-value mappings in this map.  If the
	 * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
	 * <tt>Integer.MAX_VALUE</tt>.
	 *
	 * @return the number of key-value mappings in this map
	 */
	public int size() {
		return lines.size();
	}

	/**
	 * Returns <tt>true</tt> if this map contains no key-value mappings.
	 *
	 * @return <tt>true</tt> if this map contains no key-value mappings
	 */
	public boolean isEmpty() {
		return lines.isEmpty();
	}

	/**
	 * Removes all of the mappings from this map (optional operation).
	 * The map will be empty after this call returns.
	 *
	 * @throws UnsupportedOperationException if the <tt>clear</tt> operation
	 *                                       is not supported by this map
	 */
	public void clear() {
		lines.clear();
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or {@code null} if this map contains no mapping for the key.
	 * <p/>
	 * <p>More formally, if this map contains a mapping from a key
	 * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
	 * key.equals(k))}, then this method returns {@code v}; otherwise
	 * it returns {@code null}.  (There can be at most one such mapping.)
	 * <p/>
	 * <p>If this map permits null values, then a return value of
	 * {@code null} does not <i>necessarily</i> indicate that the map
	 * contains no mapping for the key; it's also possible that the map
	 * explicitly maps the key to {@code null}.  The {@link #containsKey
	 * containsKey} operation may be used to distinguish these two cases.
	 *
	 * @param key the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or
	 * {@code null} if this map contains no mapping for the key
	 * @throws ClassCastException   if the key is of an inappropriate type for
	 *                              this map
	 *                              (<a href="Collection.html#optional-restrictions">optional</a>)
	 * @throws NullPointerException if the specified key is null and this map
	 *                              does not permit null keys
	 *                              (<a href="Collection.html#optional-restrictions">optional</a>)
	 */
	public TranslationTextPart get(Object key) {
		return lines.get(key);
	}

	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	private class TranslationTextPart {
		public String fallback;
		public String zero;
		public String one;
		public String more;

		private TranslationTextPart(String fallback) {
			this.fallback = fallback;
		}

		private TranslationTextPart(String zero, String one, String more) {
			this.zero = zero;
			this.one = one;
			this.more = more;
		}
	}
}
