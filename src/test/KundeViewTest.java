import de.hbrs.se2.dao.KundenDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class KundeViewTest {

    private WebDriver driver;
    private KundenDAO kundenDAO;

    @Before
    public void setUp() throws Exception {
        //setzen den Chrome driver in system umgebung
        //pfad zu Chrome driver
        String CHROME_PATH = "driver/chromedriver.exe";//systemproperty key
        String SYSTEM_PATH = "webdriver.chrome.driver";
        System.setProperty(SYSTEM_PATH, CHROME_PATH);
        //erzeugungen den Chrome driver
        driver = new ChromeDriver();
        //nach ausführung einer Action warte von 15s
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        //URL aufrufen
        //Webseite URL

        String URL = "http://localhost:8080/login";
        driver.get(URL);
        kundenDAO = new KundenDAO();
    }

    @Test
    public void testSuche() {
        WebElement email = driver.findElement(By.id("email"));
        //in der gefundene inpult feld soll die user name salda2s eingegeben werden
        email.sendKeys("hajar@gmail.de");

        WebElement passwort = driver.findElement(By.id("passwort"));
        passwort.sendKeys("gabiano1");

        WebElement login = driver.findElement(By.id("login"));
        login.click();

        WebElement search = driver.findElement(By.id("search"));
        search.sendKeys("bmw");


        WebElement suche = driver.findElement(By.id("suche"));
        suche.click();
        //driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);





        WebElement cancel = driver.findElement(By.id("cancel"));


        WebDriverWait wait = new WebDriverWait(driver, 25);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cancel")));
        List<WebElement> result = driver.findElements(By.tagName("tr"));

        Assert.assertEquals(3, result.size());
        cancel.click();



    }
}