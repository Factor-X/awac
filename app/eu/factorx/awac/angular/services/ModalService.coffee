# simple download service
angular
.module('app.services')
.service "modalService", ($rootScope) ->

    @LOADING = 'loading'
    @DOCUMENT_MANAGER = 'DOCUMENT_MANAGER'
    @CONFIRMATION_EXIT_FORM = 'confirmation-exit-form'
    @QUESTION_COMMENT = 'question-comment'
<<<<<<< HEAD
    @EMAIL_CHANGE = 'email-change'
    @PASSWORD_CHANGE = 'password-change'
=======
>>>>>>> f51edcc014775b241af277fc4f55bac5d26f5920


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