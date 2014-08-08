angular
.module('app.directives')
.directive "numbersOnly", (translationService) ->
    restrict: 'A'
    require: "ngModel"
    link: (scope, element, attrs, modelCtrl) ->
        modelCtrl.$parsers.push (inputValue) ->
            if inputValue is `undefined`
                return ""

            scope.errorMessage=""

            if  attrs.numbersOnly== "integer"
                scope.regex = /[^0-9]/g
                scope.errorMessage = translationService.get 'FIELD_INTEGER_ERROR_MESSAGE'
            else
                scope.regex = /[^0-9,. ]/g
                scope.errorMessage = translationService.get 'FIELD_DECIMAL_ERROR_MESSAGE'

            if inputValue?
                transformedInput = inputValue.replace(scope.regex, "")
                if transformedInput != inputValue

                    # try to display the message like a error message.
                    # used by the mmAwacQuestion directive
                    if scope.$parent? && scope.$parent.setErrorMessage?
                        scope.$parent.setErrorMessage(scope.errorMessage)

                    if transformedInput == ""
                        transformedInput = null

                    modelCtrl.$setViewValue transformedInput
                    modelCtrl.$render()
                return transformedInput
