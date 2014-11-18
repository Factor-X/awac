angular
.module('app.filters')
.filter "numberToI18N", ($filter) ->
    (input) ->
        if input?
            original = parseFloat(input)
            rounded = Math.round(original * 100.0) / 100.0
            return $filter("number") rounded, 2
        return ""
