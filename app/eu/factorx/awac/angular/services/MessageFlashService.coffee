# simple download service
angular
.module('app.services')
.service "messageFlash", (flash) ->

    #
    # display a success message
    # TODO => display multiple messages
    @displaySuccess = (message) ->
        flash.success = message.replace "\n", '<br />'

    #
    # display an error message
    # TODO => display multiple messages
    @displayError = (message) ->
        flash.error = message.replace /\n/g, '<br />'

    return
