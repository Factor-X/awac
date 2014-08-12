angular
.module('app.directives')
.directive "numbersOnly", ($filter, translationService, $locale) ->
    restrict: 'A'
    require: "ngModel"
    link: (scope, element, attrs, modelCtrl) ->

        if  attrs.numbersOnly == "integer"
            scope.errorMessage = translationService.get 'FIELD_INTEGER_ERROR_MESSAGE'
        else
            scope.errorMessage = translationService.get 'FIELD_DECIMAL_ERROR_MESSAGE'

        nbDecimal = 2

        if attrs.numbersOnly == "integer"
            nbDecimal = 0


        scope.$root.$on '$localeChangeSuccess', (event, current, previous) ->
            if modelCtrl.$modelValue?
                console.log "reformat : "
                console.log modelCtrl
                result = convertToString(parseFloat(modelCtrl.$modelValue)) #$filter("number") parseFloat(modelCtrl.$modelValue), nbDecimal
                if result?
                    console.log "to print : " + modelCtrl.$modelValue + "-" + parseFloat(modelCtrl.$modelValue) + "=>" + result
                    modelCtrl.$setViewValue result.toString()
                    modelCtrl.$render()

        modelCtrl.$parsers.unshift (viewValue) ->
            if viewValue == ""
                return null

            console.log "viewValue:" + viewValue

            formats = $locale.NUMBER_FORMATS

            decimalRegex = formats.DECIMAL_SEP
            if decimalRegex == "."
                decimalRegex = "\\."

            value = viewValue.replace(new RegExp(decimalRegex, "g"), ".")

            result = filterFloat value

            if isNaN result
                displayError()
                if scope.lastValidValue?
                    resultString = scope.lastValidValue.toString()
                else
                    resultString = ""
                modelCtrl.$setViewValue resultString
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
            result=parseFloat(modelValue)
            if  attrs.numbersOnly == "percent"
                result=result*100
            return convertToString(result) #$filter("number") result, nbDecimal

        displayError = ->
            console.log "PRINT ERROR : "+scope.errorMessage+"+"+scope.$parent
            # try to display the message like a error message.
            # used by the mmAwacQuestion directive
            if scope.$parent?
                scope.$parent.setErrorMessage(scope.errorMessage)

        convertToString = (value) ->

            formats = $locale.NUMBER_FORMATS

            result = value.toString().replace(new RegExp("\\.", "g"), formats.DECIMAL_SEP)

            return result


        filterFloat = (value) ->

            if  attrs.numbersOnly == "integer"
                regexFloat = new RegExp("^(\\-|\\+)?([0-9]+|Infinity)$")
            else
                regexFloat = new RegExp("^(\\-|\\+)?([0-9]+(\\.[0-9]*)?|Infinity)$")

            return Number(value)  if regexFloat.test(value)
            return NaN
