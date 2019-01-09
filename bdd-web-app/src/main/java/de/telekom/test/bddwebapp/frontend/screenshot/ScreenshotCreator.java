package de.telekom.test.bddwebapp.frontend.screenshot;

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jbehave.core.reporters.StoryReporterBuilder;
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

/**
 * Create screenshot and save to file system
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@AllArgsConstructor
public class ScreenshotCreator {

    private final Logger logger = LoggerFactory.getLogger(ScreenshotCreator.class);

    @NonNull
    protected final StoryReporterBuilder reporterBuilder;
    @NonNull
    protected final WebDriverWrapper webDriverWrapper;

    public String createScreenshot(String storyFolder, String step, String status) {
        if (webDriverWrapper.getDriver() == null) {
            logger.debug("No failure screenshot was taken because WebDriver is null");
            return null;
        }
        try {
            logger.info(format("Make Screenshot for story folder: \"{0}\" step: \"{1}\"", storyFolder, step));
            String screenshotName = screenshotName(step, status);
            String screenshotPath = screenshotPath(storyFolder, screenshotName);
            screenshotPath = webDriverWrapper.createScreenshot(screenshotPath);
            if (isNoneBlank(screenshotPath) && screenshotIsNotEmpty(screenshotPath)) {
                String urlPath = urlPath();
                logger.info(format("Screenshot of step \"{0}\" with url \"{1}\" has been saved to \"{2}\"", step, urlPath, screenshotPath));
                return format("../screenshots/{0}/{1}.png", storyFolder, screenshotName);
            }
        } catch (Exception e) {
            logger.error(format("Screenshot failed for story folder: \"{0}\" step: \"{1}\"", storyFolder, step));
        }
        return null;
    }

    protected String screenshotName(String step, String status) {
        step = step.replaceAll("\\s", "_"); // replace space by underscore
        step = step.replaceAll("\"[.*\"]", ""); // remove params
        return step + "_" + new Date().getTime() + "_" + status;
    }

    protected String screenshotPath(String folder, String name) {
        return format("{0}/screenshots/{1}/{2}.png", reporterBuilder.outputDirectory(), folder, name);
    }

    protected boolean screenshotIsNotEmpty(String screenshotPath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(screenshotPath));
            return !isImageEmpty(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean isImageEmpty(BufferedImage bufferedImage) {
        Raster raster = bufferedImage.getData();
        return range(0, raster.getWidth()).noneMatch(x ->
                range(0, raster.getHeight()).anyMatch(y -> bufferedImage.getRGB(x, y) != 0xFFFFFFFF));
    }

    protected String urlPath() {
        try {
            return new URL(webDriverWrapper.getDriver().getCurrentUrl()).getPath();
        } catch (Exception e) {
            return "[unknown url]";
        }
    }

}
