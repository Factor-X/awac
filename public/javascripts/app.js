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
    controller: 'Form1Ctrl'
  }).when('/form2/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form2.html',
    controller: 'Form2Ctrl'
  }).when('/form3/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form3.html',
    controller: 'Form3Ctrl'
  }).when('/form4/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form4.html',
    controller: 'Form4Ctrl'
  }).when('/form5/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form5.html',
    controller: 'Form5Ctrl'
  }).when('/form6/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form6.html',
    controller: 'Form6Ctrl'
  }).when('/form7/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-form7.html',
    controller: 'Form7Ctrl'
  }).when('/results/:period/:scope', {
    templateUrl: '$/angular/templates/mm-awac-results.html',
    controller: 'ResultsCtrl'
  }).otherwise({
    redirectTo: '/login'
  });
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
});angular.module('app.services').service("messageFlash", function($http, flash) {
  this.displaySuccess = function(message) {
    return flash.success = message.replace("\n", '<br />');
  };
  this.displayError = function(message) {
    return flash.error = message.replace(/\n/g, '<br />');
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
            angular.module('app.directives').directive("mmAwacStringQuestion", function(directiveService) {
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
        return scope.$parent.getAnswerOrCreate(scope.ngQuestionCode, scope.ngRepetitionMap);
      };
      return scope.$watch('ngCondition', function() {
        if (scope.ngCondition === false) {
          return scope.getAnswerValue().value = null;
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacDocumentQuestion", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngObject: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-document-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.getAnswerByQuestionCode = function(code) {
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
    controller: 'Form1Ctrl',
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
    controller: 'Form2Ctrl',
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
});angular.module('app.directives').directive("mmAwacSubTitle", function() {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-sub-title.html",
    replace: true,
    transclude: true,
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacSection", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngTitleCode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-section.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
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
    controller: 'Form7Ctrl',
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
    controller: 'Form4Ctrl',
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
    controller: 'Form3Ctrl',
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
});angular.module('app.directives').directive("mmAwacSelectQuestion", function(directiveService) {
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
        return scope.$parent.getAnswerOrCreate(scope.ngQuestionCode, scope.ngRepetitionMap);
      };
      scope.getOptionsByQuestionCode = function() {
        var codeList;
        codeList = scope.$parent.getCodeList(scope.ngQuestionCode);
        if (codeList) {
          return codeList.codeLabels;
        }
        return null;
      };
      return scope.$watch('ngCondition', function() {
        if (scope.ngCondition === false) {
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
    controller: 'Form5Ctrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacIntegerQuestion", function(directiveService) {
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
        return scope.$parent.getAnswerOrCreate(scope.ngQuestionCode, scope.ngRepetitionMap);
      };
      return scope.$watch('ngCondition', function() {
        if (scope.ngCondition === false) {
          return scope.getAnswerValue().value = null;
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacBooleanQuestion", function(directiveService) {
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
        return scope.$parent.getAnswerOrCreate(scope.ngQuestionCode, scope.ngRepetitionMap);
      };
      return scope.$watch('ngCondition', function() {
        if (scope.ngCondition === false) {
          return scope.getAnswerValue().value = null;
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacRealQuestion", function(directiveService) {
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
        return scope.$parent.getAnswerOrCreate(scope.ngQuestionCode, scope.ngRepetitionMap);
      };
      return scope.$watch('ngCondition', function() {
        if (scope.ngCondition === false) {
          return scope.getAnswerValue().value = null;
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacRepetitionQuestion", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionSetCode: '=',
      ngIteration: '=',
      ngRepetitionMap: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-repetition-question.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getQuestionSet = function() {
        return scope.$parent.getQuestionSet(scope.ngQuestionSetCode);
      };
      return scope.removeAnwser = function() {
        return scope.$parent.removeIteration(scope.ngQuestionSetCode, scope.ngIteration, scope.ngRepetitionMap);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRealWithUnitQuestion", function(directiveService) {
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
        return scope.$parent.getAnswerOrCreate(scope.ngQuestionCode, scope.ngRepetitionMap);
      };
      scope.getUnitsByQuestionCode = function() {
        var result;
        result = scope.$parent.getUnitCategories(scope.ngQuestionCode);
        if (result) {
          return result.units;
        }
        return null;
      };
      return scope.$watch('ngCondition', function() {
        if (scope.ngCondition === false) {
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
    controller: 'Form6Ctrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
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
});angular.module('app.controllers').controller("Form6Ctrl", function($scope, downloadService, $http) {
  $scope.formIdentifier = "TAB6";
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    $scope.o = data;
    $scope.A = function(code) {
      var qv, _i, _len, _ref;
      _ref = $scope.o.answersSaveDTO.listAnswers;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qv = _ref[_i];
        if (qv.questionKey === code) {
          return qv;
        }
      }
      return null;
    };
    $scope.U = function(code) {
      var q, uc, unitCategoryId, _i, _j, _len, _len2, _ref, _ref2;
      unitCategoryId = null;
      _ref = $scope.o.questions;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        q = _ref[_i];
        if (q.questionKey === code) {
          unitCategoryId = q.unitCategoryId;
        }
      }
      if (unitCategoryId === null) {
        console.error("impossible to find question by its code: " + code);
        return null;
      }
      _ref2 = $scope.o.unitCategories;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        uc = _ref2[_j];
        if (uc.id === unitCategoryId) {
          return uc.units;
        }
      }
      console.error("impossible to find unit category by its id: " + unitCategoryId + " question code was: " + code);
      return null;
    };
    return $scope.O = function(code) {
      var cl, codeListName, q, _i, _j, _len, _len2, _ref, _ref2;
      codeListName = null;
      _ref = $scope.o.questions;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        q = _ref[_i];
        if (q.questionKey === code) {
          codeListName = q.codeListName;
        }
      }
      if (codeListName === null) {
        console.error("impossible to find question by its code: " + code);
        return null;
      }
      _ref2 = $scope.o.codeLists;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        cl = _ref2[_j];
        if (cl.code === codeListName) {
          return cl.codeLabels;
        }
      }
      console.error("impossible to find codeList by its code: " + codeLabelName + " question code was: " + code);
      return null;
    };
  });
  return $scope.save = function() {
    var promise;
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSaveDTO
    });
    promise.success(function(data, status, headers, config) {
      console.log("SAVE !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      console.log("ERROR : " + data.message);
      return;
    });
  };
});angular.module('app.controllers').controller("Form7Ctrl", function($scope, downloadService, $http) {
  $scope.formIdentifier = "TAB7";
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    $scope.o = data;
    $scope.A = function(code) {
      var qv, _i, _len, _ref;
      _ref = $scope.o.answersSaveDTO.listAnswers;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qv = _ref[_i];
        if (qv.questionKey === code) {
          return qv;
        }
      }
      return null;
    };
    $scope.U = function(code) {
      var q, uc, unitCategoryId, _i, _j, _len, _len2, _ref, _ref2;
      unitCategoryId = null;
      _ref = $scope.o.questions;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        q = _ref[_i];
        if (q.questionKey === code) {
          unitCategoryId = q.unitCategoryId;
        }
      }
      if (unitCategoryId === null) {
        console.error("impossible to find question by its code: " + code);
        return null;
      }
      _ref2 = $scope.o.unitCategories;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        uc = _ref2[_j];
        if (uc.id === unitCategoryId) {
          return uc.units;
        }
      }
      console.error("impossible to find unit category by its id: " + unitCategoryId + " question code was: " + code);
      return null;
    };
    return $scope.O = function(code) {
      var cl, codeListName, q, _i, _j, _len, _len2, _ref, _ref2;
      codeListName = null;
      _ref = $scope.o.questions;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        q = _ref[_i];
        if (q.questionKey === code) {
          codeListName = q.codeListName;
        }
      }
      if (codeListName === null) {
        console.error("impossible to find question by its code: " + code);
        return null;
      }
      _ref2 = $scope.o.codeLists;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        cl = _ref2[_j];
        if (cl.code === codeListName) {
          return cl.codeLabels;
        }
      }
      console.error("impossible to find codeList by its code: " + codeLabelName + " question code was: " + code);
      return null;
    };
  });
  return $scope.save = function() {
    var promise;
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSaveDTO
    });
    promise.success(function(data, status, headers, config) {
      console.log("SAVE !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      console.log("ERROR : " + data.message);
      return;
    });
  };
});angular.module('app.controllers').controller("Form4Ctrl", function($scope, downloadService, $http) {
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
    $scope.o = data;
    $scope.storeAnswers = function() {
      var answerSave, qSet, _i, _len, _ref;
      console.log("je suis $scope.storeAnswers");
      console.log("$scope.o");
      console.log($scope.o);
      answerSave = $scope.o.answersSave;
      $scope.answerList = answerSave.listAnswers;
      console.log("$scope.answerList");
      console.log($scope.answerList);
      _ref = $scope.o.questionSets;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qSet = _ref[_i];
        $scope.loopRepetition(qSet);
      }
      $scope.mapRepetition['A244'] = [
        {
          'A244': 1
        }, {
          'A244': 2
        }
      ];
      $scope.mapRepetition['A273'] = [
        {
          'A244': 1,
          'A273': 1
        }, {
          'A244': 2,
          'A273': 1
        }, {
          'A244': 2,
          'A273': 2
        }
      ];
      console.log("$scope.mapRepetition");
      return console.log($scope.mapRepetition);
    };
    $scope.loopRepetition = function(questionSetDTO, currentRepetition) {
      var answer, code, founded, listAnswer, q, repetition, repetitionNumber, repetitionToAdd, _i, _len, _ref, _results;
      if (currentRepetition == null) {
        currentRepetition = null;
      }
      if (questionSetDTO.repetitionAllowed === true) {
        if (questionSetDTO.questions) {
          _ref = questionSetDTO.questions;
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            q = _ref[_i];
            listAnswer = $scope.getListAnswer(q.code);
            _results.push((function() {
              var _i, _len, _results;
              _results = [];
              for (_i = 0, _len = listAnswer.length; _i < _len; _i++) {
                answer = listAnswer[_i];
                console.log("answer in $scope.loopRepetition");
                console.log(answer);
                _results.push((function() {
                  var _i, _len, _ref;
                  if (answer.mapRepetition === null) {
                    return console.log("mapRepetition expected but not found");
                  } else {
                    repetitionNumber = answer.mapRepetition[questionSetDTO.code];
                    code = questionSetDTO.code;
                    repetitionToAdd = {};
                    repetitionToAdd[questionSetDTO.code] = repetitionNumber;
                    if ($scope.mapRepetition[questionSetDTO.code]) {
                      founded = false;
                      _ref = $scope.mapRepetition[questionSetDTO.code];
                      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                        repetition = _ref[_i];
                        if (repetition[questionSetDTO.code] === repetitionNumber) {
                          console.log("exite dajà");
                          founded = true;
                        }
                      }
                      if (founded === false) {
                        console.log("existe mais ajouté");
                        return $scope.mapRepetition[questionSetDTO.code][$scope.mapRepetition[questionSetDTO.code].length] = repetitionToAdd;
                      }
                    } else {
                      console.log("exite pas, ajoute");
                      $scope.mapRepetition[questionSetDTO.code] = [];
                      return $scope.mapRepetition[questionSetDTO.code][0] = repetitionToAdd;
                    }
                  }
                })());
              }
              return _results;
            })());
          }
          return _results;
        }
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
      console.log("SAVE !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      console.log("ERROR : " + data.message);
      return;
    });
  });
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
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
      if (answer.questionKey === code && scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
        listAnswer[listAnswer.length] = answer;
      }
    }
    return listAnswer;
  };
  $scope.addIteration = function(code, mapRepetition) {
    var max, repetition, repetitionToAdd, _i, _len, _ref;
    max = 1;
    repetitionToAdd = {};
    if (mapRepetition !== null && mapRepetition !== void 0) {
      console.log("mapRepetition");
      console.log(mapRepetition);
      repetitionToAdd = angular.copy(mapRepetition);
    }
    if ($scope.mapRepetition[code] === null || $scope.mapRepetition[code] === void 0) {
      repetitionToAdd[code] = max;
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
      if (question.mapRepetition !== null && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)) {
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
    if (mapContained === null || mapContained === void 0) {
      return true;
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
});angular.module('app.controllers').controller("MainCtrl", function($scope, downloadService, translationService, $sce, $http, $location, $route, $routeParams) {
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
  $scope.nav = function(loc) {
    return $location.path(loc + "/" + $scope.period + "/" + $scope.scopeId);
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
  console.log("run  !!");
  if (!$rootScope.currentPerson) {
    $location.path('/login');
  }
  $rootScope.isLogin = function() {
    return $location.path().substring(0, 6) === "/login";
  };
  $rootScope.logout = function() {
    var promise;
    console.log("logout ??");
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
  $rootScope.testAuthentication = function() {
    var promise;
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
    return promise.error(function(data, status, headers, config) {
      return;
    });
  };
  return $rootScope.testAuthentication();
});angular.module('app.controllers').controller("Form5Ctrl", function($scope, downloadService, $http) {
  $scope.formIdentifier = "TAB5";
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    $scope.o = data;
    $scope.A = function(code) {
      var qv, _i, _len, _ref;
      _ref = $scope.o.answersSaveDTO.listAnswers;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qv = _ref[_i];
        if (qv.questionKey === code) {
          return qv;
        }
      }
      return null;
    };
    $scope.U = function(code) {
      var q, uc, unitCategoryId, _i, _j, _len, _len2, _ref, _ref2;
      unitCategoryId = null;
      _ref = $scope.o.questions;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        q = _ref[_i];
        if (q.questionKey === code) {
          unitCategoryId = q.unitCategoryId;
        }
      }
      if (unitCategoryId === null) {
        console.error("impossible to find question by its code: " + code);
        return null;
      }
      _ref2 = $scope.o.unitCategories;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        uc = _ref2[_j];
        if (uc.id === unitCategoryId) {
          return uc.units;
        }
      }
      console.error("impossible to find unit category by its id: " + unitCategoryId + " question code was: " + code);
      return null;
    };
    return $scope.O = function(code) {
      var cl, codeListName, q, _i, _j, _len, _len2, _ref, _ref2;
      codeListName = null;
      _ref = $scope.o.questions;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        q = _ref[_i];
        if (q.questionKey === code) {
          codeListName = q.codeListName;
        }
      }
      if (codeListName === null) {
        console.error("impossible to find question by its code: " + code);
        return null;
      }
      _ref2 = $scope.o.codeLists;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        cl = _ref2[_j];
        if (cl.code === codeListName) {
          return cl.codeLabels;
        }
      }
      console.error("impossible to find codeList by its code: " + codeLabelName + " question code was: " + code);
      return null;
    };
  });
  return $scope.save = function() {
    var promise;
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSaveDTO
    });
    promise.success(function(data, status, headers, config) {
      console.log("SAVE !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      console.log("ERROR : " + data.message);
      return;
    });
  };
});angular.module('app.controllers').controller("Form1Ctrl", function($scope, downloadService, $http) {
  $scope.formIdentifier = "TAB1";
  $scope.answerList = [];
  $scope.loading = true;
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    $scope.o = data;
    $scope.storeAnswers = function() {
      var answerSave;
      console.log("$scope.o");
      console.log($scope.o);
      answerSave = $scope.o.answersSave;
      return $scope.answerList = answerSave.listAnswers;
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
    $scope.o.answersSave.listAnswers = listAnswerToSave;
    console.log("$scope.o.answersSave.listAnswers");
    console.log($scope.o.answersSave.listAnswers);
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSave
    });
    promise.success(function(data, status, headers, config) {
      console.log("SAVE !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      console.log("ERROR : " + data.message);
      return;
    });
  });
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
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
    if (mapIteration == null) {
      mapIteration = null;
    }
    result = $scope.getAnswer(code);
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
  return $scope.getAnswer = function(code, mapIteration) {
    var answer, failed, iParam, _i, _j, _len, _len2, _ref;
    if (mapIteration == null) {
      mapIteration = null;
    }
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code) {
        failed = false;
        if (mapIteration) {
          for (_j = 0, _len2 = mapIteration.length; _j < _len2; _j++) {
            iParam = mapIteration[_j];
            if (answer.mapRepetition[iParam.key] === null || answer.mapRepetition[iParam.key] !== iParam.value) {
              failed = true;
            }
          }
        }
        if (failed === false) {
          return answer;
        }
      }
    }
    return null;
  };
});angular.module('app.controllers').controller("Form2Ctrl", function($scope, downloadService, $http) {
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
    $scope.o = data;
    $scope.storeAnswers = function() {
      var answerSave, qSet, _i, _len, _ref;
      console.log("je suis $scope.storeAnswers");
      console.log("$scope.o");
      console.log($scope.o);
      answerSave = $scope.o.answersSave;
      $scope.answerList = answerSave.listAnswers;
      console.log("$scope.answerList");
      console.log($scope.answerList);
      _ref = $scope.o.questionSets;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qSet = _ref[_i];
        $scope.loopRepetition(qSet);
      }
      console.log("$scope.mapRepetition");
      return console.log($scope.mapRepetition);
    };
    $scope.loopRepetition = function(questionSetDTO, currentRepetition) {
      var answer, code, founded, listAnswer, q, repetition, repetitionNumber, repetitionToAdd, _i, _len, _ref, _results;
      if (currentRepetition == null) {
        currentRepetition = null;
      }
      if (questionSetDTO.repetitionAllowed === true) {
        _ref = questionSetDTO.questions;
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          q = _ref[_i];
          listAnswer = $scope.getListAnswer(q.code);
          _results.push((function() {
            var _i, _len, _results;
            _results = [];
            for (_i = 0, _len = listAnswer.length; _i < _len; _i++) {
              answer = listAnswer[_i];
              console.log("answer in $scope.loopRepetition");
              console.log(answer);
              _results.push((function() {
                var _i, _len, _ref;
                if (answer.mapRepetition === null) {
                  return console.log("mapRepetition expected but not found");
                } else {
                  repetitionNumber = answer.mapRepetition[questionSetDTO.code];
                  code = questionSetDTO.code;
                  repetitionToAdd = {};
                  repetitionToAdd[questionSetDTO.code] = repetitionNumber;
                  if ($scope.mapRepetition[questionSetDTO.code]) {
                    founded = false;
                    _ref = $scope.mapRepetition[questionSetDTO.code];
                    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                      repetition = _ref[_i];
                      if (repetition[questionSetDTO.code] === repetitionNumber) {
                        console.log("exite dajà");
                        founded = true;
                      }
                    }
                    if (founded === false) {
                      console.log("existe mais ajouté");
                      return $scope.mapRepetition[questionSetDTO.code][$scope.mapRepetition[questionSetDTO.code].length] = repetitionToAdd;
                    }
                  } else {
                    console.log("exite pas, ajoute");
                    $scope.mapRepetition[questionSetDTO.code] = [];
                    return $scope.mapRepetition[questionSetDTO.code][0] = repetitionToAdd;
                  }
                }
              })());
            }
            return _results;
          })());
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
      console.log("SAVE !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      console.log("ERROR : " + data.message);
      return;
    });
  });
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.loading) {
      return null;
    }
    question = $scope.getQuestion(code);
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
  $scope.getRepetitionMapByQuestionSet = function(code) {
    return $scope.mapRepetition[code];
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
    if (mapIteration == null) {
      mapIteration = null;
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
    var answer, failed, key, value, _i, _j, _len, _len2, _ref, _ref2;
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.questionKey === code) {
        failed = false;
        if (mapIteration) {
          _ref2 = Object.keys(mapIteration);
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            key = _ref2[_j];
            if (key !== '$$hashKey') {
              value = mapIteration[key];
              if (answer.mapRepetition[key] === null || answer.mapRepetition[key] !== value) {
                failed = true;
              }
            }
          }
        }
        if (failed === false) {
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
      if (answer.questionKey === code) {
        listAnswer[listAnswer.length] = answer;
      }
    }
    return listAnswer;
  };
  $scope.addIteration = function(code, mapRepetition) {
    var max, repetition, repetitionToAdd, _i, _len, _ref;
    max = 1;
    repetitionToAdd = {};
    if ($scope.mapRepetition[code] === null || $scope.mapRepetition[code] === void 0) {
      repetitionToAdd[code] = max;
      $scope.mapRepetition[code] = [];
      return $scope.mapRepetition[code][0] = repetitionToAdd;
    } else {
      _ref = $scope.mapRepetition[code];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        repetition = _ref[_i];
        if (repetition[code] > max) {
          max = repetition[code];
        }
      }
      repetitionToAdd[code] = max + 1;
      return $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd;
    }
  };
  return $scope.removeIteration = function(questionSetCode, iterationToDelete) {
    var iteration, len, question, _results;
    len = $scope.answerList.length;
    while (len--) {
      question = $scope.answerList[len];
      if (question.mapRepetition !== null) {
        if (question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] === iterationToDelete[questionSetCode]) {
          $scope.answerList.splice(len, 1);
        }
      }
    }
    if ($scope.mapRepetition[questionSetCode]) {
      len = $scope.mapRepetition[questionSetCode].length;
      _results = [];
      while (len--) {
        iteration = $scope.mapRepetition[questionSetCode][len];
        _results.push(iteration[questionSetCode] && iteration[questionSetCode] === iterationToDelete[questionSetCode] ? $scope.mapRepetition[questionSetCode].splice(len, 1) : void 0);
      }
      return _results;
    }
  };
});angular.module('app.controllers').controller("Form3Ctrl", function($scope, downloadService, $http) {
  $scope.formIdentifier = "TAB3";
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, function(data) {
    $scope.o = data;
    $scope.A = function(code) {
      var qv, _i, _len, _ref;
      _ref = $scope.o.answersSaveDTO.listAnswers;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        qv = _ref[_i];
        if (qv.questionKey === code) {
          return qv;
        }
      }
      return null;
    };
    $scope.U = function(code) {
      var q, uc, unitCategoryId, _i, _j, _len, _len2, _ref, _ref2;
      unitCategoryId = null;
      _ref = $scope.o.questions;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        q = _ref[_i];
        if (q.questionKey === code) {
          unitCategoryId = q.unitCategoryId;
        }
      }
      if (unitCategoryId === null) {
        console.error("impossible to find question by its code: " + code);
        return null;
      }
      _ref2 = $scope.o.unitCategories;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        uc = _ref2[_j];
        if (uc.id === unitCategoryId) {
          return uc.units;
        }
      }
      console.error("impossible to find unit category by its id: " + unitCategoryId + " question code was: " + code);
      return null;
    };
    return $scope.O = function(code) {
      var cl, codeListName, q, _i, _j, _len, _len2, _ref, _ref2;
      codeListName = null;
      _ref = $scope.o.questions;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        q = _ref[_i];
        if (q.questionKey === code) {
          codeListName = q.codeListName;
        }
      }
      if (codeListName === null) {
        console.error("impossible to find question by its code: " + code);
        return null;
      }
      _ref2 = $scope.o.codeLists;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        cl = _ref2[_j];
        if (cl.code === codeListName) {
          return cl.codeLabels;
        }
      }
      console.error("impossible to find codeList by its code: " + codeLabelName + " question code was: " + code);
      return null;
    };
  });
  return $scope.$on('SAVE', function() {
    var promise;
    promise = $http({
      method: "POST",
      url: 'answer/save',
      headers: {
        "Content-Type": "application/json"
      },
      data: $scope.o.answersSaveDTO
    });
    promise.success(function(data, status, headers, config) {
      console.log("SAVE !");
      return;
    });
    return promise.error(function(data, status, headers, config) {
      console.log("ERROR : " + data.message);
      return;
    });
  });
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
});angular.module('app.directives').run(function($templateCache) {$templateCache.put('$/angular/templates/mm-awac-string-question.html', "<div ng-hide=\"getCondition() === false\"><div ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></div><div class=\"oneelement\"><div class=\"status answer\" mm-not-implemented></div><input ng-model=\"getAnswerValue().value\" type=\"text\"><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-document-question.html', "<div mm-not-implemented><div ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></div><div class=\"oneelement\"><div class=\"status answer\" mm-not-implemented></div><!--input(type=\"text\", ng-model=\"getAnswerByQuestionCode(getQuestionCode()).value\")--><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-form1.html', "<mm-awac-section title-code=\"A1_TITLE\"><mm-awac-integer-question ng-question-code=\"'A2'\"></mm-awac-integer-question><mm-awac-select-question ng-question-code=\"'A3'\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A3').value == '1'\" ng-question-code=\"'A4'\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A3').value == '2' || getAnswer('A3').value == '3'\" ng-question-code=\"'A5'\"></mm-awac-select-question><mm-awac-select-question ng-condition=\"getAnswer('A3').value == '4'\" ng-question-code=\"'A6'\"></mm-awac-select-question><mm-awac-boolean-question ng-condition=\"getAnswer('A3').value == '4'\" ng-question-code=\"'A7'\"></mm-awac-boolean-question><mm-awac-select-question ng-question-code=\"'A8'\"></mm-awac-select-question><mm-awac-real-with-unit-question ng-question-code=\"'A9'\"></mm-awac-real-with-unit-question><mm-awac-real-with-unit-question ng-question-code=\"'A10'\"></mm-awac-real-with-unit-question><mm-awac-boolean-question ng-question-code=\"'A11'\"></mm-awac-boolean-question><mm-awac-integer-question ng-question-code=\"'A12'\"></mm-awac-integer-question></mm-awac-section>");$templateCache.put('$/angular/templates/mm-awac-results.html', "<ng-virtual><h1>Results</h1><table border=\"1\"><thead><th></th><th>Scope 1</th><th>Scope 2</th><th>Scope 3</th></thead><tbody><tr ng-repeat=\"rl in o.reportLines\"><td>{{rl.indicatorName}}</td><td>{{rl.scope1Value}}</td><td>{{rl.scope2Value}}</td><td>{{rl.scope3Value}}</td></tr></tbody></table><fs-donut bind=\"temp.browsers\"></fs-donut></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-form2.html', "<mm-awac-section title-code=\"A13_TITLE\"><!--mm-awac-document-question (ng-question-code=\"'A14'\")--><div ng-bind-html=\"'A15_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" ng-question-set-code=\"'A15'\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A15')\"><mm-awac-select-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A16'\"></mm-awac-select-question><mm-awac-real-with-unit-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A17'\"></mm-awac-real-with-unit-question></mm-awac-repetition-question><button ng-click=\"addIteration('A15')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A15_LOOPDESC' | translate\"></span></button><div><div class=\"sub_title\">Vapeur--x</div></div><mm-awac-sub-title ng-bind-html=\"'A25_TITLE' | translate\"></mm-awac-sub-title></mm-awac-section><!--<div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Consommation de combustibles</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><mm-awac-repetition-question questions-to-add=\"A14\" question-code=\"A13\" ng-object=\"o\"><mm-awac-select-question question-code=\"A14\" ng-object=\"o\"></mm-awac-select-question></mm-awac-repetition-question><div><div>Pièces documentaires liées aux consommations de combustible</div></div><div ng-repeat=\"v in A('A14')\"><div></div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A14')\" ng-model=\"v.value\"></select><div class=\"user_icon\">XM</div></div><button ng-click=\"removeAnwser(v)\">Remove</button></div><div><div></div><div class=\"oneelement\"><button ng-click=\"addNewAnswer('A14')\">Add</button></div></div><div><div>Combustion de combustible par les sources statiques des sites de l'entreprise</div></div></div><div class=\"element_table\" ng-repeat=\"ri in getRepetitionIndicesFor(['A16', 'A17'])\"><div><div>Combustible</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A16')\" ng-model=\"getByCodeAndRepetitionIndex('A16', ri).value\"></select><div class=\"user_icon\">XM</div></div></div><div><div>Quantité</div><div class=\"twoelements\"><div class=\"status answer\"></div><input ng-model=\"A('A17').value\" type=\"text\"><select ng-options=\"p.id as p.name for p in U('A17')\" ng-model=\"getByCodeAndRepetitionIndex('A17', ri).unitId\"></select><div class=\"user_icon\">XM</div></div></div></div></div></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Electricité et vapeur achetées</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div>Pièces documentaires liées aux achats d'électricité et de vapeur</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A21')\" ng-model=\"A('A21').value\"></select><div class=\"user_icon\">XM</div></div></div><div><div class=\"sub_title\">Electricité</div></div><div><div><div class=\"info\"><div class=\"info_popup\">voici un text d'information assez longue à propos de cette question</div></div><div>Consommation d'électricité verte</div></div><div class=\"twoelements\"><div class=\"status answer\"></div><input ng-model=\"A('A23').value\" type=\"text\"><select ng-options=\"p.id as p.name for p in U('A23')\" ng-model=\"A('A23').unitId\"></select><div class=\"user_icon\">XM</div></div></div><div><div>Consommation d'électricité grise</div><div class=\"twoelements\"><div class=\"status answer\"></div><input ng-model=\"A('A24').value\" type=\"text\"><select ng-options=\"p.id as p.name for p in U('A24')\" ng-model=\"A('A24').unitId\"></select><div class=\"user_icon\">XM</div></div></div><div><div class=\"sub_title\">Vapeur--x</div></div><mm-awac-sub-title ng-bind-html=\"'A25_TITLE' | translate\"></mm-awac-sub-title><div><div>Energie primaire utilisée pour produire la vapeur:</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A26')\" ng-model=\"A('A26').value\"></select><div class=\"user_icon\">XM</div></div></div><div><div><div class=\"sub_sub_title\">Efficacité de la chaudière</div></div></div><div><div>Efficacité de la chaudière</div><div class=\"twoelements\"><div class=\"status answer\"></div><input ng-model=\"A('A27').value\" type=\"text\"><select ng-options=\"p.id as p.name for p in U('A27')\" ng-model=\"A('A27').unitId\"></select><div class=\"user_icon\">XM</div></div></div><div><div>Quantité achetée</div><div class=\"twoelements\"><div class=\"status answer\"></div><input ng-model=\"A('A28').value\" type=\"text\"><select ng-options=\"p.id as p.name for p in U('A28')\" ng-model=\"A('A28').unitId\"></select><div class=\"user_icon\">XM</div></div></div></div></div></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">GES des processus de production</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div>Pièces documentaires liées aux GES des processus de production</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A33')\" ng-model=\"A('A33').value\"></select><div class=\"user_icon\">XM</div></div></div><div><div class=\"sub_title\">Type de GES émis par la production</div></div></div></div></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Systèmes de refroidissement</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div>Pièces documentaires liées aux systèmes de refroidissement</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A39')\" ng-model=\"A('A39').value\"></select><div class=\"user_icon\">XM</div></div></div><div><div class=\"sub_title\">Estimation des émissions à partir des recharges de gaz</div></div><div><div class=\"sub_title\">Estimation des émissions à partir de la puissance du groupe de froid</div></div><div><div class=\"sub_title\">Estimation des émissions à partir de la consommation électrique du site</div></div></div></div></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Froid</div><div class=\"title_arrow\"></div><div class=\"upload\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status\"></div><div class=\"answered_status disable_status\"></div><div class=\"validate_status disable_status\"></div></div></div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter l’usage des systèmes froids. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Systèmes</tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><div><div>Choix du combustible ?</div><div class=\"oneelement\"><div class=\"status answer\"></div><select><option>Mazout</option><option>Fuel</option></select><div class=\"user_icon\">XM</div></div><div class=\"oneelement\"><input value=\"Mazout\"><div class=\"user_icon\">XM</div></div></div><div><div>Consommation précise :</div><div class=\"twoelements\"><div class=\"status\"></div><input value=\"170\"><select><option>Litres</option><option>Kilogrammes</option></select><div class=\"user_icon\">AG</div></div><div class=\"twoelements\"><input value=\"170\"><div style=\"display:inline-block; color: black\">litres</div><div class=\"user_icon\">AG</div></div></div><div><div></div><div><div class=\"status\"></div><button class=\"comments\" type=\"button\">2 commentaires</button></div><div><button class=\"comments\" type=\"button\">1 commentaire</button></div></div></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Recharges</tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><div><div>Choix du combustible ? 2</div><div class=\"oneelement\"><div class=\"status answer\"></div><select><option>Mazout</option><option>Fuel</option></select><div class=\"user_icon\">XM</div></div><div class=\"oneelement\"><input value=\"Mazout\"><div class=\"user_icon\">XM</div></div></div><div><div>Consommation précise :</div><div class=\"twoelements\"><div class=\"status\"></div><input value=\"170\"><select><option>Litres</option><option>Kilogrammes</option></select><div class=\"user_icon\">AG</div></div><div class=\"twoelements\"><input value=\"170\"><div style=\"display:inline-block; color: black\">litres</div><div class=\"user_icon\">AG</div></div></div><div><div></div><div><div class=\"status\"></div><button class=\"comments\" type=\"button\">2 commentaires</button></div><div><button class=\"comments\" type=\"button\">1 commentaire</button></div></div></div></div></tab><tab class=\"tab-color-yellow\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Usages</tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><div><div>Choix du combustible ?</div><div class=\"oneelement\"><div class=\"status answer\"></div><select><option>Mazout</option><option>Fuel</option></select><div class=\"user_icon\">XM</div></div><div class=\"oneelement\"><input value=\"Mazout\"><div class=\"user_icon\">XM</div></div></div><div><div>Consommation précise :</div><div class=\"twoelements\"><div class=\"status\"></div><input value=\"170\"><select><option>Litres</option><option>Kilogrammes</option></select><div class=\"user_icon\">AG</div></div><div class=\"twoelements\"><input value=\"170\"><div style=\"display:inline-block; color: black\">litres</div><div class=\"user_icon\">AG</div></div></div><div><div></div><div><div class=\"status\"></div><button class=\"comments\" type=\"button\">2 commentaires</button></div><div><button class=\"comments\" type=\"button\">1 commentaire</button></div></div></div></div></tab><tab class=\"tab-color-orange\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Méthode #4</tab-heading><div class=\"sub_block tab-color-orange\"><div class=\"element_table\"><div><div>Choix du combustible ?</div><div class=\"oneelement\"><div class=\"status answer\"></div><select><option>Mazout</option><option>Fuel</option></select><div class=\"user_icon\">XM</div></div><div class=\"oneelement\"><input value=\"Mazout\"><div class=\"user_icon\">XM</div></div></div><div><div>Consommation précise :</div><div class=\"twoelements\"><div class=\"status\"></div><input value=\"170\"><select><option>Litres</option><option>Kilogrammes</option></select><div class=\"user_icon\">AG</div></div><div class=\"twoelements\"><input value=\"170\"><div style=\"display:inline-block; color: black\">litres</div><div class=\"user_icon\">AG</div></div></div><div><div></div><div><div class=\"status\"></div><button class=\"comments\" type=\"button\">2 commentaires</button></div><div><button class=\"comments\" type=\"button\">1 commentaire</button></div></div></div></div></tab></tabset></div></div></div></div>-->");$templateCache.put('$/angular/templates/mm-awac-enterprise-survey.html', "<ng-virtual>\n\n    <div class=\"header\">\n        <div class=\"wallonie_logo\"></div>\n        <div class=\"awac_logo\"></div>\n        <div>\n            <div class=\"calculateur_type\" ng-bind-html=\"'TITLE' | translate\"></div>\n            <div class=\"entreprise_name\">{{ $root.organization.name }}</div>\n        </div>\n\n        <div class=\"users\">\n            <div ng-show=\"$root.currentPerson!=null\"><span ng-bind-html=\"'WELCOME' | translate\"></span>, <span class=\"username\">{{$root.currentPerson.firstName}} {{$root.currentPerson.lastName}}</span>\n            </div>\n            <div ng-show=\"$root.currentPerson==null\">Your are currently not connected</div>\n            <!-- temporary -->\n            <button ng-show=\"$root.currentPerson!=null\"\n                    type=\"button\"\n                    ng-click=\"$root.logout();\"\n                    ng-bind-html=\"'LOGOUT_BUTTON' | translate\"\n                    class=\"user_manage\">\n            </button>\n            <!--<button type=\"button\" class=\"user_manage\">Gestion utilisateurs</button>-->\n        </div>\n\n        <div class=\"small_separator\"></div>\n\n        <div class=\"lg_cf_help\">\n            <div>\n                <select ng-model=\"$parent.language\">\n                    <option value=\"en\" ng-bind-html=\"'EN' | translate\"></option>\n                    <option value=\"fr\" ng-bind-html=\"'FR' | translate\"></option>\n                    <option value=\"nl\" ng-bind-html=\"'NL' | translate\"></option>\n                </select>\n            </div>\n            <button type=\"button\" class=\"confidentiality\" mm-not-implemented>Confidentialité</button>\n            <button type=\"button\" class=\"help\" mm-not-implemented>Assistance</button>\n        </div>\n\n    </div>\n\n    <div class=\"data_menu\">\n        <div class=\"data_date\">\n            <div ng-bind-html=\"'PERIOD_DATA' | translate\"></div>\n            <select ng-options=\"p.id as p.label for p in $root.periods\" ng-model=\"period\"></select>\n        </div>\n\n        <div class=\"big_separator\" mm-not-implemented></div>\n\n        <div class=\"data_date_compare\" mm-not-implemented>\n            <div>Comparaison avec</div>\n            <select>\n                <option>données 2012</option>\n                <option>données 2011</option>\n                <option>données 2010</option>\n            </select>\n        </div>\n\n        <div class=\"big_separator\"></div>\n\n        <div class=\"data_save\">\n            <div class=\"last_save\">\n                <span ng-bind-html=\"'LAST_SAVE' | translate\"></span><br>\n                07/01/2014 - 19:42\n            </div>\n            <div class=\"small_separator\"></div>\n            <div class=\"save_button\">\n                <button type=\"button\" class=\"save\" ng-click=\"save()\" ng-bind-html=\"'SAVE_BUTTON' | translate\"></button>\n            </div>\n        </div>\n    </div>\n\n\n    <div class=\"nav_tabs\">\n        <div class=\"nav_entreprise\">\n            <div class=\"site_menu\">\n                <div class=\"site\">\n                    <button type=\"button\" class=\"site_manage\" mm-not-implemented>Gérer les sites</button>\n                    <div>Site</div>\n                    <div class=\"small_separator\"></div>\n                    <div class=\"sitename\">{{ $root.organization.sites[0].name }}</div>\n                    <button type=\"button\" class=\"verification\" mm-not-implemented>Vérification</button>\n                </div>\n                <div class=\"menu\">\n                    <div class=\"{{ getMenuCurrentClass('/form1') }}\" ng-click=\"nav('/form1')\">\n                        <div ng-bind-html=\"'TAB1' | translate\"></div>\n                        <div class=\"menu_jauge\" mm-not-implemented>\n                            <div>rempli à 16%</div>\n                            <div class=\"jauge\">\n                                <div class=\"jauge_limit\" style=\"margin-left: 70%\"></div>\n                                <div class=\"jauge_current\" style=\"width: 15%\"></div>\n                                <div class=\"jauge_bg\"></div>\n                            </div>\n                        </div>\n                        <div class=\"menu_arrow\"></div>\n                    </div>\n                    <div class=\"{{ getMenuCurrentClass('/form2') }}\" ng-click=\"nav('/form2')\">\n                        <div ng-bind-html=\"'TAB2' | translate\"></div>\n                        <div class=\"menu_jauge\" mm-not-implemented>\n                            <div>rempli à 70%</div>\n                            <div class=\"jauge\">\n                                <div class=\"jauge_limit\" style=\"margin-left: 25%\"></div>\n                                <div class=\"jauge_current jauge_ok\" style=\"width: 70%\"></div>\n                                <div class=\"jauge_bg jauge_ok\"></div>\n                            </div>\n                        </div>\n                        <div class=\"menu_arrow\"></div>\n                    </div>\n                    <div class=\"{{ getMenuCurrentClass('/form3') }}\" ng-click=\"nav('/form3')\">\n                        <div ng-bind-html=\"'TAB3' | translate\"></div>\n                        <div class=\"menu_jauge\" mm-not-implemented>\n                            <div>rempli à 5%</div>\n                            <div class=\"jauge\">\n                                <div class=\"jauge_limit\" style=\"margin-left: 50%\"></div>\n                                <div class=\"jauge_current\" style=\"width: 5%\"></div>\n                                <div class=\"jauge_bg\"></div>\n                            </div>\n                        </div>\n                        <div class=\"menu_arrow\"></div>\n                    </div>\n                    <div class=\"{{ getMenuCurrentClass('/form4') }}\" ng-click=\"nav('/form4')\">\n                        <div ng-bind-html=\"'TAB4' | translate\"></div>\n                        <div class=\"menu_jauge\" mm-not-implemented>\n                            <div>rempli à 25%</div>\n                            <div class=\"jauge\">\n                                <div class=\"jauge_limit\" style=\"margin-left: 45%\"></div>\n                                <div class=\"jauge_current\" style=\"width: 25%\"></div>\n                                <div class=\"jauge_bg\"></div>\n                            </div>\n                        </div>\n                        <div class=\"menu_arrow\"></div>\n                    </div>\n\n                    <div class=\"{{ getMenuCurrentClass('/form5') }}\" ng-click=\"nav('/form5')\">\n                        <div ng-bind-html=\"'TAB5' | translate\"></div>\n                        <div class=\"menu_jauge\" mm-not-implemented>\n                            <div>rempli à 82%</div>\n                            <div class=\"jauge\">\n                                <div class=\"jauge_limit\" style=\"margin-left: 45%\"></div>\n                                <div class=\"jauge_current jauge_ok\" style=\"width: 82%\"></div>\n                                <div class=\"jauge_bg jauge_ok\"></div>\n                            </div>\n                        </div>\n                        <div class=\"menu_arrow\"></div>\n                    </div>\n\n                    <div class=\"{{ getMenuCurrentClass('/form6') }}\" ng-click=\"nav('/form6')\">\n                        <div ng-bind-html=\"'TAB6' | translate\"></div>\n                        <div class=\"menu_jauge\" mm-not-implemented>\n                            <div>rempli à 50%</div>\n                            <div class=\"jauge\">\n                                <div class=\"jauge_limit\" style=\"margin-left: 50%\"></div>\n                                <div class=\"jauge_current jauge_ok\" style=\"width: 50%\"></div>\n                                <div class=\"jauge_bg jauge_ok\"></div>\n                            </div>\n                        </div>\n                        <div class=\"menu_arrow\"></div>\n                    </div>\n\n                    <div class=\"{{ getMenuCurrentClass('/form7') }}\" ng-click=\"nav('/form7')\">\n                        <div ng-bind-html=\"'TAB7' | translate\"></div>\n                        <div class=\"menu_jauge\" mm-not-implemented>\n                            <div>rempli à 50%</div>\n                            <div class=\"jauge\">\n                                <div class=\"jauge_limit\" style=\"margin-left: 50%\"></div>\n                                <div class=\"jauge_current jauge_ok\" style=\"width: 50%\"></div>\n                                <div class=\"jauge_bg jauge_ok\"></div>\n                            </div>\n                        </div>\n                        <div class=\"menu_arrow\"></div>\n                    </div>\n                </div>\n            </div>\n            <div class=\"last_menu\">\n                <div class=\"{{ getMenuCurrentClass('/results') }}\" ng-click=\"nav('/results')\">\n                    résultats\n                </div>\n                <div mm-not-implemented>\n                    actions de réduction\n                </div>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"content\" ng-view>\n\n\n    </div>\n\n\n    <div class=\"footer\"></div>\n\n</ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-sub-title.html', "<div><div class=\"sub_title\"><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-section.html', "<ng-virtual><div class=\"element\"><div class=\"element_header\"><div class=\"title\" ng-bind-html=\"getTitleCode() | translate\"></div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\" ng-transclude></div></div></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-form7.html', "<ng-virtual><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Achat de biens et services</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div>Pièces documentaires liées aux transport & distribution, traitement, utilisation et fin de vie des produits vendus</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A51')\" ng-model=\"A('A51').value\"></select><div class=\"user_icon\">XM</div></div></div><div><div class=\"sub_title\">Lister les différents produits ou groupes de produits vendus par l'entreprise</div><div><div class=\"sub_title\">Transport</div><div class=\"sub_title\">Traitement</div><div class=\"sub_title\">Utilisation</div></div></div></div></div></div><div class=\"horizontal_separator\"></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-form4.html', "<mm-awac-section title-code=\"A243_TITLE\"><div ng-bind-html=\"'A244_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel1\" ng-question-set-code=\"'A244'\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A244')\"><mm-awac-string-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A245'\"></mm-awac-string-question><div ng-bind-html=\"'A273_TITLE' | translate\"></div><mm-awac-repetition-question ng-iteration=\"itLevel2\" ng-question-set-code=\"'A273'\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A273',itLevel1)\"><mm-awac-string-question ng-repetition-map=\"itLevel2\" ng-question-code=\"'A274'\"></mm-awac-string-question></mm-awac-repetition-question><button ng-click=\"addIteration('A273',itLevel1)\" type=\"button\">Add an iteration</button></mm-awac-repetition-question><button ng-click=\"addIteration('A244')\" type=\"button\">Add an iteration</button></mm-awac-section>");$templateCache.put('$/angular/templates/mm-awac-form3.html', "<ng-virtual><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Mobilité</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div>Pièces documentaires liées à la mobilité</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A51')\" ng-model=\"A('A51').value\"></select><div class=\"user_icon\">XM</div></div></div></div></div></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Transport routier</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport routier. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Calcul par les consommations</tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><div><div class=\"sub_title\">Véhicules de société ou détenus par l'entreprise</div></div><div><div class=\"sub_title\">Autres véhicules: déplacements domicile-travail des employés</div></div><div><div class=\"sub_title\">Autres véhicules: Déplacements professionnels & visiteurs</div></div></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Estimation par les kilomètres</tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><div><div class=\"sub_title\">Créez autant de catégories de véhicules que souhaité</div></div></div></div></tab></tabset></div></div><tab class=\"tab-color-yellow\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Estimation par euros dépensés</tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><div><div class=\"sub_title\">Créez autant de catégories de véhicules que souhaité</div></div></div></div></tab></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Transport en commun</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport en commun. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Estimation par le détail des déplacements</tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Estimation par nombre d'employés</tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"></div></div></tab></tabset></div></div></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Transport en avion (déplacements professionnels ou des visiteurs)</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport en avion. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Méthode par le détail des vols</tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Méthode des moyennes</tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"></div></div></tab></tabset></div></div></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-modal-field-text.html', "<tr><td>{{getInfo().fieldTitle}}<td><input ng-keyup=\"controlField()\" placeholder=\"{{getInfo().placeholder}}\" ng-model=\"getInfo().field\" type=\"{{getInfo().fieldType}}\"></td><td><img src=\"/awac/assets/images/field_valid.png\" ng-show=\"getInfo().isValid\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/awac/assets/images/field_invalid.png\"><div>{{getInfo().validationMessage}}</div></div></td></td></tr>");$templateCache.put('$/angular/templates/mm-awac-modal-login.html', "<!--Modal--><div class=\"modal\" id=\"modalLogin\" aria-labelledby=\"myModalLabel\" ng-enter=\"send()\" aria-hidden=\"true\" tabindex=\"-1\" role=\"dialog\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><!--button(type=\"button\",class=\"close\",data-dismiss=\"modal\")<span aria-hidden=\"true\">&times;<span</span><span class=\"sr-only\">Close</span>--><h4 id=\"myModalLabel\" class=\"modal-title\">Login</h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><!--button(type=\"button\",class=\"btn btn-default\",data-dismiss=\"modal\") Close--><button ng-disabled=\"!allFieldValid()\" ng-click=\"send();\" ng-enabled=\"allFieldValid()\" class=\"btn btn-primary\" type=\"button\">Login</button><button ng-disabled=\"!allFieldValid()\" ng-click=\"test();\" ng-enabled=\"allFieldValid()\" class=\"btn btn-primary\" type=\"button\">test</button></div><img src=\"/awac/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-select-question.html', "<div ng-hide=\"getCondition() === false\"><div ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></div><div class=\"oneelement\"><div class=\"status answer\" mm-not-implemented></div><select ng-options=\"p.key as p.label for p in getOptionsByQuestionCode()\" ng-model=\"getAnswerValue().value\"></select><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-form5.html', "<ng-virtual><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Déchets</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div>Pièces documentaires liées aux déchets</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A51')\" ng-model=\"A('A51').value\"></select><div class=\"user_icon\">XM</div></div></div></div></div></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Déchets générés par les opérations</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div class=\"sub_title\">Listez vos différents postes de déchets</div></div></div></div></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Eaux usées</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div class=\"sub_title\">Eaux usées domestiques par grand type de bâtiments</div><div><div class=\"sub_title\">Usine ou atelier</div><div class=\"sub_title\">Bureau</div><div class=\"sub_title\">Hôtel, pension, hôpitaux, prison</div><div class=\"sub_title\">Restaurant ou cantine</div></div></div><div><div class=\"sub_title\">Eaux usées industrielles</div></div><div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter les eaux usées industrielles. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Méthode par la quantité de m³ rejetés</tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><div></div></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i>Méthode par le poids de CO2 chimique des effluents rejetés</tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><div></div></div></div></tab></tabset></div></div></div></div></div></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-integer-question.html', "<div ng-hide=\"getCondition() === false\"><div ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></div><div class=\"oneelement\"><div class=\"status answer\" mm-not-implemented></div><input ng-model=\"getAnswerValue().value\" type=\"text\"><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-boolean-question.html', "<div ng-hide=\"getCondition() === false\"><div ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></div><div class=\"oneelement\"><div class=\"status answer\" mm-not-implemented></div><span style=\"width:75px;display:inline-block;text-align:right;\">oui</span><input style=\"width :20px !important\" name=\"{{getQuestionCode()}}\" value=\"1\" ng-model=\"getAnswerValue().value\" type=\"radio\"><span style=\"width:75px;display:inline-block;text-align:right;\">non</span><input style=\"width :20px !important\" name=\"{{getQuestionCode()}}\" value=\"0\" ng-model=\"getAnswerValue().value\" type=\"radio\"><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-login.html', "<div class=\"loginBackground\"><div class=\"loginFrame\" ng-enter=\"send()\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text></div><p style=\"background-color:#ff0000;color:#ffffff;padding:15px\" ng-show=\"errorMessage.length &gt; 0\">{{errorMessage}}</p><button ng-click=\"send()\" ng-bind-html=\"'LOGIN_BUTTON' | translate\" class=\"btn btn-primary\" type=\"button\"></button></div></div>");$templateCache.put('$/angular/templates/mm-awac-real-question.html', "<div ng-hide=\"getCondition() === false\"><div ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></div><div class=\"oneelement\"><div class=\"status answer\" mm-not-implemented></div><input ng-model=\"getAnswerValue().value\" type=\"text\"><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-repetition-question.html', "<div><div><div style=\"display : inline-block; margin-right : 20px\" ng-bind-html=\"getQuestionSetCode() + '_LOOPDESC' | translate\"></div><button ng-click=\"removeAnwser()\" type=\"button\">Remove</button><div><ng-virtual ng-transclude class=\"element_table\"></ng-virtual></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-real-with-unit-question.html', "<div ng-hide=\"getCondition() === false\"><div ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></div><div class=\"twoelement\"><div class=\"status answer\" mm-not-implemented></div><input ng-model=\"getAnswerValue().value\" type=\"text\"><select ng-options=\"p.id as p.name for p in getUnitsByQuestionCode()\" ng-model=\"getAnswerValue().unitId\"></select><div class=\"user_icon\" mm-not-implemented>XM</div></div></div>");$templateCache.put('$/angular/templates/mm-awac-form6.html', "<ng-virtual><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Achat de biens et services</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div>Pièces documentaires liées aux achats</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A51')\" ng-model=\"A('A51').value\"></select><div class=\"user_icon\">XM</div></div></div><div><div class=\"sub_title\">Détail des achats</div></div><div><div class=\"sub_title\">Créez et nommez vos postes d'achats (et préciser la catégorie et le type de matériaux ensuite)</div></div><div><div class=\"sub_title\">Autres matériaux spécifiques pour lesquels l'entreprise dispose du facteur d'émissions cradle-to-gate</div></div><div><div class=\"sub_title\">Créez et nommez vos postes d'achats spécifiques (et précisez ensuite la catégorie, le type de matériaux et le facteur d'émission cradle-to-gate spécifique)</div></div></div></div></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Infrastructures</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div>Pièces documentaires liées aux infrastructures</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A51')\" ng-model=\"A('A51').value\"></select><div class=\"user_icon\">XM</div></div></div><div><div class=\"sub_title\">Infrastructures (achetées durant l'année de déclaration)</div></div><div><div class=\"sub_title\">Créez et nommez vos postes d'infrastructure</div></div><div><div class=\"sub_title\">Autres infrastructures spécifiques pour lesquels l'entreprise dispose du facteur d'émission cradle-to-gate</div></div><div><div class=\"sub_title\">Créez et nommez vos postes d'infrastructure spécifiques</div></div></div></div></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Actifs loués (aval)</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div>Pièces documentaires liées aux actifs loués</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A51')\" ng-model=\"A('A51').value\"></select><div class=\"user_icon\">XM</div></div></div><div><div class=\"sub_title\">Créez autant de catégories d'actifs loués que nécessaire</div></div></div></div></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Franchises</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div>Pièces documentaires liées aux franchises</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A51')\" ng-model=\"A('A51').value\"></select><div class=\"user_icon\">XM</div></div></div><div><div class=\"sub_title\">Créez autant de catégories de franchisés que nécessaire</div></div></div></div></div><div class=\"horizontal_separator\"></div><div class=\"element\"><div class=\"element_header\"><div class=\"title\">Activités d'investissement</div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\"><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div class=\"element_table\"><div><div>Pièces documentaires liées aux activités d'investissement</div><div class=\"oneelement\"><div class=\"status answer\"></div><select ng-options=\"p.key as p.labelFr for p in O('A51')\" ng-model=\"A('A51').value\"></select><div class=\"user_icon\">XM</div></div></div><div><div class=\"sub_title\">Veuillez indiquer ici tous les projets dans lesquels votre entreprise investit</div></div></div></div></div></ng-virtual>");});