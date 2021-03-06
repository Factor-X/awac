# simple code label helper
angular
.module('app.services')
.service "codeLabelHelper", () ->

    @getLabelByKey = (codeLabels, codeKey) ->
        return _.findWhere(codeLabels, {key: codeKey }).label

    @sortCodeLabelsByKey = (codeLabels) ->
        return _.sortBy codeLabels, (codeLabel) ->
            return codeLabel.key

    @sortCodeLabelsByNumericKey = (codeLabels) ->
        return _.sortBy codeLabels, (codeLabel) ->
            return parseInt(codeLabel.key.match(/\d+/), 10)

    @sortCodeLabelsByOrder = (codeLabels) ->
        return _.sortBy codeLabels, (codeLabel) ->
            return codeLabel.orderIndex

    @removeCodeLabelsByKeys = (codeLabels, keysToRemove) ->
        return _.reject codeLabels, (codeLabel) ->
            return _.contains keysToRemove, codeLabel.key

    return