import de.hbrs.se2.dao.KundenDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegistrierungViewTest {
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
    public void testRegistrierung(){

        WebElement registrieren = driver.findElement(By.id("registrieren"));
        registrieren.click();
        //driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 25);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        WebElement email = driver.findElement(By.id("email"));

        email.sendKeys("hajar8@gmail.de");

        WebElement passwort = driver.findElement(By.id("passwort"));
        passwort.sendKeys("gabiano1");

        WebElement passwortWdh = driver.findElement(By.id("passwortWdh"));
        passwortWdh.sendKeys("gabiano1");

        WebElement name = driver.findElement(By.id("name"));
        name.sendKeys("raja");

        WebElement registration = driver.findElement(By.id("regestrieren"));
        registration.click();
        WebElement login = driver.findElement(By.id("login"));
        Assert.assertNotNull(login);
        kundenDAO.delete("hajar8@gmail.de");




    }


    @After
    public void tearDown(){
        driver.close();
    }

}
