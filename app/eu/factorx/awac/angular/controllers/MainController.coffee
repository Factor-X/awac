angular
.module('app.controllers')
.controller "MainCtrl", ($scope, downloadService, translationService, $sce, $modal, $http) ->
  $scope.isLoading = ->
    for k of $scope.initialLoad
      return true  unless $scope.initialLoad[k]
    false

  $scope.initialLoad =
    translations: false

  $scope.$on "LOAD_FINISHED", (event, args) ->
    if args.type is "TRANSLATIONS"
      $scope.initialLoad.translations = args.success
    return

  translationService.initialize('fr')

  $scope.language = 'fr';

  $scope.$watch 'language', (lang) ->
    translationService.initialize(lang)

  #$scope.prettyPrint = (o) ->
  #    return $sce.trustAsHtml(hljs.highlight('json', JSON.stringify(o, null, '  ')).value);



  $scope.logout = () ->
    console.log("logout ??")
    promise = $http
      method: "POST"
      url: 'logout'
      headers:
        "Content-Type": "application/json"
    promise.success (data, status, headers, config) ->
      $scope.currentUser = null
      $('#modalLogin').modal('show')
      return

    promise.error (data, status, headers, config) ->
      return

  $scope.currentUser=null

  $scope.setCurrentUser = (user) ->
    $scope.currentUser = user

  $scope.getCurrentUser = () ->
    return $scope.currentUser

  $scope.testAuthentication = () ->
    promise = $http
      method: "POST"
      url: 'testAuthentication'
      headers:
        "Content-Type": "application/text"

    promise.success (data, status, headers, config) ->
      $scope.setCurrentUser(data)
      return

    promise.error (data, status, headers, config) ->
      $('#modalLogin').modal('show')
      $scope.$apply()
      return

  #test the authentication of the user
  $scope.testAuthentication()



