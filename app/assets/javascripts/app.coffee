define [
  "angular"
  "jquery"
  "angular-route"
  "domReady"
  "angular-animate"
  "angular-sanitize"
  "ui-bootstrap"
  "angular-flash"
  "angular-file-upload"
  "underscore"
  "angular-bootstrap"
  "d3js"
  "dangle"
  "./controllers/index"
  "./directives/index"
  "./filters/index"
  "./services/index"
], (ng) ->
  "use strict"

#  ng.module 'app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'dangle']

#
#  ng.module 'app.filters', []
#
#  ng.module 'app.services', []
#
#  ng.module 'app.controllers', ['app.services', 'ngRoute', 'angular-flash.service',
#                                   'angular-flash.flash-alert-directive',
#                                   'angularFileUpload']
  ng.module "app", [
    "app.directives",
    "app.filters",
    "app.services",
    "app.controllers"
    "ngRoute"
  ]

  #rootScope
  ng.module('app').run ($rootScope, $location, $http, flash)->

    #
    # Redirect user to login view if not logged in
    #
    if not $rootScope.currentPerson
      $location.path('/login')

    #
    # Is login
    #
    $rootScope.isLogin = () ->
      return $location.path().substring(0, 6) == "/login"

    #
    # logout the current user
    #
    $rootScope.logout = () ->
      promise = $http
        method: "POST"
        url: 'logout'
        headers:
          "Content-Type": "application/json"
      promise.success (data, status, headers, config) ->
        $rootScope.currentPerson = null
        $location.path('/login')
        return

      promise.error (data, status, headers, config) ->
        $location.path('/login')
        return
    #
    # success after login => store some datas, display the path
    #
    $rootScope.loginSuccess = (data) ->
      $rootScope.periods = data.availablePeriods
      $rootScope.currentPerson = data.person
      $rootScope.organization = data.organization
      $rootScope.users = data.organization.users

      $location.path('/form1/' + data.defaultPeriod + '/' + data.organization.sites[0].scope)

    #
    # get user
    #
    $rootScope.getUserByIdentifier = (identifier) ->
      for user in $rootScope.users
        if user.identifier == identifier
          return user
      return null

    #
    # test if the user is currently connected on the server
    #
    promise = $http
      method: "POST"
      url: 'testAuthentication'
      headers:
        "Content-Type": "application/text"
    promise.success (data, status, headers, config) ->
      $rootScope.loginSuccess data
      return

    promise.error (data, status, headers, config) ->
      return

    #
    # parameter to display / hide the modal background
    #
    $rootScope.displayModalBackground = false
