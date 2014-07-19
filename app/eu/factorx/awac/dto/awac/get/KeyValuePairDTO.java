package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class KeyValuePairDTO<T, U> extends DTO {
	private T key;
	private U value;

	public KeyValuePairDTO(T key, U value) {
		this.key = key;
		this.value = value;
	}


	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public U getValue() {
		return value;
	}

	public void setValue(U value) {
		this.value = value;
	}
}
