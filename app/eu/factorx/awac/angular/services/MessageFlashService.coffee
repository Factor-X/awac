# simple download service
angular
.module('app.services')
.service "messageFlash", (flash) ->

    #
    # display a success message
    # TODO => display multiple messages
    @displaySuccess = (message) ->
        Messenger().post
            message: message
            type: 'success'
            hideAfter: 5
            showCloseButton: true

    #
    # display an info message
    # TODO => display multiple messages
    @displayInfo = (message) ->
        Messenger().post
            message: message
            type: 'info'
            hideAfter: 5
            showCloseButton: true

    #
    # display an error message
    # TODO => display multiple messages
    @displayError = (message) ->
        Messenger().post
            message: message
            type: 'error'
            hideAfter: 5
            showCloseButton: true

    return
