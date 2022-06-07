package com.company;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class Controller {

	@FXML
	private TextArea taText;

	@FXML
	private TextArea taProb;

	HashMap<String, Token> map = new HashMap<String, Token>();
	ArrayList<FinalToken> highestProb;
	private int pos = -1;
	private float prob[];
	private boolean isLanguageModalLodaded = false;

	@FXML
	void spell(ActionEvent event) throws IOException {
		//test();
		taProb.clear();
		highestProb = new ArrayList<>();
		if (!isLanguageModalLodaded) {
			loadLanguageModal();
		}

		String[] text = taText.getText().split(" ");

		for (int i = 0; i < text.length; i++) {
			if ("#".compareTo(text[i]) == 0) {
				pos = i;
			}
		}

		prob = new float[pos];
		for (int j = 1; j < pos && j < 5; j++) {
			if (map.get(text[pos - j - 1] + " " + text[pos - j]) != null) {
				prob[j - 1] = map.get(text[pos - j - 1] + " " + text[pos - j]).getProbability();
			}
		}

		float temp = 1;
		for (int i = 0; i < prob.length; i++) {
			if (prob[i] != 0)
				temp *= prob[i];
		}

		for (Map.Entry<String, Token> entry : map.entrySet()) {
			String words[] = entry.getKey().split(" ");
			if (words.length == 2 && words[0].compareTo(text[pos - 1]) == 0) {
				try {
					float prob1 = entry.getValue().getProbability();// (pos-1) + pos
					float prob2 = Integer.MAX_VALUE;// (pos-2) + (pos-1) + pos
					float prob3 = Integer.MAX_VALUE;// pos + (pos +1)
					float prob4 = Integer.MAX_VALUE;// (pos - 1) + pos + (pos +1)
					float prob5 = Integer.MAX_VALUE;// pos + (pos +1) + (pos +2)

					try {
						prob2 = map.get(text[pos - 2] + " " + text[pos - 1] + " " + words[1]).getProbability();
					} catch (Exception ex) {
						prob2 = 1;
					}

					try {
						prob3 = map.get(words[1] + " " + text[pos + 1]).getProbability();
					} catch (Exception ex) {
						prob3 = 1;
					}

					try {
						prob4 = map.get(text[pos - 1] + " " + words[1] + " " + text[pos + 1]).getProbability();
					} catch (Exception ex) {
						prob4 = 1;
					}

					try {
						prob5 = map.get(words[1] + " " + text[pos + 1] + " " + text[pos + 2]).getProbability();
					} catch (Exception ex) {
						prob5 = 1;
					}

					highestProb.add(new FinalToken(words[1], prob1 * prob2 * prob3 * prob4 * prob5 * temp));
				} catch (Exception ex) {
					taProb.appendText("sorry no words found");
				}
			}
		}

		if (highestProb.size() == 0) {
			taProb.appendText("sorry no words found");
		} else {
			bubbleSort(highestProb, highestProb.size());

			for (int i = 0; i < 5 && i < highestProb.size(); i++) {
				taProb.appendText(highestProb.get(i).getKey() + "\t" + highestProb.get(i).getProbability() + "\n");
			}
		}
	}

	private void loadLanguageModal() throws IOException {
		List<String> text = Files.readAllLines(
				Paths.get("/Users/laithbada7a/Documents/Ai-project3/outnew.csv"), StandardCharsets.UTF_8);
		for (int i = 1; i < text.size(); i++) {
			String[] split = text.get(i).split(",");
			for (int j = 0; j < split.length; j++) {
				map.put(split[0], new Token(Float.parseFloat(split[1]), Float.parseFloat(split[2])));
			}
		}
		isLanguageModalLodaded = true;
	}

	static void bubbleSort(ArrayList ar, int n) {
		if (n == 1) {
			return;
		}

		for (int i = 0; i < n - 1; i++) {
			if (((FinalToken) ar.get(i)).getProbability() < ((FinalToken) ar.get(i + 1)).getProbability()) {
				FinalToken temp = ((FinalToken) ar.get(i));
				ar.set(i, ar.get(i + 1));
				ar.set(i + 1, temp);
			}
		}
		bubbleSort(ar, n - 1);
	}

	@FXML
	void exit(ActionEvent event) {
		System.exit(0);
	}

	public void test () {
	int numOfWords = 0;
	
			// TODO Auto-generated method stub
			try {
				HashMap<String, Token> map = new HashMap<String, Token>();
	
				List<String> text = Files.readAllLines(Paths.get("/Users/laithbada7a/Documents/Ai-project3/inputnew.txt"), StandardCharsets.UTF_8);
	
				for (int i = 0; i < text.size(); i++) {
					String[] split = text.get(i).split(" ");
					for (int j = 0; j < split.length; j++) {
						if (map.containsKey(split[j])) {
							Token t = map.get(split[j]);
							t.setFrequency(t.getFrequency() + 1);
							map.put(split[j], t);
						} else {
							map.put(split[j], new Token(1, 0));
							numOfWords++;
						}
					}
				}
	
				for (int i = 0; i < text.size(); i++) {
					String[] split = text.get(i).split(" ");
					for (int j = 0; j < split.length - 1; j++) {
						String temp = split[j] + " " + split[j + 1];
						if (map.containsKey(temp)) {
							Token t = map.get(temp);
							t.setFrequency(t.getFrequency() + 1);
							map.put(temp, t);
						} else {
							map.put(temp, new Token(1, 0));
						}
					}
				}
	
				for (int i = 0; i < text.size(); i++) {
					String[] split = text.get(i).split(" ");
					for (int j = 0; j < split.length - 2; j++) {
						String temp = split[j] + " " + split[j + 1] + " " + split[j + 2];
						if (map.containsKey(temp)) {
							Token t = map.get(temp);
							t.setFrequency(t.getFrequency() + 1);
							map.put(temp, t);
						} else {
							map.put(temp, new Token(1, 0));
						}
					}
				}
	
				for (Map.Entry<String, Token> entry : map.entrySet()) {
					int gram = entry.getKey().split(" ").length;
					if (gram == 1) {
						float prob = entry.getValue().getFrequency() / numOfWords;
						entry.getValue().setProbability(prob);
					} else if (gram == 2) {
						String[] words = entry.getKey().split(" ");
						float prob = entry.getValue().getFrequency() / map.get(words[0]).getFrequency();
						entry.getValue().setProbability(prob);
					} else if (gram == 3) {
						String[] words = entry.getKey().split(" ");
						float prob = entry.getValue().getFrequency() / map.get(words[0] + " " + words[1]).getFrequency();
						entry.getValue().setProbability(prob);
					}
				}
	
				String csvFilePath = "/Users/laithbada7a/Documents/Ai-project3/outnew.csv";
				BufferedWriter out = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(csvFilePath), StandardCharsets.UTF_8));
				out.write('\ufeff');
				out.write('\ufeef');
				out.write('\ufebb');
				out.write('\ufebf');
				out.write("Token,Count,Prob\n");
	
				for (Map.Entry<String, Token> entry : map.entrySet()) {
					out.write(entry.getKey() + "," + entry.getValue().getFrequency() + ","
							+ entry.getValue().getProbability());
					out.write("\n");
					out.flush();
				}
				out.close();
	
			} catch (
	
			FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
