var Browser = require("zombie");
var calculator = require("./calculator");

//Tell Zombie to pose as Safari 5.0
var browser = new Browser({
    userAgent: "Mozilla/5.0 (CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko ) Version/5.0 Safari/7534.48.3"
});

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

describe("multiplication", function () {
    it("should multiply 2 and 3", function () {
        var product = calculator.multiply(2, 3);
        expect(product).toBe(6);
    });
    it("should multiply 2 and 2", function () {
        var product = calculator.multiply(2, 2);
        expect(product).toBe(4);
    });
});