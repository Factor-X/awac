angular
.module('app.directives')
.directive "mmForceNumbers", () ->
    require: "ngModel"
    link: (scope, element, attrs, modelCtrl) ->
        modelCtrl.$parsers.push (inputValue) ->
            return ""  unless inputValue?
            transformedInput = inputValue.replace(/[^0-9+.]/g, "")

            unless transformedInput is inputValue
                modelCtrl.$setViewValue transformedInput
                modelCtrl.$render()
            transformedInput

        return
