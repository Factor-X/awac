# simple download service
angular
.module('app.services')
.service "modalService", ($rootScope) ->

    @LOADING = 'LOADING'
    @DOCUMENT_MANAGER = 'DOCUMENT_MANAGER'
    @CONFIRMATION_EXIT_FORM = 'CONFIRMATION_EXIT_FORM'


    @show = (modalName,params) ->
        arg = {}
        arg.show = true
        arg.params = params
        target ='SHOW_MODAL_'+modalName
        $rootScope.$broadcast(target, arg)
        $rootScope.displayModalBackground = true

    @hide = (modalName) ->
        arg = {}
        arg.show = false
        $rootScope.$broadcast('SHOW_MODAL_'+modalName, arg)
        $rootScope.displayModalBackground = false

    return