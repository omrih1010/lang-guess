
package be.frma.langguess;

import static org.mockito.Mockito.*;

import java.io.Closeable;
import java.io.IOException;

import org.junit.Test;

public class IOUtilsTest {

	@Test
	public void closeQuietlyNullStream() {
		IOUtils.closeQuietly(null);
	}

	@Test
	public void closeQuietlyWhenExceptionThrown() throws IOException {
		Closeable stream = mock(Closeable.class);
		doThrow(new IOException()).when(stream).close();
		IOUtils.closeQuietly(stream);
	}

	@Test
	public void closeQuietly() throws IOException {
		Closeable stream = mock(Closeable.class);
		IOUtils.closeQuietly(stream);
		verify(stream, times(1)).close();
	}

}
