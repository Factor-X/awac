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


  $scope.showLoginModal = (target) ->
    $modal.open (
      templateUrl: target
    )
    return false

  downloadService.getJson "dummy/household/2014", (data) ->
    $scope.o = data
    $scope.o.consumption.units = $scope.volumeUnits;

    $scope.addOtherFuel = () ->
      console.log 'ok'
      $scope.o.otherFuels.push
        value: 0,
        unit: 0,
        units: $scope.volumeUnits,
        reviewers:
          dataOwner: null
          dataValidator: null
          dataVerifier: null
          dataLocker: null
      return false

  $scope.logout = () ->
    console.log("logout ??")
    promise = $http
      method: "POST"
      url: 'logout'
      headers:
        "Content-Type": "application/json"
    promise.success (data, status, headers, config) ->
      console.log("logout ok")
      return

    promise.error (data, status, headers, config) ->
      console.log("logout error")
      return
