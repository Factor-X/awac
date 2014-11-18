angular
.module('app.filters')
.filter "translate", ($sce, translationService) ->
    (input, count) ->
        text = translationService.get(input, count)
        if text?
            $sce.trustAsHtml "<span class=\"translated-text\" data-code=\"" + input + "\">" + text + "</span>"
        else
            $sce.trustAsHtml "<span class=\"translated-text translation-missing\" data-code=\"" + input + "\">[" + input + "]</span>"
