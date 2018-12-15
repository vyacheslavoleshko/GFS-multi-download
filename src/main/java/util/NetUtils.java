package util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;

/**
 * @author Viacheslav Oleshko
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NetUtils {
    public static String read(URL url)  {
        try (InputStream in = url.openStream()) {
            return IOUtils.toString(in, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("%s Cannot read from URL. \n %s", url.toString(), e));
        }
    }

    public static void download(URL url, Path path)  {
        try {
            FileUtils.copyURLToFile(url, path.toFile());
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("%s Cannot read to file from URL. \n %s", url.toString(), e));
        }
    }

}
