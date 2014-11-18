angular
.module('app.filters')
.filter "numberToI18NOrLess", ($filter, translationService) ->
    (input) ->
        if input?
            original = parseFloat(input)
            rounded = Math.round(original * 1000.0) / 1000.0
            if rounded < 0.001
                if original > 0
                    return translationService.get('LESS_THAN_MINIMUM')
                else
                    return ''
            else
                return $filter("number") rounded, 3
        return ""
