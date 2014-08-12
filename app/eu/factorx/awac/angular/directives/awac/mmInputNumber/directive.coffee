angular
.module('app.directives')
.directive "numbersOnly", ($filter,translationService) ->
    restrict: 'A'
    require: "ngModel"
    link: (scope, element, attrs, modelCtrl) ->

        nbDecimal = 2
        if attrs.numbersOnly== "integer"
            nbDecimal = 0

        p = (viewValue) ->

            value = viewValue.toLocaleString()

            return $filter("number") value,6
        f = (modelValue) ->
            $filter("number") parseFloat(modelValue), nbDecimal

        modelCtrl.$parsers.unshift p
        modelCtrl.$formatters.unshift f

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
