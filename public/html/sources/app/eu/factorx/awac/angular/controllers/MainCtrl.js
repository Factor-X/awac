angular.module('app.controllers').controller("MainCtrl", function($scope, downloadService, translationService, $sce, $http, $location, $route, $routeParams, modalService) {
  $scope.isLoading = function() {
    var k;
    for (k in $scope.initialLoad) {
      if (!$scope.initialLoad[k]) {
        return true;
      }
    }
    return false;
  };
  $scope.initialLoad = {
    translations: false
  };
  $scope.$on("LOAD_FINISHED", function(event, args) {
    if (args.type === "TRANSLATIONS") {
      $scope.initialLoad.translations = args.success;
    }
    return;
  });
  translationService.initialize('fr');
  $scope.language = 'fr';
  $scope.$watch('language', function(lang) {
    return translationService.initialize(lang);
  });
  $scope.isMenuCurrentlySelected = function(loc) {
    if ($location.path().substring(0, loc.length) === loc) {
      return true;
    } else {
      return false;
    }
  };
  window.onbeforeunload = function(event) {
    var canBeContinue, result;
    canBeContinue = true;
    if ($scope.getMainScope().validNavigation !== void 0) {
      result = $scope.getMainScope().validNavigation();
      if (result.valid === false) {
        return "Certaines données n'ont pas été sauvegardées. Êtes-vous sûr de vouloir quittez cette page ?";
      }
    }
  };
  $scope.$on('NAV', function(event, args) {
    return $scope.nav(args.loc, args.confirmed);
  });
  $scope.nav = function(loc, confirmed) {
    var canBeContinue, params, result;
    if (confirmed == null) {
      confirmed = false;
    }
    canBeContinue = true;
    if ($scope.getMainScope().validNavigation !== void 0 && confirmed === false) {
      result = $scope.getMainScope().validNavigation();
      if (result.valid === false) {
        canBeContinue = false;
        params = {};
        params.loc = loc;
        modalService.show(result.modalForConfirm, params);
      }
    }
    if (canBeContinue) {
      $location.path(loc + "/" + $scope.periodKey + "/" + $scope.scopeId);
      if (!$scope.$$phase) {
        return $scope.$apply();
      }
    }
  };
  $scope.periodKey = null;
  $scope.$watch('periodKey', function() {
    var k, p, v;
    $routeParams.period = $scope.periodKey;
    if ($route.current) {
      p = $route.current.$$route.originalPath;
      for (k in $routeParams) {
        v = $routeParams[k];
        p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v);
      }
      $location.path(p);
    }
    if ($scope.periodKey === $scope.periodToCompare) {
      $scope.periodToCompare = 'default';
    }
    $scope.loadPeriodForComparison();
    return $scope.loadFormProgress();
  });
  $scope.$watch('scopeId', function() {
    var k, p, v;
    $routeParams.period = $scope.periodKey;
    if ($route.current) {
      p = $route.current.$$route.originalPath;
      for (k in $routeParams) {
        v = $routeParams[k];
        p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v);
      }
      $location.path(p);
    }
    $scope.loadPeriodForComparison();
    return $scope.loadFormProgress();
  });
  $scope.periodsForComparison = [
    {
      'key': 'default',
      'label': 'aucune periode'
    }
  ];
  $scope.periodToCompare = 'default';
  $scope.save = function() {
    $scope.$broadcast('SAVE');
    return $scope.$root.$broadcast("REFRESH_LAST_SAVE_TIME");
  };
  $scope.$on("$routeChangeSuccess", function(event, current, previous) {
    $scope.periodKey = $routeParams.period;
    return $scope.scopeId = parseInt($routeParams.scope);
  });
  $scope.getMainScope = function() {
    var mainScope;
    return mainScope = angular.element($('[ng-view]')[0]).scope();
  };
  $scope.loadPeriodForComparison = function() {
    var promise, url;
    url = 'answer/getPeriodsForComparison/' + $scope.scopeId;
    if (($scope.scopeId != null) && $scope.scopeId !== NaN && $scope.scopeId !== 'NaN') {
      promise = $http({
        method: "GET",
        url: url,
        headers: {
          "Content-Type": "application/json"
        }
      });
      promise.success(function(data, status, headers, config) {
        var period, _i, _len, _ref;
        $scope.periodsForComparison = [
          {
            'key': 'default',
            'label': 'aucune periode'
          }
        ];
        _ref = data.periodDTOList;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          period = _ref[_i];
          if (period.key !== $scope.periodKey) {
            $scope.periodsForComparison[$scope.periodsForComparison.length] = period;
          }
        }
        return;
      });
      return promise.error(function(data, status, headers, config) {
        return;
      });
    }
  };
  $scope.getProgress = function(form) {
    var formProgress, _i, _len, _ref;
    if ($scope.formProgress !== null) {
      _ref = $scope.formProgress;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        formProgress = _ref[_i];
        if (formProgress.form === form) {
          return formProgress.percentage;
        }
      }
    }
    return 0;
  };
  $scope.formProgress = null;
  $scope.loadFormProgress = function() {
    var promise;
    if (($scope.scopeId != null) && ($scope.periodKey != null)) {
      promise = $http({
        method: "GET",
        url: "answer/formProgress/" + $scope.periodKey + "/" + $scope.scopeId,
        headers: {
          "Content-Type": "application/json"
        }
      });
      return promise.success(function(data, status, headers, config) {
        console.log("FormProgress : answer/formProgress/" + $scope.periodKey + "/" + $scope.scopeId);
        console.log(data);
        $scope.formProgress = data.listFormProgress;
        return;
      });
    }
  };
  $scope.$on("REFRESH_LAST_SAVE_TIME", function(event, args) {
    var date, minuteToAdd;
    if (args !== void 0) {
      console.log("TIME : " + args.time);
      if (args.time === null) {
        date = null;
      } else {
        date = new Date(args.time);
        minuteToAdd = new Date().getTimezoneOffset();
        date = new Date(date.getTime() - minuteToAdd * 60000);
      }
    } else {
      date = new Date();
    }
    return $scope.lastSaveTime = date;
  });
  $scope.displayMenu = function() {
    if (($scope.getMainScope() != null) && ($scope.getMainScope().displayFormMenu != null) && $scope.getMainScope().displayFormMenu === true) {
      return true;
    }
    return false;
  };
  return $scope.getClassContent = function() {
    if ($scope.$root.isLogin() === false) {
      if ($scope.getMainScope() != null) {
        if (($scope.getMainScope().displayFormMenu != null) && $scope.getMainScope().displayFormMenu === true) {
          return 'content-with-menu';
        } else {
          return 'content-without-menu';
        }
      }
    }
    return '';
  };
});
angular.module('app').run(function($rootScope, $location, $http, flash) {
  var promise;
  if (!$rootScope.currentPerson) {
    $location.path('/login');
  }
  $rootScope.isLogin = function() {
    return $location.path().substring(0, 6) === "/login";
  };
  $rootScope.logout = function() {
    var promise;
    promise = $http({
      method: "POST",
      url: 'logout',
      headers: {
        "Content-Type": "application/json"
      }
    });
    promise.success(function(data, status, headers, config) {
      $rootScope.currentPerson = null;
      $location.path('/login');
      return;
    });
    return promise.error(function(data, status, headers, config) {
      $location.path('/login');
      return;
    });
  };
  $rootScope.loginSuccess = function(data) {
    $rootScope.periods = data.availablePeriods;
    $rootScope.currentPerson = data.person;
    $rootScope.organization = data.organization;
    $rootScope.users = data.organization.users;
    return $location.path('/form1/' + data.defaultPeriod + '/' + data.organization.sites[0].scope);
  };
  $rootScope.getUserByIdentifier = function(identifier) {
    var user, _i, _len, _ref;
    _ref = $rootScope.users;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      user = _ref[_i];
      if (user.identifier === identifier) {
        return user;
      }
    }
    return null;
  };
  promise = $http({
    method: "POST",
    url: 'testAuthentication',
    headers: {
      "Content-Type": "application/text"
    }
  });
  promise.success(function(data, status, headers, config) {
    $rootScope.loginSuccess(data);
    return;
  });
  promise.error(function(data, status, headers, config) {
    return;
  });
  return $rootScope.displayModalBackground = false;
});