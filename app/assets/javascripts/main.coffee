require.config
  paths:
    "angular-animate": "../webjars/angular-animate"
    "angular-bootstrap": "../webjars/bootstrap"
    "angular-route": "../webjars/angular-route"
    "angular-sanitize": "../webjars/angular-sanitize"
    "angular": "../webjars/angular"
    "ui-bootstrap": "../webjars/ui-bootstrap"
    "d3js": "../webjars/d3"
    "jquery": "../webjars/jquery"
    "dangle": "../webjars/dangle"
    "underscore": "../webjars/underscore"
    "angular-flash": "../webjars/angular-flash"
    "angular-file-upload": "../webjars/angular-file-upload"
    "domReady": "../webjars/domReady"

  shim:
    "domReady":
      exports: "domReady"
    "angular":
      exports: "angular"
    "jquery":
      exports: "jquery"
    "underscore":
      exports: "underscore"
    "angular-route":
      deps: ["angular"]
    "angular-bootstrap":
      deps: ["angular"]
    "angular-sanitize":
      deps: ["angular"]
    "angular-flash":
      deps: ["angular"]
    "angular-file-upload":
      deps: ["angular"]
    "ui-bootstrap":
      deps: ["angular"]
    "dangle":
      deps: ["angular"]
    "d3js":
      deps: ["angular","dangle"]
    "angular-animate":
      deps: ["angular"]
#     exports: "angular-route"
    "bootstrap":
      deps: ["jquery"]
    "ui-bootstrap":
      deps: ["angular"]


  deps: ["./boot"]