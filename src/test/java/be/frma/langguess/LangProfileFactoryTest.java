
package be.frma.langguess;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.cybozu.labs.langdetect.util.LangProfile;

public class LangProfileFactoryTest {
	private static final File PROFILE_DIR = new File(new File(new File(new File("src"), "main"), "resources"), "languages");

	@Test
	public void readEnFile() throws IOException {
		checkProfileFile("en", 3, 2301);
	}

	@Test
	public void readBnFile() throws IOException {
		checkProfileFile("bn", 3, 2846);
	}

	@Test
	public void readFrFile() throws IOException {
		checkProfileFile("fr", 3, 2232);
	}

	@Test
	public void readNlFile() throws IOException {
		checkProfileFile("nl", 3, 2163);
	}
	
	@Test
	public void writeEnProfile() throws IOException {
		checkProfileCopy("en");
	}

	@Test
	public void writeFrProfile() throws IOException {
		checkProfileCopy("fr");
	}

	@Test
	public void writeNlProfile() throws IOException {
		checkProfileCopy("nl");
	}

	protected void checkProfileCopy(String language) throws FileNotFoundException, IOException {
		File originalFile = new File(PROFILE_DIR, language);
		final LangProfile originalProfile = readProfileFile(originalFile);
		File newFile = File.createTempFile("profile-copy-", null);
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(newFile);
			LangProfileFactory.writeProfile(originalProfile, output);
			LangProfile newProfile = readProfileFile(newFile);
			assertThat(newProfile.freq.size(), is(equalTo(originalProfile.freq.size())));
			assertThat(newProfile.freq, is(equalTo(originalProfile.freq)));
			assertThat(newProfile.n_words, is(equalTo(originalProfile.n_words)));
			assertThat(newProfile.name, is(equalTo(originalProfile.name)));
		} finally {
			IOUtils.closeQuietly(output);
			newFile.delete();
		}
	}

	private static void checkProfileFile(String language, int nWordSize, int freqSize) throws IOException {
		File profileFile = new File(PROFILE_DIR, language);
		final LangProfile langProfile = readProfileFile(profileFile);
		assertThat(langProfile, is(notNullValue()));
		assertThat(langProfile.name, is(equalTo(language)));
		assertThat(langProfile.n_words, is(notNullValue()));
		assertThat(langProfile.n_words.length, is(equalTo(nWordSize)));
		assertThat(langProfile.freq, is(notNullValue()));
		assertThat(langProfile.freq.size(), is(equalTo(freqSize)));
	}

	private static LangProfile readProfileFile(File profileFile) throws FileNotFoundException, IOException {
		FileInputStream input = null;
		final LangProfile langProfile;
		try {
			input = new FileInputStream(profileFile);
			langProfile = LangProfileFactory.readProfile(input);
		} finally {
			IOUtils.closeQuietly(input);
		}
		return langProfile;
	}

}
