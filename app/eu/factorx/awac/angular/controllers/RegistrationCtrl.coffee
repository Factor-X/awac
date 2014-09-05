angular
.module('app.controllers')
.controller "RegistrationCtrl", ($scope,downloadService, $location, messageFlash, $compile,$timeout,modalService,translationService,$routeParams) ->


  $scope.loading = false

  $scope.tabActive = []
#
#  $scope.$watch 'tabActive[1]', ->
#    if $scope.tabActive[1] != true
#      $scope.forgotEmailSuccessMessage = null
#      $scope.forgotPasswordInfo.field = ""

  $scope.enterEvent = ->

    if $scope.tabActive[0] == true
      $scope.send()
#    else if $scope.tabActive[1] == true
#      $scope.sendForgotPassword()

  $scope.loginInfo =
    fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE"
    fieldType: "text"
    placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER"
    validationRegex: "^\\S{5,20}$"
    validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH"
    field: ""
    isValid: false
    focus: ->
      return $scope.tabActive[0]

  $scope.passwordInfo =
    fieldTitle: "LOGIN_FORM_PASSWORD_FIELD_TITLE"
    fieldType: "password"
    validationRegex: "^\\S{5,20}$"
    validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
    field: ""
    isValid: false

#  $scope.forgotPasswordInfo =
#    fieldTitle: "IDENTIFIENT_OR_EMAIL"
#    fieldType: "text"
#    validationRegex: "^\\S+$"
#    validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
#    field: ""
#    isValid: false
#    focus:  ->
#      return $scope.tabActive[1]

  $scope.lastNameInfo =
    fieldTitle: "USER_LASTNAME"
    validationRegex: "^.{1,255}$"
    validationMessage: "USER_LASTNAME_WRONG_LENGTH"
    #field: $scope.$root.currentPerson.lastName
    hideIsValidIcon: true
    isValid: true
    focus: ->
      return true

  $scope.firstNameInfo =
    fieldTitle: "USER_FIRSTNAME"
    fieldType: "text"
    validationRegex: "^.{1,255}$"
    validationMessage: "USER_FIRSTNAME_WRONG_LENGTH"
    #field: $scope.$root.currentPerson.firstName
    hideIsValidIcon: true
    isValid: true

  $scope.emailInfo =
    fieldTitle: "USER_EMAIL"
    #disabled: true
    #field: $scope.$root.currentPerson.email


  $scope.connectionFieldValid = () ->
    if $scope.loginInfo.isValid && $scope.passwordInfo.isValid && $scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid && $scope.emailInfo.isValid
      return true
    return false

#  $scope.forgotPasswordFieldValid = () ->
#    if $scope.forgotPasswordInfo.isValid && $scope.forgotPasswordInfo.isValid
#      return true
#    return false

  #send the request to the server
  $scope.send = () ->

    #active loading mode
    $scope.isLoading = true

    #send request
    downloadService.postJson '/awac/register', { login: $scope.loginInfo.field, password: $scope.passwordInfo.field, lastName: $scope.lastNameInfo.field, firstName: $scope.firstNameInfo.field, interfaceName: $scope.$root.instanceName, email: $scope.emailInfo.field, key:  $routeParams.key }, (result) ->
      if result.success
#        $scope.$root.loginSuccess(result.data)
        messageFlash.displaySuccess "You are now registered. Please log-in."
        $scope.isLoading = false
        $location.path('/login')
      else
        messageFlash.displaySuccess "Registered fails. Please try again."
        $scope.isLoading = false
         #display the error message
        messageFlash.displayError result.data.message
    #disactive loading mode

    return false

#  $scope.forgotEmailSuccessMessage = null
#
#  $scope.sendForgotPassword = () ->
#
#    #active loading mode
#    $scope.isLoading = true
#
#    #send request
#    downloadService.postJson '/awac/forgotPassword', { identifier: $scope.forgotPasswordInfo.field, interfaceName: $scope.$root.instanceName }, (result) ->
#      if result.success
#        $scope.forgotEmailSuccessMessage = translationService.get('LOGIN_FORGOT_PASSWORD_SUCCESS')
#        $scope.isLoading = false
#        return
#      else
#        #display the error message
#        messageFlash.displayError result.data.message
#        #disactive loading mode
#        $scope.isLoading = false
#        return
#
#    return false

  $scope.injectRegistrationDirective = ->
    if $scope.$root?
      if $scope.$root.instanceName == 'enterprise'
        directiveName = "mm-awac-registration-enterprise"
      else if $scope.$root.instanceName == 'municipality'
        directiveName = "mm-awac-registration-municipality"

      directive = $compile("<" + directiveName + "></" + directiveName + ">")($scope)
      $('.inject-registration-form').append(directive)

  $timeout(->
    $scope.injectRegistrationDirective()
  , 0)