define [
  "require"
  "angular"
  "jquery"
  "domReady"
  "angular-animate"
  "angular-sanitize"
  "angular-route"
  "ui-bootstrap"
  "angular-flash"
  "angular-file-upload"
  "underscore"
  "angular-bootstrap"
  "d3js"
  "dangle"
  "app"
  "routes"
], (require, ng) ->
  "use strict"
  require ["domReady",
           "angular",
           "jquery",
           "angular-animate",
           "angular-sanitize",
           "angular-route",
           "ui-bootstrap",
           "angular-flash",
           "angular-file-upload",
           "underscore",
           "angular-bootstrap",
           "d3js",
           "dangle"
           "routes"], (document) ->
    ng.bootstrap document, ["app"]
    return

  return
