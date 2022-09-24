package com.learn.playwright;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Paths;
import java.util.*;

public class Example {
  public static void main(String[] args) {
    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
        .setHeadless(false));
      BrowserContext context = browser.newContext();
      
      context.tracing().start(new Tracing.StartOptions()
    		  .setScreenshots(true)
    		  .setSnapshots(true)
    		  .setSources(true));
      
           // Open new page
      Page page = context.newPage();

      // Go to https://www.saucedemo.com/
      page.navigate("https://www.saucedemo.com/");

      // Click [data-test="username"]
      page.locator("[data-test=\"username\"]").click();

      // Fill [data-test="username"]
      page.locator("[data-test=\"username\"]").fill("standard_user");

      // Press Tab
      page.locator("[data-test=\"username\"]").press("Tab");

      // Fill [data-test="password"]
      page.locator("[data-test=\"password\"]").fill("secret_sauce");

      // Click [data-test="login-button"]
      page.locator("[data-test=\"login-button\"]").click();
      assertThat(page).hasURL("https://www.saucedemo.com/inventory.html");
      
      context.tracing().stop(new Tracing.StopOptions()
    		  .setPath(Paths.get("trace.zip")));
    }
  }
}