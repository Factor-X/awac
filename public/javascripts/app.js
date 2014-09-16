var initializeCommonRoutes;
initializeCommonRoutes = function() {
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/login', {
      templateUrl: '$/angular/views/login.html',
      controller: 'LoginCtrl',
      anonymousAllowed: true
    }).when('/admin', {
      templateUrl: '$/angular/views/admin.html',
      controller: 'AdminCtrl'
    }).when('/user_data/:period/:scope', {
      templateUrl: '$/angular/views/user_data.html',
      controller: 'UserDataCtrl'
    }).when('/user_manager/:period/:scope', {
      templateUrl: '$/angular/views/user_manager.html',
      controller: 'UserManagerCtrl'
    }).when('/site_manager/:period/:scope', {
      templateUrl: '$/angular/views/site_manager.html',
      controller: 'SiteManagerCtrl'
    }).when('/registration/:key', {
      templateUrl: '$/angular/views/user_registration.html',
      controller: 'RegistrationCtrl',
      anonymousAllowed: true
    }).otherwise({
      redirectTo: '/login'
    });
    return;
  });
};var initializeMunicipalityRoutes;
initializeMunicipalityRoutes = function() {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    return $rootScope.getFormPath = function() {
      return '/municipality-tab1';
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/municipality-tab1/:period/:scope', {
      templateUrl: '$/angular/views/municipality/TAB_C1.html',
      controller: 'FormCtrl',
      resolve: {
        formIdentifier: function() {
          return 'TAB_C1';
        },
        displayFormMenu: function() {
          return true;
        }
      }
    }).when('/municipality-tab2/:period/:scope', {
      templateUrl: '$/angular/views/municipality/TAB_C2.html',
      controller: 'FormCtrl',
      resolve: {
        formIdentifier: function() {
          return 'TAB_C2';
        },
        displayFormMenu: function() {
          return true;
        }
      }
    }).when('/municipality-tab3/:period/:scope', {
      templateUrl: '$/angular/views/municipality/TAB_C3.html',
      controller: 'FormCtrl',
      resolve: {
        formIdentifier: function() {
          return 'TAB_C3';
        },
        displayFormMenu: function() {
          return true;
        }
      }
    }).when('/municipality-tab4/:period/:scope', {
      templateUrl: '$/angular/views/municipality/TAB_C4.html',
      controller: 'FormCtrl',
      resolve: {
        formIdentifier: function() {
          return 'TAB_C4';
        },
        displayFormMenu: function() {
          return true;
        }
      }
    }).when('/municipality-tab5/:period/:scope', {
      templateUrl: '$/angular/views/municipality/TAB_C5.html',
      controller: 'FormCtrl',
      resolve: {
        formIdentifier: function() {
          return 'TAB_C5';
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
  });
};var initializeEnterpriseRoutes;
initializeEnterpriseRoutes = function() {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    return $rootScope.getFormPath = function() {
      return '/enterprise-tab2';
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/enterprise-tab2/:period/:scope', {
      templateUrl: '$/angular/views/enterprise/TAB2.html',
      controller: 'FormCtrl',
      resolve: {
        formIdentifier: function() {
          return 'TAB2';
        },
        displayFormMenu: function() {
          return true;
        }
      }
    }).when('/enterprise-tab3/:period/:scope', {
      templateUrl: '$/angular/views/enterprise/TAB3.html',
      controller: 'FormCtrl',
      resolve: {
        formIdentifier: function() {
          return 'TAB3';
        },
        displayFormMenu: function() {
          return true;
        }
      }
    }).when('/enterprise-tab4/:period/:scope', {
      templateUrl: '$/angular/views/enterprise/TAB4.html',
      controller: 'FormCtrl',
      resolve: {
        formIdentifier: function() {
          return 'TAB4';
        },
        displayFormMenu: function() {
          return true;
        }
      }
    }).when('/enterprise-tab5/:period/:scope', {
      templateUrl: '$/angular/views/enterprise/TAB5.html',
      controller: 'FormCtrl',
      resolve: {
        formIdentifier: function() {
          return 'TAB5';
        },
        displayFormMenu: function() {
          return true;
        }
      }
    }).when('/enterprise-tab6/:period/:scope', {
      templateUrl: '$/angular/views/enterprise/TAB6.html',
      controller: 'FormCtrl',
      resolve: {
        formIdentifier: function() {
          return 'TAB6';
        },
        displayFormMenu: function() {
          return true;
        }
      }
    }).when('/enterprise-tab7/:period/:scope', {
      templateUrl: '$/angular/views/enterprise/TAB7.html',
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
  });
};var iName;
angular.module('app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap']);
angular.module('app.filters', []);
angular.module('app.services', []);
angular.module('app.controllers', ['app.services', 'ngRoute', 'angularFileUpload', 'tmh.dynamicLocale', 'ngTable']);
angular.module('app', ['app.directives', 'app.filters', 'app.services', 'app.controllers']);
angular.module("tmh.dynamicLocale").config(function(tmhDynamicLocaleProvider) {
  return tmhDynamicLocaleProvider.localeLocationPattern('assets/components/angular-i18n/angular-locale_{{locale}}.js');
});
initializeCommonRoutes();
if (document.querySelector("meta[name=app]") != null) {
  iName = document.querySelector("meta[name=app]").getAttribute("content");
  if (iName === "municipality") {
    initializeMunicipalityRoutes();
  }
  if (iName === "enterprise") {
    initializeEnterpriseRoutes();
  }
}
angular.module('app').run(function($rootScope) {
  return $rootScope.instanceName = iName;
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
      callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: true
      });
      return;
    });
    promise.error(function(data, status, headers, config) {
      this.downloadsInProgress--;
      callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: false
      });
      return;
    });
    return promise;
  };
  this.postJson = function(url, data, callback) {
    var promise;
    if (data === null) {
      data = {};
    }
    this.downloadsInProgress++;
    promise = $http({
      method: "POST",
      url: url,
      data: data,
      headers: {
        "Content-Type": "application/json"
      }
    });
    promise.success(function(data, status, headers, config) {
      this.downloadsInProgress--;
      callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: true
      });
      return;
    });
    promise.error(function(data, status, headers, config) {
      this.downloadsInProgress--;
      callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: false
      });
      return;
    });
    return promise;
  };
  return;
});angular.module('app.services').service("modalService", function($rootScope) {
  this.LOADING = 'loading';
  this.DOCUMENT_MANAGER = 'DOCUMENT_MANAGER';
  this.CONFIRMATION_EXIT_FORM = 'confirmation-exit-form';
  this.QUESTION_COMMENT = 'question-comment';
  this.EMAIL_CHANGE = 'email-change';
  this.PASSWORD_CHANGE = 'password-change';
  this.CONNECTION_PASSWORD_CHANGE = 'connection-password-change';
  this.INVITE_USER = 'invite-user';
  this.EDIT_SITE = 'edit-site';
  this.ADD_USER_SITE = 'add-user-site';
  this.show = function(modalName, params) {
    var args;
    args = [];
    args.show = true;
    args.params = params;
    args.target = modalName;
    $rootScope.displayModalBackground = true;
    return $rootScope.$broadcast('SHOW_MODAL', args);
  };
  this.close = function(modalName) {
    var args;
    args = [];
    args.show = false;
    args.target = modalName;
    $rootScope.displayModalBackground = false;
    return $rootScope.$broadcast('SHOW_MODAL', args);
  };
  return;
});angular.module('app.services').service('loggerService', function() {
  var svc;
  svc = this;
  svc.initialize = function() {
    var layout, log;
    log = log4javascript.getLogger('main');
    svc.appender = new log4javascript.InPageAppender('logger', true, false, true, '100%', '100%');
    layout = new log4javascript.PatternLayout("%d [%-5p] %15c - %m");
    svc.appender.setLayout(layout);
    log.addAppender(svc.appender);
    return log.info("Logger initialized");
  };
  svc.get = function(name) {
    var log;
    log = log4javascript.getLogger(name);
    log.addAppender(svc.appender);
    return log;
  };
  return;
});angular.module('app.services').service("translationService", function($rootScope, downloadService) {
  var svc;
  svc = this;
  svc.elements = null;
  svc.initialize = function(lang) {
    return downloadService.getJson("/awac/translations/" + lang, function(result) {
      if (result.success) {
        svc.elements = result.data.lines;
        return $rootScope.$broadcast("LOAD_FINISHED", {
          type: "TRANSLATIONS",
          success: true
        });
      } else {
        svc.elements = [];
        return $rootScope.$broadcast("LOAD_FINISHED", {
          type: "TRANSLATIONS",
          success: false
        });
      }
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
});angular.module('app.services').service("messageFlash", function(translateTextFilter) {
  this.display = function(type, message, opts) {
    var options;
    options = {
      message: translateTextFilter(message),
      type: type,
      hideAfter: 5,
      showCloseButton: true
    };
    return Messenger().post(angular.extend(options, angular.copy(opts)));
  };
  this.displaySuccess = function(message, opts) {
    return this.display('success', message, opts);
  };
  this.displayInfo = function(message, opts) {
    return this.display('info', message, opts);
  };
  this.displayError = function(message, opts) {
    return this.display('error', message, opts);
  };
  return;
});angular.module('app.filters').filter("stringToFloat", function($sce, translationService) {
  return function(input) {
    if (input != null) {
      return parseFloat(input);
    }
  };
});angular.module('app.filters').filter("numberToI18NOrLess", function($filter, translationService) {
  return function(input) {
    var original, rounded;
    if (input != null) {
      original = parseFloat(input);
      rounded = Math.round(original * 1000.0) / 1000.0;
      if (rounded < 0.001) {
        return translationService.get('LESS_THAN_MINIMUM');
      } else {
        return $filter("number")(rounded, 3);
      }
    }
    return "";
  };
});angular.module('app.filters').filter("translateText", function($sce, translationService) {
  return function(input, count) {
    var text;
    text = translationService.get(input, count);
    if (text != null) {
      return text;
    }
    return input;
  };
});angular.module('app.filters').filter("numberToI18N", function($filter) {
  return function(input) {
    var original, rounded;
    if (input != null) {
      original = parseFloat(input);
      rounded = Math.round(original * 100.0) / 100.0;
      return $filter("number")(rounded, 2);
    }
    return "";
  };
});angular.module('app.filters').filter("nullToZero", function($sce, translationService) {
  return function(input) {
    if (input === void 0 || input === null) {
      return 0;
    } else {
      return parseFloat(input);
    }
  };
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
angular.module('app.directives').directive('mmRangeValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmRangeValidator'.length) == 'mmRangeValidator' && k.length > 'mmRangeValidator'.length) {
                        arg = k.substring('mmRangeValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(value, args) {
    if(parseFloat(value)==null || parseFloat(value)==NaN)
        return false;
    return parseFloat(value)>=parseInt(args.min) && parseFloat(value)<=parseInt(args.max);
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('range', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('range', false);
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
    if(value == null)
        if(args.min > 0)
            return false;
        return true;
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
            angular.module('app.directives').directive("mmAwacRegistrationEnterprise", function(directiveService, downloadService, messageFlash) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-registration-enterprise.html",
    replace: true,
    controller: function($scope) {
      $scope.validatePasswordConfirmField = function() {
        if ($scope.passwordInfo.field === $scope.passwordConfirmInfo.field) {
          return true;
        }
        $scope.passwordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_CONFIRMATION";
        return false;
      };
      $scope.identifierInfo = {
        fieldTitle: "USER_IDENTIFIER",
        validationRegex: "[a-zA-Z0-9-]{5,20}",
        validationMessage: "IDENTIFIER_CHECK_WRONG"
      };
      $scope.lastNameInfo = {
        fieldTitle: "USER_LASTNAME",
        validationRegex: "^.{1,255}$",
        validationMessage: "USER_LASTNAME_WRONG_LENGTH"
      };
      $scope.firstNameInfo = {
        fieldTitle: "USER_FIRSTNAME",
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
        focus: function() {
          return $scope.$parent.tabActive[2];
        }
      };
      $scope.emailInfo = {
        fieldTitle: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_TITLE",
        validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
      };
      $scope.passwordInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE",
        fieldType: "password",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
      };
      $scope.passwordConfirmInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
        fieldType: "password",
        validationFct: $scope.validatePasswordConfirmField,
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
      };
      $scope.organizationNameInfo = {
        fieldTitle: "ORGANIZATION_NAME",
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
      };
      $scope.firstSiteNameInfo = {
        fieldTitle: "MAIN_SITE_NAME",
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "SITE_NAME_WRONG_LENGTH"
      };
      $scope.registrationFieldValid = function() {
        if ($scope.identifierInfo.isValid && $scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid && $scope.emailInfo.isValid && $scope.passwordInfo.isValid && $scope.passwordConfirmInfo.isValid && $scope.organizationNameInfo.isValid && $scope.firstSiteNameInfo.isValid) {
          return true;
        }
        return false;
      };
      return $scope.registration = function() {
        var data;
        $scope.isLoading = true;
        data = {};
        data.person = {};
        data.person.email = $scope.emailInfo.field;
        data.person.identifier = $scope.identifierInfo.field;
        data.person.firstName = $scope.firstNameInfo.field;
        data.person.lastName = $scope.lastNameInfo.field;
        data.password = $scope.passwordInfo.field;
        data.organizationName = $scope.organizationNameInfo.field;
        data.firstSiteName = $scope.firstSiteNameInfo.field;
        data.person.defaultLanguage = $scope.$root.language;
        downloadService.postJson('/enterprise/registration', data, function(result) {
          if (result.success) {
            $scope.$root.loginSuccess(result.data);
            messageFlash.displaySuccess("You are now connected");
            return $scope.isLoading = false;
          } else {
            messageFlash.displayError(result.data.message);
            return $scope.isLoading = false;
          }
        });
        return false;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacSubSubTitle", function(directiveService, translationService) {
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
      ngDataToCompare: '=',
      ngIsAggregation: '='
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
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        return scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
    }
  };
});angular.module('app.directives').directive("mmAwacDocumentQuestion", function(directiveService, translationService, $upload, messageFlash, modalService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
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
          if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
            scope.$watch('getAnswer().value', function(o, n) {
              if ("" + n !== "" + o) {
                return scope.$parent.edited();
              }
            });
          }
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
                return;
              }).success(function(data, status, headers, config) {
                var fileName;
                scope.percent = 0;
                scope.inDownload = false;
                fileName = "??";
                messageFlash.displaySuccess("The file " + fileName + " was upload successfully");
                if (scope.getAnswer().value === null || scope.getAnswer().value === void 0) {
                  scope.getAnswer().value = {};
                }
                scope.getAnswer().value[data.id] = data.name;
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
});angular.module('app.directives').directive("mmAwacModalConnectionPasswordChange", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-connection-password-change.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.validatePasswordConfirmField = function() {
        if ($scope.newPasswordConfirmInfo.field.match("^\\S{5,20}$")) {
          if ($scope.newPasswordInfo.field === $scope.newPasswordConfirmInfo.field) {
            return true;
          }
          $scope.newPasswordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_CONFIRMATION";
        } else {
          $scope.newPasswordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_LENGTH";
        }
        return false;
      };
      $scope.newPasswordInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        hideIsValidIcon: true,
        field: "",
        focus: function() {
          return true;
        }
      };
      $scope.newPasswordConfirmInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_PLACEHOLDER",
        validationFct: $scope.validatePasswordConfirmField,
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        hideIsValidIcon: true,
        field: ""
      };
      $scope.allFieldValid = function() {
        if ($scope.newPasswordInfo.isValid && $scope.newPasswordConfirmInfo.isValid) {
          return true;
        }
        return false;
      };
      $scope.save = function() {
        var data;
        if (!$scope.allFieldValid()) {
          return false;
        }
        $scope.isLoading = true;
        data = {
          login: $scope.getParams().login,
          password: $scope.getParams().password,
          interfaceName: $scope.$root.instanceName,
          newPassword: $scope.newPasswordInfo.field
        };
        downloadService.postJson('/awac/login', data, function(result) {
          if (result.success) {
            $scope.$root.loginSuccess(result.data);
            messageFlash.displaySuccess("You are now connected");
            $scope.isLoading = false;
            return $scope.close();
          } else {
            messageFlash.displayError(result.data.message);
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.CONNECTION_PASSWORD_CHANGE);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalPasswordChange", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-password-change.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.validatePasswordConfirmField = function() {
        if ($scope.newPasswordConfirmInfo.field.match("^\\S{5,20}$")) {
          if ($scope.newPasswordInfo.field === $scope.newPasswordConfirmInfo.field) {
            return true;
          }
          $scope.newPasswordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_CONFIRMATION";
        } else {
          $scope.newPasswordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_LENGTH";
        }
        return false;
      };
      $scope.oldPasswordInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        field: "",
        hideIsValidIcon: true,
        focus: function() {
          return true;
        }
      };
      $scope.newPasswordInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        hideIsValidIcon: true,
        field: ""
      };
      $scope.newPasswordConfirmInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_PLACEHOLDER",
        validationFct: $scope.validatePasswordConfirmField,
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        hideIsValidIcon: true,
        field: ""
      };
      $scope.allFieldValid = function() {
        if ($scope.oldPasswordInfo.isValid && $scope.newPasswordInfo.isValid && $scope.newPasswordConfirmInfo.isValid) {
          return true;
        }
        return false;
      };
      $scope.save = function() {
        var data;
        if (!$scope.allFieldValid()) {
          return false;
        }
        $scope.isLoading = true;
        data = {
          oldPassword: $scope.oldPasswordInfo.field,
          newPassword: $scope.newPasswordInfo.field
        };
        downloadService.postJson('/awac/user/password/save', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess("CHANGES_SAVED");
            return $scope.close();
          } else {
            messageFlash.displayError(result.data.message);
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.PASSWORD_CHANGE);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacGraphDonut", function($sce, $filter) {
  return {
    restrict: "E",
    scope: {
      ngItems: '='
    },
    templateUrl: "$/angular/templates/mm-awac-graph-donut.html",
    replace: true,
    link: function(scope, element) {
      scope.legend = '';
      return scope.$watch('ngItems', function() {
        var color, colorh, ctx, d, data, f, i, myDoughnutChart, total, _len, _len2;
        if (scope.ngItems != null) {
          ctx = $('.holder', element).get(0).getContext('2d');
          data = angular.copy(scope.ngItems);
          f = $filter('numberToI18N');
          total = 0;
          for (i = 0, _len = data.length; i < _len; i++) {
            d = data[i];
            total += d.value;
          }
          for (i = 0, _len2 = data.length; i < _len2; i++) {
            d = data[i];
            color = tinycolor({
              h: 360.0 * i / data.length,
              s: 0.5,
              l: 0.5
            }).toHexString(true);
            colorh = tinycolor({
              h: 360.0 * i / data.length,
              s: 0.75,
              l: 0.66
            }).toHexString(true);
            d.color = color;
            d.highlight = colorh;
            d.label += ' (<b>' + f(100.0 * d.value / total) + '%</b>)';
          }
          myDoughnutChart = new Chart(ctx).Doughnut(data, {
            tooltipTemplate: function(o) {
              return f(100.0 * (o.endAngle - o.startAngle) / (Math.PI * 2)) + "% (" + f(o.value) + " tCO2e)";
            },
            animation: false,
            legendTemplate: "" + "<% for (var i=0; i<segments.length; i++){%>" + "<div><span class=\"chart-legend-bullet-color\" style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></div>" + "<%}%>"
          });
          return scope.legend = $sce.trustAsHtml(myDoughnutChart.generateLegend());
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacModalQuestionComment", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-question-comment.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.close = function() {
        return modalService.close(modalService.QUESTION_COMMENT);
      };
      $scope.comment = $scope.ngParams.comment;
      return $scope.save = function() {
        $scope.ngParams.save($scope.comment);
        return $scope.close();
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
});angular.module('app.directives').directive("mmAwacModalEmailChange", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-email-change.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.passwordInfo = {
        field: "",
        fieldType: "password",
        fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE",
        placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^\\S{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        hideIsValidIcon: true,
        focus: function() {
          return true;
        }
      };
      $scope.oldEmailInfo = {
        field: $scope.getParams().oldEmail,
        fieldTitle: "EMAIL_CHANGE_FORM_OLD_EMAIL_FIELD_TITLE",
        disabled: true
      };
      $scope.newEmailInfo = {
        field: "",
        fieldTitle: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_TITLE",
        placeholder: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_PLACEHOLDER",
        validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT",
        hideIsValidIcon: true
      };
      $scope.allFieldValid = function() {
        if ($scope.passwordInfo.isValid && $scope.newEmailInfo.isValid) {
          return true;
        }
        return false;
      };
      $scope.save = function() {
        var data;
        if (!$scope.allFieldValid()) {
          return false;
        }
        $scope.isLoading = true;
        data = {
          password: $scope.passwordInfo.field,
          newEmail: $scope.newEmailInfo.field
        };
        downloadService.postJson('/awac/user/email/save', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess("CHANGES_SAVED");
            $scope.close();
            if ($scope.getParams().cb != null) {
              return $scope.getParams().cb($scope.newEmailInfo.field);
            }
          } else {
            messageFlash.displayError(result.data.message);
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.EMAIL_CHANGE);
      };
    },
    link: function(scope) {}
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
      ngDataToCompare: '=',
      ngIsAggregation: '='
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
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        return scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
    }
  };
});angular.module('app.directives').directive("focusMe", function($timeout, $parse) {
  return {
    restrict: 'A',
    scope: {
      focusMe: '='
    },
    link: function(scope, element, attrs) {
      scope.$watch('focusMe', function() {
        if (scope.focusMe === true) {
          return element[0].focus();
        }
      });
      return;
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
});angular.module('app.directives').directive("mmAwacModalEditSite", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-edit-site.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.createNewSite = true;
      if ($scope.getParams().site != null) {
        $scope.site = angular.copy($scope.getParams().site);
        $scope.createNewSite = false;
      } else {
        $scope.site = {};
        $scope.site.percentOwned = 100;
      }
      $scope.fields = {
        name: {
          fieldTitle: "NAME",
          field: $scope.site.name,
          validationRegex: "^.{1,255}$",
          validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH",
          focus: function() {
            return true;
          }
        },
        description: {
          fieldTitle: "DESCRIPTION",
          validationRegex: "^.{0,65000}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_TEXT",
          field: $scope.site.description,
          hideIsValidIcon: true
        },
        nace: {
          fieldTitle: "SITE_MANAGER_NACE_CODE",
          validationRegex: "^.{0,255}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_STRING",
          field: $scope.site.naceCode,
          hideIsValidIcon: true
        },
        orgStructure: {
          fieldTitle: "SITE_MANAGER_ORGANIZATIONAL_STRUCTURE",
          validationRegex: "^.{0,255}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_STRING",
          field: $scope.site.organizationalStructure,
          hideIsValidIcon: true
        },
        ecoInterest: {
          fieldTitle: "SITE_MANAGER_ECONOMIC_INTEREST",
          validationRegex: "^.{0,255}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_STRING",
          field: $scope.site.economicInterest,
          hideIsValidIcon: true
        },
        opePolicy: {
          fieldTitle: "SITE_MANAGER_OPERATING_POLICY",
          validationRegex: "^.{0,255}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_STRING",
          field: $scope.site.operatingPolicy,
          hideIsValidIcon: true
        },
        accountingTreatment: {
          fieldTitle: "SITE_MANAGER_ACCOUNTING_TREATMENT",
          validationRegex: "^.{0,255}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_STRING",
          field: $scope.site.accountingTreatment,
          hideIsValidIcon: true
        },
        percentOwned: {
          fieldTitle: "SITE_MANAGER_PERCENT_OWNED",
          validationFct: function() {
            if ($scope.fields.percentOwned.field >= 0 && $scope.fields.percentOwned.field <= 100) {
              return true;
            }
            return false;
          },
          validationMessage: "CONTROL_FIELD_DEFAULT_PERCENT_MAX_100",
          field: $scope.site.percentOwned,
          fieldType: 'double'
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid()) {
          data = {};
          data.name = $scope.fields.name.field;
          data.description = $scope.fields.description.field;
          data.naceCode = $scope.fields.nace.field;
          data.organizationalStructure = $scope.fields.orgStructure.field;
          data.economicInterest = $scope.fields.ecoInterest.field;
          data.operatingPolicy = $scope.fields.opePolicy.field;
          data.accountingTreatment = $scope.fields.accountingTreatment.field;
          data.percentOwned = $scope.fields.percentOwned.field;
          $scope.isLoading = true;
          if ($scope.getParams().site != null) {
            data.id = $scope.getParams().site.id;
            downloadService.postJson('/awac/site/edit', data, function(result) {
              if (result.success) {
                messageFlash.displaySuccess("CHANGES_SAVED");
                $scope.getParams().site.name = $scope.fields.name.field;
                $scope.getParams().site.description = $scope.fields.description.field;
                $scope.getParams().site.naceCode = $scope.fields.nace.field;
                $scope.getParams().site.organizationalStructure = $scope.fields.orgStructure.field;
                $scope.getParams().site.economicInterest = $scope.fields.ecoInterest.field;
                $scope.getParams().site.operatingPolicy = $scope.fields.opePolicy.field;
                $scope.getParams().site.accountingTreatment = $scope.fields.accountingTreatment.field;
                $scope.getParams().site.percentOwned = $scope.fields.percentOwned.field;
                return $scope.close();
              } else {
                messageFlash.displayError(result.data.message);
                return $scope.isLoading = false;
              }
            });
          } else {
            downloadService.postJson('/awac/site/create', data, function(result) {
              if (result.success) {
                messageFlash.displaySuccess("CHANGES_SAVED");
                $scope.$root.organization.sites[$scope.$root.organization.sites.length] = result.data;
                return $scope.close();
              } else {
                messageFlash.displayError(result.data.message);
                return $scope.isLoading = false;
              }
            });
          }
        }
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.EDIT_SITE);
      };
    },
    link: function(scope) {}
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
});angular.module('app.directives').directive("mmAwacQuestion", function(directiveService, translationService, $compile, $timeout, modalService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngRepetitionMap: '=',
      ngAggregation: '=',
      ngTabSet: '=',
      ngTab: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-question.html",
    replace: true,
    compile: function() {
      return {
        post: function(scope, element) {
          directiveService.autoScopeImpl(scope);
          scope.$watch('ngAggregation', function() {
            if (scope.getAggregation() != null) {
              return scope.getAnswer().value = scope.getAggregation();
            }
          });
          scope.$watch('$parent.o', function() {
            scope.getTemplate(true);
            return scope.getTemplate(false);
          });
          scope.getTemplate = function(dataToCompare) {
            var answerType, directive, directiveName, isAggregation, toCompare;
            if ($('.inject-data:first', element).html() === '') {
              toCompare = "";
              isAggregation = "false";
              directiveName = "";
              if (dataToCompare === true) {
                toCompare = "true";
              } else {
                toCompare = "false";
              }
              if (scope.getAggregation() != null) {
                isAggregation = "true";
              }
              if (scope.getQuestion() !== null) {
                answerType = scope.getQuestion().answerType;
                if (answerType === 'BOOLEAN') {
                  directiveName = "boolean-question";
                } else if (answerType === 'INTEGER') {
                  directiveName = "real-question";
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
                directive = $compile("<mm-awac-" + directiveName + " ng-data-to-compare=\"" + toCompare + "\" ng-is-aggregation=\"" + isAggregation + "\"></mm-awac-" + directiveName + ">")(scope);
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
              if ((scope.ngAggregation != null) && scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getTabSet(), scope.getTab()).value === null) {
                scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getTabSet(), scope.getTab()).value = scope.getAggregation();
                scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getTabSet(), scope.getTab()).isAggregation = true;
              }
              return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getTabSet(), scope.getTab());
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
            if (scope.getAggregation() != null) {
              return;
            } else {
              if (scope.getAnswer().value !== null) {
                if (scope.getAnswer().value.length === 0) {
                  scope.getAnswer().value = null;
                }
              }
              scope.getAnswer().wasEdited = true;
              return scope.getAnswer().lastUpdateUser = scope.$root.currentPerson.identifier;
            }
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
                  if (scope.$parent.loading === false) {
                    scope.getAnswer().value = null;
                    scope.edited();
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
                    scope.edited();
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
            if (scope.getAggregation() != null) {
              return;
            }
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
              if (scope.getAnswer(true).unitCode != null) {
                scope.getAnswer().unitCode = scope.getAnswer(true).unitCode;
              }
              if (scope.getAnswer(true).comment != null) {
                scope.getAnswer().comment = scope.getAnswer(true).comment;
              }
              return scope.edited();
            }
          };
          scope.logQuestionCode = function() {
            console.log(scope.getQuestionCode() + ",value:" + scope.getAnswer().value + ",wasEdited:" + scope.getAnswer().wasEdited);
            return console.log(scope.getAnswer());
          };
          scope.errorMessage = "";
          scope.getIcon = function() {
            if (scope.ngAggregation != null) {
              return 'glyphicon-cog';
            }
            if ((scope.getQuestion() != null) && scope.getQuestion().answerType === 'DOCUMENT') {
              return 'glyphicon-file';
            }
            return 'glyphicon-share-alt';
          };
          scope.setErrorMessage = function(errorMessage) {
            scope.errorMessage = errorMessage;
            if (scope.lastTimeOut != null) {
              $timeout.cancel(scope.lastTimeOut);
            }
            return scope.lastTimeOut = $timeout(function() {
              scope.errorMessage = "";
              return scope.lastTimeOut = null;
            }, 2000);
          };
          scope.saveComment = function(comment) {
            scope.getAnswer().comment = comment;
            return scope.getAnswer().wasEdited = true;
          };
          return scope.editComment = function(canBeEdited) {
            var args;
            if (canBeEdited == null) {
              canBeEdited = true;
            }
            args = {};
            args.comment = scope.getAnswer().comment;
            args.save = scope.saveComment;
            args.canBeEdited = canBeEdited;
            return modalService.show(modalService.QUESTION_COMMENT, args);
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
    controller: function($scope, downloadService) {
      $('#modalLogin').modal({
        backdrop: 'static'
      });
      $('#modalLogin').modal('hide');
      $('#modalLogin').on('shown.bs.modal', function(e) {
        $scope.initialize();
        return $scope.$apply();
      });
      $scope.initialize = function() {
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
        var data;
        if ($scope.allFieldValid()) {
          $scope.errorMessage = "";
          $scope.isLoading = true;
          data = {
            login: $scope.loginInfo.field,
            password: $scope.passwordInfo.field
          };
          downloadService.postJson('/awac/login', data, function(result) {
            if (result.success) {
              $scope.$parent.setCurrentUser(result.data);
              return $('#modalLogin').modal('hide');
            } else {
              $scope.errorMessage = "Error : " + result.data.message;
              return $scope.isLoading = false;
            }
          });
        }
        return false;
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacResultTable", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-result-table.html",
    replace: true,
    transclude: true,
    link: function(scope, element) {
      directiveService.autoScopeImpl(scope);
      scope.getTotalScope1 = function() {
        var rl, total, _i, _len, _ref;
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.scope1Value;
        }
        return total;
      };
      scope.getTotalScope2 = function() {
        var rl, total, _i, _len, _ref;
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.scope2Value;
        }
        return total;
      };
      scope.getTotalScope3 = function() {
        var rl, total, _i, _len, _ref;
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.scope3Value;
        }
        return total;
      };
      scope.getTotalOutOfScope = function() {
        var rl, total, _i, _len, _ref;
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.outOfScopeValue;
        }
        return total;
      };
      return scope.getNumber = function(rl) {
        var index, l, result, _i, _len, _ref;
        result = null;
        index = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          l = _ref[_i];
          if (l.scope1Value + l.scope2Value + l.scope3Value + l.outOfScopeValue > 0) {
            index += 1;
            if (l.indicatorName === rl.indicatorName) {
              result = index;
              break;
            }
          }
        }
        return result;
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
        return scope.$root.$broadcast('CONDITION');
      });
    }
  };
});angular.module('app.directives').directive("mmAwacMunicipalitySurvey", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-municipality-survey.html",
    transclude: true,
    replace: true,
    controller: 'MainCtrl',
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
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.isValidationDefined = (scope.getInfo().validationRegex != null) || (scope.getInfo().validationFct != null);
      scope.hideIsValidIcon = !!scope.getInfo().hideIsValidIcon;
      scope.fieldType = (scope.getInfo().fieldType != null) ? scope.getInfo().fieldType : "text";
      if (!(scope.getInfo().field != null)) {
        scope.getInfo().field = "";
      }
      if (!(scope.getInfo().isValid != null)) {
        scope.getInfo().isValid = !scope.isValidationDefined;
      }
      if (scope.isValidationDefined) {
        scope.$watch('getInfo().field', function(n, o) {
          if (n !== o) {
            return scope.isValid();
          }
        });
      }
      scope.isValid = function() {
        var isValid;
        isValid = true;
        if (scope.getInfo().validationRegex != null) {
          isValid = scope.getInfo().field.match(scope.getInfo().validationRegex);
        }
        if (scope.getInfo().validationFct != null) {
          isValid = isValid && scope.getInfo().validationFct();
        }
        scope.getInfo().isValid = isValid;
        return;
      };
      scope.isValid();
      return scope.logField = function() {
        return console.log(scope.getInfo());
      };
    }
  };
});angular.module('app.directives').directive("mmAwacSelectQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
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
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
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
});angular.module('app.directives').directive("mmAwacModalInviteUser", function(directiveService, downloadService, translationService, messageFlash, $rootScope) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-invite-user.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.inviteEmailInfo = {
        field: "",
        fieldTitle: "USER_INVITATION_EMAIL_FIELD_TITLE",
        placeholder: "USER_INVITATION_EMAIL_FIELD_PLACEHOLDER",
        validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT",
        focus: function() {
          return true;
        }
      };
      $scope.allFieldValid = function() {
        if ($scope.inviteEmailInfo.isValid) {
          return true;
        }
        return false;
      };
      $scope.save = function() {
        var data;
        console.log("entering save() of Invite users");
        if (!$scope.allFieldValid()) {
          return false;
        }
        $scope.isLoading = true;
        data = {
          invitationEmail: $scope.inviteEmailInfo.field,
          organization: $rootScope.organization
        };
        downloadService.postJson('/awac/invitation', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess("CHANGES_SAVED");
            return $scope.close();
          } else {
            messageFlash.displayError(result.data.message);
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.INVITE_USER);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacIntegerQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
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
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        return scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
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
      ngDataToCompare: '=',
      ngIsAggregation: '='
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
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        return scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
    }
  };
});angular.module('app.directives').directive("mmAwacModalDocumentManager", function(directiveService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-modal-document-manager.html",
    controller: function($scope, modalService, $location, $window) {
      var modalName;
      $scope.listDocuments = [];
      $('#modalDocumentManager').modal({
        backdrop: false
      });
      $('#modalDocumentManager').modal('show');
      modalName = modalService.DOCUMENT_MANAGER;
      $scope.show = false;
      $scope.loc = null;
      $scope.$on('SHOW_MODAL_' + modalName, function(event, args) {
        if (args.show) {
          $scope.display();
        } else {
          $scope.close();
        }
        return $scope.listDocuments = args.params['listDocuments'];
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
        url = '/awac/file/download/' + storedFileId;
        return $window.open(url);
      };
      return $scope.removeDoc = function(storedFileId) {};
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalAddUserSite", function(directiveService, downloadService, translationService, messageFlash, $rootScope) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-add-user-site.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      if ($scope.getParams().site != null) {
        $scope.site = angular.copy($scope.getParams().site);
        $scope.createNewSite = false;
      } else {
        $scope.site = {};
        $scope.site.percentOwned = 100;
      }
      $scope.allFieldValid = function() {
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          data = {
            organization: $rootScope.organization,
            site: $scope.getParams().site,
            selectedAccounts: $scope.selection
          };
          downloadService.postJson('/awac/organization/site/associatedaccounts/save', data, function(result) {
            if (result.success) {
              messageFlash.displaySuccess("CHANGES_SAVED");
              return $scope.close();
            } else {
              messageFlash.displayError(result.data.message);
              return $scope.isLoading = false;
            }
          });
        }
        return false;
      };
      $scope.accounts = [];
      $scope.selection = [];
      $scope.toggleSelection = function(account) {
        var idx;
        console.log("entering toggleSelection");
        console.log(account.identifier);
        idx = $scope.selection.indexOf(account);
        console.log(idx);
        if (idx > -1) {
          return $scope.selection.splice(idx, 1);
        } else {
          return $scope.selection.push(account);
        }
      };
      $scope.close = function() {
        return modalService.close(modalService.ADD_USER_SITE);
      };
      return $scope.getAssociatedUsers = function() {
        var data;
        console.log("entering getAssociatedUsers");
        $scope.selection = [];
        $scope.accounts = [];
        $scope.prepare = [];
        data = {
          organization: $rootScope.organization,
          site: $scope.getParams().site
        };
        downloadService.postJson('/awac/organization/site/associatedaccounts/load', data, function(result) {
          var selected, user, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3, _results;
          if (result.success) {
            _ref = result.data.organizationUserList;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              user = _ref[_i];
              console.log(user);
              console.log(result.data.siteSelectedUserList.length);
              $scope["in"] = false;
              if (result.data.siteSelectedUserList.length) {
                _ref2 = result.data.siteSelectedUserList;
                for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                  selected = _ref2[_j];
                  if (user.identifier === selected.identifier) {
                    $scope["in"] = true;
                  }
                }
              }
              if ($scope["in"] === false) {
                $scope.accounts.push(user);
              }
            }
            _ref3 = result.data.siteSelectedUserList;
            _results = [];
            for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
              selected = _ref3[_k];
              console.log(selected);
              $scope.selection.push(selected);
              _results.push($scope.accounts.push(selected));
            }
            return _results;
          } else {
            return messageFlash.displayError(result.data.message);
          }
        });
        return $scope.isLoading = false;
      };
    },
    link: function(scope) {
      console.log("entering mmAwacModalAddUserSite");
      return scope.getAssociatedUsers();
    }
  };
});angular.module('app.directives').directive("numbersOnly", function($filter, translationService, $locale) {
  return {
    restrict: 'A',
    require: "ngModel",
    link: function(scope, element, attrs, modelCtrl) {
      var convertToFloat, convertToString, displayError, filterFloat, nbDecimal;
      if (attrs.numbersOnly === "integer") {
        scope.errorMessage = translationService.get('FIELD_INTEGER_ERROR_MESSAGE');
      } else {
        scope.errorMessage = translationService.get('FIELD_DECIMAL_ERROR_MESSAGE');
      }
      nbDecimal = 2;
      if (attrs.numbersOnly === "integer") {
        nbDecimal = 0;
      }
      scope.$root.$on('$localeChangeSuccess', function(event, current, previous) {
        var result;
        if (modelCtrl.$modelValue != null) {
          result = convertToString(parseFloat(modelCtrl.$modelValue));
          if (result != null) {
            modelCtrl.$setViewValue(result.toString());
            return modelCtrl.$render();
          }
        }
      });
      modelCtrl.$parsers.unshift(function(viewValue) {
        var result, resultString, resultToDisplay;
        if (viewValue === "") {
          return null;
        }
        result = convertToFloat(viewValue);
        if (isNaN(result)) {
          displayError();
          if (scope.lastValidValue != null) {
            resultString = scope.lastValidValue.toString();
            if (attrs.numbersOnly === "percent") {
              resultToDisplay = (scope.lastValidValue * 100).toString();
            } else {
              resultToDisplay = scope.lastValidValue.toString();
            }
          } else {
            resultString = "";
            resultToDisplay = "";
          }
          modelCtrl.$setViewValue(resultToDisplay);
          modelCtrl.$render();
        } else {
          if (attrs.numbersOnly === "percent") {
            result = result / 100;
          }
          scope.lastValidValue = result;
          resultString = result.toString();
        }
        if (resultString === "") {
          return null;
        }
        return resultString;
      });
      modelCtrl.$formatters.unshift(function(modelValue) {
        var result;
        result = parseFloat(modelValue);
        if (attrs.numbersOnly === "percent") {
          result = result * 100;
        }
        return convertToString(result);
      });
      displayError = function() {
        if (scope.$parent != null) {
          return scope.$parent.setErrorMessage(scope.errorMessage);
        }
      };
      convertToString = function(value) {
        var formats, result;
        if (!(value != null) || isNaN(value)) {
          return "";
        }
        value = value.toFixed(nbDecimal);
        formats = $locale.NUMBER_FORMATS;
        return result = value.toString().replace(new RegExp("\\.", "g"), formats.DECIMAL_SEP);
      };
      convertToFloat = function(viewValue) {
        var decimalRegex, formats, value;
        if (viewValue === "") {
          return NaN;
        }
        formats = $locale.NUMBER_FORMATS;
        decimalRegex = formats.DECIMAL_SEP;
        if (decimalRegex === ".") {
          decimalRegex = "\\.";
        }
        value = viewValue.replace(new RegExp(decimalRegex, "g"), ".");
        return filterFloat(value);
      };
      filterFloat = function(value) {
        var regexFloat;
        if (attrs.numbersOnly === "integer") {
          regexFloat = new RegExp("^(\\-|\\+)?([0-9]+|Infinity)$");
        } else {
          regexFloat = new RegExp("^(\\-|\\+)?([0-9]+(\\.[0-9]*)?|Infinity)$");
        }
        if (regexFloat.test(value)) {
          return Number(value);
        }
        return NaN;
      };
      return scope.$root.$on('FORM_LOADING_FINISH', function(event, current, previous) {
        if (modelCtrl.$modelValue != null) {
          return scope.lastValidValue = modelCtrl.$modelValue;
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacRealQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
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
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        return scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
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
      ngCondition: '=',
      ngTabSet: '=',
      ngTab: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-repetition-question.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      if (scope.getTabSet() != null) {
        scope.$parent.addTabSet(scope.getTabSet(), scope.getTab(), scope.getRepetitionMap());
      }
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
});angular.module('app.directives').directive("ngEscape", function() {
  return function(scope, element, attrs) {
    return element.bind("keydown keypress", function(event) {
      if (event.which === 27) {
        scope.$apply(function() {
          return scope.$eval(attrs.ngEscape);
        });
        return event.preventDefault();
      }
    });
  };
});angular.module('app.directives').directive("mmAwacModalLoading", function(directiveService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-modal-loading.html",
    controller: function($scope, modalService) {
      return $scope.close = function() {
        return modalService.close(modalService.LOADING);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacRealWithUnitQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
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
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
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
      }
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
});angular.module('app.directives').directive("mmAwacRegistrationMunicipality", function(directiveService, downloadService, messageFlash) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-registration-municipality.html",
    replace: true,
    controller: function($scope) {
      $scope.validatePasswordConfirmField = function() {
        if ($scope.passwordInfo.field === $scope.passwordConfirmInfo.field) {
          return true;
        }
        $scope.passwordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_CONFIRMATION";
        return false;
      };
      $scope.identifierInfo = {
        fieldTitle: "USER_IDENTIFIER",
        validationRegex: "[a-zA-Z0-9-]{5,20}",
        validationMessage: "IDENTIFIER_CHECK_WRONG"
      };
      $scope.lastNameInfo = {
        fieldTitle: "USER_LASTNAME",
        validationRegex: "^.{1,255}$",
        validationMessage: "USER_LASTNAME_WRONG_LENGTH"
      };
      $scope.firstNameInfo = {
        fieldTitle: "USER_FIRSTNAME",
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
        focus: function() {
          return $scope.$parent.tabActive[2];
        }
      };
      $scope.emailInfo = {
        fieldTitle: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_TITLE",
        validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
      };
      $scope.passwordInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE",
        fieldType: "password",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
      };
      $scope.passwordConfirmInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
        fieldType: "password",
        validationFct: $scope.validatePasswordConfirmField,
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
      };
      $scope.municipalityNameInfo = {
        fieldTitle: "MUNICIPALITY_NAME",
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "MUNICIPALITY_NAME_WRONG_LENGTH"
      };
      $scope.registrationFieldValid = function() {
        if ($scope.identifierInfo.isValid && $scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid && $scope.emailInfo.isValid && $scope.passwordInfo.isValid && $scope.passwordConfirmInfo.isValid && $scope.municipalityNameInfo.isValid) {
          return true;
        }
        return false;
      };
      return $scope.registration = function() {
        var data;
        $scope.isLoading = true;
        data = {};
        data.person = {};
        data.person.email = $scope.emailInfo.field;
        data.person.identifier = $scope.identifierInfo.field;
        data.person.firstName = $scope.firstNameInfo.field;
        data.person.lastName = $scope.lastNameInfo.field;
        data.password = $scope.passwordInfo.field;
        data.municipalityName = $scope.municipalityNameInfo.field;
        data.person.defaultLanguage = $scope.$root.language;
        downloadService.postJson('/municipality/registration', data, function(result) {
          if (result.success) {
            $scope.$root.loginSuccess(result.data);
            messageFlash.displaySuccess("You are now connected");
            return $scope.isLoading = false;
          } else {
            messageFlash.displayError(result.data.message);
            return $scope.isLoading = false;
          }
        });
        return false;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalConfirmationExitForm", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-confirmation-exit-form.html",
    controller: function($scope, modalService) {
      directiveService.autoScope;
      $scope.close = function() {
        return modalService.close(modalService.CONFIRMATION_EXIT_FORM);
      };
      $scope["continue"] = function() {
        var arg;
        arg = {};
        arg.loc = $scope.ngParams.loc;
        arg.confirmed = true;
        $scope.$root.$broadcast('NAV', arg);
        return $scope.close();
      };
      return $scope.save = function() {
        var arg;
        arg = {};
        arg.loc = $scope.ngParams.loc;
        arg.confirmed = true;
        $scope.$root.$broadcast('SAVE_AND_NAV', arg);
        return $scope.close();
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalManager", function(directiveService, $compile) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngCondition: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-manager.html",
    replace: true,
    link: function(scope, element) {
      scope.$on('SHOW_MODAL', function(event, args) {
        if (args.show === true) {
          scope.displayModal(args.target, args.params);
        } else {
          scope.removeModal(args.target);
        }
        return;
      });
      scope.displayModal = function(target, params) {
        var directive, paramName;
        paramName = 'params_' + target.replace(/-/g, "_");
        scope[paramName] = params;
        console.log("display mode");
        console.log(params);
        console.log("<mm-awac-modal-" + target + " ng-params=\"" + paramName + "\" ></mm-awac-modal-" + target + ">");
        directive = $compile("<mm-awac-modal-" + target + " ng-params=\"" + paramName + "\" ></mm-awac-modal-" + target + ">")(scope);
        element.append(directive);
        return;
      };
      return scope.removeModal = function(target) {
        var child, _i, _len, _ref;
        _ref = element.children();
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          child = _ref[_i];
          if (child.tagName.toLowerCase() === 'mm-awac-modal-' + target.toLowerCase()) {
            angular.element(child).remove();
          }
        }
        return;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacAdminBadImporter", function(directiveService, $timeout, ngTableParams, $http, $filter, downloadService, messageFlash, modalService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-admin-bad-importer.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      var url;
      scope.total_bad = 0;
      scope.total_info = 0;
      scope.total_warning = 0;
      scope.total_error = 0;
      url = "awac/admin/badImporter";
      scope["import"] = function() {
        scope.total_bad = 0;
        scope.total_bad_info = 0;
        scope.total_bad_warning = 0;
        scope.total_bad_error = 0;
        scope.total_question = 0;
        scope.total_question_info = 0;
        scope.total_question_warning = 0;
        scope.total_question_error = 0;
        modalService.show(modalService.LOADING);
        return downloadService.getJson(url, function(result) {
          var logBad, logLine, logQuestion, _i, _len, _ref;
          if (!result.success) {
            messageFlash.displayError(result.data.message);
            return modalService.close(modalService.LOADING);
          } else {
            logBad = [];
            logQuestion = [];
            _ref = result.data.logLines;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              logLine = _ref[_i];
              logLine.messagesWarningNb = 0;
              logLine.messagesInfoNb = 0;
              logLine.messagesErrorNb = 0;
              if (logLine.logCategory === 'BAD') {
                scope.total_bad++;
                logBad.push(logLine);
                if (logLine.messages['WARNING'] != null) {
                  logLine.messagesWarningNb = logLine.messages['WARNING'].length;
                  scope.total_bad_warning++;
                }
                if (logLine.messages['INFO'] != null) {
                  logLine.messagesInfoNb = logLine.messages['INFO'].length;
                  scope.total_bad_info++;
                }
                if (logLine.messages['ERROR'] != null) {
                  logLine.messagesErrorNb = logLine.messages['ERROR'].length;
                  scope.total_bad_error++;
                }
              }
              if (logLine.logCategory === 'QUESTION') {
                scope.total_question++;
                logQuestion.push(logLine);
                if (logLine.messages['WARNING'] != null) {
                  logLine.messagesWarningNb = logLine.messages['WARNING'].length;
                  scope.total_question_warning++;
                }
                if (logLine.messages['INFO'] != null) {
                  logLine.messagesInfoNb = logLine.messages['INFO'].length;
                  scope.total_question_info++;
                }
                if (logLine.messages['ERROR'] != null) {
                  logLine.messagesErrorNb = logLine.messages['ERROR'].length;
                  scope.total_question_error++;
                }
              }
            }
            scope.tableParams = new ngTableParams({
              page: 1,
              count: 100,
              sorting: {
                code: "asc"
              }
            }, {
              total: 0,
              getData: function($defer, params) {
                var orderedData;
                orderedData = $filter("orderBy")(logBad, params.orderBy());
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                return params.total(logBad.length);
              }
            });
            scope.tableParams2 = new ngTableParams({
              page: 1,
              count: 100,
              sorting: {
                code: "asc"
              }
            }, {
              total: 0,
              getData: function($defer, params) {
                var orderedData;
                orderedData = $filter("orderBy")(logQuestion, params.orderBy());
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                return params.total(logQuestion.length);
              }
            });
            return modalService.close(modalService.LOADING);
          }
        });
      };
      return;
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
});angular.module('app.controllers').controller("FormCtrl", function($scope, downloadService, messageFlash, translationService, modalService, formIdentifier, $timeout, displayFormMenu) {
  $scope.formIdentifier = formIdentifier;
  $scope.displayFormMenu = displayFormMenu;
  $scope.tabSet = {};
  $scope.dataToCompare = null;
  $scope.answerList = [];
  $scope.mapRepetition = {};
  /*
  illustration of the structures
  $scope.mapRepetition['A15'] = [{'A15':1},
                                  {'A15':2}]

  $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                 {'A16':2,'A15':1}]
  */
  $scope.loading = true;
  modalService.show(modalService.LOADING);
  downloadService.getJson("/awac/answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, function(result) {
    var answerSave, args, qSet, questionSetDTO, _i, _j, _len, _len2, _ref, _ref2;
    if (!result.success) {
      messageFlash.displayError('Unable to load data...');
      return modalService.close(modalService.LOADING);
    } else {
      console.log(result.data);
      $scope.o = angular.copy(result.data);
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
        var answer, _i, _len, _ref;
        _ref = $scope.answerList;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          answer = _ref[_i];
          $scope.createTabWatcher(answer);
        }
        return $timeout(function() {
          var answer, _i, _len, _ref;
          modalService.close(modalService.LOADING);
          $scope.loading = false;
          _ref = $scope.answerList;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            answer = _ref[_i];
            if (answer.hasValidCondition === false && answer.value !== null) {
              answer.value = null;
            }
          }
          return $scope.$root.$broadcast('FORM_LOADING_FINISH');
        }, 0);
      }, 0);
      return;
    }
  });
  $scope.$on('SAVE_AND_NAV', function(event, args) {
    return $scope.save(args);
  });
  $scope.$on('SAVE', function() {
    return $scope.save();
  });
  $scope.save = function(argToNav) {
    var answer, listAnswerToSave, _i, _len, _ref;
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
      messageFlash.displayInfo(translationService.get('ALL_ANSWERS_ALREADY_SAVED'));
      return modalService.close(modalService.LOADING);
    } else {
      $scope.o.answersSave.listAnswers = listAnswerToSave;
      return downloadService.postJson('/awac/answer/save', $scope.o.answersSave, function(result) {
        var answer, founded, i, it, j, key, listToRemove, repetition, repetitionToRemove, _i, _j, _k, _len, _len2, _len3, _ref, _ref2;
        if (result.success) {
          listToRemove = [];
          _ref = Object.keys($scope.mapRepetition);
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            key = _ref[_i];
            if (key !== '$$hashKey') {
              i = 0;
              while (i < $scope.mapRepetition[key].length) {
                repetition = $scope.mapRepetition[key][i];
                founded = false;
                _ref2 = $scope.answerList;
                for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                  answer = _ref2[_j];
                  if ((answer.value != null) && (!(answer.isAggregation != null) || answer.isAggregation !== true) && $scope.compareRepetitionMap(answer.mapRepetition, repetition)) {
                    founded = true;
                    break;
                  }
                }
                if (founded === false) {
                  it = listToRemove.length;
                  listToRemove[it] = {};
                  listToRemove[it].code = key;
                  listToRemove[it].iteration = {};
                  listToRemove[it].iteration[key] = $scope.mapRepetition[key][i][key];
                  j = angular.copy($scope.mapRepetition[key][i]);
                  delete j[key];
                  listToRemove[it].map = j;
                }
                i++;
              }
            }
          }
          for (_k = 0, _len3 = listToRemove.length; _k < _len3; _k++) {
            repetitionToRemove = listToRemove[_k];
            $scope.removeIteration(repetitionToRemove.code, repetitionToRemove.iteration, repetitionToRemove.map);
          }
          i = $scope.answerList.length - 1;
          while (i >= 0) {
            answer = $scope.answerList[i];
            if (answer.wasEdited === true) {
              answer.wasEdited = false;
            }
            if (answer.toRemove === true) {
              $scope.answerList.splice(i, 1);
            }
            i--;
          }
          $scope.saveFormProgress();
          messageFlash.displaySuccess(translationService.get('ANSWERS_SAVED'));
          modalService.close(modalService.LOADING);
          if (argToNav !== null) {
            $scope.$root.$broadcast('NAV', argToNav);
          }
          return;
        } else {
          messageFlash.displayError(translationService.get('ERROR_THROWN_ON_SAVE') + data.message);
          modalService.close(modalService.LOADING);
          return;
        }
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
  $scope.getAnswerOrCreate = function(code, mapIteration, tabSet, tab) {
    var answerLine, defaultUnitCode, question, result, value, wasEdited;
    if (tabSet == null) {
      tabSet = null;
    }
    if (tab == null) {
      tab = null;
    }
    if (code === null || code === void 0) {
      console.log("ERROR !! getAnswerOrCreate : code is null or undefined");
      return null;
    }
    result = $scope.getAnswer(code, mapIteration);
    if (result) {
      if (result.toRemove != null) {
        result.toRemove = false;
      }
      if ((tabSet != null) && !(result.tabSet != null)) {
        result.tabSet = tabSet;
        result.tab = tab;
      }
      return result;
    } else {
      value = null;
      defaultUnitCode = null;
      wasEdited = false;
      if ($scope.getQuestion(code) !== null) {
        question = $scope.getQuestion(code);
        if (question.defaultValue !== null) {
          value = question.defaultValue;
          wasEdited = true;
        }
        if (question.unitCategoryId != null) {
          if (question.defaultUnit != null) {
            defaultUnitCode = question.defaultUnit.code;
          } else {
            defaultUnitCode = $scope.getUnitCategories(code).mainUnitCode;
          }
        }
      }
      answerLine = {
        'questionKey': code,
        'value': value,
        'unitCode': defaultUnitCode,
        'mapRepetition': mapIteration,
        'lastUpdateUser': $scope.$root.currentPerson.identifier,
        'wasEdited': wasEdited
      };
      if (tabSet != null) {
        answerLine.tabSet = tabSet;
        answerLine.tab = tab;
      }
      $scope.createTabWatcher(answerLine);
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
          $scope.answerList[len].toRemove = true;
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
    if ($scope.$parent !== null && $scope.$parent.periodToCompare !== 'default') {
      return downloadService.getJson('/awac/answer/getByForm/' + $scope.formIdentifier + "/" + $scope.$parent.periodToCompare + "/" + $scope.$parent.scopeId, function(result) {
        if (result.success) {
          return $scope.dataToCompare = result.data;
        } else {
          ;
        }
      });
    } else {
      return $scope.dataToCompare = null;
    }
  });
  $scope.saveFormProgress = function() {
    var answer, answered, formProgress, formProgressDTO, founded, key, listTotal, percentage, tabSet, total, _i, _j, _k, _l, _len, _len2, _len3, _len4, _len5, _len6, _m, _n, _ref, _ref2, _ref3, _ref4, _ref5, _ref6;
    percentage = 0;
    total = 0;
    answered = 0;
    listTotal = [];
    _ref = $scope.answerList;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (!(answer.tabSet != null)) {
        if ($scope.getQuestion(answer.questionKey).answerType !== 'DOCUMENT' && answer.isAggregation !== true) {
          if (answer.hasValidCondition === void 0 || answer.hasValidCondition === null || answer.hasValidCondition === true) {
            total++;
            listTotal[listTotal.length] = answer;
            if (answer.value !== null) {
              answered++;
            }
          }
        }
      }
    }
    _ref2 = Object.keys($scope.tabSet);
    for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
      key = _ref2[_j];
      if (key !== '$$hashKey') {
        _ref3 = $scope.tabSet[key];
        for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
          tabSet = _ref3[_k];
          if (tabSet.master != null) {
            _ref4 = $scope.answerList;
            for (_l = 0, _len4 = _ref4.length; _l < _len4; _l++) {
              answer = _ref4[_l];
              if ((answer.tabSet != null) && parseFloat(answer.tabSet) === parseFloat(key) && parseFloat(answer.tab) === parseFloat(tabSet.master)) {
                total++;
                answered++;
              }
            }
          } else {
            _ref5 = $scope.answerList;
            for (_m = 0, _len5 = _ref5.length; _m < _len5; _m++) {
              answer = _ref5[_m];
              if ((answer.tabSet != null) && parseFloat(answer.tabSet) === parseFloat(key) && parseFloat(answer.tab) === 1) {
                total++;
                if (answer.value !== null) {
                  answered++;
                }
              }
            }
          }
        }
      }
    }
    if (answered === 0) {
      percentage = 0;
    } else {
      percentage = answered / total * 100;
      percentage = Math.floor(percentage);
    }
    console.log("PROGRESS : " + answered + "/" + total + "=" + percentage);
    console.log(listTotal);
    formProgressDTO = {};
    formProgressDTO.form = $scope.formIdentifier;
    formProgressDTO.period = $scope.$parent.periodKey;
    formProgressDTO.scope = $scope.$parent.scopeId;
    formProgressDTO.percentage = percentage;
    founded = false;
    _ref6 = $scope.$parent.formProgress;
    for (_n = 0, _len6 = _ref6.length; _n < _len6; _n++) {
      formProgress = _ref6[_n];
      if (formProgress.form === $scope.formIdentifier) {
        founded = true;
        formProgress.percentage = percentage;
      }
    }
    if (founded === false) {
      $scope.$parent.formProgress[$scope.$parent.formProgress.length] = formProgressDTO;
    }
    return downloadService.postJson('/awac/answer/formProgress', formProgressDTO, function(result) {
      if (result.success) {
        return;
      } else {
        ;
      }
    });
  };
  $scope.addTabSet = function(tabSet, tab, mapRepetition) {
    var i, ite, _ref;
    ite = null;
    if (!($scope.tabSet[tabSet] != null)) {
      $scope.tabSet[tabSet] = [];
      $scope.tabSet[tabSet][0] = {};
      $scope.tabSet[tabSet][0].mapRepetition = mapRepetition;
      ite = 0;
    } else {
      i = 0;
      while (i < $scope.tabSet[tabSet].length) {
        if ($scope.compareRepetitionMap(mapRepetition, $scope.tabSet[tabSet][i].mapRepetition)) {
          ite = i;
          break;
        }
        i++;
      }
      if (ite === null) {
        ite = $scope.tabSet[tabSet].length;
        $scope.tabSet[tabSet][ite] = {};
        $scope.tabSet[tabSet][ite].mapRepetition = mapRepetition;
      }
    }
    if (!($scope.tabSet[tabSet][ite][tab] != null)) {
      $scope.tabSet[tabSet][ite][tab] = {};
      $scope.tabSet[tabSet][ite][tab].active = (_ref = tab === 1) != null ? _ref : {
        "true": false
      };
    }
    if (!($scope.tabSet[tabSet][ite][tab].listToCompute != null)) {
      $scope.tabSet[tabSet][ite][tab].listToCompute = [];
    }
    return ite;
  };
  $scope.createTabWatcher = function(answer) {
    var answerToTest, i, ite, j, tab, tabSet;
    if (answer.tabSet != null) {
      tabSet = answer.tabSet;
      tab = answer.tab;
      ite = $scope.addTabSet(tabSet, tab, answer.mapRepetition);
      /*
      ite=null

      if !$scope.tabSet[tabSet]?
          $scope.tabSet[tabSet] = []
          $scope.tabSet[tabSet][0] = {}
          $scope.tabSet[tabSet][0].mapRepetition = answer.mapRepetition
          ite = 0
      else
          i=0
          while i < $scope.tabSet[tabSet].length
              if $scope.compareRepetitionMap(answer.mapRepetition, $scope.tabSet[tabSet][i].mapRepetition)
                  ite = i
                  break
              i++
          if ite == null
              ite = $scope.tabSet[tabSet].length
              $scope.tabSet[tabSet][ite] = {}
              $scope.tabSet[tabSet][ite].mapRepetition = answer.mapRepetition

      if !$scope.tabSet[tabSet][ite][tab]?
          $scope.tabSet[tabSet][ite][tab] = {}
          $scope.tabSet[tabSet][ite][tab].active = (tab == 1 ? true:false)

      if !$scope.tabSet[tabSet][ite][tab].listToCompute?
          $scope.tabSet[tabSet][ite][tab].listToCompute = []
      */
      j = $scope.tabSet[tabSet][ite][tab].listToCompute.length;
      j--;
      while (j >= 0) {
        answerToTest = $scope.tabSet[tabSet][ite][tab].listToCompute[j];
        if (answerToTest.questionKey === answer.questionKey && $scope.compareRepetitionMap(answer.mapRepetition, answerToTest.mapRepetition)) {
          $scope.tabSet[tabSet][ite][tab].listToCompute.splice(j, 1);
        }
        j--;
      }
      i = $scope.tabSet[tabSet][ite][tab].listToCompute.length;
      $scope.tabSet[tabSet][ite][tab].listToCompute[i] = answer;
      $scope.$watch('tabSet[' + tabSet + '][' + ite + '][' + tab + '].listToCompute[' + i + '].value', function() {
        return $scope.computeTab(tabSet, tab, answer.mapRepetition);
      });
      if (answer.value === null) {
        return $scope.tabSet[tabSet][ite][tab].isFinish = false;
      }
    }
  };
  $scope.computeTab = function(tabSet, tab, mapRepetition) {
    var answer, i, isFinish, ite, key, value, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3, _results;
    isFinish = false;
    i = 0;
    while (i < $scope.tabSet[tabSet].length) {
      if ($scope.compareRepetitionMap(mapRepetition, $scope.tabSet[tabSet][i].mapRepetition)) {
        ite = i;
        break;
      }
      i++;
    }
    _ref = $scope.tabSet[tabSet][ite][tab].listToCompute;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      answer = _ref[_i];
      if (!(answer.hasValidCondition != null) || answer.hasValidCondition === true && $scope.getQuestion(answer.questionKey).answerType !== 'DOCUMENT' && answer.isAggregation !== true) {
        if (answer.value === null) {
          isFinish = false;
          break;
        } else {
          isFinish = true;
        }
      }
    }
    if (isFinish !== $scope.tabSet[tabSet][ite][tab].isFinish) {
      $scope.tabSet[tabSet][ite][tab].isFinish = isFinish;
      if (isFinish === true) {
        if (!($scope.tabSet[tabSet][ite].master != null) || $scope.tabSet[tabSet][ite].master > tab) {
          $scope.tabSet[tabSet][ite].master = tab;
        }
      } else if ($scope.tabSet[tabSet][ite].master === tab) {
        delete $scope.tabSet[tabSet][ite].master;
        _ref2 = Object.keys($scope.tabSet[tabSet][ite]);
        for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
          key = _ref2[_j];
          if (key !== '$$hashKey' && key !== 'master' && key !== 'active' && key !== 'mapRepetition') {
            value = $scope.tabSet[tabSet][ite][key];
            if (value.isFinish === true) {
              if (!($scope.tabSet[tabSet][ite].master != null) || parseFloat($scope.tabSet[tabSet][ite].master) > parseFloat(key)) {
                $scope.tabSet[tabSet][ite].master = parseFloat(key);
              }
            }
          }
        }
      }
      if ($scope.loading === true && ($scope.tabSet[tabSet][ite].master != null)) {
        _ref3 = Object.keys($scope.tabSet[tabSet][ite]);
        _results = [];
        for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
          key = _ref3[_k];
          _results.push(key !== '$$hashKey' && key !== 'master' && key !== 'active' && key !== 'mapRepetition' ? parseFloat(key) === parseFloat($scope.tabSet[tabSet][ite].master) ? $scope.tabSet[tabSet][ite][key].active = true : $scope.tabSet[tabSet][ite][key].active = false : void 0);
        }
        return _results;
      }
    }
  };
  $scope.getTab = function(tabSet, tab, mapRepetition) {
    var tabSetToTest, _i, _len, _ref, _ref2;
    if (mapRepetition == null) {
      mapRepetition = null;
    }
    if ($scope.loading === true || !($scope.tabSet[tabSet] != null)) {
      $scope.addTabSet(tabSet, tab, mapRepetition);
    }
    _ref = $scope.tabSet[tabSet];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      tabSetToTest = _ref[_i];
      if ($scope.compareRepetitionMap(mapRepetition, tabSetToTest.mapRepetition)) {
        if (!(tabSetToTest[tab] != null)) {
          tabSetToTest[tab] = {};
          tabSetToTest[tab].active = (_ref2 = parseFloat(tab) === 1) != null ? _ref2 : {
            "true": false
          };
          return tabSetToTest[tab];
        }
        return tabSetToTest[tab];
      }
    }
    return null;
  };
  return $scope.tabIsMaster = function(tabSet, tab, mapRepetition) {
    var tabSetToTest, _i, _len, _ref;
    if (mapRepetition == null) {
      mapRepetition = null;
    }
    if ($scope.loading === true || !($scope.tabSet[tabSet] != null)) {
      return false;
    }
    _ref = $scope.tabSet[tabSet];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      tabSetToTest = _ref[_i];
      if ($scope.compareRepetitionMap(mapRepetition, tabSetToTest.mapRepetition)) {
        if (tabSetToTest.master === parseFloat(tab)) {
          return true;
        }
        return false;
      }
    }
    return false;
  };
});angular.module('app.controllers').controller("UserDataCtrl", function($scope, downloadService, translationService, messageFlash, modalService, $timeout) {
  $scope.isLoading = false;
  $scope.identifierInfo = {
    fieldTitle: "USER_IDENTIFIER",
    disabled: true,
    field: $scope.$root.currentPerson.identifier
  };
  $scope.passwordInfo = {
    fieldTitle: "USER_PASSWORD",
    fieldType: "password",
    disabled: true,
    field: "*****"
  };
  $scope.lastNameInfo = {
    fieldTitle: "USER_LASTNAME",
    validationRegex: "^.{1,255}$",
    validationMessage: "USER_LASTNAME_WRONG_LENGTH",
    field: $scope.$root.currentPerson.lastName,
    hideIsValidIcon: true,
    isValid: true,
    focus: function() {
      return true;
    }
  };
  $scope.firstNameInfo = {
    fieldTitle: "USER_FIRSTNAME",
    fieldType: "text",
    validationRegex: "^.{1,255}$",
    validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
    field: $scope.$root.currentPerson.firstName,
    hideIsValidIcon: true,
    isValid: true
  };
  $scope.emailInfo = {
    fieldTitle: "USER_EMAIL",
    disabled: true,
    field: $scope.$root.currentPerson.email
  };
  $scope.allFieldValid = function() {
    if ($scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid) {
      return true;
    }
    return false;
  };
  $scope.$root.refreshUserData();
  $scope.send = function() {
    var data;
    if (!$scope.allFieldValid) {
      return false;
    }
    $scope.isLoading = true;
    data = {
      identifier: $scope.identifierInfo.field,
      lastName: $scope.lastNameInfo.field,
      firstName: $scope.firstNameInfo.field,
      email: $scope.emailInfo.field
    };
    downloadService.postJson('/awac/user/profile/save', data, function(result) {
      if (result.success) {
        messageFlash.displaySuccess("CHANGES_SAVED");
        $scope.$root.currentPerson.lastName = $scope.lastNameInfo.field;
        $scope.$root.currentPerson.firstName = $scope.firstNameInfo.field;
        return $scope.isLoading = false;
      } else {
        messageFlash.displayError(result.data.message);
        return $scope.isLoading = false;
      }
    });
    return false;
  };
  $scope.setNewEmail = function(newEmail) {
    $scope.emailInfo.field = newEmail;
    return $scope.$root.currentPerson.email = newEmail;
  };
  $scope.changeEmail = function() {
    return modalService.show(modalService.EMAIL_CHANGE, {
      oldEmail: $scope.emailInfo.field,
      cb: $scope.setNewEmail
    });
  };
  $scope.changePassword = function() {
    return modalService.show(modalService.PASSWORD_CHANGE, {});
  };
  return $scope.toForm = function() {
    return $scope.$parent.navToLastFormUsed();
  };
});angular.module('app').run(function(loggerService) {
  var log;
  loggerService.initialize();
  $('body').keydown(function(evt) {
    var loggerElement, state;
    console.log(evt);
    if (evt.which === 32 && evt.altKey && evt.ctrlKey) {
      loggerElement = $('#logger');
      state = parseInt(loggerElement.attr('data-state'));
      return loggerElement.attr('data-state', (state + 1) % 3);
    }
  });
  log = loggerService.get('initializer');
  return log.info("Application is started");
});
angular.module('app.controllers').controller("MainCtrl", function($scope, downloadService, translationService, $sce, $location, $route, $routeParams, modalService, $timeout) {
  $scope.displayMenu = true;
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
  $scope.isMenuCurrentlySelected = function(loc) {
    if ($location.path().substring(0, loc.length) === loc) {
      return true;
    } else {
      return false;
    }
  };
  window.onbeforeunload = function(event) {
    var canBeContinue, result, _base;
    canBeContinue = true;
    if ((typeof $scope.getMainScope === "function" ? $scope.getMainScope().validNavigation : void 0) !== void 0) {
      result = typeof $scope.getMainScope === "function" ? typeof (_base = $scope.getMainScope()).validNavigation === "function" ? _base.validNavigation() : void 0 : void 0;
      if (result.valid === false) {
        return translationService.get('MODAL_CONFIRMATION_EXIT_FORM_MESSAGE');
      }
    }
  };
  $scope.$on('NAV', function(event, args) {
    return $scope.nav(args.loc, args.confirmed);
  });
  $scope.nav = function(loc, confirmed) {
    var canBeContinue, params, result, _base;
    if (confirmed == null) {
      confirmed = false;
    }
    console.log("NAV : " + loc);
    canBeContinue = true;
    if ((typeof $scope.getMainScope === "function" ? $scope.getMainScope().validNavigation : void 0) !== void 0 && confirmed === false) {
      result = typeof $scope.getMainScope === "function" ? typeof (_base = $scope.getMainScope()).validNavigation === "function" ? _base.validNavigation() : void 0 : void 0;
      if (result.valid === false) {
        canBeContinue = false;
        params = {};
        params.loc = loc;
        modalService.show(result.modalForConfirm, params);
      }
    }
    if (canBeContinue) {
      return $location.path(loc + "/" + $scope.periodKey + "/" + $scope.scopeId);
    }
  };
  $scope.$on('$routeChangeSuccess', function(event, args) {
    return $timeout(function() {
      return $scope.computeDisplayMenu();
    }, 0);
  });
  $scope.computeDisplayMenu = function() {
    if (($scope.getMainScope() != null) && ($scope.getMainScope().displayFormMenu != null) && $scope.getMainScope().displayFormMenu === true) {
      return $scope.displayMenu = true;
    } else {
      return $scope.displayMenu = false;
    }
  };
  $scope.navToLastFormUsed = function() {
    return $scope.nav($scope.$root.getFormPath());
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
      'label': translationService.get('NO_PERIOD_SELECTED')
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
    var url;
    url = '/awac/answer/getPeriodsForComparison/' + $scope.scopeId;
    if (($scope.scopeId != null) && !isNaN($scope.scopeId)) {
      return downloadService.getJson(url, function(result) {
        var period, _i, _len, _ref, _results;
        if (result.success) {
          $scope.periodsForComparison = [
            {
              'key': 'default',
              'label': translationService.get('NO_PERIOD_SELECTED')
            }
          ];
          _ref = result.data.periodDTOList;
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            period = _ref[_i];
            _results.push(period.key !== $scope.periodKey ? $scope.periodsForComparison[$scope.periodsForComparison.length] = period : void 0);
          }
          return _results;
        } else {
          ;
        }
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
    if (($scope.scopeId != null) && ($scope.periodKey != null)) {
      return downloadService.getJson("/awac/answer/formProgress/" + $scope.periodKey + "/" + $scope.scopeId, function(result) {
        if (result.success) {
          return $scope.formProgress = result.data.listFormProgress;
        } else {
          ;
        }
      });
    }
  };
  $scope.$on("REFRESH_LAST_SAVE_TIME", function(event, args) {
    var date, minuteToAdd;
    if (args !== void 0) {
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
  return $scope.getClassContent = function() {
    if ($scope.$root.hideHeader() === false) {
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
angular.module('app').run(function($rootScope, $location, downloadService, messageFlash, $timeout, translationService, tmhDynamicLocale, $routeParams) {
  $rootScope.languages = [];
  $rootScope.languages[0] = {
    value: 'fr',
    label: 'Français'
  };
  $rootScope.languages[1] = {
    value: 'en',
    label: 'English'
  };
  $rootScope.languages[2] = {
    value: 'nl',
    label: 'Neederlands'
  };
  translationService.initialize('fr');
  $rootScope.language = 'fr';
  $rootScope.$watch('language', function(lang) {
    translationService.initialize(lang);
    return tmhDynamicLocale.set(lang.toLowerCase());
  });
  $rootScope.$on('$routeChangeStart', function(event, current) {
    $rootScope.key = $routeParams.key;
    if (!$rootScope.currentPerson) {
      return downloadService.postJson('/awac/testAuthentication', {
        interfaceName: $rootScope.instanceName
      }, function(result) {
        if (result.success) {
          return $rootScope.loginSuccess(result.data, !$rootScope.isLogin());
        } else {
          if (!current.$$route.anonymousAllowed) {
            console.log("redirect to login ");
            return $location.path('/login');
          }
        }
      });
    } else {
      return console.log("default router processing");
    }
  });
  $rootScope.getRegisterKey = function() {
    return $rootScope.key;
  };
  $rootScope.isLogin = function() {
    return $location.path().substring(0, 6) === "/login";
  };
  $rootScope.hideHeader = function() {
    console.log("hideHeader:" + $location.path().substring(0, 13));
    return $location.path().substring(0, 6) === "/login" || $location.path().substring(0, 13) === "/registration";
  };
  $rootScope.logout = function() {
    return downloadService.postJson('/awac/logout', null, function(result) {
      if (result.success) {
        $rootScope.currentPerson = null;
        return $location.path('/login');
      } else {
        return $location.path('/login');
      }
    });
  };
  $rootScope.loginSuccess = function(data, skipRedirect) {
    $rootScope.periods = data.availablePeriods;
    $rootScope.currentPerson = data.person;
    $rootScope.organization = data.organization;
    $rootScope.users = data.organization.users;
    if (!skipRedirect) {
      return $rootScope.onFormPath(data.defaultPeriod, data.organization.sites[0].scope);
    }
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
  $rootScope.refreshUserData = function() {
    return downloadService.getJson('/awac/user/profile', function(result) {
      if (result.success) {
        return $rootScope.currentPerson = result.data;
      } else {
        return messageFlash.displayError(result.data.message);
      }
    });
  };
  $rootScope.refreshNotifications = function() {
    downloadService.getJson('/awac/notifications/get_notifications', function(result) {
      var n, _i, _len, _ref, _results;
      if (result.success) {
        _ref = result.data.notifications;
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          n = _ref[_i];
          _results.push(messageFlash.display(n.kind.toLowerCase(), n.messageFr, {
            hideAfter: 3600
          }));
        }
        return _results;
      } else {
        ;
      }
    });
    return $timeout($rootScope.refreshNotifications, 3600 * 1000);
  };
  return $rootScope.refreshNotifications();
});angular.module('app.controllers').controller("AdminCtrl", function($scope, downloadService) {
  $scope.notifications = [];
  return downloadService.getJson("admin/get_notifications", function(dto) {
    if (dto != null) {
      $scope.notifications = dto.notifications;
    }
    return;
  });
});angular.module('app.controllers').controller("RegistrationCtrl", function($scope, downloadService, $location, messageFlash, $compile, $timeout, modalService, translationService, $routeParams) {
  $scope.loading = false;
  $scope.tabActive = [];
  $scope.enterEvent = function() {
    if ($scope.tabActive[0] === true) {
      return $scope.send();
    }
  };
  $scope.loginInfo = {
    fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE",
    fieldType: "text",
    placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER",
    validationRegex: "^\\S{5,20}$",
    validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH",
    field: "",
    isValid: false,
    focus: function() {
      return $scope.tabActive[0];
    }
  };
  $scope.passwordInfo = {
    fieldTitle: "LOGIN_FORM_PASSWORD_FIELD_TITLE",
    fieldType: "password",
    validationRegex: "^\\S{5,20}$",
    validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
    field: "",
    isValid: false
  };
  $scope.lastNameInfo = {
    fieldTitle: "USER_LASTNAME",
    validationRegex: "^.{1,255}$",
    validationMessage: "USER_LASTNAME_WRONG_LENGTH",
    hideIsValidIcon: true,
    isValid: true,
    focus: function() {
      return true;
    }
  };
  $scope.firstNameInfo = {
    fieldTitle: "USER_FIRSTNAME",
    fieldType: "text",
    validationRegex: "^.{1,255}$",
    validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
    hideIsValidIcon: true,
    isValid: true
  };
  $scope.emailInfo = {
    fieldTitle: "USER_EMAIL"
  };
  $scope.connectionFieldValid = function() {
    if ($scope.loginInfo.isValid && $scope.passwordInfo.isValid && $scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid && $scope.emailInfo.isValid) {
      return true;
    }
    return false;
  };
  $scope.send = function() {
    $scope.isLoading = true;
    downloadService.postJson('/awac/register', {
      login: $scope.loginInfo.field,
      password: $scope.passwordInfo.field,
      lastName: $scope.lastNameInfo.field,
      firstName: $scope.firstNameInfo.field,
      interfaceName: $scope.$root.instanceName,
      email: $scope.emailInfo.field,
      key: $routeParams.key
    }, function(result) {
      if (result.success) {
        messageFlash.displaySuccess("You are now registered. Please log-in.");
        $scope.isLoading = false;
        return $location.path('/login');
      } else {
        messageFlash.displaySuccess("Registered fails. Please try again.");
        $scope.isLoading = false;
        return messageFlash.displayError(result.data.message);
      }
    });
    return false;
  };
  $scope.injectRegistrationDirective = function() {
    var directive, directiveName;
    if ($scope.$root != null) {
      if ($scope.$root.instanceName === 'enterprise') {
        directiveName = "mm-awac-registration-enterprise";
      } else if ($scope.$root.instanceName === 'municipality') {
        directiveName = "mm-awac-registration-municipality";
      }
      directive = $compile("<" + directiveName + "></" + directiveName + ">")($scope);
      return $('.inject-registration-form').append(directive);
    }
  };
  return $timeout(function() {
    return $scope.injectRegistrationDirective();
  }, 0);
});angular.module('app.controllers').controller("SiteManagerCtrl", function($scope, translationService, modalService, downloadService) {
  $scope.isLoading = [{}, {}];
  $scope.assignPeriod = [$scope.$root.periods[1].key, $scope.$root.periods[0].key];
  $scope.isPeriodChecked = [{}, {}];
  $scope.$watch('$scope.assignPeriod', function() {
    var site, _i, _len, _ref, _results;
    _ref = $scope.$root.organization.sites;
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      site = _ref[_i];
      $scope.isPeriodChecked[0][site.id] = $scope.periodAssignTo(0, site);
      $scope.isPeriodChecked[1][site.id] = $scope.periodAssignTo(1, site);
      _results.push(console.log($scope.isPeriodChecked));
    }
    return _results;
  });
  $scope.toForm = function() {
    return $scope.$parent.navToLastFormUsed();
  };
  $scope.getSiteList = function() {
    return $scope.$root.organization.sites;
  };
  $scope.editOrCreateSite = function(site) {
    var params;
    params = {};
    if (site != null) {
      params.site = site;
    }
    return modalService.show(modalService.EDIT_SITE, params);
  };
  $scope.addUsers = function(site) {
    var params;
    params = {};
    if (site != null) {
      params.site = site;
    }
    return modalService.show(modalService.ADD_USER_SITE, params);
  };
  $scope.periodAssignTo = function(nbPeriod, site) {
    var period, _i, _len, _ref;
    if (site.listPeriodAvailable != null) {
      _ref = site.listPeriodAvailable;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        period = _ref[_i];
        if (period === $scope.assignPeriod[nbPeriod]) {
          return true;
        }
      }
    }
    return false;
  };
  return $scope.assignPeriodToSite = function(nbPeriod, site) {
    var data;
    data = {};
    data.periodKeyCode = $scope.assignPeriod[nbPeriod];
    data.siteId = site.id;
    data.assign = !$scope.periodAssignTo(nbPeriod, site);
    console.log(data);
    return downloadService.postJson('awac/site/assignPeriodToSite', data, function(result) {});
  };
});angular.module('app.controllers').controller("UserManagerCtrl", function($scope, translationService, modalService, downloadService, messageFlash) {
  $scope.title = translationService.get('USER_MANAGER_TITLE');
  $scope.isLoading = {};
  $scope.isLoading['admin'] = {};
  $scope.isLoading['isActive'] = {};
  $scope.getUserList = function() {
    return $scope.$root.users;
  };
  $scope.inviteUser = function() {
    return modalService.show(modalService.INVITE_USER);
  };
  $scope.getMyself = function() {
    return $scope.$root.currentPerson;
  };
  $scope.activeUser = function(user) {
    var data;
    if ($scope.getMyself().isAdmin === true && $scope.getMyself().email !== user.email) {
      data = {};
      data.identifier = user.identifier;
      data.isActive = !user.isActive;
      $scope.isLoading['isActive'][user.email] = true;
      return downloadService.postJson("/awac/user/activeAccount", data, function(result) {
        if (result.success) {
          user.isActive = !user.isActive;
          return $scope.isLoading['isActive'][user.email] = false;
        } else {
          $scope.isLoading['isActive'][user.email] = false;
          return messageFlash.displayError(result.data.message);
        }
      });
    }
  };
  $scope.isAdminUser = function(user) {
    var data;
    if ($scope.getMyself().isAdmin === true && $scope.getMyself().email !== user.email && user.isActive === true) {
      data = {};
      data.identifier = user.identifier;
      data.isAdmin = !user.isAdmin;
      $scope.isLoading['admin'][user.email] = true;
      return downloadService.postJson("/awac/user/isAdminAccount", data, function(result) {
        if (result.success) {
          return $scope.isLoading['admin'][user.email] = false;
        } else {
          $scope.isLoading['admin'][user.email] = false;
          return messageFlash.displayError(result.data.message);
        }
      });
    }
  };
  return $scope.toForm = function() {
    return $scope.$parent.navToLastFormUsed();
  };
});angular.module('app.controllers').controller("LoginCtrl", function($scope, downloadService, $location, messageFlash, $compile, $timeout, modalService, translationService) {
  $scope.loading = false;
  $scope.tabActive = [];
  $scope.$watch('tabActive[1]', function() {
    if ($scope.tabActive[1] !== true) {
      $scope.forgotEmailSuccessMessage = null;
      return $scope.forgotPasswordInfo.field = "";
    }
  });
  $scope.enterEvent = function() {
    if ($scope.tabActive[0] === true) {
      return $scope.send();
    } else if ($scope.tabActive[1] === true) {
      return $scope.sendForgotPassword();
    }
  };
  $scope.loginInfo = {
    fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE",
    fieldType: "text",
    placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER",
    validationRegex: "^\\S{5,20}$",
    validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH",
    field: "",
    isValid: false,
    focus: function() {
      return $scope.tabActive[0];
    }
  };
  $scope.passwordInfo = {
    fieldTitle: "LOGIN_FORM_PASSWORD_FIELD_TITLE",
    fieldType: "password",
    validationRegex: "^\\S{5,20}$",
    validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
    field: "",
    isValid: false
  };
  $scope.forgotPasswordInfo = {
    fieldTitle: "IDENTIFIENT_OR_EMAIL",
    fieldType: "text",
    validationRegex: "^\\S+$",
    validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
    field: "",
    isValid: false,
    focus: function() {
      return $scope.tabActive[1];
    }
  };
  $scope.connectionFieldValid = function() {
    if ($scope.loginInfo.isValid && $scope.passwordInfo.isValid) {
      return true;
    }
    return false;
  };
  $scope.forgotPasswordFieldValid = function() {
    if ($scope.forgotPasswordInfo.isValid && $scope.forgotPasswordInfo.isValid) {
      return true;
    }
    return false;
  };
  $scope.send = function() {
    $scope.isLoading = true;
    downloadService.postJson('/awac/login', {
      login: $scope.loginInfo.field,
      password: $scope.passwordInfo.field,
      interfaceName: $scope.$root.instanceName
    }, function(result) {
      var params;
      if (result.success) {
        $scope.$root.loginSuccess(result.data);
        messageFlash.displaySuccess("You are now connected");
        return $scope.isLoading = false;
      } else {
        $scope.isLoading = false;
        if (result.data.__type === 'eu.factorx.awac.dto.myrmex.get.MustChangePasswordExceptionsDTO') {
          params = {
            login: $scope.loginInfo.field,
            password: $scope.passwordInfo.field
          };
          return modalService.show(modalService.CONNECTION_PASSWORD_CHANGE, params);
        } else {
          return messageFlash.displayError(result.data.message);
        }
      }
    });
    return false;
  };
  $scope.forgotEmailSuccessMessage = null;
  $scope.sendForgotPassword = function() {
    $scope.isLoading = true;
    downloadService.postJson('/awac/forgotPassword', {
      identifier: $scope.forgotPasswordInfo.field,
      interfaceName: $scope.$root.instanceName
    }, function(result) {
      if (result.success) {
        $scope.forgotEmailSuccessMessage = translationService.get('LOGIN_FORGOT_PASSWORD_SUCCESS');
        $scope.isLoading = false;
        return;
      } else {
        messageFlash.displayError(result.data.message);
        $scope.isLoading = false;
        return;
      }
    });
    return false;
  };
  $scope.injectRegistrationDirective = function() {
    var directive, directiveName;
    if ($scope.$root != null) {
      if ($scope.$root.instanceName === 'enterprise') {
        directiveName = "mm-awac-registration-enterprise";
      } else if ($scope.$root.instanceName === 'municipality') {
        directiveName = "mm-awac-registration-municipality";
      }
      directive = $compile("<" + directiveName + "></" + directiveName + ">")($scope);
      return $('.inject-registration-form').append(directive);
    }
  };
  return $timeout(function() {
    return $scope.injectRegistrationDirective();
  }, 0);
});angular.module('app.controllers').controller("ResultsCtrl", function($scope, $window, downloadService, displayFormMenu, modalService) {
  $scope.displayFormMenu = displayFormMenu;
  modalService.show(modalService.LOADING);
  downloadService.getJson("/awac/result/getReport/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, function(result) {
    modalService.close(modalService.LOADING);
    if (result.success) {
      return $scope.o = result.data;
    } else {
      ;
    }
  });
  $scope.charts = {
    histogramUrl: "/awac/result/getHistogram/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId,
    webUrl: "/awac/result/getWeb/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId,
    donutUrl1: "/awac/result/getDonut/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId + "/1",
    donutUrl2: "/awac/result/getDonut/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId + "/2",
    donutUrl3: "/awac/result/getDonut/" + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId + "/3"
  };
  $scope.current_tab = 1;
  $scope.downloadAsXls = function() {
    return $window.open('/awac/result/getReportAsXls/' + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, "Downloading report file...", null);
  };
  return $scope.downloadPdf = function() {};
});angular.module('app.directives').run(function($templateCache) {$templateCache.put('$/angular/views/admin.html', "<div><h1>Admin</h1><tabset style=\"margin-top:20px\"><tab class=\"tab-color-lightgreen\"><tab-heading style=\"margin-left:25px\"><span ng-bind-html=\"'BAD Importer'\"></span></tab-heading><div style=\"border-top : 1px solid black\"><div class=\"element_table\"><mm-awac-admin-bad-importer></mm-awac-admin-bad-importer></div></div></tab><tab class=\"tab-color-lightgreen\"><tab-heading><span ng-bind-html=\"'Other .... '\"></span></tab-heading><div style=\"border-top : 1px solid black\"><div class=\"element_table\"><div>something</div></div></div></tab></tabset></div>");$templateCache.put('$/angular/views/login.html', "<div class=\"loginBackground\"><div class=\"router-bar\"><div class=\"awac_logo\"></div><ul class=\"interface_menu\"><li><a ng-class=\"{selected:$root.instanceName == 'enterprise'}\" class=\"btn btn-primary\" href=\"/enterprise\">Entreprises</a></li><li><a ng-class=\"{selected:$root.instanceName == 'municipality'}\" class=\"btn btn-primary\" href=\"/municipality\">Communes</a></li></ul></div><div class=\"loginFrame\" ng-enter=\"enterEvent()\"><select style=\"float:right\" ng-options=\"l.value as l.label for l in $root.languages\" ng-model=\"$root.language\"></select><tabset><tab class=\"tab-color-lightgreen\" active=\"tabActive[0]\"><tab-heading><span ng-bind-html=\"'LOGIN_CONNECTION' | translate\"></span></tab-heading><div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text></div><div ng-hide=\"isLoading === true\"><button class=\"button btn btn-primary\" ng-disabled=\"!connectionFieldValid()\" ng-click=\"send()\" ng-bind-html=\"'LOGIN_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading === true\"></div></tab><tab class=\"tab-color-lightgreen\" active=\"tabActive[1]\" ng-show=\"false\"><tab-heading><span ng-bind-html=\"'LOGIN_FORGOT_PASSWORD' | translate\"></span></tab-heading><div><div class=\"forgot_password_success_message\" ng-show=\"forgotEmailSuccessMessage!=null\">{{forgotEmailSuccessMessage}}</div><div ng-hide=\"forgotEmailSuccessMessage!=null\"><div ng-bind-html=\"'LOGIN_FORGOT_PASSWORD_DESC' | translate\"></div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"forgotPasswordInfo\"></mm-awac-modal-field-text></div><div ng-hide=\"isLoading === true\"><button class=\"button btn btn-primary\" ng-disabled=\"!forgotPasswordFieldValid()\" ng-click=\"sendForgotPassword()\" ng-bind-html=\"'SUBMIT' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading === true\"></div></div></tab><tab class=\"tab-color-lightgreen\" active=\"tabActive[2]\"><tab-heading><span ng-bind-html=\"'LOGIN_REGISTRATION' | translate\"></span></tab-heading><div class=\"inject-registration-form\"></div></tab></tabset></div></div>");$templateCache.put('$/angular/views/site_manager.html', "<div><div class=\"menu_close\" ng-click=\"toForm()\"></div><h1 ng-bind-html=\"'SITE_MANAGER_BUTTON' | translateText\"></h1><div class=\"element\"><table class=\"site_table\"><tr class=\"site_table_header\"><td ng-bind-html=\"'NAME' | translateText\"></td><td ng-bind-html=\"'DESCRIPTION' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_NACE_CODE' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_ORGANIZATIONAL_STRUCTURE' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_ECONOMIC_INTEREST' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_OPERATING_POLICY' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_ACCOUNTING_TREATMENT' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_PERCENT_OWNED' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_EDIT_SITE_BUTTON' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_ADD_USERS_BUTTON' | translateText\"></td><td><select ng-options=\"p.key as p.label for p in $root.periods\" ng-model=\"assignPeriod[0]\"></select></td><td><select ng-options=\"p.key as p.label for p in $root.periods\" ng-model=\"assignPeriod[1]\"></select></td></tr><tr ng-repeat=\"site in getSiteList()\"><td>{{site.name}}</td><td>{{site.description}}</td><td>{{site.naceCode}}</td><td>{{site.organizationalStructure}}</td><td>{{site.economicInterest}}</td><td>{{site.operatingPolicy}}</td><td>{{site.accountingTreatment}}</td><td>{{site.percentOwned}} %</td><td><button title=\"{{'SITE_MANAGER_EDIT_SITE_BUTTON' | translateText}}\" ng-click=\"editOrCreateSite(site)\" class=\"edit_icon glyphicon glyphicon-pencil\" type=\"button\"></button></td><td><button title=\"{{'SITE_MANAGER_ADD_USERS_BUTTON' | translateText}}\" ng-click=\"addUsers(site)\" class=\"edit_icon glyphicon glyphicon-pencil\" type=\"button\"></button></td><td><input ng-click=\"assignPeriodToSite(0,site)\" ng-model=\"isPeriodChecked[0][site.id]\" type=\"checkbox\" ng-hide=\"isLoading[0][site]=== true\"><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading[0][site]=== true\"></td><td><input ng-click=\"assignPeriodToSite(1,site)\" ng-model=\"isPeriodChecked[1][site.id]\" type=\"checkbox\" ng-hide=\"isLoading[1][site]=== true\"><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading[1][site]=== true\"></td></tr></table><button class=\"button add\" ng-click=\"editOrCreateSite()\" ng-bind-html=\"'SITE_MANAGER_ADD_SITE_BUTTON' | translateText\" type=\"button\"></button></div></div>");$templateCache.put('$/angular/views/user_manager.html', "<div><div class=\"menu_close\" ng-click=\"toForm()\"></div><h1>USER MANAGER</h1><div class=\"element\"><button class=\"button add\" ng-click=\"inviteUser()\" type=\"button\" ng-show=\"true\">invite users</button><table class=\"user_table\"><tr class=\"user_table_header\"><td ng-bind-html=\"'NAME' | translate\"></td><td ng-bind-html=\"'USER_MANAGER_ADMINISTRATOR' | translate\"></td><td ng-bind-html=\"'USER_MANAGER_ACTIF' | translate\"></td></tr><tr ng-class=\"{user_deleted : user.isActive === false}\" ng-repeat=\"user in getUserList()\"><td>{{user.firstName}} {{user.lastName}} ({{user.email}})</td><td><input ng-disabled=\"getMyself().isAdmin === false || getMyself().email === user.email || user.isActive == false\" ng-click=\"isAdminUser(user)\" ng-model=\"user.isAdmin\" type=\"checkbox\" ng-hide=\"isLoading['admin'][user.email]=== true\"><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading['admin'][user.email] === true\"></td><td ng-class=\"{is_admin : getMyself().isAdmin === true &amp;&amp; getMyself().email !== user.email}\"><div class=\"button_delete\" ng-click=\"activeUser(user)\" ng-hide=\"isLoading['isActive'][user.email]=== true\"></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading['isActive'][user.email] === true\"></td></tr></table></div></div>");$templateCache.put('$/angular/views/enterprise/TAB5.html', "<div><!--mm-awac-section(\"Déchets\")--><!--It lacks a proper fild code for \"D2chets alone\" -> TODO : insert into Excel file as an additional line--><mm-awac-section title-code=\"A173\"><mm-awac-question question-code=\"A174\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A173\"><mm-awac-repetition-name question-code=\"A175\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A175\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A175')\"><mm-awac-question question-code=\"A176\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A177\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A178\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A179\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A175')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A175_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A180\"><mm-awac-sub-title question-code=\"A181\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"A182\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A183\"></mm-awac-question><mm-awac-question question-code=\"A184\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A185\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A186\"></mm-awac-question><mm-awac-question question-code=\"A187\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A188\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A189\"></mm-awac-question><mm-awac-question question-code=\"A190\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A191\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A192\"></mm-awac-question><mm-awac-question question-code=\"A193\"></mm-awac-question><mm-awac-sub-title question-code=\"A194\"></mm-awac-sub-title><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter les eaux usées industrielles. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><span ng-bind-html=\"'A197' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"A195\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A198\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A199\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A200\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><span ng-bind-html=\"'A201' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A501\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A202\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A203\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A204\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-section></div>");$templateCache.put('$/angular/views/enterprise/TAB2.html', "<div><mm-awac-section title-code=\"A20\"><mm-awac-question question-code=\"A2\"></mm-awac-question><mm-awac-question question-code=\"A3\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value == '1'\" question-code=\"A4\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value == '2' || getAnswer('A3').value == '3'\" question-code=\"A5\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value == '4'\" question-code=\"A6\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value == '4'\" question-code=\"A7\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A7').value == '1'\" question-code=\"A8\"></mm-awac-question><mm-awac-question question-code=\"A9\"></mm-awac-question><mm-awac-question question-code=\"A10\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A3').value != '4'\" question-code=\"A11\"></mm-awac-question><mm-awac-question question-code=\"A12\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A20\"><mm-awac-question question-code=\"A14\"></mm-awac-question><mm-awac-repetition-name question-code=\"A15\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A15\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A15')\"><mm-awac-question question-code=\"A16\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A17\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A15')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A15_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A20\"><mm-awac-question question-code=\"A21\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A22\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A23\"></mm-awac-question><mm-awac-question question-code=\"A24\"></mm-awac-question><mm-awac-repetition-name question-code=\"A25\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A25\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A25')\"><mm-awac-question question-code=\"A26\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A27\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A28\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A25')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A25_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A31\"><mm-awac-question question-code=\"A32\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A32').value == '1'\" question-code=\"A33\"></mm-awac-question><mm-awac-block ng-condition=\"getAnswer('A32').value == '1'\"><mm-awac-repetition-name question-code=\"A34\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A34\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A34')\"><mm-awac-question question-code=\"A35\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A36\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A34')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A34_LOOPDESC' | translate\"></span></button></mm-awac-block></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A37\"><mm-awac-question question-code=\"A38\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A38').value == '1'\" question-code=\"A39\"></mm-awac-question></mm-awac-section><mm-awac-block ng-condition=\"getAnswer('A38').value == '1'\"><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter l’usage des systèmes froids. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><span ng-bind-html=\"'A41' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A42\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A42\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A42')\"><mm-awac-question question-code=\"A43\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A44\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A42')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A42_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><span ng-bind-html=\"'A45' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A46\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question></div></div></tab><mm-awac-block ng-condition=\"getAnswer('A5').value == '1' || getAnswer('A5').value == '2'\"><tab class=\"tab-color-yellow\" active=\"getTab(1,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,3)\"></i><span ng-bind-html=\"'A47' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><mm-awac-block class=\"element_table\" ng-condition=\"getAnswer('A5').value == '1' || getAnswer('A5').value == '2'\"><mm-awac-question question-code=\"A48\" ng-tab-set=\"1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A49\" ng-tab-set=\"1\" ng-tab=\"3\"></mm-awac-question></mm-awac-block></div></tab></mm-awac-block></tabset></div></div></mm-awac-block></div>");$templateCache.put('$/angular/views/enterprise/TAB4.html', "<div><mm-awac-section title-code=\"A205\"><mm-awac-question question-code=\"A206\"></mm-awac-question><mm-awac-sub-title question-code=\"A208\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"A209\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A209\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A209')\"><mm-awac-question question-code=\"A210\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A211\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_61'\" question-code=\"A212\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_62'\" question-code=\"A213\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_63'\" question-code=\"A214\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_64'\" question-code=\"A215\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_65'\" question-code=\"A216\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_66'\" question-code=\"A217\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_67'\" question-code=\"A218\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_68'\" question-code=\"A219\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_61' || getAnswer('A211',itLevel1).value == 'AT_62' || getAnswer('A211',itLevel1).value == 'AT_63' || getAnswer('A211',itLevel1).value == 'AT_64'\" question-code=\"A220\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value != 'AT_68'\" question-code=\"A221\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_68'\" question-code=\"A222\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A209')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A209_LOOPDESC' | translate\"></span></button><mm-awac-sub-title question-code=\"A223\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"A224\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A224\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A224')\"><mm-awac-question question-code=\"A225\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A226\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A227\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A228\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A224')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A224_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A128\"><mm-awac-question question-code=\"A129\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A130\"><mm-awac-sub-title question-code=\"A131\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"A132\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A133\"></mm-awac-question><mm-awac-question question-code=\"A134\"></mm-awac-question><mm-awac-question question-code=\"A135\"></mm-awac-question><mm-awac-question question-code=\"A136\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A136').value == '1'\" question-code=\"A137\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A136').value == '1'\" question-code=\"A138\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A138').value == '1'\" question-code=\"A139\"></mm-awac-question><mm-awac-question ng-aggregation=\"0.484\" ng-condition=\"getAnswer('A138').value == '0'\" question-code=\"A500\"></mm-awac-question><mm-awac-sub-title question-code=\"A140\"></mm-awac-sub-title><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport effectué par des transporteurs. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><span ng-bind-html=\"'A141' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A142\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A142\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A142')\"><mm-awac-question question-code=\"A143\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A145\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A146\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A147\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A148\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A149\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A150\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A151\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A152\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A153\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A154\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A155\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('A147',itLevel1).value | nullToZero)+( getAnswer('A148',itLevel1).value | nullToZero)+(getAnswer('A149',itLevel1).value | nullToZero)+(getAnswer('A150',itLevel1).value| nullToZero)+(getAnswer('A151',itLevel1).value| nullToZero)+(getAnswer('A152',itLevel1).value| nullToZero)+(getAnswer('A153',itLevel1).value| nullToZero)+(getAnswer('A154',itLevel1).value| nullToZero)+(getAnswer('A155',itLevel1).value| nullToZero)\" question-code=\"A156\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A142')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A142_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><span ng-bind-html=\"'A157' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A158\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A159\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"5000\" ng-condition=\"getAnswer('A159').value == '3'\" question-code=\"A160\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"2500\" ng-condition=\"getAnswer('A159').value == '2'\" question-code=\"A161\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"200\" ng-condition=\"getAnswer('A159').value == '1'\" question-code=\"A162\" ng-tab-set=\"1\" ng-tab=\"2\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A163\"><mm-awac-repetition-name question-code=\"A164\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A164\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A164')\"><mm-awac-question question-code=\"A165\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A166\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A166\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A166',itLevel1)\"><mm-awac-question question-code=\"A167\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A168\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A166',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A166_LOOPDESC' | translate\"></span></button><mm-awac-question question-code=\"A169\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A170\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A170\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A170',itLevel1)\"><mm-awac-question question-code=\"A171\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A172\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A170',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A170_LOOPDESC' | translate\"></span></button></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A164')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A164_LOOPDESC' | translate\"></span></button></mm-awac-section></div>");$templateCache.put('$/angular/views/enterprise/TAB6.html', "<ng-virtual><mm-awac-section title-code=\"A229\"><mm-awac-question question-code=\"A230\"></mm-awac-question><mm-awac-repetition-name question-code=\"A231\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A231\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A231')\"><mm-awac-question question-code=\"A232\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A233\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"(getAnswer('A233',itLevel1).value | stringToFloat) &lt; 18   || getAnswer('A233',itLevel1).value == '23'\" question-code=\"A234\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A233',itLevel1).value == '20' || getAnswer('A233',itLevel1).value == '21'|| getAnswer('A233',itLevel1).value == '22'\" question-code=\"A235\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A233',itLevel1).value == '18' || getAnswer('A233',itLevel1).value == '19'\" question-code=\"A236\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A231')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A231_LOOPDESC' | translate\"></span></button><mm-awac-sub-sub-title question-code=\"A237\"></mm-awac-sub-sub-title><mm-awac-repetition-name question-code=\"A238\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A238\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A238')\"><mm-awac-question question-code=\"A239\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A240\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A241\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A242\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A238')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A238_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A309\"><mm-awac-question question-code=\"A310\"></mm-awac-question><mm-awac-repetition-name question-code=\"A311\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A311\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A311')\"><mm-awac-question question-code=\"A312\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A313\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A313\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A313',itLevel1)\"><mm-awac-question question-code=\"A314\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A315\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A313',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A313_LOOPDESC' | translate\"></span></button><mm-awac-question question-code=\"A316\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A317\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A317\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A317',itLevel1)\"><mm-awac-question question-code=\"A318\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A319\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A317',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A317_LOOPDESC' | translate\"></span></button></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A311')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A311_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A320\"><mm-awac-question question-code=\"A321\"></mm-awac-question><mm-awac-repetition-name question-code=\"A322\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A322\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A322')\"><mm-awac-question question-code=\"A323\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A324\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A325\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A325\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A325',itLevel1)\"><mm-awac-question question-code=\"A326\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A327\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A325',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A325_LOOPDESC' | translate\"></span></button><mm-awac-question question-code=\"A328\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A329\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A329\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A329',itLevel1)\"><mm-awac-question question-code=\"A330\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A331\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A329',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A329_LOOPDESC' | translate\"></span></button></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A322')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A322_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A332\"><mm-awac-question question-code=\"A333\"></mm-awac-question><mm-awac-repetition-name question-code=\"A334\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A334\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A334')\"><mm-awac-question question-code=\"A335\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A336\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A337\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A338\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A334')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A334_LOOPDESC' | translate\"></span></button></mm-awac-section></ng-virtual>");$templateCache.put('$/angular/views/enterprise/TAB3.html', "<div><mm-awac-section title-code=\"A50\"><mm-awac-question question-code=\"A51\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A52\"></mm-awac-section><div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport routier. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><span ng-bind-html=\"'A53' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-sub-title question-code=\"A54\"></mm-awac-sub-title><mm-awac-question question-code=\"A55\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A56\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A57\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-sub-title question-code=\"A58\"></mm-awac-sub-title><mm-awac-question question-code=\"A59\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A60\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A61\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-sub-title question-code=\"A62\"></mm-awac-sub-title><mm-awac-question question-code=\"A63\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A64\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A65\" ng-tab-set=\"1\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><span ng-bind-html=\"'A66' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A67\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A67\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A67')\"><mm-awac-question question-code=\"A68\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A69\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A69',itLevel1).value == '0'\" question-code=\"A70\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A71\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A72\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A72',itLevel1).value == '1'\" question-code=\"A73\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A72',itLevel1).value == '2'|| getAnswer('A72',itLevel1).value == '3'|| getAnswer('A72',itLevel1).value == '4'|| getAnswer('A72',itLevel1).value == '5'\" question-code=\"A74\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A72',itLevel1).value == '6' || getAnswer('A72',itLevel1).value == '7'\" question-code=\"A75\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A76\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A67')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A67_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(1,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,3)\"></i><span ng-bind-html=\"'A77' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A78\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A78\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A78')\"><mm-awac-question question-code=\"A79\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A80\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A80',itLevel1).value == '0'\" question-code=\"A81\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A83\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"A88\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A83',itLevel1).value == 'AS_5'\" question-code=\"A89\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A83',itLevel1).value == 'AS_162'\" question-code=\"A90\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A83',itLevel1).value == 'AS_6' || getAnswer('A83',itLevel1).value == 'AS_7'\" question-code=\"A91\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A83',itLevel1).value == 'AS_163'\" question-code=\"A92\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A78')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A78_LOOPDESC' | translate\"></span></button></div></div></tab></tabset></div></div></div><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A93\"></mm-awac-section><div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport en commun. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(2,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,1)\"></i><span ng-bind-html=\"'A94' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"A95\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A96\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A97\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A98\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A99\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A100\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A101\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A102\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A103\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A104\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A105\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A106\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A107\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A108\" ng-tab-set=\"2\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(2,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,2)\"></i><span ng-bind-html=\"'A109' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A110\" ng-tab-set=\"2\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A111\" ng-tab-set=\"2\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A112\" ng-tab-set=\"2\" ng-tab=\"2\"></mm-awac-question></div></div></tab></tabset></div></div></div><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A113\"></mm-awac-section><div><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport en avion. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(3,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,1)\"></i><span ng-bind-html=\"'A114' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A115\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"A115\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A115')\"><mm-awac-question question-code=\"A116\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A117\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A118\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A119\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A120\" ng-tab-set=\"3\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A115')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A115_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(3,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,2)\"></i><span ng-bind-html=\"'A121' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A122\" ng-tab-set=\"3\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A123\" ng-tab-set=\"3\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A123',itLevel1).value == '0'\" question-code=\"A124\" ng-tab-set=\"3\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"2500\" ng-condition=\"getAnswer('A124',itLevel1).value == '1'\" question-code=\"A125\" ng-tab-set=\"3\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"5000\" ng-condition=\"getAnswer('A124',itLevel1).value == '0'\" question-code=\"A126\" ng-tab-set=\"3\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A123',itLevel1).value == '1'\" question-code=\"A127\" ng-tab-set=\"3\" ng-tab=\"2\"></mm-awac-question></div></div></tab></tabset></div></div></div></div>");$templateCache.put('$/angular/views/enterprise/TAB7.html', "<mm-awac-section title-code=\"A243\"><mm-awac-repetition-name question-code=\"A244\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" ng-question-set-code=\"'A244'\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A244')\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A245'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A246'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A247'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A248'\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('A248',itLevel1).value == '2'\" ng-repetition-map=\"itLevel1\" ng-question-code=\"'A249'\"></mm-awac-question><mm-awac-block ng-condition=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Transport--><mm-awac-sub-title question-code=\"A250\"></mm-awac-sub-title><mm-awac-question question-code=\"A251\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A252\"></mm-awac-sub-sub-title><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter le transport aval. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1,itLevel1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1,itLevel1)\"></i><span ng-bind-html=\"'A253' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question question-code=\"A254\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A255\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A256\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A257\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A258\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A259\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A260\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A261\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A262\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A263\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"A264\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('A256',itLevel1).value | nullToZero)+( getAnswer('A257',itLevel1).value | nullToZero)+(getAnswer('A258',itLevel1).value | nullToZero)+(getAnswer('A259',itLevel1).value| nullToZero)+(getAnswer('A260',itLevel1).value| nullToZero)+(getAnswer('A261',itLevel1).value| nullToZero)+(getAnswer('A262',itLevel1).value| nullToZero)+(getAnswer('A263',itLevel1).value| nullToZero)+(getAnswer('A264',itLevel1).value| nullToZero)\" question-code=\"A265\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2,itLevel1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2,itLevel1)\"></i><span ng-bind-html=\"'A266' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question question-code=\"A267\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"A268\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"5000\" ng-condition=\"getAnswer('A268',itLevel1).value == '3'\" question-code=\"A269\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"2500\" ng-condition=\"getAnswer('A268',itLevel1).value == '2'\" question-code=\"A270\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-aggregation=\"200\" ng-condition=\"getAnswer('A268',itLevel1).value == '1'\" question-code=\"A271\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Distribution--><mm-awac-sub-title question-code=\"A272\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"A273\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" ng-question-set-code=\"'A273'\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A273',itLevel1)\"><mm-awac-question question-code=\"A274\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-repetition-name question-code=\"A275\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel3\" question-set-code=\"A275\" ng-repetition-map=\"itLevel2\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A275',itLevel2)\"><mm-awac-question question-code=\"A276\" ng-repetition-map=\"itLevel3\"></mm-awac-question><mm-awac-question question-code=\"A277\" ng-repetition-map=\"itLevel3\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A275',itLevel2)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A275_LOOPDESC' | translate\"></span></button><mm-awac-question question-code=\"A278\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-repetition-name question-code=\"A279\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel3\" question-set-code=\"A279\" ng-repetition-map=\"itLevel2\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A279',itLevel2)\"><mm-awac-question question-code=\"A280\" ng-repetition-map=\"itLevel3\"></mm-awac-question><mm-awac-question question-code=\"A281\" ng-repetition-map=\"itLevel3\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A279',itLevel2)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A279_LOOPDESC' | translate\"></span></button></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A273',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A273_LOOPDESC' | translate\"></span></button></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A249',itLevel1).value == '1'\"><!--Traitement--><mm-awac-sub-title question-code=\"A282\"></mm-awac-sub-title><mm-awac-question question-code=\"A283\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A284\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A284\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A284',itLevel1)\"><mm-awac-question question-code=\"A285\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A286\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A284',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A284_LOOPDESC' | translate\"></span></button><mm-awac-question question-code=\"A287\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A288\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A288\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A288',itLevel1)\"><mm-awac-question question-code=\"A289\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A290\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A288',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A288_LOOPDESC' | translate\"></span></button></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Utilisation--><mm-awac-sub-title question-code=\"A291\"></mm-awac-sub-title><mm-awac-question question-code=\"A292\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A293\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A294\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A295\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A296\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A297\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A297\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A297',itLevel1)\"><mm-awac-question question-code=\"A298\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A299\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A297',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A297_LOOPDESC' | translate\"></span></button></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Fin de vie--><mm-awac-sub-title question-code=\"A300\"></mm-awac-sub-title><mm-awac-question question-code=\"A301\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"A302\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"A303\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"A303\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A303',itLevel1)\"><mm-awac-question question-code=\"A304\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A305\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A306\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A307\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"A308\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A303',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A303_LOOPDESC' | translate\"></span></button></mm-awac-block></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('A244')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'A244_LOOPDESC' | translate\"></span></button></mm-awac-section>");$templateCache.put('$/angular/views/results.html', "<div class=\"pdf-able\"><h1><span ng-bind-html=\"'RESULTS' | translate\"></span></h1><br><div><span ng-bind-html=\"'ACCOMPANIMENT_WORD' | translate\"></span><button><span ng-bind-html=\"'XLS_EXPORT' | translate\"></span></button><button><span ng-bind-html=\"'PDF_EXPORT' | translate\"></span></button></div><table class=\"wide\"><tr><td class=\"top-aligned\"><div class=\"sites-panel\"><div class=\"sites-panel-title\"><span ng-bind-html=\"'SITES_LIST' | translate\"></span></div><div class=\"sites-panel-all-items\"><table><tr><td><span ng-bind-html=\"'ALL_SITES_SELECTED' | translate\"></span></td><td><input type=\"checkbox\"></td></tr></table></div><div class=\"sites-panel-items\"><div class=\"sites-panel-item\"><table><tr><td>Namur</td><td><input type=\"checkbox\"></td></tr></table></div><div class=\"sites-panel-item\"><table><tr><td>Namur</td><td><input type=\"checkbox\"></td></tr></table></div><div class=\"sites-panel-item\"><table><tr><td>Namur</td><td><input type=\"checkbox\"></td></tr></table></div><div class=\"sites-panel-item\"><table><tr><td>Namur</td><td><input type=\"checkbox\"></td></tr></table></div><div class=\"sites-panel-item\"><table><tr><td>Namur</td><td><input type=\"checkbox\"></td></tr></table></div><div class=\"sites-panel-item\"><table><tr><td>Namur</td><td><input type=\"checkbox\"></td></tr></table></div></div></div></td><td class=\"top-aligned horizontally-padded wide\"><div class=\"results_disclaimer\"><span class=\"results_disclaimer_text\" ng-bind-html=\"'RESULTS_DISCLAIMER' | translate\"></span></div><div ng-show=\"current_tab == 1\"><h2><span ng-bind-html=\"'VALUES_BY_CATEGORY' | translate\"></span></h2><br><center><img style=\"display:inline-block; max-width: 15cm;\" ng-src=\"{{ charts.histogramUrl }}\"></center><br><mm-awac-result-table ng-model=\"o\"></mm-awac-result-table></div><div ng-show=\"current_tab == 2\"><h2><span ng-bind-html=\"'IMPACTS_PARTITION' | translate\"></span></h2><br><h3><span ng-bind-html=\"'SCOPE_1' | translate\"></span></h3><center><img style=\"display:inline-block; max-width: 5cm;\" ng-src=\"{{ charts.donutUrl1 }}\"></center><h3><span ng-bind-html=\"'SCOPE_2' | translate\"></span></h3><center><img style=\"display:inline-block; max-width: 5cm;\" ng-src=\"{{ charts.donutUrl2 }}\"></center><h3><span ng-bind-html=\"'SCOPE_3' | translate\"></span></h3><center><img style=\"display:inline-block; max-width: 5cm;\" ng-src=\"{{ charts.donutUrl3 }}\"></center><br><mm-awac-result-table ng-model=\"o\"></mm-awac-result-table></div><div ng-show=\"current_tab == 3\"><h2><span ng-bind-html=\"'KIVIAT_DIAGRAM' | translate\"></span></h2><br><center><img style=\"display:inline-block; max-width: 15cm;\" ng-src=\"{{ charts.webUrl }}\"></center><br><mm-awac-result-table ng-model=\"o\"></mm-awac-result-table></div><div ng-show=\"current_tab == 4\"><h2><span ng-bind-html=\"'NUMBERS' | translate\"></span></h2><mm-awac-result-table ng-model=\"o\"></mm-awac-result-table></div><div ng-show=\"current_tab == 5\"><h2><span ng-bind-html=\"'COMPARISION_WITH_CONSTANT_EMISSION_FACTORS' | translate\"></span></h2></div><div ng-show=\"current_tab == 6\"><h2><span ng-bind-html=\"'CALCULUS_EXPLANATION' | translate\"></span></h2></div><div ng-show=\"current_tab == 7\"><h2><span ng-bind-html=\"'USED_EMISSION_FACTORS' | translate\"></span></h2></div><br><br><br></td><td class=\"top-aligned\"><div class=\"charts-panel-tabset\"><div class=\"charts-panel-tab\" ng-click=\"current_tab = 1\" ng-class=\"{ active: current_tab == 1 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_bars\" ng-bind-html=\"'VALUES_BY_CATEGORY' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 2\" ng-class=\"{ active: current_tab == 2 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_donut\" ng-bind-html=\"'IMPACTS_PARTITION' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 3\" ng-class=\"{ active: current_tab == 3 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_web\" ng-bind-html=\"'KIVIAT_DIAGRAM' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 4\" ng-class=\"{ active: current_tab == 4 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_numbers\" ng-bind-html=\"'NUMBERS' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 5\" ng-class=\"{ active: current_tab == 5 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_constant_factors\" ng-bind-html=\"'COMPARISION_WITH_CONSTANT_EMISSION_FACTORS' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 6\" ng-class=\"{ active: current_tab == 6 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_calculus\" ng-bind-html=\"'CALCULUS_EXPLANATION' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 7\" ng-class=\"{ active: current_tab == 7 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_fe\" ng-bind-html=\"'USED_EMISSION_FACTORS' | translate\"></div></div></div></td></tr></table></div>");$templateCache.put('$/angular/views/change_password.html', "<div class=\"loginBackground\"><div class=\"loginFrame\" ng-enter=\"send()\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"oldPasswordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newPasswordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newPasswordInfo_confirm\"></mm-awac-modal-field-text></div><p style=\"background-color:#ff0000;color:#ffffff;padding:15px\" ng-show=\"errorMessage.length &gt; 0\">{{errorMessage}}</p><button ng-click=\"send()\" ng-bind-html=\"'CHANGE_PASSWORD_BUTTON' | translate\" class=\"btn btn-primary\" type=\"button\"></button></div></div>");$templateCache.put('$/angular/views/user_registration.html', "<div class=\"loginBackground\"><div class=\"router-bar\"><div class=\"awac_logo\"></div><!--ul.interface_menu<li><a ng-class=\"{selected:$root.instanceName == 'enterprise'}\" class=\"btn btn-primary\" href=\"/enterprise\">Entreprises</a></li><li><a ng-class=\"{selected:$root.instanceName == 'municipality'}\" class=\"btn btn-primary\" href=\"/municipality\">Communes</a></li>--></div><div class=\"registrationFrame\" ng-enter=\"enterEvent()\"><select style=\"float:right\" ng-options=\"l.value as l.label for l in $root.languages\" ng-model=\"$root.language\"></select><tabset><tab class=\"tab-color-lightgreen\" active=\"tabActive[0]\"><tab-heading><span ng-bind-html=\"'USER_REGISTRATION' | translate\"></span></tab-heading><div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"emailInfo\"></mm-awac-modal-field-text></div><div ng-hide=\"isLoading === true\"><button class=\"button btn btn-primary\" ng-disabled=\"!connectionFieldValid()\" ng-click=\"send()\" ng-bind-html=\"'USER_REGISTER_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading === true\"></div></tab><!--tab.tab-color-lightgreen(active=\"tabActive[1]\", ng-show=\"false\")--><!--tab-heading--><!--span(ng-bind-html=\"'LOGIN_FORGOT_PASSWORD' | translate\")--><!--div--><!--div(ng-show=\"forgotEmailSuccessMessage!=null\", class=\"forgot_password_success_message\") {{forgotEmailSuccessMessage}}--><!----><!--div(ng-hide=\"forgotEmailSuccessMessage!=null\")--><!--div(ng-bind-html=\"'LOGIN_FORGOT_PASSWORD_DESC' | translate\")--><!----><!--div(class=\"field_form\")--><!--mm-awac-modal-field-text(ng-info=\"forgotPasswordInfo\")--><!----><!--div(ng-hide=\"isLoading === true\")--><!----><!--button.button(--><!--type=\"button\",--><!--class=\"btn btn-primary\",--><!--ng-bind-html=\"'SUBMIT' | translate\",--><!--ng-click=\"sendForgotPassword()\",--><!--ng-disabled=\"!forgotPasswordFieldValid()\")--><!----><!--img(ng-show=\"isLoading === true\",src=\"/assets/images/modal-loading.gif\")--><!--tab.tab-color-lightgreen(active=\"tabActive[2]\")--><!--tab-heading--><!--span(ng-bind-html=\"'LOGIN_REGISTRATION' | translate\")--><!--div.inject-registration-form--></tabset></div></div>");$templateCache.put('$/angular/views/user_data.html', "<div><div class=\"menu_close\" ng-click=\"toForm()\"></div><h1>user profile</h1><style>.edit_icon {\n    width: 22px;\n    height: 22px;\n    top: -1px;\n}</style><div class=\"user_data\"><div style=\"display:table\" class=\"field_form\"><mm-awac-modal-field-text ng-info=\"identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"><button title=\"{{'UPDATE_PASSWORD_BUTTON' | translateText}}\" ng-click=\"changePassword()\" class=\"edit_icon glyphicon glyphicon-pencil\" type=\"button\"></button></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"emailInfo\"><button title=\"{{'UPDATE_EMAIL_BUTTON' | translateText}}\" ng-click=\"changeEmail()\" class=\"edit_icon glyphicon glyphicon-pencil\" type=\"button\"></button></mm-awac-modal-field-text><br><div style=\"display:table-row\"><div style=\"display:table-cell\"></div><div style=\"display:table-cell\"></div><div style=\"display:table-cell\"><div style=\"text-align: right\" ng-hide=\"isLoading\"><button ng-disabled=\"!allFieldValid()\" ng-click=\"send()\" ng-bind-html=\"'SAVE_BUTTON' | translate\" class=\"btn btn-primary\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div></div>");$templateCache.put('$/angular/views/municipality/TAB_C2.html', "<div><mm-awac-section title-code=\"AC9\"><!--main loop--><mm-awac-repetition-name question-code=\"AC10\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC10\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC10')\"><mm-awac-question question-code=\"AC11\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC12\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC12',itLevel1).value == '8'\" question-code=\"AC13\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC14\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC15\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC16\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC17\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC18\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC19\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC20\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC21\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC19',itLevel1).value == '1' || getAnswer('AC19',itLevel1).value == '3'\" question-code=\"AC22\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC23\" ng-repetition-map=\"itLevel1\"></mm-awac-question><!--AC24--><mm-awac-sub-title question-code=\"AC24\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AC25\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"AC25\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC25', itLevel1)\"><mm-awac-question question-code=\"AC26\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC27\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC25',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC25_LOOPDESC' | translate\"></span></button><!--AC28--><mm-awac-sub-title question-code=\"AC28\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"AC29\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"AC30\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC31\" ng-repetition-map=\"itLevel1\"></mm-awac-question><!--AC32--><mm-awac-block ng-condition=\"getAnswer('AC19',itLevel1).value == '1' || getAnswer('AC19',itLevel1).value == '3'\"><mm-awac-sub-title question-code=\"AC32\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AC33\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"AC33\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC33', itLevel1)\"><mm-awac-question question-code=\"AC34\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC35\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC36\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC33',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC33_LOOPDESC' | translate\"></span></button></mm-awac-block><!--AC37--><mm-awac-sub-title question-code=\"AC37\" ng-repetition-map=\"itLevel1\"></mm-awac-sub-title><mm-awac-question question-code=\"AC38\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC39\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"AC39\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC39', itLevel1)\"><mm-awac-question question-code=\"AC40\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC41\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC39',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC39_LOOPDESC' | translate\"></span></button><!--AC42--><mm-awac-block ng-condition=\"getAnswer('AC19',itLevel1).value == '1' || getAnswer('AC19',itLevel1).value == '3'\"><mm-awac-sub-title question-code=\"AC42\" ng-repetition-map=\"itLevel1\"></mm-awac-sub-title><mm-awac-question question-code=\"AC43\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC44\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel2\" question-set-code=\"AC44\" ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC44', itLevel1)\"><mm-awac-question question-code=\"AC45\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC46\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC45',itLevel2).value != 'AS_186' || getAnswer('AC45',itLevel2).value != 'AS183'\" question-code=\"AC47\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC45',itLevel2).value != 'AS_186'\" question-code=\"AC48\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC45',itLevel2).value == 'AS_183'\" question-code=\"AC49\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC45',itLevel2).value == 'AS_183'\" question-code=\"AC50\" ng-repetition-map=\"itLevel2\"></mm-awac-question><mm-awac-question question-code=\"AC51\" ng-repetition-map=\"itLevel2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC44',itLevel1)\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC44_LOOPDESC' | translate\"></span></button></mm-awac-block></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC10')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC10_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><!--éclairage public AS52--><mm-awac-section title-code=\"AC52\"><mm-awac-question question-code=\"AC53\"></mm-awac-question><mm-awac-question question-code=\"AC54\"></mm-awac-question><mm-awac-question question-code=\"AC55\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC56\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC56\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC56')\"><mm-awac-question question-code=\"AC57\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC58\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC59\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC56')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC56_LOOPDESC' | translate\"></span></button></mm-awac-section></div>");$templateCache.put('$/angular/views/municipality/TAB_C4.html', "<div><!--achat bien et service--><mm-awac-section title-code=\"AC114\"><mm-awac-question question-code=\"AC115\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC116\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC116\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC116')\"><mm-awac-question question-code=\"AC117\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC118\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_61'\" question-code=\"AC119\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_62'\" question-code=\"AC120\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_63'\" question-code=\"AC121\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_64'\" question-code=\"AC122\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_65'\" question-code=\"AC123\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_66'\" question-code=\"AC124\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_67'\" question-code=\"AC125\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_68'\" question-code=\"AC126\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_61' || getAnswer('AC118',itLevel1).value == 'AT_62' || getAnswer('AC118',itLevel1).value == 'AT_63' || getAnswer('AC118',itLevel1).value == 'AT_64'\" question-code=\"AC127\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_61' || getAnswer('AC118',itLevel1).value == 'AT_62' || getAnswer('AC118',itLevel1).value == 'AT_63' || getAnswer('AC118',itLevel1).value == 'AT_64' || getAnswer('AC118',itLevel1).value == 'AT_65' || getAnswer('AC118',itLevel1).value == 'AT_66' || getAnswer('AC118',itLevel1).value == 'AT_67'\" question-code=\"AC128\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_68'\" question-code=\"AC129\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC116')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC116_LOOPDESC' | translate\"></span></button></mm-awac-section></div>");$templateCache.put('$/angular/views/municipality/TAB_C3.html', "<div><!--mobilite AC60--><mm-awac-section title-code=\"AC60\"><mm-awac-question question-code=\"AC61\"></mm-awac-question><!--transport routier AC62--><mm-awac-sub-title question-code=\"AC62\"></mm-awac-sub-title><mm-awac-question question-code=\"AC63\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"AC64\"></mm-awac-sub-sub-title><div class=\"element_content\"><div class=\"element_text\">Plusieurs méthodes différentes existent pour rapporter les déplacements routiers. Elles vous sont présentées de la meilleure à la plus approximative. L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Le système utilisera les données de la meilleure méthode entièrement complétée pour effectuer le calcul.</div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><span ng-bind-html=\"'AC65' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><!--véhicule AC66--><mm-awac-repetition-name question-code=\"AC66\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC66\" ng-tab-set=\"1\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC66')\" ng-tab=\"1\"><mm-awac-question question-code=\"AC67\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"AC68\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC68',itLevel1).value == '2'\" question-code=\"AC69\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"AC70\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question><mm-awac-question question-code=\"AC71\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC66')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC66_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><span ng-bind-html=\"'AC72' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><!--kilomètrage AC 73--><mm-awac-repetition-name question-code=\"AC73\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC73\" ng-tab-set=\"1\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC73')\" ng-tab=\"2\"><mm-awac-question question-code=\"AC74\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC75\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC75',itLevel1).value == '2'\" question-code=\"AC76\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC77\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC78\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC78',itLevel1).value == '1' || getAnswer('AC78',itLevel1).value == '2' \" question-code=\"AC79\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC78',itLevel1).value == '5' || getAnswer('AC78',itLevel1).value == '8'|| getAnswer('AC78',itLevel1).value == '9' \" question-code=\"AC80\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC78',itLevel1).value == '6' || getAnswer('AC78',itLevel1).value == '7' \" question-code=\"AC81\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC78',itLevel1).value == '3' || getAnswer('AC78',itLevel1).value == '4' \" question-code=\"AC82\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question><mm-awac-question question-code=\"AC83\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"2\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC73')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC73_LOOPDESC' | translate\"></span></button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(1,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,3)\"></i><span ng-bind-html=\"'AC84' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><!--dépense AC 85--><mm-awac-repetition-name question-code=\"AC85\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC85\" ng-tab-set=\"1\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC85')\" ng-tab=\"3\"><mm-awac-question question-code=\"AC86\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC87\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC88\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC89\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question question-code=\"AC90\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC90',itLevel1).value == '2'\" question-code=\"AC91\" ng-tab-set=\"1\" ng-repetition-map=\"itLevel1\" ng-tab=\"3\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC85')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC85_LOOPDESC' | translate\"></span></button></div></div></tab></tabset></div></div><!--transport public AC92--><mm-awac-sub-title question-code=\"AC92\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"AC93\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"AC94\"></mm-awac-question><mm-awac-question question-code=\"AC95\"></mm-awac-question><mm-awac-question question-code=\"AC96\"></mm-awac-question><mm-awac-question question-code=\"AC97\"></mm-awac-question><!--déplacements professionels AC98--><mm-awac-sub-title question-code=\"AC98\"></mm-awac-sub-title><mm-awac-question question-code=\"AC99\"></mm-awac-question><mm-awac-question question-code=\"AC100\"></mm-awac-question><mm-awac-question question-code=\"AC101\"></mm-awac-question><mm-awac-question question-code=\"AC102\"></mm-awac-question><mm-awac-question question-code=\"AC103\"></mm-awac-question><mm-awac-question question-code=\"AC104\"></mm-awac-question><mm-awac-question question-code=\"AC105\"></mm-awac-question><!--déplacement avion AC106--><mm-awac-sub-title question-code=\"AC106\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AC107\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC107\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC107')\"><mm-awac-question question-code=\"AC108\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC109\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC110\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC111\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC112\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC113\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC107')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC107_LOOPDESC' | translate\"></span></button></mm-awac-section></div>");$templateCache.put('$/angular/views/municipality/TAB_C1.html', "<mm-awac-section title-code=\"AC1\"><mm-awac-sub-title question-code=\"AC2\"></mm-awac-sub-title><mm-awac-question question-code=\"AC3\"></mm-awac-question><mm-awac-question question-code=\"AC4\"></mm-awac-question><mm-awac-question question-code=\"AC5\"></mm-awac-question><mm-awac-question question-code=\"AC6\"></mm-awac-question><mm-awac-question question-code=\"AC7\"></mm-awac-question><mm-awac-question question-code=\"AC8\"></mm-awac-question></mm-awac-section>");$templateCache.put('$/angular/views/municipality/TAB_C5.html', "<div><!--achat bien et service--><mm-awac-section title-code=\"AC130\"><mm-awac-question question-code=\"AC131\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC132\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC132\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC132')\"><mm-awac-question question-code=\"AC133\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"(getAnswer('AC133',itLevel1).value | stringToFloat) &lt; 18   || getAnswer('AC133',itLevel1).value == '23'\" question-code=\"AC134\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC133',itLevel1).value == '20' || getAnswer('AC133',itLevel1).value == '21'|| getAnswer('AC133',itLevel1).value == '22'\" question-code=\"AC135\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question ng-condition=\"getAnswer('AC133',itLevel1).value == '18' || getAnswer('AC133',itLevel1).value == '19'\" question-code=\"AC136\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC132')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC132_LOOPDESC' | translate\"></span></button></mm-awac-section><div class=\"horizontal_separator\"></div><!--investissement AC137--><mm-awac-section title-code=\"AC137\"><mm-awac-question question-code=\"AC138\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC139\"></mm-awac-repetition-name><mm-awac-repetition-question ng-iteration=\"itLevel1\" question-set-code=\"AC139\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC139')\"><mm-awac-question question-code=\"AC140\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC141\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC142\" ng-repetition-map=\"itLevel1\"></mm-awac-question><mm-awac-question question-code=\"AC143\" ng-repetition-map=\"itLevel1\"></mm-awac-question></mm-awac-repetition-question><button class=\"button add-repetition-button\" ng-click=\"addIteration('AC139')\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span><span ng-bind-html=\"'AC139_LOOPDESC' | translate\"></span></button></mm-awac-section></div>");$templateCache.put('$/angular/templates/mm-awac-registration-enterprise.html', "<div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"emailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordConfirmInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"organizationNameInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"firstSiteNameInfo\"></mm-awac-modal-field-text><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-disabled=\"!registrationFieldValid()\" ng-click=\"registration()\" ng-bind-html=\"'REGISTRATION_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div>");$templateCache.put('$/angular/templates/mm-awac-sub-sub-title.html', "<div><div class=\"sub_sub_title\"><div class=\"question_info\" ng-show=\"hasDescription()\"><div class=\"question_info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-string-question.html', "<input class=\"oneelement\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true\" style=\"text-align:right;\" name=\"{{ getQuestionCode() }}\" ng-model=\"getAnswer().value\" type=\"text\">");$templateCache.put('$/angular/templates/mm-awac-document-question.html', "<div class=\"oneelement document-question-block document-question\"><span ng-hide=\"getDataToCompare()==true|| getIsAggregation()===true\">Upload new documents</span><div class=\"document-question-progress-bar\" ng-show=\"inDownload\"><div ng-style=\"style\"><spa></spa></div></div><div class=\"document-question-progress-percentage\" ng-show=\"inDownload\">{{percent}} %</div><input name=\"{{ getQuestionCode() }}\" ng-file-select=\"onFileSelect($files)\" type=\"file\" ng-hide=\"getDataToCompare()==true|| getIsAggregation()===true\"><div ng-show=\"getFileNumber()&gt;0\">{{getFileNumber()}} document had already uploaded</div><button class=\"button\" ng-click=\"openDocumentManager()\" type=\"button\" ng-show=\"getFileNumber()&gt;0\">consult this documents</button></div>");$templateCache.put('$/angular/templates/mm-awac-modal-connection-password-change.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'PASSWORD_CHANGE_FORM_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div ng-bind-html=\"'CONNECTION_PASSWORD_CHANGE_FORM_DESC' | translate\"></div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"newPasswordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newPasswordConfirmInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-password-change.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'PASSWORD_CHANGE_FORM_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"oldPasswordInfo\"></mm-awac-modal-field-text><br><mm-awac-modal-field-text ng-info=\"newPasswordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newPasswordConfirmInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-graph-donut.html', "<table><tr><td><canvas class=\"holder\" height=\"200\" width=\"400\"></canvas></td><td class=\"chart-legend\"><b>Legend</b><div ng-bind-html=\"legend\"></div></td></tr></table>");$templateCache.put('$/angular/templates/mm-awac-modal-question-comment.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'MODAL_QUESTION_COMMENT_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><textarea ng-disabled=\"getParams().canBeEdited === false\" focus-me=\"true\" name=\"comment\" ng-model=\"comment\" class=\"question-comment-textarea\"></textarea></div></div><div class=\"modal-footer\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-hide=\"getParams().canBeEdited === false\"></button></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-repetition-name.html', "<div><div class=\"repetition-title\"><div class=\"question_info\" ng-show=\"hasDescription()\"><div class=\"question_info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span class=\"glyphicon glyphicon-record\"></span><span ng-bind-html=\"getQuestionCode() | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-email-change.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'EMAIL_CHANGE_FORM_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text><br><mm-awac-modal-field-text ng-info=\"oldEmailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newEmailInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-repetition-question-disabled.html', "<ng-virtual><div><div class=\"repetition-title\"><div class=\"info\" ng-show=\"hasDescription()\"><div class=\"info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span class=\"glyphicon glyphicon-record\"></span><span ng-bind-html=\"getQuestionCode() | translate\"></span></div></div><div ng-iteration=\"itLevel\" ng-repeat=\"itLevel in getRepetitionMapByQuestionSet()\" ng-hide=\"getCondition() === false\"><div class=\"repetition-question\"><div class=\"repetition-question-title\" style=\"display : inline-block; margin-right : 20px\" ng-bind-html=\"getQuestionCode() + '_LOOPDESC' | translate\"></div><button class=\"remove-button button\" ng-click=\"removeAnwser()\" type=\"button\">Supprimer</button><div class=\"repetition-question-container\"><ng-virtual ng-transclude class=\"element_stack\"></ng-virtual></div></div></div><button class=\"button add-repetition-button\" ng-click=\"addIteration()\" type=\"button\"><span style=\"margin-right : 5px\" ng-bind-html=\"'ADD_NEW_ITERATION' | translate\"></span></button><span ng-bind-html=\"getQuestionCode() + '_LOOPDESC' | translate\"></span></ng-virtual>'");$templateCache.put('$/angular/templates/mm-awac-enterprise-survey.html', "<div><div ng-hide=\"$root.hideHeader()\"><div class=\"survey-header\"><table class=\"survey-header-option\"><tr><td><div><select ng-options=\"l.value as l.label for l in $root.languages\" ng-model=\"$root.language\"></select></div></td><td><div>Gestion</div></td><td><div ng-show=\"$root.currentPerson!=null\"><span ng-bind-html=\"'WELCOME' | translate\"></span>,<span class=\"username\">{{$root.currentPerson.firstName}} {{$root.currentPerson.lastName}}</span></div><div ng-show=\"$root.currentPerson==null\">Your are currently not connected</div></td></tr><tr><td><button class=\"button confidentiality\" mm-not-implemented=\"mm-not-implemented\" type=\"button\">Confidentialité</button><button class=\"button help\" mm-not-implemented=\"mm-not-implemented\" type=\"button\">Assistance</button></td><td><!--site manager button--><button class=\"button user_manage\" ng-click=\"nav('/site_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/site_manager') == true}\" ng-bind-html=\"'SITE_MANAGER_BUTTON' | translate\" type=\"button\"></button><!--user manager button--><button class=\"button user_manage\" ng-click=\"nav('/user_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_manager') == true}\" ng-bind-html=\"'USER_MANAGER_BUTTON' | translate\" type=\"button\"></button></td><td><!--user data button--><button class=\"button user_manage\" ng-click=\"nav('/user_data')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_data') == true}\" ng-bind-html=\"'USER_DATA_BUTTON' | translate\" type=\"button\"></button><!--logout button--><button class=\"button user_manage\" ng-click=\"$root.logout();\" ng-bind-html=\"'LOGOUT_BUTTON' | translate\" type=\"button\" ng-show=\"$root.currentPerson!=null\"></button></td></tr></table><div class=\"wallonie_logo\"></div><div class=\"awac_logo\"></div><div><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_ENTERPRISE' | translate\"></div><div class=\"entreprise_name\">{{ $root.organization.name }}</div></div></div><div class=\"data_menu\" ng-show=\"displayMenu===true\"><div class=\"data_date\"><div ng-bind-html=\"'PERIOD_DATA' | translate\"></div><select ng-options=\"p.key as p.label for p in $root.periods\" ng-model=\"periodKey\"></select></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><div>Site sélectionné</div><select ng-options=\"s.scope as s.name for s in $root.organization.sites\" ng-model=\"scopeId\"></select></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><div>Comparaison avec</div><select ng-options=\"p.key as p.label for p in periodsForComparison\" ng-model=\"periodToCompare\"></select></div><div class=\"big_separator\"></div><div class=\"data_save\"><div class=\"last_save\" ng-hide=\"lastSaveTime===null\"><span ng-bind-html=\"'LAST_SAVE' | translate\"></span><br>{{lastSaveTime | date: 'medium' }}</div><div class=\"small_separator\"></div><div class=\"save_button\"><button class=\"button save\" ng-click=\"save()\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div></div></div><div class=\"nav_tabs\" ng-show=\"displayMenu===true\"><div class=\"nav_entreprise\"><div class=\"site_menu\"><div class=\"site\"><button class=\"button verification\" mm-not-implemented=\"mm-not-implemented\" type=\"button\">Vérification</button></div><div class=\"menu\"><button class=\"button\" ng-click=\"nav('/enterprise-tab2')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/enterprise-tab2') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB2' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB2')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"nav('/enterprise-tab3')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/enterprise-tab3')  == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB3' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB3')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"nav('/enterprise-tab4')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/enterprise-tab4') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB4' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB4')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"nav('/enterprise-tab5')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/enterprise-tab5') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB5' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB5')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"nav('/enterprise-tab6')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/enterprise-tab6') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB6' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB6')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"nav('/enterprise-tab7')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/enterprise-tab7') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB7' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB7')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button></div></div><div class=\"last_menu\"><button class=\"button\" ng-click=\"nav('/results')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/results') == true}\"><div class=\"tab-title\">résultats</div></button><button class=\"button\" mm-not-implemented=\"mm-not-implemented\"><div class=\"tab-title\">actions de réduction</div></button></div></div></div></div><div class=\"{{getClassContent()}}\" ng-view></div><div class=\"footer\"></div></div>");$templateCache.put('$/angular/templates/mm-awac-percentage-question.html', "<span class=\"twoelement\"><input ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true\" style=\"text-align:right;\" numbers-only=\"percent\" name=\"{{ getQuestionCode() }}\" ng-model=\"getAnswer().value\" type=\"text\"><span style=\"margin-left:5px\">%</span></span>");$templateCache.put('$/angular/templates/mm-awac-sub-title.html', "<div><div class=\"sub_title\"><div class=\"question_info\" ng-show=\"hasDescription()\"><div class=\"question_info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-edit-site.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'EDIT_SITE_TITLE_CREATE' | translate\" class=\"modal-title\" ng-show=\"createNewSite === true\"></h4><h4 id=\"myModalLabel\" ng-bind-html=\"'EDIT_SITE_TITLE_EDIT' | translate\" class=\"modal-title\" ng-hide=\"createNewSite === true\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.name\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.description\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.nace\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.orgStructure\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.ecoInterest\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.opePolicy\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.accountingTreatment\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.percentOwned\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-section.html', "<ng-virtual><div class=\"element\"><div class=\"element_header\"><div class=\"title\" ng-bind-html=\"getTitleCode() | translate\"></div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\"><div class=\"block_status\" mm-not-implemented><div class=\"lock_status disable_status\"></div><div class=\"answered_status\"></div><div class=\"validate_status\"></div></div></div><div class=\"element_content\"><div ng-transclude ng-class=\"getMode()\"></div></div></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-question.html', "<div ng-class=\"{'twoanswer':displayOldDatas()===true, 'oneanswer':displayOldDatas()===false,'condition-false':getCondition() === false}\" class=\"question_field\"><div><div class=\"question_info\" ng-show=\"hasDescription()\"><div class=\"question_info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-class=\"getIcon()\" class=\"glyphicon\"></span><span ng-click=\"logQuestionCode()\" ng-bind-html=\"getQuestionCode() | translate\"></span></div><div><div class=\"status\" ng-class=\"getStatusClass()\"></div><div class=\"error_message\" ng-show=\"errorMessage.length&gt;0\"><div>{{errorMessage}}</div><img src=\"/assets/images/question_field_error_message_icon_arrow.png\"></div><span class=\"inject-data\"></span><button class=\"button edit_comment_icon glyphicon glyphicon-pencil\" ng-click=\"editComment()\" name=\"{{ getQuestionCode() }}_COMMENT\" ng-class=\"{edit_comment_icon_selected:getAnswer().comment !=null}\" ng-hide=\"getAggregation()!=null\"></button><div class=\"user_icon\" ng-hide=\"getAggregation()!=null || getAnswer().value == null\">{{getUserName(false,true)}}<div><span>{{getUserName(false,false)}}</span><img src=\"/assets/images/user_icon_arrow.png\"></div></div></div><div ng-show=\"displayOldDatas() === true &amp;&amp; getAnswer(true) != null\"><button class=\"button\" title=\"Copier la valeur\" ng-click=\"copyDataToCompare()\"><<</button><span class=\"inject-data-to-compare\"></span><button class=\"button edit_comment_icon glyphicon glyphicon-pencil edit_comment_icon_selected\" ng-click=\"editComment(false)\" name=\"OLD_{{ getQuestionCode() }}_COMMENT\" ng-hide=\"getAggregation()!=null || getAnswer(true).comment ==null\"></button><div class=\"edit_comment_icon\" ng-hide=\"getAnswer(true).comment !=null\"></div><div class=\"user_icon\">{{getUserName(true,true)}}<div><div>{{getUserName(true,false)}}</div><img src=\"/assets/images/user_icon_arrow.png\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-login-disabled.html', "<!--Modal--><div class=\"modal\" id=\"modalLogin\" aria-labelledby=\"myModalLabel\" ng-enter=\"send()\" aria-hidden=\"true\" tabindex=\"-1\" role=\"dialog\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><!--button.button(type=\"button\",class=\"close\",data-dismiss=\"modal\")<span aria-hidden=\"true\">&times;<span</span><span class=\"sr-only\">Close</span>--><h4 id=\"myModalLabel\" class=\"modal-title\">Login</h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><!--button.button(type=\"button\",class=\"btn btn-default\",data-dismiss=\"modal\") Close--><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"send();\" ng-enabled=\"allFieldValid()\" type=\"button\">Login</button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"test();\" ng-enabled=\"allFieldValid()\" type=\"button\">test</button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-result-table.html', "<ng-virtual><table class=\"indicators_table\"><thead><th width=\"100%\"></th><th class=\"align-right scope1\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_1' | translate\"></span><span>(tCO2e)</span></span></div></th><th class=\"align-right scope2\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_2' | translate\"></span><span>(tCO2e)</span></span></div></th><th class=\"align-right scope3\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_3' | translate\"></span><span>(tCO2e)</span></span></div></th><th class=\"align-right out-of-scope\"><div><span class=\"wrapped\"><span ng-bind-html=\"'OUT_OF_SCOPE' | translate\"></span><span>(tCO2e)</span></span></div></th></thead><tbody><tr ng-show=\"showAll || (rl.scope1Value + rl.scope2Value + rl.scope3Value + rl.outOfScopeValue &gt; 0)\" ng-repeat=\"rl in ngModel.reportLines\"><td><span class=\"circled-number\" ng-show=\"getNumber(rl) != null\">{{ getNumber(rl) }}</span><span ng-bind-html=\"rl.indicatorName\"></span></td><td class=\"align-right scope1\"><span ng-bind-html=\"rl.scope1Value | numberToI18NOrLess\" ng-show=\"rl.scope1Value &gt; 0\"></span></td><td class=\"align-right scope2\"><span ng-bind-html=\"rl.scope2Value | numberToI18NOrLess\" ng-show=\"rl.scope2Value &gt; 0\"></span></td><td class=\"align-right scope3\"><span ng-bind-html=\"rl.scope3Value | numberToI18NOrLess\" ng-show=\"rl.scope3Value &gt; 0\"></span></td><td class=\"align-right out-of-scope\"><span ng-bind-html=\"rl.outOfScopeValue | numberToI18NOrLess\" ng-show=\"rl.outOfScopeValue &gt; 0\"></span></td></tr></tbody><tfoot><tr><td><span ng-bind-html=\"'RESULTS_TOTAL' | translate\"></span><span>{{ (totalScope1 + totalScope2 + totalScope3 + totalOutOfScope) | numberToI18NOrLess }}</span><span>(tCO2e)</span></td><td class=\"align-right scope1\"><span ng-bind-html=\"getTotalScope1() | numberToI18NOrLess\"></span></td><td class=\"align-right scope2\"><span ng-bind-html=\"getTotalScope2() | numberToI18NOrLess\"></span></td><td class=\"align-right scope3\"><span ng-bind-html=\"getTotalScope3() | numberToI18NOrLess\"></span></td><td class=\"align-right out-of-scope\"><span ng-bind-html=\"getTotalOutOfScope() | numberToI18NOrLess\"></span></td></tr></tfoot></table><div><input ng-model=\"showAll\" type=\"checkbox\"><label><span ng-bind-html=\"'SHOW_ALL_INDICATORS' | translate\"></span></label></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-block.html', "<ng-virtual><div ng-transclude ng-class=\"{true:'condition-false', false:''}[getCondition() === false]\"></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-municipality-survey.html', "<ng-virtual><div ng-hide=\"$root.isLogin()\"><div class=\"survey-header\"><!--user block--><table class=\"survey-header-option\"><tr><td><div><select ng-options=\"l.value as l.label for l in $root.languages\" ng-model=\"$root.language\"></select></div></td><td><div>Gestion</div></td><td><div ng-show=\"$root.currentPerson!=null\"><span ng-bind-html=\"'WELCOME' | translate\"></span>,<span class=\"username\">{{$root.currentPerson.firstName}} {{$root.currentPerson.lastName}}</span></div><div ng-show=\"$root.currentPerson==null\">Your are currently not connected</div></td></tr><tr><td><button class=\"button confidentiality\" mm-not-implemented=\"mm-not-implemented\" type=\"button\">Confidentialité</button><button class=\"button help\" mm-not-implemented=\"mm-not-implemented\" type=\"button\">Assistance</button></td><td><!--user manager button--><button class=\"button user_manage\" ng-click=\"nav('/user_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_manager') == true}\" ng-bind-html=\"'USER_MANAGER_BUTTON' | translate\" type=\"button\"></button></td><td><!--user data button--><button class=\"button user_manage\" ng-click=\"nav('/user_data')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_data') == true}\" ng-bind-html=\"'USER_DATA_BUTTON' | translate\" type=\"button\"></button><!--logout button--><button class=\"button user_manage\" ng-click=\"$root.logout();\" ng-bind-html=\"'LOGOUT_BUTTON' | translate\" type=\"button\" ng-show=\"$root.currentPerson!=null\"></button></td></tr></table><div class=\"wallonie_logo\"></div><div class=\"awac_logo\"></div><div><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_MUNICIPALITY' | translate\"></div><div class=\"entreprise_name\">{{ $root.organization.name }}</div></div></div><div class=\"data_menu\" ng-show=\"displayMenu===true\"><div class=\"data_date\"><div ng-bind-html=\"'PERIOD_DATA' | translate\"></div><select ng-options=\"p.key as p.label for p in $root.periods\" ng-model=\"periodKey\"></select></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><div>Comparaison avec</div><select ng-options=\"p.key as p.label for p in periodsForComparison\" ng-model=\"periodToCompare\"></select></div><div class=\"big_separator\"></div><div class=\"data_save\"><div class=\"last_save\" ng-hide=\"lastSaveTime===null\"><span ng-bind-html=\"'LAST_SAVE' | translate\"></span><br>{{lastSaveTime | date: 'medium' }}</div><div class=\"small_separator\"></div><div class=\"save_button\"><button class=\"button save\" ng-click=\"save()\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div></div></div><div class=\"nav_tabs\" ng-show=\"displayMenu===true\"><div class=\"nav_entreprise\"><div class=\"site_menu\"><div class=\"site\"><button class=\"button verification\" mm-not-implemented=\"mm-not-implemented\" type=\"button\">Vérification</button></div><div class=\"menu\"><button class=\"button\" ng-click=\"nav('/municipality-tab1')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/municipality-tab1') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C1' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C1')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"nav('/municipality-tab2')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/municipality-tab2') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C2' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C2')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"nav('/municipality-tab3')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/municipality-tab3')  == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C3' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C3')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"nav('/municipality-tab4')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/municipality-tab4') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C4' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C4')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-click=\"nav('/municipality-tab5')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/municipality-tab5') == true}\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C5' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C5')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button></div></div><div class=\"last_menu\"><button class=\"button\" ng-click=\"nav('/results')\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/results') == true}\"><div class=\"tab-title\">résultats</div></button><button class=\"button\" mm-not-implemented=\"mm-not-implemented\"><div class=\"tab-title\">actions de réduction</div></button></div></div></div></div><div class=\"{{getClassContent()}}\" ng-view></div><div class=\"footer\"></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-modal-field-text.html', "<tr><td ng-click=\"logField()\" ng-bind-html=\"getInfo().fieldTitle | translate\"></td><td><input ng-disabled=\"getInfo().disabled\" placeholder=\"{{getInfo().placeholder | translateText}}\" focus-me=\"getInfo().focus()\" name=\"{{ getInfo().fieldTitle }}\" ng-model=\"getInfo().field\" type=\"{{fieldType}}\"></td><td><div ng-if=\"isValidationDefined\"><img src=\"/assets/images/field_valid.png\" ng-if=\"!hideIsValidIcon\" ng-show=\"getInfo().isValid\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/assets/images/field_invalid.png\"><div ng-bind-html=\"getInfo().validationMessage | translate\"></div></div></div><div ng-transclude></div></td></tr>");$templateCache.put('$/angular/templates/mm-awac-select-question.html', "<select class=\"oneelement\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true\" style=\"text-align:right;\" name=\"{{ getQuestionCode() }}\" ng-options=\"p.key as p.label for p in getOptions()\" ng-model=\"getAnswer().value\"></select>");$templateCache.put('$/angular/templates/mm-awac-modal-invite-user.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'USER_INVITATION_FORM_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"inviteEmailInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-integer-question.html', "<input class=\"oneelement\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true\" style=\"text-align:right;\" numbers-only=\"integer\" name=\"{{ getQuestionCode() }}\" ng-model=\"getAnswer().value\" type=\"text\">");$templateCache.put('$/angular/templates/mm-awac-tab-progress-bar.html', "<div class=\"tab-pg-bar\"><div class=\"tab-pg-text\"><span ng-bind-html=\"'FILLED_BY' | translate\"></span><span>&nbsp;</span><span>{{ pg }}%</span></div><div class=\"tab-pg-background tab-pb-{{color}}-bg\"><div style=\"width: {{ pg }}%\" class=\"tab-pg-indicator tab-pb-{{color}}-fg\"></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-boolean-question.html', "<span class=\"twoelement\"><span style=\"text-align:center\"><span style=\"vertical-align:middle;margin-right : 15px;\">oui</span><input ng-disabled=\"getDataToCompare()===true || getIsAggregation()===true\" style=\"width :20px !important;margin:0;vertical-align:middle;\" name=\"{{getQuestionCode()}}\" value=\"1\" ng-model=\"getAnswer().value\" type=\"radio\"></span><span style=\"text-align:center\"><span style=\"vertical-align:middle;margin-right : 15px;\">non</span><input ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true\" style=\"width :20px !important;margin:0;vertical-align:middle;\" name=\"{{getQuestionCode()}}\" value=\"0\" ng-model=\"getAnswer().value\" type=\"radio\"></span></span>");$templateCache.put('$/angular/templates/mm-awac-modal-document-manager.html', "<!--Modal--><div class=\"modal\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;<span</span><span class=\"sr-only\">Close</span></button><h4 class=\"modal-title\">Documents uploaded</h4></div><div class=\"modal-body\"><table class=\"document-manager-table\"><thead><tr><td>Document name</td><td>Actions</td></tr></thead><tbody><tr ng-repeat=\"doc in listDocuments\"><td>{{doc}}</td><td><button class=\"button\" ng-click=\"download(doc.id)\" type=\"button\">download</button><button class=\"button\" ng-click=\"removeDoc(doc.id)\" type=\"button\">remove</button></td></tr></tbody></table></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-add-user-site.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 id=\"myModalLabel\" ng-bind-html=\"'SITE_MANAGER_ADD_USER_TITLE' | translate\" class=\"modal-title\" ng-show=\"createNewSite === true\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><table class=\"document-manager-table\"><thead><tr><td ng-bind-html=\"'SITE_MANAGER_ADD_NAME_LABEL' | translate\"></td><td ng-bind-html=\"'SITE_MANAGER_ADD_LOGIN_LABEL' | translate\"></td><td ng-bind-html=\"'SITE_MANAGER_ADD_SELECTED_LABEL' | translate\"></td></tr></thead><tbody><tr ng-repeat=\"account in accounts\"><td> {{account.person.firstName}} {{account.person.lastName}}</td><td> {{account.identifier}}</td><td><input ng-click=\"toggleSelection(account)\" name=\"{{account.identifier}}\" value=\"{{account.identifier}}\" type=\"checkbox\" ng-checked=\"selection.indexOf(account) &gt; -1\"></td></tr></tbody></table><table class=\"document-manager-table\"><thead><tr><td ng-bind-html=\"'SITE_MANAGER_ADD_LIST_LABEL' | translate\"></td></tr></thead><tbody><tr ng-repeat=\"account in selection\"><td>{{account.identifier}}</td></tr></tbody></table></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-disabled=\"!allFieldValid()\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-real-question.html', "<input class=\"oneelement\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true\" style=\"text-align:right;\" numbers-only=\"double\" name=\"{{ getQuestionCode() }}\" ng-model=\"getAnswer().value\" type=\"text\">");$templateCache.put('$/angular/templates/mm-awac-repetition-question.html', "<div ng-class=\"{true:'condition-false', false:''}[getCondition() === false]\"><div class=\"repetition-question\"><div class=\"repetition-question-title\" style=\"display : inline-block; margin-right : 20px\" ng-bind-html=\"getQuestionSetCode() + '_LOOPDESC' | translate\"></div><button class=\"button remove-button\" ng-click=\"removeAnwser()\" ng-bind-html=\"'DELETE' | translate\" type=\"button\"></button><div class=\"repetition-question-container\"><ng-virtual ng-transclude class=\"element_stack\"></ng-virtual></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-loading.html', "<!--Modal--><div class=\"modal\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\" style=\"text-align:center\"><h4>Loading</h4></div><div class=\"modal-body\" style=\"text-align:center\"><img src=\"/assets/images/loading_preorganization.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-real-with-unit-question.html', "<span class=\"twoelement\"><input ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true\" style=\"text-align:right;\" numbers-only=\"double\" name=\"{{ getQuestionCode() }}\" ng-model=\"getAnswer().value\" type=\"text\"><select ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true\" name=\"{{ getQuestionCode() }}_UNIT\" ng-options=\"p.code as p.name for p in getUnits()\" ng-model=\"getAnswer().unitCode\"></select></span>");$templateCache.put('$/angular/templates/mm-awac-registration-municipality.html', "<div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"emailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordConfirmInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"municipalityNameInfo\"></mm-awac-modal-field-text><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-disabled=\"!registrationFieldValid()\" ng-click=\"registration()\" ng-bind-html=\"'REGISTRATION_BUTTON' | translate\" type=\"button\"></button></div><img src=\"/assets/images/modal-loading.gif\" ng-show=\"isLoading\"></div>");$templateCache.put('$/angular/templates/mm-awac-modal-confirmation-exit-form.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;<span</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\" ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_MESSAGE' | translate\"></div></div><div class=\"modal-footer\"><button class=\"button\" ng-click=\"continue();\" ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_CONTINUE' | translate\" type=\"button\"></button><button class=\"button\" ng-click=\"save()\" focus-me=\"true\" ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_SAVE' | translate\" type=\"button\"></button><button class=\"button\" ng-click=\"close()\" ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_CANCEL' | translate\" type=\"button\"></button></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-manager.html', "<div></div>");$templateCache.put('$/angular/templates/mm-awac-admin-bad-importer.html', "<div><button class=\"btn btn-default\" ng-click=\"import()\">Import BAD</button><tabset style=\"margin-top:20px\"><tab class=\"tab-color-lightgreen\"><tab-heading style=\"margin-left:25px\"><span>BAD (Erros :  {{total_bad_error}} )</span></tab-heading><div style=\"border-top : 1px solid black\"><div class=\"element_table\"><table class=\"table\" style=\"width: 200px;\"><tr><td>Total BAd imported</td><td>{{total_bad}}</td></tr><tr><td>Bad with info</td><td>{{total_bad_info}}</td></tr><tr><td>Bad with warning</td><td>{{total_bad_warning}}</td></tr><tr><td>Bad with error</td><td>{{total_bad_error}}</td></tr></table><div class=\"width:100%\" loading-container=\"tableParams.settings().$loading\"><table class=\"table admin-table-import-bad width:100%\" ng-table=\"tableParams\"><tr ng-repeat=\"logLine in $data\"><td style=\"width:12.5%\" sortable=\"'lineNb'\" data-title=\"'Line nb'\">{{logLine.lineNb}}</td><td style=\"width:12.5%\" sortable=\"'name'\" data-title=\"'Code'\">{{logLine.name}}</td><td style=\"width:25%\" sortable=\"'messagesInfoNb'\" data-title=\"'Info'\"><ul><li ng-repeat=\"message in logLine.messages['INFO']\">{{message}}</li></ul></td><td style=\"width:25%\" sortable=\"'messagesWarningNb'\" data-title=\"'Warinig'\"><ul><li ng-repeat=\"message in logLine.messages['WARNING']\">{{message}}</li></ul></td><td style=\"width:25%\" sortable=\"'messagesErrorNb'\" data-title=\"'Error'\"><ul><li ng-repeat=\"message in logLine.messages['ERROR']\">{{message}}</li></ul></td></tr></table></div></div></div></tab><tab class=\"tab-color-lightgreen\"><tab-heading style=\"margin-left:25px\"><span>Questions (Erros :  {{total_question_error}} )</span></tab-heading><div style=\"border-top : 1px solid black\"><div class=\"element_table\"><table class=\"table\" style=\"width: 200px;\"><tr><td>Total Question with data imported</td><td>{{total_question}}</td></tr><tr><td>question with info</td><td>{{total_question_info}}</td></tr><tr><td>question with warning</td><td>{{total_question_warning}}</td></tr><tr><td>question with error</td><td>{{total_question_error}}</td></tr></table><div class=\"width:100%\" loading-container=\"tableParams2.settings().$loading\"><table class=\"table admin-table-import-bad width:100%\" ng-table=\"tableParams2\"><tr ng-repeat=\"logLine in $data\"><td style=\"width:12.5%\" sortable=\"'lineNb'\" data-title=\"'Line nb'\">{{logLine.lineNb}}</td><td style=\"width:12.5%\" sortable=\"'name'\" data-title=\"'Code'\">{{logLine.name}}</td><td style=\"width:25%\" sortable=\"'messagesInfoNb'\" data-title=\"'Info'\"><ul><li ng-repeat=\"message in logLine.messages['INFO']\">{{message}}</li></ul></td><td style=\"width:25%\" sortable=\"'messagesWarningNb'\" data-title=\"'Warinig'\"><ul><li ng-repeat=\"message in logLine.messages['WARNING']\">{{message}}</li></ul></td><td style=\"width:25%\" sortable=\"'messagesErrorNb'\" data-title=\"'Error'\"><ul><li ng-repeat=\"message in logLine.messages['ERROR']\">{{message}}</li></ul></td></tr></table></div></div></div></tab></tabset></div>");});