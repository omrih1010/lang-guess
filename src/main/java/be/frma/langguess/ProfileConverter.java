/*
 * Copyright 2011 Francois ROLAND
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package be.frma.langguess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import net.arnx.jsonic.JSON;

import com.cybozu.labs.langdetect.util.LangProfile;

/**
 * This command-line utility converts profiles from language-detection to profiles understandable by this project.
 */
public class ProfileConverter {
	public static void main(String[] args) throws IOException {
		File profiles = new File("profiles");
		File languages = new File(new File(new File(new File("src"), "main"), "resources"), "languages");
		FileInputStream in = null;
		ObjectOutputStream out = null;
		System.out.println("Converting profiles...");
		try {
			for (File profile : profiles.listFiles()) {
				in = new FileInputStream(profile);
				LangProfile langProfile = JSON.decode(in, LangProfile.class);
				File outFile = new File(languages, profile.getName());
				out = new ObjectOutputStream(new FileOutputStream(outFile));
				out.writeObject(langProfile);
				out.flush();
				System.out.println("Converted " + profile.getCanonicalPath() + " to " + outFile.getCanonicalPath());
			}
		} finally {
			out.close();
			in.close();
		}
		System.out.println("Conversion successful.");
	}
}
