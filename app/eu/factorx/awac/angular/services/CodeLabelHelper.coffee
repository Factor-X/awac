# simple code label helper
angular
.module('app.services')
.service "codeLabelHelper", () ->

    @getLabelByKey = (codeLabelsList, codeKey) ->
        return _.findWhere(codeLabelsList, {key: codeKey }).label

    return