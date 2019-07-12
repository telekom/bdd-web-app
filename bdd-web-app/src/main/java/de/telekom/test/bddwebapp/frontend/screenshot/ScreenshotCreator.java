package de.telekom.test.bddwebapp.frontend.screenshot;

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jbehave.core.reporters.StoryReporterBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import static java.text.MessageFormat.format;
import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;

/**
 * Create screenshot and save it to file system.
 * Return the screenshot URL relative of story report.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@AllArgsConstructor
@Slf4j
public class ScreenshotCreator {

    protected static String SCREENSHOT_PATH = "{0}/screenshots/{1}/{2}.png";

    @NonNull
    protected final StoryReporterBuilder reporterBuilder;
    @NonNull
    protected final WebDriverWrapper webDriverWrapper;

    /**
     * Create screenshot for given step.
     *
     * @param storyFolder - The current story folder. Every story has it's own folder
     * @param step        - The current step
     * @param status      - The status of the current step: successful, failed
     * @return The URL of the created screenshot as String. If no screenshot was created, the result is null.
     */
    public String createScreenshot(String storyFolder, String step, String status) {
        if (webDriverWrapper.getDriver() == null) {
            log.debug("No failure screenshot was taken because WebDriver is null. " +
                    "Maybe the WebDriver was closed or the current story is in ApiOnly mode");
            return null;
        }
        try {
            log.info(format("Make Screenshot for story folder: \"{0}\" step: \"{1}\"", storyFolder, step));
            String screenshotName = screenshotName(step, status);
            String screenshotPath = format(SCREENSHOT_PATH, reporterBuilder.outputDirectory(), storyFolder, screenshotName);
            screenshotPath = webDriverWrapper.createScreenshot(screenshotPath);
            if (isNoneBlank(screenshotPath) && screenshotIsNotEmpty(screenshotPath)) {
                return screenshotUrl(storyFolder, step, screenshotName, screenshotPath);
            }
        } catch (Exception e) {
            log.error(format("Screenshot failed for story folder: \"{0}\" step: \"{1}\"", storyFolder, step));
        }
        return null;
    }

    /**
     * Create screenshot name from step, status and timestamp.
     *
     * @param step   - The step name
     * @param status - Status of the current step
     * @return The screenshot name
     */
    protected String screenshotName(String step, String status) {
        step = step.replaceAll("\\s", "_"); // replace space by underscore
        step = step.replaceAll("\"[.*\"]", ""); // remove params
        return step + "_" + new Date().getTime() + "_" + status;
    }

    protected boolean screenshotIsNotEmpty(String screenshotPath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(screenshotPath));
            Raster raster = bufferedImage.getData();
            return !(range(0, raster.getWidth()).noneMatch(x ->
                    range(0, raster.getHeight()).anyMatch(y -> bufferedImage.getRGB(x, y) != 0xFFFFFFFF)));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected String screenshotUrl(String storyFolder, String step, String screenshotName, String screenshotPath) {
        // log screenshot creation with step, path and current url
        String urlPath;
        try {
            urlPath = new URL(webDriverWrapper.getDriver().getCurrentUrl()).getPath();
        } catch (Exception e) {
            urlPath = "[unknown url]";
        }
        log.info(format("Screenshot of step \"{0}\" with url \"{1}\" has been saved to \"{2}\"", step, urlPath, screenshotPath));
        // screenshot name url encoding
        try {
            screenshotName = URLEncoder.encode(screenshotName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("Exception at screenshot name url encoding", e);
        }
        // get the screenshot path relative to the story report
        return format(SCREENSHOT_PATH, "..", storyFolder, screenshotName);
    }

}
