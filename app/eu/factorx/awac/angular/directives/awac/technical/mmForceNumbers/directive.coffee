angular
.module('app.directives')
.directive "mmForceNumbers", () ->
    require: "ngModel"
    link: (scope, element, attrs, modelCtrl) ->
        modelCtrl.$parsers.push (inputValue) ->
            return ""  unless inputValue?
            transformedInput = inputValue.replace(/[^0-9+.]/g, "")
            console.log 'OK I WATCH !:'
            console.log this
            console.log arguments
            console.log transformedInput

            unless transformedInput is inputValue
                modelCtrl.$setViewValue transformedInput
                modelCtrl.$render()
            transformedInput

        return
