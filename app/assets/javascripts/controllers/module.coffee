define ["angular","jquery"], (ng,$) ->
  "use strict"
  ng.module "app.controllers", ['app.services', 'ngRoute', 'angular-flash.service','angular-flash.flash-alert-directive','angularFileUpload']