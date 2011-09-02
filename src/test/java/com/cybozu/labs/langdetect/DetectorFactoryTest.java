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

package com.cybozu.labs.langdetect;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class DetectorFactoryTest {

	@Test
	public void loadProfilesFromClasspath() throws LangDetectException {
		DetectorFactory.loadProfile(this.getClass().getClassLoader(), "languages", "en", "fr", "nl");
		Detector detector = DetectorFactory.create();
		assertThat(detector, is(notNullValue()));
		detector.append("This is some English text.");
		assertThat(detector.detect(), is(equalTo("en")));
		detector = DetectorFactory.create();
		detector.append("Ceci est un texte français.");
		assertThat(detector.detect(), is(equalTo("fr")));
		detector = DetectorFactory.create();
		detector.append("Dit is een Nederlandse tekst.");
		assertThat(detector.detect(), is(equalTo("nl")));
	}

}
