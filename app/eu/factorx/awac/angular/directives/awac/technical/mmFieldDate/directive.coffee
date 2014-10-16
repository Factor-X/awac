angular
.module('app.directives')
.directive "mmFieldDate", (directiveService, $filter, generateId) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngInfo: '='
    templateUrl: "$/angular/templates/mm-field-date.html"
    replace: true
    transclude: true
    compile: ->
        pre: (scope) ->
            scope.id = generateId.generate()
            scope.idHtag = '#' + scope.id

        post: (scope) ->
            directiveService.autoScopeImpl scope

            scope.result = null

            scope.$watch 'result', ->
                scope.resultFormated = $filter('date')(scope.result, 'yyyy-MM-dd')

            scope.$watch 'getInfo().field', ->
                if scope.getInfo().field?
                    scope.result = new Date(Number(scope.getInfo().field))

            scope.$watch 'result', ->
                if scope.result?
                    scope.getInfo().field = scope.result.getTime()
                else
                    scope.getInfo().field = null
                scope.isValid()

            scope.isValid = ->
                console.log("Validating date = " + scope.getInfo().field)
                if scope.getInfo().disabled == true || scope.getInfo().hidden == true
                    scope.getInfo().isValid = true
                    return
                isValid = true
                if !scope.getInfo().field?
                    isValid = false
                if scope.getInfo().minValue?
                    isValid = (scope.getInfo().field > scope.getInfo().minValue.getTime())
                if scope.getInfo().maxValue?
                    isValid = (scope.getInfo().field < scope.getInfo().maxValue.getTime())
                scope.getInfo().isValid = isValid
                return

            scope.isValid()

            scope.logField = ->
                console.log scope.getInfo()
