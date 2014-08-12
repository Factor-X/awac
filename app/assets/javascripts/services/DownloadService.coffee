# simple download service
define ["./module"], (services) ->
  "use strict"
  services.service "downloadService", ($http) ->
    downloadsInProgress = 0

    @getDownloadsInProgress = ->
        downloadsInProgress

    @getJson = (url, callback) ->
        downloadsInProgress++
        promise = $http(
            method: "GET"
            url: url
            headers:
                "Content-Type": "application/json"
        )
        promise.success (data, status, headers, config) ->
            downloadsInProgress--
            callback data, status, headers, config
            return

        promise.error (data, status, headers, config) ->
            downloadsInProgress--
            callback null, status, headers, config
            console.log "error when loading from " + url
            return

        promise

    return
