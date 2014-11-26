angular
.module('app.directives')
.directive "mmAwacModalHelp", (directiveService, downloadService) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-help.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        if $scope.getParams().template?
            downloadService.getJson "/awac/admin/translations/wysiwyg/get/" + $scope.$root.instanceName + "/" + $scope.getParams().template + ":" + $scope.$root.language, (result) ->
                if result.success
                    $('.modal-help-content').html(result.data.content)

        $scope.close = ->
            modalService.close modalService.HELP

    link: (scope) ->

