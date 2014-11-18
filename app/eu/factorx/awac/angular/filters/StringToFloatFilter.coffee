angular
.module('app.filters')
.filter "stringToFloat", ($sce, translationService) ->
    (input) ->
        if input?
            return parseFloat(input)
