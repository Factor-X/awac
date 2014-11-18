angular
.module('app.filters')
.filter "translateWithVars", ($sce, translationService) ->
    (input, vars) ->
        text = translationService.get(input, null)
        if text?

            for k,v of vars
                text = text.replace('{' + k + '}', v)

            $sce.trustAsHtml "<span class=\"translated-text\" data-code=\"" + input + "\">" + text + "</span>"
        else
            $sce.trustAsHtml "<span class=\"translated-text translation-missing\" data-code=\"" + input + "\">[" + input + "]</span>"
