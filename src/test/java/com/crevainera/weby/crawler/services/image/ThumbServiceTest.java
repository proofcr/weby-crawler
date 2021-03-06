package com.crevainera.weby.crawler.services.image;

import com.crevainera.weby.crawler.exception.WebyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for {@link ThumbService}
 */
public class ThumbServiceTest {

    private static final String DOT = ".";
    private static final String IMAGES_DIRECTORY = "images/";
    private static final String IMAGE_NAME = "duke";
    private static final String THUMB_NAME = IMAGE_NAME + "_thumb";
    private static final String INPUT_PATH = IMAGES_DIRECTORY + IMAGE_NAME + DOT;
    private static final String OUTPUT_PATH = IMAGES_DIRECTORY + THUMB_NAME + DOT;
    private static final int MAX_THUMB_WIDTH = 50;
    private static final int MAX_THUMB_HEIGHT = 50;
    private static final String BROWSER_NAME = "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6";

    private ThumbService thumbService;

    @BeforeEach
    public void setUp() {
        thumbService = new ThumbService(new ImageProcessorService(BROWSER_NAME), MAX_THUMB_WIDTH, MAX_THUMB_HEIGHT);
    }

    @ParameterizedTest
    @ValueSource(strings = {"gif", "jpg", "png"})
    public void generatedThumbBytesShouldMatchWithExpectedThumbBytes(final String fileExtension)
            throws WebyException, URISyntaxException, IOException {
        URL inputPath = ClassLoader.getSystemResource(INPUT_PATH + fileExtension);
        byte[] actualImageArray = thumbService.resize(inputPath);

        URL expectedUrl = ClassLoader.getSystemResource(OUTPUT_PATH + fileExtension);
        File expectedFile = new File(expectedUrl.toURI());
        byte[] expectedImageArray = Files.readAllBytes(expectedFile.toPath());

        assertTrue(Arrays.equals(expectedImageArray, actualImageArray));
    }
}