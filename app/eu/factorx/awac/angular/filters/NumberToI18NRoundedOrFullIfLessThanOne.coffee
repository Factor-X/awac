angular
.module('app.filters')
.filter "numberToI18NRoundedOrFullIfLessThanOne", ($filter) ->
    (input) ->
        if input?
            original = parseFloat(input)
            if original <= 1
                return $filter("number") original
            else
                rounded = Math.round(original * 1000.0) / 1000.0
                return $filter("number") rounded, 3
        return ""
