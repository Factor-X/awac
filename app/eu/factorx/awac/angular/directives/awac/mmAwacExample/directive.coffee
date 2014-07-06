angular
.module('app.directives')
.directive "mmAwacExample", (directiveService, $http) ->
  restrict: "E"
  scope: {}
  templateUrl: "$/angular/templates/mm-awac-example.html"
  controller: (s) ->
    s.login = "Your login"
    s.password = ""

    s.send = () ->
      promise = $http
        method: "POST"
        url: 'login'
        headers:
          "Content-Type": "application/json"
        data:
          login: s.login
          password: s.password

      promise.success (data, status, headers, config) ->
        s.message = data.firstName + " " + data.lastName
        return

      promise.error (data, status, headers, config) ->
        s.message = "error : " + data.message
        return

      return false







