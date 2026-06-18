describe("Bloomberg Markets Page", () => {
  beforeEach(() => {
    cy.visitBloomberg("/markets");
  });

  it("should load the markets URL", () => {
    cy.url().should("include", "bloomberg.com");
    cy.title().should("not.be.empty");
  });

  it("should display page content", () => {
    cy.get("body").invoke("text").should("have.length.greaterThan", 50);
  });

  it("should contain market-related text", () => {
    cy.get("body")
      .invoke("text")
      .then((text) => {
        const lower = text.toLowerCase();
        const hasMarketContent =
          lower.includes("market") ||
          lower.includes("bloomberg") ||
          lower.includes("subscribe");
        expect(hasMarketContent).to.be.true;
      });
  });

  it("should have links on the markets page", () => {
    cy.get("a[href]").should("have.length.greaterThan", 0);
  });

  it("should have heading elements", () => {
    cy.get("h1, h2, h3, h4, h5, h6").should("have.length.greaterThan", 0);
  });

  it("should serve the page over HTTPS", () => {
    cy.url().should("match", /^https:\/\//);
  });

  it("should have a valid HTML document", () => {
    cy.document().its("doctype").should("not.be.undefined");
  });
});
