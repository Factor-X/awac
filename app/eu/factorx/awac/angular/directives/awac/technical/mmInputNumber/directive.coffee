angular
.module('app.directives')
.directive "numbersOnly", ($filter, $log, translationService, $locale) ->
    restrict: 'A'
    require: "ngModel"
    link: (scope, element, attrs, modelCtrl) ->

        scope.format = (v) ->
            return v
        scope.parse = modelCtrl.$rollbackViewValue

        modelCtrl.$parsers.unshift (v) ->
            return scope.parse(v)
        modelCtrl.$formatters.unshift (v) ->
            return scope.format(v)



        convertToString = (value, decimal) ->
            if !value? || value == "" || isNaN value
                return ""
            value=parseFloat(value).toFixed(decimal)
            formats = $locale.NUMBER_FORMATS
            return value.toString().split('.').join(formats.DECIMAL_SEP)

        convertToFloat = (viewValue) ->
            if viewValue == "" or viewValue == null
                return null
            formats = $locale.NUMBER_FORMATS
            sep = formats.DECIMAL_SEP
            value = viewValue.split(sep).join('.')
            return value


        if attrs.numbersOnly == ''
            found = true

            scope.format = (v) ->
                return v
            scope.parse = (v) ->
                return v

        scope.$watch attrs.numbersOnly, () ->
            scope.lastValidValue = ""

            found = false

            # INTEGER
            if attrs.numbersOnly == "integer"
                found = true

                scope.format = (modelValue) ->
                    v = modelValue
                    return convertToString(v, 0)

                scope.parse = (viewValue) ->
                    errorMessage = null

                    viewValue = convertToFloat(viewValue.trim())
                    if viewValue == null or viewValue == ''
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

                scope.format = (modelValue) ->
                    v = modelValue
                    return convertToString(v, 3)

                scope.parse = (viewValue) ->
                    errorMessage = null

                    viewValue = convertToFloat(viewValue.trim())
                    if viewValue == null or viewValue == ''
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

                scope.format = (modelValue) ->
                    v = modelValue * 100.0
                    return convertToString(v,3)

                scope.parse = (viewValue) ->
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

            if attrs.numbersOnly == ''
                found = true

                scope.format = (v) ->
                    return v
                scope.parse = (v) ->
                    return v

            if found
                if modelCtrl.$modelValue?
                    modelCtrl.$setViewValue scope.format(modelCtrl.$modelValue)
                    modelCtrl.$render()
