angular
.module('app.filters')
.filter "trustAsHtml", ($sce) ->
    (input) ->
        return $sce.trustAsHtml(input)
