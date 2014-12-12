angular
.module('app.directives')
.directive "numbersOnly", ($filter, $log, translationService, $locale) ->
    restrict: 'A'
    require: "ngModel"
    link: (scope, element, attrs, modelCtrl) ->


        convertToString = (value, decimal) ->
            if !value? || isNaN value
                return ""
            value=value.toFixed(decimal)
            formats = $locale.NUMBER_FORMATS
            return value.toString().split('.').join(formats.DECIMAL_SEP)

        convertToFloat = (viewValue) ->
            if viewValue == ""
                return NaN
            formats = $locale.NUMBER_FORMATS
            sep = formats.DECIMAL_SEP
            value = viewValue.split(sep).join('.')
            return value


        scope.$watch attrs.numbersOnly, () ->
            scope.lastValidValue = ""

            found = false
            format = angular.noop
            parse = angular.noop

            # INTEGER
            if attrs.numbersOnly == "integer"
                found = true

                format = (modelValue) ->
                    v = modelValue
                    return convertToString(v, 0)

                parse = (viewValue) ->
                    errorMessage = null

                    viewValue = convertToFloat(viewValue.trim())
                    if viewValue == ''
                        return null
                    regexFloat = new RegExp("^(\\-|\\+)?([0-9]+|Infinity)?$")
                    if regexFloat.test(viewValue)
                        parseResult = viewValue
                    else
                        parseResult = null
                        errorMessage = $filter('translateText')('ONLY_INTEGER')

                    if scope.setErrorMessage?
                        scope.setErrorMessage(errorMessage)

                    return parseResult

            # DOUBLE
            if attrs.numbersOnly == "double"
                found = true

                format = (modelValue) ->
                    v = modelValue
                    return convertToString(v, 3)

                parse = (viewValue) ->
                    errorMessage = null

                    viewValue = convertToFloat(viewValue.trim())
                    if viewValue == ''
                        return null
                    regexFloat = new RegExp("^(\\-|\\+)?([0-9]+(\\.[0-9]*)?|Infinity)?$")
                    if regexFloat.test(viewValue)
                        parseResult = viewValue
                    else
                        parseResult = null
                        errorMessage = $filter('translateText')('ONLY_NUMBER')

                    if scope.setErrorMessage?
                        scope.setErrorMessage(errorMessage)

                    return parseResult


            # PERCENT
            if attrs.numbersOnly == "percent"
                found = true

                format = (modelValue) ->
                    v = modelValue * 100.0
                    return convertToString(v,3)

                parse = (viewValue) ->
                    errorMessage = null

                    viewValue = convertToFloat(viewValue.trim())
                    if viewValue == ''
                        return null
                    regexFloat = new RegExp("^(\\-|\\+)?([0-9]+(\\.[0-9]*)?|Infinity)?$")
                    if regexFloat.test(viewValue)
                        parseResult = viewValue * 0.01
                    else
                        parseResult = null
                        errorMessage = $filter('translateText')('ONLY_NUMBER')

                    if scope.setErrorMessage?
                        scope.setErrorMessage(errorMessage)

                    return parseResult
            else
                format = (modelValue) ->
                    return modelValue
                parse = (viewValue) ->
                    return viewValue

            if found
                if modelCtrl.$modelValue?
                    modelCtrl.$setViewValue format(modelCtrl.$modelValue)
                    modelCtrl.$render()
                modelCtrl.$parsers.unshift parse
                modelCtrl.$formatters.unshift format

