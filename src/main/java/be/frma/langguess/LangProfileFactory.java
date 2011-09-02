
package be.frma.langguess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cybozu.labs.langdetect.util.LangProfile;

public class LangProfileFactory {
	private static final Pattern FREQ_PATTERN = Pattern.compile("\"freq\" ?: ?\\{(.+?)\\}");
	private static final Pattern N_WORDS_PATTERN = Pattern.compile("\"n_words\" ?: ?\\[(.+?)\\]");
	private static final Pattern NAME_PATTERN = Pattern.compile("\"name\" ?: ?\"(.+?)\"");
	
	public static LangProfile readProfile(InputStream input) throws IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("utf-8")));
		String line = null;
		while((line = reader.readLine()) != null) {
			if (buffer.length() > 0) {
				buffer.append(' ');
			}
			buffer.append(line);
		}
		String storedProfile = buffer.toString();
		LangProfile langProfile = new LangProfile();

		Matcher m = FREQ_PATTERN.matcher(storedProfile);
		if (m.find()) {
			String[] entries = m.group(1).split(",");
			for (String entry : entries) {
				String[] keyValue = entry.split(":");
				String label = keyValue[0].trim().replace("\"", "");
				langProfile.freq.put(label, Integer.valueOf(keyValue[1]));
			}
		}

		m = N_WORDS_PATTERN.matcher(storedProfile);
		if (m.find()) {
			String[] nWords = m.group(1).split(",");
			langProfile.n_words = new int[nWords.length];
			for (int i = 0; i < nWords.length; i++) {
				langProfile.n_words[i] = Integer.parseInt(nWords[i]);
			}
		}
		
		m = NAME_PATTERN.matcher(storedProfile);
		if (m.find()) {
			langProfile.name = m.group(1);
		}

		return langProfile;
	}

	public static void writeProfile(LangProfile langProfile, OutputStream output) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Charset.forName("utf-8")));
		writer.write("{\"freq\":{");
		boolean first = true;
		for (Map.Entry<String, Integer> entry : langProfile.freq.entrySet()) {
			if (!first) {
				writer.write(',');
			}
			writer.write('"');
			writer.write(entry.getKey());
			writer.write("\":");
			writer.write(entry.getValue().toString());
			first = false;
		}
		writer.write("},\"n_words\":[");
		first = true;
		for (int nWord : langProfile.n_words) {
			if (!first) {
				writer.write(',');
			}
			writer.write(Integer.toString(nWord, 10));
			first = false;
		}
		writer.write("],\"name\":\"");
		writer.write(langProfile.name);
		writer.write("\"}");
		writer.flush();
	}
}
