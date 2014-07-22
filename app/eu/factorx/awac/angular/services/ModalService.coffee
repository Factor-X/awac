# simple download service
angular
.module('app.services')
.service "modalService", () ->
    @show = (modalName) ->
      $("#"+modalName).modal('show')

    @hide = (modalName) ->
      $("#"+modalName).modal('hide')

    return