angular.module('app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'dangle']);
angular.module('app.filters', []);
angular.module('app.services', []);
angular.module('app.controllers', ['app.services', 'ngRoute', 'angular-flash.service', 'angular-flash.flash-alert-directive', 'angularFileUpload']);
angular.module('app', ['app.directives', 'app.filters', 'app.services', 'app.controllers']);
angular.module('app.controllers').config(function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: '$/angular/views/login.html',
    controller: 'LoginCtrl'
  }).when('/user_data/:period/:scope', {
    templateUrl: '$/angular/views/user_data.html',
    controller: 'UserDataCtrl'
  }).when('/user_manager/:period/:scope', {
    templateUrl: '$/angular/views/user_manager.html',
    controller: 'UserManagerCtrl'
  }).when('/site_manager/:period/:scope', {
    templateUrl: '$/angular/views/site_manager.html',
    controller: 'SiteManagerCtrl'
  }).when('/form1/:period/:scope', {
    templateUrl: '$/angular/views/form1.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB1';
      },
      displayFormMenu: function() {
        return true;
      }
    }
  }).when('/form2/:period/:scope', {
    templateUrl: '$/angular/views/form2.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB2';
      },
      displayFormMenu: function() {
        return true;
      }
    }
  }).when('/form3/:period/:scope', {
    templateUrl: '$/angular/views/form3.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB3';
      },
      displayFormMenu: function() {
        return true;
      }
    }
  }).when('/form4/:period/:scope', {
    templateUrl: '$/angular/views/form4.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB4';
      },
      displayFormMenu: function() {
        return true;
      }
    }
  }).when('/form5/:period/:scope', {
    templateUrl: '$/angular/views/form5.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB5';
      },
      displayFormMenu: function() {
        return true;
      }
    }
  }).when('/form6/:period/:scope', {
    templateUrl: '$/angular/views/form6.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB6';
      },
      displayFormMenu: function() {
        return true;
      }
    }
  }).when('/form7/:period/:scope', {
    templateUrl: '$/angular/views/form7.html',
    controller: 'FormCtrl',
    resolve: {
      formIdentifier: function() {
        return 'TAB7';
      },
      displayFormMenu: function() {
        return true;
      }
    }
  }).when('/results/:period/:scope', {
    templateUrl: '$/angular/views/results.html',
    controller: 'ResultsCtrl',
    resolve: {
      displayFormMenu: function() {
        return true;
      }
    }
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
});angular.module('app.services').service("messageFlash", function(flash) {
  this.displaySuccess = function(message) {
    return flash.success = message.replace("\n", '<br />');
  };
  this.displayError = function(message) {
    return flash.error = message.replace(/\n/g, '<br />');
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
});angular.module('app.services').service("modalService", function($rootScope) {
  this.LOADING = 'LOADING';
  this.DOCUMENT_MANAGER = 'DOCUMENT_MANAGER';
  this.CONFIRMATION_EXIT_FORM = 'CONFIRMATION_EXIT_FORM';
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
      ngDataToCompare: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-string-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      return scope.$watch('getAnswer().value', function(o, n) {
        if ("" + n !== "" + o) {
          return scope.$parent.edited();
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacDocumentQuestion", function(directiveService, translationService, $upload, messageFlash, modalService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-document-question.html",
    replace: true,
    compile: function() {
      return {
        pre: function(scope) {
          directiveService.autoScopeImpl(scope);
          scope.getQuestionCode = function() {
            return scope.$parent.getQuestionCode();
          };
          scope.getAnswer = function() {
            return scope.$parent.getAnswer(scope.getDataToCompare());
          };
          scope.$watch('getAnswer().value', function(o, n) {
            if ("" + n !== "" + o) {
              return scope.$parent.edited();
            }
          });
          scope.inDownload = false;
          scope.percent = 0;
          scope.$watch('percent', function() {
            var _ref;
            return scope.style = {
              "width": scope.percent + "%",
              "color": (_ref = scope.percent > 50) != null ? _ref : {
                "white": "black"
              }
            };
          });
          scope.openDocumentManager = function() {
            var args;
            if (scope.getAnswer() !== null) {
              args = {};
              args['listDocuments'] = scope.getAnswer().value;
              return modalService.show(modalService.DOCUMENT_MANAGER, args);
            }
          };
          scope.getFileNumber = function() {
            if (scope.getAnswer() === null || scope.getAnswer().value === null || scope.getAnswer().value === void 0) {
              return 0;
            }
            return Object.keys(scope.getAnswer().value).length;
          };
          return scope.onFileSelect = function($files) {
            var file, i;
            scope.inDownload = true;
            i = 0;
            while (i < $files.length) {
              file = $files[i];
              scope.upload = $upload.upload({
                url: "file/upload/",
                data: {
                  myObj: scope.myModelObj
                },
                file: file
              }).progress(function(evt) {
                scope.percent = parseInt(100.0 * evt.loaded / evt.total);
                console.log("percent: " + scope.percent);
                return;
              }).success(function(data, status, headers, config) {
                var fileName;
                scope.percent = 0;
                scope.inDownload = false;
                fileName = "??";
                messageFlash.displaySuccess("The file " + fileName + " was upload successfully");
                console.log(data);
                if (scope.getAnswer().value === null || scope.getAnswer().value === void 0) {
                  scope.getAnswer().value = {};
                }
                scope.getAnswer().value[data.id] = data.name;
                console.log("AnswerValue : ");
                console.log(scope.getAnswer());
                return;
              }).error(function(data, status, headers, config) {
                scope.percent = 0;
                scope.inDownload = false;
                messageFlash.displayError("The upload of the file was faild");
                return;
              });
              i++;
            }
            return;
          };
        }
      };
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
});angular.module('app.directives').directive("mmAwacRepetitionName", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-repetition-name.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
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
});angular.module('app.directives').directive("percent", function($filter) {
  var f, p;
  p = function(viewValue) {
    var m;
    m = viewValue.match(/^(\d+)\/(\d+)/);
    if (m != null) {
      return $filter("number")(parseInt(m[1]) / parseInt(m[2]), 2);
    }
    return $filter("number")(parseFloat(viewValue) / 100, 2);
  };
  f = function(modelValue) {
    return $filter("number")(parseFloat(modelValue) * 100, 2);
  };
  return {
    require: "ngModel",
    link: function(scope, ele, attr, ctrl) {
      ctrl.$parsers.unshift(p);
      ctrl.$formatters.unshift(f);
      return;
    }
  };
});angular.module('app.directives').directive("mmAwacRepetitionQuestionDisabled", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngIteration: '=',
      ngRepetitionMap: '=',
      ngCondition: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-repetition-question-disabled.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getQuestionSet = function() {
        return scope.$parent.getQuestionSet(scope.getQuestionCode());
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      scope.removeAnwser = function() {
        return scope.$parent.removeIteration(scope.getQuestionCode(), scope.getIteration(), scope.getRepetitionMap());
      };
      scope.$watch('ngCondition', function() {
        return scope.$root.$broadcast('CONDITION');
      });
      scope.addIteration = function() {
        return scope.$parent.addIteration(scope.getQuestionCode(), scope.getRepetitionMap());
      };
      return scope.getRepetitionMapByQuestionSet = function() {
        return scope.$parent.getRepetitionMapByQuestionSet(scope.getQuestionCode(), scope.getRepetitionMap());
      };
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
      ngDataToCompare: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-percentage-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      return scope.$watch('getAnswer().value', function(o, n) {
        if ("" + n !== "" + o) {
          return scope.$parent.edited();
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
});angular.module('app.directives').directive("mmAwacQuestion", function(directiveService, translationService, $compile, $timeout) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngRepetitionMap: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-question.html",
    replace: true,
    compile: function() {
      return {
        post: function(scope, element) {
          directiveService.autoScopeImpl(scope);
          scope.$watch('$parent.o', function() {
            scope.getTemplate(true);
            return scope.getTemplate(false);
          });
          scope.getTemplate = function(dataToCompare) {
            var answerType, directive, directiveName, toCompare;
            if ($('.inject-data:first', element).html() === '') {
              toCompare = "";
              directiveName = "";
              if (dataToCompare === true) {
                toCompare = "true";
              } else {
                toCompare = "false";
              }
              if (scope.getQuestion() !== null) {
                answerType = scope.getQuestion().answerType;
                if (answerType === 'BOOLEAN') {
                  directiveName = "boolean-question";
                } else if (answerType === 'INTEGER') {
                  directiveName = "integer-question";
                } else if (answerType === 'DOUBLE') {
                  if (scope.getQuestion().unitCategoryId !== null || scope.getQuestion().unitCategoryId !== void 0) {
                    directiveName = "real-with-unit-question";
                  } else {
                    directiveName = "real-question";
                  }
                } else if (answerType === 'PERCENTAGE') {
                  directiveName = "percentage-question";
                } else if (answerType === 'STRING') {
                  directiveName = "string-question";
                } else if (answerType === 'VALUE_SELECTION') {
                  directiveName = "select-question";
                } else if (answerType === 'DOCUMENT') {
                  directiveName = "document-question";
                }
                directive = $compile("<mm-awac-" + directiveName + " ng-data-to-compare=\"" + toCompare + "\"></mm-awac-" + directiveName + ">")(scope);
                if (dataToCompare === true) {
                  return $('.inject-data-to-compare:first', element).append(directive);
                } else {
                  return $('.inject-data:first', element).append(directive);
                }
              }
            }
          };
          scope.getQuestion = function() {
            return scope.$parent.getQuestion(scope.getQuestionCode());
          };
          scope.hasDescription = function() {
            return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
          };
          scope.getAnswer = function(forDataToCompare) {
            if (forDataToCompare == null) {
              forDataToCompare = false;
            }
            if (forDataToCompare) {
              return scope.$parent.getAnswerToCompare(scope.getQuestionCode(), scope.getRepetitionMap());
            } else {
              return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap());
            }
          };
          scope.getUnitCategories = function() {
            return scope.$parent.getUnitCategories(scope.getQuestionCode());
          };
          scope.getCodeList = function() {
            return scope.$parent.getCodeList(scope.getQuestionCode());
          };
          scope.displayOldDatas = function() {
            if (scope.$parent.dataToCompare === null) {
              return false;
            }
            return true;
          };
          scope.edited = function() {
            if (scope.getAnswer().value !== null) {
              if (scope.getAnswer().value.length === 0) {
                scope.getAnswer().value = null;
              }
            }
            scope.getAnswer().wasEdited = true;
            return scope.getAnswer().lastUpdateUser = scope.$root.currentPerson.identifier;
          };
          scope.$watch('ngCondition', function(n, o) {
            if (n !== o) {
              return scope.$root.$broadcast('CONDITION');
            }
          });
          scope.$on('CONDITION', function(event, args) {
            return scope.testVisibility(element);
          });
          scope.firstComputecondition = true;
          scope.testVisibility = function(elementToTest) {
            if (elementToTest.hasClass('condition-false') === true || ((scope.getCondition() != null) && scope.getCondition() === false)) {
              if (scope.getAnswer().hasValidCondition !== false) {
                scope.getAnswer().hasValidCondition = false;
                if (scope.getAnswer().value !== null) {
                  scope.getAnswer().value = null;
                  if (scope.$parent.loading === false) {
                    scope.getAnswer().wasEdited = true;
                    scope.getAnswer().lastUpdateUser = scope.$root.currentPerson.identifier;
                    scope.$root.$broadcast('CONDITION');
                  } else {
                    scope.getAnswer().wasEdited = false;
                  }
                }
              }
              return false;
            }
            if (elementToTest.parent()[0].tagName !== 'BODY') {
              return scope.testVisibility(elementToTest.parent());
            } else {
              if (scope.getAnswer().hasValidCondition !== true) {
                scope.getAnswer().hasValidCondition = true;
                if (scope.getQuestion() !== null && scope.getQuestion().defaultValue !== null && scope.getAnswer().value === null) {
                  scope.getAnswer().value = scope.getQuestion().defaultValue;
                  if (scope.$parent.loading === false) {
                    scope.getAnswer().wasEdited = true;
                    scope.getAnswer().lastUpdateUser = scope.$root.currentPerson.identifier;
                  }
                }
              }
              return true;
            }
          };
          scope.getUserName = function(forDataToCompare, initialOnly) {
            var initial, name, user;
            user = null;
            if (forDataToCompare === true) {
              if (scope.displayOldDatas() && scope.getAnswer(true) !== null) {
                user = scope.$root.getUserByIdentifier(scope.getAnswer(true).lastUpdateUser);
              }
            } else {
              if (scope.getAnswer() !== null) {
                user = scope.$root.getUserByIdentifier(scope.getAnswer().lastUpdateUser);
              }
            }
            if (user === null) {
              return "";
            } else if (initialOnly) {
              initial = user.firstName.substring(0, 1) + user.lastName.substring(0, 1);
              return initial;
            } else {
              name = user.firstName + " " + user.lastName;
              return name;
            }
          };
          scope.getStatusClass = function() {
            var answer;
            if (scope.getQuestion() !== null && scope.getQuestion().answerType === 'DOCUMENT') {
              return "";
            }
            answer = scope.getAnswer();
            if (answer.value !== null) {
              if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
                return 'answer_temp';
              }
              return 'answer';
            } else {
              if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
                return 'pending_temp';
              }
              return 'pending';
            }
          };
          scope.copyDataToCompare = function() {
            if (scope.getAnswer(true) !== null) {
              scope.getAnswer().value = scope.getAnswer(true).value;
              if (scope.getAnswer(true).unitId != null) {
                scope.getAnswer().unitId = scope.getAnswer(true).unitId;
              }
              return scope.getAnswer().wasEdited = true;
            }
          };
          scope.logQuestionCode = function() {
            return console.log(scope.getQuestionCode() + ",value:" + scope.getAnswer().value + ",wasEdited:" + scope.getAnswer().wasEdited);
          };
          scope.errorMessage = "";
          return scope.setErrorMessage = function(errorMessage) {
            scope.errorMessage = errorMessage;
            if (scope.lastTimeOut != null) {
              $timeout.cancel(scope.lastTimeOut);
            }
            return scope.lastTimeOut = $timeout(function() {
              scope.errorMessage = "";
              return scope.lastTimeOut = null;
            }, 2000);
          };
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacBlock", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngCondition: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-block.html",
    replace: true,
    transclude: true,
    link: function(scope, element) {
      directiveService.autoScopeImpl(scope);
      return scope.$watch('ngCondition', function(n, o) {
        if (n !== o) {
          return scope.$root.$broadcast('CONDITION');
        }
      });
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
      ngDataToCompare: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-select-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      scope.$watch('getAnswer().value', function(o, n) {
        if ("" + n !== "" + o) {
          return scope.$parent.edited();
        }
      });
      return scope.getOptions = function() {
        var codeList;
        codeList = scope.$parent.getCodeList();
        if (codeList === null) {
          return null;
        }
        return codeList.codeLabels;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacIntegerQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-integer-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      return scope.$watch('getAnswer().value', function(o, n) {
        if ("" + n !== "" + o) {
          return scope.$parent.edited();
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacTabProgressBar", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngValue: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-tab-progress-bar.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.pg = null;
      scope.color = null;
      return scope.$watch('value', function(v) {
        if (v < 0) {
          v = 0;
        }
        if (v > 100) {
          v = 100;
        }
        if (v >= 66) {
          scope.color = 'green';
        } else {
          if (v >= 33) {
            scope.color = 'orange';
          } else {
            scope.color = 'red';
          }
        }
        return scope.pg = v;
      });
    }
  };
});angular.module('app.directives').directive("mmAwacBooleanQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-boolean-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      return scope.$watch('getAnswer().value', function(o, n) {
        if ("" + n !== "" + o) {
          return scope.$parent.edited();
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacModalDocumentManager", function(directiveService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-modal-document-manager.html",
    controller: function($scope, modalService, $http, $location, $window) {
      var modalName;
      $('#modalDocumentManager').modal({
        backdrop: false
      });
      $('#modalDocumentManager').modal('show');
      modalName = modalService.DOCUMENT_MANAGER;
      $scope.show = false;
      $scope.loc = null;
      $scope.listDocuments = [];
      $scope.$on('SHOW_MODAL_' + modalName, function(event, args) {
        if (args.show) {
          $scope.display();
        } else {
          $scope.close();
        }
        console.log("args");
        console.log(args);
        $scope.listDocuments = args.params['listDocuments'];
        console.log("listDocuments");
        return console.log($scope.listDocuments);
      });
      $scope.display = function() {
        return $scope.show = true;
      };
      $scope.close = function() {
        $scope.show = false;
        return modalService.hide("SHOW_MODAL_" + modalName);
      };
      $scope.download = function(storedFileId) {
        var url;
        url = $location.absUrl().replace(/#.*$/, "") + 'file/download/' + storedFileId;
        return $window.open(url);
      };
      return $scope.removeDoc = function(storedFileId) {};
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("numbersOnly", function(translationService) {
  return {
    restrict: 'A',
    require: "ngModel",
    link: function(scope, element, attrs, modelCtrl) {
      return modelCtrl.$parsers.push(function(inputValue) {
        var transformedInput;
        if (inputValue === undefined) {
          return "";
        }
        scope.errorMessage = "";
        if (attrs.numbersOnly === "integer") {
          scope.regex = /[^0-9]/g;
          scope.errorMessage = translationService.get('FIELD_INTEGER_ERROR_MESSAGE');
        } else {
          scope.regex = /[^0-9,. ]/g;
          scope.errorMessage = translationService.get('FIELD_DECIMAL_ERROR_MESSAGE');
        }
        if (inputValue != null) {
          transformedInput = inputValue.replace(scope.regex, "");
          if (transformedInput !== inputValue) {
            if ((scope.$parent != null) && (scope.$parent.setErrorMessage != null)) {
              scope.$parent.setErrorMessage(scope.errorMessage);
            }
            if (transformedInput === "") {
              transformedInput = null;
            }
            modelCtrl.$setViewValue(transformedInput);
            modelCtrl.$render();
          }
          return transformedInput;
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacRealQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-real-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      return scope.$watch('getAnswer().value', function(o, n) {
        if ("" + n !== "" + o) {
          return scope.$parent.edited();
        }
      });
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
      return scope.$watch('ngCondition', function() {
        return scope.$root.$broadcast('CONDITION');
      });
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
      modalName = modalService.LOADING;
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
      ngDataToCompare: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-real-with-unit-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      scope.$watch('getAnswer().value', function(o, n) {
        if ("" + n !== "" + o) {
          return scope.$parent.edited();
        }
      });
      scope.$watch('getAnswer().unitId', function(o, n) {
        if ("" + n !== "" + o) {
          return scope.$parent.edited();
        }
      });
      return scope.getUnits = function() {
        var unitCategory;
        unitCategory = scope.$parent.getUnitCategories();
        if (unitCategory === null) {
          return null;
        }
        return unitCategory.units;
      };
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
      modalName = modalService.CONFIRMATION_EXIT_FORM;
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
        var arg;
        arg = {};
        arg.loc = $scope.loc;
        arg.confirmed = true;
        $scope.$root.$broadcast('SAVE_AND_NAV', arg);
        return $scope.close();
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
});angular.module('app.controllers').controller("ResultsCtrl", function($scope, downloadService, $http, displayFormMenu) {
  $scope.displayFormMenu = displayFormMenu;
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
    if ($scope.scopeId !== null && $scope.scopeId !== void 0 && $scope.scopeId !== NaN && $scope.scopeId !== 'NaN') {
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
          if (period.id !== $routeParams.period) {
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
});angular.module('app.controllers').controller("UserManagerCtrl", function($scope, translationService) {
  return $scope.title = translationService.get('USER_MANAGER_TITLE');
});angular.module('app.controllers').controller("SiteManagerCtrl", function($scope, translationService) {});angular.module('app.controllers').controller("UserDataCtrl", function($scope) {});angular.module('app.controllers').controller("FormCtrl", function($scope, downloadService, $http, messageFlash, modalService, formIdentifier, $timeout, displayFormMenu) {
  $scope.formIdentifier = formIdentifier;
  $scope.displayFormMenu = displayFormMenu;
  $scope.dataToCompare = null;
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
  modalService.show(modalService.LOADING);
  downloadService.getJson("answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, function(data) {
    var answerSave, args, qSet, questionSetDTO, _i, _j, _len, _len2, _ref, _ref2;
    console.log("data");
    console.log(data);
    $scope.o = angular.copy(data);
    if ($scope.o.answersSave !== null && $scope.o.answersSave !== void 0) {
      answerSave = $scope.o.answersSave;
      $scope.answerList = answerSave.listAnswers;
    }
    $scope.loopRepetition = function(questionSetDTO, listQuestionSetRepetition) {
      var answer, child, _i, _j, _len, _len2, _ref, _ref2, _results;
      if (listQuestionSetRepetition == null) {
        listQuestionSetRepetition = [];
      }
      if (questionSetDTO.repetitionAllowed === true) {
        listQuestionSetRepetition[listQuestionSetRepetition.length] = questionSetDTO.code;
        _ref = $scope.answerList;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          answer = _ref[_i];
          if (answer.mapRepetition !== null && answer.mapRepetition !== void 0) {
            if (answer.mapRepetition[questionSetDTO.code] !== null && answer.mapRepetition[questionSetDTO.code] !== void 0 && answer.mapRepetition[questionSetDTO.code] !== 0) {
              $scope.addMapRepetition(questionSetDTO.code, answer.mapRepetition, listQuestionSetRepetition);
            }
          }
        }
      }
      if (questionSetDTO.children) {
        _ref2 = questionSetDTO.children;
        _results = [];
        for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
          child = _ref2[_j];
          _results.push($scope.loopRepetition(child, angular.copy(listQuestionSetRepetition)));
        }
        return _results;
      }
    };
    $scope.addDefaultValue = function(questionSetDTO) {
      var answer, child, question, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3, _results;
      _ref = questionSetDTO.questions;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        question = _ref[_i];
        if (question.defaultValue !== void 0 && question.defaultValue !== null) {
          _ref2 = $scope.answerList;
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            answer = _ref2[_j];
            if (answer.questionKey === question.code && answer.value === null) {
              answer.value = question.defaultValue;
            }
          }
        }
      }
      _ref3 = questionSetDTO.children;
      _results = [];
      for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
        child = _ref3[_k];
        _results.push($scope.addDefaultValue(child));
      }
      return _results;
    };
    args = {};
    args.time = $scope.o.answersSave.lastUpdateDate;
    $scope.$root.$broadcast("REFRESH_LAST_SAVE_TIME", args);
    _ref = $scope.o.questionSets;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      qSet = _ref[_i];
      $scope.loopRepetition(qSet);
    }
    console.log("$scope.mapRepetition");
    console.log($scope.mapRepetition);
    _ref2 = $scope.o.questionSets;
    for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
      questionSetDTO = _ref2[_j];
      $scope.addDefaultValue(questionSetDTO);
    }
    $timeout(function() {
      /*
      console.log "COMPUTE COND START -------------------------"
      $scope.$root.$broadcast('CONDITION')
      console.log "COMPUTE COND END -------------------------"
      */      modalService.hide(modalService.LOADING);
      $scope.loading = false;
      return console.log($scope.answerList);
    }, 0);
    return;
  });
  $scope.$on('SAVE_AND_NAV', function(event, args) {
    return $scope.save(args);
  });
  $scope.$on('SAVE', function() {
    return $scope.save();
  });
  $scope.save = function(argToNav) {
    var answer, listAnswerToSave, promise, _i, _len, _ref;
    if (argToNav == null) {
      argToNav = null;
    }
    modalService.show(modalService.LOADING);
    listAnswerToSave = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
        if (answer.hasValidCondition !== void 0 && answer.hasValidCondition === false) {
          answer.value = null;
        }
        listAnswerToSave[listAnswerToSave.length] = answer;
      }
    }
    console.log("listAnswerToSave");
    console.log(listAnswerToSave);
    if (listAnswerToSave.length === 0) {
      messageFlash.displaySuccess("All answers are already saved !");
      return modalService.hide(modalService.LOADING);
    } else {
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
        var answer, _i, _len, _ref;
        messageFlash.displaySuccess("Your answers are saved !");
        modalService.hide(modalService.LOADING);
        _ref = $scope.answerList;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          answer = _ref[_i];
          if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
            answer.wasEdited = false;
          }
        }
        $scope.saveFormProgress();
        if (argToNav !== null) {
          $scope.$root.$broadcast('NAV', argToNav);
        }
        return;
      });
      return promise.error(function(data, status, headers, config) {
        messageFlash.displayError("An error was thrown during the save : " + data.message);
        modalService.hide(modalService.LOADING);
        return;
      });
    }
  };
  $scope.getUnitCategories = function(code) {
    var question;
    if ($scope.getQuestion(code) === null) {
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
      listQuestionSets = null;
    }
    if (listQuestionSets === null) {
      if ($scope.o === null || $scope.o === void 0 || $scope.o.questionSets === null) {
        return null;
      }
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
  $scope.getAnswerToCompare = function(code, mapIteration) {
    var answer, _i, _len, _ref;
    if ($scope.dataToCompare !== null) {
      _ref = $scope.dataToCompare.answersSave.listAnswers;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        answer = _ref[_i];
        if (answer.questionKey === code) {
          if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
            return answer;
          }
        }
      }
    }
    return null;
  };
  $scope.getAnswerOrCreate = function(code, mapIteration) {
    var answerLine, defaultUnitId, question, result, value, wasEdited;
    if (mapIteration == null) {
      mapIteration = null;
    }
    if (code === null || code === void 0) {
      console.log("ERROR !! getAnswerOrCreate : code is null or undefined");
      return null;
    }
    result = $scope.getAnswer(code, mapIteration);
    if (result) {
      return result;
    } else {
      value = null;
      defaultUnitId = null;
      wasEdited = false;
      if ($scope.getQuestion(code) !== null) {
        question = $scope.getQuestion(code);
        if (question.defaultValue !== null) {
          value = question.defaultValue;
          wasEdited = true;
        }
        if (question.unitCategoryId !== null && question.unitCategoryId !== void 0) {
          defaultUnitId = $scope.getUnitCategories(code).mainUnitId;
        }
      }
      answerLine = {
        'questionKey': code,
        'value': value,
        'unitId': defaultUnitId,
        'mapRepetition': mapIteration,
        'lastUpdateUser': $scope.$root.currentPerson.identifier,
        'wasEdited': wasEdited
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
    if (mapRepetition != null) {
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
      if ((question.mapRepetition != null) && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)) {
        if (question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] === iterationToDelete[questionSetCode]) {
          if ($scope.answerList[len].value !== null) {
            $scope.answerList[len].value = null;
            $scope.answerList[len].wasEdited = true;
          }
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
  $scope.compareRepetitionMap = function(mapContainer, mapContained) {
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
  $scope.addMapRepetition = function(questionSetCode, mapRepetitionSource, listQuestionSetRepetition) {
    var founded, key, mapRepetitionToAdd, questionCode, repetition, _i, _j, _k, _len, _len2, _len3, _ref, _ref2;
    mapRepetitionToAdd = {};
    _ref = Object.keys(mapRepetitionSource);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        for (_j = 0, _len2 = listQuestionSetRepetition.length; _j < _len2; _j++) {
          questionCode = listQuestionSetRepetition[_j];
          if (questionCode === key) {
            mapRepetitionToAdd[key] = mapRepetitionSource[key];
          }
        }
      }
    }
    if ($scope.mapRepetition[questionSetCode]) {
      founded = false;
      _ref2 = $scope.mapRepetition[questionSetCode];
      for (_k = 0, _len3 = _ref2.length; _k < _len3; _k++) {
        repetition = _ref2[_k];
        if ($scope.compareRepetitionMap(repetition, mapRepetitionToAdd)) {
          founded = true;
        }
      }
      if (founded === false) {
        return $scope.mapRepetition[questionSetCode][$scope.mapRepetition[questionSetCode].length] = angular.copy(mapRepetitionToAdd);
      }
    } else {
      $scope.mapRepetition[questionSetCode] = [];
      return $scope.mapRepetition[questionSetCode][0] = angular.copy(mapRepetitionToAdd);
    }
  };
  $scope.validNavigation = function() {
    var answer, result, _i, _len, _ref;
    result = {};
    result.modalForConfirm = modalService.CONFIRMATION_EXIT_FORM;
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
        result.valid = false;
        break;
      }
    }
    return result;
  };
  $scope.$parent.$watch('periodToCompare', function() {
    var promise;
    if ($scope.$parent !== null && $scope.$parent.periodToCompare !== 'default') {
      promise = $http({
        method: "GET",
        url: 'answer/getByForm/' + $scope.formIdentifier + "/" + $scope.$parent.periodToCompare + "/" + $scope.$parent.scopeId,
        headers: {
          "Content-Type": "application/json"
        }
      });
      promise.success(function(data, status, headers, config) {
        $scope.dataToCompare = data;
        return;
      });
      return promise.error(function(data, status, headers, config) {
        return;
      });
    } else {
      return $scope.dataToCompare = null;
    }
  });
  return $scope.saveFormProgress = function() {
    var answer, answered, formProgress, formProgressDTO, founded, listTotal, percentage, promise, total, _i, _j, _len, _len2, _ref, _ref2;
    percentage = 0;
    total = 0;
    answered = 0;
    listTotal = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if ($scope.getQuestion(answer.questionKey).answerType !== 'DOCUMENT') {
        if (answer.hasValidCondition === void 0 || answer.hasValidCondition === null || answer.hasValidCondition === true) {
          total++;
          listTotal[listTotal.length] = answer;
          if (answer.value !== null) {
            answered++;
          }
        }
      }
    }
    percentage = answered / total * 100;
    percentage = Math.floor(percentage);
    console.log("PROGRESS : " + answered + "/" + total + "=" + percentage);
    console.log(listTotal);
    formProgressDTO = {};
    formProgressDTO.form = $scope.formIdentifier;
    formProgressDTO.period = $scope.$parent.period;
    formProgressDTO.scope = $scope.$parent.scopeId;
    formProgressDTO.percentage = percentage;
    founded = false;
    _ref2 = $scope.$parent.formProgress;
    for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
      formProgress = _ref2[_j];
      console.log(formProgress.form + "-" + $scope.formIdentifier);
      if (formProgress.form === $scope.formIdentifier) {
        founded = true;
        formProgress.percentage = percentage;
      }
    }
    if (founded === false) {
      $scope.$parent.formProgress[$scope.$parent.formProgress.length] = formProgressDTO;
    }
    promise = $http({
      method: "POST",
      url: 'answer/formProgress',
      headers: {
        "Content-Type": "application/json"
      },
      data: formProgressDTO
    });
    return promise.success(function(data, status, headers, config) {
      return;
    });
  };
});angular.module('app.directives').run(function($templateCache) {$templateCache.put('$/angular/views/form2.html', "<mm-awac-section title-code=\"A13_TITLE\"><mm-awac-question question-code=\"A14\"></mm-awac-question><mm-awac-name question-code=\"A15\"></mm-awac-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A15\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A15')\"><mm-awac-question question-code=\"A16\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A17\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A15')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A15_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A20_TITLE\"><mm-awac-question question-code=\"A21\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A22\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A23\"></mm-awac-question><mm-awac-question question-code=\"A24\"></mm-awac-question><mm-awac-repetition-name question-code=\"A25\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A25\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A25')\"><mm-awac-question question-code=\"A26\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A27\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A28\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A25')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A25_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A31_TITLE\"><mm-awac-question question-code=\"A32\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A32').value == '1'\" question-code=\"A33\"></mm-awac-question><mm-awac-block ng-condition=\"getAnswer('A32').value == '1'\"><mm-awac-repetition-name question-code=\"A34\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A34\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A34')\"><mm-awac-question question-code=\"A35\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A36\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A34')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A34_LOOPDESC' | translate\"></span></button></mm-awac-block></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A37_TITLE\"><mm-awac-question question-code=\"A38\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A38').value == '1'\" question-code=\"A39\"></mm-awac-question></mm-awac-section><mm-awac-block ng-condition=\"getAnswer('A38').value == '1'\"><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter l’usage des systèmes froids. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A41_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A42\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A42\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A42')\"><mm-awac-question question-code=\"A43\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A44\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A42')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A42_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A45_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A46\"></mm-awac-question></div></div></tab><mm-awac-block ng-condition=\"getAnswer('A5').value == '1' || getAnswer('A5').value == '2'\"><tab class=\"tab-color-yellow\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A47_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><mm-awac-block class=\"element_table\" ng-condition=\"getAnswer('A5').value == '1' || getAnswer('A5').value == '2'\"><mm-awac-question question-code=\"A48\"></mm-awac-question><mm-awac-question question-code=\"A49\"></mm-awac-question></mm-awac-block></div></tab></mm-awac-block></tabset></div></div></mm-awac-block>");$templateCache.put('$/angular/views/form4.html', "<mm-awac-section title-code=\"A128_TITLE\"><mm-awac-question question-code=\"A129\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A130_TITLE\"><mm-awac-sub-title question-code=\"A131\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"A132\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A133\"></mm-awac-question><mm-awac-question question-code=\"A134\"></mm-awac-question><mm-awac-question question-code=\"A135\"></mm-awac-question><mm-awac-question question-code=\"A136\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A136').value == '1'\" question-code=\"A137\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A136').value == '1'\" question-code=\"A138\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A138').value == '1'\" question-code=\"A139\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A138').value == '0'\" question-code=\"A500\"></mm-awac-question><mm-awac-sub-title question-code=\"A140\"></mm-awac-sub-title><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport effectué par des transporteurs. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A141_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A142\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A142\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A142')\"><mm-awac-question question-code=\"A143\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A145\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A146\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A147\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A148\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A149\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A150\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A151\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A152\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A153\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A154\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A155\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A156\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A142')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A142_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A157_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A158\"></mm-awac-question><mm-awac-question question-code=\"A159\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A159').value == '3'\" question-code=\"A160\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A159').value == '2'\" question-code=\"A161\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A159').value == '1'\" question-code=\"A162\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A163_TITLE\"><mm-awac-repetition-name question-code=\"A164\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A164\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A164')\"><mm-awac-question question-code=\"A165\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A166\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A166\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A166',itLevel1)\"><mm-awac-question question-code=\"A167\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A168\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A166',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A166_LOOPDESC' | translate\"></span></button><mm-awac-question question-code=\"A169\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A170\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A170\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A170',itLevel1)\"><mm-awac-question question-code=\"A171\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A172\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A170',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A170_LOOPDESC' | translate\"></span></button></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A164')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A164_LOOPDESC' | translate\"></span></button></mm-awac-section>");$templateCache.put('$/angular/views/form6.html', "<ng-virtual><mm-awac-section title-code=\"A205_TITLE\"><mm-awac-question question-code=\"A206\"></mm-awac-question><mm-awac-sub-title question-code=\"A208\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"A209\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A209\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A209')\"><mm-awac-question question-code=\"A210\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A211\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == '1'\" question-code=\"A212\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == '2'\" question-code=\"A213\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == '3'\" question-code=\"A214\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == '4'\" question-code=\"A215\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == '5'\" question-code=\"A216\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == '6'\" question-code=\"A217\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == '8'\" question-code=\"A218\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == '7'\" question-code=\"A219\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == '1' || getAnswer('A211',itLevel1).value == '2' || getAnswer('A211',itLevel1).value == '3' || getAnswer('A211',itLevel1).value == '4'\" question-code=\"A220\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value != '7'\" question-code=\"A221\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == '7'\" question-code=\"A222\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A209')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A209_LOOPDESC' | translate\"></span></button><mm-awac-sub-title question-code=\"A223\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"A224\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A224\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A224')\"><mm-awac-question question-code=\"A225\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A226\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A227\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A228\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A224')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A224_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A229_TITLE\"><mm-awac-question question-code=\"A230\"></mm-awac-question><mm-awac-repetition-name question-code=\"A231\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A231\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A231')\"><mm-awac-question question-code=\"A232\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A233\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A233',itLevel1).value &lt; '18' || getAnswer('A233',itLevel1).value == '23'\" question-code=\"A234\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A233',itLevel1).value == '20' || getAnswer('A233',itLevel1).value == '21'|| getAnswer('A233',itLevel1).value == '22'\" question-code=\"A235\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A233',itLevel1).value == '18' || getAnswer('A233',itLevel1).value == '19'\" question-code=\"A236\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A231')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A231_LOOPDESC' | translate\"></span></button><mm-awac-sub-sub-title question-code=\"A237\"></mm-awac-sub-sub-title><mm-awac-repetition-name question-code=\"A238\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A238\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A238')\"><mm-awac-question question-code=\"A239\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A240\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A241\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A242\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A238')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A238_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A309_TITLE\"><mm-awac-question question-code=\"A310\"></mm-awac-question><mm-awac-repetition-name question-code=\"A311\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A311\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A311')\"><mm-awac-question question-code=\"A312\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A313\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A313\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A313',itLevel1)\"><mm-awac-question question-code=\"A314\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A315\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A313',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A313_LOOPDESC' | translate\"></span></button><mm-awac-question question-code=\"A316\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A317\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A317\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A317',itLevel1)\"><mm-awac-question question-code=\"A318\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A319\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A317',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A317_LOOPDESC' | translate\"></span></button></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A311')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A311_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A320_TITLE\"><mm-awac-question question-code=\"A321\"></mm-awac-question><mm-awac-repetition-name question-code=\"A322\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A322\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A322')\"><mm-awac-question question-code=\"A323\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A324\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A325\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A325\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A325',itLevel1)\"><mm-awac-question question-code=\"A326\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A327\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A325',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A325_LOOPDESC' | translate\"></span></button><mm-awac-question question-code=\"A328\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A329\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A329\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A329',itLevel1)\"><mm-awac-question question-code=\"A330\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A331\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A329',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A329_LOOPDESC' | translate\"></span></button></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A322')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A322_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A332_TITLE\"><mm-awac-question question-code=\"A333\"></mm-awac-question><mm-awac-repetition-name question-code=\"A334\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A334\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A334')\"><mm-awac-question question-code=\"A335\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A336\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A337\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A338\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A334')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A334_LOOPDESC' | translate\"></span></button></mm-awac-section></ng-virtual>");$templateCache.put('$/angular/views/login.html', "<div class=\"loginBackground\"><div class=\"loginFrame\" ng-enter=\"send()\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text></div><p style=\"background-color:#ff0000;color:#ffffff;padding:15px\" ng-show=\"errorMessage.length &gt; 0\">{{errorMessage}}</p><button ng-click=\"send()\" ng-bind-html=\"'LOGIN_BUTTON' | translate\" class=\"btn btn-primary\" type=\"button\"></button></div></div>");$templateCache.put('$/angular/views/site_manager.html', "<h1>{{title}}</h1>");$templateCache.put('$/angular/views/user_manager.html', "<h1>{{title}}</h1>");$templateCache.put('$/angular/views/form5.html', "<!--mm-awac-section(\"Déchets\")--><!--It lacks a proper fild code for \"D2chets alone\" -> TODO : insert into Excel file as an additional line--><mm-awac-section title-code=\"A173_TITLE\"><mm-awac-question question-code=\"A174\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A173_TITLE\"><mm-awac-repetition-name question-code=\"A175\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A175\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A175')\"><mm-awac-question question-code=\"A176\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A177\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A178\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A179\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A175')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A175_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A180_TITLE\"><mm-awac-sub-title question-code=\"A181\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"A182\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A183\"></mm-awac-question><mm-awac-question question-code=\"A184\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A185\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A186\"></mm-awac-question><mm-awac-question question-code=\"A187\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A188\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A189\"></mm-awac-question><mm-awac-question question-code=\"A190\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A191\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A192\"></mm-awac-question><mm-awac-question question-code=\"A193\"></mm-awac-question><mm-awac-sub-title question-code=\"A194\"></mm-awac-sub-title><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter les eaux usées industrielles. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A197_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"A195\"></mm-awac-question><mm-awac-question question-code=\"A198\"></mm-awac-question><mm-awac-question question-code=\"A199\"></mm-awac-question><mm-awac-question question-code=\"A200\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A201_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A501\"></mm-awac-question><mm-awac-question question-code=\"A202\"></mm-awac-question><mm-awac-question question-code=\"A203\"></mm-awac-question><mm-awac-question question-code=\"A204\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-section>");$templateCache.put('$/angular/views/results.html', "<ng-virtual><h1>Results</h1><table border=\"1\"><thead><th></th><th>Scope 1</th><th>Scope 2</th><th>Scope 3</th></thead><tbody><tr ng-repeat=\"rl in o.reportLines\"><td>{{rl.indicatorName}}</td><td>{{rl.scope1Value}}</td><td>{{rl.scope2Value}}</td><td>{{rl.scope3Value}}</td></tr></tbody></table><fs-donut bind=\"temp.browsers\"></fs-donut></ng-virtual>");$templateCache.put('$/angular/views/user_data.html', "");$templateCache.put('$/angular/views/form1.html', "<mm-awac-section title-code=\"A1_TITLE\"><mm-awac-sub-title question-code=\"A1\"></mm-awac-sub-title><mm-awac-question question-code=\"A2\"></mm-awac-question><mm-awac-question question-code=\"A3\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value == '1'\" question-code=\"A4\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value == '2' || getAnswer('A3').value == '3'\" question-code=\"A5\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value == '4'\" question-code=\"A6\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value == '4'\" question-code=\"A7\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A7').value == '1'\" question-code=\"A8\"></mm-awac-question><mm-awac-question question-code=\"A9\"></mm-awac-question><mm-awac-question question-code=\"A10\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value != '4'\" question-code=\"A11\"></mm-awac-question><mm-awac-question question-code=\"A12\"></mm-awac-question></mm-awac-section>");$templateCache.put('$/angular/views/form7.html', "<mm-awac-section title-code=\"A243_TITLE\"><mm-awac-repetition-name question-code=\"A244\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" ng-question-set-code=\"'A244'\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A244')\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A245'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A246'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A247'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A248'\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A248',itLevel1).value == '2'\" ng-repetition-map=\"itLevel1\" ng-question-code=\"'A249'\"></mm-awac-question><mm-awac-block ng-condition=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Transport--><mm-awac-sub-title question-code=\"A250\"></mm-awac-sub-title><mm-awac-question question-code=\"A251\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A252\"></mm-awac-sub-sub-title><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport aval. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A253_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"A254\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A255\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A256\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A257\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A258\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A259\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A260\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A261\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A262\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A263\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A264\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A265\" ng-repetition-map=\"itLevel1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A266_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A267\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A268\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A268',itLevel1).value == '3'\" question-code=\"A269\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A268',itLevel1).value == '2'\" question-code=\"A270\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A268',itLevel1).value == '1'\" question-code=\"A271\" ng-repetition-map=\"itLevel1\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Distribution--><mm-awac-sub-title question-code=\"A272\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"A273\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" ng-question-set-code=\"'A273'\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A273',itLevel1)\"><mm-awac-question question-code=\"A274\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-repetition-name question-code=\"A275\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel3\" question-set-code=\"A275\" ng-repetition-map=\"itLevel2\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A275',itLevel2)\"><mm-awac-question question-code=\"A276\" ng-repetition-map=\"itLevel3\"></mm-awac-question><mm-awac-question question-code=\"A277\" ng-repetition-map=\"itLevel3\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A275',itLevel2)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A275_LOOPDESC' | translate\"></span></button><mm-awac-question question-code=\"A278\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-repetition-name question-code=\"A279\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel3\" question-set-code=\"A279\" ng-repetition-map=\"itLevel2\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A279',itLevel2)\"><mm-awac-question question-code=\"A280\" ng-repetition-map=\"itLevel3\"></mm-awac-question><mm-awac-question question-code=\"A281\" ng-repetition-map=\"itLevel3\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A279',itLevel2)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A279_LOOPDESC' | translate\"></span></button></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A273',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A273_LOOPDESC' | translate\"></span></button></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A249',itLevel1).value == '1'\"><!--Traitement--><mm-awac-sub-title question-code=\"A282\"></mm-awac-sub-title><mm-awac-question question-code=\"A283\"></mm-awac-question><mm-awac-repetition-name question-code=\"A284\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A284\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A284',itLevel1)\"><mm-awac-question question-code=\"A285\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A286\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A284',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A284_LOOPDESC' | translate\"></span></button><mm-awac-question question-code=\"A287\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A288\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A288\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A288',itLevel1)\"><mm-awac-question question-code=\"A289\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A290\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A288',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A288_LOOPDESC' | translate\"></span></button></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Utilisation--><mm-awac-sub-title question-code=\"A291\"></mm-awac-sub-title><mm-awac-question question-code=\"A292\"></mm-awac-question><mm-awac-question question-code=\"A293\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A294\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A295\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A296\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A297\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A297\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A297',itLevel1)\"><mm-awac-question question-code=\"A298\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A299\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A297',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A297_LOOPDESC' | translate\"></span></button></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Fin de vie--><mm-awac-sub-title question-code=\"A300\"></mm-awac-sub-title><mm-awac-question question-code=\"A301\"></mm-awac-question><mm-awac-question question-code=\"A302\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A303\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A303\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A303',itLevel1)\"><mm-awac-question question-code=\"A304\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A305\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A306\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A307\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A308\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A303',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A303_LOOPDESC' | translate\"></span></button></mm-awac-block></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A244')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A244_LOOPDESC' | translate\"></span></button></mm-awac-section>");$templateCache.put('$/angular/views/form3.html', "<mm-awac-section title-code=\"A50_TITLE\"><mm-awac-question question-code=\"A51\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A52_TITLE\"></mm-awac-section><div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport routier. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A53_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-sub-title question-code=\"A54\"></mm-awac-sub-title><mm-awac-question question-code=\"A55\"></mm-awac-question><mm-awac-question question-code=\"A56\"></mm-awac-question><mm-awac-question question-code=\"A57\"></mm-awac-question><mm-awac-sub-title question-code=\"A58\"></mm-awac-sub-title><mm-awac-question question-code=\"A59\"></mm-awac-question><mm-awac-question question-code=\"A60\"></mm-awac-question><mm-awac-question question-code=\"A61\"></mm-awac-question><mm-awac-sub-title question-code=\"A62\"></mm-awac-sub-title><mm-awac-question question-code=\"A63\"></mm-awac-question><mm-awac-question question-code=\"A64\"></mm-awac-question><mm-awac-question question-code=\"A65\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A66_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A67\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A67\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A67')\"><mm-awac-question question-code=\"A68\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A69\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A69',itLevel1).value == '0'\" question-code=\"A70\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A71\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A72\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A72',itLevel1).value == '1'\" question-code=\"A73\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A72',itLevel1).value == '2'|| getAnswer('A72',itLevel1).value == '3'|| getAnswer('A72',itLevel1).value == '4'|| getAnswer('A72',itLevel1).value == '5'\" question-code=\"A74\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A72',itLevel1).value == '6' || getAnswer('A72',itLevel1).value == '7'\" question-code=\"A75\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A76\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A67')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A67_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-yellow\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A77_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A78\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A78\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A78')\"><mm-awac-question question-code=\"A79\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A80\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A80',itLevel1).value == '0'\" question-code=\"A81\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A83\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A88\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A83',itLevel1).value == 'AS_5'\" question-code=\"A89\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A83',itLevel1).value == 'AS_162'\" question-code=\"A90\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A83',itLevel1).value == 'AS_6'\" question-code=\"A91\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A83',itLevel1).value == 'AS_163'\" question-code=\"A92\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A78')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A78_LOOPDESC' | translate\"></span></button></div></div></tab></tabset></div></div><div class=\"horizontal_separator\"></div></div><mm-awac-section title-code=\"A93_TITLE\"></mm-awac-section><div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport en commun. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A94_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"A95\"></mm-awac-question><mm-awac-question question-code=\"A96\"></mm-awac-question><mm-awac-question question-code=\"A97\"></mm-awac-question><mm-awac-question question-code=\"A98\"></mm-awac-question><mm-awac-question question-code=\"A99\"></mm-awac-question><mm-awac-question question-code=\"A100\"></mm-awac-question><mm-awac-question question-code=\"A101\"></mm-awac-question><mm-awac-question question-code=\"A102\"></mm-awac-question><mm-awac-question question-code=\"A103\"></mm-awac-question><mm-awac-question question-code=\"A104\"></mm-awac-question><mm-awac-question question-code=\"A105\"></mm-awac-question><mm-awac-question question-code=\"A106\"></mm-awac-question><mm-awac-question question-code=\"A107\"></mm-awac-question><mm-awac-question question-code=\"A108\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A109_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A110\"></mm-awac-question><mm-awac-question question-code=\"A111\"></mm-awac-question><mm-awac-question question-code=\"A112\"></mm-awac-question></div></div></tab></tabset></div></div><div class=\"horizontal_separator\"></div></div><mm-awac-section title-code=\"A113_TITLE\"></mm-awac-section><div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport en avion. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A114_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A115\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A115\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A115')\"><mm-awac-question question-code=\"A116\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A117\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A118\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A119\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A120\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"add-repetition-button\" ng-click=\"addIteration('A115')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A115_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-green\"><tab-heading><i class=\"glyphicon glyphicon-bell\"></i><span ng-bind-html=\"'A121_TITLE' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A122\"></mm-awac-question><mm-awac-question question-code=\"A123\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A123',itLevel1).value == '0'\" question-code=\"A124\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A124',itLevel1).value == '1'\" question-code=\"A125\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A124',itLevel1).value == '0'\" question-code=\"A126\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A123',itLevel1).value == '1'\" question-code=\"A127\"></mm-awac-question></div></div></tab></tabset></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-sub-sub-title.html', "<div><div class=\"sub_sub_title\"><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-string-question.html', "<input class=\"oneelement\" ng-disabled=\"getDataToCompare()==true\" ng-model=\"getAnswer().value\" type=\"text\">");$templateCache.put('$/angular/templates/mm-awac-document-question.html', "<div class=\"oneelement document-question-block document-question\"><span ng-hide=\"getDataToCompare()==true\">Upload new documents</span><div class=\"document-question-progress-bar\" ng-show=\"inDownload\"><div ng-style=\"style\"><spa></spa></div></div><div class=\"document-question-progress-percentage\" ng-show=\"inDownload\">{{percent}} %</div><input ng-file-select=\"onFileSelect($files)\" type=\"file\" ng-hide=\"getDataToCompare()==true\"><div ng-show=\"getFileNumber()&gt;0\">{{getFileNumber()}} document had already uploaded</div><button ng-click=\"openDocumentManager()\" type=\"button\" ng-show=\"getFileNumber()&gt;0\">consult this documents</button></div>");$templateCache.put('$/angular/templates/mm-awac-modal-question-comment.html', "<!--Modal--><div class=\"modal\" id=\"modalLogin\" aria-labelledby=\"myModalLabel\" ng-enter=\"send()\" aria-hidden=\"true\" tabindex=\"-1\" role=\"dialog\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button data-dismiss=\"modal\" class=\"close\" type=\"button\"><span aria-hidden=\"true\">&times;<span</span><span class=\"sr-only\">Close</span></button><h4 id=\"myModalLabel\" class=\"modal-title\">Ajouter un commentaire</h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"loginInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><button ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-enabled=\"allFieldValid()\" class=\"btn btn-primary\" type=\"button\">Save</button><button ng-click=\"close();\" class=\"btn btn-primary\" type=\"button\">Cancel</button></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-repetition-name.html', "<div><div class=\"repetition-title\"><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span class=\"glyphicon glyphicon-record\"></span><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-repetition-question-disabled.html', "<ng-virtual><div><div class=\"repetition-title\"><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span class=\"glyphicon glyphicon-record\"></span><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span></div></div><div ng-iteration=\"itLevel\" ng-repeat=\"itLevel in getRepetitionMapByQuestionSet()\" ng-hide=\"getCondition() === false\"><div class=\"repetition-question\"><div class=\"repetition-question-title\" style=\"display : inline-block; margin-right : 20px\" ng-bind-html=\"getQuestionCode() + '_LOOPDESC' | translate\"></div><button class=\"remove-button\" ng-click=\"removeAnwser()\" type=\"button\">Supprimer</button><div class=\"repetition-question-container\"><ng-virtual ng-transclude class=\"element_stack\"></ng-virtual></div></div></div><button class=\"add-repetition-button\" ng-click=\"addIteration()\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span></button><span ng-bind-html=\"getQuestionCode() + '_LOOPDESC' | translate\"></span></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-enterprise-survey.html', "<ng-virtual>\n    <div ng-hide=\"$root.isLogin()\">\n        <div class=\"header\">\n\n            <!-- user block -->\n            <table class=\"header-option\">\n\n                <tr>\n                    <td>\n                        <div>\n                            <select ng-model=\"$parent.language\">\n                                <option value=\"en\" ng-bind-html=\"'EN' | translate\"></option>\n                                <option value=\"fr\" ng-bind-html=\"'FR' | translate\"></option>\n                                <option value=\"nl\" ng-bind-html=\"'NL' | translate\"></option>\n                            </select>\n                        </div>\n                    </td>\n\n                    <td>\n                        <div>Gestion</div>\n                    </td>\n                    <td>\n                        <div ng-show=\"$root.currentPerson!=null\"><span\n                                ng-bind-html=\"'WELCOME' | translate\"></span>, <span\n                                class=\"username\">{{$root.currentPerson.firstName}} {{$root.currentPerson.lastName}}</span>\n                        </div>\n                        <div ng-show=\"$root.currentPerson==null\">Your are currently not connected</div>\n                    </td>\n\n                </tr>\n\n                <tr>\n\n                    <td>\n\n                        <button type=\"button\" class=\"confidentiality\" mm-not-implemented>Confidentialité</button>\n                        <button type=\"button\" class=\"help\" mm-not-implemented>Assistance</button>\n                    </td>\n\n                    <td>\n\n\n                        <!-- site manager button -->\n                        <button type=\"button\"\n                                class=\"user_manage\"\n                                ng-bind-html=\"'SITE_MANAGER_BUTTON' | translate\"\n                                ng-click=\"nav('/site_manager')\"\n                                ng-class=\"{'selected': isMenuCurrentlySelected('/site_manager') == true}\">\n                        </button>\n\n                        <!-- user manager button -->\n                        <button type=\"button\"\n                                class=\"user_manage\"\n                                ng-bind-html=\"'USER_MANAGER_BUTTON' | translate\"\n                                ng-click=\"nav('/user_manager')\"\n                                ng-class=\"{'selected': isMenuCurrentlySelected('/user_manager') == true}\">\n                        </button>\n\n                    </td>\n\n                    <td>\n\n\n                        <!-- user data button -->\n                        <button type=\"button\"\n                                class=\"user_manage\"\n                                ng-bind-html=\"'USER_DATA_BUTTON' | translate\"\n                                ng-click=\"nav('/user_data')\"\n                                ng-class=\"{'selected': isMenuCurrentlySelected('/user_data') == true}\">\n                        </button>\n\n                        <!-- logout button -->\n                        <button ng-show=\"$root.currentPerson!=null\"\n                                type=\"button\"\n                                ng-click=\"$root.logout();\"\n                                ng-bind-html=\"'LOGOUT_BUTTON' | translate\"\n                                class=\"user_manage\">\n                        </button>\n\n                    </td>\n                </tr>\n            </table>\n\n\n            <div class=\"wallonie_logo\"></div>\n            <div class=\"awac_logo\"></div>\n            <div>\n                <div class=\"calculateur_type\" ng-bind-html=\"'TITLE' | translate\"></div>\n                <div class=\"entreprise_name\">{{ $root.organization.name }}</div>\n            </div>\n\n        </div>\n\n        <div class=\"data_menu\" ng-show=\"displayMenu()===true\">\n            <div class=\"data_date\">\n                <div ng-bind-html=\"'PERIOD_DATA' | translate\"></div>\n                <select ng-options=\"p.key as p.label for p in $root.periods\" ng-model=\"periodKey\"></select>\n            </div>\n\n            <div class=\"big_separator\"></div>\n\n            <div class=\"data_date_compare\">\n                <div>Site sélectionné</div>\n                <select ng-options=\"s.id as s.name for s in $root.organization.sites\" ng-model=\"scopeId\"></select>\n            </div>\n\n            <div class=\"big_separator\"></div>\n\n            <div class=\"data_date_compare\">\n                <div>Comparaison avec</div>\n                <select ng-options=\"p.key as p.label for p in periodsForComparison\" ng-model=\"periodToCompare\"></select>\n            </div>\n\n            <div class=\"big_separator\"></div>\n\n            <div class=\"data_save\">\n                <div class=\"last_save\" ng-hide=\"lastSaveTime===null\">\n                    <span ng-bind-html=\"'LAST_SAVE' | translate\"></span><br>\n                    {{lastSaveTime | date: 'medium' }}\n                </div>\n                <div class=\"small_separator\"></div>\n                <div class=\"save_button\">\n                    <button type=\"button\" class=\"save\" ng-click=\"save()\"\n                            ng-bind-html=\"'SAVE_BUTTON' | translate\"></button>\n                </div>\n            </div>\n        </div>\n\n\n        <div class=\"nav_tabs\">\n            <div class=\"nav_entreprise\">\n                <div class=\"site_menu\">\n                    <div class=\"site\">\n                        <button type=\"button\" class=\"verification\" mm-not-implemented>Vérification</button>\n                    </div>\n                    <div class=\"menu\">\n                        <button ng-class=\"{'menu_current': isMenuCurrentlySelected('/form1') == true}\" ng-click=\"nav('/form1')\">\n                            <div ng-bind-html=\"'TAB1' | translate\"></div>\n                            <mm-awac-tab-progress-bar ng-value=\"getProgress('TAB1')\"></mm-awac-tab-progress-bar>\n                            <div class=\"menu_arrow\"></div>\n                        </button>\n                        <button ng-class=\"{'menu_current': isMenuCurrentlySelected('/form2') == true}\" ng-click=\"nav('/form2')\">\n                            <div ng-bind-html=\"'TAB2' | translate\"></div>\n                            <mm-awac-tab-progress-bar ng-value=\"getProgress('TAB2')\"></mm-awac-tab-progress-bar>\n                            <div class=\"menu_arrow\"></div>\n                        </button>\n                        <button ng-class=\"{'menu_current': isMenuCurrentlySelected('/form3')  == true}\" ng-click=\"nav('/form3')\">\n                            <div ng-bind-html=\"'TAB3' | translate\"></div>\n                            <mm-awac-tab-progress-bar ng-value=\"getProgress('TAB3')\"></mm-awac-tab-progress-bar>\n                            <div class=\"menu_arrow\"></div>\n                        </button>\n                        <button ng-class=\"{'menu_current': isMenuCurrentlySelected('/form4') == true}\" ng-click=\"nav('/form4')\">\n                            <div ng-bind-html=\"'TAB4' | translate\"></div>\n                            <mm-awac-tab-progress-bar ng-value=\"getProgress('TAB4')\"></mm-awac-tab-progress-bar>\n                            <div class=\"menu_arrow\"></div>\n                        </button>\n                        <button ng-class=\"{'menu_current': isMenuCurrentlySelected('/form5') == true}\" ng-click=\"nav('/form5')\">\n                            <div ng-bind-html=\"'TAB5' | translate\"></div>\n                            <mm-awac-tab-progress-bar ng-value=\"getProgress('TAB5')\"></mm-awac-tab-progress-bar>\n                            <div class=\"menu_arrow\"></div>\n                        </button>\n                        <button ng-class=\"{'menu_current': isMenuCurrentlySelected('/form6') == true}\" ng-click=\"nav('/form6')\">\n                            <div ng-bind-html=\"'TAB6' | translate\"></div>\n                            <mm-awac-tab-progress-bar ng-value=\"getProgress('TAB6')\"></mm-awac-tab-progress-bar>\n                            <div class=\"menu_arrow\"></div>\n                        </button>\n                        <button ng-class=\"{'menu_current': isMenuCurrentlySelected('/form7') == true}\" ng-click=\"nav('/form7')\">\n                            <div ng-bind-html=\"'TAB7' | translate\"></div>\n                            <mm-awac-tab-progress-bar ng-value=\"getProgress('TAB7')\"></mm-awac-tab-progress-bar>\n                            <div class=\"menu_arrow\"></div>\n                        </button>\n                    </div>\n                </div>\n                <div class=\"last_menu\">\n                    <button ng-class=\"{'menu_current': isMenuCurrentlySelected('/results') == true}\" ng-click=\"nav('/results')\">\n                        résultats\n                    </button>\n                    <button mm-not-implemented>\n                        actions de réduction\n                    </button>\n                </div>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"{{getClassContent()}}\" ng-view>\n    </div>\n\n\n    <div class=\"footer\"></div>\n\n</ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-percentage-question.html', "<span class=\"twoelement\"><input ng-disabled=\"getDataToCompare()==true\" percent style=\"text-align:right;\" numbers-only=\"double\" ng-model=\"getAnswer().value\" type=\"text\"><span style=\"margin-left:5px\">%</span></span>");$templateCache.put('$/angular/templates/mm-awac-sub-title.html', "<div><div class=\"sub_title\"><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-section.html', "<ng-virtual><div class=\"element\"><div class=\"element_header\"><div class=\"title\" ng-bind-html=\"getTitleCode() | translate\"></div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\" mm-not-implemented><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div ng-transclude ng-class=\"getMode()\"></div></div></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-question.html', "<div ng-class=\"{'twoanswer':displayOldDatas()===true, 'oneanswer':displayOldDatas()===false,'condition-false':getCondition() === false}\" class=\"question_field\"><div><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-class=\"getQuestion().answerType === 'DOCUMENT' ? 'glyphicon-file' : 'glyphicon-share-alt' \" class=\"glyphicon\"></span><span ng-click=\"logQuestionCode()\" ng-bind-html=\"getQuestionCode() + '_TITLE' | translate\"></span></div><div><div class=\"status\" ng-class=\"getStatusClass()\"></div><div class=\"error_message\" ng-show=\"errorMessage.length&gt;0\"><div>{{errorMessage}}</div><img src=\"/awac/assets/images/question_field_error_message_icon_arrow.png\"></div><span class=\"inject-data\"></span><div class=\"user_icon\">{{getUserName(false,true)}}<div><span>{{getUserName(false,false)}}</span><img src=\"/awac/assets/images/user_icon_arrow.png\"></div></div></div><div ng-show=\"displayOldDatas() === true &amp;&amp; getAnswer(true) != null\"><button title=\"Copier la valeur\" ng-click=\"copyDataToCompare()\"><<</button><span class=\"inject-data-to-compare\"></span><div class=\"user_icon\">{{getUserName(true,true)}}<div><div>{{getUserName(true,false)}}</div><img src=\"/awac/assets/images/user_icon_arrow.png\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-block.html', "<ng-virtual><div ng-transclude ng-class=\"{true:'condition-false', false:''}[getCondition() === false]\"></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-modal-field-text.html', "<tr><td>{{getInfo().fieldTitle}}<td><input ng-keyup=\"controlField()\" placeholder=\"{{getInfo().placeholder}}\" ng-model=\"getInfo().field\" type=\"{{getInfo().fieldType}}\"></td><td><img src=\"/awac/assets/images/field_valid.png\" ng-show=\"getInfo().isValid\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/awac/assets/images/field_invalid.png\"><div>{{getInfo().validationMessage}}</div></div></td></td></tr>");$templateCache.put('$/angular/templates/mm-awac-modal-login.html', "<!--Modal--><div class=\"modal\" id=\"modalLogin\" aria-labelledby=\"myModalLabel\" ng-enter=\"send()\" aria-hidden=\"true\" tabindex=\"-1\" role=\"dialog\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><!--button(type=\"button\",class=\"close\",data-dismiss=\"modal\")<span aria-hidden=\"true\">&times;<span</span><span class=\"sr-only\">Close</span>--><h4 id=\"myModalLabel\" class=\"modal-title\">Login</h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><!--button(type=\"button\",class=\"btn btn-default\",data-dismiss=\"modal\") Close--><button ng-disabled=\"!allFieldValid()\" ng-click=\"send();\" ng-enabled=\"allFieldValid()\" class=\"btn btn-primary\" type=\"button\">Login</button><button ng-disabled=\"!allFieldValid()\" ng-click=\"test();\" ng-enabled=\"allFieldValid()\" class=\"btn btn-primary\" type=\"button\">test</button></div><img src=\"/awac/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-select-question.html', "<select class=\"oneelement\" ng-disabled=\"getDataToCompare()==true\" ng-options=\"p.key as p.label for p in getOptions()\" ng-model=\"getAnswer().value\"></select>");$templateCache.put('$/angular/templates/mm-awac-integer-question.html', "<input class=\"oneelement\" ng-disabled=\"getDataToCompare()==true\" style=\"text-align:right;\" numbers-only=\"integer\" ng-model=\"getAnswer().value\" type=\"text\">");$templateCache.put('$/angular/templates/mm-awac-tab-progress-bar.html', "<div class=\"tab-pg-bar\"><div class=\"tab-pg-text\"><span ng-bind-html=\"'FILLED_BY' | translate\"></span><span>&nbsp;</span><span>{{ pg }}%</span></div><div class=\"tab-pg-background tab-pb-{{color}}-bg\"><div style=\"width: {{ pg }}%\" class=\"tab-pg-indicator tab-pb-{{color}}-fg\"></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-boolean-question.html', "<span class=\"twoelement\"><span style=\"text-align:center\"><span style=\"vertical-align:middle;margin-right : 15px;\">oui</span><input ng-disabled=\"getDataToCompare()==true\" style=\"width :20px !important;margin:0;vertical-align:middle;\" name=\"{{getQuestionCode()}}\" value=\"1\" ng-model=\"getAnswer().value\" type=\"radio\"></span><span style=\"text-align:center\"><span style=\"vertical-align:middle;margin-right : 15px;\">non</span><input ng-disabled=\"getDataToCompare()==true\" style=\"width :20px !important;margin:0;vertical-align:middle;\" name=\"{{getQuestionCode()}}\" value=\"0\" ng-model=\"getAnswer().value\" type=\"radio\"></span></span>");$templateCache.put('$/angular/templates/mm-awac-modal-document-manager.html', "<!--Modal--><div class=\"modal\" id=\"modalDocumentManager\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\" tabindex=\"-1\" role=\"dialog\" ng-show=\"show\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button ng-click=\"close()\" style=\"float:right\" type=\"button\"><span aria-hidden=\"true\">&times;<span</span><span class=\"sr-only\">Close</span></button><h4 id=\"myModalLabel\" class=\"modal-title\">Docuements uploaded</h4></div><div class=\"modal-body\"><table class=\"document-manager-table\"><thead><tr><td>Document name</td><td>Actions</td></tr></thead><tbody><tr ng-repeat=\"doc in listDocuments\"><td>{{doc}}</td><td><button ng-click=\"download(doc.id)\" type=\"button\">download</button><button ng-click=\"removeDoc(doc.id)\" type=\"button\">remove</button></td></tr></tbody></table></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-real-question.html', "<input class=\"oneelement\" ng-disabled=\"getDataToCompare()==true\" style=\"text-align:right;\" numbers-only=\"double\" ng-model=\"getAnswer().value\" type=\"text\">");$templateCache.put('$/angular/templates/mm-awac-repetition-question.html', "<div ng-class=\"{true:'condition-false', false:''}[getCondition() === false]\"><div class=\"repetition-question\"><div class=\"repetition-question-title\" style=\"display : inline-block; margin-right : 20px\" ng-bind-html=\"getQuestionSetCode() + '_LOOPDESC' | translate\"></div><button class=\"remove-button\" ng-click=\"removeAnwser()\" type=\"button\">Supprimer</button><div class=\"repetition-question-container\"><ng-virtual ng-transclude class=\"element_stack\"></ng-virtual></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-loading.html', "<!--Modal--><div class=\"modal\" id=\"modalLoading\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\" tabindex=\"-1\" role=\"dialog\" ng-show=\"show\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\" style=\"text-align:center\"><h4>Loading</h4></div><div class=\"modal-body\" style=\"text-align:center\"><img src=\"/awac/assets/images/loading_preorganization.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-real-with-unit-question.html', "<span class=\"twoelement\"><input ng-disabled=\"getDataToCompare()==true\" style=\"text-align:right;\" numbers-only=\"double\" ng-model=\"getAnswer().value\" type=\"text\"><select ng-disabled=\"getDataToCompare()==true\" ng-options=\"p.id as p.name for p in getUnits()\" ng-model=\"getAnswer().unitId\"></select></span>");$templateCache.put('$/angular/templates/mm-awac-modal-confirmation-exit-form.html', "<!--Modal--><div class=\"modal\" id=\"modalConfirmationExitForm\" aria-labelledby=\"myModalLabel\" ng-enter=\"save()\" aria-hidden=\"true\" tabindex=\"-1\" role=\"dialog\" ng-show=\"show\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button ng-click=\"close()\" style=\"float:right\" type=\"button\"><span aria-hidden=\"true\">&times;<span</span><span class=\"sr-only\">Close</span></button><h4 id=\"myModalLabel\" class=\"modal-title\">Attention</h4></div><div class=\"modal-body\"><div class=\"field_form\">Vous allez perdre toutes vos modifications depuis la dernière sauvegarde. Êtes-vous sûr de vouloir continuer ?</div></div><div class=\"modal-footer\"><button ng-click=\"continue();\" type=\"button\"> Continuer sans sauvegarder</button><button ng-click=\"save()\" type=\"button\">Enregister et continuer</button><button ng-click=\"close()\" type=\"button\">Annuler</button></div></div></div></div>");});