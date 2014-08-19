var Browser = require("zombie");
var assert = require("assert");

// Load the page from localhost
browser = new Browser()
browser.visit("http://localhost:9000/awac/", function () {

    // Fill email, password and submit form
    browser.
        fill("email", "zombie@underworld.dead").
        fill("password", "eat-the-living").
        pressButton("Sign Me Up!", function() {

            // Form submitted, new page loaded.
            assert.ok(browser.success);
            assert.equal(browser.text("title"), "Welcome To Brains Depot");

        })
});