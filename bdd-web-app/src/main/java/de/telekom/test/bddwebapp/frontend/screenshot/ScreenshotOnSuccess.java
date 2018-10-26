package de.telekom.test.bddwebapp.frontend.screenshot;

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import static java.text.MessageFormat.format;
import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Take screenshot on success and save to file system
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@AllArgsConstructor
public class ScreenshotOnSuccess {

    private final Logger logger = LoggerFactory.getLogger(ScreenshotOnSuccess.class);

    @NonNull
    protected final StoryReporterBuilder reporterBuilder;
    @NonNull
    protected final WebDriverWrapper webDriverWrapper;

    public String makeScreenshot(String storyFolder, String step) {
        WebDriver driver = webDriverWrapper.getDriver();
        String currentUrl;
        if (driver != null && isNotBlank(currentUrl = driver.getCurrentUrl())) {
            try {
                logger.info(format("Make Screenshot for story folder: \"{0}\" step: \"{1}\"", storyFolder, step));
                URL url = new URL(currentUrl);
                String contextPath = url.getPath();
                String screenshotName = getScreenshotName(contextPath);
                String screenshotPath = format("{0}/screenshots/{1}/{2}.png", reporterBuilder.outputDirectory(), storyFolder, screenshotName);
                screenshotPath = webDriverWrapper.saveScreenshotTo(screenshotPath);
                if (isNoneBlank(screenshotPath) && screenshotIsNotEmpty(screenshotPath)) {
                    logger.info(format("Screenshot of page \"{0}\" has been saved to \"{1}\"", contextPath, screenshotPath));
                    return format("../screenshots/{0}/{1}.png", storyFolder, screenshotName);
                }
            } catch (Exception e) {
                logger.error(format("Screenshot failed for story folder: \"{0}\" step: \"{1}\"", storyFolder, step));
            }
        }
        return null;
    }

    protected String getScreenshotName(String contextPath) {
        String screenshotName = contextPath.replaceAll("/", ".");
        screenshotName = screenshotName.substring(1);
        screenshotName = screenshotName.replaceAll(".xhtml", "");
        return "success-" + new Date().getTime() + "_" + screenshotName;
    }

    protected boolean screenshotIsNotEmpty(String screenshotPath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(screenshotPath));
            return !isEmpty(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean isEmpty(BufferedImage bufferedImage) {
        Raster raster = bufferedImage.getData();
        return range(0, raster.getWidth()).noneMatch(x ->
                range(0, raster.getHeight()).anyMatch(y -> bufferedImage.getRGB(x, y) != 0xFFFFFFFF));
    }

}
