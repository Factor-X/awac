angular
.module('app.directives')
.directive "mmAwacVerificationForm", (directiveService) ->
    restrict: "E"
    templateUrl: ->
        return "$/angular/views/enterprise/"+form+".html"