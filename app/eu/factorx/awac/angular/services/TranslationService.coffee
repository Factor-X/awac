angular
.module('app')
.service "translationService", ($http, $rootScope, downloadService) ->
    svc = this
    svc.elements = null
    downloadService.getJson "/awac/translations/fr", (data) ->
        svc.elements = data.lines
        $rootScope.$broadcast "LOAD_FINISHED",
            type: "TRANSLATIONS"
            success: data?

        return

    svc.get = (code) ->
        return ""  unless svc.elements?
        svc.elements[code]

    return
