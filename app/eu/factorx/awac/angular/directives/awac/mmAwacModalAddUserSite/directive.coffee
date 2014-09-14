angular
.module('app.directives')
.directive "mmAwacModalAddUserSite", (directiveService, downloadService, translationService, messageFlash, $rootScope) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-add-user-site.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope


        if $scope.getParams().site?
            $scope.site =  angular.copy($scope.getParams().site)
            $scope.createNewSite =false
        else
            $scope.site = {}
            #create a default value for percentOwned
            $scope.site.percentOwned = 100


        $scope.allFieldValid = () ->
            return true

        #send the request to the server
        $scope.save = () ->

            if $scope.allFieldValid()

              $scope.isLoading = true

              #create DTO
              data =
                  organization: $rootScope.organization
                  site: $scope.getParams().site
                  selectedAccounts: $scope.selection

              downloadService.postJson '/awac/organization/site/associatedaccounts/save', data, (result) ->
                    if result.success
                        #display success message
                        messageFlash.displaySuccess "CHANGES_SAVED"
                        #close window
                        $scope.close()
                    else
                        #display success message
                        messageFlash.displayError result.data.message
                        $scope.isLoading = false

            return false

        # accounts list for a given organization
        $scope.accounts=[];

        #accounts selection for a given site
        $scope.selection=[];

        # toggle selection for a given account by name
        $scope.toggleSelection = (account) ->
            console.log "entering toggleSelection"
            console.log account.identifier

            idx = $scope.selection.indexOf(account)
            console.log idx

            # is currently selected
            if idx > -1
              # remove from selection
              $scope.selection.splice(idx,1);
              # is newly selected
            else
              # add to selection
              $scope.selection.push(account);
        # user selection - end

        $scope.close = ->
            modalService.close modalService.ADD_USER_SITE

        #get list of all users associated to organisation
        $scope.getAssociatedUsers = () ->
          console.log("entering getAssociatedUsers")

          #empty arrays
          $scope.selection = []
          $scope.accounts = []

          #create DTO
          data =
            organization: $rootScope.organization
            site: $scope.getParams().site

          downloadService.postJson '/awac/organization/site/associatedaccounts/load', data, (result) ->
            if result.success

              for user in result.data.organizationUserList
                console.log user
                $scope.accounts.push(user)

              for selected in result.data.siteSelectedUserList
                console.log selected
                $scope.selection.push(selected)


#              for user in result.data.organizationUserList
#                console.log user
#                console.log result.data.siteSelectedUserList.length
#                if result.data.siteSelectedUserList.length
#                  for selected in result.data.siteSelectedUserList
#                    index = selected.identifier.indexOf(user.identifier)
#                    console.log index
#                    if index == -1
#                      $scope.accounts.push(user)
#                    else
#                      $scope.accounts.push(result.data.siteSelectedUserList[index])
#                else
#                  $scope.accounts.push(user)
#
#              for selected in result.data.siteSelectedUserList
#                console.log selected
#                $scope.selection.push(selected)

            else
              messageFlash.displayError result.data.message

          $scope.isLoading = false


    link: (scope) ->
      console.log("entering mmAwacModalAddUserSite")
      scope.getAssociatedUsers()



