package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class AmazonPurchaseFlowTest {

    Page page;
    Browser browser;

    @BeforeTest
    public void setup() {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().launch();
        BrowserContext context = browser.newContext();
        page = context.newPage();
    }

    @Test
    public void AmazonSanityFlow() {
        page.navigate("https://amazon.in");
        page.waitForSelector("//input[@id='twotabsearchtextbox']");
        page.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Search Amazon.in")).fill("HP smart tank");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Go").setExact(true)).click();
        page.waitForSelector("//div[@role='listitem']//*[contains(text(),'Smart Tank 589')]");
        page.click("//div[@role='listitem']//*[contains(text(),'Smart Tank 589')]");
        page.waitForTimeout(1000);

        String price = page.locator("//span[@class='a-price-whole']").first().textContent();

        Page page1 = page.waitForPopup(() -> {
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Smart Tank 589 All-in-One WiFi Colour Printer |Up to 4000 Black & 6000 Colour Prints I Print,Scan & Copy for Home/Office").setExact(true)).click();
        });
        page1.locator("#a-autoid-6-announce").getByText("1", new Locator.GetByTextOptions().setExact(true)).click();
        page1.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("2").setExact(true)).click();
        page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart").setExact(true)).click();
        page1.waitForSelector("//span[contains(text(),'Cart subtotal: ')]");

        String doublePrice = page1.locator("//span[@id='sw-subtotal-item-count']//following-sibling::span//span[@class='a-offscreen']").textContent();
        Assert.assertTrue(page1.locator("//span[contains(text(),'Cart subtotal: ')]").isVisible(), "Cant find Cart Subtotal");

        String cleanedPrice = price.replaceAll("[^\\d]", "");
        int unitPrice = Integer.parseInt(cleanedPrice);
        String cleaned = doublePrice.replaceAll("[^0-9.]", "");
        BigDecimal bd = new BigDecimal(cleaned);
        bd = bd.stripTrailingZeros();
        int dPrice = bd.intValue();
        Assert.assertEquals(dPrice, unitPrice * 2, "Price mismatch for 2 items");

        page1.locator("//a[normalize-space()='Go to Cart']").first().click();
        page.waitForTimeout(2000);
        Assert.assertTrue(page1.locator("//h2[normalize-space()='Shopping Cart']").isVisible(), "Shopping Cart not visible");
        Assert.assertTrue(page1.locator(".a-truncate-cut").filter(new Locator.FilterOptions().setHasText("Smart Tank 589")).isVisible());
        Assert.assertEquals(page1.locator("[data-a-selector='inner-value']").textContent(), "2","less items found in cart");
    }

    @AfterTest
    public void tearDown() {
        page.close();
        browser.close();
    }
}