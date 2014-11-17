angular
.module('app.services')
.service "generateId", ($rootScope) ->

    @generate = ->
        text = ""
        possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        i=0
        while(i<20)
            text += possible.charAt(Math.floor(Math.random() * possible.length))
            i++
        return text
    return