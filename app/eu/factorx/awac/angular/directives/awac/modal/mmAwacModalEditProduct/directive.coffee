angular
.module('app.directives')
.directive "mmAwacModalEditProduct", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-edit-product.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.createNewProduct = true

        if $scope.getParams().product?
            $scope.product =  angular.copy($scope.getParams().product)
            $scope.createNewProduct =false
        else
            $scope.product = {}

        $scope.fields = {

            name :{
                fieldTitle: "NAME"
                field:$scope.product.name
                validationRegex: "^.{1,255}$"
                validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
                focus: ->
                    return true
            }

            description :{
                fieldTitle: "DESCRIPTION"
                fieldType:'textarea'
                validationRegex: "^.{0,65000}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_TEXT"
                field:$scope.product.description
                hideIsValidIcon: true
            }
        }

        $scope.allFieldValid = () ->
            for key in Object.keys($scope.fields)
                if key != '$$hashKey'
                    if !$scope.fields[key].isValid? || $scope.fields[key].isValid == false
                        return false
            return true

        #send the request to the server
        $scope.save = () ->

            if $scope.allFieldValid()

                #create DTO
                data = {}
                data.name = $scope.fields.name.field
                data.description = $scope.fields.description.field

                $scope.isLoading = true

                if $scope.getParams().product?

                    #edit product
                    data.id = $scope.getParams().product.id
                    downloadService.postJson '/awac/product/edit', data, (result) ->
                        if result.success

                            #display success message
                            messageFlash.displaySuccess translationService.get "CHANGES_SAVED"

                            #edit product
                            $scope.getParams().product.name = $scope.fields.name.field
                            $scope.getParams().product.description = $scope.fields.description.field

                            #refresh my product
                            $scope.getParams().refreshMyProduct()

                            #close window
                            $scope.close()
                        else
                            $scope.isLoading = false
                else
                    #create product
                    downloadService.postJson '/awac/product/create', data, (result) ->
                        if result.success

                            #display success message
                            messageFlash.displaySuccess translationService.get "CHANGES_SAVED"

                            # add new product to the list
                            $scope.getParams().organization.products.push result.data

                            # add to mySite
                            $scope.$root.mySites.push result.data

                            #refresh
                            console.log "$scope.getParams()"
                            console.log $scope.getParams()
                            $scope.getParams().refreshMyProduct()

                            #close window
                            $scope.close()
                        else
                            $scope.isLoading = false
            return false

        $scope.close = ->
            modalService.close modalService.EDIT_PRODUCT

    link: (scope) ->

