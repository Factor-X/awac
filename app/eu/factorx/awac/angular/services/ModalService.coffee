# simple download service
angular
.module('app.services')
.service "modalService", ($rootScope) ->

    @LOADING = 'loading'
    @DOCUMENT_MANAGER = 'DOCUMENT_MANAGER'
    @CONFIRMATION_EXIT_FORM = 'confirmation-exit-form'
    @QUESTION_COMMENT = 'question-comment'
    @EMAIL_CHANGE = 'email-change'
    @PASSWORD_CHANGE = 'password-change'
    @CONNECTION_PASSWORD_CHANGE = 'connection-password-change'

    @show = (modalName,params) ->

        args = []
        args.show = true
        args.params = params
        args.target = modalName
        $rootScope.displayModalBackground = true
        $rootScope.$broadcast('SHOW_MODAL', args)

    @close = (modalName) ->

        args = []
        args.show = false
        args.target = modalName
        $rootScope.displayModalBackground = false
        $rootScope.$broadcast('SHOW_MODAL', args)

    return