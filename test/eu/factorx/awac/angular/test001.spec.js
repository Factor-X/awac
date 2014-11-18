var zombie = require("zombie");
//var url = "http://localhost:9000/awac/";
var url = "http://www.factorx.eu";

var SHOCKWAVE_FLASH = "Shockwave Flash",
    FLASH_MIME_TYPE = "application/x-shockwave-flash";



describe("testing with zombie", function() {

    it("should have defined headless browser", function(next){
        expect(typeof browser != "undefined").toBe(true);
        expect(browser instanceof Browser).toBe(true);
        asyncSpecDone();
        //next();
    });

    it("should visit the site and see the login form", function(next) {
            zombie.visit(url, function(err, browser) {
                //console.log (browser.html())
                expect(browser.success).toBe(true);
                //expect(browser.query("input[value='Login']")).toBeDefined();
                asyncSpecDone();
                //next();
        })
    });

    it("should have title", function(next) {
        zombie.visit(url, function(err, browser) {
            console.log("testing should have title")
            assert.equal(browser.text("title"), "Welcome To Brains Depot");
            asyncSpecDone();
            //next();
        })
    });

});
