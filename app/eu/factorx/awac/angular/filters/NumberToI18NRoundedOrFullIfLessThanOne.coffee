angular
.module('app.filters')
.filter "numberToI18NRoundedOrFullIfLessThanOne", ($filter) ->
    (input) ->
        if input?
            original = parseFloat(input)
            if original < 1
                return $filter("number") original,12
            else
                return $filter("number") original, 3
        return ""
