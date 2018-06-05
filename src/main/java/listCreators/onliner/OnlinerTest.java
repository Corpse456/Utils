package listCreators.onliner;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class OnlinerTest {
    protected WebDriver driver;
    
    @Test
    public void Videcard () {
        String url = "https://catalog.onliner.by/videocard";

        driver.get(url);
        
        List<WebElement> title = driver.findElements(By.xpath("//*[@class='schema-product__group']"));
        System.out.println(title.size());
    }
    
    @Before
    public void initDriver() throws InterruptedException {
        driver = new ChromeDriver();
    }
    
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        driver.quit();
    }
}
