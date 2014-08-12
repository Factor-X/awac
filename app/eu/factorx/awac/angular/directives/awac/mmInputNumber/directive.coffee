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
                result = $filter("number") parseFloat(modelCtrl.$modelValue), nbDecimal
                if result?
                    console.log "to print : " + modelCtrl.$modelValue + "-" + parseFloat(modelCtrl.$modelValue) + "=>" + result
                    modelCtrl.$setViewValue result.toString()
                    modelCtrl.$render()

        modelCtrl.$parsers.unshift (viewValue) ->
            if viewValue == ""
                return null

            console.log "viewValue:" + viewValue

            formats = $locale.NUMBER_FORMATS

            groupRegex = formats.GROUP_SEP
            if groupRegex == "."
                groupRegex = "\\."
            decimalRegex = formats.DECIMAL_SEP
            if decimalRegex == "."
                decimalRegex = "\\."

            value = viewValue.replace(new RegExp(groupRegex, "g"), "")
            value = value.replace(new RegExp(decimalRegex, "g"), ".")

            result = filterFloat value

            console.log "result:"+result+"=>"+value+"/"+decimalRegex

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
            $filter("number") result, nbDecimal

        displayError = ->
            console.log "PRINT ERROR : "+scope.errorMessage+"+"+scope.$parent
            # try to display the message like a error message.
            # used by the mmAwacQuestion directive
            if scope.$parent?
                scope.$parent.setErrorMessage(scope.errorMessage)

        filterFloat = (value) ->
            formats = $locale.NUMBER_FORMATS

            groupRegex = formats.GROUP_SEP.replace(/\\./g, "\\.")
            decimalRegex = formats.DECIMAL_SEP.replace(/\\./g, "\\.")

            if  attrs.numbersOnly == "integer"
                regexFloat = new RegExp("^(\\-|\\+)?([0-9]+|Infinity)$")
            else
                regexFloat = new RegExp("^(\\-|\\+)?([0-9]+(\\.[0-9]*)?|Infinity)$")

            return Number(value)  if regexFloat.test(value)
            return NaN
