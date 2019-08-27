import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MalleInformationGatherer {

    public static void main(String[] args) {
        //For Mozilla

        //For Chrome
        String localDriver = "";
        localDriver = System.getProperty("user.dir");


        //For Chrome usage:
			//localDriver = localDriver.concat("//chromdriver_1.exe");
			//System.setProperty("webdriver.chrome.driver",localDriver);
        //For Mozilla
			Path currentRelativePath = Paths.get("");
			String s = currentRelativePath.toUri().toString().concat("drivers/geckodriver.exe");
			String[] sNew = s.split("///");
			s = sNew[1].replace("%20", " ");
			System.out.println("Current relative path is: " + s);
			System.setProperty("webdriver.gecko.driver",s);
			List<Reise> ReiseAngebote;

        String[] urlStrings;
        urlStrings = new String[10];

        urlStrings[0] = "https://www.urlaubsguru.de/";
        for(int i = 2; i < 11; i++) {
            urlStrings[(i-1)] = "https://www.urlaubsguru.de/page/" + i +"/";
        }
        for (String k: urlStrings){
            System.out.println("Page will be querried soon: " + k);
        }
        System.out.println("hello world");
        // starte Mozilla FireFox
        WebDriver obj = new FirefoxDriver();
        //WebDriver obj = new ChromeDriver();
        //obj.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //obj.get(urlStrings[0]);

        String[] locations;
        //List<WebElement> elements = obj.findElements(By.cssSelector("a[id^=\"deal-headline-link\""));
        List<String> kurzArr = new ArrayList<>();
        List<String> langArr = new ArrayList<>();
        List<String> prices = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<Reise> WebpagesEntries = new ArrayList<>();
        List<WebElement> rowtext;
        List<WebElement> rowfooters;
        List<WebElement> rowheaders;
        List<WebElement> elements;

        int pageNumber = 0;

        while (pageNumber < urlStrings.length) {
            obj.get(urlStrings[pageNumber]);
            //obj.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            elements = obj.findElements(By.cssSelector("div[class^=\"col-xs-12 content\""));
            if(elements.size() > 0) {
                System.out.println("Page: " + pageNumber + "| Es wurden COL-XS-12 Container gefunden: (maximale zahl an hits auf dieser Seite) = " + elements.size());
                int counter = 0;
                for (WebElement element : elements) {
                    //System.out.println("#Page: " + (pageNumber+1) + ", #elementOnPage: " + counter++);
                    rowtext = element.findElements(By.cssSelector("div[class^=\"row text\""));
                    rowfooters = element.findElements(By.cssSelector("div[class^=\"row footer\""));
                    rowheaders = element.findElements(By.cssSelector("div[class^=\"row header\""));

                    //Reise r = new Reise();
                    for(WebElement element2 : rowtext) {
                        WebElement h2 =  element2.findElement(By.cssSelector(("a[id^=\"deal-headline-link\"")));
                        WebElement h3 =  element2.findElement(By.cssSelector(("a[id^=\"deal-subheadline-link\"")));
                        //System.out.println("h2 = " + h2.getText() + ", h3 = " + h3.getText() + "\n");

                        kurzArr.add(h2.getText());
                        langArr.add(h3.getText());
                    }
                    for(WebElement element3 : rowfooters) {
                        if(element3.findElements(By.cssSelector("span[class^=\"price\"")).size() > 0) {
                            WebElement price = element3.findElement(By.cssSelector("span[class^=\"price\""));
                            prices.add(price.getText().trim()); //+ trim spaces of html object
                        } else {
                            prices.add(" #error");
                        }

                    }

                    for(WebElement element4 : rowheaders) {
                        //WebElement date1 = element4.findElement(By.cssSelector("div[class^=\"date\""));
                        if(element4.findElements(By.cssSelector("div[class^=\"date\"")).size() > 0) {
                            WebElement date1 = element4.findElement(By.cssSelector("div[class=\"date\""));
                            //System.out.println(date1.getText());
                            dates.add(date1.getText().trim()); //+ trim spaces of html object
                        } else {
                            dates.add("no date a/v");
                            System.out.println("Kein Date verfügbar auf: " + (pageNumber+1) + ", #elementOnPage: " + counter++);
                        }


                    }
                }
            } else {
                System.out.println("Size = 0 ");
            }
            pageNumber++;
        }
        // Close Google Chrome
        obj.close();

        System.out.println("Reiseziele: " + kurzArr.size() + ", Kurzbeschreibungen: " + langArr.size() + ", Preise: " + prices.size() + ", Daten: " + dates.size() + "\n");

        //Integrity Check if all hits are equal.
        if(kurzArr.size() == langArr.size() && langArr.size() == prices.size() && prices.size() == dates.size()) {
            for(int i = 0; i < kurzArr.size(); i++) {
                Reise r = new Reise(kurzArr.get(i), langArr.get(i), prices.get(i), dates.get(i));
                WebpagesEntries.add(r);
            }
            int ReiseTippCounter = 1;

            String emailContent = "Mallorca-Reisen:\n";
            int mallorcaCounter = 1;
            for(Reise k : WebpagesEntries) {
                if(k.ReiseZiel.toLowerCase().contains("mallorca")){
                    System.out.println(ReiseTippCounter + ". Angebot - " + k.ReisetoString());
                    emailContent += "\n" + mallorcaCounter++ + ". Angebot..." + k.ReisetoString();
                }
                //System.out.print(ReiseTippCounter + ". Angebot - " + k.ReisetoString() + "\n");
                ReiseTippCounter++;
            }

        } else {
            System.out.println("Different Array sizes of Destination/Description/Prices/Date");

        }
    }

    public static void sendeMail(String contentString) {
        System.setProperty("webdriver.chrome.driver","C:/Users/Marc/Documents/MalleInformer/chromedriver.exe"); // individualized prop
        WebDriver obj = new ChromeDriver();
        obj.get("https://exchange.hs-regensburg.de/owa/"); //individ. mail server

        WebElement userName = obj.findElement(By.id("username"));
        userName.sendKeys("*******");

        WebElement password = obj.findElement(By.id("password"));
        password.sendKeys("*******");

        WebElement login = obj.findElement(By.className("signinTxt"));
        login.click();

        WebElement newMail = obj.findElement(By.id("_ariaId_26"));
        newMail.click();

        WebElement receiver = obj.findElement(By.cssSelector("input[autoid^='_fp_5']"));
        receiver.sendKeys("******"); //individ e-mail
        receiver.sendKeys(Keys.TAB);

        /*WebElement webElement = obj.findElement(By.);//You can use xpath, ID or name whatever you like
        webElement.sendKeys(Keys.TAB);*/

        WebElement subject = obj.findElement(By.cssSelector("input[autoid^='_mcp_c']"));
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyyy-mm-dd | hh:ss");
        subject.sendKeys(dt1.format(new Date()) + " -- Mallorca-Liste von Urlaubsguru");
        subject.sendKeys(Keys.TAB);
        //webElement.sendKeys(Keys.TAB);
        //subject.sendKeys("test");

        /// ab hier stimmts nicht.
        List<WebElement> contents = obj.findElements((By.cssSelector("input[autoid^='_z_l']")));
        List<WebElement> content;
        WebElement firstPContent;
        for(WebElement cntn : contents) {
            content = cntn.findElements(By.tagName("p"));
            if(content.size()>0) {
                firstPContent = content.get(0);
                firstPContent.sendKeys(contentString);
                //WebElement content = obj.findElement((By.cssSelector("#primaryContainer > div:nth-child(7) > div > div._n_T > div > div._n_X > div:nth-child(3) > div > div._n_Y > div.allowTextSelection > div > div._mcp_T2._mcp_W2 > div._mcp_U2._mcp_W2.customScrollBar.scrollContainer._mcp_Y2 > div > div._mcp_d1.ms-border-color-neutralLight > div._mcp_e1.ms-bg-color-white > div:nth-child(2) > div._mcp_z1.ms-border-color-neutralTertiary-hover.ms-border-color-neutralTertiaryAlt > div._mcp_22 > div > div._z_41.ms-bg-color-white > div:nth-child(1) > div:nth-child(3) > div > p:nth-child(1)")));
            } else {
                System.out.println(" KEIN CONTENT....");
            }
        }



        WebElement sendMailButton = obj.findElement(By.xpath("//*[@id=\"primaryContainer\"]/div[5]/div/div[1]/div/div[5]/div[3]/div/div[5]/div[1]/div/div[3]/div[5]/div/div[2]/div[1]/button[1]/span[2]"));
        sendMailButton.click();

        WebElement logOut = obj.findElement(By.xpath("//*[@id=\"_ariaId_156\"]/div/div[2]/div[3]/div/div[4]/button/div/span[2]"));
        logOut.click();

        obj.close();
    }
}
