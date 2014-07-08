angular
.module('app.directives')
.directive "mmAwacModalLogin", (directiveService) ->
  restrict: "E"
  scope: {}
  templateUrl: "$/angular/templates/mm-awac-modal-login.html"
  controller: ($scope, downloadService, translationService, $sce, $modal, $http) ->

    $scope.login = ""
    $scope.login_placeholder="Your login"
    $scope.loginExpectedMessage="Between 4 and 20 letters"
    $scope.loginIsValid=false

    $scope.password = ""
    $scope.passwordIsValid=false
    $scope.passwordExpectedMessage="Between 4 and 20 letters"

    $scope.isLoading = false
    $scope.errorMessage=""


    $scope.loginInfo= [
      fieldTitle:"Your login"
      fieldType:"text"
      placeholder:"your login"
      validationMessage:"between 5 and 20 letters"
      field:"login"
    ]





    #$keyboardManager.bind 'ctrl+shift+d', () ->
    #  console.log 'Callback ctrl+shift+d'


    #$document.bind('keypress', function(event) {
      #console.debug(event)
    #})

    $scope.controlLoginField = () ->
      if $scope.login.length>4 && $scope.login.length< 20
        $scope.loginIsValid=true
      else
        $scope.loginIsValid=false

    $scope.controlPasswordField = () ->
      if $scope.password.length>4 && $scope.password.length< 20
        $scope.passwordIsValid=true
      else
        $scope.passwordIsValid=false

    $scope.send = () ->

      #remove the error message
      $scope.errorMessage=""

      #active loading mode
      $scope.isLoading =true

      #send request
      promise = $http
        method: "POST"
        url: 'login'
        headers:
          "Content-Type": "application/json"
        data:
          login: $scope.login
          password: $scope.password

      promise.success (data, status, headers, config) ->
        #close the modal
        $('#modalLogin').modal('hide')
        return

      promise.error (data, status, headers, config) ->
        #display the error message
        $scope.errorMessage = "Error : " + data.message
        #disactive loading mode
        $scope.isLoading=false
        return

      return false
  link: (scope) ->

