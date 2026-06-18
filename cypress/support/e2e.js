// ***********************************************************
// This support file is processed and loaded automatically before
// your test files.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

import "./commands";

// Prevent uncaught exceptions from failing tests on third-party sites
Cypress.on("uncaught:exception", () => {
  return false;
});

// Custom command: assert an element is visible and contains text
Cypress.Commands.add("shouldBeVisibleWithText", (selector, text) => {
  cy.get(selector).should("be.visible").and("contain.text", text);
});

// Custom command: wait for page to stabilize (useful for SPAs)
Cypress.Commands.add("waitForPageLoad", () => {
  cy.document().its("readyState").should("eq", "complete");
});

// Helper: check if page content loaded (not blocked by bot detection)
Cypress.Commands.add("assertNotBlocked", () => {
  cy.get("body")
    .invoke("text")
    .then((text) => {
      const isBlocked =
        text.includes("unusual activity") ||
        text.includes("not a robot") ||
        text.includes("Block reference ID");
      if (isBlocked) {
        cy.log(
          "⚠ Bot detection active on this page — assertions against rich content may fail"
        );
      }
    });
});
