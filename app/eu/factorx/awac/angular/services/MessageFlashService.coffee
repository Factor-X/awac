# simple download service
angular
.module('app.services')
.service "messageFlash", (flash, translateTextFilter) ->

    #
    # display a success message
    # TODO => display multiple messages
    @displaySuccess = (message) ->
        Messenger().post
            message: translateTextFilter(message)
            type: 'success'
            hideAfter: 5
            showCloseButton: true

    #
    # display an info message
    # TODO => display multiple messages
    @displayInfo = (message) ->
        Messenger().post
            message: translateTextFilter(message)
            type: 'info'
            hideAfter: 5
            showCloseButton: true

    #
    # display an error message
    # TODO => display multiple messages
    @displayError = (message) ->
        Messenger().post
            message: translateTextFilter(message)
            type: 'error'
            hideAfter: 5
            showCloseButton: true

    return
