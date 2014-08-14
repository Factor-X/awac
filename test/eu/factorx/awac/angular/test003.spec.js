var Browser = require("zombie");
var assert = require("assert");

var url = "http://localhost:9000/awac/";

var SHOCKWAVE_FLASH = "Shockwave Flash",
    FLASH_MIME_TYPE = "application/x-shockwave-flash";

var browser = new Browser({
    userAgent: "Mozilla/5.0 (CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko ) Version/5.0 Safari/7534.48.3"
});

var browser = new Browser();


// init handler
// Add a callback for the loaded event, which fires before DOMContentLoaded
browser.on("loaded", function (event) {
    var window = browser.window;

    // Fake a mimeTypes array and set the Flash mimeType to enabled
    window.navigator.mimeTypes = [];
    window.navigator.mimeTypes[FLASH_MIME_TYPE] =  {
        enabledPlugin: true
    };

    // Add an entry for SHOCKWAVE_FLASH and set the description to the Flash version report required
    window.navigator.plugins[SHOCKWAVE_FLASH] = {
        description: "Shockwave Flash 10.1 r102"
    };
});

// error handler
browser.on("error", function(error) {
    console.error(error);
})

describe("testing AWAC UI", function() {

    it("should have defined headless browser", function(next){
        expect(typeof browser != "undefined").toBe(true);
        expect(browser instanceof Browser).toBe(true);
        next();
    });

    it("should have title", function(next) {
        browser.visit(url, function(err) {
            expect(browser.success).toBe(true);
            expect(browser.text("title")).toMatch("AWAC")
            next();
        })
    });

    it("should be redirected to login page", function(next) {
        browser.visit(url, function(err) {
            expect(browser.location.pathname).toBe("/awac/#/login");
            next();
        })
    });


    it("should visit the site and see the login form", function(next) {
        browser.visit(url, function(err) {
            expect(browser.success).toBe(true);
            expect(browser.query("input[value='Login']")).toBeDefined();
            next();
        })
    });

    it("shopuld display html", function(next) {
        browser.visit(url, function(err) {
            console.log(browser.html());
            next();
        })
    });


});