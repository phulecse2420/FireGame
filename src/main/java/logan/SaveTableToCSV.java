package logan;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SaveTableToCSV {

    public static void main (String[] args) throws IOException {
        // Run chrome in dev mode: google-chrome --remote-debugging-port=9222 --user-data-dir="/tmp/chrome-dev"
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // Set up ChromeOptions to connect to the running instance
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--user-data-dir=/tmp/chrome-dev");

        // Initialize WebDriver with the specified options
        WebDriver driver = new ChromeDriver(options);

        var topics = List.of(
            "events.gam-advertiser-v202405", "events.gam-creative-v202405",
            "events.gam-creative-city-stats-v202405", "events.gam-creative-country-stats-v202405",
            "events.gam-creative-device-category-stats-v202405", "events.gam-creative-metro-stats-v202405",
            "events.gam-creative-placement-stats-v202405", "events.gam-creative-stats-v202405",
            "events.gam-creative-targeting-stats-v202405", "events.gam-lineitem-v202405",
            "events.gam-lineitem-city-stats-v202405", "events.gam-lineitem-creative-association-v202405",
            "events.gam-lineitem-metro-stats-v202405", "events.gam-lineitem-postal-code-stats-v202405",
            "events.gam-lineitem-region-stats-v202405", "events.gam-lineitem-stats-v202405",
            "events.gam-lineitem-unique-stats-lifetime-v202405",
            "events.gam-master-companion-creative-stats-v202405", "events.gam-order-v202405", "gam_advertiser",
            "gam_creative", "gam_creative_city_stats", "gam_creative_country_stats",
            "gam_creative_device_category_stats", "gam_creative_metro_stats", "gam_creative_placement_stats",
            "gam_creative_stats", "gam_creative_targeting_stats", "gam_lineitem", "gam_lineitem_city_stats",
            "gam_lineitem_creative_association", "gam_lineitem_metro_stats", "gam_lineitem_postal_code_stats",
            "gam_lineitem_region_stats", "gam_lineitem_stats", "gam_lineitem_unique_stats_lifetime",
            "gam_master_companion_creative_stats", "gam_order"
        );
        var outputFile = "output_" + System.currentTimeMillis() + ".csv";
        try (
            FileWriter writer = new FileWriter("./output/" + outputFile);
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)
        ) {
            topics.forEach(t -> {
                try {
                    downloadForTopic(driver, csvPrinter, t);
                }
                catch (IOException e) {
                    log.error("[{}] Error", t, e);
                }
            });
        }

    }

    private static void downloadForTopic (WebDriver driver, CSVPrinter csvPrinter, String topic) throws IOException {
        driver.get("https://akhq.unified.com/ui/prod-kafka/topic/" + topic + "/partitions");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(d -> {
            try {
                WebElement table = driver.findElement(By.className("table-striped"));
                if (table.isDisplayed()) {
                    List<WebElement> rows = table.findElements(By.tagName("tr"));
                    return rows.size() > 2;
                }
            } catch (Exception e) {
                // ignore
            }
            return false;
        });

        WebElement table = driver.findElement(By.className("table-striped"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (int i = 1; i < rows.size(); i++) {
            var cells = rows.get(i).findElements(By.tagName("td"));
            var data = new String[] { topic, cells.get(0).getText(), cells.get(3).getText() };
            csvPrinter.printRecord((Object[]) data);
        }
        csvPrinter.flush();

        log.info("Processed topic [{}]", topic);
    }
}
