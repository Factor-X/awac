angular
.module('app.directives')
.directive "numbersOnly", ($filter, translationService, $locale) ->
    restrict: 'A'
    require: "ngModel"
    link: (scope, element, attrs, modelCtrl) ->

        scope.$watch attrs.numbersOnly, () ->

            if attrs.numbersOnly == "integer" || attrs.numbersOnly == "double" || attrs.numbersOnly == "percent"

                if  attrs.numbersOnly == "integer"
                    errorMessage = $filter('translateText')('ONLY_INTEGER')
                else
                    errorMessage = $filter('translateText')('ONLY_NUMBER')

                nbDecimal = 3

                if attrs.numbersOnly == "integer"
                    nbDecimal = 0


                scope.$root.$on '$localeChangeSuccess', (event, current, previous) ->
                    if modelCtrl.$modelValue?
                        result = convertToString(parseFloat(modelCtrl.$modelValue))
                        if result?
                            modelCtrl.$setViewValue result.toString()
                            modelCtrl.$render()

                modelCtrl.$parsers.unshift (viewValue) ->
                    if viewValue == ""
                        return null

                    result = convertToFloat(viewValue)

                    if isNaN result
                        displayError()
                        if scope.lastValidValue?
                            resultString = scope.lastValidValue.toString()
                            if  attrs.numbersOnly == "percent"
                                resultToDisplay = (scope.lastValidValue*100).toString()
                            else
                                resultToDisplay = scope.lastValidValue.toString()
                        else
                            resultString = ""
                            resultToDisplay =""
                        modelCtrl.$setViewValue resultToDisplay
                        modelCtrl.$render()
                    else
                        if  attrs.numbersOnly == "percent"
                            result=result/100
                        scope.lastValidValue = result
                        resultString = result.toString()
                    if resultString == ""
                        return null
                    return  resultString


                modelCtrl.$formatters.unshift (modelValue) ->
                    return scope.displayValue(modelValue)

                scope.displayValue = (modelValue) ->
                    result=parseFloat(modelValue)
                    if  attrs.numbersOnly == "percent"
                        result=result*100
                    return convertToString(result)

                displayError = ->
                    # try to display the message like a error message.
                    # used by the mmAwacQuestion directive
                    if scope.setErrorMessage?
                        scope.setErrorMessage(errorMessage)

                convertToString = (value) ->

                    if !value? || isNaN value
                        return ""

                    value=value.toFixed(nbDecimal)

                    formats = $locale.NUMBER_FORMATS
                    result= value.toString().replace(new RegExp("\\.", "g"), formats.DECIMAL_SEP)

                convertToFloat = (viewValue) ->
                    if viewValue == ""
                        return NaN

                    formats = $locale.NUMBER_FORMATS

                    decimalRegex = formats.DECIMAL_SEP
                    if decimalRegex == "."
                        decimalRegex = "\\."

                    value = viewValue.replace(new RegExp(decimalRegex, "g"), ".")

                    return filterFloat value

                #
                # filter a string to float
                #
                filterFloat = (value) ->
                    if  attrs.numbersOnly == "integer"
                        regexFloat = new RegExp("^(\\-|\\+)?([0-9]+|Infinity)$")
                    else
                        regexFloat = new RegExp("^(\\-|\\+)?([0-9]+(\\.[0-9]*)?|Infinity)$")

                    return Number(value)  if regexFloat.test(value)
                    return NaN

                #
                # first initialization : save first value and display it
                #
                if modelCtrl.$modelValue?
                    scope.lastValidValue = parseFloat(modelCtrl.$modelValue)
                    # first displaying
                    valueToDisplay = scope.displayValue(scope.lastValidValue)
                    modelCtrl.$setViewValue(valueToDisplay)
                    modelCtrl.$render()