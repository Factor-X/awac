# simple download service
angular
.module('app.services')
.service "messageFlash", (flash) ->

    #
    # display a success message
    # TODO => display multiple messages
    @display = (type, message, opts) ->
        options =
            message: message
            type: type
            hideAfter: 5
            showCloseButton: true

        Messenger().post angular.extend(options, angular.copy(opts))

    #
    # display a success message
    # TODO => display multiple messages
    @displaySuccess = (message, opts) ->
        @display('success', message, opts)

    #
    # display an info message
    # TODO => display multiple messages
    @displayInfo = (message, opts) ->
        @display('info', message, opts)

    #
    # display an error message
    # TODO => display multiple messages
    @displayError = (message, opts) ->
        @display('error', message, opts)

    return
