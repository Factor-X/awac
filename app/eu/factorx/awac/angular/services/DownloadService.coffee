# simple download service
angular
.module('app.services')
.service "downloadService", ($http, $q) ->
    @downloadsInProgress = 0

    @getDownloadsInProgress = ->
        @downloadsInProgress

    @getJson = (url, callback) ->
        console.log "GET URL TO "+url
        deferred = $q.defer()
        @downloadsInProgress++
        promise = $http(
            method: "GET"
            url: url
            headers:
                "Content-Type": "application/json"
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
            return deferred.resolve(callback(
                data: data,
                status: status,
                headers: headers,
                config: config,
                success: false
            ))

        return deferred.promise

    @postJson = (url, data, callback) ->
        console.log "POST URL TO "+url
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
            return deferred.resolve(callback(
                data: data,
                status: status,
                headers: headers,
                config: config,
                success: false
            ))

        return deferred.promise

    return
