// Custom Cypress commands for Bloomberg E2E tests

// Check if the current page is a bot detection/CAPTCHA page
Cypress.Commands.add("isBlockedByBot", () => {
  return cy.get("body").invoke("text").then((text) => {
    return (
      text.includes("unusual activity") ||
      text.includes("not a robot") ||
      text.includes("captcha") ||
      text.includes("Block reference ID")
    );
  });
});

// Skip the current test if bot detection is active
Cypress.Commands.add("skipIfBlocked", () => {
  cy.get("body")
    .invoke("text")
    .then((text) => {
      if (
        text.includes("unusual activity") ||
        text.includes("not a robot") ||
        text.includes("Block reference ID")
      ) {
        cy.log("**Bot detection active — skipping test**");
        // Use Cypress.currentTest to annotate
        return true;
      }
      return false;
    });
});

// Navigate and wait for Bloomberg page to load
Cypress.Commands.add("visitBloomberg", (path = "/") => {
  cy.visit(path, {
    headers: {
      "Accept-Language": "en-US,en;q=0.9",
      Accept:
        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
    },
    failOnStatusCode: false,
  });
  cy.waitForPageLoad();
});
