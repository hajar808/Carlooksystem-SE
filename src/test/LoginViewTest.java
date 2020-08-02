import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class LoginViewTest {

    private WebDriver driver;

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
    }

    @Test
    public void testLogin(){
        WebElement email = driver.findElement(By.id("email"));
        //in der gefundene inpult feld soll die user name salda2s eingegeben werden
        email.sendKeys("hajar@carlook.de");

        WebElement passwort = driver.findElement(By.id("passwort"));
        passwort.sendKeys("gabiano");

        WebElement login = driver.findElement(By.id("login"));
        login.click();

        WebElement logout = driver.findElement(By.id("logout"));
        Assert.assertNotNull(logout);

        WebElement email_lbl = driver.findElement(By.id("email_lbl"));
        Assert.assertNotNull(email_lbl);






    }
    @After
    public void tearDown(){
        driver.close();
    }


}
