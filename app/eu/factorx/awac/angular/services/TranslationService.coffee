angular
.module('app.services')
.service "translationService", ($rootScope, downloadService) ->
    svc = this
    svc.elements = null

    svc.initialize = (lang) ->
        downloadService.getJson "/awac/translations/" + lang, (result) ->
            if result.success
                svc.elements = result.data.lines
                $rootScope.$broadcast "LOAD_FINISHED",
                    type: "TRANSLATIONS"
                    success: true
            else
                # TODO ERROR HANDLING
                svc.elements = []
                $rootScope.$broadcast "LOAD_FINISHED",
                    type: "TRANSLATIONS"
                    success: false


    svc.get = (code, count) ->
        return "" unless svc.elements?

        v = svc.elements[code]

        if not v?
            return null

        if count?
            if "" + count == "0"
                txt = v.zero || v.fallback
            else if "" + count == "1"
                txt = v.one || v.fallback
            else
                txt = v.more || v.fallback
            txt = txt.replace(/\{0\}/g, count)
        else
            txt = v.fallback || ''

        return txt

    return
