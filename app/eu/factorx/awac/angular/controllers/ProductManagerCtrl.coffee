angular
.module('app.controllers')
.controller "ProductManagerCtrl", ($scope, translationService, modalService, downloadService, messageFlash) ->
    $scope.isLoading = {}
    if $scope.$root.periods?
        $scope.assignPeriod = $scope.$root.periods[0].key
    else
        $scope.$watch '$root.periods', ->
            $scope.assignPeriod = $scope.$root.periods[0].key
    $scope.isPeriodChecked = {}

    $scope.selectedPeriodForEvent = $scope.$root.periods[0].key

    # load my organization
    modalService.show(modalService.LOADING)
    downloadService.getJson 'awac/organization/getMyOrganization', (result) ->
        if not result.success
            modalService.close(modalService.LOADING)
        else
            modalService.close(modalService.LOADING)
            $scope.organization = result.data

            $scope.$watchCollection 'assignPeriod', ->
                $scope.refreshPeriod()

            $scope.refreshPeriod = ->
                for product in $scope.organization.products
                    $scope.isPeriodChecked[product.id] = $scope.periodAssignTo(product)


            $scope.toForm = ->
                $scope.$root.navToLastFormUsed()


            $scope.getProductList = () ->
                return $scope.organization.products

            $scope.editOrCreateProduct = (product) ->
                params = {}
                if product?
                    params.product = product
                params.organization = $scope.organization
                params.refreshMyProduct = ->
                    $scope.refreshMyProducts()
                    $scope.refreshPeriod()

                modalService.show(modalService.EDIT_PRODUCT, params)

            $scope.addUsers = (product) ->
                params = {}
                if product?
                    params.product = product
                params.organization = $scope.organization
                # TODO
                modalService.show(modalService.ADD_USER_PRODUCT, params)

            $scope.periodAssignTo = (product) ->
                if product.listPeriodAvailable?
                    for period in product.listPeriodAvailable
                        if period.key == $scope.assignPeriod
                            return true
                return false

            $scope.assignPeriodToProduct = (product) ->
                $scope.isLoading[product.id] = true
                data =
                    periodKeyCode: $scope.assignPeriod
                    productId: product.id
                    assign: !$scope.periodAssignTo(product)

                downloadService.postJson 'awac/product/assignPeriodToProduct', data, (result) ->
                    $scope.isLoading[product.id] = false
                    if result.success
                        product.listPeriodAvailable = result.data.periodsList
                        $scope.refreshMyProduct()

            #
            # this function replace my product by organization product
            #
            $scope.refreshMyProducts = () ->
                myScope = []
                for product in $scope.organization.products
                    if product.listPersons?
                        for person in product.listPersons
                            if person.identifier == $scope.$root.currentPerson.identifier
                                myScope.push product
                $scope.$root.mySites = myScope