describe("Bloomberg Navigation", () => {
  beforeEach(() => {
    cy.visitBloomberg("/");
  });

  it("should have links in the header area", () => {
    cy.get("header a, [role='banner'] a, nav a, a[href]")
      .should("have.length.greaterThan", 0);
  });

  it("should have links pointing to bloomberg.com subpages", () => {
    cy.get("a[href]").then(($links) => {
      const hrefs = [...$links]
        .map((el) => el.getAttribute("href") || "")
        .filter(
          (h) =>
            h.includes("bloomberg.com") || (h.startsWith("/") && h.length > 1)
        );
      expect(hrefs.length).to.be.greaterThan(0);
    });
  });

  it("should navigate to /markets without crashing", () => {
    cy.visitBloomberg("/markets");
    cy.url().should("include", "bloomberg.com");
    cy.get("body").should("be.visible");
    cy.title().should("not.be.empty");
  });

  it("should navigate to /technology without crashing", () => {
    cy.visitBloomberg("/technology");
    cy.url().should("include", "bloomberg.com");
    cy.get("body").should("be.visible");
    cy.title().should("not.be.empty");
  });

  it("should navigate to /politics without crashing", () => {
    cy.visitBloomberg("/politics");
    cy.url().should("include", "bloomberg.com");
    cy.get("body").should("be.visible");
    cy.title().should("not.be.empty");
  });

  it("should navigate to /opinion without crashing", () => {
    cy.visitBloomberg("/opinion");
    cy.url().should("include", "bloomberg.com");
    cy.get("body").should("be.visible");
    cy.title().should("not.be.empty");
  });

  it("should have accessible landmark elements", () => {
    cy.get(
      'nav, [role="navigation"], [role="banner"], header, [role="main"], main, footer, [role="contentinfo"]'
    ).should("have.length.greaterThan", 0);
  });

  it("should contain section-related link text", () => {
    cy.get("a")
      .invoke("text")
      .then((allText) => {
        const lower = allText.toLowerCase();
        // At least one section keyword should appear somewhere in link text
        const keywords = [
          "market",
          "business",
          "tech",
          "politic",
          "opinion",
          "subscribe",
          "sign in",
          "economics",
        ];
        const found = keywords.some((kw) => lower.includes(kw));
        expect(found).to.be.true;
      });
  });
});
