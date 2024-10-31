package com.qualitystream.tutorial;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RecruitmentAutomationTest {

    private WebDriver driver;
    private WebDriverWait wait;

    // Localizadores de elementos
    By usernameLocator = By.name("username");
    By passwordLocator = By.name("password");
    By loginButtonLocator = By.xpath("//button[@type='submit']");
    By recruitmentTabLocator = By.xpath("//a[@href='/web/index.php/recruitment/viewRecruitmentModule']");
    By addButtonLocator = By.xpath("//i[contains(@class, 'bi-plus')]/..");

    // Localizadores de campos de creación de candidato
    By jobTitleLocator = By.name("jobTitle");
    By vacancyLocator = By.name("vacancy");
    By hiringManagerLocator = By.name("hiringManager");
    By statusLocator = By.name("status");
    By candidateNameLocator = By.name("candidateName");
    By keywordsLocator = By.name("keywords");
    By dateOfApplicationLocator = By.name("dateOfApplication");
    By methodOfApplicationLocator = By.name("methodOfApplication");
    By saveButtonLocator = By.xpath("//button[contains(text(), 'Save')]");

    // Datos del candidato
    String expectedCandidateName = "John Doe";
    String expectedStatus = "Hired";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void recruitmentProcess() {
        // Login
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameLocator)).sendKeys("Admin");
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocator)).sendKeys("admin123");
        wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator)).click();
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']"))).isDisplayed());

        // Navegar a la pestaña de Recruitment
        wait.until(ExpectedConditions.elementToBeClickable(recruitmentTabLocator)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Candidates']")));

        // Hacer clic en el botón "+ Add"
        wait.until(ExpectedConditions.elementToBeClickable(addButtonLocator)).click();

        // Rellenar los campos para crear un candidato
        wait.until(ExpectedConditions.visibilityOfElementLocated(jobTitleLocator)).sendKeys("Software Engineer");
        wait.until(ExpectedConditions.visibilityOfElementLocated(vacancyLocator)).sendKeys("Vacante1");
        wait.until(ExpectedConditions.visibilityOfElementLocated(hiringManagerLocator)).sendKeys("Manager Name");
        selectDropdownByVisibleText(statusLocator, "Hired");
        wait.until(ExpectedConditions.visibilityOfElementLocated(candidateNameLocator)).sendKeys(expectedCandidateName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(keywordsLocator)).sendKeys("Java, Selenium");
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateOfApplicationLocator)).sendKeys("2024-10-30");
        selectDropdownByVisibleText(methodOfApplicationLocator, "Online");

        // Guardar los datos del candidato
        wait.until(ExpectedConditions.elementToBeClickable(saveButtonLocator)).click();

        // Validar que los datos del candidato se hayan guardado
        validateCandidateData(expectedCandidateName, expectedStatus);
    }

    private void selectDropdownByVisibleText(By locator, String visibleText) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);
    }

    private void validateCandidateData(String candidateName, String status) {
        
    }
}