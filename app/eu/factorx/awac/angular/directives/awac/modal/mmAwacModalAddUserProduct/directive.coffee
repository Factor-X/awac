angular
.module('app.directives')
.directive "mmAwacModalAddUserProduct", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-add-user-product.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope


        if $scope.getParams().product?
            $scope.product = angular.copy($scope.getParams().product)
            $scope.createNewProduct = false
        else
            $scope.product = {}
            #create a default value for percentOwned
            $scope.product.percentOwned = 100


        $scope.allFieldValid = () ->
            return true

        #send the request to the server
        $scope.save = () ->
            if $scope.allFieldValid()

                $scope.isLoading = true

                #create DTO
                data =
                    organization: $scope.getParams().organization
                    product: $scope.getParams().product
                    selectedAccounts: $scope.selection

                downloadService.postJson '/awac/organization/product/associatedaccounts/save', data, (result) ->
                    if result.success

                        #add product to user
                        ownProduct = false
                        for account in $scope.selection
                            if account.identifier == $scope.$root.currentPerson.identifier
                                ownProduct = true
                                break

                        i = 0
                        founded=false
                        for product in $scope.$root.mySites
                            if parseFloat(product.id) == parseFloat($scope.getParams().product.id)
                                founded=true
                                if ownProduct == false
                                    $scope.$root.mySites.splice(i, 1)
                                break
                            i++


                        if ownProduct==true && founded==false
                            $scope.$root.mySites.push $scope.getParams().product

                        #close window
                        $scope.close()
                    else
                        #display success message
                        messageFlash.displayError result.data.message
                        $scope.isLoading = false

            return false

        # accounts list for a given organization
        $scope.accounts = [];

        #accounts selection for a given product
        $scope.selection = [];

        # toggle selection for a given account by name
        $scope.toggleSelection = (account) ->
            console.log "entering toggleSelection"
            console.log account.identifier

            idx = $scope.selection.indexOf(account)
            console.log idx

            # is currently selected
            if idx > -1
                # remove from selection
                $scope.selection.splice(idx, 1);
                # is newly selected
            else
                # add to selection
                $scope.selection.push(account);
        # user selection - end

        $scope.close = ->
            modalService.close modalService.ADD_USER_PRODUCT

        #get list of all users associated to organisation
        $scope.getAssociatedUsers = () ->
            console.log("entering getAssociatedUsers")

            #empty arrays
            $scope.selection = []
            $scope.accounts = []
            $scope.prepare = []

            #create DTO
            data =
                organization: $scope.getParams().organization
                product: $scope.getParams().product

            downloadService.postJson '/awac/organization/product/associatedaccounts/load', data, (result) ->
                if result.success
                    for user in result.data.organizationUserList
                        $scope.in = false
                        if result.data.productSelectedUserList.length
                            for selected in result.data.productSelectedUserList
                                if (user.identifier == selected.identifier)
                                    $scope.in = true
                        if $scope.in == false
                            $scope.accounts.push(user)

                    for selected in result.data.productSelectedUserList
                        $scope.selection.push(selected)
                        $scope.accounts.push(selected)

            $scope.isLoading = false


    link: (scope) ->
        console.log("entering mmAwacModalAddUserProduct")
        scope.getAssociatedUsers()



