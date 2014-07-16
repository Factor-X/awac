angular
.module('app.services')
.service "translationService", ($http, $rootScope, downloadService) ->
    svc = this
    svc.elements = null

    svc.initialize = (lang) ->
        downloadService.getJson "/awac/translations/" + lang, (data) ->
            svc.elements = data.lines
            $rootScope.$broadcast "LOAD_FINISHED",
                type: "TRANSLATIONS"
                success: data?

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
            txt = txt.replace( /\{0\}/g, count)
        else
            txt = v.fallback || ''

        return txt

    return
