angular
.module('app.filters')
.filter "translateText", ($sce, translationService) ->
    (input, count) ->
        text = translationService.get(input, count)
        if text?
            return text
        return input
