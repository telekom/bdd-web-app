package de.telekom.jbehave.webapp.frontend.screenshot;

import de.telekom.jbehave.webapp.frontend.lifecycle.WebDriverWrapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

/**
 * Take screenshot on success and save to file system
 *
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@AllArgsConstructor
public class ScreenshotOnSuccess {

    private final static Logger LOGGER = LoggerFactory.getLogger(ScreenshotOnSuccess.class);

    private final StoryReporterBuilder reporterBuilder;
    private final WebDriverWrapper webDriverWrapper;

    public String makeScreenshot(String storyFolder, String step) {
        long timestamp = new Date().getTime();
        String currentUrl = webDriverWrapper.getDriver().getCurrentUrl();
        if (StringUtils.isBlank(currentUrl)) {
            return null;
        }
        try {
            LOGGER.info(format("Make Screenshot for story folder: \"{0}\" step: \"{1}\"", storyFolder, step));
            URL url = new URL(currentUrl);
            String contextPath = url.getPath();
            String screenshotName = getScreenshotName(contextPath, timestamp);
            String screenshotPath = format("{0}/screenshots/{1}/{2}.png", reporterBuilder.outputDirectory(), storyFolder, screenshotName);
            screenshotPath = webDriverWrapper.saveScreenshotTo(screenshotPath);
            if (StringUtils.isNoneBlank(screenshotPath) && screenshotIsNotEmpty(screenshotPath)) {
                LOGGER.info(format("Screenshot of page \"{0}\" has been saved to \"{1}\"", contextPath, screenshotPath));
                return format("../screenshots/{0}/{1}.png", storyFolder, screenshotName);
            }
        } catch (Exception e) {
            LOGGER.error(format("Screenshot failed for story folder: \"{0}\" step: \"{1}\"", storyFolder, step));
        }
        return null;
    }

    private String getScreenshotName(String contextPath, long timestamp) {
        String screenshotName = contextPath.replaceAll("/", ".");
        screenshotName = screenshotName.substring(1, screenshotName.length());
        screenshotName = screenshotName.replaceAll(".xhtml", "");
        return "success-" + timestamp + "_" + screenshotName;
    }

    private boolean screenshotIsNotEmpty(String screenshotPath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(screenshotPath));
            return !isEmpty(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmpty(BufferedImage bufferedImage) {
        Raster raster = bufferedImage.getData();
        for (int x = 0; x < raster.getWidth(); x++) {
            for (int y = 0; y < raster.getHeight(); y++) {
                if (bufferedImage.getRGB(x, y) != 0xFFFFFFFF) {
                    return false;
                }
            }
        }
        return true;
    }

}
