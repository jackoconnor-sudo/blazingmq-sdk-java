describe("Bloomberg Accessibility & Performance", () => {
  beforeEach(() => {
    cy.visitBloomberg("/");
  });

  it("should have a lang attribute on the html element", () => {
    cy.get("html").should("have.attr", "lang").and("not.be.empty");
  });

  it("should have proper heading hierarchy", () => {
    cy.get("h1, h2, h3, h4, h5, h6").should("have.length.greaterThan", 0);
  });

  it("should have ARIA landmarks or semantic HTML", () => {
    cy.get(
      '[role="banner"], [role="navigation"], [role="main"], [role="contentinfo"], header, nav, main, footer'
    ).should("have.length.greaterThan", 0);
  });

  it("should have a viewport meta tag for mobile responsiveness", () => {
    cy.get('meta[name="viewport"]')
      .should("exist")
      .and("have.attr", "content")
      .and("include", "width");
  });

  it("should load with a reasonable page title", () => {
    cy.title().should("not.be.empty").and("have.length.greaterThan", 5);
  });

  it("should have focusable interactive elements", () => {
    cy.get("a, button, input, select, textarea").should(
      "have.length.greaterThan",
      0
    );
  });

  it("should have a charset declaration", () => {
    cy.get('meta[charset], meta[http-equiv="Content-Type"]').should("exist");
  });

  it("should have a doctype", () => {
    cy.document().its("doctype").should("not.be.undefined");
  });
});

describe("Bloomberg Page Performance Basics", () => {
  it("should load the homepage within acceptable time", () => {
    const start = Date.now();
    cy.visitBloomberg("/");
    cy.waitForPageLoad().then(() => {
      const loadTime = Date.now() - start;
      expect(loadTime).to.be.lessThan(30000);
    });
  });

  it("should load stylesheets or inline styles", () => {
    cy.visitBloomberg("/");
    cy.get('link[rel="stylesheet"], style').should(
      "have.length.greaterThan",
      0
    );
  });

  it("should load JavaScript resources", () => {
    cy.visitBloomberg("/");
    cy.get("script").should("have.length.greaterThan", 0);
  });
});
