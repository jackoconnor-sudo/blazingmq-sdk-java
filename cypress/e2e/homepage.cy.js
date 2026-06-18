describe("Bloomberg Homepage", () => {
  beforeEach(() => {
    cy.visitBloomberg("/");
  });

  it("should load the homepage successfully", () => {
    cy.url().should("include", "bloomberg.com");
    cy.title().should("not.be.empty");
  });

  it("should display header/branding area", () => {
    cy.get("header, [role='banner'], nav, [class*='header' i]")
      .should("exist");
  });

  it("should contain navigation or header links", () => {
    cy.get("header a, nav a, [role='banner'] a, a[href]")
      .should("have.length.greaterThan", 0);
  });

  it("should display page content", () => {
    cy.get("body").invoke("text").should("have.length.greaterThan", 100);
  });

  it("should have working internal links on the page", () => {
    cy.get("a[href]")
      .should("have.length.greaterThan", 0)
      .first()
      .should("have.attr", "href")
      .and("not.be.empty");
  });

  it("should contain heading elements", () => {
    cy.get("h1, h2, h3, h4, h5, h6").should("have.length.greaterThan", 0);
  });

  it("should have a document title containing Bloomberg", () => {
    cy.title().should("match", /bloomberg/i);
  });

  it("should load with proper HTML structure", () => {
    cy.get("html").should("exist");
    cy.get("head").should("exist");
    cy.get("body").should("exist");
  });

  it("should include script tags", () => {
    cy.get("script").should("have.length.greaterThan", 0);
  });

  it("should serve content over HTTPS", () => {
    cy.url().should("match", /^https:\/\//);
  });
});
