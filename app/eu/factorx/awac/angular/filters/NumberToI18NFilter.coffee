angular
.module('app.filters')
.filter "numberToI18N", ($filter) ->
    (input) ->
        if input?
            return $filter("number") parseFloat(input), 2
        return ""
