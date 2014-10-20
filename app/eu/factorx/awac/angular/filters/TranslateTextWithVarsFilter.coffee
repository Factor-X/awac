angular
.module('app.filters')
.filter "translateWithVarsText", ($sce, translationService) ->
    (input, vars) ->
        text = translationService.get(input, null)
        if text?
            for k,v of vars
                text = text.replace('{' + k + '}', v)
            return text
        return input
