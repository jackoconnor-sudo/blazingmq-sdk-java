describe("Bloomberg Responsive Design", () => {
  const viewports = [
    { name: "mobile", width: 375, height: 667 },
    { name: "tablet", width: 768, height: 1024 },
    { name: "desktop", width: 1280, height: 720 },
  ];

  viewports.forEach(({ name, width, height }) => {
    describe(`${name} viewport (${width}x${height})`, () => {
      beforeEach(() => {
        cy.viewport(width, height);
        cy.visitBloomberg("/");
      });

      it(`should render the page on ${name}`, () => {
        cy.get("body").should("be.visible");
        cy.get("html").should("exist");
      });

      it(`should have a non-empty title on ${name}`, () => {
        cy.title().should("not.be.empty");
      });

      it(`should have visible text content on ${name}`, () => {
        cy.get("body").invoke("text").should("have.length.greaterThan", 50);
      });

      it(`should have clickable elements on ${name}`, () => {
        cy.get("a, button").should("have.length.greaterThan", 0);
      });

      it(`should serve over HTTPS on ${name}`, () => {
        cy.url().should("match", /^https:\/\//);
      });
    });
  });
});
