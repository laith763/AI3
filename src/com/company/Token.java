package com.company;

public class Token {
	private float frequency = 0;
	private float probability = 0;

	public Token(float frequency, float probability) {
		super();
		this.frequency = frequency;
		this.probability = probability;
	}

	public float getFrequency() {
		return frequency;
	}

	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}

	public float getProbability() {
		return probability;
	}

	public void setProbability(float probability) {
		this.probability = probability;
	}
}
