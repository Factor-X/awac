angular
.module('app.directives')
.directive "ngEnter", () ->
  return (scope, element, attrs) ->
    element.bind("keydown keypress", (event) ->
      if event.which == 13
        scope.$apply( () ->
          scope.$eval(attrs.ngEnter)
        event.preventDefault()

.directive "mmAwacModalLogin", (directiveService) ->
  restrict: "E"
  scope: {}
  templateUrl: "$/angular/templates/mm-awac-modal-login.html"
  controller: ($scope, downloadService, translationService, $sce, $modal, $http) ->

    $scope.firstTime=true

    #change option of the modal
    $('#modalLogin').modal({
      backdrop:'static'
    })

    #initialize the modal when it's displayed
    $('#modalLogin').on 'shown.bs.modal', (e) ->
      if $scope.firstTime
        $scope.firstTime=false
      else
        $scope.initialize()
        #refresh angular
        $scope.$apply()



    #initilize variables for the modal
    $scope.initialize = () ->

      console.log("initialization !!")
      $scope.loginInfo=
        fieldTitle:"Your login"
        fieldType:"text"
        placeholder:"your login"
        validationMessage:"between 5 and 20 letters"
        field:""
        isValid:false

      $scope.passwordInfo=
        fieldTitle:"Your password"
        fieldType:"password"
        validationMessage:"between 5 and 20 letters"
        field:""
        isValid:false

      $scope.isLoading=false
      $scope.errorMessage=""

    #intialize the modal
    $scope.initialize()

    #control is all field are valid
    $scope.allFieldValid = () ->
      if $scope.loginInfo.isValid && $scope.passwordInfo.isValid
        return true
      return false

    #send the request to the server
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
          login: $scope.loginInfo.field
          password: $scope.passwordInfo.field

      promise.success (data, status, headers, config) ->
        $scope.$parent.setCurrentUser(data)
        #close the modal
        $('#modalLogin').modal('hide')
        $scope.$apply()
        return

      promise.error (data, status, headers, config) ->
        #display the error message
        $scope.errorMessage = "Error : " + data.message
        #disactive loading mode
        $scope.isLoading=false
        return

      return false
  link: (scope) ->

