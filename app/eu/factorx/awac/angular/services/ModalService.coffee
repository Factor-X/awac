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
    @CREATE_REDUCTION_ACTION = 'create-reduction-action'
    @EDIT_OR_CREATE_REDUCTION_ACTION = 'edit-or-create-reduction-action'
    @REQUEST_VERIFICATION = 'request_verification'
    @VERIFICATION_ASSIGN = 'verification_assign'
    @VERIFICATION_REJECT = 'verification_reject'
    @VERIFICATION_REJECT='verification_reject'
    @CONSULT_EVENT='consult-event'
    @VERIFICATION_FINALIZATION = 'verification-finalization'
    @PASSWORD_CONFIRMATION = 'password-confirmation'
    @VERIFICATION_CONFIRMATION = 'verification-confirmation'
    @VERIFICATION_DOCUMENT='verification-document'
    @VERIFICATION_FINALIZATION_VISUALIZATION='verification-finalization-visualization'
    @DRIVER_ADD_VALUE = 'driver-add-value'
    @CONFIRM_DIALOG = 'confirm-dialog'
    @AGREEMENT = 'agreement'
    @CREATE_OR_EDIT_REDUCTION_ACTION_ADVICE = 'create-or-edit-reduction-action-advice'
    @EDIT_SUB_LIST = 'edit-sub-list'
    @SEND_EMAIL = 'send-email'
    @EDIT_PRODUCT = 'edit-product'
    @ADD_USER_PRODUCT = 'add-user-product'

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