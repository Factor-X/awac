# simple download service
angular
.module('app.services')
.service "downloadService", ($http) ->
    @downloadsInProgress = 0

    @getDownloadsInProgress = ->
        @downloadsInProgress

    @getJson = (url, callback) ->
        @downloadsInProgress++
        promise = $http(
            method: "GET"
            url: url
            headers:
                "Content-Type": "application/json"
        )
        promise.success (data, status, headers, config) ->
            @downloadsInProgress--
            callback
                data: data,
                status: status,
                headers: headers,
                config: config,
                success: true
            return

        promise.error (data, status, headers, config) ->
            @downloadsInProgress--
            callback
                data: data,
                status: status,
                headers: headers,
                config: config,
                success: false
            return

        promise

    @postJson = (url, data, callback) ->
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
            callback
                data: data,
                status: status,
                headers: headers,
                config: config,
                success: true
            return

        promise.error (data, status, headers, config) ->
            @downloadsInProgress--
            callback
                data: data,
                status: status,
                headers: headers,
                config: config,
                success: false
            return

        promise

    return
