# simple download service
angular
.module('app.services')
.service "downloadService", ($http, $q,messageFlash,translationService) ->
    @downloadsInProgress = 0

    @getDownloadsInProgress = ->
        @downloadsInProgress

    @getJson = (url, callback) ->
        console.log "GET URL TO " + url
        deferred = $q.defer()
        @downloadsInProgress++
        promise = $http(
            method: "GET"
            url: url
            headers:
                "Content-Type": "application/json"
                #"Last-Modified": "boom"
                "Pragma": "no-cache"
                #"Cache-Control": "must-revalidate, private"
                #"Expires": "-1"
        )
        promise.success (data, status, headers, config) ->
            @downloadsInProgress--
            return deferred.resolve(callback(
                data: data,
                status: status,
                headers: headers,
                config: config,
                success: true
            ))

        promise.error (data, status, headers, config) ->
            @downloadsInProgress--
            #display error
            if !!data
                messageFlash.displayError translationService.translateExceptionsDTO(data)
            return deferred.resolve(callback(
                data: data,
                status: status,
                headers: headers,
                config: config,
                success: false
            ))

        return deferred.promise

    @postJson = (url, data, callback, options) ->
        console.log "POST URL TO " + url
        deferred = $q.defer()
        if data == null
            data = {}
        @downloadsInProgress++

        promise = $http(
            method: "POST"
            url: url
            data: data
            headers:
                "Content-Type": "application/json"
                "Accept": "application/json,application/octet-stream"
        )
        promise.success (data, status, headers, config) ->
            @downloadsInProgress--
            return deferred.resolve(callback(
                data: data,
                status: status,
                headers: headers,
                config: config,
                success: true
            ))

        promise.error (data, status, headers, config) ->
            @downloadsInProgress--
            #display error
            if !!data
                messageFlash.displayError translationService.translateExceptionsDTO(data)
            return deferred.resolve(callback(
                data: data,
                status: status,
                headers: headers,
                config: config,
                success: false
            ))

        return deferred.promise

    return
