angular
.module('app.controllers')
.controller "VerificationVerificationCtrl", ($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu

    $scope.selectedRequest = null

    modalService.show(modalService.LOADING)
    downloadService.getJson '/awac/verification/myVerificationRequests', (result)->
        if not result.success
            modalService.close(modalService.LOADING)
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

    $scope.form = null
    $scope.requestSelected = null
    $scope.selectRequest = (request) ->
        $('.injectForm:first').empty()
        $('.injectFormMenu:first').empty()

        if $scope.directiveMenu?
            $scope.directiveMenu.remove()
        if $scope.directive?
            $scope.directive.remove()

        if request?
            $scope.requestSelected = request
            $scope.$root.mySites = [request.scope]
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
            directiveName = "<div ng-include=\"'$/angular/views/" + $scope.requestSelected.organizationCustomer.interfaceName + "/" + $scope.form + ".html'\" ng-init=\"init('" + $scope.form + "')\" ng-controller=\"FormCtrl\"></div>"
        $scope.directive = $compile(directiveName)($scope)
        $('.injectForm:first').append($scope.directive)

        #load menu
        $scope.directiveMenu = $compile("<mm-awac-" + $scope.requestSelected.organizationCustomer.interfaceName + "-menu form=\"" + $scope.form + "\"></mm-awac-" + $scope.requestSelected.organizationCustomer.interfaceName + "-menu>")($scope)

        $scope.displayMenu = true
        $('.injectFormMenu:first').append($scope.directiveMenu)


        $scope.testClosingValidation()


    $scope.isMenuCurrentlySelected = (target) ->
        if '/form/' + $scope.form == target || $scope.form == target
            return true
        return false

    $scope.navTo = (target) ->
        form = target.replace('/form/', '')
        $scope.form = form
        $scope.selectRequest()

    $scope.consultEvent = () ->
        data =
            organizationCustomer: $scope.requestSelected.organizationCustomer
        modalService.show modalService.CONSULT_EVENT, data


    $scope.$on 'TEST_CLOSING_VALIDATION', () ->
        $scope.testClosingValidation()

    $scope.testClosingValidation = () ->
        downloadService.getJson '/awac/answer/testClosingValidation/' + $scope.requestSelected.period.key + "/" + $scope.requestSelected.scope.id, (result) ->
            if not result.success
                modalService.close(modalService.LOADING)
            else
                $scope.verificationFinalization = result.data


    $scope.removeRequest = ->
        i = 0
        for request in $scope.requests
            if request.id == $scope.requestSelected.id
                $scope.requests.splice i, 1
            i++
        $scope.displayTable()
        $('.injectForm:first').empty()
        $('.injectFormMenu:first').empty()
        $scope.requestSelected = null

    $scope.finalizeVerification = () ->
        console.log $scope.verificationFinalization
        data =
            removeRequest: $scope.removeRequest
            verificationSuccess: $scope.verificationFinalization.success
            request: $scope.requestSelected
        modalService.show modalService.VERIFICATION_FINALIZATION, data
