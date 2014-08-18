angular
.module('app.filters')
.filter "nullToZero", ($sce, translationService) ->
    (input) ->
        if input == undefined  || input == null
            return 0
        else
            return parseFloat input
