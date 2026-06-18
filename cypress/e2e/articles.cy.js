describe("Bloomberg Articles & News", () => {
  it("should load the homepage with link elements", () => {
    cy.visitBloomberg("/");
    cy.get("a[href]").should("have.length.greaterThan", 0);
  });

  it("should have links that could lead to articles", () => {
    cy.visitBloomberg("/");
    cy.get("a[href]").then(($links) => {
      const hrefs = [...$links].map((el) => el.getAttribute("href") || "");
      // Bloomberg links may include /news/, /articles/, or various section paths
      expect(hrefs.length).to.be.greaterThan(5);
    });
  });

  it("should load the economics section page", () => {
    cy.visitBloomberg("/economics");
    cy.url().should("include", "bloomberg.com");
    cy.get("body").should("be.visible");
    cy.title().should("not.be.empty");
  });

  it("should load the technology section page", () => {
    cy.visitBloomberg("/technology");
    cy.url().should("include", "bloomberg.com");
    cy.get("body").should("be.visible");
    cy.title().should("not.be.empty");
  });

  it("should display content on section pages", () => {
    cy.visitBloomberg("/technology");
    cy.get("body").invoke("text").should("have.length.greaterThan", 50);
  });

  it("should have heading elements on section pages", () => {
    cy.visitBloomberg("/technology");
    cy.get("h1, h2, h3, h4, h5, h6").should("have.length.greaterThan", 0);
  });

  it("should have links on section pages", () => {
    cy.visitBloomberg("/economics");
    cy.get("a[href]").should("have.length.greaterThan", 0);
  });

  it("should serve section pages over HTTPS", () => {
    cy.visitBloomberg("/economics");
    cy.url().should("match", /^https:\/\//);
  });
});
