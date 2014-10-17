angular
.module('app.controllers')
.controller "VerificationSubmitCtrl",  ($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile,$window) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu

    $scope.selectedRequest = null
    $scope.form = null
    $scope.requestSelected = null

    modalService.show(modalService.LOADING)
    downloadService.getJson '/awac/verification/verificationRequestsVerifiedToConfirm', (result)->
        if not result.success
            modalService.close(modalService.LOADING)
            messageFlash.displaySuccess translationService.get result.data
        else
            modalService.close(modalService.LOADING)
            $scope.requests = result.data.list
            $scope.displayTable()

    $scope.displayTable = () ->
        if not $scope.requests
            $scope.requests = []
        if $scope.tableParams?
            $scope.tableParams.reload();
        else
            $scope.tableParams = new ngTableParams(
                page: 1 # show first page
                count: 100 # count per page
            ,
                counts: []
                total: 1
                getData: ($defer, params) ->
                    orderedData = $filter("orderBy")($scope.requests, params.orderBy())
                    $defer.resolve orderedData.slice((params.page() - 1) * params.count(),
                            params.page() * params.count())
                    params.total(orderedData.length)
                    return
            )

    $scope.selectRequest = (request) ->
        $('.injectForm:first').empty()
        $('.injectFormMenu:first').empty()

        if $scope.directiveMenu?
            $scope.directiveMenu.remove()
        if $scope.directive?
            $scope.directive.remove()

        if request?
            $scope.requestSelected = request
        $scope.selectedRequest = $scope.requestSelected
        $scope.$root.periodSelectedKey = $scope.requestSelected.period.key
        $scope.$root.scopeSelectedId = $scope.requestSelected.scope.id

        #load content
        #compute form
        if not $scope.form
            if $scope.requestSelected.organizationCustomer.interfaceName == 'enterprise'
                $scope.form = 'TAB2'
            if $scope.requestSelected.organizationCustomer.interfaceName == 'municipality'
                $scope.form = 'TAB_C1'

        #inject
        if $scope.form == '/results'
            directiveName = "<div ng-include=\"'$/angular/views/results.html'\" ng-controller=\"ResultsCtrl\"></div>"
        else
            directiveName = "<div ng-include=\"'$/angular/views/enterprise/" + $scope.form + ".html'\" ng-init=\"init('" + $scope.form + "')\" ng-controller=\"FormCtrl\"></div>"
        $scope.directive = $compile(directiveName)($scope)
        $('.injectForm:first').append($scope.directive)

        #load menu
        $scope.directiveMenu = $compile("<mm-awac-" + $scope.requestSelected.organizationCustomer.interfaceName + "-menu form=\"" + $scope.form + "\"></mm-awac-" + $scope.requestSelected.organizationCustomer.interfaceName + "-menu>")($scope)

        $scope.displayMenu = true
        $('.injectFormMenu:first').append($scope.directiveMenu)

        $scope.$root.mySites = [request.scope]

    $scope.isMenuCurrentlySelected = (target) ->
        if '/form/' + $scope.form == target || $scope.form == target
            return true
        return false

    $scope.downloadFile =->
        url = '/awac/file/download/' + $scope.selectedRequest.verificationSuccessFileId
        $window.open(url)

    $scope.validVerificationFinalization = (valid) ->

        if valid== true
            if $scope.requestSelected.status == 'VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_SUCCESS'
                newStatus ='VERIFICATION_STATUS_WAIT_CUSTOMER_VERIFIED_CONFIRMATION'
            else
                newStatus ='VERIFICATION_STATUS_CORRECTION'
        else
            newStatus ='VERIFICATION_STATUS_VERIFICATION'

        data =
            url:"/awac/verification/setStatus"
            successMessage:"CHANGES_SAVED"
            title:"VALID_REQUEST"
            data:
                scopeId: $scope.selectedRequest.scope.id
                periodKey: $scope.selectedRequest.period.key
                newStatus:newStatus
            afterSave:->
                $scope.removeRequest()
        modalService.show modalService.PASSWORD_CONFIRMATION, data

    $scope.navTo = (target) ->
        form = target.replace('/form/', '')
        $scope.form = form
        $scope.selectRequest()

    $scope.consultFinalComment = ->
        data =
            comment: $scope.selectedRequest.verificationRejectedComment
        modalService.show modalService.VERIFICATION_FINALIZATION_VISUALIZATION, data

    $scope.consultEvent = () ->
        data =
            organizationCustomer: $scope.requestSelected.organizationCustomer
        modalService.show modalService.CONSULT_EVENT,


    $scope.removeRequest = ->
        i=0
        for request in $scope.requests
            if request.id == $scope.requestSelected.id
                $scope.requests.splice i,1
            i++
        $scope.displayTable()
        $('.injectForm:first').empty()
        $('.injectFormMenu:first').empty()
        $scope.requestSelected = null