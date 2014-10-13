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
    @INVITE_USER = 'invite-user'
    @EDIT_SITE = 'edit-site'
    @ADD_USER_SITE = 'add-user-site'
    @EDIT_EVENT = 'edit-event'
    @CONFIRM_CLOSING = 'confirm-closing'
    @HELP = 'help'
    @EDIT_OR_CREATE_REDUCTION_ACTION = 'edit-or-create-reduction-action'
    @REQUEST_VERIFICATION = 'request_verification'
    @VERIFICATION_ASSIGN = 'verification_assign'
    @VERIFICATION_REJECT = 'verification_reject'
    @VERIFICATION_REJECT='verification_reject'

    @show = (modalName, params) ->
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