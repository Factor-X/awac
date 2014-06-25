angular
.module('app.services')
.service "translationService", ($http, $rootScope, downloadService) ->
    svc = this
    svc.elements = null

    svc.initialize = () ->
        downloadService.getJson "/awac/translations/fr", (data) ->
            svc.elements = data.lines
            $rootScope.$broadcast "LOAD_FINISHED",
                type: "TRANSLATIONS"
                success: data?

    svc.get = (code) ->
        return ""  unless svc.elements?
        svc.elements[code]

    return
