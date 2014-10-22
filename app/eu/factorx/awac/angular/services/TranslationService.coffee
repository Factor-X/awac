angular
.module('app.services')
.service "translationService", ($rootScope, $filter, $http) ->
    svc = this
    svc.elements = null

    svc.initialize = (lang) ->
        $http(
            method: "GET"
            url: "/awac/translations/" + lang
            headers:
                "Content-Type": "application/json"
        )
        .success (data, status, headers, config) ->
            svc.elements = data.lines
            $rootScope.$broadcast "LOAD_FINISHED",
                type: "TRANSLATIONS"
                success: true

        .error (data, status, headers, config) ->
            svc.elements = []
            $rootScope.$broadcast "LOAD_FINISHED",
                type: "TRANSLATIONS"
                success: false
        return

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

    svc.translateExceptionsDTO = (exception) ->

        if Object.keys(exception.params).length > 0
            return $filter('translateTextWithVars')(exception.messageToTranslate, exception.params)
        else if exception.messageToTranslate?
            return $filter('translate')(exception.messageToTranslate)
        else
            return exception.message

    return
