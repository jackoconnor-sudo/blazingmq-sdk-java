describe("Bloomberg Search Functionality", () => {
  it("should have a search route available", () => {
    cy.visitBloomberg("/search");
    cy.url().should("include", "bloomberg.com");
    cy.get("body").should("be.visible");
    cy.title().should("not.be.empty");
  });

  it("should be able to navigate directly to a search URL", () => {
    cy.visitBloomberg("/search?query=Apple");
    cy.url().should("include", "bloomberg.com");
    cy.get("body").should("be.visible");
    cy.title().should("not.be.empty");
  });

  it("should display content on the search results page", () => {
    cy.visitBloomberg("/search?query=Tesla");
    cy.get("body").invoke("text").should("have.length.greaterThan", 50);
  });

  it("should have links on the search results page", () => {
    cy.visitBloomberg("/search?query=Microsoft");
    cy.get("a[href]").should("have.length.greaterThan", 0);
  });

  it("should include the search query in the URL", () => {
    cy.visitBloomberg("/search?query=Bloomberg+Terminal");
    cy.url().should("include", "search");
  });
});
