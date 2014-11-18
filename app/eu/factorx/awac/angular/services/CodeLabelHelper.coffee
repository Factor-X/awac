# simple code label helper
angular
.module('app.services')
.service "codeLabelHelper", () ->

    @getLabelByKey = (codeLabels, codeKey) ->
        return _.findWhere(codeLabels, {key: codeKey }).label

    @sortCodeLabelsByNumericKey = (codeLabels) ->
        return _.sortBy codeLabels, (codeLabel) ->
            return parseInt codeLabel.key.match(/\d+/), 10

    @sortCodeLabelsByKey = (codeLabels) ->
        return _.sortBy codeLabels, (codeLabel) ->
            return codeLabel.key

    @removeCodeLabelsByKeys = (codeLabels, keysToRemove) ->
        return _.reject codeLabels, (codeLabel) ->
            return _.contains keysToRemove, codeLabel.key

    return