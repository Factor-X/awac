angular
.module('app.directives')
.directive "numbersOnly", ($filter, $log, translationService, $locale) ->
    restrict: 'A'
    require: "ngModel"
    link: (scope, element, attrs, modelCtrl) ->
        scope.$watch attrs.numbersOnly, () ->
            scope.lastValidValue = ""

            format = angular.noop
            parse = angular.noop

            # INTEGER
            if attrs.numbersOnly == "integer"
                format = (modelValue) ->
                    v = modelValue
                    return v.toFixed(0)

                parse = (viewValue) ->
                    errorMessage = null

                    viewValue = viewValue.trim()
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
                format = (modelValue) ->
                    v = modelValue
                    return v.toFixed(3)

                parse = (viewValue) ->
                    errorMessage = null

                    viewValue = viewValue.trim()
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
                format = (modelValue) ->
                    v = modelValue * 100.0
                    return v.toFixed(3)

                parse = (viewValue) ->
                    errorMessage = null

                    viewValue = viewValue.trim()
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


            if modelCtrl.$modelValue?
                modelCtrl.$setViewValue format(modelCtrl.$modelValue)
                modelCtrl.$render()
            modelCtrl.$parsers.unshift parse
            modelCtrl.$formatters.unshift format

#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#angular
#.module('app.directives')
#.directive "numbersOnly", ($filter, translationService, $locale) ->
#    restrict: 'A'
#    require: "ngModel"
#    link: (scope, element, attrs, modelCtrl) ->
#
#        scope.$watch attrs.numbersOnly, () ->
#
#            if attrs.numbersOnly == "integer" || attrs.numbersOnly == "double" || attrs.numbersOnly == "percent"
#
#                scope.lastValidValue=""
#
#                if  attrs.numbersOnly == "integer"
#                    errorMessage = $filter('translateText')('ONLY_INTEGER')
#                    nbDecimal = 0
#                else
#                    errorMessage = $filter('translateText')('ONLY_NUMBER')
#                    nbDecimal = 3
#
#                scope.$root.$on '$localeChangeSuccess', (event, current, previous) ->
#                    if modelCtrl.$modelValue?
#                        result = convertToString(parseFloat(modelCtrl.$modelValue))
#                        if result?
#                            modelCtrl.$setViewValue result.toString()
#                            modelCtrl.$render()
#
#                modelCtrl.$parsers.unshift (viewValue) ->
#
#                    console.log "$parsers je suis la valeur visuel : "+viewValue
#
#
#                    if viewValue == ""
#                        return null
#
#                    result = convertToFloat(viewValue)
#
#                    if isNaN result
#                        displayError()
#                        if scope.lastValidValue?
#                            resultString = scope.lastValidValue.toString()
#                            if  attrs.numbersOnly == "percent"
#                                resultToDisplay = (scope.lastValidValue*100).toString()
#                            else
#                                resultToDisplay = scope.lastValidValue.toString()
#                        else
#                            resultString = ""
#                            resultToDisplay =""
#                        modelCtrl.$setViewValue resultToDisplay
#                        modelCtrl.$render()
#                    else
#                        if  attrs.numbersOnly == "percent"
#                            result=result/100
#                        scope.lastValidValue = result
#                        resultString = result.toString()
#                    if resultString == ""
#                        return null
#                    console.log "$parsers == > la nouvelle valeur-model est : "+resultString
#                    return  resultString
#
#
#                modelCtrl.$formatters.unshift (modelValue) ->
#                    return scope.displayValue(modelValue)
#
#                scope.displayValue = (modelValue) ->
#                    console.log "$formatters je suis la valeur model : "+modelValue
#                    result=parseFloat(modelValue)
#                    if  attrs.numbersOnly == "percent"
#                        result=angular.copy(result)*100
#                    viewValue =  convertToString(result)
#
#                    console.log "$formatters == > la nouvelle valeur-view est : "+viewValue
#                    return viewValue
#
#                displayError = ->
#                    # try to display the message like a error message.
#                    # used by the mmAwacQuestion directive
#                    if scope.setErrorMessage?
#                        scope.setErrorMessage(errorMessage)
#
#                convertToString = (value) ->
#
#                    if !value? || isNaN value
#                        return ""
#
#                    value=value.toFixed(nbDecimal)
#
#                    formats = $locale.NUMBER_FORMATS
#                    return value.toString().replace(new RegExp("\\.", "g"), formats.DECIMAL_SEP)
#
#                convertToFloat = (viewValue) ->
#                    if viewValue == ""
#                        return NaN
#
#                    formats = $locale.NUMBER_FORMATS
#
#                    decimalRegex = formats.DECIMAL_SEP
#                    if decimalRegex == "."
#                        decimalRegex = "\\."
#
#                    value = viewValue.replace(new RegExp(',', "g"), "").replace(new RegExp(decimalRegex, "g"), ".")
#
#                    value = parseFloat(value)
#
#
#                    return filterFloat value
#
#                #
#                # filter a string to float
#                #
#                filterFloat = (value) ->
#                    if value.isNaN
#                        return NaN
#                    if  attrs.numbersOnly == "integer"
#                        regexFloat = new RegExp("^(\\-|\\+)?([0-9]+|Infinity)?$")
#                    else
#                        regexFloat = new RegExp("^(\\-|\\+)?([0-9]+(\\.[0-9]*)?|Infinity)?$")
#
#                    return Number(value)  if regexFloat.test(value)
#                    return NaN
#
#                #
#                # first initialization : save first value and display it
#                #
#                if modelCtrl.$modelValue?
#                    scope.lastValidValue = parseFloat(modelCtrl.$modelValue)
#                    console.log "je suis l'initilaization !! la modelCtrl.$modelValue est : "+modelCtrl.$modelValue
#                    # first displaying
#                    valueToDisplay = scope.displayValue(scope.lastValidValue)
#                    console.log "=> la valeur de initilaization visuel est "+valueToDisplay
#                    modelCtrl.$setViewValue(valueToDisplay)
#                    modelCtrl.$render()
