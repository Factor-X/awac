angular
.module('app.controllers')
.controller "VerificationVerificationCtrl", ($scope,displayLittleFormMenu,downloadService,modalService,messageFlash,translationService,ngTableParams,$filter,$compile) ->

    $scope.displayLittleFormMenu=displayLittleFormMenu

    $scope.selectedRequest = null

    modalService.show(modalService.LOADING)
    downloadService.getJson '/awac/verification/myVerificationRequests', (result)->
        console.log result
        if not result.success
            modalService.close(modalService.LOADING)
            messageFlash.displaySuccess translationService.get result.data
        else
            modalService.close(modalService.LOADING)
            $scope.requests = result.data.list
            $scope.displayTable()

    $scope.displayTable = () ->
        if not $scope.requests
            $scope.requests=[]
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
                    $defer.resolve orderedData.slice((params.page() - 1) * params.count(),params.page() * params.count())
                    params.total(orderedData.length)
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
        $scope.selectedRequest = $scope.requestSelected
        $scope.$root.periodSelectedKey = $scope.requestSelected.period.key
        $scope.$root.scopeSelectedId = $scope.requestSelected.scope.id

        #load content
        #compute form
        if not $scope.form
            if $scope.requestSelected.organizationCustomer.interfaceName == 'enterprise'
                $scope.form='TAB2'
            if $scope.requestSelected.organizationCustomer.interfaceName == 'municipality'
                $scope.form='TAB_C1'

        #inject
        directiveName = "<div ng-include=\"'$/angular/views/enterprise/"+$scope.form+".html'\" ng-init=\"init('"+$scope.form+"')\" ng-controller=\"FormCtrl\"></div>"
        $scope.directive = $compile(directiveName)($scope)
        $('.injectForm:first').append($scope.directive)

        #load menu
        $scope.directiveMenu = $compile("<mm-awac-"+$scope.requestSelected.organizationCustomer.interfaceName+"-menu form=\""+$scope.form+"\"></mm-awac-"+$scope.requestSelected.organizationCustomer.interfaceName+"-menu>")($scope)

        $scope.displayMenu = true
        $('.injectFormMenu:first').append($scope.directiveMenu)

    $scope.isMenuCurrentlySelected = (target) ->
        if '/form/'+$scope.form == target
            return true
        return false

    $scope.navTo = (target) ->
        form = target.replace('/form/','')
        $scope.form = form
        $scope.selectRequest()
