angular.module('app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'dangle']);
angular.module('app.filters', []);
angular.module('app.services', []);
angular.module('app.controllers', ['app.services', 'ngRoute', 'angular-flash.service', 'angular-flash.flash-alert-directive']);
angular.module('app', ['app.directives', 'app.filters', 'app.services', 'app.controllers']);
angular.module('app.controllers').config(function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: '$/angular/templates/mm-awac-login.html',
    controller: 'LoginCtrl'
  }).when('/form1/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form1.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB1';
      }
    }
  }).when('/form2/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form2.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB2';
      }
    }
  }).when('/form3/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form3.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB3';
      }
    }
  }).when('/form4/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form4.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB4';
      }
    }
  }).when('/form5/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form5.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB5';
      }
    }
  }).when('/form6/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form6.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB6';
      }
    }
  }).when('/form7/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form7.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB7';
      }
    }
  }).when('/results/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-results.html',
    controller: 'ResultsCtrl'
  }).otherwise({
    redirectTo: '/login'
  });
  return;
});angular.module('app.services').service("directiveService", function($sce) {
  this.autoScope = function(s) {
    var k, res, v;
    res = {};
    for (k in s) {
      v = s[k];
      res[k] = v;
      if (k.slice(0, 2) === 'ng' && v === '=') {
        res[k[2].toLowerCase() + k.slice(3)] = '@';
      }
    }
    return res;
  };
  this.autoScopeImpl = function(s, name) {
    var fget, key, val;
    s.$$NAME = name;
    for (key in s) {
      val = s[key];
      if (key.slice(0, 2) === 'ng') {
        fget = function(scope, k) {
          return function() {
            var v;
            v = 0;
            if (scope[k] === void 0 || scope[k] === null || scope[k] === '') {
              v = scope[k[2].toLowerCase() + k.slice(3)];
            } else {
              v = scope[k];
            }
            if (scope['decorate' + k.slice(2)]) {
              return scope['decorate' + k.slice(2)](v);
            } else {
              return v;
            }
          };
        };
        s['get' + key.slice(2)] = fget(s, key);
      }
    }
    s.isTrue = function(v) {
      return v === true || v === 'true' || v === 'y';
    };
    s.isFalse = function(v) {
      return v === false || v === 'false' || v === 'n';
    };
    s.isNull = function(v) {
      return v === null;
    };
    return s.html = function(v) {
      return $sce.trustAsHtml(v);
    };
  };
  return;
});angular.module('app.services').service("downloadService", function($http) {
  this.downloadsInProgress = 0;
  this.getDownloadsInProgress = function() {
    return this.downloadsInProgress;
  };
  this.getJson = function(url, callback) {
    var promise;
    this.downloadsInProgress++;
    promise = $http({
      method: "GET",
      url: url,
      headers: {
        "Content-Type": "application/json"
      }
    });
    promise.success(function(data, status, headers, config) {
      this.downloadsInProgress--;
      callback(data, status, headers, config);
      return;
    });
    promise.error(function(data, status, headers, config) {
      this.downloadsInProgress--;
      callback(null, status, headers, config);
      console.log("error when loading from " + url);
      return;
    });
    return promise;
  };
  return;
});angular.module('app.services').service("modalService", function($rootScope) {
  this.show = function(modalName, params) {
    var arg, target;
    arg = {};
    arg.show = true;
    arg.params = params;
    target = 'SHOW_MODAL_' + modalName;
    $rootScope.$broadcast(target, arg);
    return $rootScope.displayModalBackground = true;
  };
  this.hide = function(modalName) {
    var arg;
    arg = {};
    arg.show = false;
    $rootScope.$broadcast('SHOW_MODAL_' + modalName, arg);
    return $rootScope.displayModalBackground = false;
  };
  return;
});angular.module('app.services').service("messageFlash", function(flash) {
  this.displaySuccess = function(message) {
    return flash.success = message.replace("\n", '<br />');
  };
  this.displayError = function(message) {
    return flash.error = message.replace(/\n/g, '<br />');
  };
  return;
});angular.module('app.services').service("translationService", function($http, $rootScope, downloadService) {
  var svc;
  svc = this;
  svc.elements = null;
  svc.initialize = function(lang) {
    return downloadService.getJson("/awac/translations/" + lang, function(data) {
      svc.elements = data.lines;
      return $rootScope.$broadcast("LOAD_FINISHED", {
        type: "TRANSLATIONS",
        success: data != null
      });
    });
  };
  svc.get = function(code, count) {
    var txt, v;
    if (svc.elements == null) {
      return "";
    }
    v = svc.elements[code];
    if (!(v != null)) {
      return null;
    }
    if (count != null) {
      if ("" + count === "0") {
        txt = v.zero || v.fallback;
      } else if ("" + count === "1") {
        txt = v.one || v.fallback;
      } else {
        txt = v.more || v.fallback;
      }
      txt = txt.replace(/\{0\}/g, count);
    } else {
      txt = v.fallback || '';
    }
    return txt;
  };
  return;
});angular.module('app.filters').filter("translate", function($sce, translationService) {
  return function(input, count) {
    var text;
    text = translationService.get(input, count);
    if (text != null) {
      return $sce.trustAsHtml("<span class=\"translated-text\" data-code=\"" + input + "\">" + text + "</span>");
    } else {
      return $sce.trustAsHtml("<span class=\"translated-text translation-missing\" data-code=\"" + input + "\">[" + input + "]</span>");
    }
  };
});
angular.module('app.directives').directive('mmPatternValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmPatternValidator'.length) == 'mmPatternValidator' && k.length > 'mmPatternValidator'.length) {
                        arg = k.substring('mmPatternValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(value, args) {
    var re = new RegExp(eval(args.regexp));
    return re.test(value);
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('pattern', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('pattern', false);
                  return undefined;
                }

            });
        }
    };
});
            
angular.module('app.directives').directive('mmSizeValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmSizeValidator'.length) == 'mmSizeValidator' && k.length > 'mmSizeValidator'.length) {
                        arg = k.substring('mmSizeValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(value, args) {
    var re = value.length >= parseInt(args.min) && value.length <= parseInt(args.max);
    return re;
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('size', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('size', false);
                  return undefined;
                }

            });
        }
    };
});
            
angular.module('app.directives').directive('mmNullValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmNullValidator'.length) == 'mmNullValidator' && k.length > 'mmNullValidator'.length) {
                        arg = k.substring('mmNullValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(v) {
    return v == null
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('null', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('null', false);
                  return undefined;
                }

            });
        }
    };
});
            
angular.module('app.directives').directive('mmNotNullValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmNotNullValidator'.length) == 'mmNotNullValidator' && k.length > 'mmNotNullValidator'.length) {
                        arg = k.substring('mmNotNullValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(v) {
    return v != null
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('not-null', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('not-null', false);
                  return undefined;
                }

            });
        }
    };
});
            angular.module('app.directives').directive("mmAwacSubSubTitle", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-sub-sub-title.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacStringQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngRepetitionMap: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-string-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getAnswerValue = function() {
        return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap());
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      return scope.$watch('ngCondition', function() {
        if (scope.getCondition() === false) {
          return scope.getAnswerValue().value = null;
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacDocumentQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngObject: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-document-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getAnswerByQuestionCode = function(code) {
        var qv, _i, _len, _ref;
        if (scope.ngObject) {
          _ref = scope.ngObject.answersSaveDTO.listAnswers;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            qv = _ref[_i];
            if (qv.questionKey === code) {
              return qv;
            }
          }
        }
        return null;
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      return scope.$watch('ngCondition', function() {});
    }
  };
});angular.module('app.directives').directive("mmAwacForm1", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-form1.html",
    transclude: true,
    replace: true,
    controller: 'FormCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("ngEnter", function() {
  return function(scope, element, attrs) {
    return element.bind("keydown keypress", function(event) {
      if (event.which === 13) {
        scope.$apply(function() {
          return scope.$eval(attrs.ngEnter);
        });
        return event.preventDefault();
      }
    });
  };
});angular.module('app.directives').directive("mmAwacForm2", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-form2.html",
    transclude: true,
    replace: true,
    controller: 'FormCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacEnterpriseSurvey", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-enterprise-survey.html",
    transclude: true,
    replace: true,
    controller: 'MainCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacPercentageQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngRepetitionMap: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-percentage-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getAnswerValue = function() {
        return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap());
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      return scope.$watch('ngCondition', function() {
        if (scope.getCondition() === false) {
          return scope.getAnswerValue().value = null;
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacSubTitle", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-sub-title.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacSection", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngTitleCode: '=',
      ngMode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-section.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.decorateMode = function(v) {
        if (v) {
          return 'element_' + v;
        } else {
          return 'element_table';
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacForm7", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-form7.html",
    transclude: true,
    replace: true,
    controller: 'FormCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacForm4", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-form4.html",
    transclude: true,
    replace: true,
    controller: 'FormCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacForm3", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-form3.html",
    transclude: true,
    replace: true,
    controller: 'FormCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacModalFieldText", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngInfo: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-field-text.html",
    replace: true,
    controller: function($scope, downloadService, translationService, $sce, $modal, $http) {
      return $scope.controlField = function() {
        if ($scope.getInfo().field.length > 4 && $scope.getInfo().field.length < 20) {
          return $scope.getInfo().isValid = true;
        } else {
          return $scope.getInfo().isValid = false;
        }
      };
    },
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacModalLogin", function(directiveService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-modal-login.html",
    controller: function($scope, downloadService, translationService, $sce, $modal, $http) {
      $('#modalLogin').modal({
        backdrop: 'static'
      });
      $('#modalLogin').modal('hide');
      $('#modalLogin').on('shown.bs.modal', function(e) {
        $scope.initialize();
        return $scope.$apply();
      });
      $scope.initialize = function() {
        console.log("initialization !!");
        $scope.loginInfo = {
          fieldTitle: "Your login",
          fieldType: "text",
          placeholder: "your login",
          validationMessage: "between 5 and 20 letters",
          field: "",
          isValid: false
        };
        $scope.passwordInfo = {
          fieldTitle: "Your password",
          fieldType: "password",
          validationMessage: "between 5 and 20 letters",
          field: "",
          isValid: false
        };
        $scope.isLoading = false;
        return $scope.errorMessage = "";
      };
      $scope.initialize();
      $scope.allFieldValid = function() {
        if ($scope.loginInfo.isValid && $scope.passwordInfo.isValid) {
          return true;
        }
        return false;
      };
      return $scope.send = function() {
        var promise;
        if ($scope.allFieldValid()) {
          $scope.errorMessage = "";
          $scope.isLoading = true;
          promise = $http({
            method: "POST",
            url: 'login',
            headers: {
              "Content-Type": "application/json"
            },
            data: {
              login: $scope.loginInfo.field,
              password: $scope.passwordInfo.field
            }
          });
          promise.success(function(data, status, headers, config) {
            $scope.$parent.setCurrentUser(data);
            $('#modalLogin').modal('hide');
            $scope.$apply();
            return;
          });
          promise.error(function(data, status, headers, config) {
            $scope.errorMessage = "Error : " + data.message;
            $scope.isLoading = false;
            return;
          });
        }
        return false;
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacSelectQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngRepetitionMap: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-select-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getAnswerValue = function() {
        return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap());
      };
      scope.getOptionsByQuestionCode = function() {
        var codeList;
        codeList = scope.$parent.getCodeList(scope.getQuestionCode());
        if (codeList) {
          return codeList.codeLabels;
        }
        return null;
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      return scope.$watch('ngCondition', function() {
        if (scope.getCondition() === false) {
          return scope.getAnswerValue().value = null;
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacForm5", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-form5.html",
    transclude: true,
    replace: true,
    controller: 'FormCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacIntegerQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngRepetitionMap: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-integer-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getAnswerValue = function() {
        return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap());
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      return scope.$watch('ngCondition', function() {
        if (scope.getCondition() === false) {
          return scope.getAnswerValue().value = null;
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacBooleanQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngRepetitionMap: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-boolean-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getAnswerValue = function() {
        return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap());
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      scope.$watch('ngCondition', function() {});
      if (scope.getCondition() === false) {
        return scope.getAnswerValue().value = null;
      }
    }
  };
});angular.module('app.directives').directive("mmAwacRealQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngRepetitionMap: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-real-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getAnswerValue = function() {
        return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap());
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      scope.$watch('ngCondition', function() {});
      if (scope.ngCondition === false) {
        return scope.getAnswerValue().value = null;
      }
    }
  };
});angular.module('app.directives').directive("mmAwacRepetitionQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionSetCode: '=',
      ngIteration: '=',
      ngRepetitionMap: '=',
      ngCondition: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-repetition-question.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getQuestionSet = function() {
        return scope.$parent.getQuestionSet(scope.getQuestionSetCode());
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      scope.removeAnwser = function() {
        return scope.$parent.removeIteration(scope.getQuestionSetCode(), scope.getIteration(), scope.getRepetitionMap());
      };
      return scope.$watch('ngCondition', function() {});
    }
  };
});angular.module('app.directives').directive("mmAwacModalLoading", function(directiveService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-modal-loading.html",
    controller: function($scope, modalService) {
      var modalName;
      $('#modalLoading').modal({
        backdrop: false
      });
      $('#modalLoading').modal('show');
      modalName = 'LOADING';
      $scope.show = false;
      $scope.loc = null;
      $scope.$on('SHOW_MODAL_' + modalName, function(event, args) {
        if (args.show) {
          return $scope.display();
        } else {
          return $scope.close();
        }
      });
      $scope.display = function() {
        return $scope.show = true;
      };
      return $scope.close = function() {
        $scope.show = false;
        return modalService.hide("SHOW_MODAL_" + modalName);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacRealWithUnitQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngRepetitionMap: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-real-with-unit-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getAnswerValue = function() {
        return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap());
      };
      scope.getUnitsByQuestionCode = function() {
        var result;
        result = scope.$parent.getUnitCategories(scope.getQuestionCode());
        if (result) {
          return result.units;
        }
        return null;
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      return scope.$watch('ngCondition', function() {
        if (scope.getCondition() === false) {
          return scope.getAnswerValue().value = null;
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacForm6", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-form6.html",
    transclude: true,
    replace: true,
    controller: 'FormCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacModalConfirmationExitForm", function(directiveService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-modal-confirmation-exit-form.html",
    controller: function($scope, modalService) {
      var modalName;
      $('#modalConfirmationExitForm').modal({
        backdrop: false
      });
      $('#modalConfirmationExitForm').modal('show');
      modalName = 'CONFIRMATION_EXIT_FORM';
      $scope.show = false;
      $scope.loc = null;
      $scope.$on('SHOW_MODAL_' + modalName, function(event, args) {
        if (args.show) {
          return $scope.display(args.params);
        } else {
          return $scope.close();
        }
      });
      $scope.display = function(params) {
        $scope.show = true;
        if (params.loc !== void 0) {
          return $scope.loc = params.loc;
        }
      };
      $scope.close = function() {
        $scope.show = false;
        return modalService.hide("SHOW_MODAL_" + modalName);
      };
      $scope["continue"] = function() {
        var arg;
        arg = {};
        arg.loc = $scope.loc;
        arg.confirmed = true;
        $scope.$root.$broadcast('NAV', arg);
        return $scope.close();
      };
      return $scope.save = function() {
        $scope.$root.$broadcast('SAVE');
        return $scope["continue"]();
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmNotImplemented", function(directiveService) {
  return {
    restrict: "A",
    scope: {},
    link: function(scope, elem, attrs) {
      elem.css('opacity', '0.25');
      return elem.bind('click', function(e) {
        e.preventDefault();
        e.stopPropagation();
        return false;
      });
    }
  };
});angular.module('app.controllers').controller("ResultsCtrl", function($scope, downloadService, $http) {
  downloadService.getJson("result/getReport/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    return $scope.o = data;
  });
  $scope.temp = {};
  return $scope.temp.browsers = {
    _type: 'terms',
    missing: 0,
    total: 454,
    other: 0,
    terms: [
      {
        term: 'Prod-A',
        count: 306
      }, {
        term: 'Prod-B',
        count: 148
      }, {
        term: 'Prod-C',
        count: 25
      }
    ]
  };
});angular.module('app.controllers').controller("MainCtrl", function($scope, downloadService, translationService, $sce, $http, $location, $route, $routeParams, modalService) {
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
  $scope.getMenuCurrentClass = function(loc) {
    if ($location.path().substring(0, loc.length) === loc) {
      return "menu_current";
    } else {
      return "";
    }
  };
  $scope.$on('NAV', function(event, args) {
    return $scope.nav(args.loc, args.confirmed);
  });
  $scope.nav = function(loc, confirmed) {
    var params;
    if (confirmed == null) {
      confirmed = false;
    }
    if (loc.indexOf("/form") > -1 && !confirmed) {
      params = {};
      params.loc = loc;
      return modalService.show("CONFIRMATION_EXIT_FORM", params);
    } else {
      return $location.path(loc + "/" + $scope.period + "/" + $scope.scopeId);
    }
  };
  $scope.period = 0;
  $scope.$watch('period', function() {
    var k, p, v;
    $routeParams.period = $scope.period;
    if ($route.current) {
      p = $route.current.$$route.originalPath;
      for (k in $routeParams) {
        v = $routeParams[k];
        p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v);
      }
      return $location.path(p);
    }
  });
  $scope.$watch('scopeId', function() {
    var k, p, v;
    $routeParams.period = $scope.period;
    if ($route.current) {
      p = $route.current.$$route.originalPath;
      for (k in $routeParams) {
        v = $routeParams[k];
        p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v);
      }
      return $location.path(p);
    }
  });
  $scope.save = function() {
    return $scope.$broadcast('SAVE');
  };
  return $scope.$on("$routeChangeSuccess", function(event, current, previous) {
    $scope.period = parseInt($routeParams.period);
    return $scope.scopeId = parseInt($routeParams.scope);
  });
});
angular.module('app').run(function($rootScope, $location, $http, flash) {
  var promise;
  console.log("run  !!");
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
    return $location.path('/form1/' + data.defaultPeriod + '/' + data.organization.sites[0].scope);
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
});angular.module('app.controllers').controller("Form2Ctrl", function($scope, downloadService, $http, messageFlash) {
  $scope.formIdentifier = "TAB2";
  $scope.answerList = [];
  $scope.mapRepetition = [];
  /*
  illustration of the structures
  $scope.mapRepetition['A15'] = [{'A15':1},
                                  {'A15':2}]

  $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                 {'A16':2,'A15':1}]
  */
  $scope.loading = true;
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    console.log("data");
    console.log(data);
    $scope.o = data;
    $scope.storeAnswers = function() {
      var answerSave, qSet, _i, _len, _ref;
      answerSave = $scope.o.answersSave;
      $scope.answerList = answerSave.listAnswers;
      _ref = $scope.o.questionSets;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qSet = _ref[_i];
        $scope.loopRepetition(qSet);
      }
      console.log("$scope.mapRepetition");
      return console.log($scope.mapRepetition);
    };
    $scope.loopRepetition = function(questionSetDTO) {
      var answer, child, founded, listAnswer, q, repetition, _i, _j, _k, _l, _len, _len2, _len3, _len4, _ref, _ref2, _ref3, _results;
      if (questionSetDTO.repetitionAllowed === true) {
        if (questionSetDTO.questions) {
          _ref = questionSetDTO.questions;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            q = _ref[_i];
            listAnswer = $scope.getListAnswer(q.code);
            for (_j = 0, _len2 = listAnswer.length; _j < _len2; _j++) {
              answer = listAnswer[_j];
              if (answer.mapRepetition === null) {
                console.log("mapRepetition expected but not found");
              } else {
                /*
                repetitionNumber = answer.mapRepetition[questionSetDTO.code]
                code= questionSetDTO.code
                repetitionToAdd = {}#code:repetition}
                repetitionToAdd[questionSetDTO.code] =repetitionNumber
                */
                if ($scope.mapRepetition[questionSetDTO.code]) {
                  founded = false;
                  _ref2 = $scope.mapRepetition[questionSetDTO.code];
                  for (_k = 0, _len3 = _ref2.length; _k < _len3; _k++) {
                    repetition = _ref2[_k];
                    if ($scope.compareRepetitionMap(repetition, answer.mapRepetition)) {
                      founded = true;
                    }
                  }
                  if (founded === false) {
                    $scope.mapRepetition[questionSetDTO.code][$scope.mapRepetition[questionSetDTO.code].length] = angular.copy(answer.mapRepetition);
                  }
                } else {
                  $scope.mapRepetition[questionSetDTO.code] = [];
                  $scope.mapRepetition[questionSetDTO.code][0] = angular.copy(answer.mapRepetition);
                }
              }
            }
          }
        }
      }
      if (questionSetDTO.children) {
        _ref3 = questionSetDTO.children;
        _results = [];
        for (_l = 0, _len4 = _ref3.length; _l < _len4; _l++) {
          child = _ref3[_l];
          _results.push($scope.loopRepetition(child));
        }
        return _results;
      }
    };
    $scope.storeAnswers();
    return $scope.loading = false;
  });
  $scope.$on('SAVE', function() {
    var answer, listAnswerToSave, promise, _i, _len, _ref;
    listAnswerToSave = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.value) {
        listAnswerToSave[listAnswerToSave.length] = answer;
      }
    }
    console.log("listAnswerToSave");
    console.log(listAnswerToSave);
    $scope.o.answersSave.listAnswers = listAnswerToSave;
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSave
    });
    promise.success(function(data, status, headers, config) {
      messageFlash.displaySuccess("Your answers are saved !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      messageFlash.displayError("An error was thrown during the save : " + data.message);
      return;
    });
  });
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    if (question === null || question === void 0) {
      console.log("ERROR : this question was not found : " + code);
      return null;
    }
    if (question.unitCategoryId === null || question.unitCategoryId === void 0) {
      console.log("ERROR : there is no unitCategoryId for this question : " + code);
      return null;
    }
    return $scope.o.unitCategories[question.unitCategoryId];
  };
  $scope.getCodeList = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    return $scope.o.codeLists[question.codeListName];
  };
  $scope.getRepetitionMapByQuestionSet = function(code, mapRepetition) {
    var listRepetition, repetition, _i, _len, _ref;
    listRepetition = [];
    if ($scope.mapRepetition[code] !== null && $scope.mapRepetition[code] !== void 0) {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if (mapRepetition === null || mapRepetition === void 0 || $scope.compareRepetitionMap(repetition, mapRepetition)) {
          listRepetition[listRepetition.length] = repetition;
        }
      }
    }
    return listRepetition;
  };
  $scope.getQuestion = function(code, listQuestionSets) {
    var q, qSet, result, _i, _j, _len, _len2, _ref;
    if (listQuestionSets == null) {
      listQuestionSets = $scope.o.questionSets;
    }
    if (listQuestionSets) {
      for (_i = 0, _len = listQuestionSets.length; _i < _len; _i++) {
        qSet = listQuestionSets[_i];
        if (qSet.questions) {
          _ref = qSet.questions;
          for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
            q = _ref[_j];
            if (q.code === code) {
              return q;
            }
          }
        }
        if (qSet.children) {
          result = $scope.getQuestion(code, qSet.children);
          if (result) {
            return result;
          }
        }
      }
    }
    return null;
  };
  $scope.getAnswerOrCreate = function(code, mapIteration) {
    var answerLine, result;
    result = $scope.getAnswer(code, mapIteration);
    if (result) {
      return result;
    } else {
      answerLine = {
        'questionKey': code,
        'value': null,
        'unitId': null,
        'mapRepetition': mapIteration
      };
      $scope.answerList[$scope.answerList.length] = answerLine;
      return answerLine;
    }
  };
  $scope.getAnswer = function(code, mapIteration) {
    var answer, _i, _len, _ref;
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code) {
        if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
          return answer;
        }
      }
    }
    return null;
  };
  $scope.getListAnswer = function(code, mapIteration) {
    var answer, listAnswer, _i, _len, _ref;
    listAnswer = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code && $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
        listAnswer[listAnswer.length] = answer;
      }
    }
    return listAnswer;
  };
  $scope.addIteration = function(code, mapRepetition) {
    var max, repetition, repetitionToAdd, _i, _len, _ref;
    max = 0;
    repetitionToAdd = {};
    if (mapRepetition !== null && mapRepetition !== void 0) {
      repetitionToAdd = angular.copy(mapRepetition);
    }
    if ($scope.mapRepetition[code] === null || $scope.mapRepetition[code] === void 0) {
      repetitionToAdd[code] = max + 1;
      $scope.mapRepetition[code] = [];
      return $scope.mapRepetition[code][0] = repetitionToAdd;
    } else {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if ($scope.compareRepetitionMap(repetition, mapRepetition) && repetition[code] > max) {
          max = repetition[code];
        }
      }
      repetitionToAdd[code] = max + 1;
      return $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd;
    }
  };
  $scope.removeIteration = function(questionSetCode, iterationToDelete, mapRepetition) {
    var iteration, key, len, question, _i, _len, _ref, _results;
    len = $scope.answerList.length;
    while (len--) {
      question = $scope.answerList[len];
      if (question.mapRepetition !== null && question.mapRepetition !== void 0 && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)) {
        if (question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] === iterationToDelete[questionSetCode]) {
          $scope.answerList.splice(len, 1);
        }
      }
    }
    _ref = Object.keys($scope.mapRepetition);
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      _results.push((function() {
        var _results;
        if (key !== '$$hashKey') {
          len = $scope.mapRepetition[key].length;
          _results = [];
          while (len--) {
            iteration = $scope.mapRepetition[key][len];
            _results.push($scope.compareRepetitionMap(iteration, mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] === iterationToDelete[questionSetCode] ? $scope.mapRepetition[key].splice(len, 1) : void 0);
          }
          return _results;
        }
      })());
    }
    return _results;
  };
  return $scope.compareRepetitionMap = function(mapContainer, mapContained) {
    var key, value, _i, _len, _ref;
    if (mapContained === null || mapContained === void 0 || mapContained.length === 0) {
      return true;
    }
    if (mapContainer === null || mapContainer === void 0 || mapContainer.length === 0) {
      return false;
    }
    _ref = Object.keys(mapContained);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        value = mapContained[key];
        if (mapContainer[key] === null || mapContainer[key] === void 0 || mapContainer[key] !== value) {
          return false;
        }
      }
    }
    return true;
  };
});angular.module('app.controllers').controller("Form4Ctrl", function($scope, downloadService, $http, messageFlash) {
  $scope.formIdentifier = "TAB4";
  $scope.answerList = [];
  $scope.mapRepetition = [];
  /*
  illustration of the structures
  $scope.mapRepetition['A15'] = [{'A15':1},
                                  {'A15':2}]

  $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                 {'A16':2,'A15':1}]
  */
  $scope.loading = true;
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    console.log("data");
    console.log(data);
    $scope.o = data;
    $scope.storeAnswers = function() {
      var answerSave, qSet, _i, _len, _ref;
      answerSave = $scope.o.answersSave;
      $scope.answerList = answerSave.listAnswers;
      _ref = $scope.o.questionSets;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qSet = _ref[_i];
        $scope.loopRepetition(qSet);
      }
      console.log("$scope.mapRepetition");
      return console.log($scope.mapRepetition);
    };
    $scope.loopRepetition = function(questionSetDTO) {
      var answer, child, founded, listAnswer, q, repetition, _i, _j, _k, _l, _len, _len2, _len3, _len4, _ref, _ref2, _ref3, _results;
      if (questionSetDTO.repetitionAllowed === true) {
        if (questionSetDTO.questions) {
          _ref = questionSetDTO.questions;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            q = _ref[_i];
            listAnswer = $scope.getListAnswer(q.code);
            for (_j = 0, _len2 = listAnswer.length; _j < _len2; _j++) {
              answer = listAnswer[_j];
              if (answer.mapRepetition === null) {
                console.log("mapRepetition expected but not found");
              } else {
                /*
                repetitionNumber = answer.mapRepetition[questionSetDTO.code]
                code= questionSetDTO.code
                repetitionToAdd = {}#code:repetition}
                repetitionToAdd[questionSetDTO.code] =repetitionNumber
                */
                if ($scope.mapRepetition[questionSetDTO.code]) {
                  founded = false;
                  _ref2 = $scope.mapRepetition[questionSetDTO.code];
                  for (_k = 0, _len3 = _ref2.length; _k < _len3; _k++) {
                    repetition = _ref2[_k];
                    if ($scope.compareRepetitionMap(repetition, answer.mapRepetition)) {
                      founded = true;
                    }
                  }
                  if (founded === false) {
                    $scope.mapRepetition[questionSetDTO.code][$scope.mapRepetition[questionSetDTO.code].length] = angular.copy(answer.mapRepetition);
                  }
                } else {
                  $scope.mapRepetition[questionSetDTO.code] = [];
                  $scope.mapRepetition[questionSetDTO.code][0] = angular.copy(answer.mapRepetition);
                }
              }
            }
          }
        }
      }
      if (questionSetDTO.children) {
        _ref3 = questionSetDTO.children;
        _results = [];
        for (_l = 0, _len4 = _ref3.length; _l < _len4; _l++) {
          child = _ref3[_l];
          _results.push($scope.loopRepetition(child));
        }
        return _results;
      }
    };
    $scope.storeAnswers();
    return $scope.loading = false;
  });
  $scope.$on('SAVE', function() {
    var answer, listAnswerToSave, promise, _i, _len, _ref;
    listAnswerToSave = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.value) {
        listAnswerToSave[listAnswerToSave.length] = answer;
      }
    }
    console.log("listAnswerToSave");
    console.log(listAnswerToSave);
    $scope.o.answersSave.listAnswers = listAnswerToSave;
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSave
    });
    promise.success(function(data, status, headers, config) {
      messageFlash.displaySuccess("Your answers are saved !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      messageFlash.displayError("An error was thrown during the save : " + data.message);
      return;
    });
  });
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    if (question === null || question === void 0) {
      console.log("ERROR : this question was not found : " + code);
      return null;
    }
    if (question.unitCategoryId === null || question.unitCategoryId === void 0) {
      console.log("ERROR : there is no unitCategoryId for this question : " + code);
      return null;
    }
    return $scope.o.unitCategories[question.unitCategoryId];
  };
  $scope.getCodeList = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    return $scope.o.codeLists[question.codeListName];
  };
  $scope.getRepetitionMapByQuestionSet = function(code, mapRepetition) {
    var listRepetition, repetition, _i, _len, _ref;
    listRepetition = [];
    if ($scope.mapRepetition[code] !== null && $scope.mapRepetition[code] !== void 0) {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if (mapRepetition === null || mapRepetition === void 0 || $scope.compareRepetitionMap(repetition, mapRepetition)) {
          listRepetition[listRepetition.length] = repetition;
        }
      }
    }
    return listRepetition;
  };
  $scope.getQuestion = function(code, listQuestionSets) {
    var q, qSet, result, _i, _j, _len, _len2, _ref;
    if (listQuestionSets == null) {
      listQuestionSets = $scope.o.questionSets;
    }
    if (listQuestionSets) {
      for (_i = 0, _len = listQuestionSets.length; _i < _len; _i++) {
        qSet = listQuestionSets[_i];
        if (qSet.questions) {
          _ref = qSet.questions;
          for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
            q = _ref[_j];
            if (q.code === code) {
              return q;
            }
          }
        }
        if (qSet.children) {
          result = $scope.getQuestion(code, qSet.children);
          if (result) {
            return result;
          }
        }
      }
    }
    return null;
  };
  $scope.getAnswerOrCreate = function(code, mapIteration) {
    var answerLine, result;
    result = $scope.getAnswer(code, mapIteration);
    if (result) {
      return result;
    } else {
      answerLine = {
        'questionKey': code,
        'value': null,
        'unitId': null,
        'mapRepetition': mapIteration
      };
      $scope.answerList[$scope.answerList.length] = answerLine;
      return answerLine;
    }
  };
  $scope.getAnswer = function(code, mapIteration) {
    var answer, _i, _len, _ref;
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code) {
        if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
          return answer;
        }
      }
    }
    return null;
  };
  $scope.getListAnswer = function(code, mapIteration) {
    var answer, listAnswer, _i, _len, _ref;
    listAnswer = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code && $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
        listAnswer[listAnswer.length] = answer;
      }
    }
    return listAnswer;
  };
  $scope.addIteration = function(code, mapRepetition) {
    var max, repetition, repetitionToAdd, _i, _len, _ref;
    max = 0;
    repetitionToAdd = {};
    if (mapRepetition !== null && mapRepetition !== void 0) {
      repetitionToAdd = angular.copy(mapRepetition);
    }
    if ($scope.mapRepetition[code] === null || $scope.mapRepetition[code] === void 0) {
      repetitionToAdd[code] = max + 1;
      $scope.mapRepetition[code] = [];
      return $scope.mapRepetition[code][0] = repetitionToAdd;
    } else {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if ($scope.compareRepetitionMap(repetition, mapRepetition) && repetition[code] > max) {
          max = repetition[code];
        }
      }
      repetitionToAdd[code] = max + 1;
      return $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd;
    }
  };
  $scope.removeIteration = function(questionSetCode, iterationToDelete, mapRepetition) {
    var iteration, key, len, question, _i, _len, _ref, _results;
    len = $scope.answerList.length;
    while (len--) {
      question = $scope.answerList[len];
      if (question.mapRepetition !== null && question.mapRepetition !== void 0 && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)) {
        if (question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] === iterationToDelete[questionSetCode]) {
          $scope.answerList.splice(len, 1);
        }
      }
    }
    _ref = Object.keys($scope.mapRepetition);
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      _results.push((function() {
        var _results;
        if (key !== '$$hashKey') {
          len = $scope.mapRepetition[key].length;
          _results = [];
          while (len--) {
            iteration = $scope.mapRepetition[key][len];
            _results.push($scope.compareRepetitionMap(iteration, mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] === iterationToDelete[questionSetCode] ? $scope.mapRepetition[key].splice(len, 1) : void 0);
          }
          return _results;
        }
      })());
    }
    return _results;
  };
  return $scope.compareRepetitionMap = function(mapContainer, mapContained) {
    var key, value, _i, _len, _ref;
    if (mapContained === null || mapContained === void 0 || mapContained.length === 0) {
      return true;
    }
    if (mapContainer === null || mapContainer === void 0 || mapContainer.length === 0) {
      return false;
    }
    _ref = Object.keys(mapContained);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        value = mapContained[key];
        if (mapContainer[key] === null || mapContainer[key] === void 0 || mapContainer[key] !== value) {
          return false;
        }
      }
    }
    return true;
  };
});angular.module('app.controllers').controller("LoginCtrl", function($scope, downloadService, $http, $location, messageFlash) {
  console.log("je suis le logincontroller");
  $scope.loginInfo = {
    fieldTitle: "Your login",
    fieldType: "text",
    placeholder: "your login",
    validationMessage: "between 5 and 20 letters",
    field: "",
    isValid: false
  };
  $scope.passwordInfo = {
    fieldTitle: "Your password",
    fieldType: "password",
    validationMessage: "between 5 and 20 letters",
    field: "",
    isValid: false
  };
  $scope.send = function() {
    var promise;
    $scope.isLoading = true;
    promise = $http({
      method: "POST",
      url: 'login',
      headers: {
        "Content-Type": "application/json"
      },
      data: {
        login: $scope.loginInfo.field,
        password: $scope.passwordInfo.field
      }
    });
    promise.success(function(data, status, headers, config) {
      $scope.$root.loginSuccess(data);
      messageFlash.displaySuccess("You are now connected");
      return;
    });
    promise.error(function(data, status, headers, config) {
      messageFlash.displayError(data.message);
      $scope.isLoading = false;
      return;
    });
    return false;
  };
  return $scope.test = function() {
    return $('#modalLogin').modal('show');
  };
});angular.module('app.controllers').controller("Form1Ctrl", function($scope, downloadService, $http, messageFlash) {
  $scope.formIdentifier = "TAB1";
  $scope.answerList = [];
  $scope.mapRepetition = [];
  /*
  illustration of the structures
  $scope.mapRepetition['A15'] = [{'A15':1},
                                  {'A15':2}]

  $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                 {'A16':2,'A15':1}]
  */
  $scope.loading = true;
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    console.log("data");
    console.log(data);
    $scope.o = data;
    $scope.storeAnswers = function() {
      var answerSave, qSet, _i, _len, _ref;
      answerSave = $scope.o.answersSave;
      $scope.answerList = answerSave.listAnswers;
      _ref = $scope.o.questionSets;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qSet = _ref[_i];
        $scope.loopRepetition(qSet);
      }
      console.log("$scope.mapRepetition");
      return console.log($scope.mapRepetition);
    };
    $scope.loopRepetition = function(questionSetDTO) {
      var answer, child, founded, listAnswer, q, repetition, _i, _j, _k, _l, _len, _len2, _len3, _len4, _ref, _ref2, _ref3, _results;
      if (questionSetDTO.repetitionAllowed === true) {
        if (questionSetDTO.questions) {
          _ref = questionSetDTO.questions;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            q = _ref[_i];
            listAnswer = $scope.getListAnswer(q.code);
            for (_j = 0, _len2 = listAnswer.length; _j < _len2; _j++) {
              answer = listAnswer[_j];
              if (answer.mapRepetition === null) {
                console.log("mapRepetition expected but not found");
              } else {
                /*
                repetitionNumber = answer.mapRepetition[questionSetDTO.code]
                code= questionSetDTO.code
                repetitionToAdd = {}#code:repetition}
                repetitionToAdd[questionSetDTO.code] =repetitionNumber
                */
                if ($scope.mapRepetition[questionSetDTO.code]) {
                  founded = false;
                  _ref2 = $scope.mapRepetition[questionSetDTO.code];
                  for (_k = 0, _len3 = _ref2.length; _k < _len3; _k++) {
                    repetition = _ref2[_k];
                    if ($scope.compareRepetitionMap(repetition, answer.mapRepetition)) {
                      founded = true;
                    }
                  }
                  if (founded === false) {
                    $scope.mapRepetition[questionSetDTO.code][$scope.mapRepetition[questionSetDTO.code].length] = angular.copy(answer.mapRepetition);
                  }
                } else {
                  $scope.mapRepetition[questionSetDTO.code] = [];
                  $scope.mapRepetition[questionSetDTO.code][0] = angular.copy(answer.mapRepetition);
                }
              }
            }
          }
        }
      }
      if (questionSetDTO.children) {
        _ref3 = questionSetDTO.children;
        _results = [];
        for (_l = 0, _len4 = _ref3.length; _l < _len4; _l++) {
          child = _ref3[_l];
          _results.push($scope.loopRepetition(child));
        }
        return _results;
      }
    };
    $scope.storeAnswers();
    return $scope.loading = false;
  });
  $scope.$on('SAVE', function() {
    var answer, listAnswerToSave, promise, _i, _len, _ref;
    listAnswerToSave = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.value) {
        listAnswerToSave[listAnswerToSave.length] = answer;
      }
    }
    console.log("listAnswerToSave");
    console.log(listAnswerToSave);
    $scope.o.answersSave.listAnswers = listAnswerToSave;
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSave
    });
    promise.success(function(data, status, headers, config) {
      messageFlash.displaySuccess("Your answers are saved !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      messageFlash.displayError("An error was thrown during the save : " + data.message);
      return;
    });
  });
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    if (question === null || question === void 0) {
      console.log("ERROR : this question was not found : " + code);
      return null;
    }
    if (question.unitCategoryId === null || question.unitCategoryId === void 0) {
      console.log("ERROR : there is no unitCategoryId for this question : " + code);
      return null;
    }
    return $scope.o.unitCategories[question.unitCategoryId];
  };
  $scope.getCodeList = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    return $scope.o.codeLists[question.codeListName];
  };
  $scope.getRepetitionMapByQuestionSet = function(code, mapRepetition) {
    var listRepetition, repetition, _i, _len, _ref;
    listRepetition = [];
    if ($scope.mapRepetition[code] !== null && $scope.mapRepetition[code] !== void 0) {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if (mapRepetition === null || mapRepetition === void 0 || $scope.compareRepetitionMap(repetition, mapRepetition)) {
          listRepetition[listRepetition.length] = repetition;
        }
      }
    }
    return listRepetition;
  };
  $scope.getQuestion = function(code, listQuestionSets) {
    var q, qSet, result, _i, _j, _len, _len2, _ref;
    if (listQuestionSets == null) {
      listQuestionSets = $scope.o.questionSets;
    }
    if (listQuestionSets) {
      for (_i = 0, _len = listQuestionSets.length; _i < _len; _i++) {
        qSet = listQuestionSets[_i];
        if (qSet.questions) {
          _ref = qSet.questions;
          for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
            q = _ref[_j];
            if (q.code === code) {
              return q;
            }
          }
        }
        if (qSet.children) {
          result = $scope.getQuestion(code, qSet.children);
          if (result) {
            return result;
          }
        }
      }
    }
    return null;
  };
  $scope.getAnswerOrCreate = function(code, mapIteration) {
    var answerLine, result;
    result = $scope.getAnswer(code, mapIteration);
    if (result) {
      return result;
    } else {
      answerLine = {
        'questionKey': code,
        'value': null,
        'unitId': null,
        'mapRepetition': mapIteration
      };
      $scope.answerList[$scope.answerList.length] = answerLine;
      return answerLine;
    }
  };
  $scope.getAnswer = function(code, mapIteration) {
    var answer, _i, _len, _ref;
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code) {
        if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
          return answer;
        }
      }
    }
    return null;
  };
  $scope.getListAnswer = function(code, mapIteration) {
    var answer, listAnswer, _i, _len, _ref;
    listAnswer = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code && $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
        listAnswer[listAnswer.length] = answer;
      }
    }
    return listAnswer;
  };
  $scope.addIteration = function(code, mapRepetition) {
    var max, repetition, repetitionToAdd, _i, _len, _ref;
    max = 0;
    repetitionToAdd = {};
    if (mapRepetition !== null && mapRepetition !== void 0) {
      repetitionToAdd = angular.copy(mapRepetition);
    }
    if ($scope.mapRepetition[code] === null || $scope.mapRepetition[code] === void 0) {
      repetitionToAdd[code] = max + 1;
      $scope.mapRepetition[code] = [];
      return $scope.mapRepetition[code][0] = repetitionToAdd;
    } else {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if ($scope.compareRepetitionMap(repetition, mapRepetition) && repetition[code] > max) {
          max = repetition[code];
        }
      }
      repetitionToAdd[code] = max + 1;
      return $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd;
    }
  };
  $scope.removeIteration = function(questionSetCode, iterationToDelete, mapRepetition) {
    var iteration, key, len, question, _i, _len, _ref, _results;
    len = $scope.answerList.length;
    while (len--) {
      question = $scope.answerList[len];
      if (question.mapRepetition !== null && question.mapRepetition !== void 0 && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)) {
        if (question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] === iterationToDelete[questionSetCode]) {
          $scope.answerList.splice(len, 1);
        }
      }
    }
    _ref = Object.keys($scope.mapRepetition);
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      _results.push((function() {
        var _results;
        if (key !== '$$hashKey') {
          len = $scope.mapRepetition[key].length;
          _results = [];
          while (len--) {
            iteration = $scope.mapRepetition[key][len];
            _results.push($scope.compareRepetitionMap(iteration, mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] === iterationToDelete[questionSetCode] ? $scope.mapRepetition[key].splice(len, 1) : void 0);
          }
          return _results;
        }
      })());
    }
    return _results;
  };
  return $scope.compareRepetitionMap = function(mapContainer, mapContained) {
    var key, value, _i, _len, _ref;
    if (mapContained === null || mapContained === void 0 || mapContained.length === 0) {
      return true;
    }
    if (mapContainer === null || mapContainer === void 0 || mapContainer.length === 0) {
      return false;
    }
    _ref = Object.keys(mapContained);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        value = mapContained[key];
        if (mapContainer[key] === null || mapContainer[key] === void 0 || mapContainer[key] !== value) {
          return false;
        }
      }
    }
    return true;
  };
});angular.module('app.controllers').controller("Form7Ctrl", function($scope, downloadService, $http, messageFlash) {
  $scope.formIdentifier = "TAB7";
  $scope.answerList = [];
  $scope.mapRepetition = [];
  /*
  illustration of the structures
  $scope.mapRepetition['A15'] = [{'A15':1},
                                  {'A15':2}]

  $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                 {'A16':2,'A15':1}]
  */
  $scope.loading = true;
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    console.log("data");
    console.log(data);
    $scope.o = data;
    $scope.storeAnswers = function() {
      var answerSave, qSet, _i, _len, _ref;
      answerSave = $scope.o.answersSave;
      $scope.answerList = answerSave.listAnswers;
      _ref = $scope.o.questionSets;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qSet = _ref[_i];
        $scope.loopRepetition(qSet);
      }
      console.log("$scope.mapRepetition");
      return console.log($scope.mapRepetition);
    };
    $scope.loopRepetition = function(questionSetDTO) {
      var answer, child, founded, listAnswer, q, repetition, _i, _j, _k, _l, _len, _len2, _len3, _len4, _ref, _ref2, _ref3, _results;
      if (questionSetDTO.repetitionAllowed === true) {
        if (questionSetDTO.questions) {
          _ref = questionSetDTO.questions;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            q = _ref[_i];
            listAnswer = $scope.getListAnswer(q.code);
            for (_j = 0, _len2 = listAnswer.length; _j < _len2; _j++) {
              answer = listAnswer[_j];
              if (answer.mapRepetition === null) {
                console.log("mapRepetition expected but not found");
              } else {
                /*
                repetitionNumber = answer.mapRepetition[questionSetDTO.code]
                code= questionSetDTO.code
                repetitionToAdd = {}#code:repetition}
                repetitionToAdd[questionSetDTO.code] =repetitionNumber
                */
                if ($scope.mapRepetition[questionSetDTO.code]) {
                  founded = false;
                  _ref2 = $scope.mapRepetition[questionSetDTO.code];
                  for (_k = 0, _len3 = _ref2.length; _k < _len3; _k++) {
                    repetition = _ref2[_k];
                    if ($scope.compareRepetitionMap(repetition, answer.mapRepetition)) {
                      founded = true;
                    }
                  }
                  if (founded === false) {
                    $scope.mapRepetition[questionSetDTO.code][$scope.mapRepetition[questionSetDTO.code].length] = angular.copy(answer.mapRepetition);
                  }
                } else {
                  $scope.mapRepetition[questionSetDTO.code] = [];
                  $scope.mapRepetition[questionSetDTO.code][0] = angular.copy(answer.mapRepetition);
                }
              }
            }
          }
        }
      }
      if (questionSetDTO.children) {
        _ref3 = questionSetDTO.children;
        _results = [];
        for (_l = 0, _len4 = _ref3.length; _l < _len4; _l++) {
          child = _ref3[_l];
          _results.push($scope.loopRepetition(child));
        }
        return _results;
      }
    };
    $scope.storeAnswers();
    return $scope.loading = false;
  });
  $scope.$on('SAVE', function() {
    var answer, listAnswerToSave, promise, _i, _len, _ref;
    listAnswerToSave = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.value) {
        listAnswerToSave[listAnswerToSave.length] = answer;
      }
    }
    console.log("listAnswerToSave");
    console.log(listAnswerToSave);
    $scope.o.answersSave.listAnswers = listAnswerToSave;
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSave
    });
    promise.success(function(data, status, headers, config) {
      messageFlash.displaySuccess("Your answers are saved !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      messageFlash.displayError("An error was thrown during the save : " + data.message);
      return;
    });
  });
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    if (question === null || question === void 0) {
      console.log("ERROR : this question was not found : " + code);
      return null;
    }
    if (question.unitCategoryId === null || question.unitCategoryId === void 0) {
      console.log("ERROR : there is no unitCategoryId for this question : " + code);
      return null;
    }
    return $scope.o.unitCategories[question.unitCategoryId];
  };
  $scope.getCodeList = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    return $scope.o.codeLists[question.codeListName];
  };
  $scope.getRepetitionMapByQuestionSet = function(code, mapRepetition) {
    var listRepetition, repetition, _i, _len, _ref;
    listRepetition = [];
    if ($scope.mapRepetition[code] !== null && $scope.mapRepetition[code] !== void 0) {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if (mapRepetition === null || mapRepetition === void 0 || $scope.compareRepetitionMap(repetition, mapRepetition)) {
          listRepetition[listRepetition.length] = repetition;
        }
      }
    }
    return listRepetition;
  };
  $scope.getQuestion = function(code, listQuestionSets) {
    var q, qSet, result, _i, _j, _len, _len2, _ref;
    if (listQuestionSets == null) {
      listQuestionSets = $scope.o.questionSets;
    }
    if (listQuestionSets) {
      for (_i = 0, _len = listQuestionSets.length; _i < _len; _i++) {
        qSet = listQuestionSets[_i];
        if (qSet.questions) {
          _ref = qSet.questions;
          for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
            q = _ref[_j];
            if (q.code === code) {
              return q;
            }
          }
        }
        if (qSet.children) {
          result = $scope.getQuestion(code, qSet.children);
          if (result) {
            return result;
          }
        }
      }
    }
    return null;
  };
  $scope.getAnswerOrCreate = function(code, mapIteration) {
    var answerLine, result;
    result = $scope.getAnswer(code, mapIteration);
    if (result) {
      return result;
    } else {
      answerLine = {
        'questionKey': code,
        'value': null,
        'unitId': null,
        'mapRepetition': mapIteration
      };
      $scope.answerList[$scope.answerList.length] = answerLine;
      return answerLine;
    }
  };
  $scope.getAnswer = function(code, mapIteration) {
    var answer, _i, _len, _ref;
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code) {
        if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
          return answer;
        }
      }
    }
    return null;
  };
  $scope.getListAnswer = function(code, mapIteration) {
    var answer, listAnswer, _i, _len, _ref;
    listAnswer = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code && $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
        listAnswer[listAnswer.length] = answer;
      }
    }
    return listAnswer;
  };
  $scope.addIteration = function(code, mapRepetition) {
    var max, repetition, repetitionToAdd, _i, _len, _ref;
    max = 0;
    repetitionToAdd = {};
    if (mapRepetition !== null && mapRepetition !== void 0) {
      repetitionToAdd = angular.copy(mapRepetition);
    }
    if ($scope.mapRepetition[code] === null || $scope.mapRepetition[code] === void 0) {
      repetitionToAdd[code] = max + 1;
      $scope.mapRepetition[code] = [];
      return $scope.mapRepetition[code][0] = repetitionToAdd;
    } else {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if ($scope.compareRepetitionMap(repetition, mapRepetition) && repetition[code] > max) {
          max = repetition[code];
        }
      }
      repetitionToAdd[code] = max + 1;
      return $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd;
    }
  };
  $scope.removeIteration = function(questionSetCode, iterationToDelete, mapRepetition) {
    var iteration, key, len, question, _i, _len, _ref, _results;
    len = $scope.answerList.length;
    while (len--) {
      question = $scope.answerList[len];
      if (question.mapRepetition !== null && question.mapRepetition !== void 0 && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)) {
        if (question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] === iterationToDelete[questionSetCode]) {
          $scope.answerList.splice(len, 1);
        }
      }
    }
    _ref = Object.keys($scope.mapRepetition);
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      _results.push((function() {
        var _results;
        if (key !== '$$hashKey') {
          len = $scope.mapRepetition[key].length;
          _results = [];
          while (len--) {
            iteration = $scope.mapRepetition[key][len];
            _results.push($scope.compareRepetitionMap(iteration, mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] === iterationToDelete[questionSetCode] ? $scope.mapRepetition[key].splice(len, 1) : void 0);
          }
          return _results;
        }
      })());
    }
    return _results;
  };
  return $scope.compareRepetitionMap = function(mapContainer, mapContained) {
    var key, value, _i, _len, _ref;
    if (mapContained === null || mapContained === void 0 || mapContained.length === 0) {
      return true;
    }
    if (mapContainer === null || mapContainer === void 0 || mapContainer.length === 0) {
      return false;
    }
    _ref = Object.keys(mapContained);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        value = mapContained[key];
        if (mapContainer[key] === null || mapContainer[key] === void 0 || mapContainer[key] !== value) {
          return false;
        }
      }
    }
    return true;
  };
});angular.module('app.controllers').controller("Form3Ctrl", function($scope, downloadService, $http, messageFlash, modalService) {
  $scope.formIdentifier = "TAB3";
  $scope.answerList = [];
  $scope.mapRepetition = [];
  /*
  illustration of the structures
  $scope.mapRepetition['A15'] = [{'A15':1},
                                  {'A15':2}]

  $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                 {'A16':2,'A15':1}]
  */
  $scope.loading = true;
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    console.log("data");
    console.log(data);
    $scope.o = data;
    $scope.storeAnswers = function() {
      var answerSave, qSet, _i, _len, _ref;
      answerSave = $scope.o.answersSave;
      $scope.answerList = answerSave.listAnswers;
      _ref = $scope.o.questionSets;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qSet = _ref[_i];
        $scope.loopRepetition(qSet);
      }
      console.log("$scope.mapRepetition");
      return console.log($scope.mapRepetition);
    };
    $scope.loopRepetition = function(questionSetDTO) {
      var answer, child, founded, listAnswer, q, repetition, _i, _j, _k, _l, _len, _len2, _len3, _len4, _ref, _ref2, _ref3, _results;
      if (questionSetDTO.repetitionAllowed === true) {
        if (questionSetDTO.questions) {
          _ref = questionSetDTO.questions;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            q = _ref[_i];
            listAnswer = $scope.getListAnswer(q.code);
            for (_j = 0, _len2 = listAnswer.length; _j < _len2; _j++) {
              answer = listAnswer[_j];
              if (answer.mapRepetition === null) {
                console.log("mapRepetition expected but not found");
              } else {
                /*
                repetitionNumber = answer.mapRepetition[questionSetDTO.code]
                code= questionSetDTO.code
                repetitionToAdd = {}#code:repetition}
                repetitionToAdd[questionSetDTO.code] =repetitionNumber
                */
                if ($scope.mapRepetition[questionSetDTO.code]) {
                  founded = false;
                  _ref2 = $scope.mapRepetition[questionSetDTO.code];
                  for (_k = 0, _len3 = _ref2.length; _k < _len3; _k++) {
                    repetition = _ref2[_k];
                    if ($scope.compareRepetitionMap(repetition, answer.mapRepetition)) {
                      founded = true;
                    }
                  }
                  if (founded === false) {
                    $scope.mapRepetition[questionSetDTO.code][$scope.mapRepetition[questionSetDTO.code].length] = angular.copy(answer.mapRepetition);
                  }
                } else {
                  $scope.mapRepetition[questionSetDTO.code] = [];
                  $scope.mapRepetition[questionSetDTO.code][0] = angular.copy(answer.mapRepetition);
                }
              }
            }
          }
        }
      }
      if (questionSetDTO.children) {
        _ref3 = questionSetDTO.children;
        _results = [];
        for (_l = 0, _len4 = _ref3.length; _l < _len4; _l++) {
          child = _ref3[_l];
          _results.push($scope.loopRepetition(child));
        }
        return _results;
      }
    };
    $scope.storeAnswers();
    return $scope.loading = false;
  });
  $scope.$on('SAVE', function() {
    var answer, listAnswerToSave, promise, _i, _len, _ref;
    modalService.show('modalLoading');
    listAnswerToSave = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.value) {
        listAnswerToSave[listAnswerToSave.length] = answer;
      }
    }
    console.log("listAnswerToSave");
    console.log(listAnswerToSave);
    $scope.o.answersSave.listAnswers = listAnswerToSave;
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSave
    });
    promise.success(function(data, status, headers, config) {
      messageFlash.displaySuccess("Your answers are saved !");
      modalService.hide('modalLoading');
      return;
    });
    return promise.error(function(data, status, headers, config) {
      messageFlash.displayError("An error was thrown during the save : " + data.message);
      modalService.hide('modalLoading');
      return;
    });
  });
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    if (question === null || question === void 0) {
      console.log("ERROR : this question was not found : " + code);
      return null;
    }
    if (question.unitCategoryId === null || question.unitCategoryId === void 0) {
      console.log("ERROR : there is no unitCategoryId for this question : " + code);
      return null;
    }
    return $scope.o.unitCategories[question.unitCategoryId];
  };
  $scope.getCodeList = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    return $scope.o.codeLists[question.codeListName];
  };
  $scope.getRepetitionMapByQuestionSet = function(code, mapRepetition) {
    var listRepetition, repetition, _i, _len, _ref;
    listRepetition = [];
    if ($scope.mapRepetition[code] !== null && $scope.mapRepetition[code] !== void 0) {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if (mapRepetition === null || mapRepetition === void 0 || $scope.compareRepetitionMap(repetition, mapRepetition)) {
          listRepetition[listRepetition.length] = repetition;
        }
      }
    }
    return listRepetition;
  };
  $scope.getQuestion = function(code, listQuestionSets) {
    var q, qSet, result, _i, _j, _len, _len2, _ref;
    if (listQuestionSets == null) {
      listQuestionSets = $scope.o.questionSets;
    }
    if (listQuestionSets) {
      for (_i = 0, _len = listQuestionSets.length; _i < _len; _i++) {
        qSet = listQuestionSets[_i];
        if (qSet.questions) {
          _ref = qSet.questions;
          for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
            q = _ref[_j];
            if (q.code === code) {
              return q;
            }
          }
        }
        if (qSet.children) {
          result = $scope.getQuestion(code, qSet.children);
          if (result) {
            return result;
          }
        }
      }
    }
    return null;
  };
  $scope.getAnswerOrCreate = function(code, mapIteration) {
    var answerLine, result;
    result = $scope.getAnswer(code, mapIteration);
    if (result) {
      return result;
    } else {
      answerLine = {
        'questionKey': code,
        'value': null,
        'unitId': null,
        'mapRepetition': mapIteration
      };
      $scope.answerList[$scope.answerList.length] = answerLine;
      return answerLine;
    }
  };
  $scope.getAnswer = function(code, mapIteration) {
    var answer, _i, _len, _ref;
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code) {
        if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
          return answer;
        }
      }
    }
    return null;
  };
  $scope.getListAnswer = function(code, mapIteration) {
    var answer, listAnswer, _i, _len, _ref;
    listAnswer = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code && $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
        listAnswer[listAnswer.length] = answer;
      }
    }
    return listAnswer;
  };
  $scope.addIteration = function(code, mapRepetition) {
    var max, repetition, repetitionToAdd, _i, _len, _ref;
    max = 0;
    repetitionToAdd = {};
    if (mapRepetition !== null && mapRepetition !== void 0) {
      repetitionToAdd = angular.copy(mapRepetition);
    }
    if ($scope.mapRepetition[code] === null || $scope.mapRepetition[code] === void 0) {
      repetitionToAdd[code] = max + 1;
      $scope.mapRepetition[code] = [];
      return $scope.mapRepetition[code][0] = repetitionToAdd;
    } else {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if ($scope.compareRepetitionMap(repetition, mapRepetition) && repetition[code] > max) {
          max = repetition[code];
        }
      }
      repetitionToAdd[code] = max + 1;
      return $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd;
    }
  };
  $scope.removeIteration = function(questionSetCode, iterationToDelete, mapRepetition) {
    var iteration, key, len, question, _i, _len, _ref, _results;
    len = $scope.answerList.length;
    while (len--) {
      question = $scope.answerList[len];
      if (question.mapRepetition !== null && question.mapRepetition !== void 0 && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)) {
        if (question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] === iterationToDelete[questionSetCode]) {
          $scope.answerList.splice(len, 1);
        }
      }
    }
    _ref = Object.keys($scope.mapRepetition);
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      _results.push((function() {
        var _results;
        if (key !== '$$hashKey') {
          len = $scope.mapRepetition[key].length;
          _results = [];
          while (len--) {
            iteration = $scope.mapRepetition[key][len];
            _results.push($scope.compareRepetitionMap(iteration, mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] === iterationToDelete[questionSetCode] ? $scope.mapRepetition[key].splice(len, 1) : void 0);
          }
          return _results;
        }
      })());
    }
    return _results;
  };
  return $scope.compareRepetitionMap = function(mapContainer, mapContained) {
    var key, value, _i, _len, _ref;
    if (mapContained === null || mapContained === void 0 || mapContained.length === 0) {
      return true;
    }
    if (mapContainer === null || mapContainer === void 0 || mapContainer.length === 0) {
      return false;
    }
    _ref = Object.keys(mapContained);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        value = mapContained[key];
        if (mapContainer[key] === null || mapContainer[key] === void 0 || mapContainer[key] !== value) {
          return false;
        }
      }
    }
    return true;
  };
});angular.module('app.controllers').controller("FormCtrl", function($scope, downloadService, $http, messageFlash, modalService, formIdentifier) {
  $scope.formIdentifier = formIdentifier;
  $scope.answerList = [];
  $scope.mapRepetition = [];
  /*
  illustration of the structures
  $scope.mapRepetition['A15'] = [{'A15':1},
                                  {'A15':2}]

  $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                 {'A16':2,'A15':1}]
  */
  $scope.loading = true;
  modalService.show('modalLoading');
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    var answerSave, qSet, _i, _len, _ref;
    console.log("data");
    console.log(data);
    $scope.o = data;
    $scope.loopRepetition = function(questionSetDTO) {
      var answer, child, founded, listAnswer, q, repetition, _i, _j, _k, _l, _len, _len2, _len3, _len4, _ref, _ref2, _ref3, _results;
      if (questionSetDTO.repetitionAllowed === true) {
        if (questionSetDTO.questions) {
          _ref = questionSetDTO.questions;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            q = _ref[_i];
            listAnswer = $scope.getListAnswer(q.code);
            for (_j = 0, _len2 = listAnswer.length; _j < _len2; _j++) {
              answer = listAnswer[_j];
              if (answer.mapRepetition === null) {
                console.log("mapRepetition expected but not found");
              } else {
                /*
                repetitionNumber = answer.mapRepetition[questionSetDTO.code]
                code= questionSetDTO.code
                repetitionToAdd = {}#code:repetition}
                repetitionToAdd[questionSetDTO.code] =repetitionNumber
                */
                if ($scope.mapRepetition[questionSetDTO.code]) {
                  founded = false;
                  _ref2 = $scope.mapRepetition[questionSetDTO.code];
                  for (_k = 0, _len3 = _ref2.length; _k < _len3; _k++) {
                    repetition = _ref2[_k];
                    if ($scope.compareRepetitionMap(repetition, answer.mapRepetition)) {
                      founded = true;
                    }
                  }
                  if (founded === false) {
                    $scope.mapRepetition[questionSetDTO.code][$scope.mapRepetition[questionSetDTO.code].length] = angular.copy(answer.mapRepetition);
                  }
                } else {
                  $scope.mapRepetition[questionSetDTO.code] = [];
                  $scope.mapRepetition[questionSetDTO.code][0] = angular.copy(answer.mapRepetition);
                }
              }
            }
          }
        }
      }
      if (questionSetDTO.children) {
        _ref3 = questionSetDTO.children;
        _results = [];
        for (_l = 0, _len4 = _ref3.length; _l < _len4; _l++) {
          child = _ref3[_l];
          _results.push($scope.loopRepetition(child));
        }
        return _results;
      }
    };
    if ($scope.o.answersSave !== null && $scope.o.answersSave !== void 0) {
      answerSave = $scope.o.answersSave;
      $scope.answerList = answerSave.listAnswers;
    }
    _ref = $scope.o.questionSets;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      qSet = _ref[_i];
      $scope.loopRepetition(qSet);
    }
    console.log("$scope.mapRepetition");
    console.log($scope.mapRepetition);
    modalService.hide('modalLoading');
    return $scope.loading = false;
  });
  $scope.$on('SAVE', function() {
    var answer, listAnswerToSave, promise, _i, _len, _ref;
    modalService.show('modalLoading');
    listAnswerToSave = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.value && (answer.value.$valid === null || answer.value.$valid === void 0 || answer.value.$valid === true)) {
        listAnswerToSave[listAnswerToSave.length] = answer;
      }
    }
    console.log("listAnswerToSave");
    console.log(listAnswerToSave);
    $scope.o.answersSave.listAnswers = listAnswerToSave;
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSave
    });
    promise.success(function(data, status, headers, config) {
      messageFlash.displaySuccess("Your answers are saved !");
      modalService.hide('modalLoading');
      return;
    });
    return promise.error(function(data, status, headers, config) {
      messageFlash.displayError("An error was thrown during the save : " + data.message);
      modalService.hide('modalLoading');
      return;
    });
  });
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    if (question === null || question === void 0) {
      console.log("ERROR : this question was not found : " + code);
      return null;
    }
    if (question.unitCategoryId === null || question.unitCategoryId === void 0) {
      console.log("ERROR : there is no unitCategoryId for this question : " + code);
      return null;
    }
    return $scope.o.unitCategories[question.unitCategoryId];
  };
  $scope.getCodeList = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    return $scope.o.codeLists[question.codeListName];
  };
  $scope.getRepetitionMapByQuestionSet = function(code, mapRepetition) {
    var listRepetition, repetition, _i, _len, _ref;
    listRepetition = [];
    if ($scope.mapRepetition[code] !== null && $scope.mapRepetition[code] !== void 0) {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if (mapRepetition === null || mapRepetition === void 0 || $scope.compareRepetitionMap(repetition, mapRepetition)) {
          listRepetition[listRepetition.length] = repetition;
        }
      }
    }
    return listRepetition;
  };
  $scope.getQuestion = function(code, listQuestionSets) {
    var q, qSet, result, _i, _j, _len, _len2, _ref;
    if (listQuestionSets == null) {
      listQuestionSets = $scope.o.questionSets;
    }
    if (listQuestionSets) {
      for (_i = 0, _len = listQuestionSets.length; _i < _len; _i++) {
        qSet = listQuestionSets[_i];
        if (qSet.questions) {
          _ref = qSet.questions;
          for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
            q = _ref[_j];
            if (q.code === code) {
              return q;
            }
          }
        }
        if (qSet.children) {
          result = $scope.getQuestion(code, qSet.children);
          if (result) {
            return result;
          }
        }
      }
    }
    return null;
  };
  $scope.getAnswerOrCreate = function(code, mapIteration) {
    var answerLine, result;
    if (code === null || code === void 0) {
      console.log("ERROR !! getAnswerOrCreate : code is null or undefined");
      return null;
    }
    result = $scope.getAnswer(code, mapIteration);
    if (result) {
      return result;
    } else {
      answerLine = {
        'questionKey': code,
        'value': null,
        'unitId': null,
        'mapRepetition': mapIteration
      };
      $scope.answerList[$scope.answerList.length] = answerLine;
      return answerLine;
    }
  };
  $scope.getAnswer = function(code, mapIteration) {
    var answer, _i, _len, _ref;
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code) {
        if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
          return answer;
        }
      }
    }
    return null;
  };
  $scope.getListAnswer = function(code, mapIteration) {
    var answer, listAnswer, _i, _len, _ref;
    listAnswer = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code && $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
        listAnswer[listAnswer.length] = answer;
      }
    }
    return listAnswer;
  };
  $scope.addIteration = function(code, mapRepetition) {
    var max, repetition, repetitionToAdd, _i, _len, _ref;
    max = 0;
    repetitionToAdd = {};
    if (mapRepetition !== null && mapRepetition !== void 0) {
      repetitionToAdd = angular.copy(mapRepetition);
    }
    if ($scope.mapRepetition[code] === null || $scope.mapRepetition[code] === void 0) {
      repetitionToAdd[code] = max + 1;
      $scope.mapRepetition[code] = [];
      return $scope.mapRepetition[code][0] = repetitionToAdd;
    } else {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if ($scope.compareRepetitionMap(repetition, mapRepetition) && repetition[code] > max) {
          max = repetition[code];
        }
      }
      repetitionToAdd[code] = max + 1;
      return $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd;
    }
  };
  $scope.removeIteration = function(questionSetCode, iterationToDelete, mapRepetition) {
    var iteration, key, len, question, _i, _len, _ref, _results;
    len = $scope.answerList.length;
    while (len--) {
      question = $scope.answerList[len];
      if (question.mapRepetition !== null && question.mapRepetition !== void 0 && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)) {
        if (question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] === iterationToDelete[questionSetCode]) {
          $scope.answerList.splice(len, 1);
        }
      }
    }
    _ref = Object.keys($scope.mapRepetition);
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      _results.push((function() {
        var _results;
        if (key !== '$$hashKey') {
          len = $scope.mapRepetition[key].length;
          _results = [];
          while (len--) {
            iteration = $scope.mapRepetition[key][len];
            _results.push($scope.compareRepetitionMap(iteration, mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] === iterationToDelete[questionSetCode] ? $scope.mapRepetition[key].splice(len, 1) : void 0);
          }
          return _results;
        }
      })());
    }
    return _results;
  };
  return $scope.compareRepetitionMap = function(mapContainer, mapContained) {
    var key, value, _i, _len, _ref;
    if (mapContained === null || mapContained === void 0 || mapContained.length === 0) {
      return true;
    }
    if (mapContainer === null || mapContainer === void 0 || mapContainer.length === 0) {
      return false;
    }
    _ref = Object.keys(mapContained);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        value = mapContained[key];
        if (mapContainer[key] === null || mapContainer[key] === void 0 || mapContainer[key] !== value) {
          return false;
        }
      }
    }
    return true;
  };
});angular.module('app.controllers').controller("Form5Ctrl", function($scope, downloadService, $http, messageFlash) {
  $scope.formIdentifier = "TAB5";
  $scope.answerList = [];
  $scope.mapRepetition = [];
  /*
  illustration of the structures
  $scope.mapRepetition['A15'] = [{'A15':1},
                                  {'A15':2}]

  $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                 {'A16':2,'A15':1}]
  */
  $scope.loading = true;
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    console.log("data");
    console.log(data);
    $scope.o = data;
    $scope.storeAnswers = function() {
      var answerSave, qSet, _i, _len, _ref;
      answerSave = $scope.o.answersSave;
      $scope.answerList = answerSave.listAnswers;
      _ref = $scope.o.questionSets;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qSet = _ref[_i];
        $scope.loopRepetition(qSet);
      }
      console.log("$scope.mapRepetition");
      return console.log($scope.mapRepetition);
    };
    $scope.loopRepetition = function(questionSetDTO) {
      var answer, child, founded, listAnswer, q, repetition, _i, _j, _k, _l, _len, _len2, _len3, _len4, _ref, _ref2, _ref3, _results;
      if (questionSetDTO.repetitionAllowed === true) {
        if (questionSetDTO.questions) {
          _ref = questionSetDTO.questions;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            q = _ref[_i];
            listAnswer = $scope.getListAnswer(q.code);
            for (_j = 0, _len2 = listAnswer.length; _j < _len2; _j++) {
              answer = listAnswer[_j];
              if (answer.mapRepetition === null) {
                console.log("mapRepetition expected but not found");
              } else {
                /*
                repetitionNumber = answer.mapRepetition[questionSetDTO.code]
                code= questionSetDTO.code
                repetitionToAdd = {}#code:repetition}
                repetitionToAdd[questionSetDTO.code] =repetitionNumber
                */
                if ($scope.mapRepetition[questionSetDTO.code]) {
                  founded = false;
                  _ref2 = $scope.mapRepetition[questionSetDTO.code];
                  for (_k = 0, _len3 = _ref2.length; _k < _len3; _k++) {
                    repetition = _ref2[_k];
                    if ($scope.compareRepetitionMap(repetition, answer.mapRepetition)) {
                      founded = true;
                    }
                  }
                  if (founded === false) {
                    $scope.mapRepetition[questionSetDTO.code][$scope.mapRepetition[questionSetDTO.code].length] = angular.copy(answer.mapRepetition);
                  }
                } else {
                  $scope.mapRepetition[questionSetDTO.code] = [];
                  $scope.mapRepetition[questionSetDTO.code][0] = angular.copy(answer.mapRepetition);
                }
              }
            }
          }
        }
      }
      if (questionSetDTO.children) {
        _ref3 = questionSetDTO.children;
        _results = [];
        for (_l = 0, _len4 = _ref3.length; _l < _len4; _l++) {
          child = _ref3[_l];
          _results.push($scope.loopRepetition(child));
        }
        return _results;
      }
    };
    $scope.storeAnswers();
    return $scope.loading = false;
  });
  $scope.$on('SAVE', function() {
    var answer, listAnswerToSave, promise, _i, _len, _ref;
    listAnswerToSave = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.value) {
        listAnswerToSave[listAnswerToSave.length] = answer;
      }
    }
    console.log("listAnswerToSave");
    console.log(listAnswerToSave);
    $scope.o.answersSave.listAnswers = listAnswerToSave;
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSave
    });
    promise.success(function(data, status, headers, config) {
      messageFlash.displaySuccess("Your answers are saved !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      messageFlash.displayError("An error was thrown during the save : " + data.message);
      return;
    });
  });
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    if (question === null || question === void 0) {
      console.log("ERROR : this question was not found : " + code);
      return null;
    }
    if (question.unitCategoryId === null || question.unitCategoryId === void 0) {
      console.log("ERROR : there is no unitCategoryId for this question : " + code);
      return null;
    }
    return $scope.o.unitCategories[question.unitCategoryId];
  };
  $scope.getCodeList = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    return $scope.o.codeLists[question.codeListName];
  };
  $scope.getRepetitionMapByQuestionSet = function(code, mapRepetition) {
    var listRepetition, repetition, _i, _len, _ref;
    listRepetition = [];
    if ($scope.mapRepetition[code] !== null && $scope.mapRepetition[code] !== void 0) {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if (mapRepetition === null || mapRepetition === void 0 || $scope.compareRepetitionMap(repetition, mapRepetition)) {
          listRepetition[listRepetition.length] = repetition;
        }
      }
    }
    return listRepetition;
  };
  $scope.getQuestion = function(code, listQuestionSets) {
    var q, qSet, result, _i, _j, _len, _len2, _ref;
    if (listQuestionSets == null) {
      listQuestionSets = $scope.o.questionSets;
    }
    if (listQuestionSets) {
      for (_i = 0, _len = listQuestionSets.length; _i < _len; _i++) {
        qSet = listQuestionSets[_i];
        if (qSet.questions) {
          _ref = qSet.questions;
          for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
            q = _ref[_j];
            if (q.code === code) {
              return q;
            }
          }
        }
        if (qSet.children) {
          result = $scope.getQuestion(code, qSet.children);
          if (result) {
            return result;
          }
        }
      }
    }
    return null;
  };
  $scope.getAnswerOrCreate = function(code, mapIteration) {
    var answerLine, result;
    result = $scope.getAnswer(code, mapIteration);
    if (result) {
      return result;
    } else {
      answerLine = {
        'questionKey': code,
        'value': null,
        'unitId': null,
        'mapRepetition': mapIteration
      };
      $scope.answerList[$scope.answerList.length] = answerLine;
      return answerLine;
    }
  };
  $scope.getAnswer = function(code, mapIteration) {
    var answer, _i, _len, _ref;
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code) {
        if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
          return answer;
        }
      }
    }
    return null;
  };
  $scope.getListAnswer = function(code, mapIteration) {
    var answer, listAnswer, _i, _len, _ref;
    listAnswer = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code && $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
        listAnswer[listAnswer.length] = answer;
      }
    }
    return listAnswer;
  };
  $scope.addIteration = function(code, mapRepetition) {
    var max, repetition, repetitionToAdd, _i, _len, _ref;
    max = 0;
    repetitionToAdd = {};
    if (mapRepetition !== null && mapRepetition !== void 0) {
      repetitionToAdd = angular.copy(mapRepetition);
    }
    if ($scope.mapRepetition[code] === null || $scope.mapRepetition[code] === void 0) {
      repetitionToAdd[code] = max + 1;
      $scope.mapRepetition[code] = [];
      return $scope.mapRepetition[code][0] = repetitionToAdd;
    } else {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if ($scope.compareRepetitionMap(repetition, mapRepetition) && repetition[code] > max) {
          max = repetition[code];
        }
      }
      repetitionToAdd[code] = max + 1;
      return $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd;
    }
  };
  $scope.removeIteration = function(questionSetCode, iterationToDelete, mapRepetition) {
    var iteration, key, len, question, _i, _len, _ref, _results;
    len = $scope.answerList.length;
    while (len--) {
      question = $scope.answerList[len];
      if (question.mapRepetition !== null && question.mapRepetition !== void 0 && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)) {
        if (question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] === iterationToDelete[questionSetCode]) {
          $scope.answerList.splice(len, 1);
        }
      }
    }
    _ref = Object.keys($scope.mapRepetition);
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      _results.push((function() {
        var _results;
        if (key !== '$$hashKey') {
          len = $scope.mapRepetition[key].length;
          _results = [];
          while (len--) {
            iteration = $scope.mapRepetition[key][len];
            _results.push($scope.compareRepetitionMap(iteration, mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] === iterationToDelete[questionSetCode] ? $scope.mapRepetition[key].splice(len, 1) : void 0);
          }
          return _results;
        }
      })());
    }
    return _results;
  };
  return $scope.compareRepetitionMap = function(mapContainer, mapContained) {
    var key, value, _i, _len, _ref;
    if (mapContained === null || mapContained === void 0 || mapContained.length === 0) {
      return true;
    }
    if (mapContainer === null || mapContainer === void 0 || mapContainer.length === 0) {
      return false;
    }
    _ref = Object.keys(mapContained);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        value = mapContained[key];
        if (mapContainer[key] === null || mapContainer[key] === void 0 || mapContainer[key] !== value) {
          return false;
        }
      }
    }
    return true;
  };
});angular.module('app.controllers').controller("Form6Ctrl", function($scope, downloadService, $http, messageFlash) {
  $scope.formIdentifier = "TAB6";
  $scope.answerList = [];
  $scope.mapRepetition = [];
  /*
  illustration of the structures
  $scope.mapRepetition['A15'] = [{'A15':1},
                                  {'A15':2}]

  $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                 {'A16':2,'A15':1}]
  */
  $scope.loading = true;
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    console.log("data");
    console.log(data);
    $scope.o = data;
    $scope.storeAnswers = function() {
      var answerSave, qSet, _i, _len, _ref;
      answerSave = $scope.o.answersSave;
      $scope.answerList = answerSave.listAnswers;
      _ref = $scope.o.questionSets;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qSet = _ref[_i];
        $scope.loopRepetition(qSet);
      }
      console.log("$scope.mapRepetition");
      return console.log($scope.mapRepetition);
    };
    $scope.loopRepetition = function(questionSetDTO) {
      var answer, child, founded, listAnswer, q, repetition, _i, _j, _k, _l, _len, _len2, _len3, _len4, _ref, _ref2, _ref3, _results;
      if (questionSetDTO.repetitionAllowed === true) {
        if (questionSetDTO.questions) {
          _ref = questionSetDTO.questions;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            q = _ref[_i];
            listAnswer = $scope.getListAnswer(q.code);
            for (_j = 0, _len2 = listAnswer.length; _j < _len2; _j++) {
              answer = listAnswer[_j];
              if (answer.mapRepetition === null) {
                console.log("mapRepetition expected but not found");
              } else {
                /*
                repetitionNumber = answer.mapRepetition[questionSetDTO.code]
                code= questionSetDTO.code
                repetitionToAdd = {}#code:repetition}
                repetitionToAdd[questionSetDTO.code] =repetitionNumber
                */
                if ($scope.mapRepetition[questionSetDTO.code]) {
                  founded = false;
                  _ref2 = $scope.mapRepetition[questionSetDTO.code];
                  for (_k = 0, _len3 = _ref2.length; _k < _len3; _k++) {
                    repetition = _ref2[_k];
                    if ($scope.compareRepetitionMap(repetition, answer.mapRepetition)) {
                      founded = true;
                    }
                  }
                  if (founded === false) {
                    $scope.mapRepetition[questionSetDTO.code][$scope.mapRepetition[questionSetDTO.code].length] = angular.copy(answer.mapRepetition);
                  }
                } else {
                  $scope.mapRepetition[questionSetDTO.code] = [];
                  $scope.mapRepetition[questionSetDTO.code][0] = angular.copy(answer.mapRepetition);
                }
              }
            }
          }
        }
      }
      if (questionSetDTO.children) {
        _ref3 = questionSetDTO.children;
        _results = [];
        for (_l = 0, _len4 = _ref3.length; _l < _len4; _l++) {
          child = _ref3[_l];
          _results.push($scope.loopRepetition(child));
        }
        return _results;
      }
    };
    $scope.storeAnswers();
    return $scope.loading = false;
  });
  $scope.$on('SAVE', function() {
    var answer, listAnswerToSave, promise, _i, _len, _ref;
    listAnswerToSave = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.value) {
        listAnswerToSave[listAnswerToSave.length] = answer;
      }
    }
    console.log("listAnswerToSave");
    console.log(listAnswerToSave);
    $scope.o.answersSave.listAnswers = listAnswerToSave;
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSave
    });
    promise.success(function(data, status, headers, config) {
      messageFlash.displaySuccess("Your answers are saved !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      messageFlash.displayError("An error was thrown during the save : " + data.message);
      return;
    });
  });
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    if (question === null || question === void 0) {
      console.log("ERROR : this question was not found : " + code);
      return null;
    }
    if (question.unitCategoryId === null || question.unitCategoryId === void 0) {
      console.log("ERROR : there is no unitCategoryId for this question : " + code);
      return null;
    }
    return $scope.o.unitCategories[question.unitCategoryId];
  };
  $scope.getCodeList = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
    return $scope.o.codeLists[question.codeListName];
  };
  $scope.getRepetitionMapByQuestionSet = function(code, mapRepetition) {
    var listRepetition, repetition, _i, _len, _ref;
    listRepetition = [];
    if ($scope.mapRepetition[code] !== null && $scope.mapRepetition[code] !== void 0) {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if (mapRepetition === null || mapRepetition === void 0 || $scope.compareRepetitionMap(repetition, mapRepetition)) {
          listRepetition[listRepetition.length] = repetition;
        }
      }
    }
    return listRepetition;
  };
  $scope.getQuestion = function(code, listQuestionSets) {
    var q, qSet, result, _i, _j, _len, _len2, _ref;
    if (listQuestionSets == null) {
      listQuestionSets = $scope.o.questionSets;
    }
    if (listQuestionSets) {
      for (_i = 0, _len = listQuestionSets.length; _i < _len; _i++) {
        qSet = listQuestionSets[_i];
        if (qSet.questions) {
          _ref = qSet.questions;
          for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
            q = _ref[_j];
            if (q.code === code) {
              return q;
            }
          }
        }
        if (qSet.children) {
          result = $scope.getQuestion(code, qSet.children);
          if (result) {
            return result;
          }
        }
      }
    }
    return null;
  };
  $scope.getAnswerOrCreate = function(code, mapIteration) {
    var answerLine, result;
    result = $scope.getAnswer(code, mapIteration);
    if (result) {
      return result;
    } else {
      answerLine = {
        'questionKey': code,
        'value': null,
        'unitId': null,
        'mapRepetition': mapIteration
      };
      $scope.answerList[$scope.answerList.length] = answerLine;
      return answerLine;
    }
  };
  $scope.getAnswer = function(code, mapIteration) {
    var answer, _i, _len, _ref;
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code) {
        if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
          return answer;
        }
      }
    }
    return null;
  };
  $scope.getListAnswer = function(code, mapIteration) {
    var answer, listAnswer, _i, _len, _ref;
    listAnswer = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code && $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
        listAnswer[listAnswer.length] = answer;
      }
    }
    return listAnswer;
  };
  $scope.addIteration = function(code, mapRepetition) {
    var max, repetition, repetitionToAdd, _i, _len, _ref;
    max = 0;
    repetitionToAdd = {};
    if (mapRepetition !== null && mapRepetition !== void 0) {
      repetitionToAdd = angular.copy(mapRepetition);
    }
    if ($scope.mapRepetition[code] === null || $scope.mapRepetition[code] === void 0) {
      repetitionToAdd[code] = max + 1;
      $scope.mapRepetition[code] = [];
      return $scope.mapRepetition[code][0] = repetitionToAdd;
    } else {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if ($scope.compareRepetitionMap(repetition, mapRepetition) && repetition[code] > max) {
          max = repetition[code];
        }
      }
      repetitionToAdd[code] = max + 1;
      return $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd;
    }
  };
  $scope.removeIteration = function(questionSetCode, iterationToDelete, mapRepetition) {
    var iteration, key, len, question, _i, _len, _ref, _results;
    len = $scope.answerList.length;
    while (len--) {
      question = $scope.answerList[len];
      if (question.mapRepetition !== null && question.mapRepetition !== void 0 && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)) {
        if (question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] === iterationToDelete[questionSetCode]) {
          $scope.answerList.splice(len, 1);
        }
      }
    }
    _ref = Object.keys($scope.mapRepetition);
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      _results.push((function() {
        var _results;
        if (key !== '$$hashKey') {
          len = $scope.mapRepetition[key].length;
          _results = [];
          while (len--) {
            iteration = $scope.mapRepetition[key][len];
            _results.push($scope.compareRepetitionMap(iteration, mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] === iterationToDelete[questionSetCode] ? $scope.mapRepetition[key].splice(len, 1) : void 0);
          }
          return _results;
        }
      })());
    }
    return _results;
  };
  return $scope.compareRepetitionMap = function(mapContainer, mapContained) {
    var key, value, _i, _len, _ref;
    if (mapContained === null || mapContained === void 0 || mapContained.length === 0) {
      return true;
    }
    if (mapContainer === null || mapContainer === void 0 || mapContainer.length === 0) {
      return false;
    }
    _ref = Object.keys(mapContained);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        value = mapContained[key];
        if (mapContainer[key] === null || mapContainer[key] === void 0 || mapContainer[key] !== value) {
          return false;
        }
      }
    }
    return true;
  };
});angular.module('app.directives').run(function($templateCache) {$templateCache.put('$/angular/templates/mm-awac-sub-sub-title.html', "<div><div class=\"sub_sub_title\"><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-string-question.html', "<div class=\"question_field\" ng-hide=\"getCondition() === false\"><div><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span></div><div class=\"oneelement\"><div class=\"status answer\" mm-not-implemented></div><input ng-model=\"getAnswerValue().value\" type=\"text\"><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-document-question.html', "<div ng-hide=\"getCondition() === false\"><div><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><i ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></i></div><div class=\"oneelement\"><!--.status.answer(mm-not-impleented)--><!--input(type=\"text\", ng-model=\"getAnswerByQuestionCode(getQuestionCode()).value\")--><!--.user_icon(mm-not-implemented) XM--></div></div>");$templateCache.put('$/angular/templates/mm-awac-form1.html', "<mm-awac-section title-code=\"A1_TITLE\"><mm-awac-sub-title question-code=\"A1\"></mm-awac-sub-title><mm-awac-integer-question question-code=\"A2\"></mm-awac-integer-question><mm-awac-select-question question-code=\"A3\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A3').value == '1'\" question-code=\"A4\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A3').value == '2' || getAnswer('A3').value == '3'\" question-code=\"A5\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A3').value == '4'\" question-code=\"A6\"></mm-awac-select-question><mm-awac-boolean-question ng-condition=\"getAnswer('A3').value == '4'\" question-code=\"A7\"></mm-awac-boolean-question><mm-awac-select-question ng-condition=\"getAnswer('A7').value == '1'\" question-code=\"A8\"></mm-awac-select-question><mm-awac-real-with-unit-question question-code=\"A9\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A10\"></mm-awac-real-with-unit-question><mm-awac-boolean-question ng-condition=\"getAnswer('A3').value != '4'\" question-code=\"A11\"></mm-awac-boolean-question><mm-awac-integer-question question-code=\"A12\"></mm-awac-integer-question></mm-awac-section>");$templateCache.put('$/angular/templates/mm-awac-results.html', "<ng-virtual><h1>Results</h1><table border=\"1\"><thead><th></th><th>Scope 1</th><th>Scope 2</th><th>Scope 3</th></thead><tbody><tr ng-repeat=\"rl in o.reportLines\"><td>{{rl.indicatorName}}</td><td>{{rl.scope1Value}}</td><td>{{rl.scope2Value}}</td><td>{{rl.scope3Value}}</td></tr></tbody></table><fs-donut bind=\"temp.browsers\"></fs-donut></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-form2.html', "<mm-awac-section title-code=\"A13_TITLE\"><mm-awac-document-question question-code=\"A14\"></mm-awac-document-question><div ng-bind-html=\"'A15_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A15\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A15')\"><mm-awac-select-question question-code=\"A16\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-real-with-unit-question question-code=\"A17\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A15')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A15_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A20_TITLE\"><mm-awac-document-question question-code=\"A21\"></mm-awac-document-question><mm-awac-sub-sub-title question-code=\"A22\"></mm-awac-sub-sub-title><mm-awac-real-with-unit-question question-code=\"A23\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A24\"></mm-awac-real-with-unit-question><div ng-bind-html=\"'A25_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A25\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A25')\"><mm-awac-select-question question-code=\"A26\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-percentage-question question-code=\"A27\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-real-with-unit-question question-code=\"A28\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A25')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A25_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A31_TITLE\"><mm-awac-boolean-question question-code=\"A32\"></mm-awac-boolean-question><mm-awac-document-question ng-condition=\"getAnswer('A32').value == '1'\" question-code=\"A33\"></mm-awac-document-question><div ng-show=\"getAnswer('A32').value == '1'\"><div ng-bind-html=\"'A34_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A34\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A34')\"><mm-awac-select-question ng-condition=\"getAnswer('A32').value == '1'\" question-code=\"A35\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A32').value == '1'\" question-code=\"A36\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A34')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A34_LOOPDESC' | translate\"></span></button></div></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A37_TITLE\"><mm-awac-boolean-question question-code=\"A38\"></mm-awac-boolean-question><mm-awac-document-question ng-condition=\"getAnswer('A38').value == '1'\" question-code=\"A39\"></mm-awac-document-question></mm-awac-section><div ng-show=\"getAnswer('A38').value == '1'\"><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter l’usage des systèmes froids. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A41_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><div ng-bind-html=\"'A42_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A42\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A42')\"><mm-awac-select-question ng-condition=\"getAnswer('A38').value == '1'\" question-code=\"A43\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A38').value == '1'\" question-code=\"A44\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A42')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A42_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A45_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A38').value == '1'\" question-code=\"A46\"></mm-awac-real-with-unit-question></div></div></tab><div ng-show=\"getAnswer('A5').value == '1' || getAnswer('A5').value == '2'\"><tab class=\"tab-color-yellow\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A47_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-boolean-question ng-condition=\"getAnswer('A38').value == '1' &amp;&amp; (getAnswer('A5').value == '1' || getAnswer('A5').value == '2')\" question-code=\"A48\"></mm-awac-boolean-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A38').value == '1' &amp;&amp; (getAnswer('A5').value == '1' || getAnswer('A5').value == '2')\" question-code=\"A49\"></mm-awac-real-with-unit-question></div></div></tab></div></tabset></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-enterprise-survey.html', "<ng-virtual>\n    <div ng-hide=\"$root.isLogin()\" >\n        <div class=\"header\">\n            <div class=\"wallonie_logo\"></div>\n            <div class=\"awac_logo\"></div>\n            <div>\n                <div class=\"calculateur_type\" ng-bind-html=\"'TITLE' | translate\"></div>\n                <div class=\"entreprise_name\">{{ $root.organization.name }}</div>\n            </div>\n\n            <div class=\"users\">\n                <div ng-show=\"$root.currentPerson!=null\"><span ng-bind-html=\"'WELCOME' | translate\"></span>, <span\n                        class=\"username\">{{$root.currentPerson.firstName}} {{$root.currentPerson.lastName}}</span>\n                </div>\n                <div ng-show=\"$root.currentPerson==null\">Your are currently not connected</div>\n                <!-- temporary -->\n                <button ng-show=\"$root.currentPerson!=null\"\n                        type=\"button\"\n                        ng-click=\"$root.logout();\"\n                        ng-bind-html=\"'LOGOUT_BUTTON' | translate\"\n                        class=\"user_manage\">\n                </button>\n                <!--<button type=\"button\" class=\"user_manage\">Gestion utilisateurs</button>-->\n            </div>\n\n            <div class=\"small_separator\"></div>\n\n            <div class=\"lg_cf_help\">\n                <div>\n                    <select ng-model=\"$parent.language\">\n                        <option value=\"en\" ng-bind-html=\"'EN' | translate\"></option>\n                        <option value=\"fr\" ng-bind-html=\"'FR' | translate\"></option>\n                        <option value=\"nl\" ng-bind-html=\"'NL' | translate\"></option>\n                    </select>\n                </div>\n                <button type=\"button\" class=\"confidentiality\" mm-not-implemented>Confidentialité</button>\n                <button type=\"button\" class=\"help\" mm-not-implemented>Assistance</button>\n            </div>\n\n        </div>\n\n        <div class=\"data_menu\">\n            <div class=\"data_date\">\n                <div ng-bind-html=\"'PERIOD_DATA' | translate\"></div>\n                <select ng-options=\"p.id as p.label for p in $root.periods\" ng-model=\"period\"></select>\n            </div>\n\n            <div class=\"big_separator\" mm-not-implemented></div>\n\n            <div class=\"data_date_compare\" mm-not-implemented>\n                <div>Comparaison avec</div>\n                <select>\n                    <option>données 2012</option>\n                    <option>données 2011</option>\n                    <option>données 2010</option>\n                </select>\n            </div>\n\n            <div class=\"big_separator\"></div>\n\n            <div class=\"data_save\">\n                <div class=\"last_save\" mm-not-implemented>\n                    <span ng-bind-html=\"'LAST_SAVE' | translate\"></span><br>\n                    07/01/2014 - 19:42\n                </div>\n                <div class=\"small_separator\"></div>\n                <div class=\"save_button\">\n                    <button type=\"button\" class=\"save\" ng-click=\"save()\" ng-bind-html=\"'SAVE_BUTTON' | translate\"></button>\n                </div>\n            </div>\n        </div>\n\n\n        <div class=\"nav_tabs\">\n            <div class=\"nav_entreprise\">\n                <div class=\"site_menu\">\n                    <div class=\"site\">\n                        <button type=\"button\" class=\"site_manage\" mm-not-implemented>Gérer les sites</button>\n                        <div>Site</div>\n                        <div class=\"small_separator\"></div>\n                        <div class=\"sitename\">{{ $root.organization.sites[0].name }}</div>\n                        <button type=\"button\" class=\"verification\" mm-not-implemented>Vérification</button>\n                    </div>\n                    <div class=\"menu\">\n                        <div class=\"{{ getMenuCurrentClass('/form1') }}\" ng-click=\"nav('/form1')\">\n                            <div ng-bind-html=\"'TAB1' | translate\"></div>\n                            <div class=\"menu_jauge\" mm-not-implemented>\n                                <div>rempli à 16%</div>\n                                <div class=\"jauge\">\n                                    <div class=\"jauge_limit\" style=\"margin-left: 70%\"></div>\n                                    <div class=\"jauge_current\" style=\"width: 15%\"></div>\n                                    <div class=\"jauge_bg\"></div>\n                                </div>\n                            </div>\n                            <div class=\"menu_arrow\"></div>\n                        </div>\n                        <div class=\"{{ getMenuCurrentClass('/form2') }}\" ng-click=\"nav('/form2')\">\n                            <div ng-bind-html=\"'TAB2' | translate\"></div>\n                            <div class=\"menu_jauge\" mm-not-implemented>\n                                <div>rempli à 70%</div>\n                                <div class=\"jauge\">\n                                    <div class=\"jauge_limit\" style=\"margin-left: 25%\"></div>\n                                    <div class=\"jauge_current jauge_ok\" style=\"width: 70%\"></div>\n                                    <div class=\"jauge_bg jauge_ok\"></div>\n                                </div>\n                            </div>\n                            <div class=\"menu_arrow\"></div>\n                        </div>\n                        <div class=\"{{ getMenuCurrentClass('/form3') }}\" ng-click=\"nav('/form3')\">\n                            <div ng-bind-html=\"'TAB3' | translate\"></div>\n                            <div class=\"menu_jauge\" mm-not-implemented>\n                                <div>rempli à 5%</div>\n                                <div class=\"jauge\">\n                                    <div class=\"jauge_limit\" style=\"margin-left: 50%\"></div>\n                                    <div class=\"jauge_current\" style=\"width: 5%\"></div>\n                                    <div class=\"jauge_bg\"></div>\n                                </div>\n                            </div>\n                            <div class=\"menu_arrow\"></div>\n                        </div>\n                        <div class=\"{{ getMenuCurrentClass('/form4') }}\" ng-click=\"nav('/form4')\">\n                            <div ng-bind-html=\"'TAB4' | translate\"></div>\n                            <div class=\"menu_jauge\" mm-not-implemented>\n                                <div>rempli à 25%</div>\n                                <div class=\"jauge\">\n                                    <div class=\"jauge_limit\" style=\"margin-left: 45%\"></div>\n                                    <div class=\"jauge_current\" style=\"width: 25%\"></div>\n                                    <div class=\"jauge_bg\"></div>\n                                </div>\n                            </div>\n                            <div class=\"menu_arrow\"></div>\n                        </div>\n\n                        <div class=\"{{ getMenuCurrentClass('/form5') }}\" ng-click=\"nav('/form5')\">\n                            <div ng-bind-html=\"'TAB5' | translate\"></div>\n                            <div class=\"menu_jauge\" mm-not-implemented>\n                                <div>rempli à 82%</div>\n                                <div class=\"jauge\">\n                                    <div class=\"jauge_limit\" style=\"margin-left: 45%\"></div>\n                                    <div class=\"jauge_current jauge_ok\" style=\"width: 82%\"></div>\n                                    <div class=\"jauge_bg jauge_ok\"></div>\n                                </div>\n                            </div>\n                            <div class=\"menu_arrow\"></div>\n                        </div>\n\n                        <div class=\"{{ getMenuCurrentClass('/form6') }}\" ng-click=\"nav('/form6')\">\n                            <div ng-bind-html=\"'TAB6' | translate\"></div>\n                            <div class=\"menu_jauge\" mm-not-implemented>\n                                <div>rempli à 50%</div>\n                                <div class=\"jauge\">\n                                    <div class=\"jauge_limit\" style=\"margin-left: 50%\"></div>\n                                    <div class=\"jauge_current jauge_ok\" style=\"width: 50%\"></div>\n                                    <div class=\"jauge_bg jauge_ok\"></div>\n                                </div>\n                            </div>\n                            <div class=\"menu_arrow\"></div>\n                        </div>\n\n                        <div class=\"{{ getMenuCurrentClass('/form7') }}\" ng-click=\"nav('/form7')\">\n                            <div ng-bind-html=\"'TAB7' | translate\"></div>\n                            <div class=\"menu_jauge\" mm-not-implemented>\n                                <div>rempli à 50%</div>\n                                <div class=\"jauge\">\n                                    <div class=\"jauge_limit\" style=\"margin-left: 50%\"></div>\n                                    <div class=\"jauge_current jauge_ok\" style=\"width: 50%\"></div>\n                                    <div class=\"jauge_bg jauge_ok\"></div>\n                                </div>\n                            </div>\n                            <div class=\"menu_arrow\"></div>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"last_menu\">\n                    <div class=\"{{ getMenuCurrentClass('/results') }}\" ng-click=\"nav('/results')\">\n                        résultats\n                    </div>\n                    <div mm-not-implemented>\n                        actions de réduction\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n\n    <div ng-class=\"{true: '', false: 'content'}[$root.isLogin()]\" ng-view>\n\n\n    </div>\n\n\n    <div class=\"footer\"></div>\n\n</ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-percentage-question.html', "<div class=\"question_field\" ng-hide=\"getCondition() === false\"><div><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span></div><div class=\"twoelement\"><div class=\"status answer\" mm-not-implemented></div><input ng-pattern=\"/^[0-9,. ]*$/\" ng-model=\"getAnswerValue().value\" type=\"text\"><span style=\"margin-left:5px\">%</span><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-sub-title.html', "<div><div class=\"sub_title\"><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-section.html', "<ng-virtual><div class=\"element\"><div class=\"element_header\"><div class=\"title\" ng-bind-html=\"getTitleCode() | translate\"></div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div ng-transclude ng-class=\"getMode()\"></div></div></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-form7.html', "<mm-awac-section title-code=\"A243_TITLE\"><div ng-bind-html=\"'A244_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" ng-question-set-code=\"'A244'\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A244')\"><mm-awac-string-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A245'\"></mm-awac-string-question><mm-awac-real-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A246'\"></mm-awac-real-question><mm-awac-string-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A247'\"></mm-awac-string-question><mm-awac-select-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A248'\"></mm-awac-select-question><mm-awac-boolean-question ng-condition=\"getAnswer('A248',itLevel1).value == '2'\" ng-repetition-map=\"itLevel1\" ng-question-code=\"'A249'\"></mm-awac-boolean-question><div ng-show=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Transport--><mm-awac-sub-title question-code=\"A250\"></mm-awac-sub-title><mm-awac-document-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A251\"></mm-awac-document-question><mm-awac-sub-sub-title question-code=\"A252\"></mm-awac-sub-sub-title><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport aval. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A253_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A254\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A255\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><mm-awac-percentage-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A256\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A257\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A258\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A259\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A260\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A261\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A262\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A263\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A264\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A265\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A266_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A267\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><mm-awac-select-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A268\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A268',itLevel1).value == '3' &amp;&amp; (getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1')\" question-code=\"A269\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A268',itLevel1).value == '2' &amp;&amp; (getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1')\" question-code=\"A270\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A268',itLevel1).value == '1' &amp;&amp; (getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249,itLevel1').value == '1')\" question-code=\"A271\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question></div></div></tab></tabset></div></div><div ng-show=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Distribution--><mm-awac-sub-title question-code=\"A272\"></mm-awac-sub-title><div ng-bind-html=\"'A273_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" ng-question-set-code=\"'A273'\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A273',itLevel1)\"><mm-awac-string-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A274\" ng-repetition-map=\"itLevel2\"></mm-awac-string-question><div ng-bind-html=\"'A275_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel3\" question-set-code=\"A275\" ng-repetition-map=\"itLevel2\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A275',itLevel2)\"><mm-awac-select-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A276\" ng-repetition-map=\"itLevel3\"></mm-awac-select-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A277\" ng-repetition-map=\"itLevel3\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A275',itLevel2)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A275_LOOPDESC' | translate\"></span></button><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A278\" ng-repetition-map=\"itLevel2\"></mm-awac-real-with-unit-question><div ng-bind-html=\"'A279_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel3\" question-set-code=\"A279\" ng-repetition-map=\"itLevel2\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A279',itLevel2)\"><mm-awac-select-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A280\" ng-repetition-map=\"itLevel3\"></mm-awac-select-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A281\" ng-repetition-map=\"itLevel3\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A279',itLevel2)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A279_LOOPDESC' | translate\"></span></button></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A273',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A273_LOOPDESC' | translate\"></span></button></div><div ng-show=\"getAnswer('A249',itLevel1).value == '1'\"><!--Traitement--><mm-awac-sub-title question-code=\"A282\"></mm-awac-sub-title><mm-awac-document-question ng-condition=\"getAnswer('A249',itLevel1).value == '1'\" question-code=\"A283\"></mm-awac-document-question><div ng-bind-html=\"'A284_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A284\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A284',itLevel1)\"><mm-awac-select-question ng-condition=\"getAnswer('A249',itLevel1).value == '1'\" question-code=\"A285\" ng-repetition-map=\"itLevel2\"></mm-awac-select-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A249',itLevel1).value == '1'\" question-code=\"A286\" ng-repetition-map=\"itLevel2\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A284',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A284_LOOPDESC' | translate\"></span></button><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A249',itLevel1).value == '1'\" question-code=\"A287\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><div ng-bind-html=\"'A288_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A288\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A288',itLevel1)\"><mm-awac-select-question ng-condition=\"getAnswer('A249',itLevel1).value == '1'\" question-code=\"A289\" ng-repetition-map=\"itLevel2\"></mm-awac-select-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A249',itLevel1).value == '1'\" question-code=\"A290\" ng-repetition-map=\"itLevel2\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A288',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A288_LOOPDESC' | translate\"></span></button></div><div ng-show=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Utilisation--><mm-awac-sub-title question-code=\"A291\"></mm-awac-sub-title><mm-awac-document-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249').value == '1'\" question-code=\"A292\"></mm-awac-document-question><mm-awac-real-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249').value == '1'\" question-code=\"A293\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A294\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A295\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A296\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><div ng-bind-html=\"'A297_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A297\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A297',itLevel1)\"><mm-awac-select-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A298\" ng-repetition-map=\"itLevel2\"></mm-awac-select-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A299\" ng-repetition-map=\"itLevel2\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A297',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A297_LOOPDESC' | translate\"></span></button></div><div ng-show=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Fin de vie--><mm-awac-sub-title question-code=\"A300\"></mm-awac-sub-title><mm-awac-document-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A301\"></mm-awac-document-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A302\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><div ng-bind-html=\"'A303_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A303\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A303',itLevel1)\"><mm-awac-string-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A304\" ng-repetition-map=\"itLevel2\"></mm-awac-string-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A305\" ng-repetition-map=\"itLevel2\"></mm-awac-real-with-unit-question><mm-awac-select-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A306\" ng-repetition-map=\"itLevel2\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A307\" ng-repetition-map=\"itLevel2\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A248',itLevel1).value == '1'|| getAnswer('A249',itLevel1).value == '1'\" question-code=\"A308\" ng-repetition-map=\"itLevel2\"></mm-awac-select-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A303',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A303_LOOPDESC' | translate\"></span></button></div></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A244')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A244_LOOPDESC' | translate\"></span></button></mm-awac-section>");$templateCache.put('$/angular/templates/mm-awac-form4.html', "<mm-awac-section title-code=\"A128_TITLE\"><mm-awac-document-question question-code=\"A129\"></mm-awac-document-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A130_TITLE\"><mm-awac-sub-title question-code=\"A131\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"A132\"></mm-awac-sub-sub-title><mm-awac-real-with-unit-question question-code=\"A133\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A134\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A135\"></mm-awac-real-with-unit-question><mm-awac-boolean-question question-code=\"A136\"></mm-awac-boolean-question><mm-awac-select-question ng-condition=\"getAnswer('A136').value == '1'\" question-code=\"A137\"></mm-awac-select-question><mm-awac-boolean-question ng-condition=\"getAnswer('A136').value == '1'\" question-code=\"A138\"></mm-awac-boolean-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A138').value == '1'\" question-code=\"A139\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A138').value == '0'\" question-code=\"A500\"></mm-awac-real-with-unit-question><mm-awac-sub-title question-code=\"A140\"></mm-awac-sub-title><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport effectué par des transporteurs. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A141_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><div ng-bind-html=\"'A142_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A142\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A142')\"><mm-awac-string-question question-code=\"A143\" ng-repetition-map=\"itLevel1\"></mm-awac-string-question><mm-awac-real-with-unit-question question-code=\"A145\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A146\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><mm-awac-percentage-question question-code=\"A147\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question question-code=\"A148\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question question-code=\"A149\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question question-code=\"A150\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question question-code=\"A151\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question question-code=\"A152\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question question-code=\"A153\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question question-code=\"A154\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question question-code=\"A155\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-percentage-question question-code=\"A156\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A142')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A142_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A157_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-real-with-unit-question question-code=\"A158\"></mm-awac-real-with-unit-question><mm-awac-select-question question-code=\"A159\"></mm-awac-select-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A159').value == '3'\" question-code=\"A160\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A159').value == '2'\" question-code=\"A161\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A159').value == '1'\" question-code=\"A162\"></mm-awac-real-with-unit-question></div></div></tab></tabset></div></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A163_TITLE\"><div ng-bind-html=\"'A164_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A164\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A164')\"><mm-awac-string-question question-code=\"A165\" ng-repetition-map=\"itLevel1\"></mm-awac-string-question><div ng-bind-html=\"'A166_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A166\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A166',itLevel1)\"><mm-awac-select-question question-code=\"A167\" ng-repetition-map=\"itLevel2\"></mm-awac-select-question><mm-awac-real-with-unit-question question-code=\"A168\" ng-repetition-map=\"itLevel2\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A166',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A166_LOOPDESC' | translate\"></span></button><mm-awac-real-with-unit-question question-code=\"A169\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><div ng-bind-html=\"'A170_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A170\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A170',itLevel1)\"><mm-awac-select-question question-code=\"A171\" ng-repetition-map=\"itLevel2\"></mm-awac-select-question><mm-awac-real-with-unit-question question-code=\"A172\" ng-repetition-map=\"itLevel2\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A170',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A170_LOOPDESC' | translate\"></span></button></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A164')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A164_LOOPDESC' | translate\"></span></button></mm-awac-section>");$templateCache.put('$/angular/templates/mm-awac-form3.html', "<mm-awac-section title-code=\"A50_TITLE\"><mm-awac-document-question question-code=\"A51\"></mm-awac-document-question><div class=\"horizontal_separator\"></div></mm-awac-section><mm-awac-section title-code=\"A52_TITLE\"></mm-awac-section><div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport routier. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A53_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-sub-title question-code=\"A54\"></mm-awac-sub-title><mm-awac-real-with-unit-question question-code=\"A55\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A56\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A57\"></mm-awac-real-with-unit-question><mm-awac-sub-title question-code=\"A58\"></mm-awac-sub-title><mm-awac-real-with-unit-question question-code=\"A59\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A60\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A61\"></mm-awac-real-with-unit-question><mm-awac-sub-title question-code=\"A62\"></mm-awac-sub-title><mm-awac-real-with-unit-question question-code=\"A63\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A64\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A65\"></mm-awac-real-with-unit-question></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A66_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><div ng-bind-html=\"'A67_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A67\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A67')\"><mm-awac-string-question question-code=\"A68\" ng-repetition-map=\"itLevel1\"></mm-awac-string-question><mm-awac-boolean-question question-code=\"A69\" ng-repetition-map=\"itLevel1\"></mm-awac-boolean-question><mm-awac-select-question ng-condition=\"getAnswer('A69',itLevel1).value == '0'\" question-code=\"A70\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question question-code=\"A71\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question question-code=\"A72\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-real-question ng-condition=\"getAnswer('A72',itLevel1).value == '1'\" question-code=\"A73\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-question ng-condition=\"getAnswer('A72',itLevel1).value == '2'|| getAnswer('A72',itLevel1).value == '3'|| getAnswer('A72',itLevel1).value == '4'|| getAnswer('A72',itLevel1).value == '5'\" question-code=\"A74\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-question ng-condition=\"getAnswer('A72',itLevel1).value == '6' || getAnswer('A72',itLevel1).value == '7'\" question-code=\"A75\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-question question-code=\"A76\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A67')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A67_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-yellow\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A77_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><div ng-bind-html=\"'A78_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A78\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A78')\"><mm-awac-string-question question-code=\"A79\" ng-repetition-map=\"itLevel1\"></mm-awac-string-question><mm-awac-boolean-question question-code=\"A80\" ng-repetition-map=\"itLevel1\"></mm-awac-boolean-question><mm-awac-select-question ng-condition=\"getAnswer('A80',itLevel1).value == '0'\" question-code=\"A81\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question question-code=\"A83\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-real-question question-code=\"A88\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-question ng-condition=\"getAnswer('A83',itLevel1).value == '1'\" question-code=\"A89\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-question ng-condition=\"getAnswer('A83',itLevel1).value == '2'\" question-code=\"A90\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-question ng-condition=\"getAnswer('A83',itLevel1).value == '3'\" question-code=\"A91\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-question ng-condition=\"getAnswer('A83',itLevel1).value == '4'\" question-code=\"A92\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A78')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A78_LOOPDESC' | translate\"></span></button></div></div></tab></tabset></div></div><div class=\"horizontal_separator\"></div></div><mm-awac-section title-code=\"A93_TITLE\"></mm-awac-section><div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport en commun. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A94_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-real-question question-code=\"A95\"></mm-awac-real-question><mm-awac-real-question question-code=\"A96\"></mm-awac-real-question><mm-awac-real-question question-code=\"A97\"></mm-awac-real-question><mm-awac-real-question question-code=\"A98\"></mm-awac-real-question><mm-awac-real-question question-code=\"A99\"></mm-awac-real-question><mm-awac-real-question question-code=\"A100\"></mm-awac-real-question><mm-awac-real-question question-code=\"A101\"></mm-awac-real-question><mm-awac-real-question question-code=\"A102\"></mm-awac-real-question><mm-awac-real-question question-code=\"A103\"></mm-awac-real-question><mm-awac-real-question question-code=\"A104\"></mm-awac-real-question><mm-awac-real-question question-code=\"A105\"></mm-awac-real-question><mm-awac-real-question question-code=\"A106\"></mm-awac-real-question><mm-awac-real-with-unit-question question-code=\"A107\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A108\"></mm-awac-real-with-unit-question></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A109_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-boolean-question question-code=\"A110\"></mm-awac-boolean-question><mm-awac-boolean-question question-code=\"A111\"></mm-awac-boolean-question><mm-awac-boolean-question question-code=\"A112\"></mm-awac-boolean-question></div></div></tab></tabset></div></div><div class=\"horizontal_separator\"></div></div><mm-awac-section title-code=\"A113_TITLE\"></mm-awac-section><div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport en avion. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A114_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><div ng-bind-html=\"'A115_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A115\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A115')\"><mm-awac-string-question question-code=\"A116\" ng-repetition-map=\"itLevel1\"></mm-awac-string-question><mm-awac-select-question question-code=\"A117\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question question-code=\"A118\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-real-question question-code=\"A119\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-with-unit-question question-code=\"A120\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A115')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A115_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A121_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-percentage-question question-code=\"A122\"></mm-awac-percentage-question><mm-awac-boolean-question question-code=\"A123\"></mm-awac-boolean-question><mm-awac-boolean-question ng-condition=\"getAnswer('A123',itLevel1).value == '0'\" question-code=\"A124\"></mm-awac-boolean-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A124',itLevel1).value == '1'\" question-code=\"A125\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A124',itLevel1).value == '0'\" question-code=\"A126\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A123',itLevel1).value == '1'\" question-code=\"A127\"></mm-awac-real-with-unit-question></div></div></tab></tabset></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-field-text.html', "<tr><td>{{getInfo().fieldTitle}}<td><input ng-keyup=\"controlField()\" placeholder=\"{{getInfo().placeholder}}\" ng-model=\"getInfo().field\" type=\"{{getInfo().fieldType}}\"></td><td><img src=\"/awac/assets/images/field_valid.png\" ng-show=\"getInfo().isValid\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/awac/assets/images/field_invalid.png\"><div>{{getInfo().validationMessage}}</div></div></td></td></tr>");$templateCache.put('$/angular/templates/mm-awac-modal-login.html', "<!--Modal--><div class=\"modal\" id=\"modalLogin\" aria-labelledby=\"myModalLabel\" ng-enter=\"send()\" aria-hidden=\"true\" tabindex=\"-1\" role=\"dialog\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><!--button(type=\"button\",class=\"close\",data-dismiss=\"modal\")<span aria-hidden=\"true\">&times;<span</span><span class=\"sr-only\">Close</span>--><h4 id=\"myModalLabel\" class=\"modal-title\">Login</h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><!--button(type=\"button\",class=\"btn btn-default\",data-dismiss=\"modal\") Close--><button ng-disabled=\"!allFieldValid()\" ng-click=\"send();\" ng-enabled=\"allFieldValid()\" class=\"btn btn-primary\" type=\"button\">Login</button><button ng-disabled=\"!allFieldValid()\" ng-click=\"test();\" ng-enabled=\"allFieldValid()\" class=\"btn btn-primary\" type=\"button\">test</button></div><img src=\"/awac/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-select-question.html', "<div class=\"question_field\" ng-hide=\"getCondition() === false\"><div><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span></div><div class=\"oneelement\"><div class=\"status answer\" mm-not-implemented></div><select ng-options=\"p.key as p.label for p in getOptionsByQuestionCode()\" ng-model=\"getAnswerValue().value\"></select><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-form5.html', "<!--mm-awac-section(\"Déchets\")--><!--It lacks a proper fild code for \"D2chets alone\" -> TODO : insert into Excel file as an additional line--><ng-virtual><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Déchets</div><div class=\"title_arrow\"></div></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"></div></div><mm-awac-document-question question-code=\"A174\"></mm-awac-document-question></ng-virtual><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A173_TITLE\"><div ng-bind-html=\"'A175_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A175\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A175')\"><mm-awac-string-question question-code=\"A176\" ng-repetition-map=\"itLevel1\"></mm-awac-string-question><mm-awac-select-question question-code=\"A177\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question question-code=\"A178\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-real-with-unit-question question-code=\"A179\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A175')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A175_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A180_TITLE\"><mm-awac-sub-title question-code=\"A181\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"A182\"></mm-awac-sub-sub-title><mm-awac-real-question question-code=\"A183\"></mm-awac-real-question><mm-awac-real-question question-code=\"A184\"></mm-awac-real-question><mm-awac-sub-sub-title question-code=\"A185\"></mm-awac-sub-sub-title><mm-awac-real-question question-code=\"A186\"></mm-awac-real-question><mm-awac-real-question question-code=\"A187\"></mm-awac-real-question><mm-awac-sub-sub-title question-code=\"A188\"></mm-awac-sub-sub-title><mm-awac-real-question question-code=\"A189\"></mm-awac-real-question><mm-awac-real-question question-code=\"A190\"></mm-awac-real-question><mm-awac-sub-sub-title question-code=\"A191\"></mm-awac-sub-sub-title><mm-awac-real-question question-code=\"A192\"></mm-awac-real-question><mm-awac-real-question question-code=\"A193\"></mm-awac-real-question><mm-awac-sub-title question-code=\"A194\"></mm-awac-sub-title><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter les eaux usées industrielles. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A197_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-select-question question-code=\"A195\"></mm-awac-select-question><mm-awac-select-question question-code=\"A198\"></mm-awac-select-question><mm-awac-real-with-unit-question question-code=\"A199\"></mm-awac-real-with-unit-question><mm-awac-select-question question-code=\"A200\"></mm-awac-select-question></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A201_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-select-question question-code=\"A501\"></mm-awac-select-question><mm-awac-real-with-unit-question question-code=\"A202\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question question-code=\"A203\"></mm-awac-real-with-unit-question><mm-awac-select-question question-code=\"A204\"></mm-awac-select-question></div></div></tab></tabset></div></mm-awac-section>");$templateCache.put('$/angular/templates/mm-awac-integer-question.html', "<div class=\"question_field\" ng-hide=\"getCondition() === false\"><div><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span></div><div class=\"oneelement\"><div class=\"status answer\" mm-not-implemented></div><input ng-pattern=\"/^[0-9]*$/\" ng-model=\"getAnswerValue().value\" type=\"text\"><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-boolean-question.html', "<div class=\"question_field\" ng-hide=\"getCondition() === false\"><div><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span></div><div class=\"oneelement\"><div class=\"status answer\" mm-not-implemented></div><span style=\"width:75px;display:inline-block;text-align:right;\">oui</span><input style=\"width :20px !important\" name=\"{{getQuestionCode()}}\" value=\"1\" ng-model=\"getAnswerValue().value\" type=\"radio\"><span style=\"width:75px;display:inline-block;text-align:right;\">non</span><input style=\"width :20px !important\" name=\"{{getQuestionCode()}}\" value=\"0\" ng-model=\"getAnswerValue().value\" type=\"radio\"><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-login.html', "<div class=\"loginBackground\"><div class=\"loginFrame\" ng-enter=\"send()\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text></div><p style=\"background-color:#ff0000;color:#ffffff;padding:15px\" ng-show=\"errorMessage.length &gt; 0\">{{errorMessage}}</p><button ng-click=\"send()\" ng-bind-html=\"'LOGIN_BUTTON' | translate\" class=\"btn btn-primary\" type=\"button\"></button></div></div>");$templateCache.put('$/angular/templates/mm-awac-real-question.html', "<div class=\"question_field\" ng-hide=\"getCondition() === false\"><div><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span></div><div class=\"oneelement\"><div class=\"status answer\" mm-not-implemented></div><input ng-pattern=\"/^[0-9,. ]*$/\" ng-model=\"getAnswerValue().value\" type=\"text\"><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-repetition-question.html', "<div ng-hide=\"getCondition() === false\"><div class=\"repetition-question\"><div class=\"repetition-question-title\" style=\"display : inline-block; margin-right : 20px\" ng-bind-html=\"getQuestionSetCode() + '_LOOPDESC' | translate\"></div><button class=\"remove-button\" ng-click=\"removeAnwser()\" type=\"button\">Supprimer</button><div class=\"repetition-question-container\"><ng-virtual ng-transclude class=\"element_stack\"></ng-virtual></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-loading.html', "<!--Modal--><div class=\"modal\" id=\"modalLoading\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\" tabindex=\"-1\" role=\"dialog\" ng-show=\"show\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\" style=\"text-align:center\"><h4>Loading</h4></div><div class=\"modal-body\" style=\"text-align:center\"><img src=\"/awac/assets/images/loading_preorganization.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-real-with-unit-question.html', "<div class=\"question_field\" ng-hide=\"getCondition() === false\"><div><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span></div><div class=\"twoelement\"><div class=\"status answer\" mm-not-implemented></div><input ng-pattern=\"/^[0-9,. ]*$/\" ng-model=\"getAnswerValue().value\" type=\"text\"><select ng-options=\"p.id as p.name for p in getUnitsByQuestionCode()\" ng-model=\"getAnswerValue().unitId\"></select><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-form6.html', "<ng-virtual><mm-awac-section title-code=\"A205_TITLE\"><mm-awac-document-question question-code=\"A206\"></mm-awac-document-question><mm-awac-sub-title question-code=\"A208\"></mm-awac-sub-title><div ng-bind-html=\"'A209_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A209\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A209')\"><mm-awac-string-question question-code=\"A210\" ng-repetition-map=\"itLevel1\"></mm-awac-string-question><mm-awac-select-question question-code=\"A211\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A211',itLevel1).value == '1'\" question-code=\"A212\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A211',itLevel1).value == '2'\" question-code=\"A213\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A211',itLevel1).value == '3'\" question-code=\"A214\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A211',itLevel1).value == '4'\" question-code=\"A215\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A211',itLevel1).value == '5'\" question-code=\"A216\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A211',itLevel1).value == '6'\" question-code=\"A217\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A211',itLevel1).value == '8'\" question-code=\"A218\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A211',itLevel1).value == '7'\" question-code=\"A219\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-percentage-question ng-condition=\"getAnswer('A211',itLevel1).value == '1' || getAnswer('A211',itLevel1).value == '2' || getAnswer('A211',itLevel1).value == '3' || getAnswer('A211',itLevel1).value == '4'\" question-code=\"A220\" ng-repetition-map=\"itLevel1\"></mm-awac-percentage-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A211',itLevel1).value != '7'\" question-code=\"A221\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A211',itLevel1).value == '7'\" question-code=\"A222\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A209')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A209_LOOPDESC' | translate\"></span></button><mm-awac-sub-title question-code=\"A223\"></mm-awac-sub-title><div ng-bind-html=\"'A224_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A224\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A224')\"><mm-awac-string-question question-code=\"A225\" ng-repetition-map=\"itLevel1\"></mm-awac-string-question><mm-awac-real-question question-code=\"A226\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-question question-code=\"A227\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-question question-code=\"A228\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A224')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A224_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A229_TITLE\"><mm-awac-document-question question-code=\"A230\"></mm-awac-document-question><div ng-bind-html=\"'A231_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A231\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A231')\"><mm-awac-string-question question-code=\"A232\" ng-repetition-map=\"itLevel1\"></mm-awac-string-question><mm-awac-select-question question-code=\"A233\" ng-repetition-map=\"itLevel1\"></mm-awac-select-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A233',itLevel1).value &lt; '18' || getAnswer('A233',itLevel1).value == '23'\" question-code=\"A234\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-condition=\"getAnswer('A233',itLevel1).value == '20' || getAnswer('A233',itLevel1).value == '21'|| getAnswer('A233',itLevel1).value == '22'\" question-code=\"A235\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><mm-awac-real-question ng-condition=\"getAnswer('A233',itLevel1).value == '18' || getAnswer('A233',itLevel1).value == '19'\" question-code=\"A236\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A231')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A231_LOOPDESC' | translate\"></span></button><mm-awac-sub-sub-title question-code=\"A237\"></mm-awac-sub-sub-title><div ng-bind-html=\"'A238_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A238\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A238')\"><mm-awac-string-question question-code=\"A239\" ng-repetition-map=\"itLevel1\"></mm-awac-string-question><mm-awac-real-question question-code=\"A240\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-question question-code=\"A241\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><mm-awac-real-question question-code=\"A242\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A238')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A238_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A309_TITLE\"><mm-awac-document-question question-code=\"A310\"></mm-awac-document-question><div ng-bind-html=\"'A311_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A311\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A311')\"><mm-awac-string-question question-code=\"A312\" ng-repetition-map=\"itLevel1\"></mm-awac-string-question><div ng-bind-html=\"'A313_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A313\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A313',itLevel1)\"><mm-awac-select-question question-code=\"A314\" ng-repetition-map=\"itLevel2\"></mm-awac-select-question><mm-awac-real-with-unit-question question-code=\"A315\" ng-repetition-map=\"itLevel2\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A313',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A313_LOOPDESC' | translate\"></span></button><mm-awac-real-with-unit-question question-code=\"A316\" ng-repetition-map=\"itLevel1\"></mm-awac-real-with-unit-question><div ng-bind-html=\"'A317_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A317\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A317',itLevel1)\"><mm-awac-select-question question-code=\"A318\" ng-repetition-map=\"itLevel2\"></mm-awac-select-question><mm-awac-real-with-unit-question question-code=\"A319\" ng-repetition-map=\"itLevel2\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A317',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A317_LOOPDESC' | translate\"></span></button></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A311')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A311_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A320_TITLE\"><mm-awac-document-question question-code=\"A321\"></mm-awac-document-question><div ng-bind-html=\"'A322_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A322\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A322')\"><mm-awac-string-question question-code=\"A323\" ng-repetition-map=\"itLevel1\"></mm-awac-string-question><mm-awac-real-question question-code=\"A324\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><div ng-bind-html=\"'A325_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A325\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A325',itLevel1)\"><mm-awac-select-question question-code=\"A326\" ng-repetition-map=\"itLevel2\"></mm-awac-select-question><mm-awac-real-with-unit-question question-code=\"A327\" ng-repetition-map=\"itLevel2\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A325',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A325_LOOPDESC' | translate\"></span></button><mm-awac-real-question question-code=\"A328\" ng-repetition-map=\"itLevel1\"></mm-awac-real-question><div ng-bind-html=\"'A329_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A329\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A329',itLevel1)\"><mm-awac-select-question question-code=\"A330\" ng-repetition-map=\"itLevel2\"></mm-awac-select-question><mm-awac-real-with-unit-question question-code=\"A331\" ng-repetition-map=\"itLevel2\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A329',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A329_LOOPDESC' | translate\"></span></button></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A322')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A322_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A332_TITLE\"><mm-awac-document-question question-code=\"A333\"></mm-awac-document-question><div ng-bind-html=\"'A334_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A334\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A334')\"><mm-awac-string-question question-code=\"A335\" ng-repetition-map=\"itLevel2\"></mm-awac-string-question><mm-awac-percentage-question question-code=\"A336\" ng-repetition-map=\"itLevel2\"></mm-awac-percentage-question><mm-awac-real-question question-code=\"A337\" ng-repetition-map=\"itLevel2\"></mm-awac-real-question><mm-awac-real-question question-code=\"A338\" ng-repetition-map=\"itLevel2\"></mm-awac-real-question></mm-awac-repetition-question><button class=\"add-button\" ng-click=\"addIteration('A334')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A334_LOOPDESC' | translate\"></span></button></mm-awac-section></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-modal-confirmation-exit-form.html', "<!--Modal--><div class=\"modal\" id=\"modalConfirmationExitForm\" aria-labelledby=\"myModalLabel\" ng-enter=\"save()\" aria-hidden=\"true\" tabindex=\"-1\" role=\"dialog\" ng-show=\"show\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button ng-click=\"close()\" style=\"float:right\" type=\"button\"><span aria-hidden=\"true\">&times;<span</span><span class=\"sr-only\">Close</span></button><h4 id=\"myModalLabel\" class=\"modal-title\">Attention</h4></div><div class=\"modal-body\"><div class=\"field_form\">Vous allez perdre toutes vos modifications depuis la dernière sauvegarde. Êtes-vous sûr de vouloir continuer ?</div></div><div class=\"modal-footer\"><button ng-click=\"continue();\" type=\"button\"> Continuer sans sauvegarder</button><button ng-click=\"save()\" type=\"button\">Enregister et continuer</button></div></div></div></div>");});