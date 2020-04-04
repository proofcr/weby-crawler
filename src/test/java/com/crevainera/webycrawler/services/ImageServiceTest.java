package com.crevainera.webycrawler.services;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ImageServiceTest {

    private static final String DOT = ".";
    private static final String IMAGES_DIRECTORY = "images/";
    private static final String IMAGE_NAME = "duke";
    private static final String THUMB_NAME = IMAGE_NAME + "_thumb";
    private static final String INPUT_PATH = IMAGES_DIRECTORY + IMAGE_NAME + DOT;
    private static final String OUTPUT_PATH = IMAGES_DIRECTORY + THUMB_NAME + DOT;
    private static final int MAX_THUMB_WIDTH = 50;
    private static final int MAX_THUMB_HEIGHT = 50;

    private ImageService imageService;

    @ParameterizedTest
    @ValueSource(strings = {"gif", "jpg", "png"})
    public void generatedThumbBytesShouldMatchWithExpectedThumbBytes(final String fileExtension)
            throws IOException, URISyntaxException {
        imageService = new ImageService();

        URL inputPath = ClassLoader.getSystemResource(INPUT_PATH + fileExtension);
        File inputFile = new File(inputPath.toURI());
        byte[] actualImageArray = imageService.resize(inputFile, MAX_THUMB_WIDTH, MAX_THUMB_HEIGHT);

        URL expectedUrl = ClassLoader.getSystemResource(OUTPUT_PATH + fileExtension);
        File expectedFile = new File(expectedUrl.toURI());
        byte[] expectedImageArray = Files.readAllBytes(expectedFile.toPath());

        assertTrue(Arrays.equals(expectedImageArray, actualImageArray));

    }
}