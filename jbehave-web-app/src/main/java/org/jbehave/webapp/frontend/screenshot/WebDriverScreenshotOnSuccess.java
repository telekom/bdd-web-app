package org.jbehave.webapp.frontend.screenshot;

import org.jbehave.webapp.frontend.lifecycle.WebDriverWrapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.reporters.StoryReporterBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import static java.text.MessageFormat.format;

@AllArgsConstructor
public class WebDriverScreenshotOnSuccess {

	private final StoryReporterBuilder reporterBuilder;
	private final WebDriverWrapper webDriverWrapper;

	public void makeScreenshot(String storyFolder, String step) {
		long timestamp = new Date().getTime();
		String currentUrl = webDriverWrapper.getDriver().getCurrentUrl();
		if (StringUtils.isBlank(currentUrl)) {
			return;
		}
		try {
			System.out.println(format("Make Screenshot for story folder: \"{0}\" step: \"{1}\" timestamp: \"{2}", storyFolder, step, timestamp));
			URL url = new URL(currentUrl);
			String contextPath = url.getPath();
			String screenshotName = getScreenshotName(contextPath, timestamp);
			String screenshotPath = format("{0}/screenshots/{1}/{2}.png", reporterBuilder.outputDirectory(), storyFolder, screenshotName);
			webDriverWrapper.saveScreenshotTo(screenshotPath);
			if (screenshotIsBlank(screenshotPath)) {
				System.out.println(format("Screenshot of page \"{0}\" has been saved to \"{1}\"", contextPath, screenshotPath));
			}
		} catch (Exception e) {
			System.out.println(format("Screenshot failed for story folder: \"{0}\" step: \"{1}\" timestamp: \"{2}", storyFolder, step, timestamp));
		}
	}

	private String getScreenshotName(String contextPath, long timestamp) {
		String screenshotName = contextPath.replaceAll("/", ".");
		screenshotName = screenshotName.substring(1, screenshotName.length());
		screenshotName = screenshotName.replaceAll(".xhtml", "");
		return "success-" + timestamp + "_" + screenshotName;
	}

	private boolean screenshotIsBlank(String screenshotPath) {
		try {
			BufferedImage bufferedImage = ImageIO.read(new FileInputStream(screenshotPath));
			return isBlank(bufferedImage);
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
	}

	private boolean isBlank(BufferedImage bufferedImage) {
		Raster raster = bufferedImage.getAlphaRaster();
		boolean returnT = true;
		byte[][] pixels;
		if (raster != null) {
			pixels = new byte[bufferedImage.getWidth()][];
			for (int x = 0; x < raster.getWidth(); x++) {
				pixels[x] = new byte[raster.getHeight()];
				for (int y = 0; y < raster.getHeight(); y++) {
					pixels[x][y] =
							(byte) (bufferedImage.getRGB(x, y) == 0xFFFFFFFF ? 0
									: 1);
					returnT = true;
				}
			}

			for (int x = 0; x < raster.getWidth(); x++) {
				for (int y = 0; y < raster.getHeight(); y++) {
					if (pixels[x][y] != 0) {
						returnT = false;
						break;
					}
				}
				if (!returnT) {
					break;
				}

			}
		}
		return returnT;
	}

}
