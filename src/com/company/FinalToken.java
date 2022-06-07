package com.company;

public class FinalToken {
	private String key;
	private float probability;

	public FinalToken(String key, float probability) {
		super();
		this.key = key;
		this.probability = probability;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public float getProbability() {
		return probability;
	}

	public void setProbability(float probability) {
		this.probability = probability;
	}

	public int compareTo(FinalToken f) {
		if (this.probability > f.probability)
			return 1;
		else if (this.probability < f.probability)
			return -1;
		else
			return -1;
	}
}
