# simple download service
angular
.module('app.services')
.service "messageFlash", ($http,flash) ->
  #
  # display a success message
  # TODO => display multiple messages
  @displaySuccess = (message) ->
    flash.success = message

  #
  # display an error message
  # TODO => display multiple messages
  @displayError = (message) ->
    flash.error = message

  return
