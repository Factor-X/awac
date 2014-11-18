# simple code label helper
angular
.module('app.services')
.service "codeLabelHelper", () ->

    @getLabelByKey = (codeLabels, codeKey) ->
        return _.findWhere(codeLabels, {key: codeKey }).label

    @sortCodeLabelsByKey = (codeLabels) ->
        res = _.sortBy(codeLabels, (codeLabel) ->
            return parseInt(codeLabel.key.match(/\d+/), 10)
        )
        codeLabels = res
        return codeLabels

    @removeCodeLabelsByKeys = (codeLabels, keysToRemove) ->
        res = _.reject(codeLabels, (codeLabel) ->
            return _.contains(keysToRemove, codeLabel.key)
        )
        codeLabels = res
        return codeLabels

    return